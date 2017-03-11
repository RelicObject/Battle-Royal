package net.relic.battleroyal.utils;

/**
 * @author Relic
 *
 */
public class Timer {

	int reach;
	long starttime;
	
	public Timer(int reach){
		this.reach = reach;
		this.starttime = System.currentTimeMillis();
	}
	
	public long getDuration(){
		return System.currentTimeMillis() - starttime;
	}
	
	public boolean hasReached(){
		return (System.currentTimeMillis() - starttime >= reach);
	}
	
	public long getLeft(){
		return System.currentTimeMillis() - starttime + reach;
	}
	
}
