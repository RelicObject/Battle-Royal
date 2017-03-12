package net.relic.battleroyal;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.relic.battleroyal.arena.Arena;
import net.relic.battleroyal.listeners.PlayerEvent;
import net.relic.battleroyal.tasks.GameTask;
import net.relic.battleroyal.tasks.LobbyTask;

/**
 * @author Relic
 *
 */
public class battleroyal extends JavaPlugin implements Listener{
	
	private API api;
	
	public static LobbyTask lobby;
	public static GameTask game;
	
	public void onEnable(){
		
		setupConfig();
		this.api = new API(this);
		
		this.lobby = new LobbyTask(api, getConfig().getInt("Min-Players"), 0);
		new Arena(api, "test");
		registerListeners();
		
		for(Player p : api.getServer().getOnlinePlayers()){
			api.getPlayerHandler().registerPlayer(p.getUniqueId());
			api.getPlayerHandler().getPlayerData(p.getUniqueId()).setTask(lobby);
		}
	}
	
	
	
	public void setupConfig(){
		this.getConfig().addDefault("Name", "Example Name");
		this.getConfig().addDefault("Win-Reward", 5);
		this.getConfig().addDefault("Min-Players", 5);
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	private void registerListeners(){
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getPluginManager().registerEvents(new PlayerEvent(api), this);

	}

}
