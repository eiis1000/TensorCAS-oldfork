import parsing.Parser;
import tools.singlevariable.Extrema;
import tools.singlevariable.Solver;
import functions.Function;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SolverTest {

    @Test
    void simplePolynomial() {
        Function test = Parser.parse("x^2+-1");
        assertEquals(1, Solver.getSolutionPoint(test, 3));
    }

    @Test
    void moreAdvancedPolynomial() {
        Function test = Parser.parse("x^4-5x^2+4");
        assertEquals(Arrays.toString(new double[]{-2, -1, 1, 2}), Arrays.toString(Solver.getSolutionsRange(test, -10, 10)));
    }

    @Test
    void polynomialWithNoSolution() {
        Function test = Parser.parse("x^2+1");
        assertEquals(Double.NaN, Solver.getSolutionPoint(test, 4));
    }

    @Test
    void polynomialWithNoSolutionRange() {
        Function test = Parser.parse("x^2+1");
        assertEquals(Arrays.toString(new double[]{}), Solver.getSolutionsRange(test, -10, 10));
    }

    @Test
    void simpleMinima() {
        Function test = Parser.parse("x^2-1");
        assertEquals(0, Extrema.findLocalMinima(test, -3,3));
    }

    @Test
    void lessSimpleMinima() {
        Function test = Parser.parse("x^2-6x+8");
        assertEquals(3, Extrema.findLocalMinima(test, -5,5));
    }
}
