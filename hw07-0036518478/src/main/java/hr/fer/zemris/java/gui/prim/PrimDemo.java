package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

public class PrimDemo extends JFrame {

    /**
     * Main method.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
    }

    /**
     * Basic constructor.
     */
    public PrimDemo() {
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PrimDemo program");
        setLocation(20, 20);
        setSize(500, 200);
        initGUI();
        //pack();
    }

    /**
     * This class is used for initializing graphical user interface.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        ListModel<Integer> model = new PrimListModel();

        JScrollPane listLeft = new JScrollPane(new JList<>(model));
        JScrollPane listRight = new JScrollPane(new JList<>(model));

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 2));
        jPanel.add(listLeft);
        jPanel.add(listRight);

        cp.add(jPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Next number");
        cp.add(addButton, BorderLayout.PAGE_END);

        addButton.addActionListener(e -> ((PrimListModel) model).next());

    }

}
