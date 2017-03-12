package net.relic.battleroyal;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import net.relic.battleroyal.handlers.ArenaHandler;
import net.relic.battleroyal.handlers.CommandHandler;
import net.relic.battleroyal.handlers.PlayerHandler;

/**
 * @author Relic
 *
 */
public class API {
	
	private JavaPlugin pl;
	
	private PlayerHandler ph;
	private ArenaHandler ah;
	private CommandHandler ch;
	public API(JavaPlugin pl)
	{
		this.pl = pl;
		this.ph = new PlayerHandler(this);
		this.ah = new ArenaHandler(this);
		this.ch = new CommandHandler(this);
	}
	
	public CommandHandler getCommandHandler(){
		return this.ch;
	}
	
	public ArenaHandler getArenaHandler(){
		return this.ah;
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
