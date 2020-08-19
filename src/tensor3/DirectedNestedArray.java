package tensor3;

import java.util.List;
import java.util.stream.Collectors;

public class DirectedNestedArray<I extends DirectedNestedArrayInterface<I, T>, T> extends NestedArray<I, T> implements DirectedNestedArrayInterface<I, T> {

	private final boolean isUpper;
	private final boolean[] directions;

	@SuppressWarnings({"unchecked"})
	public static <I extends DirectedNestedArrayInterface<I, T>, T> DirectedNestedArray<I, T> direct(NestedArrayInterface<?, T> nestedArray, boolean... directions) {
		if (nestedArray.getElements().get(0) instanceof NestedEndpoint)
			return new DirectedNestedArray<>(
					directions[directions.length - nestedArray.getRank()],
					nestedArray.getElements().stream()
							.map(e -> (NestedEndpoint<I, T>) e)
							.map(e -> (I) new DirectedEndpoint<I, T>(e.contained))
							.collect(Collectors.toList())
			);
		else
			return new DirectedNestedArray<>(
					directions[directions.length - nestedArray.getRank()],
					nestedArray.getElements().stream()
							.map(e -> (I) direct(e, directions))
							.collect(Collectors.toList())
			);
	}

	public static <I extends DirectedNestedArrayInterface<I, T>, T> DirectedNestedArray<I, T> direct(Object[] elements, boolean... directions) {
		return direct(nest(elements), directions);
	}

	protected DirectedNestedArray(boolean isUpper, List<I> elements) {
		super(elements);
		this.isUpper = isUpper;
		directions = calculateDirections();
	}

	@Override
	public boolean matches(DirectedNestedArrayInterface<I, T> other) {
		return isUpper == other.getDirection() && this.matches(other);
	}

	public boolean getDirection() {
		return isUpper;
	}

	private boolean[] calculateDirections() {
		boolean[] lowerDimensions = elements.get(0).getDirections();
		boolean[] dimensions = new boolean[lowerDimensions.length + 1];
		dimensions[0] = isUpper;
		System.arraycopy(lowerDimensions, 0, dimensions, 1, lowerDimensions.length);
		return dimensions;
	}

	@Override
	public boolean[] getDirections() {
		return directions;
	}


	@Override
	public String toString() {
		return (isUpper ? "v" : "c") + super.toString();
	}

}
