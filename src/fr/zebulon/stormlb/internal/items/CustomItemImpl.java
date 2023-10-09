package fr.zebulon.stormlb.internal.items;

import fr.zebulon.stormlb.api.items.CustomEnchantment;
import fr.zebulon.stormlb.api.items.ICustomItem;
import fr.zebulon.stormlb.tools.ItemBuilder;
import fr.zebulon.stormlb.tools.NBTHelper;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomItemImpl implements ICustomItem {

    private final String id;
    private final String name;
    private final Material material;
    private final byte data;
    private final List<String> lore;
    private final List<CustomEnchantment> enchantments;
    private final List<ItemFlag> flags;

    public CustomItemImpl(String id, String name, Material material, byte data, List<String> lore, List<CustomEnchantment> enchantments, List<ItemFlag> flags) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.data = data;
        this.lore = lore;
        this.enchantments = enchantments;
        this.flags = flags;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public byte getData() {
        return data;
    }

    @Override
    public List<String> getLore() {
        return lore;
    }

    @Override
    public List<CustomEnchantment> getEnchantments() {
        return enchantments;
    }

    @Override
    public List<ItemFlag> getFlags() {
        return flags;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = ItemBuilder.item(material, 1, data)
                .setName(name)
                .setLore(lore)
                .addEnchantments(enchantments)
                .addItemFlags(flags)
                .build();

        return NBTHelper.setStringInNBTTag(itemStack, "id", getId());
    }
}
