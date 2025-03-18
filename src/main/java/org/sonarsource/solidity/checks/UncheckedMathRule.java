package org.sonarsource.solidity.checks;

import org.sonar.check.Rule;
import org.sonarsource.solidity.SolidityBaseVisitor;
import org.sonarsource.solidity.SolidityParser.BinaryOperationContext;

@Rule(key = "UncheckedMath")
public class UncheckedMathRule extends SolidityBaseVisitor {

    @Override
    public void visitBinaryOperation(BinaryOperationContext ctx) {
        String operator = ctx.getChild(1).getText();
        if ((operator.equals("+") || operator.equals("*")) && !isInUncheckedBlock(ctx)) {
            reportIssue(ctx, "Consider using 'unchecked' for gas optimization or ensure overflow safety.");
        }
        super.visitBinaryOperation(ctx);
    }

    private boolean isInUncheckedBlock(BinaryOperationContext ctx) {
        return ctx.getParent() != null && ctx.getParent().getParent() instanceof SolidityParser.UncheckedBlockContext;
    }
}
