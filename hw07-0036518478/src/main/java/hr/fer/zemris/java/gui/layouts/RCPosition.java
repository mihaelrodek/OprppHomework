
package hr.fer.zemris.java.gui.layouts;

public class RCPosition {

    int row;
    int column;

    public RCPosition(int row, int column) {
        if (row <= 0 || column <= 0) throw new IllegalArgumentException("Row or column is lower than 0.");
        this.row = row;
        this.column = column;
    }


    public static RCPosition parse(String text) {
        RCPosition position;

        if (text.isEmpty())
            throw new IllegalArgumentException("Please enter proper input");


        try {
            String string = text.trim().replace("\\s+", "");
            String[] split = string.split(",");

            if (split.length != 2)
                throw new IllegalArgumentException("Too many arguments, expected row and column");
            position = new RCPosition(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Row and column should be integers");
        }
        return position;
    }


    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
