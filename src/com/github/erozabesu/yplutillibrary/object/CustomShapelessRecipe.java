package com.github.erozabesu.yplutillibrary.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapelessRecipe;

/**
 *
 * @author erozabesu
 *
 */
public class CustomShapelessRecipe implements IRecipe {

    /** クラフト結果のアイテム */
    private ItemStack result;

    /** カスタムレシピ */
    private Recipe recipe;

    /** カスタムマテリアルリスト */
    private List<IRecipeMaterial> materialList;

    /** 使用される素材の数 */
    private int materialAmount;

    public CustomShapelessRecipe(ItemStack craftResultItem, ShapelessRecipeMaterial... recipeMaterialList) {
        // レシピの格納
        ShapelessRecipe recipe = new ShapelessRecipe(craftResultItem);
        for (ShapelessRecipeMaterial recipeMaterial : recipeMaterialList) {
            recipe.addIngredient(recipeMaterial.getAmount(), recipeMaterial.getMaterialItem().getType(), recipeMaterial.getRawData());
        }
        this.setRecipe(recipe);

        // ShapelessRecipeMaterialの型をIRecipeMaterialに変換し格納
        List<IRecipeMaterial> materialList = new ArrayList<IRecipeMaterial>();
        for (ShapelessRecipeMaterial material : recipeMaterialList) {
            materialList.add(material);
        }
        this.setMaterialList(materialList);

        this.setResult(craftResultItem);

        this.setMaterialAmount(recipeMaterialList.length);
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return クラフト結果のアイテム */
    public ItemStack getResult() {
        return result;
    }

    /** @return カスタムレシピ */
    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    /** @return カスタムマテリアルリスト */
    @Override
    public List<IRecipeMaterial> getMaterialList() {
        return materialList;
    }

    /** @return 使用される素材の数 */
    @Override
    public int getMaterialAmount() {
        return materialAmount;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param result クラフト結果のアイテム */
    public void setResult(ItemStack result) {
        this.result = result;
    }

    /** @param recipe カスタムレシピ */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /** @param materialList カスタムマテリアルリスト */
    public void setMaterialList(List<IRecipeMaterial> materialList) {
        this.materialList = materialList;
    }

    /** @param materialAmount 使用される素材の数 */
    public void setMaterialAmount(int materialAmount) {
        this.materialAmount = materialAmount;
    }

    //〓 Inner Object Class 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
    *
    * @author erozabesu
    *
    */
   public static class ShapelessRecipeMaterial implements IRecipeMaterial {

       /** 素材マテリアル */
       private ItemStack item;

       /** クラフトに必要な数量 */
       private int amount;

       /** メタデータ */
       private int rawData;

       public ShapelessRecipeMaterial(ItemStack material) {
           this.setMaterialItem(material);
           this.setAmount(1);
           this.setRawData(-1);
       }

       public ShapelessRecipeMaterial(ItemStack material, int amount) {
           this.setMaterialItem(material);
           this.setAmount(amount);
           this.setRawData(-1);
       }

       public ShapelessRecipeMaterial(ItemStack material, int amount, int rawData) {
           this.setMaterialItem(material);
           this.setAmount(amount);
           this.setRawData(rawData);
       }

       //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

       /** @return 素材マテリアル */
       @Override
       public ItemStack getMaterialItem() {
           return item;
       }

       /** @return クラフトに必要な数量 */
       public int getAmount() {
           return amount;
       }

       /** @return メタデータ */
       public int getRawData() {
           return rawData;
       }

       //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

       /** @param material 素材マテリアル */
       public void setMaterialItem(ItemStack material) {
           this.item = material;
       }

       /** @param amount クラフトに必要な数量 */
       public void setAmount(int amount) {
           this.amount = amount;
       }

       /** @param rawData メタデータ */
       public void setRawData(int rawData) {
           this.rawData = rawData;
       }
   }
}
