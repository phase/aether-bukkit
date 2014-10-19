package five.aether.main.gen;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class GrassPopulator extends BlockPopulator
{
  @SuppressWarnings("deprecation")
public void populate(World arg0, Random arg1, Chunk arg2)
  {
    for (int x = 0; x < 16; x++)
      for (int z = 0; z < 16; z++)
      {
        Block block = arg0.getHighestBlockAt(arg2.getX() * 16 + x, arg2.getZ() * 16 + z).getRelative(BlockFace.DOWN);
        if (block.getTypeId() != 0)
        {
          if (block.getType().equals(Material.LEAVES)) {
            while (!block.getType().equals(Material.STONE)) {
              block = block.getRelative(BlockFace.DOWN);
              if (block.getY() == 0) {
                return;
              }
            }
          }
          block.setType(Material.GRASS);

          double height = block.getY() - arg1.nextDouble() * 2.0D - 2.0D;
          if (height < 127.0D + arg1.nextDouble() * 2.0D) {
            height = 127.0D + arg1.nextDouble() * 2.0D;
          }
          while (block.getY() > height) {
            block = block.getRelative(BlockFace.DOWN);
            if (block.getTypeId() != 1) break;
            block.setType(Material.DIRT);
          }
        }
      }
  }
}