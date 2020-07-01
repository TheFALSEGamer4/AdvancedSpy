package com.falsegamer.AdvancedDIYSpy.commands;

import com.falsegamer.AdvancedDIYSpy.DIYspyMain;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SocialSpyCmd implements CommandExecutor {
    DIYspyMain plugin;

    public SocialSpyCmd(DIYspyMain plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("socialspy")) {
            Player p;
            UUID uuid;
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only players can use this social!");
                    return false;
                }

                if (!sender.hasPermission("spy.social.self")) {
                    sender.sendMessage(this.plugin.cm.message(this.plugin.cm.errorColor + "Insufficient permissions!"));
                    return false;
                }

                p = (Player)sender;
                uuid = p.getUniqueId();
                if (this.plugin.socialSpy.contains(uuid)) {
                    this.plugin.socialSpy.remove(uuid);
                    this.sendToggleMsgs(sender, "disabled");
                    return true;
                }

                this.plugin.socialSpy.add(uuid);
                this.sendToggleMsgs(sender, "enabled");
                return true;
            }

            if (args.length > 0) {
                if (!sender.hasPermission("spy.social.others")) {
                    sender.sendMessage(this.plugin.cm.message(this.plugin.cm.errorColor + "Insufficient permissions!"));
                    return false;
                }

                p = Bukkit.getPlayerExact(args[0]);
                if (p == null) {
                    sender.sendMessage(this.plugin.cm.message(this.plugin.cm.errorColor + "Player not found!"));
                    return false;
                }

                uuid = p.getUniqueId();
                if (this.plugin.socialSpy.contains(uuid)) {
                    this.plugin.socialSpy.remove(uuid);
                    this.sendToggleMsgs(sender, p, "disabled");
                    return true;
                }

                this.plugin.socialSpy.add(uuid);
                this.sendToggleMsgs(sender, p, "enabled");
                return true;
            }
        }

        return false;
    }

    private void sendToggleMsgs(CommandSender sender, String mode) {
        sender.sendMessage(this.plugin.cm.message(this.plugin.cm.socialSpyToggleSelf.replaceAll("%mode%", mode)));
    }

    private void sendToggleMsgs(CommandSender sender, Player p, String mode) {
        sender.sendMessage(this.plugin.cm.message(this.plugin.cm.socialSpyToggleSelf.replaceAll("%mode%", mode)));
        p.sendMessage(this.plugin.cm.message(this.plugin.cm.socialSpyToggleOthers.replaceAll("%mode%", mode).replaceAll("%player%", p.getName())));
    }
}
