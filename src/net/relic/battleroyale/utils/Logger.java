package net.relic.battleroyale.utils;

import org.bukkit.ChatColor;

import net.relic.battleroyale.API;

/**
 * @author Relic
 *
 */
public class Logger {
	private API api;
	public Logger(API api){
		this.api = api;
	}
	
	public void info(String message){
		api.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cRoyal&8] &b[INFO] &f" + message));

	}
	
	public void warn(String message){
		api.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cRoyal&8] &6[WARN] &f" + message));
	}
	
	public void dang(String message){
		api.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&cRoyal&8] &c[DANG] &f" + message));

	}

}
