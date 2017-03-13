package net.relic.battleroyale.handlers;

import java.util.HashMap;
import java.util.UUID;

import net.relic.battleroyale.API;
import net.relic.battleroyale.battleroyale;
import net.relic.battleroyale.player.PlayerData;
import net.relic.battleroyale.tasks.LobbyTask;

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
	
	public void clearPlayers(){
		players.clear();
	}
	
	
	
	public void registerPlayer(UUID uuid){
		if(getPlayerData(uuid) == null){
			PlayerData pd = new PlayerData(api, uuid);
			if(pd.getCurrentTask() instanceof LobbyTask){
				LobbyTask task = (LobbyTask)pd.getCurrentTask();
				task.preparePlayer(pd.getUUID());
			}
			pd.clearChat(true);
			pd.sendMessage("Royale", "&7Welcome to &8Battle &cRoyale&7 (&8" + api.getPlugin().getConfig().getString("Name") + "&7)");
			if(battleroyale.lobby.getPlayersLeft() == 0){
				pd.sendMessage("Royale", "&7The game will start shortly.");

			}else{
				pd.sendMessage("Royale", "&7We need &6" + battleroyale.lobby.getPlayersLeft() + "&7 more players.");
			}
			players.put(uuid, new PlayerData(api, uuid));
			
		}else{
			PlayerData pd = getPlayerData(uuid);
			if(pd.getCurrentTask() instanceof LobbyTask){
				LobbyTask task = (LobbyTask)pd.getCurrentTask();
				task.preparePlayer(pd.getUUID());
			}
			pd.clearChat(true);

			pd.sendMessage("Royale", "&7Welcome to &8Battle &cRoyale&7 (&8" + api.getPlugin().getConfig().getString("Name") + "&7)");
			if(battleroyale.lobby.getPlayersLeft() == 0){
				pd.sendMessage("Royale", "&7The game will start shortly.");

			}else{
				pd.sendMessage("Royale", "&7We need &6" + battleroyale.lobby.getPlayersLeft() + "&7 more players.");
			}
		}
	}
	
	public PlayerData getPlayerData(UUID uuid){
		return players.get(uuid);
	}
	
	

}
