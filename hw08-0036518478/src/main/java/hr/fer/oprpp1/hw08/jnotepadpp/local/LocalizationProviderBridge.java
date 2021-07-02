package hr.fer.oprpp1.hw08.jnotepadpp.local;

public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    private ILocalizationProvider provider;
    private ILocalizationListener listener;
    private boolean connected;

    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.provider = provider;
        this.listener = () -> LocalizationProviderBridge.this.fire();
    }

    void connect() {
        if (!connected) {
            provider.addLocalizationListener(listener);
            connected = true;
        }
    }

    void disconnect() {
        if (!connected) return;
        provider.removeLocalizationListener(listener);
        connected = false;
        System.exit(0);
    }

    @Override
    public String getString(String key) {
        return provider.getString(key);
    }
}
