package me.thijmen321.buildingutil;

import org.bukkit.plugin.java.JavaPlugin;

public class BuildingUtil extends JavaPlugin 
{

	public void onEnable() 
	{
		this.saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(new BlockInteractListener(), this);
	}
	
	public void onDisable() 
	{
		
	}
}
