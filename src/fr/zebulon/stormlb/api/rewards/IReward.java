package fr.zebulon.stormlb.api.rewards;

import org.bukkit.entity.Player;

import java.util.List;

public interface IReward {

    /**
     * Gets id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets chance.
     *
     * @return the chance
     */
    int getChance();

    /**
     * Gets commands.
     *
     * @return the commands
     */
    List<String> getCommands();

    /**
     * Execute commands.
     *
     * @param player   the player
     * @param commands the commands
     */
    void executeCommands(Player player, List<String> commands);
}
