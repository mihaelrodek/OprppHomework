package hr.fer.oprpp1.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {

    private static final double DELTA = 1E-8;

    @Test
    public void testCreateVector() {
        Vector2D vector = new Vector2D(6.9, 42.0);
        assertEquals(6.9, vector.getX());
        assertEquals(42.0, vector.getY());
    }

    @Test
    public void testAddVector() {
        Vector2D vector = new Vector2D(3.2, 10);
        vector.add(new Vector2D(1.1, 100));

        assertEquals(4.3, Math.abs(vector.getX()), DELTA);
        assertEquals(110, Math.abs(vector.getY()), DELTA);
    }

    @Test
    public void testVectorAdded() {
        Vector2D vector = new Vector2D(2.2, 10);
        Vector2D newVector = vector.added(new Vector2D(1.1, 100));

        assertEquals(2.2, Math.abs(vector.getX()), DELTA);
        assertEquals(10, Math.abs(vector.getY()), DELTA);

        assertEquals(3.3, Math.abs(newVector.getX()), DELTA);
        assertEquals(110, Math.abs(newVector.getY()), DELTA);
    }

    @Test
    public void testRotateVector() {
        Vector2D vector = new Vector2D(3.3, 0);
        vector.rotate(Math.PI / 2);

        assertTrue(Math.abs(vector.getX()) < DELTA);
        assertEquals(3.3, Math.abs(vector.getY()), DELTA);
    }

    @Test

    public void testRotateVectorZero() {
        Vector2D vector = new Vector2D(0, 0);
        vector.rotate(Math.PI / 2);

        assertTrue(Math.abs(vector.getX()) < DELTA);
        assertEquals(0, Math.abs(vector.getX()), DELTA);
        assertEquals(0, Math.abs(vector.getY()), DELTA);
    }

    @Test
    public void testRotateVectorSame() {
        Vector2D vector = new Vector2D(2.2, 2.2);
        vector.rotate(Math.PI);

        assertEquals(2.2, Math.abs(vector.getX()), DELTA);
        assertEquals(2.2, Math.abs(vector.getY()), DELTA);
    }

    @Test
    public void testRotatedVector() {
        Vector2D vector = new Vector2D(5.0, 5.0);
        Vector2D newVector = vector.rotated(Math.PI);

        assertNotSame(newVector, vector);
        assertEquals(5.0, Math.abs(newVector.getX()), DELTA);
        assertEquals(5.0, Math.abs(newVector.getY()), DELTA);
    }

    @Test
    public void testAddRotateVector() {
        Vector2D vector = new Vector2D(3.3, 0);
        vector.add(new Vector2D(1.1, 0));
        vector.rotate(Math.PI / 2);

        assertTrue(Math.abs(vector.getX()) < DELTA);
        assertEquals(4.4, Math.abs(vector.getY()), DELTA);
    }

    @Test
    public void testCopyVector() {
        Vector2D vector = new Vector2D(0.2, 6.0);
        Vector2D newVector = vector.copy();
        assertNotSame(newVector, vector);
    }

    @Test
    public void testScaleVector() {
        Vector2D vector = new Vector2D(4.2, 2.1);
        vector.scale(2);
        assertEquals(8.4, Math.abs(vector.getX()), DELTA);
        assertEquals(4.2, Math.abs(vector.getY()), DELTA);
    }

    @Test
    public void testScaleVectorZero() {
        Vector2D vector = new Vector2D(4.2, 2.1);
        vector.scale(0);
        assertEquals(0, Math.abs(vector.getX()), DELTA);
        assertEquals(0, Math.abs(vector.getY()), DELTA);
    }

    @Test
    public void testScaledVector() {
        Vector2D vector = new Vector2D(3.2, 0.2);
        Vector2D newVector = vector.scaled(2);

        assertNotSame(newVector, vector);
        assertEquals(6.4, Math.abs(newVector.getX()), DELTA);
        assertEquals(0.4, Math.abs(newVector.getY()), DELTA);
    }

    @Test
    public void testScaledVectorZero() {
        Vector2D vector = new Vector2D(3.2, 0.2);
        Vector2D newVector = vector.scaled(0);

        assertNotSame(newVector, vector);
        assertEquals(0, Math.abs(newVector.getX()), DELTA);
        assertEquals(0, Math.abs(newVector.getY()), DELTA);
    }
}
