package fr.zebulon.stormlb.manager;

import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.api.behaviors.IBehavior;
import fr.zebulon.stormlb.api.manager.IBehaviorManager;
import fr.zebulon.stormlb.internal.BehaviorImpl;
import fr.zebulon.stormlb.internal.blocks.CustomBlockPos;

import java.util.HashMap;
import java.util.Map;

public class BehaviorManagerImpl implements IBehaviorManager {

    private final StormPlugin plugin;
    private final Map<String, IBehavior> behaviorsCache;
    private final Map<CustomBlockPos, IBehavior> customBlocksCache;

    public BehaviorManagerImpl(StormPlugin plugin) {
        this.plugin = plugin;
        this.behaviorsCache = new HashMap<>();
        this.customBlocksCache = new HashMap<>();
    }

    @Override
    public void saveBehaviors() {
        System.out.println("[StormLB] Saved " + behaviorsCache.size() + " behaviors successfully");
    }

    @Override
    public void registerBehavior(IBehavior behavior) {
        behaviorsCache.put(behavior.getId(), behavior);
        System.out.println("[StormLB] " + behavior.getId() + " behavior successfully registered");
    }

    @Override
    public BehaviorImpl getBehavior(String id) {
        return (BehaviorImpl) behaviorsCache.get(id);
    }

    public Map<String, IBehavior> getBehaviorsCache() {
        return behaviorsCache;
    }

    public Map<CustomBlockPos, IBehavior> getCustomBlocksCache() {
        return customBlocksCache;
    }

    public StormPlugin getPlugin() {
        return plugin;
    }
}
