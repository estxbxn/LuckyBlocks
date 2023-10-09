package fr.zebulon.stormlb.api.rewards;

import fr.zebulon.stormlb.api.items.ICustomItem;

public interface IReward {

    /**
     * Gets id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets reward item.
     *
     * @return the reward item
     */
    ICustomItem getRewardItem();
}
