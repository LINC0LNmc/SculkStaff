package com.lincoln.sculkstaff;

import com.lincoln.sculkstaff.items.ItemManager;
import com.lincoln.sculkstaff.listeners.CraftingEvent;
import com.lincoln.sculkstaff.listeners.EntityDeathEvent;
import com.lincoln.sculkstaff.listeners.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

import static com.lincoln.sculkstaff.Utils.log;

public class Main extends JavaPlugin {

    public static Main instanceOfMain;

    private static Logger logger;

    @Override
    public void onEnable() {


        instanceOfMain = this;
        logger = getLogger();
        ItemManager.init();
        getServer().getPluginManager().registerEvents(new EntityDeathEvent(this), this);
        getServer().getPluginManager().registerEvents(new CraftingEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEvent(this), this);
        log("SculkStaff successfully loaded!");
    }

    @Override
    public void onDisable() {
        log("§SculkStaff successfully disabled.");
    }
    public static Logger getPluginLogger() {
        return logger;
    }

}
