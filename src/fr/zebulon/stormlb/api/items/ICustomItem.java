package fr.zebulon.stormlb.api.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ICustomItem {

    /**
     * Gets id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

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
     * Gets lore.
     *
     * @return the lore
     */
    List<String> getLore();

    /**
     * Gets enchantments.
     *
     * @return the enchantments
     */
    List<CustomEnchantment> getEnchantments();

    /**
     * Gets flags.
     *
     * @return the flags
     */
    List<ItemFlag> getFlags();

    /**
     * To item stack item stack.
     *
     * @return the item stack
     */
    ItemStack toItemStack();
}
