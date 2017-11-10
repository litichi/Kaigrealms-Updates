package net.ddns.Kaigrealms;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
 // This is staying a class for now.
public class Methods {
	// methods used throughout the plugin
	static void message(CommandSender usr, String m){
		usr.sendMessage(usr.getName()+", "+m);
	}
	static String id(Player person) {
		return person.getUniqueId().toString();
	}
	static String name(Player person) {
		return person.getDisplayName();
	}
	@SuppressWarnings("deprecation")
	static Player plyr(String player) {
		return Bukkit.getServer().getPlayer(player);
	}
	static void tpMessage(Player sent, Player sender){
		message(sender, "Teleport request sent.");
		message(sent, "Use /tpyes "+name(sender)+" to accept the request, or");
		message(sent, "use /tpno "+name(sender)+" to deny the request");
	}
	static boolean commander(Command cmd, String command) {
		if (cmd.getName().equalsIgnoreCase(command)) {
			return true;
		} else {
			return false;
		}
	}
	static String tpAns(int ans, Player sender) {
		switch(ans) {
		case 1:
			return Methods.name(sender)+" wants to teleport to you";
		case 2:
			return "Use /tpyes "+Methods.name(sender)+" to accept the request, or";
		case 3:
			return "Use /tpno "+Methods.name(sender)+" to deny the request.";
		}
		return null;
	}
	// list used for storage of teleport requests
	static ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	/* methods handling teleportation either way, possibly make more simple in the future
	 * optional arguments
	 */
	static void tpRequest(Player sender, Player sent, String which){
		for (int v = 1; v<list.size(); v++){
			if (list.get(v).get(0) == id(sender) && list.get(v).get(1) == id(sent)){
				list.get(v).set(2, which);
				break;
			} else if (v == list.size()){
				ArrayList<String> request = new ArrayList<String>();
				request.set(0, id(sender));
				request.set(1, id(sent));
				request.set(2, which);
				list.add(request);
			}
		}
		if (which == "tpt"){
			message(sent, name(sender)+" wants to teleport to you");
		} else if(which == "tph"){
			message(sent, name(sender)+" wants you to teleport to them");
		}
		tpMessage(sent, sender);
	}
	static void tpResponse(Player sent, Player sender, boolean which){
		for(int i=1; i<list.size(); i++){
			if (list.get(i).get(0) == id(sender) && list.get(i).get(1) == id(sent)){
				if (which) {
					if (list.get(i).get(2) == "tpt"){
						sender.teleport(sent.getLocation());
					} else if (list.get(i).get(2) == "tph"){
						sent.teleport(sender.getLocation());
					}
				} else {
					message(sender, name(sent)+" declined your request");
				}
			} else if ((list.get(i).get(1) != id(sent) | list.get(i).get(0) != id(sender))
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
	// This doesn't actually function yet.
	static void homes(Player  plyr, String name, int command, Location place, YamlConfiguration c){
		String d = "server."+id(plyr)+"."+name;
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