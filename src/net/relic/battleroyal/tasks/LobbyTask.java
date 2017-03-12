package net.relic.battleroyal.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.relic.battleroyal.API;
import net.relic.battleroyal.battleroyal;
import net.relic.battleroyal.player.PlayerData;
import net.relic.battleroyal.utils.Timer;

/**
 * @author Relic
 *
 */
public class LobbyTask extends BukkitRunnable{
	
	private API api;
	private int minplayers;
	
	// lobby timers //
	private Timer lobbyTimer;
	private Timer playerCheckTimer;
	
	private BukkitTask task;
	
	
	private boolean started = false;
	
	
	public LobbyTask(API api, int min, int reward){
		this.api = api;
		this.minplayers = min;
		this.task = this.runTaskTimer(api.getPlugin(), 0, 20);
	}
	
	public void startGame(){
		started = true;
		battleroyal.game = new GameTask(api);
		
	}
	
	public int getPlayersLeft(){
		return this.minplayers - api.getServer().getOnlinePlayers().size();
	}
	
	private void playerCheck(){
		if(started == false){
			if(api.getServer().getOnlinePlayers().size() >= minplayers){
				this.startGame();
			}else{
				api.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fWaiting for &6" + (minplayers - api.getServer().getOnlinePlayers().size()) + "&f more players."));

				for(Player p : api.getServer().getOnlinePlayers()){
					PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
					pd.sendMessage("Royal", "Waiting for &6" + (minplayers - api.getServer().getOnlinePlayers().size()) + "&7 more players.");
				}
			}
		}
		
	}

	@Override
	public void run() {
		if(lobbyTimer == null){
			this.lobbyTimer = new Timer(-1);
		}
		if(playerCheckTimer == null){
			playerCheckTimer = new Timer(10000);
		}else if(playerCheckTimer.hasReached()){
			this.playerCheck();
			playerCheckTimer = new Timer(10000);
		}
	}

}
