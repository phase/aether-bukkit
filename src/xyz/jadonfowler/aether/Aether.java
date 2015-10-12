package xyz.jadonfowler.aether;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.*;
import xyz.jadonfowler.aether.gen.*;
import xyz.jadonfowler.aether.listener.*;

@SuppressWarnings("deprecation") public class Aether extends JavaPlugin implements Listener {

    @Override public void onEnable() {
        WorldCreator gen = new WorldCreator("aether");
        gen.generator(new IslandGenerator(140, .1, 3457, true));
        Bukkit.createWorld(gen);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
    }

    public static void log(String string) {
        System.out.println("Aether >> " + string);
    }

    public static boolean isInAether(Player p) {
        return p.getWorld().getName().equalsIgnoreCase("aether");
    }

    @EventHandler public void j(PlayerChatEvent e) {
        if (e.getMessage().startsWith("!aether")) {
            e.getPlayer().teleport(Bukkit.getWorld("aether").getSpawnLocation());
            e.setCancelled(true);
        }
    }
}
