package com.github.t31337.t3ssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TogglePVP implements CommandExecutor
{
	T3ssentials plugin;
	private TogglePVP(T3ssentials plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String cmdName, String[] args)
	{
		if(sender.hasPermission("T3.Admin"))
		{
			if(args.length==0)
			{
				Player p = (Player)sender;
				p.getWorld().setPVP(!p.getWorld().getPVP());
				Bukkit.broadcastMessage("World PVP: " + p.getWorld().getPVP());
				return true;
			}
			if(args.length>0)
			{
				Bukkit.getWorld(args[0]).setPVP(!Bukkit.getWorld(args[0]).getPVP());
				Bukkit.broadcastMessage(ChatColor.AQUA + "World: " + Bukkit.getWorld(args[0]) + " PVP: " + Bukkit.getWorld(args[1]).getPVP());
				return true;
			}
			
			return false;
		}
		else
		{
			sender.sendMessage(ChatColor.RED+"Sorry, You Don't  Have Permission To Do That...");
			return true;
		}
	}
}