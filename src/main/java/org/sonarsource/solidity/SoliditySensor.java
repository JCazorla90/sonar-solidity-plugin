package org.sonarsource.solidity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonarsource.solidity.checks.ReentrancyRule;
import org.sonarsource.solidity.checks.UncheckedMathRule;
import org.sonarsource.solidity.checks.VisibilityRule;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SoliditySensor implements Sensor {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, String> slitherRuleDescriptions = new HashMap<>();

    public SoliditySensor() {
        slitherRuleDescriptions.put("reentrancy", "Reentrancy vulnerability detected by Slither.");
        slitherRuleDescriptions.put("unused-return", "Unused return value detected by Slither.");
        slitherRuleDescriptions.put("shadowing", "Variable shadowing detected by Slither.");
    }

    @Override
    public void execute(SensorContext context) {
        int contractCount = 0;
        int functionCount = 0;
        float totalComplexity = 0;
        int gasEstimation = 0;

        for (InputFile file : context.fileSystem().inputFiles(SolidityLanguage.PREDICATE)) {
            try {
                SolidityParser parser = SolidityParser.create(file.inputStream(), file.charset());
                SolidityParser.SourceUnitContext tree = parser.sourceUnit();

                contractCount += tree.contractDefinition().size();
                for (var contract : tree.contractDefinition()) {
                    functionCount += contract.functionDefinition().size();
                    for (var func : contract.functionDefinition()) {
                        totalComplexity += calculateComplexity(func);
                        gasEstimation += estimateGas(func);
                    }
                }

                new UncheckedMathRule().scanFile(context, file, tree);
                new ReentrancyRule().scanFile(context, file, tree);
                new VisibilityRule().scanFile(context, file, tree);
            } catch (Exception e) {
                context.newIssue()
                    .forRule("Solidity:ParsingError")
                    .at(context.newIssueLocation().on(file).message("Failed to analyze file: " + e.getMessage()))
                    .save();
            }
        }

        context.newMeasure().forMetric(SolidityMetrics.CONTRACT_COUNT).on(context.project()).withValue(contractCount).save();
        context.newMeasure().forMetric(SolidityMetrics.FUNCTION_COUNT).on(context.project()).withValue(functionCount).save();
        context.newMeasure().forMetric(SolidityMetrics.AVG_COMPLEXITY).on(context.project())
            .withValue(functionCount > 0 ? totalComplexity / functionCount : 0).save();
        context.newMeasure().forMetric(SolidityMetrics.GAS_ESTIMATION).on(context.project()).withValue(gasEstimation).save();

        try {
            analyzeProjectWithSlither(context);
        } catch (Exception e) {
            context.newIssue()
                .forRule("Solidity:SlitherError")
                .at(context.newIssueLocation().on(context.fileSystem().inputFiles().iterator().next())
                    .message("Failed to run Slither: " + e.getMessage()))
                .save();
        }
    }

    private int calculateComplexity(SolidityParser.FunctionDefinitionContext func) {
        int complexity = 1;
        if (func.block() != null) {
            complexity += func.block().statement().stream()
                .filter(s -> s.ifStatement() != null || s.whileStatement() != null)
                .count();
        }
        return complexity;
    }

    private int estimateGas(SolidityParser.FunctionDefinitionContext func) {
        int gas = 21000;
        if (func.block() != null) {
            gas += func.block().statement().size() * 100;
        }
        return gas;
    }

    private void analyzeProjectWithSlither(SensorContext context) throws Exception {
        String baseDir = context.fileSystem().baseDir().getAbsolutePath();
        ProcessBuilder pb = new ProcessBuilder("slither", baseDir, "--json", "-");
        pb.redirectErrorStream(true);
        Process process = pb.start();

        StringBuilder jsonOutput = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line);
            }
        }
        process.waitFor();

        JsonNode root = mapper.readTree(jsonOutput.toString());
        JsonNode results = root.path("results").path("detectors");
        for (JsonNode issue : results) {
            String check = issue.path("check").asText();
            String description = slitherRuleDescriptions.getOrDefault(check, issue.path("description").asText());
            String filePath = issue.path("first_markdown_element").path("filename").asText();
            int line = issue.path("first_markdown_element").path("line").asInt();

            InputFile targetFile = context.fileSystem().inputFile(context.fileSystem().predicates()
                .hasAbsolutePath(Paths.get(baseDir, filePath).toString()));
            if (targetFile != null) {
                context.newIssue()
                    .forRule("Slither:" + check)
                    .at(context.newIssueLocation()
                        .on(targetFile)
                        .at(targetFile.selectLine(line))
                        .message(description))
                    .save();
            }
        }
    }
}
