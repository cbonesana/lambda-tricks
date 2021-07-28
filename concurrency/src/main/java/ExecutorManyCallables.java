import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    28.07.2021 12:23
 */
public class ExecutorManyCallables {

	/**
	 * There are many possible types of {@link ExecutorService}s, the probably most used is the
	 * {@link ThreadPoolExecutor}. This Executor has a fixed number of threads, or workers, and it
	 * is very useful in the case we need to perform some independent work, from read the content of many files to
	 * download data from a web service.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// First, let's get the number of available cores in our CPU:
		final int cores = Runtime.getRuntime().availableProcessors();

		System.out.println("Available cores: " + cores);

		// These are LOGICAL cores, it means that if we are on a 2 core CPU with hyper-threading we will see 4 cores
		// Hyper-threading is a hardware characteristic of the CPU that allows to run 2 threads on each physical core.
		// This is good if we have many small jobs (computations that requires less than few seconds to complete) but it
		// can slow down large jobs (jobs that run for several minutes with on a single core at 100% of its capacity).

		// Always check your hardware and your kind of job before using that number.

		// We create a new executor with cores-1 threads, so we can have some space for the main thread
		ExecutorService es = Executors.newFixedThreadPool(cores - 1);

		// We are now going to build many jobs. Each job will have it's pre-defined input arguments.
		// We are using Java's Streams API to build these jobs.

		// First, let's use a random generator to build a list of arguments. To keep it easy, we are building a list
		// of arrays of double of length 2.

		// To use an object external to a lambda inside a lambda, this object need to be FINAL
		final Random r = new Random(42);

		final List<double[]> arguments = IntStream.range(1, 21) // this generates a list of int from 1 to 20
				// We want to convert the int (primitive) to an Object (an array)
				.mapToObj(
						// We are going to generate 20 pair of doubles
						i -> new double[]{r.nextDouble(), r.nextDouble()}
				)
				// Finally, we want to collect all the arguments
				.collect(
						// This is a particular Collector that will collect all the element in an immutable List
						Collectors.toList()
				);

		// Now that we have the arguments, we are going to build the jobs. Each job will perform the same task but on
		// different arguments.
		final List<Callable<Double>> jobs = arguments.stream()
				// We map an argument to a Callable: we are building a Lambda that returns a lambda!
				.map(
						arg -> /* Cast to Callable: */(Callable<Double>) /* Lambda declaration: */() -> {
							// The body of our job
							double a = arg[0];
							double b = arg[1];

							System.out.printf("Arguments: a=%.2f b=%.2f%n", a, b);

							return Math.sin(a) / Math.sqrt(b);
						}
				)
				.collect(
						// This is a particular Collector that will collect all the element in an immutable List
						Collectors.toList()
				);

		// We now have a list of 20 callables, or 20 jobs. We can submit all of them to the executor using the
		// "invokeAll()" method. This is also a BLOCKING method: at the end of the execution we are guarantee that
		// all the futures have the DONE flag set to TRUE.
		try {
			final List<Future<Double>> futures = es.invokeAll(jobs);

			// We don't need the Executor anymore, we can shut it down
			es.shutdown();

			// And now we print all the results:
			for (int i = 0; i < arguments.size(); i++) {
				// We can do this because the invokeAll() method maintains the same order as the received jobs.
				final Future<Double> future = futures.get(i);
				final double[] arg = arguments.get(i);

				double a = arg[0];
				double b = arg[1];
				Double c = future.get();
				System.out.printf("Results: a=%.2f b=%.2f -> c=%.2f%n", a, b, c);
			}

		} catch (InterruptedException | ExecutionException e) {
			// This is required by the invokeAll() and future.get() methods
			e.printStackTrace();
		}
	}
}
