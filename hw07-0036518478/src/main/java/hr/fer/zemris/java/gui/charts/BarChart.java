package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * CLass that represents values of bar chart.
 * @author Mihael Rodek
 */
public class BarChart {

    /**
     * List of xy values
     */
    private List<XYValue> list;

    /**
     * Description of x axis
     */
    private String xDes;
    /**
     * Description of y axis
     */
    private String yDes;
    /**
     * Min y value
     */
    private int minY;
    /**
     * Max y value
     */
    private int maxY;
    /**
     * Distance on y axis
     */
    private int distance;

    /**
     * Default constructor
     * @param list
     * @param xDes
     * @param yDes
     * @param minY
     * @param maxY
     * @param distance
     */
    public BarChart(List<XYValue> list, String xDes, String yDes, int minY, int maxY, int distance) {
        if (maxY <= minY) throw new IllegalArgumentException("Max y must be greater than min y");

        for (XYValue val : list) {
            if (!(val.getY() > minY))
                throw new IllegalArgumentException("All y-s should be greater than given minimum y value");
        }
        this.list = list;
        this.xDes = xDes;
        this.yDes = yDes;
        this.minY = minY;
        this.maxY = maxY;

        if (((maxY - minY) % distance) != 0)
            while (((maxY - minY) % distance) != 0)
                distance++;
        this.distance = distance;
    }

    public List<XYValue> getList() {
        return list;
    }

    public String getxDes() {
        return xDes;
    }

    public String getyDes() {
        return yDes;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getDistance() {
        return distance;
    }
}
