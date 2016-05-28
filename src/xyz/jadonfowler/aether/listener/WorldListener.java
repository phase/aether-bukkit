package xyz.jadonfowler.aether.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import xyz.jadonfowler.aether.Aether;

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
            ItemStack hand = p.getInventory().getItemInMainHand();
            if (hand.getType() == Material.COAL) {
                if (hand.getAmount() > 1)
                    p.getInventory().setItemInMainHand(new ItemStack(hand.getType(), hand.getAmount() - 1));
                else p.getInventory().setItemInMainHand(new ItemStack(Material.AIR, 1));
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_CHICKEN_EGG, 3, 1);
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