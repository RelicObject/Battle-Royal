package net.relic.battleroyale.arena;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import net.relic.battleroyale.API;

/**
 * @author Relic
 *
 */
public class Arena {
	
	private API api;
	private String name;
	
	
	private File f;
	private YamlConfiguration yc;
	
	
	private int[] rewards = {1000, 500, 250};
	private Location bordercenter;
	private double borderStart;
	private double borderEnd;
	private double borderDamage;
	private double borderDecreaseAmount;
	private int borderDecreaseTime;
	private ArrayList<Location> spawns = new ArrayList<Location>();
	
	private ArrayList<Location> used = new ArrayList<Location>();
	
	private boolean exists = false;
	
	public double getBorderDecreaseAmount(){
		return this.borderDecreaseAmount;
	}
	
	public int getBorderDecreaseTime(){
		return this.borderDecreaseTime;
	}
	
	public int[] getRewards(){
		return this.rewards;
	}
	
	public void addSpawn(Location l, boolean save){
		this.spawns.add(l);
		if(save){
			this.save();
		}
	}
	
	public Location getUnusedSpawn(){
		Random r = new Random();
		Location l = spawns.get(r.nextInt(spawns.size()));
		while(used.contains(l)){
			l = spawns.get(r.nextInt(spawns.size()));
		}
		return l;
	}
	
	public ArrayList<Location> getSpawns(){
		return this.spawns;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Location getCenter(){
		return this.bordercenter;
	}
	public void setCenter(Location center, boolean save){
		this.bordercenter = center;
		if(save){
			this.save();
		}
	}
	public double getStart(){
		return this.borderStart;
	}
	public void setStart(double start, boolean save){
		this.borderStart = start;
		if(save){
			this.save();
		}
	}
	public double getEnd(){
		return this.borderEnd;
	}
	public void setEnd(double end, boolean save){
		this.borderEnd = end;
		if(save){
			this.save();
		}
	}
	public double getDamage(){
		return this.borderDamage;
	}
	public void setDamage(double damage, boolean save){
		this.borderDamage = damage;
		if(save){
			this.save();
		}
	}
	
	
	public Arena(API api, String name){
		this.api = api;
		this.name = name;
		this.load(false);
	}
	
	public boolean doesExist(){
		return this.exists;
	}
	
	
	public void load(boolean create){
		File foler = new File(api.getPlugin().getDataFolder(), "/Arenas/");
		if(!foler.exists()){
			foler.mkdir();
		}
		f = new File(api.getPlugin().getDataFolder(), "/Arenas/" + this.name + ".yml");
		if(!f.exists()){
			try{
				this.exists = false;

				if(create){
					f.createNewFile();
					
				}else{
					return;
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		this.exists = true;
		yc = YamlConfiguration.loadConfiguration(f);
		processConfig();
		this.rewards = new int[]{yc.getInt("Reward.First"), yc.getInt("Reward.Second"), yc.getInt("Reward.Third")};
		this.bordercenter = (Location) yc.get("Border.Center");
		this.borderStart = yc.getDouble("Border.Start");
		this.borderEnd = yc.getDouble("Border.End");
		this.borderDamage = yc.getDouble("Border.Damage");
		this.borderDecreaseAmount = yc.getDouble("Border.Decrease.Amount");
		this.borderDecreaseTime = yc.getInt("Border.Decrease.Time");
		this.spawns = (ArrayList<Location>) yc.get("Arena.Spawns");
	}
	
	public void save(){
		yc.set("Name", this.name);
		yc.set("Reward.First", this.rewards[0]);
		yc.set("Reward.Second", this.rewards[1]);
		yc.set("Reward.Third", this.rewards[2]);
		yc.set("Border.Center", this.bordercenter);
		yc.set("Border.Start", this.borderStart);
		yc.set("Border.End", this.borderEnd);
		yc.set("Border.Decrease.Amount", this.borderDecreaseAmount);
		yc.set("Border.Decrease.Time", this.borderDecreaseTime);
		yc.set("Border.Damage", this.borderDamage);
		yc.set("Arena.Spawns", spawns);
		try{
			
			yc.save(f);
		}catch(Exception e){
			e.printStackTrace();
		}
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
		yc.addDefault("Arena.Spawns", spawns);
		try{
			
			yc.save(f);
		}catch(Exception e){
			e.printStackTrace();
		}


	}
	
	
	
	

}
