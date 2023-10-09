package fr.zebulon.stormlb.manager;

import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.api.behaviors.IBehavior;
import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import fr.zebulon.stormlb.api.items.CustomEnchantment;
import fr.zebulon.stormlb.api.items.ICustomItem;
import fr.zebulon.stormlb.api.manager.IBehaviorManager;
import fr.zebulon.stormlb.api.rewards.IReward;
import fr.zebulon.stormlb.internal.BehaviorImpl;
import fr.zebulon.stormlb.internal.blocks.CustomBlockPos;
import fr.zebulon.stormlb.internal.items.types.CustomRewardItem;
import fr.zebulon.stormlb.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public void loadBehaviors() {
        // TODO : Charger depuis le fichier temporaire "placedLuckyBlocks.yml"
        System.out.println("[StormLB] Registered " + behaviorsCache.size() + " behaviors successfully");
    }

    @Override
    public void saveBehaviors() {
        // TODO : Sauvegarder le fichier temporaire "placedLuckyBlocks.yml"
        System.out.println("[StormLB] Saved " + behaviorsCache.size() + " behaviors successfully");
    }

    @Override
    public void registerBehavior(IBehavior behavior) {
        behaviorsCache.put(behavior.getId(), behavior);
        System.out.println("[StormLB] " + behavior.getId() + " behavior successfully registered");
    }

    public void createBehavior(String id) {
        ICustomItem customItem = new ICustomItem() {
            @Override
            public String getId() {
                return "example";
            }

            @Override
            public String getName() {
                return "&dExample";
            }

            @Override
            public Material getMaterial() {
                return Material.DIAMOND;
            }

            @Override
            public byte getData() {
                return 0;
            }

            @Override
            public List<String> getLore() {
                return new ArrayList<>();
            }

            @Override
            public List<CustomEnchantment> getEnchantments() {
                return new ArrayList<>();
            }

            @Override
            public List<ItemFlag> getFlags() {
                return new ArrayList<>();
            }

            @Override
            public ItemStack toItemStack() {
                return ItemBuilder.item(getMaterial())
                        .setName(getName())
                        .setData(getData())
                        .setLore(getLore())
                        .addEnchantments(getEnchantments())
                        .addItemFlags(getFlags())
                        .build();
            }
        };

        ICustomBlock customBlock = new ICustomBlock() {
            @Override
            public Material getMaterial() {
                return Material.STAINED_CLAY;
            }

            @Override
            public byte getData() {
                return 10;
            }
        };

        List<IReward> rewards = new ArrayList<>();
        rewards.add(
                new IReward() {
                    @Override
                    public String getId() {
                        return "example-reward";
                    }

                    @Override
                    public ICustomItem getRewardItem() {
                        return new CustomRewardItem(
                                "example",
                                "&dExample",
                                Material.DIAMOND,
                                (byte) 0,
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                10
                        );
                    }
                }
        );

        plugin.getConfig().createSection("behaviors." + id);
        plugin.getConfig().set("behaviors." + id + ".item", customItem);
        plugin.getConfig().set("behaviors." + id + ".block", customBlock);
        plugin.getConfig().set("behaviors." + id + ".rewards", rewards);
        plugin.getConfiguration().reload();

        registerBehavior(new BehaviorImpl(id, customItem, customBlock, rewards));
        System.out.println("[StormLB] " + id + " behavior successfully created");
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
