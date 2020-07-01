//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.falsegamer.AdvancedDIYSpy.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DIYspySocialEvent extends Event implements Cancellable {
    private Player sender;
    private Player recipient;
    private String command;
    private String message;
    private boolean isCancelled;
    private static final HandlerList HANDLERS = new HandlerList();

    public DIYspySocialEvent(Player sender, Player recipient, String command, String message) {
        this.sender = sender;
        this.recipient = recipient;
        this.command = command;
        this.message = message;
        this.isCancelled = false;
    }

    public Player getSender() {
        return this.sender;
    }

    public Player getRecipient() {
        return this.recipient;
    }

    public String getCommand() {
        return this.command;
    }

    public String getMessage() {
        return this.message;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public void setRecipient(Player recipient) {
        this.recipient = recipient;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
