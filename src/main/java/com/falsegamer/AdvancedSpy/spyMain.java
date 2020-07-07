package com.falsegamer.AdvancedSpy;

import com.falsegamer.AdvancedSpy.commands.CmdSpyCmd;
import com.falsegamer.AdvancedSpy.commands.ReloadCmd;
import com.falsegamer.AdvancedSpy.commands.SocialSpyCmd;
import com.falsegamer.AdvancedSpy.listeners.CmdListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.HttpsURLConnection;

public class spyMain extends JavaPlugin  implements Listener {
    public List<UUID> cmdSpy = new ArrayList();
    public List<UUID> socialSpy = new ArrayList();
    public HashMap<UUID, UUID> lastMsg = new HashMap();
    public ConfigManager cm = new ConfigManager(this);
    public CmdListener cl = new CmdListener(this);
    public CmdSpyCmd csc = new CmdSpyCmd(this);
    public SocialSpyCmd ssc = new SocialSpyCmd(this);
    public ReloadCmd rc = new ReloadCmd(this);
    public parserClass pc = new parserClass(this);

    public String updateAvailable;
    public String currentVersion;
    public String newVersion;

    @Override
    public void onEnable() {
        this.cm.setupConfig();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        console.sendMessage(ChatColor.BLUE + "AdvancedSpy");
        console.sendMessage(ChatColor.BLUE + "Version 1.1");
        console.sendMessage(ChatColor.GREEN + "Enabled Yayaya!");
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        this.getServer().getPluginManager().registerEvents(this.cl, this);
        this.getCommand("commandspy").setExecutor(this.csc);
        this.getCommand("socialspy").setExecutor(this.ssc);
        this.getCommand("spyreload").setExecutor(this.rc);
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) new URL("https://api.spigotmc.org/legacy/update.php?resource=81162").openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            newVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger logger = this.getLogger();
        getServer().getPluginManager().registerEvents(this, this);
        currentVersion = this.getDescription().getVersion();


        if (currentVersion.equalsIgnoreCase(String.valueOf(newVersion))) {
            logger.info("There is not a new update available. ");
            updateAvailable = "false";
        } else {
            logger.info("There is a new update available. Current version: " + currentVersion + ", new version: " + newVersion);
            updateAvailable = "true";
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLogin(PlayerJoinEvent event)
    {

        Player p = event.getPlayer();
        if(p.hasPermission("spy.notify")){
            if(updateAvailable.equals("true")){
                String message = this.cm.updateAvailableMessage;
                message = message.replaceAll("%currentversion%", currentVersion);
                message = message.replaceAll("%newversion%", newVersion);
                message = message.replaceAll("%downloadurl%", "https://www.spigotmc.org/resources/advancedspy.81162/");
                p.sendMessage(this.cm.message(message));
            }
        }
    }

    public void onDisable() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        console.sendMessage(ChatColor.BLUE + "AdvancedSpy");
        console.sendMessage(ChatColor.BLUE + "Version 1.1");
        console.sendMessage(ChatColor.RED + "Disabled!");
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
