package me.sistem21.itemprotection;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Protection {

	private UUID owner;
	private ItemStack item;
	
	public Protection(UUID owner, ItemStack item){
		this.owner = owner;
		this.item = item;
	}
	
	public Protection(Player owner, ItemStack item){
		this(owner.getUniqueId(), item);
	}
	
	public Protection(UUID owner, Item item){
		this(owner, item.getItemStack());
	}
	
	public Protection(Player owner, Item item){
		this(owner.getUniqueId(), item.getItemStack());
	}
	
	public UUID getOwner(){
		return owner;
	}
	
	public ItemStack getItemStack(){
		return item;
	}
	
	public Player getPlayerOwner(){
		return Bukkit.getPlayer(owner);
	}
}
