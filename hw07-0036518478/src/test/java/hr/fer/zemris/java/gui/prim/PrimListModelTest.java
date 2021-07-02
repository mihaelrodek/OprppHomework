package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PrimListModelTest {

    @Test
    public void testFirstPrimeNumber() {
        PrimListModel model = new PrimListModel();
        assertEquals(Integer.valueOf(2), model.getElementAt(0));
    }

    @Test
    public void test1000thPrimeNumber() {
        PrimListModel model = new PrimListModel();
        for (int i = 1; i <= 1000; i++)
            model.next();
        assertEquals(Integer.valueOf(7919), model.getElementAt(999));
    }

    @Test
    public void test50thPrimeNumber() {
        PrimListModel model = new PrimListModel();
        for (int i = 1; i <= 50; i++)
            model.next();
        assertEquals(Integer.valueOf(229), model.getElementAt(49));
    }

    @Test
    public void testPrimeNumber() {
        PrimListModel model = new PrimListModel();

        model.next();
        model.next();
        model.next();

        assertEquals(Integer.valueOf(5), model.getElementAt(2));
    }

    @Test
    public void testModelSize() {
        PrimListModel model = new PrimListModel();
        model.next();
        model.next();
        assertEquals(3, model.getSize());
    }

    @Test
    public void testNonPrime() {
        PrimListModel model = new PrimListModel();
        model.next();
        model.next();
        assertNotEquals(Integer.valueOf(3), model.getElementAt(2));
    }

}
