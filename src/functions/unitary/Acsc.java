package functions.unitary;

import functions.Function;
import functions.binary.Pow;
import functions.commutative.Add;
import functions.commutative.Multiply;
import functions.special.Constant;

public class Acsc extends UnitaryFunction {
    public Acsc(Function function) {
        super(function);
    }

    @Override
    public UnitaryFunction me(Function operand) {
        return new Acsc(operand);
    }

    @Override
    public Function getDerivative(int varID) {
        return new Multiply(new Constant(-1), function.getSimplifiedDerivative(varID), new Pow(new Constant(-1), new Multiply(new Abs(function), new Pow(new Constant(0.5), new Add(new Pow(new Constant(2), function), new Constant(-1))))));
    }

    @SuppressWarnings("RedundantSuppression")
    @Override
    public double evaluate(double... variableValues) {
        double functionEvaluated = function.evaluate(variableValues);
        if (functionEvaluated > 1) {
            //noinspection DuplicateExpressions
            return Math.acos(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
        } else if (functionEvaluated < -1) {
            //noinspection DuplicateExpressions
            return -Math.acos(Math.sqrt(Math.pow(functionEvaluated, 2) - 1) / functionEvaluated);
        } else {
            return Double.NaN;
        }
    }
}