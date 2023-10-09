package fr.zebulon.stormlb.api.blocks;

import org.bukkit.Material;

public interface ICustomBlock {

    /**
     * Gets material.
     *
     * @return the material
     */
    Material getMaterial();

    /**
     * Gets data.
     *
     * @return the data
     */
    byte getData();
}
