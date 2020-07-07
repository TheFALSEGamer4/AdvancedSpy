package com.falsegamer.AdvancedSpy;

import com.falsegamer.AdvancedSpy.commands.CmdSpyCmd;
import com.falsegamer.AdvancedSpy.commands.ReloadCmd;
import com.falsegamer.AdvancedSpy.commands.SocialSpyCmd;
import com.falsegamer.AdvancedSpy.listeners.CmdListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class spyMain extends JavaPlugin {
    public List<UUID> cmdSpy = new ArrayList();
    public List<UUID> socialSpy = new ArrayList();
    public HashMap<UUID, UUID> lastMsg = new HashMap();
    public ConfigManager cm = new ConfigManager(this);
    public CmdListener cl = new CmdListener(this);
    public CmdSpyCmd csc = new CmdSpyCmd(this);
    public SocialSpyCmd ssc = new SocialSpyCmd(this);
    public ReloadCmd rc = new ReloadCmd(this);
    public parserClass pc = new parserClass(this);


    public void onEnable() {
        this.cm.setupConfig();
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        console.sendMessage(ChatColor.BLUE + "AdvancedSpy");
        console.sendMessage(ChatColor.BLUE + "Version 1.0");
        console.sendMessage(ChatColor.GREEN + "Enabled Yayaya!");
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        this.getServer().getPluginManager().registerEvents(this.cl, this);
        this.getCommand("commandspy").setExecutor(this.csc);
        this.getCommand("socialspy").setExecutor(this.ssc);
        this.getCommand("spyreload").setExecutor(this.rc);

        Logger logger = this.getLogger();
        new UpdateChecker(this, 81162).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info("There is not a new update available.");
            } else {
                logger.info("There is a new update available.");
            }
        });
    }

    public void onDisable() {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        console.sendMessage(ChatColor.BLUE + "AdvancedSpy");
        console.sendMessage(ChatColor.BLUE + "Version 1.0");
        console.sendMessage(ChatColor.RED + "Disabled!");
        console.sendMessage(ChatColor.DARK_GREEN + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
