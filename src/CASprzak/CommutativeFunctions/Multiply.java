package CASprzak.CommutativeFunctions;

import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;
public class Multiply extends CommutativeFunction{
	double identity  = 1;

	public Multiply(Function[] functions) {
		super(functions);
	}

	public Multiply(Function function1, Function function2) {
		super(function1, function2);
	}

	public double evaluate(double[] variableValues) {
		double accumulator = 1;
		for (Function f : functions) accumulator *= f.evaluate(variableValues);
		return accumulator;
	}

	public String toString() {
		StringBuilder temp = new StringBuilder("(");
		for (int i = 0; i < functions.length - 1; i++) {
			temp.append(functions[i].toString());
			temp.append(" * ");
		}
		temp.append(functions[functions.length-1].toString());
		temp.append(")");
		return temp.toString();
	}

	@Override
	public Function getDerivative(int varID) {
		Function[] toAdd = new Function[functions.length];

		for (int i = 0; i < toAdd.length; i++) {
			Function[] toMultiply = new Function[functions.length];
			for (int j = 0; j < functions.length; j++) {
				if (j == i) toMultiply[j] = functions[j].getDerivative(varID);
				else toMultiply[j] = functions[j];
			}
			toAdd[i] = new Multiply(toMultiply);
		}

		return new Add(toAdd);
	}

	public Function clone() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].clone();
		return new Multiply(toMultiply);
	}


	public Function simplify() {
		Multiply init = (Multiply) simplifyInternal();
		if (init.isTimesZero())
			return new Constant((0));
		else
			return init.simplifyOneElement();
	}

	@Override
	protected Multiply simplifyElements() {
		Function[] toMultiply = new Function[functions.length];
		for (int i = 0; i < functions.length; i++) toMultiply[i] = functions[i].simplify();
		return new Multiply(toMultiply);
	}

	@Override
	protected Multiply simplifyIdentity() {
		for (int i = 0; i < functions.length; i++) {
			if (functions[i] instanceof Constant) {
				if (((Constant) functions[i]).getConstant() == 1) {
					return (new Multiply(ArrLib.removeFunctionAt(functions, i))).simplifyIdentity();
				}
			}
		}
		return this;
	}

	@Override
	protected Multiply simplifyConstants() {
		for (int i = 1; i < functions.length; i++){
			for (int j = 0; j < i; j++){
				if (functions[i] instanceof Constant && functions[j] instanceof Constant) {
					Constant c = new Constant(((Constant) functions[i]).getConstant() * ((Constant) functions[j]).getConstant());
					Function[] toMultiply = ArrLib.removeFunctionAt(functions, j);
					toMultiply[i] = c;
					return (new Multiply(toMultiply)).simplifyConstants();
				}
			}
		}
		return this;
	}

	protected boolean isTimesZero() {
		for (Function function : functions) {
			if (function instanceof Constant) {
				if (((Constant) function).getConstant() == 0) {
					return true;
				}
			}
		}
		return false;
	}

	public int compareTo(Function f) {
		return 0;
	}
}
