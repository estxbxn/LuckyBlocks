package fr.zebulon.stormlb.internal.blocks.types;

import fr.zebulon.stormlb.internal.blocks.CustomBlockImpl;
import fr.zebulon.stormlb.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomHeadTextureBlock extends CustomBlockImpl {

    private final String value;

    public CustomHeadTextureBlock(String value) {
        super(Material.SKULL, (byte) 3);

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public ItemStack toItemStack() {
        return ItemBuilder.skull(value).build();
    }
}
