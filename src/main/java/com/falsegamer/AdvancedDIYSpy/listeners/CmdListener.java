//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.falsegamer.AdvancedDIYSpy.listeners;

import com.falsegamer.AdvancedDIYSpy.DIYspyMain;
import java.util.Iterator;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CmdListener implements Listener {
    DIYspyMain plugin;

    public CmdListener(DIYspyMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage().replaceAll("/", "");
        String[] args = message.split(" ");
        String cmd = args[0];
        if (args.length != 0) {
            if (this.plugin.cm.blacklist.contains(cmd)) {
                this.sendCmdSpyMsgs(p, message, true);
            } else {
                String replyMsg = "";
                String msg = "";

                for(int i = 1; i < args.length; ++i) {
                    replyMsg = replyMsg + args[i] + " ";
                    if (i > 1) {
                        msg = msg + args[i] + " ";
                    }
                }

                DIYspySocialEvent e1;
                Player recipient;
                if (this.plugin.cm.replyCmds.contains(cmd)) {
                    if (args.length >= 2) {
                        recipient = Bukkit.getPlayer((UUID)this.plugin.lastMsg.get(p.getUniqueId()));
                        if (Bukkit.getOnlinePlayers().contains(recipient)) {
                            e1 = new DIYspySocialEvent(p, recipient, cmd, replyMsg);
                            Bukkit.getPluginManager().callEvent(e1);
                            if (e1.isCancelled()) {
                                e.setCancelled(true);
                            } else {
                                this.plugin.lastMsg.put(e1.getSender().getUniqueId(), e1.getRecipient().getUniqueId());
                                this.plugin.lastMsg.put(e1.getRecipient().getUniqueId(), e1.getSender().getUniqueId());
                                this.sendSocialSpyMsgs(e1.getSender(), e1.getRecipient(), e1.getMessage());
                            }
                        }
                    }
                } else if (this.plugin.cm.socialCmds.contains(cmd)) {
                    if (args.length >= 3) {
                        recipient = Bukkit.getPlayerExact(args[1]);
                        if (recipient != null) {
                            e1 = new DIYspySocialEvent(p, recipient, cmd, msg);
                            Bukkit.getPluginManager().callEvent(e1);
                            e.setCancelled(e1.isCancelled());
                            e.setPlayer(e1.getSender());
                            this.plugin.lastMsg.put(e1.getSender().getUniqueId(), e1.getRecipient().getUniqueId());
                            this.plugin.lastMsg.put(e1.getRecipient().getUniqueId(), e1.getSender().getUniqueId());
                            this.sendSocialSpyMsgs(e1.getSender(), e1.getRecipient(), e1.getMessage());
                        }
                    }
                } else {
                    this.sendCmdSpyMsgs(p, message, false);
                }
            }
        }
    }

    private void sendCmdSpyMsgs(Player sender, String message, boolean isBlacklisted) {
        String pworld= sender.getWorld().toString();
        int pw1 = pworld.indexOf('=')+1;
        int pw2 = pworld.indexOf('}');
        pworld = pworld.substring(pw1, pw2);
        pworld = pworld.substring(0, 1).toUpperCase() + pworld.substring(1);
        String spy = this.plugin.cm.cmdSpyFormat
                .replaceAll("%sender%", sender.getName())
                .replaceAll("%command%", "/" + message)
                .replaceAll("%playerworld%", pworld)
                ;
        Iterator var5 = this.plugin.cmdSpy.iterator();

        while(true) {
            Player p;
            do {
                do {
                    do {
                        do {
                            if (!var5.hasNext()) {
                                return;
                            }

                            UUID uuid = (UUID)var5.next();
                            p = Bukkit.getPlayer(uuid);
                        } while(p == null);
                    } while(p == sender);
                } while(isBlacklisted && !p.hasPermission("spy.cmd.blacklist"));
            } while(sender.hasPermission("spy.cmd.admin") && !p.hasPermission("spy.cmd.admin"));

            p.sendMessage(spy);
        }
    }

    private void sendSocialSpyMsgs(Player sender, Player recipient, String msg) {
        String spy = this.plugin.cm.socialSpyFormat.replaceAll("%sender%", sender.getName()).replaceAll("%recipient%", recipient.getName()).replaceAll("%message%", msg);
        Iterator var5 = this.plugin.socialSpy.iterator();

        while(true) {
            Player p;
            do {
                do {
                    do {
                        do {
                            if (!var5.hasNext()) {
                                return;
                            }

                            UUID uuid = (UUID)var5.next();
                            p = Bukkit.getPlayer(uuid);
                        } while(p == null);
                    } while(p == sender);
                } while(p == recipient);
            } while((sender.hasPermission("spy.social.admin") || recipient.hasPermission("spy.social.admin")) && !p.hasPermission("spy.social.admin"));

            p.sendMessage(spy);
        }
    }
}
