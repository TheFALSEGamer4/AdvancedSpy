package com.falsegamer.AdvancedSpy.commands;

import com.falsegamer.AdvancedSpy.spyMain;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdSpyCmd implements CommandExecutor {
    spyMain plugin;

    public CmdSpyCmd(spyMain plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("commandspy")) {
            Player p;
            UUID uuid;
            if (args.length == 0) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only players can use this command!");
                    return false;
                }

                if (!sender.hasPermission("spy.cmd.self")) {
                    sender.sendMessage(this.plugin.cm.message(this.plugin.cm.errorColor + "Insufficient permissions!"));
                    return false;
                }

                p = (Player)sender;
                uuid = p.getUniqueId();
                if (this.plugin.cmdSpy.contains(uuid)) {
                    this.plugin.cmdSpy.remove(uuid);
                    this.sendToggleMsgs(sender, "disabled");
                    return true;
                }

                this.plugin.cmdSpy.add(uuid);
                this.sendToggleMsgs(sender, "enabled");
                return true;
            }

            if (args.length > 0) {
                if (!sender.hasPermission("spy.cmd.others")) {
                    sender.sendMessage(this.plugin.cm.message(this.plugin.cm.errorColor + "Insufficient permissions!"));
                    return false;
                }

                p = Bukkit.getPlayerExact(args[0]);
                if (p == null) {
                    sender.sendMessage(this.plugin.cm.message(this.plugin.cm.errorColor + "Player not found!"));
                    return false;
                }

                uuid = p.getUniqueId();
                if (this.plugin.cmdSpy.contains(uuid)) {
                    this.plugin.cmdSpy.remove(uuid);
                    this.sendToggleMsgs(sender, p, "disabled");
                    return true;
                }

                this.plugin.cmdSpy.add(uuid);
                this.sendToggleMsgs(sender, p, "enabled");
                return true;
            }
        }

        return false;
    }

    private void sendToggleMsgs(CommandSender sender, String mode) {
        sender.sendMessage(this.plugin.cm.message(this.plugin.cm.cmdSpyToggleSelf.replaceAll("%mode%", mode)));
    }

    private void sendToggleMsgs(CommandSender sender, Player p, String mode) {
        sender.sendMessage(this.plugin.cm.message(this.plugin.cm.cmdSpyToggleSelf.replaceAll("%mode%", mode)));
        p.sendMessage(this.plugin.cm.message(this.plugin.cm.cmdSpyToggleOthers.replaceAll("%mode%", mode).replaceAll("%player%", p.getName())));
    }
}
