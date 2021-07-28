import java.util.concurrent.*;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    28.07.2021 11:40
 */
public class ExecutorCallable {

	/**
	 * In this example we are going to use a {@link Callable} task and use it with a {@link ThreadPoolExecutor} with a
	 * single thread. The {@link Callable} requires the implementation of the only available method,
	 * {@link Callable#call()}. We are going to use a lambda for that.
	 * <p>
	 * Then we will look for how to get the result of the task.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// Executors is a Factory class, here we instantiate 1 workers
		ExecutorService es = Executors.newSingleThreadExecutor();

		// Something "callable" is some code that has something to return at the end of the job.
		// In this case we are returning a Long object.
		Callable<Long> job = () -> {
			// It is important to manage the exceptions INSIDE the lambda
			try {
				System.out.println("Start producing!");
				for (int i = 0; i < 2; i++) {
					System.out.println("...");
					Thread.sleep(1000);
				}
				System.out.println("Production completed!");
			} catch (InterruptedException e) {
				// This exception is needed since we are using the Thread.sleep method
				e.printStackTrace();
			}
			return System.currentTimeMillis(); // A callable need to return always something
		};

		// Submit the job, and continue with the program
		final Future<Long> future = es.submit(job);

		// The Future object is what the two jobs will return. We can interrogate these objects to know the state of the
		// job, for example if it is done
		System.out.println("The job is done? " + future.isDone());

		// If we want to wait for the job to finish we can use the following two lines of code:

		// 	while (!future.isDone())
		// 		Thread.sleep(1000);

		// But this is a VERY BAD design!
		// With Thread.sleep() we are BLOCKING the current thread (in this case, the MAIN thread of the application!)
		// until the job finish.

		// A better solution, is to just shutdown the executor: the jobs that are running will do until termination.
		// It is also possible to set a timeout on the shutdown to halt everything after a certain amount of time, but
		// then we need to check if all the submitted tasks finished correctly or not.
		es.shutdown();

		// When the job is finished we can get the result. Note that the call .get() on a future is BLOCKING, this means
		// that the code will not continue until the future is completed. This is like the while-sleep code above, but
		// done in the right way
		try {
			// In this case it is a long, the system time in milliseconds of when the job has finished
			final Long time = future.get();
			System.out.println("Produced: " + time);
		} catch (ExecutionException | InterruptedException e) {
			// These two exceptions are needed by the future.get() method
			e.printStackTrace();
		}
	}

}
