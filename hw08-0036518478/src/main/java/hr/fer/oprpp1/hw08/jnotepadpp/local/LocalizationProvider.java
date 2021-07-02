package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationProvider extends AbstractLocalizationProvider {

    private String language;
    private static LocalizationProvider providerInstance;
    private ResourceBundle bundle;

    private LocalizationProvider() {
        this.language = "en";
        this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.vjezba.prijevodi", Locale.forLanguageTag(language));
    }

    public static LocalizationProvider getInstance() {
        if (providerInstance == null)
            providerInstance = new LocalizationProvider();
        return providerInstance;
    }

    public void setLanguage(String language) {
          if (language.equals(this.language)) return;
        this.language = language;
        this.bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.vjezba.prijevodi", Locale.forLanguageTag(this.language));
        fire();
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

}
