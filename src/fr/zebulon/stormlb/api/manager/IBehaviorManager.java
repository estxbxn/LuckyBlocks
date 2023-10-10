package fr.zebulon.stormlb.api.manager;

import fr.zebulon.stormlb.api.behaviors.IBehavior;

public interface IBehaviorManager {

    /**
     * Save behaviors.
     */
    void saveBehaviors();

    /**
     * Register behavior.
     *
     * @param behavior the behavior
     */
    void registerBehavior(IBehavior behavior);

    /**
     * Gets behavior.
     *
     * @param id the id
     * @return the behavior
     */
    IBehavior getBehavior(String id);
}
