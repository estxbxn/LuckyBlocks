package fr.zebulon.stormlb.api.rewards.impl;

import fr.zebulon.stormlb.api.rewards.IReward;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Reward implements IReward {

    private final String id;
    private final int chance;
    private final List<String> commands;

    public Reward(String id, int chance, List<String> commands) {
        this.id = id;
        this.chance = chance;
        this.commands = commands;
    }

    @Override
    public void executeCommands(Player player, List<String> commands) {
        for (String command : commands) {
            command = command.replace("%player%", player.getName()).replace("%command%", command);

            // Command start with [PLAYER] the command will be executed by the player
            if (command.startsWith("[PLAYER]")) {
                Bukkit.dispatchCommand(player, command.replace("[PLAYER] ", ""));
                return;
            }

            // Command start with [CONSOLE] the command will be executed by the server
            if (command.startsWith("[CONSOLE]")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("[CONSOLE] ", ""));
                return;
            }

            // By default the command will be executed by the server
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("[CONSOLE] ", ""));
        }
    }

    public String getId() {
        return id;
    }

    @Override
    public int getChance() {
        return chance;
    }

    @Override
    public List<String> getCommands() {
        return commands;
    }
}