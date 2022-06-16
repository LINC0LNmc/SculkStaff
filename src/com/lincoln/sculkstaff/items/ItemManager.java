package com.lincoln.sculkstaff.items;

import com.lincoln.sculkstaff.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

import static com.lincoln.sculkstaff.Utils.color;

public class ItemManager {
    public static ItemStack soulStone;
    public static ItemStack soulFire;
    public static ItemStack sculkHaft;
    public static ItemStack sculkStaff;

    public static void init() {

        createSculkStaff();
        createSoulStone();
        createSoulFire();
        createSoulHaft();
    }
    public static void setItemTag(ItemMeta meta, String key, double value) {
        meta.getPersistentDataContainer().set(new NamespacedKey(Main.instanceOfMain, key), PersistentDataType.DOUBLE, value);
    }

    private static void createSoulStone() {
        ItemStack item = new ItemStack(Material.DIAMOND);
        ItemMeta meta = item.getItemMeta();
//        meta.addEnchant(Enchantment.LUCK, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(color("&3Soulstone"));
        meta.setCustomModelData(1);
        List<String> lore = new ArrayList<>();
        lore.add(color("&7The souls that previously resided in the warden"));
        lore.add(color("&7lurk within this stone."));
        lore.add(color("&8Material"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        soulStone = item;
    }
    private static void createSoulFire() {
        ItemStack item = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(1);
        meta.setDisplayName(color("&3Soul Fire"));
        List<String> lore = new ArrayList<>();
        lore.add(color("&7This flame flickers with the"));
        lore.add(color("&7essence of ancient souls."));
        lore.add(color("&8Material"));
        meta.setLore(lore);
        item.setItemMeta(meta);
        soulFire = item;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("soulfire"), item);
        sr.shape("BSB",
                 "SPS",
                 "BSB");
        sr.setIngredient('S', Material.SOUL_SAND);
        sr.setIngredient('B', Material.BONE_BLOCK);
        sr.setIngredient('P', Material.BLAZE_POWDER);
        Bukkit.getServer().addRecipe(sr);
    }
    private static void createSoulHaft() {
        ItemStack item = new ItemStack(Material.ECHO_SHARD);
        ItemMeta meta = item.getItemMeta();
//        meta.addEnchant(Enchantment.LUCK, 1, true);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(color("&3Sculk Haft"));
        List<String> lore = new ArrayList<>();
        lore.add(color("&7The magical properties of Lapis Lazuli combined with"));
        lore.add(color("&7with echo shards makes this haft strong."));
        lore.add(color("&8Material"));
        meta.setLore(lore);
        meta.setCustomModelData(1);
        item.setItemMeta(meta);
        sculkHaft = item;

        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("sculkhaft"), item);
        sr.shape("ELE",
                "LEL",
                "ELE");
        sr.setIngredient('L', Material.LAPIS_BLOCK);
        sr.setIngredient('E', Material.ECHO_SHARD);
        Bukkit.getServer().addRecipe(sr);
    }

    private static void createSculkStaff() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color("&3Sculk Staff"));
        List<String> lore = new ArrayList<>();
        lore.add(color("&6Ability: Sonic Boom &e&lRIGHT CLICK"));
        lore.add(color("&7Harness the otherworldly powers of the"));
        lore.add(color("&7Warden and unleash a sonic boom."));
        lore.add(color("&8Cool Down: &a300s"));
        meta.setLore(lore);
        setItemTag(meta, "Unique", Math.random());
        meta.setCustomModelData(1);
        item.setItemMeta(meta);
        sculkStaff = item;

//        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("sculkstaff"), item);
//
//        sr.shape("PSP",
//                 " H ",
//                 " H ");
//        sr.setIngredient('S', (RecipeChoice) new ItemStack(soulStone));
//        sr.setIngredient('P', Material.STICK);
//        sr.setIngredient('H', Material.STICK);
//
//        Bukkit.getServer().addRecipe(sr);
    }
}
