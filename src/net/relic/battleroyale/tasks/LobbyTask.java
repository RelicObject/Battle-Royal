package net.relic.battleroyale.tasks;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.relic.battleroyale.API;
import net.relic.battleroyale.battleroyale;
import net.relic.battleroyale.arena.Arena;
import net.relic.battleroyale.player.PlayerData;
import net.relic.battleroyale.utils.Timer;

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
	
	private Arena arena;
	private boolean started = false;
	
	
	public LobbyTask(API api, int min, int reward){
		this.api = api;
		this.minplayers = min;
		this.arena = api.getArenaHandler().getRandomArena();
		this.api.getServer().getWorld("world").setSpawnLocation(arena.getCenter().getBlockX(), arena.getCenter().getBlockY(), arena.getCenter().getBlockZ());
		this.api.getServer().getWorld("world").getWorldBorder().setCenter(arena.getCenter());
		this.api.getServer().getWorld("world").getWorldBorder().setSize(arena.getEnd());
		this.api.getServer().getWorld("world").getWorldBorder().setDamageAmount(arena.getDamage());
		
		this.task = this.runTaskTimer(api.getPlugin(), 0, 20);
	}
	
	public Arena getArena(){
		return this.arena;
	}
	
	public int getMinPlayers(){
		return this.minplayers;
	}
	
	public void startGame(){
		started = true;
		battleroyale.game = new GameTask(api, arena);
		
	}
	
	public void reset(){
		this.started = false;
		this.api.getServer().getWorld("world").getWorldBorder().setCenter(arena.getCenter());
		this.api.getServer().getWorld("world").getWorldBorder().setSize(arena.getEnd());
		this.api.getServer().getWorld("world").getWorldBorder().setDamageAmount(arena.getDamage());
	}
	
	public int getPlayersLeft(){
		return this.minplayers - api.getServer().getOnlinePlayers().size();
	}
	
	public void preparePlayer(UUID uuid){
		Player p = api.getServer().getPlayer(uuid);
		
		p.teleport(this.api.getServer().getWorld("world").getHighestBlockAt(arena.getCenter()).getLocation());
		
		
	}
	
	public void playerCheck(){
		if(started == false){
			if(api.getServer().getOnlinePlayers().size() >= minplayers){
				this.startGame();
			}else{
				api.getLogger().info("&fWaiting for &6" + (minplayers - api.getServer().getOnlinePlayers().size()) + "&f more players.");

				for(Player p : api.getServer().getOnlinePlayers()){
					PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
					pd.sendMessage("Royale", "Waiting for &6" + (minplayers - api.getServer().getOnlinePlayers().size()) + "&7 more players.");
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
