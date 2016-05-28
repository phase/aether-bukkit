package xyz.jadonfowler.aether;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.jadonfowler.aether.gen.IslandGenerator;
import xyz.jadonfowler.aether.listener.WorldListener;

public class Aether extends JavaPlugin implements Listener {

    @Override public void onEnable() {
        WorldCreator gen = new WorldCreator("aether");
        gen.generator(new IslandGenerator(140, .1, 3457, true));
        Bukkit.createWorld(gen);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new WorldListener(), this);
    }

    public static boolean isInAether(Player p) {
        return p.getWorld().getName().equalsIgnoreCase("aether");
    }

    @Override public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.toString().equalsIgnoreCase("aether")) {
            if (args[0].equalsIgnoreCase("join")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (!isInAether(p))
                        p.teleport(Bukkit.getWorld("aether").getSpawnLocation());
                    else Messenger.sendMessage(p, "You are already in the Aether.");
                }
                else {
                    Messenger.sendMessage(sender, "Only Players can join the Aether.");
                }
            }
            else if (args[0].equalsIgnoreCase("leave")) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (isInAether(p))
                        p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                    else Messenger.sendMessage(p, "You are not in the Aether.");
                }
                else {
                    Messenger.sendMessage(sender, "Only Players can leave the Aether.");
                }
            }
        }
        return false;
    }
}