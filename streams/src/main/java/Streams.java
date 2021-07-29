import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    29.07.2021 09:37
 */
public class Streams {

	/**
	 * The idea of a Java's {@link java.util.stream.Stream} is to compose a series of transformations in a workflow.
	 * They cane be an option to nested loops, but they are not a replacement of loops.
	 * <p>
	 * In this example, we are going to manipulate some {@link String}s, first with the usual loops, then with the
	 * {@link java.util.stream.Stream}s.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// Let's start with a sentence
		String sentence = "The cat Fluffy, the White Cat, is sleeping on the lap.";

		// Let's start with a simple thing, we transform the sentence in a list of Strings by splitting on spaces
		List<String> words = List.of(sentence.split(" "));

		System.out.println("WORDS: " + words);

		// We want to manipulate these Strings. As an example, we want them lowercase
		List<String> lowercase = new ArrayList<>();

		for (String word : words)
			lowercase.add(word.toLowerCase());

		System.out.println("LOWER: " + lowercase);

		// Now we want to remove commas and periods
		List<String> clean = new ArrayList<>();

		for (String word : lowercase)
			clean.add(word.replaceAll("[.,]", ""));

		System.out.println("CLEAN: " + clean);

		// Here we have a list of stopwords that we want to remove from the list of words
		Set<String> stopwords = Set.of("the", "is", "on");
		List<String> removed = new ArrayList<>();

		for (String word : clean)
			if (!stopwords.contains(word))
				removed.add(word);

		System.out.println("REMOS: " + removed);

		// Finally, we count the occurrences of these words
		Map<String, Integer> counts = new HashMap<>();

		for (String word : removed) {
			if (counts.containsKey(word))
				counts.put(word, counts.get(word) + 1);
			else
				counts.put(word, 1);
		}

		System.out.println("COUNTS: " + counts);

		// What is the same procedure using Java streams?

		final Map<String, Long> streamCounts = words
				// Take all the words as a stream of data
				.stream()
				// Make the word lowercase (we use a method reference)
				.map(String::toLowerCase)
				// Clean periods and commas (we use a lambda function)
				.map(word -> word.replaceAll("[.,]", ""))
				// Remove stopwords (we use the filter and another lambda)
				.filter(word -> !stopwords.contains(word))
				// This is just for debug purposes: we print the whole content of the collections and continue
				.peek(System.out::println)
				// Count the occurrences (using a special collector)
				.collect(
						// This collector will build a Map by grouping the keys and performing something with the values
						Collectors.groupingBy(
								word -> word,           // This lambda set how to collect the keys (it is an identity)
								Collectors.counting()   // This collector set how to collect the values (count them)
						)
				);

		System.out.println("STREAM COUNTS: " + streamCounts);

		// In the case of a sequence of operations to perform on a collection of data, the Streams are very useful and
		// keep the code short to write and easy to understand. It's not something NEW, it just the same with a
		// different syntax and some pre-defined code

		// Another important detail of the streams is that they can be consumed only once.
		try {
			// Streams are normal objects that can be assigned to variables
			Stream<String> streamToLower = words.stream().map(String::toLowerCase);

			// Here we consume the stream:
			long count = streamToLower.count();
			System.out.println("COUNT: " + count);

			// This will raise an IllegalStateException since we already consumed the stream
			long filteredCount = streamToLower.filter(word -> !stopwords.contains(word)).count();
			System.out.println("FILTERED COUNT: " + filteredCount);

		} catch (IllegalStateException e) {
			System.out.println("Note how \"FILTERED COUNT\" was not printed");
			System.out.println("Exception is \"" + e.getMessage() + "\"");
		}
	}

}
