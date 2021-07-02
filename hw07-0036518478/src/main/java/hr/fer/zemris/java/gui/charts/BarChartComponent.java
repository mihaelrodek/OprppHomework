package hr.fer.zemris.java.gui.charts;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Class that represents bar chart and its GUI components.
 */
public class BarChartComponent extends JComponent {

    /**
     * Right default padding.
     */
    private static final int RIGHT = 10;

    /**
     * Top default padding.
     */
    private static final int TOP = 10;
    /**
     * Axes size
     */
    private static final int AXES = 6;

    /**
     * Size of arrow
     */
    private static final int ARROW_SIZE = 6;

    private BarChart chart;

    /**
     * Distance from left edge of window.
     */
    private int leftGap;

    /**
     * distance from bottom of the window.
     */
    private int bottomGap;

    /**
     * Default constructor
     *
     * @param chart {@link BarChart} to be drawn
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        Dimension d = getSize();

        Stroke previousStroke = graphics.getStroke();
        Font previousFont = graphics.getFont();
        Color previousColor = graphics.getColor();

        int actualWidth = d.width - RIGHT;
        int actualHeight = d.height - TOP;

        int columns = chart.getList().size();
        int rows = Double.valueOf(Math.ceil((chart.getMaxY() - chart.getMinY()) / (1.0 * chart.getDistance()))).intValue();

        leftGap = actualWidth / columns;
        bottomGap = actualHeight / rows;

        int columnWidth = (actualWidth - leftGap - RIGHT) / columns;
        int rowHeight = (actualHeight - bottomGap - TOP) / rows;

        int maxVertical = actualHeight - bottomGap - rows * rowHeight;
        int maxHorizontal = leftGap + columns * columnWidth;

        graphics.setFont(new Font("Calibri", Font.BOLD, 13));
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, d.width, d.height);

        drawHorizontal(graphics, maxHorizontal, rows, rowHeight, actualHeight);
        drawVertical(graphics, maxVertical, columnWidth, columns, actualHeight);


        drawAxis(graphics, leftGap - AXES, actualHeight - bottomGap, actualWidth + RIGHT,
                actualHeight - bottomGap);
        drawAxis(graphics, leftGap, actualHeight - bottomGap + AXES, leftGap, 0);

        drawBars(graphics, actualHeight, rowHeight, columnWidth, columns);


        writeXAxisDescription(graphics, columns, columnWidth, actualHeight, previousFont);
        writeYAxisDescription(graphics, actualHeight, previousFont);

        graphics.setTransform(AffineTransform.getQuadrantRotateInstance(0));
        graphics.setColor(previousColor);
        graphics.setFont(previousFont);
        graphics.setStroke(previousStroke);

    }

    /**
     * Method that draws horziontal line
     * @param graphics
     * @param maxHorizontal
     * @param rows
     * @param rowHeight
     * @param actualHeight
     */

    private void drawHorizontal(Graphics2D graphics, int maxHorizontal, int rows, int rowHeight, int actualHeight) {
        int minValue = chart.getMinY();
        int step = chart.getDistance();
        for (int j = actualHeight - bottomGap, z = 0; z <= rows; j -= rowHeight, z++) {
            graphics.setColor(Color.DARK_GRAY);
            graphics.drawLine(leftGap - AXES, j, leftGap, j);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(leftGap, j, maxHorizontal + AXES, j);
            String number = String.valueOf(minValue++ * step);
            graphics.drawString(number, leftGap - AXES - leftGap / 3, j + AXES);
        }

    }

    /**
     * Method that draws vertical line
     * @param graphics
     * @param maxVertical
     * @param columnWidth
     * @param cols
     * @param actualHeight
     */
    private void drawVertical(Graphics2D graphics, int maxVertical, int columnWidth, int cols, int actualHeight) {
        for (int i = leftGap, z = 0; z < cols; i += columnWidth, z++) {
            graphics.setColor(Color.DARK_GRAY);
            graphics.drawLine(i, actualHeight - bottomGap, i, actualHeight - bottomGap + AXES);
            graphics.setColor(Color.BLACK);
            graphics.drawLine(i, maxVertical - AXES, i, actualHeight - bottomGap);
            String number = String.valueOf(chart.getList().get(z).getX());
            graphics.drawString(number, i - number.length() / 2 + (columnWidth / 2),
                    actualHeight - 2 * (bottomGap / 3));
        }

    }

    /**
     * Method that draws x axis description
     * @param graphics
     * @param cols
     * @param columnWidth
     * @param actualHeight
     * @param font
     */
    private void writeXAxisDescription(Graphics2D graphics, int cols, int columnWidth, int actualHeight, Font font) {
        graphics.setColor(Color.BLACK);
        Font setFont = new Font(font.getName(), Font.BOLD, font.getSize() + 5);
        graphics.setFont(setFont);
        graphics.drawString(chart.getxDes(), columnWidth / 2 * cols, actualHeight - (bottomGap / 5));
    }

    /**
     * Method that draws y axis description
     * @param graph
     * @param actualHeight
     * @param font font used
     */

    private void writeYAxisDescription(Graphics2D graph, int actualHeight, Font font) {
        Graphics2D graphics = (Graphics2D) graph.create();
        Font setFont = new Font(font.getName(), Font.BOLD, font.getSize() + 5);
        graphics.setFont(setFont);
        graphics.setTransform(AffineTransform.getQuadrantRotateInstance(3));
        graphics.drawString(chart.getyDes(), -actualHeight / 2, leftGap / 3);
    }

    /**
     * Method that draws bars on chart
     * @param graphics
     * @param actualHeight
     * @param rowHeight
     * @param columnWidth
     * @param cols
     */

    private void drawBars(Graphics2D graphics, int actualHeight, int rowHeight, int columnWidth, int cols) {
        for (int i = 0, z = leftGap + 1; i < cols; i++, z += columnWidth) {
            int value = chart.getList().get(i).getY();

            if (value < chart.getMinY()) value = chart.getMinY();
            if (value > chart.getMaxY()) value = chart.getMaxY();

            int height = rowHeight * (value - chart.getMinY()) / chart.getDistance();
            graphics.setColor(Color.RED);
            graphics.fillRect(z, actualHeight - bottomGap - height, columnWidth, height);

            graphics.setColor(Color.DARK_GRAY);
            graphics.drawLine(z, actualHeight - bottomGap - height,
                    z + columnWidth, actualHeight - bottomGap - height);
            graphics.drawLine(z + columnWidth - 1, actualHeight - bottomGap - height,
                    z + columnWidth - 1, actualHeight - bottomGap);
        }
    }

    /**
     * Method that draws axis on chart
     * @param g1
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */

    private void drawAxis(Graphics g1, int x1, int y1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();
        g.setColor(Color.BLACK);
        double angle = Math.atan2((y2 - y1), (x2 - x1));
        int len = (int) Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        g.drawLine(0, 0, len, 0);
        int[] xPoints = new int[]{len, len - ARROW_SIZE, len - ARROW_SIZE, len};
        int[] yPoints = new int[]{0, -ARROW_SIZE, ARROW_SIZE, 0};
        g.fillPolygon(xPoints, yPoints, 4);
    }
}



