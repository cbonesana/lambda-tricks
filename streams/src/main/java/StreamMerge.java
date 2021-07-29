import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    29.07.2021 14:02
 */
public class StreamMerge {

	/**
	 * A {@link java.util.stream.Stream} object is an object that can be manipulated until it is consumed. A consumer
	 * operation can be a sum of a primitive stream or a collection operation. A common operation is the concatenation
	 * of two streams: we perform some operations with a collection, some other operations with another collection, then
	 * we <i>merge</i> these two streams in a single stream and complete the computation.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// Streams are a workflow, and workflow can be merged if the operations are the same
		String sentence1 = "This IS, a caSe of VERy dirty... sentence... JUst, clean, it!";
		String sentence2 = "- THIS - OTHER - SENTENCE - IS - JUST - UPPER - CASE -";

		// This is just a set of words to avoid
		Set<String> stopwords = Set.of("a", "is", "it", "of", "this");

		// we create a list of tokens (words) from each sentence
		List<String> tokens1 = List.of(sentence1.split(" "));
		List<String> tokens2 = List.of(sentence2.split(" "));

		// The first sentence need to remove some punctuations
		Stream<String> stream1 = tokens1.stream()
				// We replace the wrong characters with a regex
				.map(word -> word.replaceAll("[.,!]", ""));

		// The second sentences instead is full of "-" symbols that need to be filtered out
		Stream<String> stream2 = tokens2.stream()
				// This is exactly what we will put in an if
				.filter(word -> !word.equals("-"));

		// Finally, we can concat the two streams and perform the commons operations
		Map<String, Integer> counts = Stream.concat(stream1, stream2)
				// Change everything to lowercase
				.map(String::toLowerCase)
				// Remove the stopwords
				.filter(word -> !stopwords.contains(word))
				// Collect to a map the occurrences of each word
				.collect(
						Collectors.toMap(
								// The key of the map will be a word
								key -> key,
								// Each word will be mapped to the value of 1
								value -> 1,
								// The merge function that will be called in the case a key already exists is a sum
								Integer::sum
						)
				);

		System.out.println("counts: " + counts);

	}

}
