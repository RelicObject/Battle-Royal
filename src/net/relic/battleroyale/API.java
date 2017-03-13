package net.relic.battleroyale;

import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import net.relic.battleroyale.handlers.ArenaHandler;
import net.relic.battleroyale.handlers.CommandHandler;
import net.relic.battleroyale.handlers.PlayerHandler;
import net.relic.battleroyale.utils.Logger;

/**
 * @author Relic
 *
 */
public class API {
	
	private JavaPlugin pl;
	
	private PlayerHandler ph;
	private ArenaHandler ah;
	private CommandHandler ch;
	private Logger logger;
	
	public API(JavaPlugin pl)
	{
		this.logger = new Logger(this);
		this.pl = pl;
		
		this.ph = new PlayerHandler(this);
		this.logger.info("Loaded PlayerHandler");
		this.ah = new ArenaHandler(this);
		this.logger.info("Loaded ArenaHandler");
		this.ch = new CommandHandler(this);
		this.logger.info("Loaded CommandHandler");

	}
	
	public Logger getLogger(){
		return this.logger;
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
