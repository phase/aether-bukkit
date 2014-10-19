package five.aether.main.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class IslandGenerator extends ChunkGenerator {
	double scale;
	double threshold;
	long seed;
	boolean moreTrees;

	@SuppressWarnings("deprecation")
	void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
		if (chunk[(y >> 4)] == null)
			chunk[(y >> 4)] = new byte[4096];
		if ((y > 256) || (y < 0) || (x > 16) || (x < 0) || (z > 16) || (z < 0))
			return;
		try {
			chunk[(y >> 4)][((y & 0xF) << 8 | z << 4 | x)] = ((byte) material
					.getId());
		} catch (Exception localException) {
		}
	}

	public IslandGenerator(double scale, double threshold, long seed,
			boolean moreTrees) {
		this.scale = scale;
		this.threshold = threshold;
		this.seed = seed;
		this.moreTrees = moreTrees;
	}

	public byte[][] generateBlockSections(World world, Random rand, int ChunkX,
			int ChunkZ, ChunkGenerator.BiomeGrid biome) {
		byte[][] chunk = new byte[world.getMaxHeight() / 16][];

		SimplexOctaveGenerator islands = new SimplexOctaveGenerator(this.seed,
				8);
		PerlinOctaveGenerator roughMap = new PerlinOctaveGenerator(this.seed, 8);
		SimplexOctaveGenerator bottum = new SimplexOctaveGenerator(this.seed, 8);

		islands.setScale(1.0D / this.scale);

		roughMap.setScale(0.0625D);
		bottum.setScale(0.25D);

		double ratio = 1.0D - 1.0D / (this.scale / 64.0D * 5.0D);

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int realX = x + ChunkX * 16;
				int realZ = z + ChunkZ * 16;
				double frequency = 0.5D;
				double amplitude = 0.5D;

				double thickness = (islands.noise(realX, realZ, frequency,
						amplitude) - this.threshold)
						/ (1.0D - this.threshold)
						* this.scale;

				if (thickness > 0.0D) {
					double bottomThick = thickness / 8.0D;

					thickness = (Math.pow(ratio, thickness) - 1.0D)
							/ (ratio - 1.0D);

					double roughness = (roughMap.noise(realX, realZ, frequency,
							amplitude) + 1.0D) / 2.0D * 1.5D;

					double height = 128.0D;

					for (int y = (int) height; y < height + thickness
							* roughness; y++) {
						setBlock(x, y, z, chunk, Material.STONE);
					}

					bottomThick = (bottomThick + thickness / 2.0D) / 2.0D;

					double bottomRough = (bottum.noise(realX, realZ, frequency,
							amplitude) - 1.0D) / 2.0D;

					bottomRough *= thickness;

					for (int y = (int) height; y > height - bottomThick
							+ bottomRough; y--) {
						setBlock(x, y, z, chunk, Material.STONE);
					}
				}
			}
		}
		return chunk;
	}

	public List<BlockPopulator> getDefaultPopulators(World world) {
		List<BlockPopulator> pops = new ArrayList<BlockPopulator>();
		pops.add(new GrassPopulator());
		pops.add(new TreePopulator(this.seed, this.moreTrees));
		pops.add(new OrePopulator());
		return pops;
	}

	public boolean canSpawn(World world, int x, int z) {
		if (world.getHighestBlockYAt(x, z) <= 0) {
			return false;
		}
		return true;
	}

	public Location getFixedSpawnLocation(World world, Random random) {
		return getNearestSpawn(world, 0.0D, 0.0D);
	}

	public Location getNearestSpawn(World world, double x, double z) {
		SimplexOctaveGenerator islands = new SimplexOctaveGenerator(world, 8);
		islands.setScale(1.0D / this.scale);

		double thickness = (islands.noise(x, 0.0D, 0.5D, 0.5D) - this.threshold)
				/ (1.0D - this.threshold);
		double prevThick = thickness;
		if (thickness <= 0.0D) {
			while ((thickness <= 0.0D) || (prevThick <= thickness)) {
				x += 1.0D;
				prevThick = thickness;
				thickness = islands.noise(x, z, 0.5D, 0.5D) * 16.0D - 8.0D;
			}
		}

		int y = world.getHighestBlockYAt((int) x, (int) z);
		if (y < 128) {
			y = (int) (130.0D + thickness);
		}

		return new Location(world, x, y, z);
	}
}
