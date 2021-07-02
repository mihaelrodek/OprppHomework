package hr.fer.zemris.java.gui.charts;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo class for bar chart
 */
public class BarChartDemo extends JFrame {

    BarChart chart;

    public static void main(String[] args) throws IOException {

        if (args.length != 1) throw new IllegalArgumentException("Invalid path to the file.");

        List<String> lines = Files.readAllLines(Paths.get(args[0]));

        final BarChart finalChart = parse(lines);
        SwingUtilities.invokeLater(() -> new BarChartDemo(finalChart).setVisible(true));
    }

    /**
     * Method to parse given input file
     * @param lines
     * @return
     * @throws IOException
     */

    private static BarChart parse(List<String> lines) throws IOException {
        if (lines.size() != 6) throw new IOException("Invalid number of rows");

        String[] values = lines.get(2).split(" ");
        List<XYValue> xyValues = new ArrayList<>();

        for (String value : values) {
            String[] xy = value.split(",");
            if (xy.length != 2)
                throw new IOException("Invalid number in x,y pairs.");
            else xyValues.add(new XYValue(Integer.parseInt(xy[0]), Integer.parseInt(xy[1])));
        }

        return new BarChart(xyValues, lines.get(0), lines.get(1), Integer.parseInt(lines.get(3)),
                Integer.parseInt(lines.get(4)), Integer.parseInt(lines.get(5)));

    }

    /**
     * Constructor for instatiating demo class
     * @param chart
     */
    public BarChartDemo(BarChart chart) {
        super();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.chart = chart;
        setTitle("BarChartDemo program");
        setLocation(20, 20);
        setSize(600, 600);
        initGUI();
    }

    /**
     * Initialization of GUI
     */
    public void initGUI() {
        Container cp = getContentPane();
        cp.add(new BarChartComponent(chart));
    }


}
