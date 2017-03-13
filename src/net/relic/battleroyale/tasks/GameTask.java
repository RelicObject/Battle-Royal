package net.relic.battleroyale.tasks;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
public class GameTask extends BukkitRunnable{

	private API api;
	private BukkitTask task;
	
	
	// GAME TIMERS //
	private Timer gameTimer;
	private Timer combatTimer;
	private Timer borderTimer;

	private boolean combatReached = false;
	
	
	private Arena arena;
	
	public GameTask(API api, Arena arena){
		this.api = api;
		this.arena = arena;
		this.api.getServer().getWorld("world").getWorldBorder().setCenter(arena.getCenter());
		this.api.getServer().getWorld("world").getWorldBorder().setSize(arena.getStart());
		this.api.getServer().getWorld("world").getWorldBorder().setDamageAmount(arena.getDamage());
		
		
		preparePlayers();
		this.start();
	}
	
	private void preparePlayers(){
		for(Player p : api.getServer().getOnlinePlayers()){
			PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
			pd.setTask(this);
			p.setGameMode(GameMode.SURVIVAL);
			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(Integer.MAX_VALUE);
			p.getInventory().setArmorContents(new ItemStack[]{});
			p.getInventory().clear();
			p.updateInventory();
			p.teleport(this.api.getServer().getWorld("world").getHighestBlockAt(arena.getUnusedSpawn()).getLocation());
			pd.clearChat(true);
			pd.sendMessage("Royale", "The game has started. (&8Arena: &a" + StringUtils.capitalise(arena.getName()) + "&7)");
			pd.sendMessage("Royale", "You have &630 &7second's of protection.");
			
		}

	}
	
	public void start(){
		this.task = this.runTaskTimer(api.getPlugin(), 0, 20);
		this.api.getServer().getWorld("world").setTime(23000);
		api.getLogger().info("Game Has Started!");

	}
	
	public void end(){
	
		for(Player p : api.getServer().getOnlinePlayers()){
			PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
			battleroyale.lobby.reset();
			pd.setTask(battleroyale.lobby);
			p.kickPlayer("Game ended!");
		}
		battleroyale.game = null;
		this.cancel();
	}
	
	@Override
	public void run() {
		if(this.api.getServer().getOnlinePlayers().size() == 0){
			end();
		}
		if(this.gameTimer == null){
			this.gameTimer = new Timer(1500000);
		}else{
			if(this.gameTimer.hasReached()){
				end();
			}
		}
		if(this.combatTimer == null){
			this.combatTimer = new Timer(30000);
		}else{
			if(this.combatReached == false && this.combatTimer.hasReached()){
				this.combatReached = true;
				for(Player p : api.getServer().getOnlinePlayers()){
					PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
					pd.sendMessage("Royale", "Protection has been &cDisabled&7!");
				}
				api.getLogger().warn("Protection has been disabled!");
			}
		}
		if(this.borderTimer == null){
			this.borderTimer = new Timer(30000);
		}else{
			if(!this.combatTimer.hasReached())
				return;
			if(this.borderTimer.hasReached()){
				api.getLogger().warn("Playable area is decreasing!");
				if(this.api.getServer().getWorld("world").getWorldBorder().getSize() == this.arena.getEnd()){
					this.api.getServer().getWorld("world").getWorldBorder().setSize(this.api.getServer().getWorld("world").getWorldBorder().getSize() + 1.0, 1);
					this.borderTimer = new Timer(45000);
					
				}else{
					double left = this.api.getServer().getWorld("world").getWorldBorder().getSize() - this.arena.getBorderDecreaseAmount();
					if(left < this.arena.getEnd()){
						this.api.getServer().getWorld("world").getWorldBorder().setSize(this.arena.getEnd(), this.arena.getBorderDecreaseTime());
						
					}else{
						this.api.getServer().getWorld("world").getWorldBorder().setSize(this.api.getServer().getWorld("world").getWorldBorder().getSize() - arena.getBorderDecreaseAmount(), arena.getBorderDecreaseTime());
						
					}
				}
				
				for(Player p : api.getServer().getOnlinePlayers()){
					PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
					pd.sendMessage("Royale", "The playable area is decreasing!");

				}
				this.borderTimer = new Timer(45000);
				/**if(this.api.getServer().getWorld("world").getWorldBorder().getSize() > arena.getEnd()){
					this.api.getServer().getWorld("world").getWorldBorder().setSize(this.api.getServer().getWorld("world").getWorldBorder().getSize() - arena.getBorderDecreaseAmount(), arena.getBorderDecreaseTime());
					api.getLogger().warn("Playable area is decreasing!");

					for(Player p : api.getServer().getOnlinePlayers()){
						PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
						pd.sendMessage("Royale", "Playable area is decreasing!");
					}
					if(this.api.getServer().getWorld("world").getWorldBorder().getSize() > arena.getEnd()){
						this.borderTimer = new Timer(75000);

					}else{
						for(Player p : api.getServer().getOnlinePlayers()){
							PlayerData pd = api.getPlayerHandler().getPlayerData(p.getUniqueId());
							pd.sendMessage("Royale", "The playable area will no longer decrease after this point.");
						}
						this.borderTimer = new Timer(Integer.MAX_VALUE);

					}

				}**/
			}
		}
	}
}
