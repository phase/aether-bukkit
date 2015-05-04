package xyz.jadonfowler.aether;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import xyz.jadonfowler.aether.gen.IslandGenerator;
import xyz.jadonfowler.aether.listener.WorldListener;

@SuppressWarnings("deprecation")
public class Aether extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		WorldCreator gen = new WorldCreator("aether");
		gen.generator(new IslandGenerator(140, .1, 3457, true));
		Bukkit.createWorld(gen);
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(this, new WorldListener());
	}

	public static void log(String string) {
		System.out.println("Aether >> " + string);
	}

	public static boolean isInAether(Player p) {
		return p.getWorld().getName().equalsIgnoreCase("aether");
	}

	@EventHandler
	public void j(PlayerChatEvent e) {
		if (e.getMessage().startsWith("!aether")) {
			e.getPlayer()
					.teleport(Bukkit.getWorld("aether").getSpawnLocation());
			e.setCancelled(true);
		}
	}

}
