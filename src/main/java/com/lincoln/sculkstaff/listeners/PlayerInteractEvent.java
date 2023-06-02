package com.lincoln.sculkstaff.listeners;

import com.lincoln.sculkstaff.Main;
import com.lincoln.sculkstaff.items.ItemManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.*;

import static com.lincoln.sculkstaff.Utils.*;

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
            int range = 0;
            int damage = 0;
            int coolDown = 0;
            if (!(event.getClickedBlock() == null)) {
                if (blockList.contains(event.getClickedBlock().getType().toString().toLowerCase()) || event.getClickedBlock().getType().toString().toLowerCase().contains("button") || event.getClickedBlock().getType().toString().toLowerCase().contains("shulker_box")) {
                    return;
                }
            }
                if (event.getItem() != null) {
                    if (event.getItem().getItemMeta().getDisplayName().equals(ItemManager.sculkStaff.getItemMeta().getDisplayName()) && event.getItem().getItemMeta().getCustomModelData() == 1) {
//                        player.setCoolDown(Material.STICK, 120);
                        if (!sonicBoomCD.containsKey(player.getUniqueId())) {
                            FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/SculkStaff", "SculkStaffConfig.yml"));


                            if (config.get("SculkStaffRange") instanceof Integer) {
                                if (config.getInt("SculkStaffRange") >= 0) {
                                    range = (config.getInt("SculkStaffRange"));
                                } else {
                                    range = 40;
                                }
                            } else {
                                range = 40;
                            }
                            if (config.get("SculkStaffDamage") instanceof Integer) {
                                if (config.getInt("SculkStaffDamage") >= 0) {
                                    damage = (config.getInt("SculkStaffDamage"));
                                } else {
                                    damage = 150;
                                }
                            } else {
                                damage = 150;
                            }
                            if (config.get("SculkStaffCoolDown") instanceof Integer) {
                                if (config.getInt("SculkStaffCoolDown") >= 0) {
                                    coolDown = (config.getInt("SculkStaffCoolDown"));
                                } else {
                                    coolDown = 300;
                                }
                            } else {
                                coolDown = 300;
                            }
                            int finalRange = range;
                            int finalDamage = damage;
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
                                            if (!(livingEntity.getUniqueId() == player.getUniqueId())) {
                                                livingEntity.damage(finalDamage);
                                            }
                                        }
                                    }

                                    location.subtract(x, y, z);

                                    if (t > finalRange || !isPassableBlock) { //int here is length
                                        this.cancel();
                                    }
                                }
                            }.runTaskTimer(main, 0, 1);
                            sonicBoomCD.put(player.getUniqueId(), coolDown + 1);
                            new BukkitRunnable() {
                                public void run() {
                                    int left = sonicBoomCD.get(player.getUniqueId());
                                    if (left == 1) {
                                        sonicBoomCD.remove(player.getUniqueId());
                                        msgPlayerAB(player, "&7Your &3&lSculkStaff &r&7is ready!");
                                        soundPlayer(player, Sound.ENTITY_EVOKER_CAST_SPELL, 1F, 0.5F);
                                        cancel();
                                    } else {
                                        sonicBoomCD.put(player.getUniqueId(), (left - 1));
                                    }
                                }
                            }.runTaskTimer(main, 0L, 20L);
//                            ItemStack staff = null;
                            for (int i = 0; i < player.getInventory().getSize(); i++) {
                                if (!(player.getInventory().getItem(i)==null)) {
                                    ItemStack item = player.getInventory().getItem(i);
                                    if (item.getItemMeta().getDisplayName().equals(color("&3Sculk Staff")) && item.getItemMeta().getCustomModelData()==1) {
                                        List<String> lore = item.getItemMeta().getLore();
                            if (!lore.get(3).equals(color("&8Cool Down: &a"+ coolDown + "s"))) {
                                lore.remove(3);
                                ItemMeta meta = item.getItemMeta();
//                            List<String> lore = item.getItemMeta().getLore();
                                lore.add(color("&8Cool Down: &a" + coolDown + "s"));
                                meta.setLore(lore);
                                item.setItemMeta(meta);
                            }
                                    }
                                }
                            }
//                            if (!(player.getInventory().getItemInMainHand().getType()==Material.AIR)) {
//                                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(color("&3Sculk Staff")) && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1) {
//                                    staff = player.getInventory().getItemInMainHand();
//                                } else {
//                                    staff = player.getInventory().getItemInOffHand();
//                                }
//                            } else {
//                                staff = player.getInventory().getItemInOffHand();
//                            }
//                            List<String> lore = staff.getItemMeta().getLore();
//                            if (!lore.get(3).equals(color("&8Cool Down: &a"+ coolDown + "s"))) {
//                                lore.remove(3);
//                                ItemMeta meta = staff.getItemMeta();
////                            List<String> lore = staff.getItemMeta().getLore();
//                                lore.add(color("&8Cool Down: &a"+coolDown + "s"));
//                                meta.setLore(lore);
//                                staff.setItemMeta(meta);
//                            }
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
