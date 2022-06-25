package com.lincoln.sculkstaff.listeners;

import com.lincoln.sculkstaff.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.lincoln.sculkstaff.Utils.msgPlayer;

public class OnJoinEvent implements Listener {
    private Main main;

    public OnJoinEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setResourcePack("https://github.com/LINC0LNmc/ResourcePacks/blob/master/SculkStaff.zip?raw=true");
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 160, 5));
        msgPlayer(player, "&3[SculkStaff] You have been given temporary immunity to load your texturepack!");

    }

}
