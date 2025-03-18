package org.sonarsource.solidity.checks;

import org.junit.Test;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import java.io.File;

import static org.junit.Assert.assertEquals;

public class ReentrancyRuleTest {

    @Test
    public void testReentrancyDetection() throws Exception {
        SensorContextTester context = SensorContextTester.create(new File("."));
        File file = new TestInputFileBuilder(".", "test.sol")
            .setContents("pragma solidity ^0.8.0;\ncontract Test { function withdraw() public { msg.sender.call{value: 1 ether}(''); } }")
            .build();
        context.fileSystem().add(file);

        SolidityParser parser = SolidityParser.create(file.inputStream(), file.charset());
        new ReentrancyRule().scanFile(context, file, parser.sourceUnit());

        assertEquals(1, context.allIssues().size());
        assertEquals("ReentrancyRisk", context.allIssues().iterator().next().ruleKey().rule());
    }

    @Test
    public void testNoReentrancy() throws Exception {
        SensorContextTester context = SensorContextTester.create(new File("."));
        File file = new TestInputFileBuilder(".", "test.sol")
            .setContents("pragma solidity ^0.8.0;\ncontract Test { function safe() public { uint x = 1; } }")
            .build();
        context.fileSystem().add(file);

        SolidityParser parser = SolidityParser.create(file.inputStream(), file.charset());
        new ReentrancyRule().scanFile(context, file, parser.sourceUnit());

        assertEquals(0, context.allIssues().size());
    }
}
