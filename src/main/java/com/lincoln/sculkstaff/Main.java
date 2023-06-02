package com.lincoln.sculkstaff;

import com.lincoln.sculkstaff.commands.ReloadSculkStaff;
import com.lincoln.sculkstaff.items.ItemManager;
import com.lincoln.sculkstaff.listeners.CraftingEvent;
import com.lincoln.sculkstaff.listeners.EntityDeathEvent;
import com.lincoln.sculkstaff.listeners.OnJoinEvent;
import com.lincoln.sculkstaff.listeners.PlayerInteractEvent;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import static com.lincoln.sculkstaff.Utils.log;

public class Main extends JavaPlugin {

    public static Main instanceOfMain;

    private static Logger logger;

    public File customConfigFile;
    public FileConfiguration customConfig;

    @Override
    public void onEnable() {
        createCustomConfig();
        new ReloadSculkStaff(this);
        instanceOfMain = this;
        logger = getLogger();
        ItemManager.init();
        getServer().getPluginManager().registerEvents(new OnJoinEvent(this), this);
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
    public void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "SculkStaffConfig.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            FileConfiguration custom = YamlConfiguration.loadConfiguration(customConfigFile);
            saveResource("SculkStaffConfig.yml", false);
            custom.set("SculkStaffDamage", 150);
            custom.set("SculkStaffCoolDown", 300);
            custom.set("SculkStaffRange", 40);
            try {
                custom.save(customConfigFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        customConfig = new YamlConfiguration();

//        customConfig.addDefault(".SculkStaffDamage:", 150);
//        customConfig.addDefault(".CoolDown:", 300);

        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
//        customConfig.set(".SculkStaffDamage:", 150);
//        customConfig.set(".SculkStaffCoolDown:", 300);
    }
    String configName = "SculkStaffConfig";

}
