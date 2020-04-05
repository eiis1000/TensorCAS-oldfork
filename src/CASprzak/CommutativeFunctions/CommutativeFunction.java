package CASprzak.CommutativeFunctions;
import CASprzak.ArrLib;
import CASprzak.Function;
import CASprzak.SpecialFunctions.Constant;

import java.util.Arrays;

public abstract class CommutativeFunction extends Function {
    protected double identityValue;

    protected final Function[] functions;

    public CommutativeFunction(Function... functions) {
        this.functions = functions;
        Arrays.sort(this.functions);
    }

    public Function simplify() {
        return this.simplifyInternal().simplifyOneElement();
    }

    public CommutativeFunction simplifyInternal() {
        return this.simplifyElements().simplifyPull().simplifyIdentity().simplifyConstants();
    }

    protected abstract CommutativeFunction simplifyElements();

    protected abstract CommutativeFunction simplifyIdentity();

    protected abstract CommutativeFunction simplifyConstants();

    protected abstract CommutativeFunction simplifyPull();

    protected Function simplifyOneElement() {
        if (functions.length == 0)
            return new Constant(identityValue);
        if (functions.length == 1)
            return functions[0].simplify();
        return this;
    }

    public Function[] getFunctions() {
        return ArrLib.deepClone(functions);
    }


    public boolean equals(Function that) {
        if (this.getClass().equals(that.getClass()))
            return ArrLib.deepEquals(this.getFunctions(), ((CommutativeFunction)that).getFunctions());
        return false;
    }

    public int compareSelf(Function that) {
        return 0; //TODO
    }
}
