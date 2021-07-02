package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class that represents representation of Complex number.
 * @author Mihael Rodek
 */
public class Complex {

    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);
    public static final Complex ONE_NEG = new Complex(-1, 0);
    public static final Complex IM = new Complex(0, 1);
    public static final Complex IM_NEG = new Complex(0, -1);

    /**
     * Real part
     */
    private double real;
    /**
     * Imaginary part
     */
    private double imaginary;
    /**
     * Magnitude
     */
    private double magnitude;
    /**
     * Angle
     */
    private double angle;


    /**
     * Default constructor for Compley
     *
     * @param re real part
     * @param im imaginary part
     */
    public Complex(double re, double im) {
        this.real = re;
        this.imaginary = im;
        this.magnitude = Math.sqrt(Math.pow(re, 2) + Math.pow(im, 2));
        this.angle = Math.atan2(im, re);
    }

    /**
     * Method that returns module of complex number
     *
     * @return module of complex number
     */
    public double module() {
        return magnitude;
    }

    /**
     * Method that multiplies number with number c and returns this*c
     *
     * @param c number to be multiplied with
     * @return new Complex number
     */
    public Complex multiply(Complex c) {
        Objects.requireNonNull(c);
        return new Complex(this.real * c.getReal() - this.imaginary * c.getImaginary(), this.real * c.getImaginary() + imaginary * c.getReal());
    }

    /**
     * Method that divides number with number c and returns this/c
     *
     * @param c number to be divided with
     * @return new Complex number
     */
    public Complex divide(Complex c) {
        if (c.getReal() == 0.0 && c.getImaginary() == 0.0) {
            throw new IllegalArgumentException("Cannot divide with 0");
        }

        double div = c.getReal() / c.getImaginary();
        if (Math.abs(c.getReal()) < Math.abs(c.getImaginary())) {
            return new Complex((real * div + imaginary) / (c.getReal() * div + c.getImaginary()),
                    (imaginary * div - real) / (c.getReal() * div + c.getImaginary()));
        } else {
            div = Math.pow(div, -1);
            return new Complex((imaginary * div + real) / (c.getReal() * div + c.getImaginary()),
                    (imaginary - real * div) / (c.getReal() * div + c.getImaginary()));
        }
    }

    /**
     * Method that adds number with number c and returns this+c
     *
     * @param c number to be added
     * @return new Complex number
     */
    public Complex add(Complex c) {
        Objects.requireNonNull(c, "Complex number cannot be null!");
        return new Complex(real + c.getReal(), imaginary + c.getImaginary());
    }

    /**
     * Method that subtracts number with number c and returns this-c
     *
     * @param c number to be subbed with
     * @return new Complex number
     */
    public Complex sub(Complex c) {
        Objects.requireNonNull(c, "Complex number cannot be null!");
        return new Complex(real - c.getReal(), imaginary - c.getImaginary());
    }

    /**
     * Method that negates given number and returns -this
     *
     * @return new Complex number
     */
    public Complex negate() {
        return new Complex(-real, -imaginary);
    }


    /**
     * Method that powers given number to the n-th exponent and returns this^n
     *
     * @return new Complex number
     * @throws IllegalArgumentException if n is lower than 0
     */
    public Complex power(int n) {
        if (n < 0) throw new IllegalArgumentException("Power can't be lower than 0.");
        return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), getAngle() * n);
    }


    /**
     * Method that gets the n-th root of given number and returns it
     *
     * @return List of roots
     * @throws IllegalArgumentException if n is lower than 0
     */
    public List<Complex> root(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Root most be greater than 0.");

        double rootAngle = getAngle() / n;
        double rootMagnitude = Math.pow(getMagnitude(), 1. / (float) n);
        double angle = 2 * Math.PI / n;

        List<Complex> roots = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            roots.add(fromMagnitudeAndAngle(rootMagnitude, rootAngle));
            rootAngle += angle;
        }
        return roots;
    }

    /**
     * Helper method to transfrom number from magnitude and angle to Complex number
     *
     * @param magnitude magnitude
     * @param angle     angle
     * @return new Complex number
     */

    public static Complex fromMagnitudeAndAngle(double magnitude, double angle) {
        return new Complex(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    /**
     * Getter for real part
     *
     * @return real
     */

    public double getReal() {
        return real;
    }

    /**
     * Getter for imaginary part
     *
     * @return imaginary
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Getter for magnitude
     *
     * @return magnitude
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Getter for angle
     *
     * @return angle
     */
    public double getAngle() {
        return angle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String prefix;

        if (imaginary < 0) prefix = "-";
        else prefix = "+";
        sb.append(String.format("%.1f%si%.1f", real, prefix, Math.abs(imaginary)));

        return sb.toString();
    }
}
