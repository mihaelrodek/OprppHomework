package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.*;

public class LJLabel extends JLabel {

    private String key;
    private static ILocalizationProvider lp;

    public LJLabel(String key, ILocalizationProvider lp) {

        this.key = key;
        this.lp = lp;

        updateLabel();
        lp.addLocalizationListener(this::updateLabel);
    }

    void updateLabel() {
        LJLabel.this.setText(lp.getString(key));
    }
}
