package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalcLayoutTest {

    @Test
    public void invalidConstraintFirstTest() {
        JPanel p = new JPanel(new CalcLayout(2));
        assertThrows(IllegalArgumentException.class, () -> p.add(new JButton("Marko"), new RCPosition(0, 5)));
    }

    @Test
    public void invalidConstraintSecondTest() {
        JPanel p = new JPanel(new CalcLayout(2));
        assertThrows(CalcLayoutException.class, () -> p.add(new JButton("Darko"), new RCPosition(1, 5)));
    }

    @Test
    public void throwsAdd() {
        JPanel p = new JPanel(new CalcLayout(2));
        assertThrows(CalcLayoutException.class, () -> p.add(new JButton("Darko"), 0));
    }

    @Test
    public void invalidConstraint3() {
        JPanel p = new JPanel(new CalcLayout(2));
        p.add(new JButton("Stipe"), new RCPosition(2, 5));
        p.add(new JButton("Stipe"), new RCPosition(2, 5));
        //assertThrows(CalcLayoutException.class,()->p.add(new JButton("Mile"), new RCPosition(2, 5)));
    }

    @Test
    void testDimensionWidthAndHeight() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    void testDimensionWidthAndHeightSecondTest() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

}
