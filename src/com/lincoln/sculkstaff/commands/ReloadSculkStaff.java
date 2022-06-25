package com.lincoln.sculkstaff.commands;

import com.lincoln.sculkstaff.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.List;

import static com.lincoln.sculkstaff.Utils.color;
import static com.lincoln.sculkstaff.Utils.msgPlayer;

public class ReloadSculkStaff extends Command {
    private File SculkStaffConfig;
    public ReloadSculkStaff(Main main) {
        super(main, "sculkstaffreload");
        SculkStaffConfig = (new File("plugins/SculkStaff", "SculkStaffConfig.yml"));
    }
    @Override
    public void execute(Player player, String[] args) {
        main.reloadConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File("plugins/SculkStaff", "SculkStaffConfig.yml"));
        int range = 0;
        int damage = 0;
        int coolDown = 0;
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
        msgPlayer(player, "&8============================");
        msgPlayer(player, "  &3&lSculkStaffPlugin Reloaded");
        msgPlayer(player, "       &5&lCurrent Values:");
        msgPlayer(player, "        &7Cooldown: &9" + coolDown);
        msgPlayer(player, "        &7Damage: &9" + damage);
        msgPlayer(player, "        &7Range: &9" + range);
        msgPlayer(player, "&8============================");
        for (Player playerForStaff : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i < playerForStaff.getInventory().getSize(); i++) {
                if (!(playerForStaff.getInventory().getItem(i)==null)) {
                    ItemStack item = playerForStaff.getInventory().getItem(i);
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
        }
        }
    }
