package fr.zebulon.stormlb.internal.blocks;

import fr.zebulon.stormlb.api.blocks.ICustomBlock;
import org.bukkit.Material;

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
}
