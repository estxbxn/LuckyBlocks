package fr.zebulon.stormlb.internal.blocks.types;

import fr.zebulon.stormlb.internal.blocks.CustomBlockImpl;
import org.bukkit.Material;

public class CustomHeadBlock extends CustomBlockImpl {

    private final String headId;

    public CustomHeadBlock(String headId) {
        super(Material.SKULL, (byte) 3);

        this.headId = headId;
    }

    public String getHeadId() {
        return headId;
    }
}
