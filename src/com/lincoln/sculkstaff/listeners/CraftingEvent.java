package com.lincoln.sculkstaff.listeners;

import com.lincoln.sculkstaff.Main;
import com.lincoln.sculkstaff.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

import static com.lincoln.sculkstaff.items.ItemManager.setItemTag;

public class CraftingEvent implements Listener {
    private Main main;

    public CraftingEvent(Main main) {
        this.main = main;
    }
    static HashMap<Integer, ItemStack> itemRecipe = new HashMap<>() {{
        put(0, ItemManager.soulFire);
        put(1, ItemManager.soulStone);
        put(2, ItemManager.soulFire);
        put(3, new ItemStack(Material.DIAMOND));
        put(4, ItemManager.sculkHaft);
        put(5, new ItemStack(Material.DIAMOND));
        put(6, new ItemStack(Material.NETHERITE_INGOT));
        put(7, ItemManager.sculkHaft);
        put(8, new ItemStack(Material.NETHERITE_INGOT));
    }};
    @EventHandler
    public void onPlayerCraftItem(PrepareItemCraftEvent event) {
        if (event.getInventory().getMatrix().length < 9) {
            return;
        }

        ItemStack item = new ItemStack(ItemManager.sculkStaff);
        ItemMeta itemMeta = item.getItemMeta();
        setItemTag(itemMeta, "Unique", Math.random());
        item.setItemMeta(itemMeta);
        checkCraft(item, event.getInventory(), itemRecipe);
    }
    public void checkCraft(ItemStack result, CraftingInventory inv, HashMap<Integer, ItemStack> ingredients) {
        ItemStack[] matrix = inv.getMatrix();
        for(int i = 0; i < 9; i++) {
            if (ingredients.containsKey(i)) {
                if(matrix[i] == null || !matrix[i].equals(ingredients.get(i))){
                    return;
                }
            } else {
                if(matrix[i] != null) {
                    return;
                }
            }
        }
        inv.setResult(result);
    }
}
