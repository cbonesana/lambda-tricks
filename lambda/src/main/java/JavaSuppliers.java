import java.util.function.Supplier;

/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda
 * Date:    28.07.2021 10:56
 */
public class JavaSuppliers {

	/**
	 *  In the package {@link java.util.function}, Java offers some useful generic interfaces ready to be used for the
	 *  declaration of lambda functions. In this example we will use the  {@link java.util.function.Supplier} interface.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// Supplier is an interface for the production of something. In this case we produce a string
		Supplier<String> strNow = () -> "Current time in millis: " + System.currentTimeMillis();

		System.out.println(strNow.get() + "\n");

		// One interesting characteristic of the lambda is that we can assign methods to them.
		// In this case, we assign the static method System.currentTimeMillis().
		Supplier<Long> now = System::currentTimeMillis;

		System.out.println("Now is: " + now.get() + "\n");

		// We can also assign methods of objects, if we have them instantiated.
		// In this case the lambda "strNow" is an object of type "Supplier<String>" and we assign its "get()"
		// method to another object, still of type "Supplier<String>"
		Supplier<String> strNowToString = strNow::get;

		System.out.println("StrNowToString: " + strNowToString.get() + "\n");

		// Note that strNow and strNowToString are two different objects: the first is just "inside" the second
		System.out.printf("OBJ1: %s%nOBJ2: %s%n%n", strNow, strNowToString);


		/*
		Expected output:
		Current time in millis: <some numbers>

		Now is: <some numbers>

		StrNowToString: Current time in millis: <some numbers>

		OBJ1: JavasLambdas$$Lambda$14/<some numbers>@<some hex>
		OBJ2: JavasLambdas$$Lambda$16/<some numbers>@<some hex>

		 */
	}
}
