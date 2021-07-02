package hr.fer.zemris.java.gui.layouts;

import javax.swing.*;
import java.awt.*;

public class DemoFrame2 extends JFrame {

    public DemoFrame2() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500,500);
        initGUI();
        pack();
    }
    private void initGUI() {
        Container p = getContentPane();
        p.setLayout(new CalcLayout(3));
        p.add(l("x"), new RCPosition(1, 1));
        p.add(l("y"), new RCPosition(2, 3));
        p.add(l("z"), new RCPosition(2, 7));
        p.add(l("w"), new RCPosition(4, 2));
        p.add(l("a"), new RCPosition(4, 5));
        p.add(l("b"), new RCPosition(4, 7));
    }

    private JLabel l(String text) {
        JLabel l = new JLabel(text);
        l.setBackground(Color.YELLOW);
        l.setOpaque(true);
        return l;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> new DemoFrame2().setVisible(true));
    }
}
