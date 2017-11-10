package net.ddns.Kaigrealms;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands {
	public boolean onCommand(CommandSender usr, Command cmd, String label, String[] args){
		if((usr instanceof Player)){
			// This is still mostly a mess.
			Player plyr = (Player) usr;
			if(!(plyr.isDead())){
				if (args.length==0){
					if(Methods.commander(cmd, "suicide")){
					plyr.setHealth(0.0D);
					return true;
					} else if(Methods.commander(cmd, "spawn")){
						plyr.teleport(plyr.getWorld().getSpawnLocation());
						Methods.message(plyr,", you have been teleported to spawn.");
						return true;
					} else if(Methods.commander(cmd, "setspawn")){
						if (plyr.isOp()){
							Location loc = plyr.getLocation();
							plyr.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
							Methods.message(plyr,", you have successfully set the spawn of this world.");
							return true;
						} else {
							Methods.message(plyr, "No perms.");
							return true;
						}
					} else if(Methods.commander(cmd,  "getaway")) {
						double r = plyr.getWorld().getWorldBorder().getSize()%2;
						System.out.println(r);
						plyr.teleport(new Location(plyr.getWorld(), Math.random()*r, Math.random()*r, Math.random()*r));
					}
				} else if(args.length==1){
					if(Methods.commander(cmd, "tpt")){
						Methods.teleRequester(Methods.plyr(args[0]), plyr, false, "tpt");
					} else if(Methods.commander(cmd, "tph")){
						Methods.teleRequester(Methods.plyr(args[0]), plyr, false, "tph");
					} else if(Methods.commander(cmd, "tpyes")){
						Methods.teleRequester(Methods.plyr(args[0]), plyr, true, "");
					} else if(Methods.commander(cmd, "tpno")){
						Methods.teleRequester(Methods.plyr(args[0]), plyr, false, "");
					} else if (Methods.commander(cmd, "sethome")){
						Methods.homes(plyr, args[0], 1, plyr.getLocation(), net.ddns.Kaigrealms.Kaigrealms.homes);
					} else if (Methods.commander(cmd, "delhome")){
						Methods.homes(plyr, args[0], 2, null, net.ddns.Kaigrealms.Kaigrealms.homes);
					} else if (Methods.commander(cmd, "home")){
						Methods.homes(plyr, args[0], 3, null, net.ddns.Kaigrealms.Kaigrealms.homes);
					}
				} else {
					Methods.message(plyr, "You have too many arguments");
				}
			} else {
				Methods.message(usr, "...you aren't even alive.");
				return false;
			}
		} else {
			Methods.message(usr, "I get it. You wanna be cool. But you can't without a body.");
			return false;
		}
		return false;
	}
}