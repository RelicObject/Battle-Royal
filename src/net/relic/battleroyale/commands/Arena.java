package net.relic.battleroyale.commands;

import java.util.UUID;

import net.relic.battleroyale.API;
import net.relic.battleroyale.player.PlayerData;

/**
 * @author Relic
 *
 */
public class Arena extends Command{

	/**
	 * @param api
	 * @param initial
	 * @param desc
	 * @param perm
	 */
	public Arena(API api) {
		super(api, "arena", "Used for arena configuration", "battleroyal.arena");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onExecute(UUID sender, String[] args) {
		PlayerData pd = getAPI().getPlayerHandler().getPlayerData(sender);
		if(args.length == 0){
			this.onHelp(sender);
		}else{
			String option = args[0];
			switch(option.toLowerCase()){
			case "list":
				
				break;
			case "set":
				if(args.length < 3){
					pd.sendMessage("Arena", "/Arena set <Name> <Flag> {value}");
					return;
				}else{
					net.relic.battleroyale.arena.Arena a = getAPI().getArenaHandler().getArena(args[1]);
					if(a == null){
						pd.sendMessage("Arena", "Arena `&6" + args[1] + "&7` does not exist!");

					}else{
						String flag = args[2];
						switch(flag.toLowerCase()){
						case "spawn":
							a.addSpawn(getAPI().getServer().getPlayer(pd.getUUID()).getLocation(), true);
							pd.sendMessage("Arena", "Spawn added to Arena `&6" + a.getName() + "&7`!");
							break;
						}

					}
				}
				break;
			case "create":
				if(args.length < 2){
					pd.sendMessage("Arena", "/Arena Create <Name>");
					return;
				}
				net.relic.battleroyale.arena.Arena a = getAPI().getArenaHandler().getArena(args[1]);
				if(a == null){
					a = new net.relic.battleroyale.arena.Arena(getAPI(), args[1]);
					a.load(true);
					pd.sendMessage("Arena", "Arena `&6" + args[1] + "&7` created!");
				}else{
					pd.sendMessage("Arena", "Arena `&6" + args[1] + "&7` already exist's!");

				}
				
				break;
			}
		}
	}


	@Override
	public void onHelp(UUID sender) {
		PlayerData pd = getAPI().getPlayerHandler().getPlayerData(sender);
		pd.clearChat(true);
		pd.sendMessage("Arena", "/Arena - This command.");
		pd.sendMessage("Arena", "/Arena List - List the current arena's.");
		pd.sendMessage("Arena", "/Arena Create <Arena Name> - Create a new arena.");
		pd.sendMessage("Arena", "/Arena Delete <Arena Name> - Delete an arena.");
		pd.sendMessage("Arena", "/Arena Set <Arena Name> <Flag> {Value} - Set a flag with an optional value.");
	}

}
