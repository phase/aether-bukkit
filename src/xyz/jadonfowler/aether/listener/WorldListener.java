package xyz.jadonfowler.aether.listener;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import xyz.jadonfowler.aether.*;

public class WorldListener implements Listener {

    @EventHandler public void fallInVoid(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (Aether.isInAether(p)) {
            if (e.getTo().getY() <= 10) {
                p.teleport(new Location(Bukkit.getWorld("world"), e.getTo().getX(), 315, e.getTo().getZ()));
            }
        }
    }

    @EventHandler public void eatCoal(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (Aether.isInAether(p)) {
            if (p.getFoodLevel() > 19) return;
            if (p.getItemInHand().getType() == Material.COAL) {
                ItemStack hand = p.getItemInHand();
                if (p.getItemInHand().getAmount() > 1)
                    p.setItemInHand(new ItemStack(hand.getType(), hand.getAmount() - 1));
                else p.setItemInHand(new ItemStack(Material.AIR, 1));
                p.getWorld().playSound(p.getLocation(), Sound.BURP, 3, 1);
                p.setFoodLevel(p.getFoodLevel() + 5);
            }
        }
    }

    @EventHandler public void mineZanite(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (Aether.isInAether(p)) {
            if (e.getBlock().getType() == Material.IRON_ORE) {
                e.setCancelled(true);
                e.getBlock().setType(Material.AIR);
                e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(),
                        new ItemStack(Material.IRON_INGOT, 1));
            }
        }
    }
}