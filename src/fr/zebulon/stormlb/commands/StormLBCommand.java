package fr.zebulon.stormlb.commands;

import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.internal.BehaviorImpl;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StormLBCommand implements CommandExecutor {

    private final StormPlugin plugin;

    public StormLBCommand(StormPlugin plugin) {
        this.plugin = plugin;

        plugin.getCommand("stormlb").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) return false;

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("stormlb.lucky.reload")) return false;

            plugin.getConfiguration().reload();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aLe plugin a été rechargé avec succès !"));
            return true;

        } else if (args.length == 1 && args[0].equalsIgnoreCase("menu")) {
            Player player = (Player) sender;
            if (!player.hasPermission("stormlb.lucky.menu")) return false;

            plugin.getBehaviorListener().openMenu(player);
            return true;

        } else if (args.length == 4 && args[0].equalsIgnoreCase("give")
                && Bukkit.getPlayer(args[1]) != null
                && plugin.getBehaviorManager().getBehavior(args[2]) != null
                && Integer.parseInt(args[3]) > 0) {
            if (!sender.hasPermission("stormlb.lucky.give")) return false;

            Player targetPlayer = Bukkit.getPlayer(args[1]);
            if (targetPlayer == null) return false;

            BehaviorImpl behavior = plugin.getBehaviorManager().getBehavior(args[2]);
            if (behavior == null) return false;

            ItemStack itemStack = behavior.getItem().toItemStack();
            if (itemStack == null) return false;

            itemStack.setAmount(Integer.parseInt(args[3]));
            targetPlayer.getInventory().addItem(itemStack);
            return true;
        }
        return false;
    }
}
