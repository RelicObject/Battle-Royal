package net.relic.battleroyal.handlers;

import java.util.ArrayList;

import net.relic.battleroyal.API;
import net.relic.battleroyal.commands.Command;
import net.relic.battleroyal.commands.Help;

/**
 * @author Relic
 *
 */
public class CommandHandler {
	
	private ArrayList<Command> commands = new ArrayList<Command>();
	
	public CommandHandler(API api){		
		commands.add(new Help(api));
	}
	
	public ArrayList<Command> getCommands(){
		return this.commands;
	}

}
