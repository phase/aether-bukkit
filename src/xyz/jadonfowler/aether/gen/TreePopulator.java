package xyz.jadonfowler.aether.gen;

import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.generator.*;

public class TreePopulator extends BlockPopulator {

    private Random random;
    private boolean moreTrees;

    public TreePopulator(long seed, boolean moreTrees) {
        this.random = new Random(seed);
        this.moreTrees = moreTrees;
    }

    public void populate(World world, Random r, Chunk chunk) {
        if ((!chunk.getBlock(0, 0, 0).getBiome().equals(Biome.FOREST))
                && (!chunk.getBlock(0, 0, 0).getBiome().equals(Biome.FOREST_HILLS)) && (!this.moreTrees)
                && (this.random.nextDouble() > 0.04D)) { return; }
        int x = this.random.nextInt(16) + chunk.getX() * 16;
        int z = this.random.nextInt(16) + chunk.getZ() * 16;
        Location loc = new Location(world, x, world.getHighestBlockYAt(x, z), z);
        if (loc.getY() <= 1.0D) { return; }
        TreeType[] types = { TreeType.TREE, TreeType.BIRCH, TreeType.BIG_TREE, TreeType.TALL_REDWOOD };
        double chance = 0.7D;
        for (TreeType type : types) {
            if (this.random.nextDouble() < chance) {
                if (type != TreeType.TALL_REDWOOD) {
                    world.generateTree(loc, type);
                    return;
                }
                else {
                    Location base = loc.clone();
                    for (int y = 0; y < 7; y++) {
                        base.clone().add(0, y, 0).getBlock().setType(Material.LOG);
                    }
                    for (int x1 = -1; x1 < 2; x1++)
                        for (int z1 = -1; z1 < 2; z1++)
                            for (int y = -1; y < 3; y++) {
                                Block b = base.clone().add(x1, y + 5, z1).getBlock();
                                if (b.getType() != Material.LOG) b.setType(Material.LEAVES);
                            }
                }
            }
            chance *= 0.5D;
        }
    }
}