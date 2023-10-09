package fr.zebulon.stormlb.commands;

import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.api.items.ICustomItem;
import fr.zebulon.stormlb.api.rewards.IReward;
import fr.zebulon.stormlb.internal.BehaviorImpl;
import fr.zebulon.stormlb.internal.items.types.CustomRewardItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class StormLBCommand implements CommandExecutor {

    private final StormPlugin plugin;

    public StormLBCommand(StormPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("stormlb").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (args.length == 0) return false;

        if (args.length == 1 && args[0].equalsIgnoreCase("debug")) {
            if (!player.hasPermission("stormlb.lucky.debug")) return false;

            BehaviorImpl behavior = plugin.getBehaviorManager().getBehavior("rare");
            if (behavior == null) return false;
            Bukkit.broadcastMessage(behavior.getId());

            ICustomItem item = behavior.getItem();
            if (item == null) return false;
            Bukkit.broadcastMessage(item.getName());

            ItemStack itemStack = item.toItemStack();
            if (itemStack == null) return false;
            Bukkit.broadcastMessage(itemStack.toString());

            player.getInventory().addItem(itemStack);
            return true;

        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("stormlb.lucky.reload")) return false;

            plugin.getConfiguration().reload();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLe plugin a été rechargé avec succès !"));
            return true;

        } else if (args.length == 1 && args[0].equalsIgnoreCase("menu")) {
            if (!player.hasPermission("stormlb.lucky.menu")) return false;

            plugin.getBehaviorListener().openMenu(player);
            return true;

        } else if (args.length == 2 && args[0].equalsIgnoreCase("create")
                && args[1] != null) {
            if (!player.hasPermission("stormlb.lucky.create")) return false;

            plugin.getBehaviorManager().createBehavior(args[1]);
            player.sendMessage(plugin.getBehaviorManager().getBehavior(args[1]).getId());
            return true;

        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")
                && (Integer.parseInt(args[1]) > 0 || Integer.parseInt(args[1]) < 100)
                && plugin.getBehaviorManager().getBehavior(args[2]) != null) {
            if (!player.hasPermission("stormlb.lucky.add")) return false;

            ItemStack item = player.getItemInHand();
            if (item == null || !item.hasItemMeta()) return false;

            BehaviorImpl behavior = plugin.getBehaviorManager().getBehavior(args[2]);
            if (behavior == null) return false;

            behavior.getRewards().add(
                    new IReward() {
                        @Override
                        public String getId() {
                            return item.getType().name().toLowerCase() + "-reward";
                        }

                        @Override
                        public ICustomItem getRewardItem() {
                            return new CustomRewardItem(
                                    behavior.getId(),
                                    item.getItemMeta().getDisplayName(),
                                    item.getType(),
                                    (byte) item.getDurability(),
                                    item.getItemMeta().getLore(),
                                    new ArrayList<>(),
                                    new ArrayList<>(),
                                    Integer.parseInt(args[1])
                            );
                        }
                    }
            );

        } else if (args.length == 4 && args[0].equalsIgnoreCase("give")
                && Bukkit.getPlayer(args[1]) != null
                && plugin.getBehaviorManager().getBehavior(args[2]) != null
                && Integer.parseInt(args[3]) > 0) {
            if (!player.hasPermission("stormlb.lucky.give")) return false;

            Player targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) return false;

            BehaviorImpl behavior = plugin.getBehaviorManager().getBehavior(args[2]);
            if (behavior == null) return false;

            targetPlayer.getInventory().addItem(behavior.getItem().toItemStack());
        }

        return false;
    }
}
