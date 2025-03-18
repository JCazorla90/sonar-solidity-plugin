package org.sonarsource.solidity;

import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import java.util.Arrays;
import java.util.List;

public class SolidityMetrics implements Metrics {

    public static final Metric<Integer> CONTRACT_COUNT = new Metric.Builder("solidity_contracts", "Number of Contracts", Metric.ValueType.INT)
        .setDescription("Total number of Solidity contracts")
        .setDirection(Metric.DIRECTION_WORSE)
        .setQualitative(false)
        .create();

    public static final Metric<Integer> FUNCTION_COUNT = new Metric.Builder("solidity_functions", "Number of Functions", Metric.ValueType.INT)
        .setDescription("Total number of functions in Solidity files")
        .setDirection(Metric.DIRECTION_WORSE)
        .setQualitative(false)
        .create();

    public static final Metric<Float> AVG_COMPLEXITY = new Metric.Builder("solidity_avg_complexity", "Average Complexity", Metric.ValueType.FLOAT)
        .setDescription("Average cyclomatic complexity of functions")
        .setDirection(Metric.DIRECTION_WORSE)
        .setQualitative(true)
        .create();

    public static final Metric<Integer> GAS_ESTIMATION = new Metric.Builder("solidity_gas_estimation", "Estimated Gas Usage", Metric.ValueType.INT)
        .setDescription("Rough estimation of gas usage based on operations")
        .setDirection(Metric.DIRECTION_WORSE)
        .setQualitative(true)
        .create();

    @Override
    public List<Metric> getMetrics() {
        return Arrays.asList(CONTRACT_COUNT, FUNCTION_COUNT, AVG_COMPLEXITY, GAS_ESTIMATION);
    }
}
