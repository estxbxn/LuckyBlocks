package fr.zebulon.stormlb.api.config;

public interface IConfiguration {

    /**
     * Load configuration.
     */
    void loadConfiguration();

    /**
     * Reload.
     */
    void reload();

    /**
     * Save.
     */
    void save();
}
