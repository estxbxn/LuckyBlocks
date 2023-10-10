package fr.zebulon.stormlb.internal.blocks;

import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import fr.zebulon.stormlb.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomBlockImpl implements ICustomBlock {

    private final Material material;
    private final byte data;

    public CustomBlockImpl(Material material, byte data) {
        this.material = material;
        this.data = data;
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
    public ItemStack toItemStack() {
        return ItemBuilder.item(material, 1, data).build();
    }
}
