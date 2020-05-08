import functions.GeneralFunction;
import org.junit.jupiter.api.Test;
import parsing.KeywordInterface;
import parsing.Parser;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DerivativeTest {

    @Test
    void constantsGive0() {
        GeneralFunction test = Parser.parse("2");
        assertEquals(0, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 3467.0)));
    }

    @Test
    void variablesGive1() {
        GeneralFunction test = Parser.parse("x");
        assertEquals(1, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 3467.0)));
    }

    @Test
    void simpleSumAndProductDerivatives() {
        GeneralFunction test;
        test = Parser.parse("x+3");
        assertEquals(1, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 9.0)));
        test = Parser.parse("x*7");
        assertEquals(7, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 9.0)));
        test = Parser.parse("2*x+7");
        assertEquals(2, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 9.0)));
        test = Parser.parse("2*(x+7)");
        assertEquals(2, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 9.0)));
    }

    @Test
    void longDerivative() {
        GeneralFunction test = Parser.parse("(\\sin(x^0.5+1) * e^(x^0.5)) * x^-0.5").getSimplifiedDerivative('x').getSimplifiedDerivative('x');
        assertEquals(-0.13874, test.evaluate(Map.of('x', 4.0)), 0.0001);
//        System.out.println("Second derivative simplified once:");
//        System.out.println(test);
//        System.out.println("Second derivative simplified twice:");
//        System.out.println(test.simplify());
    }

    @Test
    void arcTrigTests() {
        GeneralFunction test;
        test = Parser.parse("4\\acos(x)-10\\atan(x)");
        assertEquals(-12.773, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 0.456)), 0.01);
        test = Parser.parse("\\asin(x)+x");
        assertEquals(2.638, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 0.792)), 0.01);
    }

    @Test
    void getNthDerivative() {
        GeneralFunction test = Parser.parse("x^2");
        assertEquals(test.getNthDerivative('x', 0), test);
    }

    @Test
    void logbTests() {
        GeneralFunction test;
        test = Parser.parse("\\logb_{10}(x)");
        assertEquals(0.659, test.getSimplifiedDerivative('x').evaluate(Map.of('x', 0.659)), 0.01);
    }

    @Test
    void hardTrig() {
        GeneralFunction test1 = (GeneralFunction) KeywordInterface.useKeywords("pdn x 3 \\sec(x)");
        GeneralFunction test2 = Parser.parse("\\sec(x)*\\tan(x)*(5*(\\sec(x))^2+(\\tan(x))^2)");
        assertEquals(test2.evaluate(Map.of('x', 2.0)), test1.evaluate(Map.of('x', 2.0)), 0.001);
    }

    @Test
    void get4thSinDerivative() {
        GeneralFunction test = Parser.parse("\\sin(x)");
        assertEquals(test, test.getNthDerivative('x', 4));
    }
}
