package net.relic.battleroyal.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;

import net.relic.battleroyal.API;
import net.relic.battleroyal.battleroyal;

/**
 * @author Relic
 *
 */
public class PlayerEvent implements Listener{
	
	private API api;
	public PlayerEvent(API api){
		this.api = api;
	}
	
	
	@EventHandler
	public void onPingEvent(ServerListPingEvent e){
		if(battleroyal.game == null){
			e.setMotd(ChatColor.translateAlternateColorCodes('&', "&aJoin now!"));
		}else{
			e.setMotd(ChatColor.translateAlternateColorCodes('&', "&cGame in progress!"));
		}
		e.setMaxPlayers(-1337);
	}
	
	@EventHandler
	public void onLoginEvent(PlayerLoginEvent e){
		if(battleroyal.game != null){
			e.disallow(Result.KICK_OTHER, "Game in progress.");
		}
	}
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e){
		e.setJoinMessage("");
		api.getPlayerHandler().registerPlayer(e.getPlayer().getUniqueId());

	}

}
