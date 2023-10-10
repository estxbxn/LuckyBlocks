package fr.zebulon.stormlb.internal.blocks.types;

import fr.zebulon.stormlb.StormPlugin;
import fr.zebulon.stormlb.internal.blocks.CustomBlockImpl;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomHeadBlock extends CustomBlockImpl {

    private final String headId;

    public CustomHeadBlock(String headId) {
        super(Material.SKULL, (byte) 3);

        this.headId = headId;
    }

    public String getHeadId() {
        return headId;
    }

    @Override
    public ItemStack toItemStack() {
        return StormPlugin.getHdbAPI().getItemHead(headId);
    }
}
