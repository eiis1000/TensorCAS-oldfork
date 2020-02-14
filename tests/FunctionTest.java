import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FunctionTest {
	@Test void fxReturnsX() {
		Function test = Function.makeFunction("x");
		double b = test.evaluate(new String[]{"x"}, new double[]{2});
		assertEquals(2, b);
	}

	@Test void basicFunctionsWithX() {
		Function test = Function.makeFunction("x ^ x + 2 + 5 * x");
		double b = test.evaluate(new String[]{"x"}, new double[]{3});
		assertEquals(44, b);
	}

	@Test void multipleVariablesWorks() {
		Function test = Function.makeFunction("x + y + y");
		double b = test.evaluate(new String[]{"x", "y"}, new double[]{3, 4});
		assertEquals(11, b);
	}


}
