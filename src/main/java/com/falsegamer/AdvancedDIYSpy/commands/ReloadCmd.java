package com.falsegamer.AdvancedDIYSpy.commands;

import com.falsegamer.AdvancedDIYSpy.DIYspyMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCmd implements CommandExecutor {
    private DIYspyMain plugin;
    public ReloadCmd(DIYspyMain plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("spyreload")) {
            if (sender.hasPermission("spy.reload")) {
                this.plugin.cm.reload();
                sender.sendMessage(this.plugin.cm.message(this.plugin.cm.reloadMessage));
            } else {
                sender.sendMessage(this.plugin.cm.message("&cInsufficient permissions!"));
            }
        }

        return false;
    }
}
