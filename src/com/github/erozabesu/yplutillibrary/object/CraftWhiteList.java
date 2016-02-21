package com.github.erozabesu.yplutillibrary.object;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author erozabesu
 *
 */
public class CraftWhiteList {

    /** クラフトに使用される素材 */
    private ItemStack craftMaterial;

    /** クラフトを許可するクラフト結果のアイテム */
    private List<ItemStack> craftResultList;

    public CraftWhiteList(ItemStack craftMaterial, ItemStack... craftResultList) {
        this.setCraftMaterial(craftMaterial);
        this.setCraftResultList(Arrays.asList(craftResultList));
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return クラフトに使用される素材 */
    public ItemStack getCraftMaterial() {
        return craftMaterial;
    }

    /** @return クラフトを許可するクラフト結果のアイテム */
    public List<ItemStack> getCraftResultList() {
        return craftResultList;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param craftMaterial クラフトに使用される素材 */
    public void setCraftMaterial(ItemStack craftMaterial) {
        this.craftMaterial = craftMaterial;
    }

    /** @param craftResultList クラフトを許可するクラフト結果のアイテム */
    public void setCraftResultList(List<ItemStack> craftResultList) {
        this.craftResultList = craftResultList;
    }
}
