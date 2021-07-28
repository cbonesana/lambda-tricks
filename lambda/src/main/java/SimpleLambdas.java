/**
 * Author:  Claudio "Dna" Bonesana
 * Project: lambda
 * Date:    28.07.2021 10:19
 */
public class SimpleLambdas {

	/**
	 * In this example, we use the interfaces {@link MyFunction} and {@link MyOtherFunction} to test some lambda.
	 * These interfaces have only one method, so we can use them as templates for lambda functions.
	 *
	 * @param args ignored
	 */
	public static void main(String[] args) {

		// The interface MyFunction has just one method "$" that accepts an int as input and return "something" as int.
		MyFunction square = (int x) -> x * x;

		// To execute the function, just call the name of the method
		System.out.println("3^2 = " + square.$(3) + "\n");

		// It is the same as an anonymous class but with a lot less code
		MyFunction anonSquare = new MyFunction() {
			@Override
			public int $(int x) {
				return x * x;
			}
		};

		System.out.println("3^2 = " + anonSquare.$(3) + " (anon)\n");

		// We can also drop parenthesis, type, and change name to the arguments when the lambda is simple...
		MyFunction cube = n -> n * n * n;

		System.out.println("4^3 = " + cube.$(4) + "\n");

		// ...but we need to keep the code in curly brackets if the computation needs more lines
		MyFunction squareThenCube = n -> {
			int s = square.$(n);
			System.out.println("    square of " + n + " is: " + s);
			int c = cube.$(s);
			System.out.println("    cube of " + s + " is: " + c);
			return c; // return statement is mandatory when we have multiple lines
		};

		System.out.println("(7^2)^3 = " + squareThenCube.$(7) + "\n");

		// Lambda functions can have multiple arguments of different types
		MyOtherFunction pow = (s, f, i) -> {
			double p = 1.0;
			for (int j = 0; j < i; j++)
				p = p * f;

			System.out.printf("%5s := %.2f^%3d = %.2f%n", s, f, i, p);
			return p;
		};

		System.out.println("2.0^4 = " + pow.perform("X", 2.0, 4) + "\n");

		/*
		Expected output:
			3^2 = 9

			3^2 = 9 (anon)

			4^3 = 64
			    square of 7 is: 49
			    cube of 49 is: 117649

			(7^2)^3 = 117649
			    X := 2,00^  4 = 16,00

			2.0^4 = 16.0
		 */
	}

}
