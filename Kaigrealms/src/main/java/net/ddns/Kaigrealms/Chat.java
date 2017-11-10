package net.ddns.Kaigrealms;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat {
	// What do you expect on a server where the owner has a pufferfish skin?
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent evnt){
		if (evnt.getMessage().toLowerCase().contains("fishy")){
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "give "+evnt.getPlayer().getName()+" cooked_fish:1 5");
			evnt.getPlayer().sendMessage("Eh? You think so?");
		}
	}
	// Can begin implementation of regex/formatting in chat in version 0.4.8
}