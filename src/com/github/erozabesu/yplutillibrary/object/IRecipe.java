package com.github.erozabesu.yplutillibrary.object;

import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 *
 * @author erozabesu
 *
 */
public interface IRecipe {

    public ItemStack getResult();
    public Recipe getRecipe();
    public List<IRecipeMaterial> getMaterialList();
    public int getMaterialAmount();

    /**
     *
     * @author erozabesu
     *
     */
    public interface IRecipeMaterial {
       public ItemStack getMaterialItem();
    }
}