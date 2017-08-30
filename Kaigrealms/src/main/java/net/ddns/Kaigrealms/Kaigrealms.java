package net.ddns.Kaigrealms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Kaigrealms extends JavaPlugin implements Listener {
	public File folder = this.getDataFolder();
	public File file = new File("homes.yml");
	static YamlConfiguration homes = new YamlConfiguration();
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
        System.out.print("[Kaigrealms] Kaig Enabled!");
        if(!folder.exists()) {
        	folder.mkdir();
        }	 
        if(!file.exists()) {
        	file.mkdir();
        }
        	try {
				homes.load(file);
				System.out.println("Homes file loaded!");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        reloadConfig();
	}
	public void reloadConfig(){
        this.getConfig().options().copyDefaults(true);
		this.saveConfig();
    }
}