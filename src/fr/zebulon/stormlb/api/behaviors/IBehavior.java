package fr.zebulon.stormlb.api.behaviors;

import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import fr.zebulon.stormlb.api.items.ICustomItem;
import fr.zebulon.stormlb.api.rewards.IReward;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IBehavior {

    /**
     * Place behavior.
     *
     * @param player      the player
     * @param itemInHand  the item in hand
     * @param targetBlock the target block
     * @param location    the location
     */
    void placeBehavior(Player player, ItemStack itemInHand, Block targetBlock, Location location);

    /**
     * Break behavior.
     *
     * @param player      the player
     * @param targetBlock the target block
     * @param location    the location
     */
    void breakBehavior(Player player, Block targetBlock, Location location);

    /**
     * Gets id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets item.
     *
     * @return the item
     */
    ICustomItem getItem();

    /**
     * Gets block.
     *
     * @return the block
     */
    ICustomBlock getBlock();

    /**
     * Gets rewards.
     *
     * @return the rewards
     */
    List<IReward> getRewards();
}
