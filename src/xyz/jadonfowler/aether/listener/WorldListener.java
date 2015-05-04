package xyz.jadonfowler.aether.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WorldListener implements Listener {

	@EventHandler
	public void EatCoal(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (isInAether(p)) {
			if (p.getFoodLevel() > 19)
				return;
			if (p.getItemInHand().getType() == Material.COAL) {
				if (p.getItemInHand().getAmount() > 2)
					p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
				else
					p.getItemInHand().setType(Material.AIR);
				p.getWorld().playSound(p.getLocation(), Sound.BURP, 3, 1);
				p.setFoodLevel(p.getFoodLevel() + 5);
			}
		}
	}

	@EventHandler
	public void MineZanite(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (isInAether(p)) {
			if (e.getBlock().getType() == Material.IRON_ORE) {
				e.setCancelled(true);
				e.getBlock().setType(Material.AIR);
				e.getBlock().getLocation().getWorld()
					.dropItemNaturally(e.getBlock().getLocation(),
						new ItemStack(Material.IRON_INGOT, 1));
			}
		}
	}
}