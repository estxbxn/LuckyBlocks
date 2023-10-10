package fr.zebulon.stormlb.api.blocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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

    /**
     * To item stack item stack.
     *
     * @return the item stack
     */
    ItemStack toItemStack();
}
