package fr.zebulon.stormlb.internal.blocks.types;

import fr.zebulon.stormlb.internal.blocks.CustomBlockImpl;
import org.bukkit.Material;

public class CustomHeadTextureBlock extends CustomBlockImpl {

    private final String value;

    public CustomHeadTextureBlock(String value) {
        super(Material.SKULL, (byte) 3);

        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
