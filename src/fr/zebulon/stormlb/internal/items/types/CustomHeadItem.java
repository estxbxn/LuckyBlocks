package fr.zebulon.stormlb.internal.items.types;

import fr.zebulon.stormlb.api.items.CustomEnchantment;
import fr.zebulon.stormlb.tools.ItemBuilder;
import fr.zebulon.stormlb.internal.items.CustomItemImpl;
import fr.zebulon.stormlb.tools.NBTHelper;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomHeadItem extends CustomItemImpl {

    private final String headId;

    public CustomHeadItem(String id, String name, List<String> lore, List<CustomEnchantment> enchantments, List<ItemFlag> flags, String headId) {
        super(id, name, Material.SKULL_ITEM, (byte) 3, lore, enchantments, flags);

        this.headId = headId;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = ItemBuilder.skull(headId)
                .build();

        return NBTHelper.setStringInNBTTag(itemStack, "id", getId());
    }
}
