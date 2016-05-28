package xyz.jadonfowler.aether;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messenger {

    public static void sendMessage(CommandSender sender, String message) {
        String m = ChatColor.WHITE + "[" + ChatColor.GREEN + "Aether" + ChatColor.WHITE + "] " + ChatColor.BLUE
                + message;
        sender.sendMessage(m);
    }
}