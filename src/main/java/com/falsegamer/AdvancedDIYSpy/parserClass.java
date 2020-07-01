package com.falsegamer.AdvancedDIYSpy;

public class parserClass {
    private final DIYspyMain plugin;

    public parserClass(DIYspyMain plugin) {
        this.plugin = plugin;
    }
    public String worldParser(String world){
        String playerWorld = world;
        int pw1 = playerWorld.indexOf('=')+1;
        int pw2 = playerWorld.indexOf('}');
        playerWorld = playerWorld.substring(pw1, pw2);

        if (playerWorld.contains("_")){
            int thingy = playerWorld.indexOf("_")+1;
            playerWorld = playerWorld.substring(thingy);
            
            if (playerWorld.contains("_")){
                playerWorld = playerWorld.replace("_", " ");
                int thingy2 = playerWorld.indexOf(" ")+1;
                playerWorld = playerWorld.substring(0,thingy2)+ playerWorld.substring(thingy2, thingy2+1).toUpperCase() + playerWorld.substring(thingy2+1);
            }
        }
        playerWorld = playerWorld.substring(0, 1).toUpperCase() + playerWorld.substring(1);
        return playerWorld;
    }
}
