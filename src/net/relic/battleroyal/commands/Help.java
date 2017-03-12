package net.relic.battleroyal.commands;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import net.relic.battleroyal.API;
import net.relic.battleroyal.player.PlayerData;

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
		super(api, "Help", "View our command's.", "battleroyal.help");
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void onExecute(UUID sender, String[] args) {
		PlayerData pd = getAPI().getPlayerHandler().getPlayerData(sender);
		if(args.length == 0){
			pd.clearChat(true);
			for(Command c : getAPI().getCommandHandler().getCommands()){
				if(getAPI().getServer().getPlayer(pd.getUUID()).hasPermission(c.getPermission())){
					getAPI().getServer().getPlayer(pd.getUUID()).sendMessage(ChatColor.translateAlternateColorCodes('&', "  &a* &7/" + StringUtils.capitalise(c.getInitial()) + " &f- &7" + this.getDesc()));
				}
			}
		}
	}

	@Override
	public void onHelp(UUID sender) {
		// TODO Auto-generated method stub
		
	}

}
