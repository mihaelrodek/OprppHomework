package hr.fer.zemris.math;

import java.util.Objects;

/**
 * Class that models polyom on Complex numbers in given format : f(z) -> z0*(z-z1)*(z-z2)*...*(z-zn)
 *
 * @author Mihael Rodek
 */
public class ComplexRootedPolynomial {

    private final Complex constant;
    private Complex[] roots;

    /**
     * Default constructor
     *
     * @param constant
     * @param roots
     */
    public ComplexRootedPolynomial(Complex constant, Complex... roots) {
        if (roots.length < 1) throw new IllegalArgumentException("Root degree mustn't be less than 1!");
        this.constant = Objects.requireNonNull(constant);
        this.roots = Objects.requireNonNull(roots);
    }


    /**
     * Method that computes polynomial value at given point z
     *
     * @param z point
     * @return new Complex number
     */
    public Complex apply(Complex z) {
        Complex result = constant;
        for (Complex complex : roots)
            result = result.multiply(z.sub(complex));
        return result;
    }

    /**
     * Method that   converts this representation to ComplexPolynomial type
     *
     * @return new {@link ComplexPolynomial}
     */
    public ComplexPolynomial toComplexPolynom() {

        ComplexPolynomial polynomial = new ComplexPolynomial(constant);

        for (Complex root : roots)
            polynomial = polynomial.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));

        return polynomial;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("(" + constant.toString() + ")");
        for (Complex complex : roots)
            sb.append("*(z-(").append(complex.toString()).append("))");

        return sb.toString();
    }


    /**
     * Method that finds index of closest root for given complex number z that is within threshold.
     * If there is no such root, returns -1
     *
     * @param z         Complex number
     * @param threshold threshold within root can be
     * @return index of closest threshold
     */

    public int indexOfClosestRootFor(Complex z, double threshold) {
        if (roots.length == 0) return -1;

        int closestRootIndex = -1;
        double closestRootDistance = Double.POSITIVE_INFINITY;
        for (int i = 0; i < roots.length; i++) {
            double distance = z.sub(roots[i]).module();
            if (distance < closestRootDistance && distance < threshold) {
                closestRootIndex = i;
                closestRootDistance = distance;
            }
        }

        return closestRootIndex;
    }

}
