package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;

public class LJMenu extends JMenu {

    private ILocalizationProvider localizationProvider;

    private String key;

    public LJMenu(String key, ILocalizationProvider localizationProvider) {
        this.localizationProvider = localizationProvider;
        this.key = key;
        updateName();
        localizationProvider.addLocalizationListener(this::updateName);
    }

    private void updateName() {
        setText(localizationProvider.getString(key));
    }
}
