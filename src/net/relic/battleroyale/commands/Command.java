package net.relic.battleroyale.commands;

import java.util.UUID;

import net.relic.battleroyale.API;

/**
 * @author Relic
 *
 */
public abstract class Command {

	private String initial;
	private String desc;
	private String permission;
	
	private API api;
	
	public Command(API api, String initial, String desc, String perm){
		this.api = api;
		this.initial = initial; 
		this.permission = perm;
		this.desc = desc;
	}
	
	public String getDesc(){
		return this.desc;
	}
	
	public API getAPI(){
		return this.api;
	}
	
	public String getInitial(){
		return this.initial;
	}
	
	public String getPermission(){
		return this.permission;
	}
	
	public abstract void onExecute(UUID sender, String[] args);
	public abstract void onHelp(UUID sender);
	
}
