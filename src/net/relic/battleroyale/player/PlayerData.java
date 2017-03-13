package net.relic.battleroyale.player;

import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.relic.battleroyale.API;
import net.relic.battleroyale.tasks.GameTask;

/**
 * @author Relic
 *
 */
public class PlayerData {

	private API api;
	private UUID uuid;
	
	private BukkitRunnable task;
	
	public PlayerData(API api, UUID uuid){
		this.api = api;
		this.uuid = uuid;
	}
	
	public UUID getUUID(){
		return this.uuid;
	}
	
	public void sendMessage(String tag, String message){
		this.api.getServer().getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c" + tag + "&8] &7" + message));
	}
	
	public void clearChat(boolean silent){
		for(int i = 0; i < 100; i++){
			this.api.getServer().getPlayer(uuid).sendMessage("");
		}
		if(!silent){
			this.sendMessage("Royale", "You chat has been cleared.");

		}
	}
	
	public boolean isInGame(){
		return (task instanceof GameTask);
	}
	
	public BukkitRunnable getCurrentTask(){
		return this.task;
	}
	
	public void setTask(BukkitRunnable task){
		this.task = task;
	}
}
