package me.sistem21.IceFightPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class IceFightPlayer extends JavaPlugin implements Listener {

	private List<Protection> protections = new ArrayList<>();

	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
	}

	@EventHandler
	public void on(PlayerDeathEvent e) {
		Player dead = e.getEntity();

		if (dead.getKiller() != null) {
			if (dead.getKiller() instanceof Player) {
				Player killer = dead.getKiller();
				for (ItemStack item : e.getDrops()) {
					Protection killerProt = new Protection(killer, item);

					protections.add(killerProt);
				}
			}
		}
	}

	@EventHandler
	public void on(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem().getItemStack();

		for (Iterator<Protection> iterator = protections.iterator(); iterator.hasNext();) {
			Protection prot = iterator.next();
			if (prot.getPlayerOwner() != null) {
				if (prot.getItemStack().equals(item)) {
					if (!prot.getOwner().equals(p.getUniqueId())) {
						e.setCancelled(true);
						p.setVelocity(p.getLocation().getDirection().multiply(-0.5));
						p.sendMessage(getConfig().getString("pickup-not-allowed").replaceAll("&", "§").replaceAll("%player", p.getName())
								.replaceAll("%owner", prot.getPlayerOwner().getName()));
					} else {
						e.setCancelled(false);
						iterator.remove();
					}
				}
			}else{
				iterator.remove();
			}
		}
	}
}
