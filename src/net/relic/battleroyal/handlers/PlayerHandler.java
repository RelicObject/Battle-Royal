package net.relic.battleroyal.handlers;

import java.util.HashMap;
import java.util.UUID;

import net.relic.battleroyal.API;
import net.relic.battleroyal.battleroyal;
import net.relic.battleroyal.player.PlayerData;

/**
 * @author Relic
 *
 */
public class PlayerHandler {
	
	private API api;
	private HashMap<UUID, PlayerData> players = new HashMap<UUID, PlayerData>();
	
	public PlayerHandler(API api){
		this.api = api;
	}
	public void registerPlayer(UUID uuid){
		if(getPlayerData(uuid) == null){
			PlayerData pd = new PlayerData(api, uuid);
			pd.sendMessage("Royal", "&7Welcome to &8Battle &cRoyal&7 (&8" + api.getPlugin().getConfig().getString("Name") + "&7)");
			if(battleroyal.lobby.getPlayersLeft() == 0){
				pd.sendMessage("Royal", "&7The game will start shortly.");

			}else{
				pd.sendMessage("Royal", "&7We need &6" + battleroyal.lobby.getPlayersLeft() + "&7 more players.");
			}
			players.put(uuid, new PlayerData(api, uuid));
		}else{
			PlayerData pd = getPlayerData(uuid);

			pd.sendMessage("Royal", "&7Welcome to &8Battle &cRoyal&7 (&8" + api.getPlugin().getConfig().getString("Name") + "&7)");
			if(battleroyal.lobby.getPlayersLeft() == 0){
				pd.sendMessage("Royal", "&7The game will start shortly.");

			}else{
				pd.sendMessage("Royal", "&7We need &6" + battleroyal.lobby.getPlayersLeft() + "&7 more players.");
			}
		}
	}
	
	public PlayerData getPlayerData(UUID uuid){
		return players.get(uuid);
	}
	
	

}
