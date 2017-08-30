package net.ddns.Kaigrealms;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CommandMethods {
	
	static void message(CommandSender usr, String m){
		usr.sendMessage(usr.getName()+", "+m);
	}
	
	static ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	static void tpMessage(Player sent, Player sender){
		message(sender, "Teleport request sent.");
		message(sent, "Use /tpyes "+sender.getDisplayName()+" to accept the request, or");
		message(sent, "use /tpno "+sender.getDisplayName()+" to deny the request");
	}
	static void tpRequest(Player sender, Player sent, String which){
		for (int v = 1; v<list.size(); v++){
			if (list.get(v).get(0) == sender.getUniqueId().toString() && list.get(v).get(1) == sent.getUniqueId().toString()){
				list.get(v).set(2, which);
				break;
			} else if (v == list.size()){
				ArrayList<String> request = new ArrayList<String>();
				request.set(0, sender.getUniqueId().toString());
				request.set(1, sent.getUniqueId().toString());
				request.set(2, which);
				list.add(request);
			}
		}
		if (which == "tpt"){
			message(sent, sender.getDisplayName()+" wants to teleport to you");
		} else if(which == "tph"){
			message(sent, sender.getDisplayName()+" wants you to teleport to them");
		}
		tpMessage(sent, sender);
	}
	static void tpResponse(Player sent, Player sender, boolean which){
		for(int i=1; i<list.size(); i++){
			if (list.get(i).get(0) == sender.getUniqueId().toString() && list.get(i).get(1) == sent.getUniqueId().toString()){
				if (which) {
					if (list.get(i).get(2) == "tpt"){
						sender.teleport(sent.getLocation());
					} else if (list.get(i).get(2) == "tph"){
						sent.teleport(sender.getLocation());
					}
				} else {
					message(sender, sent.getDisplayName()+" declined your request");
				}
			} else if ((list.get(i).get(1) != sent.getUniqueId().toString() | list.get(i).get(0) != sender.getUniqueId().toString())
					&& i == list.size() ){
				message(sent, "You don't have a teleport request from this player right now");
			}
		}
	}
	static void teleRequester(Player sent, Player send, boolean value, String type){
		if (sent.isOnline() && type != "") {
			tpRequest(send, sent, type);
		} else if (sent.isOnline() && type == ""){
			tpResponse(send, sent, value);
		} else {
			message(send, "That player doesn't exist or is offline");
		}
	}
	
	static void homes(Player  plyr, String name, int command, Location place, YamlConfiguration c){
		String d = "server."+plyr.getUniqueId()+"."+name;
		switch (command){
		case 1: //Sethome
			c.set(d+".World", place.getWorld());
			c.set(d+".X", place.getX());
			c.set(d+".Y", place.getY());
			c.set(d+".Z", place.getZ());
			c.set(d+".Yaw", place.getYaw());
			c.set(d+".Pitch", place.getPitch());
			break;
		case 2: //Delhome
			if (c.get(d) != null){
				c.set(d, null);
			} else if (c.getList(d) == null){
				message(plyr, "This home does not exist");
			}
			break;
		case 3: //Home
			if (c.get(d) != null){
				plyr.teleport(new Location(Bukkit.getServer().getWorld(c.getString(d+".World")), c.getDouble(d+".X"), 
						c.getDouble(d+".Y"), c.getDouble(d+".Z"), c.getInt(d+".Yaw"), c.getInt(d+".Pitch")));
			}
			break;
		default:
			message(plyr, "Something went wrong - check your spelling!");
			break;
		}
	}
}