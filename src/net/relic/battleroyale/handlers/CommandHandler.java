package net.relic.battleroyale.handlers;

import java.util.ArrayList;

import net.relic.battleroyale.API;
import net.relic.battleroyale.commands.Arena;
import net.relic.battleroyale.commands.Command;
import net.relic.battleroyale.commands.Help;

/**
 * @author Relic
 *
 */
public class CommandHandler {
	
	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public CommandHandler(API api){		
		commands.add(new Help(api));
		commands.add(new Arena(api));
	}
	
	public ArrayList<Command> getCommands(){
		return this.commands;
	}

}
