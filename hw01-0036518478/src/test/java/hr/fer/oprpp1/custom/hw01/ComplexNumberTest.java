package hr.fer.oprpp1.custom.hw01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.hw01.ComplexNumber;

public class ComplexNumberTest {

    private static final double DELTA = 1E-6;

    @Test
    public void getReal() {
        ComplexNumber c1 = new ComplexNumber(32, 4);
        assertEquals(32, c1.getReal(), DELTA);
    }

    @Test
    public void getImaginary() {
        ComplexNumber c1 = new ComplexNumber(2, 34);
        assertEquals(34, c1.getImaginary(), DELTA);
    }

    @Test
    public void getMagnitude() {
        ComplexNumber c1 = new ComplexNumber(6, 9);
        assertEquals(Math.sqrt(Math.pow(c1.getReal(), 2) + Math.pow(c1.getImaginary(), 2)), c1.getMagnitude(), DELTA);
    }

    @Test
    public void getAngle() {
        ComplexNumber c1 = new ComplexNumber(0, 5);
        assertEquals(Math.PI / 2, c1.getAngle(), DELTA);

    }

    @Test
    public void fromReal() {
        ComplexNumber c1 = ComplexNumber.fromReal(2);
        assertEquals(2, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void fromImaginaryNegative() {
        ComplexNumber c1 = ComplexNumber.fromImaginary(-1);
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(-1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseNegativeRealPositiveImaginary() {

        ComplexNumber c1 = ComplexNumber.parse("  -2.71  +3.51i  ");
        assertEquals(-2.71, c1.getReal(), DELTA);
        assertEquals(3.51, c1.getImaginary(), DELTA);
    }

    @Test
    public void parsePositiveRealNegativeImaginary() {

        ComplexNumber c1 = ComplexNumber.parse("  2.69 -6.99i");
        assertEquals(2.69, c1.getReal(), DELTA);
        assertEquals(-6.99, c1.getImaginary(), DELTA);
    }

    @Test
    public void parsePositiveRealPOsitiveImaginary() {

        ComplexNumber c1 = ComplexNumber.parse("  +2.00 +5.01i");
        assertEquals(2.0, c1.getReal(), DELTA);
        assertEquals(5.01, c1.getImaginary(), DELTA);
    }

    @Test
    public void fromImaginaryNegativePositive() {
        ComplexNumber c1 = ComplexNumber.fromImaginary(1);
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(1, c1.getImaginary(), DELTA);
    }

    @Test
    public void fromMagnitudeAndAngle() {
        ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(7, Math.PI / 2);
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(7, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseDecimalWithImaginaryEqualsMinusOne() {
        ComplexNumber c1 = ComplexNumber.parse("-2.71-i");
        assertEquals(-2.71, c1.getReal(), DELTA);
        assertEquals(-1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseDecimalWithNegativeRealImaginaryEqualsOne() {
        ComplexNumber c1 = ComplexNumber.parse("-2.71+i");
        assertEquals(-2.71, c1.getReal(), DELTA);
        assertEquals(1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseDecimalWithPositiveeRealImaginaryEqualsOne() {
        ComplexNumber c1 = ComplexNumber.parse("+2.71+i");
        assertEquals(2.71, c1.getReal(), DELTA);
        assertEquals(1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseImaginaryEqualsOnePlusWithoutReal() {
        ComplexNumber c1 = ComplexNumber.parse("  +  i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseImaginaryEqualsOneNoPlusWithoutReal() {
        ComplexNumber c1 = ComplexNumber.parse("   i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseImaginaryEqualsMinusOne() {
        ComplexNumber c1 = ComplexNumber.parse("  - i ");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(-1, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseNegativeImaginary() {
        ComplexNumber c1 = ComplexNumber.parse("-4.20i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(-4.20, c1.getImaginary(), DELTA);
    }

    @Test
    public void parsePositiveImaginary() {
        ComplexNumber c1 = ComplexNumber.parse("0.0i");
        assertEquals(0, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseNegativeReal() {
        ComplexNumber c1 = ComplexNumber.parse("-23");
        assertEquals(-23, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parsePositiveRealZeroDot() {
        ComplexNumber c1 = ComplexNumber.parse("0.");
        assertEquals(0., c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseRealEqualOne() {

        ComplexNumber c1 = ComplexNumber.parse("1.0");
        assertEquals(1, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseRealEqualsMinusOne() {

        ComplexNumber c1 = ComplexNumber.parse("-1.");
        assertEquals(-1, c1.getReal(), DELTA);
        assertEquals(0, c1.getImaginary(), DELTA);
    }

    @Test
    public void parseString() {
        assertThrows(NumberFormatException.class, () -> ComplexNumber.parse("Marko sunce ti tvoje"));
    }

    @Test
    public void parseStringNullPointerException() {
        assertThrows(NullPointerException.class, () -> ComplexNumber.parse(null));
    }

    @Test
    public void divideWithZeroThrows() {
        ComplexNumber c1 = new ComplexNumber(1, 1);
        ComplexNumber c2 = new ComplexNumber(0, 0);
        assertThrows(IllegalArgumentException.class, () -> c1.div(c2));
    }

    @Test
    public void div() {
        ComplexNumber c1 = new ComplexNumber(-2, 4);
        ComplexNumber c2 = new ComplexNumber(1, 5);
        ComplexNumber c3 = c1.div(c2);

        assertEquals(9. / 13., c3.getReal(), DELTA);
        assertEquals(7. / 13., c3.getImaginary(), DELTA);
    }

    @Test
    public void power() {
        ComplexNumber c1 = new ComplexNumber(4, 5);
        ComplexNumber c2 = c1.power(5);

        assertEquals(-10475, c2.getImaginary(), DELTA);

        assertEquals(-2476, c2.getReal(), DELTA);

    }

    @Test
    public void add() {
        ComplexNumber c1 = new ComplexNumber(-2, 4);
        ComplexNumber c2 = new ComplexNumber(2, 5);

        ComplexNumber c3 = c1.add(c2);
        ComplexNumber c4 = c1.add(new ComplexNumber(2, 3));
        assertEquals(0, c3.getReal(), DELTA);
        assertEquals(9, c3.getImaginary(), DELTA);
        assertEquals(7, c4.getImaginary(), DELTA);
    }

    @Test
    public void sub() {
        ComplexNumber c1 = new ComplexNumber(13, 4);
        ComplexNumber c2 = new ComplexNumber(4, 5);

        ComplexNumber c3 = c1.sub(c2);

        assertEquals(9, c3.getReal(), DELTA);
        assertEquals(-1, c3.getImaginary(), DELTA);

    }

    @Test
    public void subWithAdd() {
        ComplexNumber c1 = new ComplexNumber(13, 4);
        ComplexNumber c2 = new ComplexNumber(4, 5);

        ComplexNumber c3 = c1.sub(c2);
        ComplexNumber c4 = c3.add(new ComplexNumber(2, 3));
        assertEquals(9, c3.getReal(), DELTA);
        assertEquals(-1, c3.getImaginary(), DELTA);
        assertEquals(2, c4.getImaginary(), DELTA);
    }

    @Test
    public void mul() {
        ComplexNumber c1 = new ComplexNumber(3, 4);
        ComplexNumber c2 = new ComplexNumber(4, 5);

        ComplexNumber c3 = c1.mul(c2);

        assertEquals(-8, c3.getReal(), DELTA);
        assertEquals(31, c3.getImaginary(), DELTA);
    }

    @Test
    public void root() {
        ComplexNumber c1 = new ComplexNumber(3, 5);
        ComplexNumber[] roots = c1.root(3);

        ComplexNumber root1 = roots[0];
        ComplexNumber root2 = roots[1];
        ComplexNumber root3 = roots[2];

        assertEquals(1.69477038, root1.getReal(), DELTA);
        assertEquals(0.60610653, root1.getImaginary(), DELTA);

        assertEquals(-1.3722888, root2.getReal(), DELTA);
        assertEquals(1.16466094, root2.getImaginary(), DELTA);

        assertEquals(-0.3224815, root3.getReal(), DELTA);
        assertEquals(-1.7707674, root3.getImaginary(), DELTA);

    }

    @Test
    public void rootNegative() {
        ComplexNumber c1 = new ComplexNumber(-2, 5);
        ComplexNumber[] roots = c1.root(2);

        assertEquals(1.30099285, roots[0].getReal(), DELTA);
        assertEquals(1.92160932, roots[0].getImaginary(), DELTA);

        assertEquals(-1.3009928, roots[1].getReal(), DELTA);
        assertEquals(-1.9216093, roots[1].getImaginary(), DELTA);

    }

}
