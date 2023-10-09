package fr.zebulon.stormlb.api.items;

import org.bukkit.enchantments.Enchantment;

public class CustomEnchantment {

    private final Enchantment enchantment;
    private final int level;

    /**
     * Instantiates a new Custom enchantment.
     *
     * @param enchantment the enchantment
     * @param level       the level
     */
    public CustomEnchantment(Enchantment enchantment, int level) {
        this.enchantment = enchantment;
        this.level = level;
    }

    /**
     * Gets enchantment.
     *
     * @return the enchantment
     */
    public Enchantment getEnchantment() {
        return enchantment;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }
}
