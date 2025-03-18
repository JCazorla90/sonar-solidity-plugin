package org.sonarsource.solidity;

import org.sonar.api.server.rule.RulesDefinition;

public class SolidityRulesDefinition implements RulesDefinition {

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository("solidity", "solidity").setName("Solidity Analyzer");

        repository.createRule("UncheckedMath")
            .setName("Unchecked Math Operations")
            .setHtmlDescription("<p><strong>⚠️ Warning:</strong> Arithmetic operations could benefit from <code>unchecked</code> for gas savings.</p>")
            .setActivatedByDefault(true);

        repository.createRule("ReentrancyRisk")
            .setName("Reentrancy Risk")
            .setHtmlDescription("<p><strong>🔒 Security:</strong> External calls may lead to reentrancy. Use checks-effects-interactions or <code>nonReentrant</code>.</p>")
            .setActivatedByDefault(true);

        repository.createRule("MissingVisibility")
            .setName("Missing Function Visibility")
            .setHtmlDescription("<p><strong>👀 Visibility:</strong> Functions without explicit visibility (<code>public</code>, <code>private</code>, etc.) detected.</p>")
            .setActivatedByDefault(true);

        repository.createRule("Slither:reentrancy")
            .setName("Slither: Reentrancy")
            .setHtmlDescription("<p><strong>🔍 Slither:</strong> Reentrancy vulnerability detected.</p>")
            .setActivatedByDefault(true);

        repository.createRule("Slither:unused-return")
            .setName("Slither: Unused Return")
            .setHtmlDescription("<p><strong>⚙️ Optimization:</strong> Unused return value detected by Slither.</p>")
            .setActivatedByDefault(true);

        repository.createRule("Slither:shadowing")
            .setName("Slither: Variable Shadowing")
            .setHtmlDescription("<p><strong>🛠️ Code Quality:</strong> Variable shadowing detected by Slither.</p>")
            .setActivatedByDefault(true);

        repository.done();
    }
}
