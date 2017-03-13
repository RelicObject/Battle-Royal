package net.relic.battleroyale.commands;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import net.relic.battleroyale.API;
import net.relic.battleroyale.player.PlayerData;

/**
 * @author Relic
 *
 */
public class Help extends Command{

	/**
	 * @param initial
	 * @param perm
	 */
	public Help(API api) {
		super(api, "Help", "This Command.", "battleroyal.help");
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onExecute(UUID sender, String[] args) {
		PlayerData pd = getAPI().getPlayerHandler().getPlayerData(sender);
		if(args.length == 0){
			pd.clearChat(true);
			pd.sendMessage("Royale", "Here is a list of our commands.");
			for(Command c : getAPI().getCommandHandler().getCommands()){
				if(getAPI().getServer().getPlayer(pd.getUUID()).hasPermission(c.getPermission())){
					getAPI().getServer().getPlayer(pd.getUUID()).sendMessage(ChatColor.translateAlternateColorCodes('&', " &a* &7/" + StringUtils.capitalise(c.getInitial()) + " &f- &7" + this.getDesc()));
				}
			}
		}
	}

	@Override
	public void onHelp(UUID sender) {
		// TODO Auto-generated method stub
		
	}

}
