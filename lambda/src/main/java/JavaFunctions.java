import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda
 * Date:    28.07.2021 11:14
 */
public class JavaFunctions {

	/**
	 * In this example, we examine some interfaces of the {@link java.util.function} package. In particular the
	 * {@link java.util.function.Function}, {@link java.util.function.BiFunction}, and
	 * {@link java.util.function.DoubleFunction} interfaces. All of them have the <code>apply(T)</code> method.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// The interface Function need to know the input type T and the output type R
		Function</* T */ Integer, /* R */ String> itoa = i -> "" + ((char) i.intValue());

		System.out.println("char(65) = " + itoa.apply(65) + "\n");

		// The interface BiFunction instead work with two inputs type T and U and an output type R
		// In this case we want to implement a sum of integer, we already have such static method
		BiFunction</*T*/ Integer,/*U*/ Integer,/*R*/ Integer> add = Integer::sum;

		System.out.println("40 + 2 = " + add.apply(40, 2) + "\n");

		// One interesting detail of the functions is that they can be composed!
		final String compose = add.andThen(itoa).apply(65, 32);

		System.out.println("lower(char(65)) = " + compose + "\n");

		// One detail is that these functions always returns an Object. If we want, as an example, a primitive double,
		// we need to use some special functions where the return type is not generic:
		DoubleFunction<Double> invSqrt = d -> 1. / Math.sqrt(d);

		System.out.println("InvertedSQRT(42) = " + invSqrt.apply(42) + "\n");

		/*
		Expected output:

		char(65) = A

		40 + 2 = 42

		lower(char(65)) = a

		InvertedSQRT(42) = 0.1543033499620919

		 */
	}
}
