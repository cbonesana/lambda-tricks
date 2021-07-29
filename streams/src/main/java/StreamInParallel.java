import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    29.07.2021 14:29
 */
public class StreamInParallel {

	/**
	 * The {@link java.util.stream.Stream} interface also offers the possibility to run in parallel some computations.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// Let's start with a sentence...
		String sentence = "A little fox was searching for a little bit of grapes.";

		// ...and split it in a list of words
		List<String> words = List.of(sentence.split(" "));

		// We are going to use a parallel stream. This variant of the streams will try to run the computation in
		// parallel, although this is not guaranteed
		final ConcurrentMap<String, Integer> counts = words.parallelStream()
				// We map everything to lowercase
				.map(String::toLowerCase)
				// Then we collect to a map
				.collect(
						// Since we are working in a concurrent environment, it is savvy to work with a ConcurrentMap
						Collectors.toConcurrentMap(
								// The key of the map will be a word
								word -> word,
								// The element will be mapped to the value of 1
								count -> 1,
								// The merge function that will be called in the case a key already exists is a sum
								Integer::sum
						)
				);

		System.out.println("Counts: " + counts);
	}

}
