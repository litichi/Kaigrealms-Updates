package net.ddns.Kaigrealms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands extends Kaigrealms {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender usr, Command cmd, String label, String[] args){
		if((usr instanceof Player)){
			Player plyr = (Player) usr;
			if(!(plyr.isDead())){
				if (args.length==0){
					if(cmd.getName().equalsIgnoreCase("suicide")){
					plyr.setHealth(0.0D);
					return true;
					} else if(cmd.getName().equalsIgnoreCase("spawn")){
						plyr.teleport(plyr.getWorld().getSpawnLocation());
						CommandMethods.message(plyr,", you have been teleported to spawn.");
						return true;
					} else if(cmd.getName().equalsIgnoreCase("setspawn")){
						if (plyr.isOp()){
							Location loc = plyr.getLocation();
							plyr.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
							CommandMethods.message(plyr,", you have successfully set the spawn of this world.");
							return true;
						} else {
							CommandMethods.message(plyr,", currently you have to be an operator to do this.");
							return false;
						}
					}
				} else if(args.length==1){
					if(cmd.getName().equalsIgnoreCase("tpt")){
						CommandMethods.teleRequester(Bukkit.getServer().getPlayer(args[0]), plyr, false, "tpt");
					} else if(cmd.getName().equalsIgnoreCase("tph")){
						CommandMethods.teleRequester(Bukkit.getServer().getPlayer(args[0]), plyr, false, "tph");
					} else if(cmd.getName().equalsIgnoreCase("tpyes")){
						CommandMethods.teleRequester(Bukkit.getServer().getPlayer(args[0]), plyr, true, "");
					} else if(cmd.getName().equalsIgnoreCase("tpno")){
						CommandMethods.teleRequester(Bukkit.getServer().getPlayer(args[0]), plyr, false, "");
					} else if (cmd.getName().equalsIgnoreCase("sethome")){
						CommandMethods.homes(plyr, args[0], 1, plyr.getLocation(), net.ddns.Kaigrealms.Kaigrealms.homes);
					} else if (cmd.getName().equalsIgnoreCase("delhome")){
						CommandMethods.homes(plyr, args[0], 2, null, net.ddns.Kaigrealms.Kaigrealms.homes);
					} else if (cmd.getName().equalsIgnoreCase("home")){
						CommandMethods.homes(plyr, args[0], 3, null, net.ddns.Kaigrealms.Kaigrealms.homes);
					}
				} else {
					CommandMethods.message(plyr, "You have too many arguments");
				}
			} else {
				CommandMethods.message(usr, "...you aren't even alive.");
				return false;
			}
		} else {
			CommandMethods.message(usr, "I get it. You wanna be cool. But you can't without a body.");
			return false;
		}
		return false;
	}
	/*
	 * warps useable by all, cross worlds as well
	 * random tp system, possibly biome related
	 * permissions system.. like silencing and freezing
	 * tags for usernames just for fun
	 */
	//diguises as mobs eventually [likely sooner rather than later]
}