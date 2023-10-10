package fr.zebulon.stormlb.api.behaviors.impl;

import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import fr.zebulon.stormlb.api.rewards.IReward;
import fr.zebulon.stormlb.api.rewards.impl.Reward;
import fr.zebulon.stormlb.internal.BehaviorImpl;
import fr.zebulon.stormlb.internal.blocks.CustomBlockImpl;
import fr.zebulon.stormlb.internal.items.CustomItemImpl;
import fr.zebulon.stormlb.internal.items.types.CustomRewardItem;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class DefaultBehavior extends BehaviorImpl {

    private static String id;

    public DefaultBehavior(String id) {
        super(id, getDefaultItem(), getDefaultBlock(), getDefaultRewards());

        DefaultBehavior.id = id;
    }

    private static String getDefaultId() {
        return id;
    }

    private static CustomItemImpl getDefaultItem() {
        return new CustomItemImpl(
                getDefaultId(),
                "Default Item",
                Material.DIAMOND_BOOTS,
                (byte) 0,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    private static ICustomBlock getDefaultBlock() {
        return new CustomBlockImpl(
                Material.DIAMOND_BLOCK,
                (byte) 0
        );
    }

    private static List<IReward> getDefaultRewards() {
        List<IReward> rewards = new ArrayList<>();
        rewards.add(
                new Reward(
                        "default-reward",
                        new CustomRewardItem(
                                "default-reward",
                                "Default Reward",
                                Material.DIAMOND_SWORD,
                                (byte) 0,
                                new ArrayList<>(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                10
                        ),
                        new ArrayList<>()
                )
        );
        return rewards;
    }
}
