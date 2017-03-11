package net.relic.battleroyal;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import net.relic.battleroyal.handlers.PlayerHandler;

/**
 * @author Relic
 *
 */
public class API {
	
	private JavaPlugin pl;
	
	private PlayerHandler ph;
	
	public API(JavaPlugin pl)
	{
		this.pl = pl;
		this.ph = new PlayerHandler(this);
	}
	
	public PlayerHandler getPlayerHandler(){
		return this.ph;
	}
	
	public JavaPlugin getPlugin(){
		return this.pl;
	}
	
	public Server getServer(){
		return this.pl.getServer();
	}

}
