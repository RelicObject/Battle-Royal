package net.relic.battleroyale.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;

import net.relic.battleroyale.API;
import net.relic.battleroyale.battleroyale;

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
		if(battleroyale.game == null){
			e.setMotd(ChatColor.translateAlternateColorCodes('&', "&7[&aOpen&7] &6" + (battleroyale.lobby.getMinPlayers() - api.getServer().getOnlinePlayers().size()) + " &7More Players &a| &7Arena: &a" + StringUtils.capitalise(battleroyale.lobby.getArena().getName())));
		}else{
			e.setMotd(ChatColor.translateAlternateColorCodes('&', "&8[&cClosed&8] &6" + this.api.getServer().getOnlinePlayers().size() + " &7Players left. &c| &7Arena: &c" + StringUtils.capitalise(battleroyale.lobby.getArena().getName())));
		}
		e.setMaxPlayers(-1337);
	}
	
	@EventHandler
	public void onLoginEvent(PlayerLoginEvent e){
		if(battleroyale.game != null){
			e.disallow(Result.KICK_OTHER, "Game in progress.");
		}
	}
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent e){
		e.setJoinMessage("");
		api.getPlayerHandler().registerPlayer(e.getPlayer().getUniqueId());
		if(battleroyale.game == null){
			battleroyale.lobby.playerCheck();
		}
	}

}
