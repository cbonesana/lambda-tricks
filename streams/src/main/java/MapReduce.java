import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    28.07.2021 15:47
 */
public class MapReduce {

	/**
	 * MapReduce is a programming model where some input data are transformed (mapped) and then aggregated (reduced)
	 * before the output is returned. In Java, the {@link java.util.stream.Stream}s offer the possibility to build this
	 * exactly behavior using the <code>.map()</code> and <code>.reduce()</code> methods.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// Reduce is a powerful operation that allows to combine all the elements of a collection together
		// In this example we calculate the product of 10 numbers
		final int product = IntStream.range(0, 10)
				.reduce(1, (total, e) -> total * e);

		System.out.println("Product(0..10): " + product);

		// This method allows to have a lambda function as aggregation operation that closes the stream. In the example
		// above, an IntStream does not offer the .product() method, so we implemented it.

		// The use of lambda function for reductions allows us to aggregate all kind of data
		//
		String sentence = "Wholly is a fluffy sheep with a dark nose";
		Set<Character> vowels = Set.of('A', 'E', 'I', 'O', 'U');

		final Optional<String> reduced = Arrays.stream(sentence.split(" "))
				// We capitalize the first letter of each word
				.map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
				// We remove all the words that starts with a vowel
				.filter(word -> !vowels.contains(word.charAt(0)))
				// We concat all the words together
				.reduce((word1, word2) -> word1 + word2);

		// In this case, when we reduce objects, we obtain an Optional<String>. Optionals are containers that can also
		// be empty, null, or with the content we want. We can do many interesting things with them.

		// Check for the value is you just want it
		if (reduced.isPresent())
			System.out.println("Should be present " + reduced.get());

		// We can get a default value is the optional is empty
		System.out.println("Should not be empty: " + reduced.orElse("Empty"));

		// We can throw exceptions if the value is not present
		try {
			System.out.println("No exceptions raised: " + reduced.orElseThrow(Exception::new));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
