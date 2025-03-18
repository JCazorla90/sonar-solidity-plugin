package org.sonarsource.solidity.checks;

import org.sonar.check.Rule;
import org.sonarsource.solidity.SolidityBaseVisitor;
import org.sonarsource.solidity.SolidityParser.FunctionCallContext;

@Rule(key = "ReentrancyRisk")
public class ReentrancyRule extends SolidityBaseVisitor {

    @Override
    public void visitFunctionCall(FunctionCallContext ctx) {
        if (ctx.getText().contains(".call") || ctx.getText().contains(".transfer")) {
            reportIssue(ctx, "Potential reentrancy risk: Use checks-effects-interactions pattern or non-reentrant modifier.");
        }
        super.visitFunctionCall(ctx);
    }
}
