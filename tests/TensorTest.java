import functions.GeneralFunction;
import functions.commutative.Product;
import functions.endpoint.Constant;
import org.junit.jupiter.api.Test;
import tensors.*;
import tensors.ArrayTensor;
import tensors.elementoperations.ElementProduct;
import tensors.elementoperations.ElementWrapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tensors.TensorTools.*;
import static tools.DefaultFunctions.*;

public class TensorTest {

	static {
		DefaultSpaces.s2.hashCode();
	}

	@Test
	void undirectedTest() {
		NestedArray<?, Integer> test = NestedArray.nest(new Object[][]{
				{1, 2},
				{3, 4}
		});
		System.out.println(test);
		assertEquals(2, test.getAtIndex(0, 1));
		assertEquals(2, test.getRank());
		test.setAtIndex(-1, 0, 0);
		assertEquals(-1, test.getAtIndex(0, 0));
		Nested<?, Integer> test2 = test.modifyWith(i -> -2 * i);
		assertEquals(-4, test2.getAtIndex(0, 1));
	}

	@Test
	void directedTest() {
		DirectedNested<?, Integer> test = DirectedNestedArray.direct(
				new Object[][]{
						{1, 2},
						{3, 4}
				}, new boolean[]{true, false}
		);
		System.out.println(test);
		assertEquals(2, test.getAtIndex(0, 1));
		assertTrue(test.getDirection());
	}

	@Test
	void tensorTest() {
		Tensor test = ArrayTensor.tensor(
				new Object[][]{
						{ONE, TWO},
						{new Constant(3), new Product(TWO, E)}
				},
				true, false);
		System.out.println(test);
		assertEquals(TWO, test.getAtIndex(0, 1));
		assertTrue(test.getDirection());
		DirectedNested<?, GeneralFunction> test2d = DirectedNestedArray.direct(NestedArray.nest(
				new Object[][]{
						{ONE, ONE},
						{ONE, TWO}
				}), new boolean[]{true, false});
		Tensor test2 = ArrayTensor.tensor(test2d);
//		Tensor sum = ArrayTensor.sum(test, test2);
//		assertEquals(sum.getAtIndex(1, 0), new Constant(4));
//		System.out.println(sum);
	}

	@Test
	void elementTest() {
		Tensor id2u = ArrayTensor.tensor(
				new Object[][]{
						{ZERO, ONE},
						{ONE, ZERO}
				},
				false, false
		);
		Tensor id2d = ArrayTensor.tensor(
				new Object[][]{
						{ZERO, ONE},
						{ONE, ZERO}
				},
				true, true
		);
		System.out.println(createFrom(List.of("a", "b"), new boolean[]{true, false}, 2,
				new ElementProduct(new ElementWrapper(id2u, "a", "m"), new ElementWrapper(id2d, "m", "b"))
				));
	}

	@Test
	void scalarTest1() {
		Tensor C = ArrayTensor.tensor(
				new Object[]{
						ONE,
						TWO
				},
				true
		);
		Tensor R = ArrayTensor.tensor(
				new Object[]{
						TEN, ONE
				},
				false
		);
		System.out.println(createFrom(List.of(), new boolean[]{}, 2,
				new ElementProduct(new ElementWrapper(R, "\\mu"), new ElementWrapper(C, "\\mu"))
		));
	}

	@Test
	void scalarTest2() {
		Tensor C = ArrayTensor.tensor(
				new Object[]{
						TEN, ONE
				},
				true
		);
		Tensor metric = ArrayTensor.tensor(
				new Object[][]{
						{NEGATIVE_ONE, ZERO},
						{ZERO, ONE}
				},
				false, false
		);
		System.out.println(createFrom(List.of("\\nu"), new boolean[]{false}, 2,
				new ElementProduct(new ElementWrapper(C, "\\mu"), new ElementWrapper(metric, "\\mu", "\\nu"))
		));
	}

	@Test
	void christoffel() {
		Space space = DefaultSpaces.s2;
		System.out.println(space.christoffel);
	}



}
