package xyz.jadonfowler.aether.gen;

import java.util.*;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.generator.*;

public class OrePopulator extends BlockPopulator {

    @Override public void populate(World w, Random r, Chunk c) {
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                for (int y = 0; y < 256; y++) {
                    Location l = c.getBlock(x, y, z).getLocation();
                    c.getBlock(x, y, z).setBiome(Biome.EXTREME_HILLS);
                    if (r.nextDouble() > 0.99) spawnOreVain(l);
                }
    }

    private void spawnOreVain(Location l) {
        Block b = l.getBlock();
        if (b.getType() == Material.STONE) {
            b.setType(getOreType());
            for (int x = -1; x < 2; x++)
                for (int z = -1; z < 2; z++)
                    for (int y = -1; y < 2; y++)
                        if (l.clone().add(x, y, z).getBlock().getType() == Material.STONE) {
                            l.clone().add(x, y, z).getBlock().setType(b.getType());
                        }
        }
    }

    private Material getOreType() {
        Random r = new Random();
        int i = r.nextInt(4) + 1;
        switch (i) {
        default:
            return Material.COAL_ORE;
        case 1:
            return Material.COAL_ORE;
        case 2:
            return Material.IRON_ORE;
        case 3:
            return Material.DIAMOND_ORE;
        case 4:
            return Material.GOLD_ORE;
        }
    }
}
