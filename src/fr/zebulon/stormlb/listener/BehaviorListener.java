package fr.zebulon.stormlb.listener;

import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.api.behaviors.IBehavior;
import fr.zebulon.stormlb.internal.blocks.CustomBlockPos;
import fr.zebulon.stormlb.tools.NBTHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BehaviorListener implements Listener {

    private final StormPlugin plugin;

    public BehaviorListener(StormPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBehaviorPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getInventory().getItemInHand();
        if (itemInHand == null || itemInHand.getType() == Material.AIR) return;

        Block targetBlock = event.getBlockPlaced();
        if (targetBlock == null) return;

        Location location = targetBlock.getLocation();
        if (location == null) return;

        boolean isBehaviorItem = NBTHelper.getStringInNBTTag(itemInHand, "id") != null;
        if (!isBehaviorItem) return;

        String behaviorId = NBTHelper.getStringInNBTTag(itemInHand, "id");
        IBehavior behavior = plugin.getBehaviorManager().getBehavior(behaviorId);
        if (behavior == null) return;

        CustomBlockPos blockPos = new CustomBlockPos(location);
        plugin.getBehaviorManager().getCustomBlocksCache().put(blockPos, behavior);
        behavior.placeBehavior(player, itemInHand, targetBlock, location);

        // TODO : Save dans un fichier temporaire "placedLuckyBlocks.yml" en cas de crash
    }

    @EventHandler
    public void onBehaviorBreak(BlockBreakEvent event) {
        Block targetBlock = event.getBlock();
        if (targetBlock == null) return;

        Location location = targetBlock.getLocation();
        if (location == null) return;

        CustomBlockPos blockPos = new CustomBlockPos(location);
        boolean isBehaviorBlock = plugin.getBehaviorManager().getCustomBlocksCache().get(blockPos) != null;
        if (!isBehaviorBlock) return;

        IBehavior behavior = plugin.getBehaviorManager().getCustomBlocksCache().get(blockPos);
        if (behavior == null) return;

        plugin.getBehaviorManager().getCustomBlocksCache().remove(blockPos);
        behavior.breakBehavior(targetBlock, location);

        // TODO : Remove dans le fichier temporaire "placedLuckyBlocks.yml" en cas de crash
    }

    private final Inventory menu = Bukkit.createInventory(null, 54, "Menu");

    public void openMenu(Player player) {
        int slot = 0;
        for (IBehavior behavior : plugin.getBehaviorManager().getBehaviorsCache().values()) {
            ItemStack itemStack = behavior.getItem().toItemStack();
            if (itemStack == null) continue;

            menu.setItem(slot, itemStack);
            slot++;
        }

        player.openInventory(menu);
    }

    @EventHandler
    public void menuListener(InventoryClickEvent event) {
        Inventory clickedInv = event.getClickedInventory();
        if (clickedInv == null) return;
        if (!clickedInv.getTitle().equalsIgnoreCase("Menu")) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        String behaviorId = NBTHelper.getStringInNBTTag(clickedItem, "id");
        if (behaviorId == null) return;

        IBehavior behavior = plugin.getBehaviorManager().getBehavior(behaviorId);
        if (behavior == null) return;

        player.getInventory().addItem(behavior.getItem().toItemStack());
        player.closeInventory();
    }

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        System.out.println("[StormLB] Registering BehaviorListener successfully!");
    }

    public StormPlugin get() {
        return plugin;
    }
}
