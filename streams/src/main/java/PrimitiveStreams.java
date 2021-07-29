import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    28.07.2021 15:46
 */
public class PrimitiveStreams {

	/**
	 * Java's {@link java.util.stream.Stream}s are intended to work with collections of data. When we have to work with
	 * primitives and arrays we can still use them, but with some dedicated classes. It is also important to consider
	 * the (un)boxing mechanism that allows to switch from primitives (int) to objects (Integer) and vice-versa.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// We want an array of the square roots of the odd number lower than 100 (excluded)
		final double[] oddSquared = IntStream // we are using a stream of int
				// From 0 (included) to 10 (excluded)
				.range(0, 10)
				// We filter to have ints only odd
				.filter(i -> i % 2 == 1)
				// Math.sqrt returns a double, so we have to use .mapToDouble()
				.mapToDouble(Math::sqrt)
				// Instead of collect to a collection, we just get an array of the outputs
				.toArray();

		// This will produce an array of 5 elements: [sqrt(1), sqrt(3), sqrt(5), sqrt(7), sqrt(9)]
		System.out.println("Odd Squared: " + Arrays.toString(oddSquared));

		// IntStream is also used to replicate the behavior of a for(i=0, i<n; i++)
		final Random r = new Random(42);

		// We generate a sequence of 10 random integers between 0 and 1
		final int n = 10;
		final int[] true_y = IntStream.range(0, n)
				.map(i -> r.nextInt(2))
				.toArray();

		// Then a sequence of random integers between 0 and 1 where we have 60% probability to have a 1
		final int[] pred_y = IntStream.range(0, n)
				.map(i -> r.nextDouble() < 0.6 ? 1 : 0)
				.toArray();

		// Now we want to have the mean of the square difference between the two arrays (known as Mean Squared Error)
		final double mse = IntStream.range(0, n)
				.map(i -> true_y[i] - pred_y[i])    // Difference between true and predicted values
				.mapToDouble(x -> x * x)            // Squared of the difference
				.sum() / n;                         // Mean: sum of all differences divided by the number of elements

		System.out.println("MSE: " + mse);

	}

}
