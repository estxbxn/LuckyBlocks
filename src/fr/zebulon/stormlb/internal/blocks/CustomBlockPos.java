package fr.zebulon.stormlb.internal.blocks;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Objects;

public class CustomBlockPos {

    private final World world;
    private final int x, y, z;

    public CustomBlockPos(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CustomBlockPos(Location location) {
        this.world = location.getWorld();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public CustomBlockPos(Block block) {
        this.world = block.getWorld();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
    }

    public Chunk getChunk() {
        return world.getChunkAt(x, z);
    }

    public Block getBlock() {
        return world.getBlockAt(x, y, z);
    }

    public World getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Location toLocation() {
        return new Location(world, x, y, z);
    }

    public String toSerializableString() {
        return world.getName() + ":" + x + ":" + y + ":" + z;
    }

    public static CustomBlockPos fromSerializableString(String s) {
        String[] split = s.split(":");
        return new CustomBlockPos(Bukkit.getWorld(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomBlockPos blockPos = (CustomBlockPos) o;
        return x == blockPos.x && y == blockPos.y && z == blockPos.z && Objects.equals(world, blockPos.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(world, x, y, z);
    }
}