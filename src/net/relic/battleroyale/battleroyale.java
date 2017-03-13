package net.relic.battleroyale;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.relic.battleroyale.listeners.PlayerEvent;
import net.relic.battleroyale.player.PlayerData;
import net.relic.battleroyale.tasks.GameTask;
import net.relic.battleroyale.tasks.LobbyTask;

/**
 * @author Relic
 *
 */
public class battleroyale extends JavaPlugin implements Listener{
	
	private API api;
	
	public static LobbyTask lobby;
	public static GameTask game;
	
	public void onEnable(){
		
		setupConfig();
		this.api = new API(this);
		api.getLogger().info("Loaded API");
		this.lobby = new LobbyTask(api, getConfig().getInt("Min-Players"), 0);
		api.getLogger().info("Created Lobby");
		//new Arena(api, "test");
		registerListeners();
		api.getLogger().info("Registered Listeners");

		for(Player p : api.getServer().getOnlinePlayers()){
			api.getPlayerHandler().registerPlayer(p.getUniqueId());
			api.getPlayerHandler().getPlayerData(p.getUniqueId()).setTask(lobby);
			lobby.preparePlayer(p.getUniqueId());
		}
		api.getLogger().info("Handled Online Players");
		api.getLogger().info("Finished Loading");
		lobby.playerCheck();
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
	
	
	
	
	
	
	
	
	@Override
	public boolean onCommand (CommandSender sender, Command command, String commandLabel, String[] args){
		if(sender instanceof Player){
			Player p = (Player)sender;
			PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
			for(net.relic.battleroyale.commands.Command c : api.getCommandHandler().getCommands()){
				if(command.getName().equalsIgnoreCase(c.getInitial())){
					if(p.hasPermission(c.getPermission())){
						c.onExecute(p.getUniqueId(), args);
					}
				}
			}
		}
		return false;
	}
	
	
	

}
