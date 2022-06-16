package com.lincoln.sculkstaff.listeners;

import com.lincoln.sculkstaff.Main;
import com.lincoln.sculkstaff.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class CraftingEvent implements Listener {
    private Main main;

    public CraftingEvent(Main main) {
        this.main = main;
    }
    static HashMap<Integer, ItemStack> itemRecipe = new HashMap<>();
    @EventHandler
    public void onPlayerCraftItem(PrepareItemCraftEvent event) {
        if (event.getInventory().getMatrix().length < 9) {
            return;
        }
        itemRecipe.put(0, ItemManager.soulFire);
        itemRecipe.put(1, ItemManager.soulStone);
        itemRecipe.put(2, ItemManager.soulFire);
        itemRecipe.put(3, new ItemStack(Material.DIAMOND));
        itemRecipe.put(4, ItemManager.sculkHaft);
        itemRecipe.put(5, new ItemStack(Material.DIAMOND));
        itemRecipe.put(6, new ItemStack(Material.NETHERITE_INGOT));
        itemRecipe.put(7, ItemManager.sculkHaft);
        itemRecipe.put(8, new ItemStack(Material.NETHERITE_INGOT));
        checkCraft(new ItemStack(ItemManager.sculkStaff), event.getInventory(), itemRecipe);
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
