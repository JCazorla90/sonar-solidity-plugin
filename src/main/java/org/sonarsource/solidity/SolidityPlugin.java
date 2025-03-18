package org.sonarsource.solidity;

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;

public class SolidityPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtensions(
            SolidityLanguage.class,
            SoliditySensor.class,
            SolidityRulesDefinition.class,
            SolidityMetrics.class,
            PropertyDefinition.builder("sonar.solidity.file.suffixes")
                .name("Solidity File Suffixes")
                .description("Comma-separated list of suffixes for Solidity files")
                .defaultValue(".sol")
                .build()
        );
    }
}
