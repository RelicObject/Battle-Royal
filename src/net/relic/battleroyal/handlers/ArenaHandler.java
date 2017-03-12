package net.relic.battleroyal.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.ChatColor;

import net.relic.battleroyal.API;
import net.relic.battleroyal.arena.Arena;

/**
 * @author Relic
 *
 */
public class ArenaHandler {
	
	private ArrayList<Arena> arenas = new ArrayList<Arena>();
	private API api;
	
	public ArenaHandler(API api){
		this.api = api;
		loadArenas();
	}
	
	public Arena getRandomArena(){
		if(this.arenas.size() == 0){
			api.getServer().getPluginManager().disablePlugin(api.getPlugin());
			api.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&fNo arena's exist, &cExiting!"));

			
			return null;
		}else{
			Random r = new Random();
			int index = r.nextInt(arenas.size());
			return arenas.get(index);
		}
	}
	
	public Arena getArena(String name){
		for(Arena a : arenas){
			if(a.getName().equalsIgnoreCase(name)){
				return a;
			}
		}
		return null;
	}
	
	
	private void loadArenas(){
		File folder = new File(api.getPlugin().getDataFolder(), "/Arenas/");
		if(!folder.exists()){
			folder.mkdir();
		}
		for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory()) {
	        	String name = FilenameUtils.removeExtension(fileEntry.getName());
	        	if(getArena(name) == null){
		        	arenas.add(new Arena(api, name));
	        	}
	        }
	    }
	}

}
