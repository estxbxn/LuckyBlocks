package fr.zebulon.stormlb.internal.items.types;

import fr.zebulon.stormlb.api.items.CustomEnchantment;
import fr.zebulon.stormlb.internal.items.CustomItemImpl;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

public class CustomRewardItem extends CustomItemImpl {

    private final int chance;

    public CustomRewardItem(String id, String name, Material material, byte data, List<String> lore, List<CustomEnchantment> enchantments, List<ItemFlag> flags, int chance) {
        super(id, name, material, data, lore, enchantments, flags);

        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }
}
