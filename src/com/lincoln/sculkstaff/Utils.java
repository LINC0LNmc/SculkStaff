package com.lincoln.sculkstaff;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Logger;

public class Utils {

    private static Logger logger = Main.getPluginLogger();
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static void msgPlayer(Player player, String... strings) {
        for (String string : strings) {
            player.sendMessage(color(string));
        }
    }
    public static void log(String... messages) {
        for (String message : messages) {
            logger.info(message);
        }
    }
    public static void soundPlayer(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
