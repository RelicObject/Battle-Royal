package net.relic.battleroyal.arena;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import net.relic.battleroyal.API;

/**
 * @author Relic
 *
 */
public class Arena {
	
	private API api;
	private String name;
	
	
	private File f;
	private YamlConfiguration yc;
	
	
	private int[] rewards;
	private Location bordercenter;
	private double borderStart;
	private double borderEnd;
	private double borderDamage;
	private double borderDecreaseAmount;
	private int borderDecreaseTime;
	
	
	public double getBorderDecreaseAmount(){
		return this.borderDecreaseAmount;
	}
	
	public int getBorderDecreaseTime(){
		return this.borderDecreaseTime;
	}
	
	public int[] getRewards(){
		return this.rewards;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Location getCenter(){
		return this.bordercenter;
	}
	public double getStart(){
		return this.borderStart;
	}
	public double getEnd(){
		return this.borderEnd;
	}
	public double getDamage(){
		return this.borderDamage;
	}
	
	
	public Arena(API api, String name){
		this.api = api;
		this.name = name;
		this.load();
	}
	
	
	
	
	private void load(){
		File foler = new File(api.getPlugin().getDataFolder(), "/Arenas/");
		if(!foler.exists()){
			foler.mkdir();
		}
		f = new File(api.getPlugin().getDataFolder(), "/Arenas/" + this.name + ".yml");
		if(!f.exists()){
			try{
				f.createNewFile();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		yc = YamlConfiguration.loadConfiguration(f);
		processConfig();
		this.rewards = new int[]{yc.getInt("Reward.First"), yc.getInt("Reward.Second"), yc.getInt("Reward.Third")};
		this.bordercenter = (Location) yc.get("Border.Center");
		this.borderStart = yc.getDouble("Border.Start");
		this.borderEnd = yc.getDouble("Border.End");
		this.borderDamage = yc.getDouble("Border.Damage");
		this.borderDecreaseAmount = yc.getDouble("Border.Decrease.Amount");
		this.borderDecreaseTime = yc.getInt("Border.Decrease.Time");

	}
	
	
	private void processConfig(){
		yc.options().copyDefaults(true);
		yc.addDefault("Name", this.name);
		yc.addDefault("Reward.First", 1000);
		yc.addDefault("Reward.Second", 500);
		yc.addDefault("Reward.Third", 250);
		yc.addDefault("Border.Center", this.api.getServer().getWorld("world").getWorldBorder().getCenter());
		yc.addDefault("Border.Start", 250.0);
		yc.addDefault("Border.End", 35.0);
		yc.addDefault("Border.Decrease.Amount", 15.0);
		yc.addDefault("Border.Decrease.Time", 15);
		yc.addDefault("Border.Damage", 0.05);
		try{
			
			yc.save(f);
		}catch(Exception e){
			e.printStackTrace();
		}


	}
	
	
	
	

}
