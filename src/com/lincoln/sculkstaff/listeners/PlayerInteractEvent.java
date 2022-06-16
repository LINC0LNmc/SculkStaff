package com.lincoln.sculkstaff.listeners;

import com.lincoln.sculkstaff.Main;
import com.lincoln.sculkstaff.items.ItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

import static com.lincoln.sculkstaff.Utils.msgPlayer;
import static com.lincoln.sculkstaff.Utils.soundPlayer;

public class PlayerInteractEvent implements Listener {

    private Main main;
    private Map<UUID, Integer> sonicBoomCD = new HashMap<>();
    private List<String> blockList = Arrays.asList("enchanting_table", "crafting_table", "chest", "ender_chest", "trapped_chest", "furnace", "blast_furnace", "smoker", "barrel", "loom", "stonecutter", "smithing_table", "grindstone", "composter", "smithing_table", "lectern", "note_block");

    public PlayerInteractEvent(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onRightClick(org.bukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (!(event.getClickedBlock() == null)) {
                if (blockList.contains(event.getClickedBlock().getType().toString().toLowerCase()) || event.getClickedBlock().getType().toString().toLowerCase().contains("button") || event.getClickedBlock().getType().toString().toLowerCase().contains("shulker_box")) {
                    return;
                }
            }
                if (event.getItem() != null) {
                    if (event.getItem().getItemMeta().getDisplayName().equals(ItemManager.sculkStaff.getItemMeta().getDisplayName())) {
                        if (!sonicBoomCD.containsKey(player.getUniqueId())) {
                            new BukkitRunnable() {
                                double t = 0;
                                Location location = player.getLocation();
                                Boolean isPassableBlock = true;
                                Vector direction = location.getDirection().normalize();

                                public void run() {
                                    t = t + 3.2; // Adding bigger numbers increases distance between particles
                                    double x = direction.getX() * t;
                                    double y = direction.getY() * t + 1.4;
                                    double z = direction.getZ() * t;
                                    location.add(x, y, z);
                                    location.getWorld().spawnParticle(Particle.SONIC_BOOM, location, 1, 0, 0, 0, 0);
                                    if (!location.getBlock().isPassable()) {
                                        isPassableBlock = false;
                                    }
                                    location.getWorld().playSound(location, Sound.ENTITY_WARDEN_SONIC_BOOM, 0.5F, 0.7F);

                                    List<Entity> entities = (List<Entity>) location.getWorld().getNearbyEntities(location, 2, 2, 2);
                                    for (Entity entity : entities) {
                                        if (entity instanceof LivingEntity) {
                                            LivingEntity livingEntity = (LivingEntity) entity;
                                            livingEntity.damage(150);
                                        }
                                    }

                                    location.subtract(x, y, z);

                                    if (t > 40 || !isPassableBlock) { //int here is length
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer(main, 0, 1);
                            sonicBoomCD.put(player.getUniqueId(), 301);
                            new BukkitRunnable() {
                                public void run() {
                                    int left = sonicBoomCD.get(player.getUniqueId());
                                    if (left == 1) {
                                        sonicBoomCD.remove(player.getUniqueId());
                                        cancel();
                                    } else {
                                        sonicBoomCD.put(player.getUniqueId(), (left - 1));
                                    }
                                }
                            }.runTaskTimer(main, 0L, 20L);
                        } else {
                            int total = sonicBoomCD.get(player.getUniqueId());
                            msgPlayer(player, "&cSonic Boom is in cooldown for " + total + " seconds");
                            soundPlayer(player, Sound.BLOCK_NOTE_BLOCK_BASS, 2F, 0.5F);
                        }
                    }
            }
        }
    }
}
