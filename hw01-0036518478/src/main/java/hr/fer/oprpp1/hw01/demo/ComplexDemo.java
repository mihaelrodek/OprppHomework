package hr.fer.oprpp1.hw01.demo;

import hr.fer.oprpp1.hw01.ComplexNumber;

/**
 * Demo class that demonstrates usage of {@link ComplexNumber}. Commented lines
 * represent expected return. Demo cases are taken from task documentation.
 * 
 * @author Mihael Rodek
 *
 */

public class ComplexDemo {
	public static void main(String[] args) {

		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);// 1.6181754193833346 - 0.06878563085611523iF
	}
}
