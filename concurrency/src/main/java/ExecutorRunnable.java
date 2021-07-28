import java.util.concurrent.*;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    28.07.2021 11:40
 */
public class ExecutorRunnable {

	/**
	 * In this example we are going to use a {@link Runnable} task and use it with a {@link ThreadPoolExecutor} with a
	 * single thread. The {@link Runnable} requires the implementation of the only available method,
	 * {@link Runnable#run()}. We are going to use a lambda for that.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) throws ExecutionException, InterruptedException {

		// Executors is a Factory class, here we instantiate 1 workers
		ExecutorService es = Executors.newSingleThreadExecutor();

		// Something "runnable" is some code that performs operations without return anything.
		Runnable job = () -> {
			// It is important to manage the exceptions INSIDE the lambda
			try {
				System.out.println("Start working!");
				for (int i = 0; i < 3; i++) {
					System.out.println("Working...");
					Thread.sleep(1000);
				}
				System.out.println("Work completed!");
			} catch (InterruptedException e) {
				// This exception is needed since we are using the Thread.sleep method
				e.printStackTrace();
			}
		};

		// Submit the job to the executor, note that the main program continues just after this command!
		es.submit(job);

		// We could collect the returned object of the submit method in a variable and check the status, but since we
		// are using a Runnable, the return will be "null" when finished.

		System.out.println("We submitted something to the executor.");

		// VERY IMPORTANT! Shutdown ALWAYS your executor when the work is finished, otherwise the program will never end!
		es.shutdown();
	}

}
