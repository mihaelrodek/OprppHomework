package hr.fer.oprpp1.hw01;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that implements a support for working with complex numbers. It has all
 * methods needed for representation of {@link ComplexNumber}.
 *
 * @author Mihael Rodek
 */
public class ComplexNumber {

    /**
     * Vraibles that represent real and imaginary part of ComplexNumber.
     */

    double real;
    double imaginary;

    /**
     * Constructor for given real and imaginary part.
     *
     * @param real      Real part of complex number
     * @param imaginary Imaginary part of complex number
     */

    public ComplexNumber(double real, double imaginary) {
        this.imaginary = imaginary;
        this.real = real;

    }

    /**
     * Constructor to get Complex number from real part
     *
     * @param real Real part of complex number
     * @return new ComplexNumber
     */

    public static ComplexNumber fromReal(double real) {
        return new ComplexNumber(real, 0.0);
    }

    /**
     * Constructor to get Complex number from imaginary part
     *
     * @param imaginary Imaginary part of complex number
     * @return new ComplexNumber
     */
    public static ComplexNumber fromImaginary(double imaginary) {
        return new ComplexNumber(0.0, imaginary);
    }

    /**
     * Constructor to get Complex number from magnitude and angle<br>
     * Formula is: r*cos(φ)+r*sin(φ)
     *
     * @param magnitude Magnitude of complex number
     * @param angle     Angle of complex number
     * @return new ComplexNumber
     */
    public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
        return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    /**
     * Method used to parse given string to a complex number format. This method
     * uses regex expression for selecting acceptable number formats. It first
     * checks if number has only real part without imaginary part. If so it tries to
     * parse it and return new number. Otherwise, it creates a regex expression for
     * both real and imaginary part. After that both pattern and matcher are invoked
     * to search for a corresponding group that satisfies regex.
     *
     * @param s string to be parsed into complex number
     * @return new ComplexNumber parsed from string
     * @throws NumberFormatException if string doesn't contain parsable number or
     *                               number is not in good formats
     */

    public static ComplexNumber parse(String s) {

        if (!s.contains("i")) {
            try {
                return new ComplexNumber(Double.parseDouble(s), 0);
            } catch (NumberFormatException ex) {
                throw new RuntimeException("Illegal number!");
            }
        }
        if (s.trim().equals("i"))
            return new ComplexNumber(0.0, 1.0);

        String realRegex = "(\\s*[-,+]?\\s*[0-9]*[.]?[0-9]*\\s*)";
        String imaginaryRegex = "(\\s*[-,+]?\\s*[0-9]*[.]?[0-9]*i\\s*)";
        Pattern pattern;
        Matcher matcher;

        if (s.contains("i")) {
            pattern = Pattern.compile(realRegex + imaginaryRegex, Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(s.trim());

            if (matcher.find(0) && !matcher.group(2).replace("i", "").trim().equals("")) {

                String imaginaryString = matcher.group(2).replace("i", "").trim();
                if (imaginaryString.length() == 1)
                    imaginaryString += "1";
                return new ComplexNumber(Double.parseDouble(matcher.group(1)), Double.parseDouble(imaginaryString));
            } else {

                // Regex for detecting complex numbers with no real part
                pattern = Pattern.compile(imaginaryRegex, Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(s.trim());

                if (matcher.find(0)) {

                    imaginaryRegex = matcher.group(1).replace("i", "").trim();
                    if (imaginaryRegex.length() == 1)
                        imaginaryRegex += "1";

                    return new ComplexNumber(0.0, Double.parseDouble(imaginaryRegex));
                }
            }
        } else
            throw new NumberFormatException("Complex number is not in a good format!");
        return new ComplexNumber(0.0, 0.0);
    }

    /**
     * Getter for real part of ComplexNumber
     *
     * @return real part of complex number
     */

    public double getReal() {
        return real;
    }

    /**
     * Getter for imaginary part of ComplexNumber
     *
     * @return imaginary part of complex number
     */
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Getter for magnitude of ComplexNumber
     *
     * @return magnitude of complex number
     */
    public double getMagnitude() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imaginary, 2));
    }

    /**
     * Getter for angle of ComplexNumber
     *
     * @return angle of complex number
     */
    public double getAngle() {
        if (Math.atan2(imaginary, real) < 0)
            return Math.atan2(imaginary, real) + 2 * Math.PI;
        else
            return Math.atan2(imaginary, real);

    }

    /**
     * Method used for adding complex numbers. Method is called using c1.add(c2)
     *
     * @param c other number to be added
     * @return Complex number that represents sum of complex numbers
     */

    public ComplexNumber add(ComplexNumber c) {
        return new ComplexNumber((this.real + c.real), (this.imaginary + c.imaginary));
    }

    /**
     * Method used for subtracting complex numbers. Method is called using
     * c1.sub(c2)
     *
     * @param c other number to be subtracted
     * @return Complex number that represents difference of complex numbers
     */

    public ComplexNumber sub(ComplexNumber c) {
        return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
    }

    /**
     * Method used for multiplying two complex numbers. Method is called using
     * c1.mul(c2)
     *
     * @param c other number to be multiplied with
     * @return Complex number that represents multiplication of complex numbers
     */
    public ComplexNumber mul(ComplexNumber c) {
        return new ComplexNumber(this.real * c.real - this.imaginary * c.imaginary,
                this.real * c.imaginary + this.imaginary * c.real);
    }

    /**
     * Method used for dividing two complex numbers.
     *
     * @param c number to be divided with
     * @return Complex number that represents quotient of complex numbers
     * @throws IllegalArgumentException thrown when trying to divide with 0 with
     *                                  appropriate message
     */

    public ComplexNumber div(ComplexNumber c) {
        if (c.getReal() == 0.0 && c.getImaginary() == 0.0) {
            throw new IllegalArgumentException("Cannot divide with 0");
        }

        double div = c.getReal() / c.getImaginary();
        if (Math.abs(c.getReal()) < Math.abs(c.getImaginary())) {
            return new ComplexNumber((real * div + imaginary) / (c.getReal() * div + c.getImaginary()),
                    (imaginary * div - real) / (c.getReal() * div + c.getImaginary()));
        } else {
            div = Math.pow(div, -1);
            return new ComplexNumber((imaginary * div + real) / (c.getReal() * div + c.getImaginary()),
                    (imaginary - real * div) / (c.getReal() * div + c.getImaginary()));
        }
    }

    /**
     * Method used for exponenting complex number. Method is called using c.power(n)
     * which represents c^n (c raised to n)
     *
     * @param n represents exponent
     * @return Complex number that represents complex number raised to exponent
     * @throws IllegalArgumentException thrown if exponent is lower than 0
     */

    public ComplexNumber power(int n) {
        if (n < 0)
            throw new IllegalArgumentException("Power can't be lower than 0.");
        return fromMagnitudeAndAngle(Math.pow(getMagnitude(), n), getAngle() * n);
    }

    /**
     * This method is used for calculating nth roots of complex number. Nth root is
     * calculated by given function: r^(1/n)*((cos((ϕ+2kπ)/n) + isin((ϕ+2kπ)/n) )
     *
     * @param n Degree of root exponent
     * @return Array of all roots of complex number
     * @throws IllegalArgumentException thrown if root is lower than 1
     */

    public ComplexNumber[] root(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Root most be greater than 0.");

        double rootAngle = getAngle() / n;
        double rootMagnitude = Math.pow(getMagnitude(), 1. / (float) n);
        double angle = 2 * Math.PI / n;
        ComplexNumber[] roots = new ComplexNumber[n];
        for (int i = 0; i < n; i++) {
            roots[i] = fromMagnitudeAndAngle(rootMagnitude, rootAngle);
            rootAngle += angle;
        }
        return roots;
    }

    /**
     * Method that converts Complex number in appropriate String format
     *
     * @return string in corresponding format
     */

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (imaginary == 0)
            sb.append(real);
        else if (real == 0)
            sb.append(imaginary).append("i");
        else if (imaginary > 0)
            sb.append(real).append(" + ").append(imaginary).append("i");
        else
            sb.append(real).append(" - ").append(-imaginary).append("i");
        return sb.toString();

    }
}