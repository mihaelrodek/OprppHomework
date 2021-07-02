package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

public class AbstractLocalizationProvider implements ILocalizationProvider {

    private List<ILocalizationListener> listeners;


    public AbstractLocalizationProvider() {
        this.listeners = new ArrayList<>();

    }

    void fire() {
        for (ILocalizationListener l : listeners) {
            l.localizationChanged();
        }
    }

    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }

    @Override
    public String getString(String string) {
        return null;
    }
}
