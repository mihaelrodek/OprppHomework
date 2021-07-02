package hr.fer.zemris.java.gui.layouts;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CalcLayout implements LayoutManager2 {


    private static final int ROWS = 5;
    private static final int COLUMNS = 7;
    private static Map<Component, RCPosition> map = new HashMap<>();
    private int space;

    public CalcLayout() {
        this(0);
    }

    public CalcLayout(int space) {
        if (space < 0) throw new IllegalArgumentException("Gap cannot be 0.");
        this.space = space;
    }

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof RCPosition) {
            RCPosition position = (RCPosition) constraints;
            if (!checkPosition(position)) throw new CalcLayoutException();
            if (map.get(position) != null) {
                throw new CalcLayoutException("Component already exists.");
            }
            map.put(comp, position);
        } else if (constraints instanceof String) {
            String stringPosition = (String) constraints;
            RCPosition position = RCPosition.parse(stringPosition);
            if (!checkPosition(position)) throw new CalcLayoutException();
            if (map.get(position) != null)
                throw new CalcLayoutException("Component already exists.");
            map.put(comp, position);
        } else {
            throw new CalcLayoutException("Unable to add component!");
        }

    }

    private boolean checkPosition(RCPosition position) {
        if (position.getRow() < 1 || position.getRow() > ROWS || position.getColumn() < 1 || position.getColumn() > COLUMNS) {
            return false;
        } else return position.getRow() != 1 || position.getColumn() <= 1 || position.getColumn() >= 6;
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        map.remove(comp);
    }

    private Dimension calculateSize(Container parent, String size) {
        int h = 0, w = 0;
        int entryHeight = 0, entryWidth = 0;

        if (size.equalsIgnoreCase("maximum")) {
            for (Map.Entry<Component, RCPosition> entries : map.entrySet()) {
                entryHeight = entries.getKey().getMaximumSize().height;
                entryWidth = entries.getKey().getMaximumSize().width;

                if (entries.getValue().getColumn() == 1 && entries.getValue().getRow() == 1) {
                    entryWidth = entryWidth - 4 * space;
                    entryWidth /= 5;
                }

                if (entryHeight > h) h = entryHeight;

                if (entryWidth > w) w = entryWidth;

            }
        } else if (size.equalsIgnoreCase("preffered")) {
            for (Map.Entry<Component, RCPosition> entries : map.entrySet()) {
                entryHeight = entries.getKey().getPreferredSize().height;
                entryWidth = entries.getKey().getPreferredSize().width;

                if (entries.getValue().getColumn() == 1 && entries.getValue().getRow() == 1) {
                    entryWidth = entryWidth - 4 * space;
                    entryWidth /= 5;
                }

                if (entryHeight > h) h = entryHeight;

                if (entryWidth > w) w = entryWidth;
            }
        } else if (size.equalsIgnoreCase("minimum")) {
            for (Map.Entry<Component, RCPosition> entries : map.entrySet()) {
                entryHeight = entries.getKey().getMinimumSize().height;
                entryWidth = entries.getKey().getMinimumSize().width;

                if (entries.getValue().getColumn() == 1 && entries.getValue().getRow() == 1) {
                    entryWidth = entryWidth - 4 * space;
                    entryWidth /= 5;
                }

                if (entryHeight > h) h = entryHeight;

                if (entryWidth > w) w = entryWidth;
            }
        } else throw new CalcLayoutException("Invalid second argument");

        Insets ins = parent.getInsets();

        return new Dimension
                (ins.right + ins.left + w * COLUMNS + space * 6, ins.top + ins.bottom + h * ROWS + space * 4);
    }

    @Override
    public void layoutContainer(Container parent) {

        int h = map.keySet().stream().map(e -> e.getMaximumSize().height).max(Integer::compareTo).get();
        int w = map.keySet().stream().map(e -> e.getMaximumSize().width).max(Integer::compareTo).get();
        w *= parent.getWidth() * 1.0 / preferredLayoutSize(parent).getWidth();
        h *= parent.getHeight() * 1.0 / preferredLayoutSize(parent).getHeight();
        w += 2;
        h += 1;

        for (Map.Entry<Component, RCPosition> field : map.entrySet()) {
            Component component = field.getKey();
            RCPosition position = field.getValue();

            if (position.getRow() == 1 && position.getColumn() == 1) {
                component.setBounds(0, 0, 5 * w + 4 * space, h);
                continue;
            } else
                component.setBounds((w + space) * (position.getColumn() - 1),
                        (h + space) * (position.getRow() - 1), w, h);
        }

    }


    @Override
    public Dimension maximumLayoutSize(Container target) {
        return calculateSize(target, "maximum");
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return calculateSize(parent, "preffered");
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return calculateSize(parent, "minimum");
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        throw new UnsupportedOperationException("Wrong method, you shouldn't call this method!");
    }
}
