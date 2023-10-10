package fr.zebulon.stormlb.config;

import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import fr.zebulon.stormlb.api.config.IConfiguration;
import fr.zebulon.stormlb.api.items.ICustomItem;
import fr.zebulon.stormlb.api.items.impl.CustomEnchantment;
import fr.zebulon.stormlb.api.rewards.IReward;
import fr.zebulon.stormlb.api.rewards.impl.Reward;
import fr.zebulon.stormlb.internal.BehaviorImpl;
import fr.zebulon.stormlb.internal.blocks.CustomBlockImpl;
import fr.zebulon.stormlb.internal.blocks.types.CustomHeadBlock;
import fr.zebulon.stormlb.internal.blocks.types.CustomHeadTextureBlock;
import fr.zebulon.stormlb.internal.items.CustomItemImpl;
import fr.zebulon.stormlb.internal.items.types.CustomHeadItem;
import fr.zebulon.stormlb.internal.items.types.CustomHeadTextureItem;
import fr.zebulon.stormlb.internal.items.types.CustomRewardItem;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Configuration implements IConfiguration {

    private final StormPlugin plugin;

    public Configuration(StormPlugin plugin) {
        this.plugin = plugin;

        plugin.saveDefaultConfig();
    }

    @Override
    public void loadConfiguration() {
        ConfigurationSection behaviors = plugin.getConfig().getConfigurationSection("behaviors");
        if (behaviors == null) return;

        for (String behaviorId : behaviors.getKeys(false)) {
            ConfigurationSection behaviorSection = behaviors.getConfigurationSection(behaviorId);
            if (behaviorSection == null) continue;
            System.out.println("[StormLB] Loading behavior " + behaviorId);

            ICustomItem item = null;
            ICustomBlock block = null;
            List<IReward> rewards = new ArrayList<>();

            for (String type : behaviorSection.getKeys(false)) {
                ConfigurationSection behaviorTypeSection = behaviorSection.getConfigurationSection(type);
                if (behaviorTypeSection == null) continue;

                if (type.equalsIgnoreCase("item")) {
                    if (behaviorTypeSection.getString("type").equalsIgnoreCase("hdb")) {
                        item = new CustomHeadItem(
                                behaviorId,
                                behaviorTypeSection.getString("name"),
                                behaviorTypeSection.getStringList("lore"),
                                fromTypeEnchantments(behaviorTypeSection.getStringList("enchantments")),
                                fromTypeFlags(behaviorTypeSection.getStringList("flags")),
                                behaviorTypeSection.getString("hdb-id")
                        );
                    } else if (behaviorTypeSection.getString("type").equalsIgnoreCase("texture")) {
                        item = new CustomHeadTextureItem(
                                behaviorId,
                                behaviorTypeSection.getString("name"),
                                behaviorTypeSection.getStringList("lore"),
                                fromTypeEnchantments(behaviorTypeSection.getStringList("enchantments")),
                                fromTypeFlags(behaviorTypeSection.getStringList("flags")),
                                behaviorTypeSection.getString("value")
                        );
                    } else {
                        item = new CustomItemImpl(
                                behaviorId,
                                behaviorTypeSection.getString("name"),
                                Material.valueOf(behaviorTypeSection.getString("material")),
                                (byte) behaviorTypeSection.getInt("data"),
                                behaviorTypeSection.getStringList("lore"),
                                fromTypeEnchantments(behaviorTypeSection.getStringList("enchantments")),
                                fromTypeFlags(behaviorTypeSection.getStringList("flags"))
                        );
                    }
                }
                if (type.equalsIgnoreCase("block")) {
                    String blockType = behaviorTypeSection.getString("type");
                    if (blockType.equalsIgnoreCase("texture")) {
                        block = new CustomHeadTextureBlock(behaviorTypeSection.getString("value"));
                    } else if (blockType.equalsIgnoreCase("hdb")) {
                        block = new CustomHeadBlock(behaviorTypeSection.getString("hdb-id"));
                    } else {
                        block = new CustomBlockImpl(
                                Material.valueOf(behaviorTypeSection.getString("material")),
                                (byte) behaviorTypeSection.getInt("data")
                        );
                    }
                }
                if (type.equalsIgnoreCase("rewards")) {
                    for (String rewardId : behaviorTypeSection.getKeys(false)) {
                        ConfigurationSection rewardsSection = behaviorTypeSection.getConfigurationSection(rewardId);
                        if (rewardsSection == null) continue;

                        IReward reward = new Reward(
                                rewardId,
                                new CustomRewardItem(
                                        behaviorId,
                                        rewardsSection.getString("name"),
                                        Material.valueOf(rewardsSection.getString("material")),
                                        (byte) rewardsSection.getInt("data"),
                                        rewardsSection.getStringList("lore"),
                                        fromTypeEnchantments(rewardsSection.getStringList("enchantments")),
                                        fromTypeFlags(rewardsSection.getStringList("flags")),
                                        rewardsSection.getInt("chance")
                                ),
                                rewardsSection.getStringList("commands")
                        );

                        rewards.add(reward);
                    }
                }
            }

            plugin.getBehaviorManager().registerBehavior(
                    new BehaviorImpl(
                            behaviorId,
                            item,
                            block,
                            rewards
                    )
            );
        }

        System.out.println("[StormLB] Configuration loaded successfully");
    }

    private List<CustomEnchantment> fromTypeEnchantments(List<String> enchantmentsList) {
        List<CustomEnchantment> enchantments = new ArrayList<>();
        if (enchantmentsList == null) return enchantments;

        for (String enchantmentId : enchantmentsList) {
            String[] enchantment = enchantmentId.split(":");
            Enchantment enchant = Enchantment.getByName(enchantment[0]);
            int level = enchantment[1].isEmpty() ? 0 : Integer.parseInt(enchantment[1]);

            CustomEnchantment customEnchantment = new CustomEnchantment(
                    enchant,
                    level
            );
            enchantments.add(customEnchantment);
        }
        return enchantments;
    }

    private List<ItemFlag> fromTypeFlags(List<String> flagsList) {
        List<ItemFlag> flags = new ArrayList<>();
        if (flagsList == null) return flags;

        for (String flagId : flagsList) {
            flags.add(ItemFlag.valueOf(flagId));
        }
        return flags;
    }

    @Override
    public void reload() {
        try {
            plugin.saveConfig();
            plugin.getConfig().load(plugin.getDataFolder() + "/config.yml");
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        loadConfiguration();
        System.out.println("[StormLB] Configuration reloaded successfully");
    }
}
