package com.lincoln.sculkstaff.listeners;

import com.lincoln.sculkstaff.Main;
import com.lincoln.sculkstaff.items.ItemManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.lincoln.sculkstaff.items.ItemManager.setItemTag;

public class EntityDeathEvent implements Listener {
    private Main main;

    public EntityDeathEvent(Main main) {
        this.main = main;
    }
    @EventHandler
    public void onEntityDeath(org.bukkit.event.entity.EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity.getType().equals(EntityType.WARDEN)) {
            Location location = entity.getLocation();
            location.getWorld().dropItemNaturally(location, ItemManager.soulStone);
        }
    }
}
