package net.ddns.Kaigrealms;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public class Kaig extends JavaPlugin implements Listener{
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent evnt){
		if (evnt.getMessage().toLowerCase().contains("fishy")){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "give "+evnt.getPlayer().getName()+" cooked_fish 5");
			evnt.getPlayer().sendMessage("Eh? You think so?");
		}
	}
	static ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		static void tpRequest(Player sender, Player sent, String which){
			for (int v = 1; v<list.size(); v++){
				if (list.get(v).get(0) == Methods.id(sender)
						&& list.get(v).get(1) == Methods.id(sent)){
					list.get(v).set(2, which);
				} else if (v == list.size() && list.get(v).get(0) != Methods.name(sender)
						| list.get(v).get(1) != Methods.name(sent)){
					ArrayList<String> request = new ArrayList<String>();
					request.set(0, Methods.name(sender));
					request.set(1, Methods.name(sent));
					request.set(2, which);
					list.add(request);
				}
			}
			Methods.message(sender, "Request sent.");
			if (which == "tpt"){
				Methods.message(sent, Methods.name(sender)+" wants to teleport to you");
				Methods.message(sent, "Use /tpyes "+Methods.name(sender)+" to accept the request, or");
				Methods.message(sent, "use /tpno "+Methods.name(sender)+" to deny the request");
			} else if(which == "tph"){
				Methods.message(sent, Methods.name(sender)+" wants you to teleport to them");
				Methods.message(sent, "Use /tpyes "+Methods.name(sender)+" to accept the request, or");
				Methods.message(sent, "use /tpno "+Methods.name(sender)+" to deny the request");
			}
		}
		static void tpResponse(Player sent, Player sender, boolean which){
			for(int i=1; i<list.size(); i++){
				if (list.get(i).get(0) == Methods.id(sender)){
					if (list.get(i).get(1) == Methods.id(sent)){
						if(which){
							if (list.get(i).get(2) == "tpt"){
								sender.teleport(sent.getLocation());
							} else if (list.get(i).get(2) == "tph"){
								sent.teleport(sender.getLocation());
							}
						} else {
							Methods.message(sender, Methods.name(sent)+" declined your request");
						}
					} else if (list.get(i).get(1) != Methods.id(sent) && i == list.size()){
						Methods.message(sent, "You don't have a teleport request from this player right now");
					}
				} else if (list.get(i).get(0) != Methods.id(sender) && i == list.size()){
					Methods.message(sent, "You don't have a teleport request from this player right now");
				}
			}
		}
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
							Methods.message(plyr, Methods.name(plyr)+", you have been teleported to spawn.");
							return true;
						} else if(cmd.getName().equalsIgnoreCase("setspawn")){
							if (plyr.isOp()){
								Location loc = plyr.getLocation();
								plyr.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
								Methods.message(plyr, Methods.name(plyr)+", you have successfully set the spawn of this world.");
								return true;
							} else {
								Methods.message(plyr, Methods.name(plyr)+", currently you have to be an operator to do this.");
								return false;
							}
						}
					} else if(args.length==1){
						if(cmd.getName().equalsIgnoreCase("tpt")){
							if (Methods.plyr(args[0]).isOnline()){
							tpRequest(plyr, Methods.plyr(args[0]), "tpt");
							} else {
								Methods.message(plyr, "That player doesn't exist or is offline");
							}
						} else if(cmd.getName().equalsIgnoreCase("tph")){
							if (Methods.plyr(args[0]).isOnline()){
							tpRequest(plyr, Methods.plyr(args[0]), "tpt");
							} else {
								Methods.message(plyr, "That player doesn't exist or is offline");
							}
						} else if(cmd.getName().equalsIgnoreCase("tpyes")){
							if (Methods.plyr(args[0]).isOnline()){
							tpResponse(plyr, Methods.plyr(args[0]), true);
							} else {
								Methods.message(plyr, "That player doesn't exist or is offline");
							}
						} else if(cmd.getName().equalsIgnoreCase("tpno")){
							if (Methods.plyr(args[0]).isOnline()){
							tpResponse(plyr, Methods.plyr(args[0]), true);
							} else {
								Methods.message(plyr, "That player doesn't exist or is offline");
							}
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