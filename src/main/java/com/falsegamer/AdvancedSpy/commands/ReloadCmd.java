package com.falsegamer.AdvancedSpy.commands;

import com.falsegamer.AdvancedSpy.spyMain;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCmd implements CommandExecutor {
    private spyMain plugin;
    public ReloadCmd(spyMain plugin) {
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
