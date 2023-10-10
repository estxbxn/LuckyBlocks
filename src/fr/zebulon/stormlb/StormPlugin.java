package fr.zebulon.stormlb;

import fr.zebulon.stormlb.api.IStormPlugin;
import fr.zebulon.stormlb.api.manager.IBehaviorManager;
import fr.zebulon.stormlb.commands.StormLBCommand;
import fr.zebulon.stormlb.config.Configuration;
import fr.zebulon.stormlb.listener.BehaviorListener;
import fr.zebulon.stormlb.manager.BehaviorManagerImpl;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.plugin.java.JavaPlugin;

public class StormPlugin extends JavaPlugin implements IStormPlugin {

    private static StormPlugin instance;
    private static HeadDatabaseAPI hdbAPI;

    private Configuration configuration;
    private IBehaviorManager behaviorManager;
    private BehaviorListener behaviorListener;

    @Override
    public void onEnable() {
        instance = this;
        hdbAPI = new HeadDatabaseAPI();

        // Managers
        this.behaviorManager = new BehaviorManagerImpl(this);

        // Configuration
        this.configuration = new Configuration(this);
        configuration.reload();

        // Listeners
        this.behaviorListener = new BehaviorListener(this);
        behaviorListener.registerListener();

        // Commands
        new StormLBCommand(this);

        System.out.println("[StormLB] " + getDescription().getName() + " v" + getVersion() + " is enabled!");
    }

    @Override
    public void onDisable() {
        behaviorManager.saveBehaviors();

        System.out.println("[StormLB] " + getDescription().getName() + " v" + getVersion() + " is disabled!");
    }

    public static StormPlugin get() {
        return instance;
    }

    public static HeadDatabaseAPI getHdbAPI() {
        return hdbAPI;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public BehaviorManagerImpl getBehaviorManager() {
        return (BehaviorManagerImpl) behaviorManager;
    }

    public BehaviorListener getBehaviorListener() {
        return behaviorListener;
    }

    @Override
    public String getAuthor() {
        return getDescription().getAuthors().get(0);
    }

    @Override
    public String getPluginName() {
        return getDescription().getName();
    }

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }
}
