package org.sonarsource.solidity.checks;

import org.sonar.check.Rule;
import org.sonarsource.solidity.SolidityBaseVisitor;
import org.sonarsource.solidity.SolidityParser.FunctionDefinitionContext;

@Rule(key = "MissingVisibility")
public class VisibilityRule extends SolidityBaseVisitor {

    @Override
    public void visitFunctionDefinition(FunctionDefinitionContext ctx) {
        if (ctx.modifierList() == null || !containsVisibility(ctx.modifierList().getText())) {
            reportIssue(ctx, "Function visibility not specified. Explicitly declare 'public', 'private', 'internal', or 'external'.");
        }
        super.visitFunctionDefinition(ctx);
    }

    private boolean containsVisibility(String modifiers) {
        return modifiers.contains("public") || modifiers.contains("private") || 
               modifiers.contains("internal") || modifiers.contains("external");
    }
}
