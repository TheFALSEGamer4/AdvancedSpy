package com.falsegamer.AdvancedSpy;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    private spyMain plugin;
    private FileConfiguration config;
    public List<String> blacklist;
    public List<String> socialCmds;
    public List<String> replyCmds;
    public String prefix;
    public String cmdSpyToggleSelf;
    public String cmdSpyToggleOthers;
    public String socialSpyToggleSelf;
    public String socialSpyToggleOthers;
    public String errorColor;
    public String cmdSpyFormat;
    public String socialSpyFormat;
    public String reloadMessage;
    public void reload() {
        plugin.saveResource("config.yml", false);
        plugin.reloadConfig();
        config = plugin.getConfig();
        setupConfig();
    }
    public ConfigManager(spyMain plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.plugin.getConfig().options().copyDefaults(true);
        this.plugin.saveDefaultConfig();
        setupConfig();
    }

    public void setupConfig() {
        this.blacklist = this.config.getStringList("AdvancedSpy.blacklist");
        this.socialCmds = this.config.getStringList("AdvancedSpy.social-commands");
        this.replyCmds = this.config.getStringList("AdvancedSpy.reply-commands");
        this.prefix = this.cc(this.config.getString("AdvancedSpy.prefix"));
        this.cmdSpyToggleSelf = this.cc(this.config.getString("AdvancedSpy.command-spy-toggle-self"));
        this.cmdSpyToggleOthers = this.cc(this.config.getString("AdvancedSpy.command-spy-toggle-others"));
        this.socialSpyToggleSelf = this.cc(this.config.getString("AdvancedSpy.social-spy-toggle-self"));
        this.socialSpyToggleOthers = this.cc(this.config.getString("AdvancedSpy.social-spy-toggle-others"));
        this.errorColor = this.config.getString("AdvancedSpy.error-message-color");
        this.cmdSpyFormat = this.cc(this.config.getString("AdvancedSpy.command-spy-format"));
        this.socialSpyFormat = this.cc(this.config.getString("AdvancedSpy.social-spy-format"));
        this.reloadMessage = this.cc(this.config.getString("AdvancedSpy.reload-message"));
    }

    public String message(String message) {
        return this.prefix + " " + this.cc(message);
    }

    public String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
