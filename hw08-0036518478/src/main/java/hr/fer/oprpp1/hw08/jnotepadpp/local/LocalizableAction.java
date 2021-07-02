package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LocalizableAction extends AbstractAction {

    private ILocalizationListener listener;

    private String key;

    public LocalizableAction(String key, ILocalizationProvider lp) {

        listener = () -> putValue(NAME, lp.getString(key));
        putValue(NAME, lp.getString(key));
        putValue(Action.SHORT_DESCRIPTION, lp.getString(key));

        lp.addLocalizationListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
