package fr.zebulon.stormlb.api;

public interface IStormPlugin {

    /**
     * Gets the author.
     *
     * @return the author
     */
    String getAuthor();

    /**
     * Gets the plugin name.
     *
     * @return the plugin name
     */
    String getPluginName();

    /**
     * Gets the version of the plugin.
     *
     * @return the version
     */
    String getVersion();
}
