package com.lincoln.sculkstaff.listeners;

import com.lincoln.sculkstaff.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoinEvent implements Listener {
    private Main main;

    public OnJoinEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setResourcePack();
    }

}
