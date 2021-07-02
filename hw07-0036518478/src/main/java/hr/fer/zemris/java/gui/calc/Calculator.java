package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

public class Calculator extends JFrame {

    private CalcModel model = new CalcModelImplemenation();

    private JPanel panel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));

    }

    public Calculator() {
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calculator");
        setLocation(20, 20);
        setSize(500, 200);
        initGUI();
    }

    private static class CalcModelImplemenation implements CalcModel {

        @Override
        public void addCalcValueListener(CalcValueListener l) {

        }

        @Override
        public void removeCalcValueListener(CalcValueListener l) {

        }

        @Override
        public double getValue() {
            return 0;
        }

        @Override
        public void setValue(double value) {

        }

        @Override
        public boolean isEditable() {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public void clearAll() {

        }

        @Override
        public void swapSign() throws CalculatorInputException {

        }

        @Override
        public void insertDecimalPoint() throws CalculatorInputException {

        }

        @Override
        public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {

        }

        @Override
        public boolean isActiveOperandSet() {
            return false;
        }

        @Override
        public double getActiveOperand() throws IllegalStateException {
            return 0;
        }

        @Override
        public void setActiveOperand(double activeOperand) {

        }

        @Override
        public void clearActiveOperand() {

        }

        @Override
        public DoubleBinaryOperator getPendingBinaryOperation() {
            return null;
        }

        @Override
        public void setPendingBinaryOperation(DoubleBinaryOperator op) {

        }

        @Override
        public void freezeValue(String value) {

        }

        @Override
        public boolean hasFrozenValue() {
            return false;
        }
    }

    public void initGUI() {
        Container cp = getContentPane();

        panel = new JPanel(new CalcLayout(3));
        panel.setBackground(Color.WHITE);

        JLabel ekran = new JLabel(model.toString());
        ekran.setBackground(Color.YELLOW);
        ekran.setHorizontalAlignment(SwingConstants.RIGHT);
        ekran.setOpaque(true);
        model.addCalcValueListener(model -> ekran.setText(model.toString()));
        panel.add(ekran, "1,1");

        panel.add(createButton("0", "5,3", e -> model.insertDigit(0)));
        panel.add(createButton("1", "5,3", e -> model.insertDigit(1)));
        panel.add(createButton("2", "5,3", e -> model.insertDigit(2)));
        panel.add(createButton("3", "5,3", e -> model.insertDigit(3)));
        panel.add(createButton("4", "5,3", e -> model.insertDigit(4)));
        panel.add(createButton("5", "5,3", e -> model.insertDigit(5)));
        panel.add(createButton("6", "5,3", e -> model.insertDigit(6)));
        panel.add(createButton("7", "5,3", e -> model.insertDigit(7)));
        panel.add(createButton("8", "5,3", e -> model.insertDigit(8)));
        panel.add(createButton("9", "5,3", e -> model.insertDigit(9)));
    }

    private JButton createButton(String name, String position, ActionListener action) {
        JButton btn = new JButton(name);
        btn.setBackground(Color.BLUE);
        btn.addActionListener(action);
        panel.add(btn, position);
        return btn;
    }
}


