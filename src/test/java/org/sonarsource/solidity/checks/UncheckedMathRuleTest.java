package org.sonarsource.solidity.checks;

import org.junit.Test;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import java.io.File;

import static org.junit.Assert.assertEquals;

public class UncheckedMathRuleTest {

    @Test
    public void testUncheckedMathDetection() throws Exception {
        SensorContextTester context = SensorContextTester.create(new File("."));
        File file = new TestInputFileBuilder(".", "test.sol")
            .setContents("pragma solidity ^0.8.0;\ncontract Test { function add(uint a, uint b) public pure returns (uint) { return a + b; } }")
            .build();
        context.fileSystem().add(file);

        SolidityParser parser = SolidityParser.create(file.inputStream(), file.charset());
        new UncheckedMathRule().scanFile(context, file, parser.sourceUnit());

        assertEquals(1, context.allIssues().size());
        assertEquals("UncheckedMath", context.allIssues().iterator().next().ruleKey().rule());
    }
}
