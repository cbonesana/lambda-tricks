import java.util.concurrent.Callable;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda-tricks
 * Date:    28.07.2021 14:53
 * <p>
 * This is just a support class for the {@link ExecutorManyJobs} example.
 */
public class Job implements Callable<Double> {

	private final double a;
	private final double b;

	public Job(double[] array) {
		this.a = array[0];
		this.b = array[1];
	}

	@Override
	public Double call() {
		// The body of our job
		System.out.printf("Arguments: a=%.2f b=%.2f%n", a, b);

		return Math.sin(a) / Math.sqrt(b);
	}
}
