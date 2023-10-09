package fr.zebulon.stormlb.internal;

import fr.zebulon.stormlb.api.behaviors.IBehavior;
import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import fr.zebulon.stormlb.api.items.ICustomItem;
import fr.zebulon.stormlb.api.rewards.IReward;
import fr.zebulon.stormlb.internal.items.types.CustomRewardItem;
import fr.zebulon.stormlb.tools.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BehaviorImpl implements IBehavior {

    private final String id;
    private final ICustomItem item;
    private final ICustomBlock block;
    private List<IReward> rewards;

    public BehaviorImpl(String id, ICustomItem item, ICustomBlock block, List<IReward> rewards) {
        this.id = id;
        this.item = item;
        this.block = block;
        this.rewards = rewards;
    }

    @Override
    public void placeBehavior(Player player, ItemStack itemInHand, Block targetBlock, Location location) {
        // TODO: Faire la méthode place du behavior
        System.out.println("[StormLB] " + id + " behavior successfully placed");
    }

    @Override
    public void breakBehavior(Block targetBlock, Location location) {
        System.out.println("[StormLB] " + id + " behavior successfully broken");

        this.rewards = rewards.stream().filter(reward -> MathUtils.percentageLuck(((CustomRewardItem) reward.getRewardItem()).getChance())).collect(Collectors.toList());
        Collections.shuffle(rewards);

        Bukkit.broadcastMessage("REWARDS SHUFFLED : " + rewards);

        CustomRewardItem reward = (CustomRewardItem) rewards.get(0).getRewardItem();
        if(reward == null) return;

        location.getWorld().playSound(location, Sound.CHICKEN_EGG_POP, 1f, 1f);
        location.getWorld().dropItem(location.clone().add(0, 1, 0), reward.toItemStack());
        targetBlock.getDrops().clear();

        System.out.println("[StormLB] " + reward.getId() + " reward successfully obtained");
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ICustomItem getItem() {
        return item;
    }

    @Override
    public ICustomBlock getBlock() {
        return block;
    }

    @Override
    public List<IReward> getRewards() {
        return rewards;
    }
}
