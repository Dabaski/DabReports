package com.dabaski.plugin.dabplayerreport;


import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TheListener implements Listener {

    @EventHandler
    public void login(PostLoginEvent event){
        DabPlayerReport.playerList.put(event.getPlayer().getDisplayName(),event.getPlayer().getUniqueId());
    }
    @EventHandler
    public void logout(PlayerDisconnectEvent event){
        ScheduledExecutorService balls = Executors.newScheduledThreadPool(2);
        final String thePlayer = event.getPlayer().getDisplayName();
        balls.schedule(() -> {
            DabPlayerReport.playerList.remove(thePlayer);
        }, 2, TimeUnit.MINUTES);
    }
}
