package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Class that models polynomial complex number in given format : f(z) -> zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0
 *
 * @author Mihael Rodek
 */
public class ComplexPolynomial {

    /**
     *
     */
    private Complex[] factors;

    /**
     * Constructor for {@link ComplexPolynomial}
     *
     * @param factors
     */
    public ComplexPolynomial(Complex... factors) {
        this.factors = factors;
    }

    /**
     * Method that returns the order for this polynom
     *
     * @return order for this polynom
     */
    public short order() {
        return (short) (factors.length - 1);
    }


    /**
     * Method that creates new polynomial performing this*p
     *
     * @param p number to be performed with
     * @return new {@link ComplexPolynomial}
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] factors = new Complex[this.order() + p.order() + 1];

        Arrays.fill(factors, Complex.ZERO);

        for (int i = 0, stop = this.order() + 1; i < stop; i++) {
            for (int j = 0, stop2 = p.order() + 1; j < stop2; j++) {
                factors[i + j] = factors[i + j].add(this.factors[i].multiply(p.factors[j]));
            }
        }

        return new ComplexPolynomial(factors);
    }

    /**
     * Method that computes first derivative of this polynomial
     *
     * @return first derivative of this polynomial
     */
    public ComplexPolynomial derive() {
        Complex[] fact = new Complex[factors.length - 1];
        for (int i = 1; i < factors.length; i++) {
            fact[i - 1] = factors[i].multiply(new Complex(i, 0));
        }
        return new ComplexPolynomial(fact);
    }


    /**
     * Method that computes polynomial value at given point z
     *
     * @param z point
     * @return new Complex number
     */
    public Complex apply(Complex z) {
        Complex complexResult = Complex.ZERO;

        for (int i = 0; i < factors.length; i++) {
            complexResult = complexResult.add(factors[i].multiply(z.power(i)));
        }

        return complexResult;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(factors[factors.length - 1].toString()).append(")*z^").append(factors.length - 1);

        for (int i = factors.length - 2; i >= 0; i--) {
            sb.append("+(" + factors[i] + ")");
            if (i != 0) sb.append("*z^").append(i);
        }

        return sb.toString();
    }


}
