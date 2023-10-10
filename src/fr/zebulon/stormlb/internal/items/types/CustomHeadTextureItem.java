package fr.zebulon.stormlb.internal.items.types;

import fr.zebulon.stormlb.api.items.impl.CustomEnchantment;
import fr.zebulon.stormlb.internal.items.CustomItemImpl;
import fr.zebulon.stormlb.tools.ItemBuilder;
import fr.zebulon.stormlb.tools.NBTHelper;
import fr.zebulon.stormlb.tools.SkullCreator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomHeadTextureItem extends CustomItemImpl {

    private final String texture;

    public CustomHeadTextureItem(String id, String name, List<String> lore, List<CustomEnchantment> enchantments, List<ItemFlag> flags, String texture) {
        super(id, name, Material.SKULL_ITEM, (byte) 3, lore, enchantments, flags);

        this.texture = texture;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = ItemBuilder.skull(SkullCreator.itemFromBase64(texture))
                .setName(getName())
                .setLore(getLore())
                .addEnchantments(getEnchantments())
                .addItemFlags(getFlags())
                .build();

        return NBTHelper.setStringInNBTTag(itemStack, "id", getId());
    }
}
