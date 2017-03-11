package net.relic.battleroyal.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.relic.battleroyal.API;
import net.relic.battleroyal.player.PlayerData;
import net.relic.battleroyal.utils.Timer;

/**
 * @author Relic
 *
 */
public class GameTask extends BukkitRunnable{

	private API api;
	private BukkitTask task;
	
	
	// GAME TIMERS //
	private Timer gameTimer;
	private Timer combatTimer;
	
	private boolean combatReached = false;
	
	public GameTask(API api){
		this.api = api;
		
		for(Player p : api.getServer().getOnlinePlayers()){
			PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
			pd.setTask(this);
			pd.sendMessage("Royal", "The game has started.");
			pd.sendMessage("Royal", "You have &630 &7second's of protection.");

		}
		this.start();
	}
	
	private void preparePlayers(){
		for(Player p : api.getServer().getOnlinePlayers()){
			p.getInventory().setArmorContents(new ItemStack[]{});
			p.getInventory().clear();
			p.updateInventory();
		}

	}
	
	public void start(){
		this.task = this.runTaskTimer(api.getPlugin(), 0, 20);
		this.api.getServer().getWorld("world").setTime(20000);
		
	}
	
	
	@Override
	public void run() {
		if(this.gameTimer == null){
			this.gameTimer = new Timer(300000);
		}else{
			if(this.gameTimer.hasReached()){
				//END GAME//
			}else{
				if(this.gameTimer.getLeft() == 240000){
					for(Player p : api.getServer().getOnlinePlayers()){
						p.sendMessage("There is 4 minute's left!");
						api.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&64 &fsecond's left!"));
					}
				}
			}
		}
		if(this.combatTimer == null){
			this.combatTimer = new Timer(30000);
		}else{
			if(this.combatReached == false && this.combatTimer.hasReached()){
				this.combatReached = true;
				for(Player p : api.getServer().getOnlinePlayers()){
					p.sendMessage("You are no longer protected!");
				}
				api.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fProtection has been &cDisabled&f!"));
			}
		}
	}
}
