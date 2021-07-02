package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class PrimListModel implements ListModel<Integer> {

    /**
     * List in which prime number will be stored.
     */
    private List<Integer> elements = new ArrayList<>();

    /**
     * List of listeners.
     */
    private List<ListDataListener> listeners = new ArrayList<>();

    /**
     * Last generated prime.
     */
    private int currentPrime = 3;

    /**
     * Basic constructor.
     */
    public PrimListModel() {
        elements.add(2);
    }

    @Override
    public int getSize() {
        return elements.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return elements.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * This method is used for generating next prime number.
     */
    public void next() {

        for (int i = 2; i <= currentPrime/2; i++) {
            if (currentPrime % i == 0) {
                currentPrime++;
                i = 2;
            }
        }
        elements.add(currentPrime++);

        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
        notifyObservers(event);
    }

    /**
     * This method is used for notifying all observers.
     *
     * @param event {@link ListDataEvent}
     */
    private void notifyObservers(ListDataEvent event) {
        for (ListDataListener l : listeners) l.intervalAdded(event);
    }


}
