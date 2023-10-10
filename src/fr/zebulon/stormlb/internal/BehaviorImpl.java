package fr.zebulon.stormlb.internal;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.api.behaviors.IBehavior;
import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import fr.zebulon.stormlb.api.items.ICustomItem;
import fr.zebulon.stormlb.api.rewards.IReward;
import fr.zebulon.stormlb.api.rewards.impl.Reward;
import fr.zebulon.stormlb.internal.blocks.types.CustomHeadBlock;
import fr.zebulon.stormlb.internal.blocks.types.CustomHeadTextureBlock;
import fr.zebulon.stormlb.tools.RandomCollections;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntitySkull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class BehaviorImpl implements IBehavior {

    private final String id;
    private final ICustomItem item;
    private final ICustomBlock block;
    private final List<IReward> rewards;
    private final RandomCollections<IReward> randomRewards;

    public BehaviorImpl(String id, ICustomItem item, ICustomBlock block, List<IReward> rewards) {
        this.id = id;
        this.item = item;
        this.block = block;
        this.rewards = rewards;

        this.randomRewards = new RandomCollections<>();
        for (IReward reward : rewards) {
            Reward rewardItem = (Reward) reward;
            randomRewards.add(rewardItem.getChance(), reward);
        }
    }

    @Override
    public void placeBehavior(Player player, ItemStack itemInHand, Block targetBlock, Location location) {
        if (block instanceof CustomHeadBlock) {
            CustomHeadBlock customHeadBlock = (CustomHeadBlock) block;
            targetBlock.setType(Material.SKULL);

            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", StormPlugin.getHdbAPI().getBase64(customHeadBlock.getHeadId())));

            TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) targetBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ()));
            skullTile.setGameProfile(profile);
            targetBlock.getState().update(true);

            return;

        } else if (block instanceof CustomHeadTextureBlock) {
            CustomHeadTextureBlock customHeadTextureBlock = (CustomHeadTextureBlock) block;
            targetBlock.setType(Material.SKULL);

            GameProfile profile = new GameProfile(UUID.randomUUID(), "");
            profile.getProperties().put("textures", new Property("textures", customHeadTextureBlock.getValue()));

            TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) targetBlock.getWorld()).getHandle().getTileEntity(new BlockPosition(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ()));
            skullTile.setGameProfile(profile);
            targetBlock.getState().update(true);

            return;
        }

        targetBlock.setType(block.getMaterial());
        targetBlock.setData(block.getData());
    }

    @Override
    public void breakBehavior(Player player, Block targetBlock, Location location) {
        Reward reward = (Reward) randomRewards.next();
        if (reward == null) return;

        location.getWorld().playSound(location, Sound.CHICKEN_EGG_POP, 1f, 1f);
        targetBlock.setType(Material.AIR);

        // Execute the reward commands
        reward.executeCommands(player, reward.getCommands());
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
