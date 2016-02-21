package com.github.erozabesu.yplutillibrary.object;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 *
 * @author erozabesu
 *
 */
public class CustomShapedRecipe implements IRecipe {

    /** クラフト結果のアイテム */
    private ItemStack result;

    /** カスタムレシピ */
    private Recipe recipe;

    /** カスタムマテリアルリスト */
    private List<IRecipeMaterial> recipeMaterialList;

    /** 3*3レシピの1行目の配置を抽象表現した文字列 */
    private String recipeCode;

    /** 3*3レシピの2行目の配置を抽象表現した文字列 */
    private String recipeCode2;

    /** 3*3レシピの3行目の配置を抽象表現した文字列 */
    private String recipeCode3;

    /** 使用される素材の数 */
    private int materialAmount;

    public CustomShapedRecipe(ItemStack craftResultItem, String recipeCode, String recipeCode2, String recipeCode3, ShapedRecipeMaterial... recipeMaterialList) {
        // レシピの格納
        ShapedRecipe recipe = new ShapedRecipe(craftResultItem);
        recipe.shape(recipeCode, recipeCode2, recipeCode3);
        for (ShapedRecipeMaterial recipeMaterial : recipeMaterialList) {
            recipe.setIngredient(recipeMaterial.getMaterialCode(), recipeMaterial.getMaterialItem().getType());
        }
        this.setRecipe(recipe);

        // ShapeledRecipeMaterialの型をIRecipeMaterialに変換し格納
        List<IRecipeMaterial> materialList = new ArrayList<IRecipeMaterial>();
        for (ShapedRecipeMaterial material : recipeMaterialList) {
            materialList.add(material);
        }
        this.setMaterialList(materialList);

        this.setResult(craftResultItem);
        this.setRecipeCode(recipeCode);
        this.setRecipeCode2(recipeCode2);
        this.setRecipeCode3(recipeCode3);

        int materialAmount = 0;
        materialAmount += recipeCode.replaceAll(" ", "").length();
        materialAmount += recipeCode2.replaceAll(" ", "").length();
        materialAmount += recipeCode3.replaceAll(" ", "").length();
        this.setMaterialAmount(materialAmount);
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return クラフト結果のアイテム */
    public ItemStack getResult() {
        return result;
    }

    /** @return カスタムマテリアルオブジェクトリスト */
    @Override
    public List<IRecipeMaterial> getMaterialList() {
        return recipeMaterialList;
    }

    /** @return カスタムレシピ */
    @Override
    public Recipe getRecipe() {
        return recipe;
    }

    /** @return 3*3レシピの1行目の配置を抽象表現した文字列 */
    public String getRecipeCode() {
        return recipeCode;
    }

    /** @return 3*3レシピの2行目の配置を抽象表現した文字列 */
    public String getRecipeCode2() {
        return recipeCode2;
    }

    /** @return 3*3レシピの3行目の配置を抽象表現した文字列 */
    public String getRecipeCode3() {
        return recipeCode3;
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

    /** @param recipeMaterial カスタムマテリアルオブジェクトリスト */
    public void setMaterialList(List<IRecipeMaterial> recipeMaterial) {
        this.recipeMaterialList = recipeMaterial;
    }

    /** @param recipe カスタムレシピ */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /** @param recipeCode 3*3レシピの1行目の配置を抽象表現した文字列 */
    public void setRecipeCode(String recipeCode) {
        this.recipeCode = recipeCode;
    }

    /** @param recipeCode2 3*3レシピの2行目の配置を抽象表現した文字列 */
    public void setRecipeCode2(String recipeCode2) {
        this.recipeCode2 = recipeCode2;
    }

    /** @param recipeCode3 3*3レシピの3行目の配置を抽象表現した文字列 */
    public void setRecipeCode3(String recipeCode3) {
        this.recipeCode3 = recipeCode3;
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
   public static class ShapedRecipeMaterial implements IRecipeMaterial {

       /** 素材マテリアル */
       private ItemStack itemStack;

       /** レシピ登録する際にマテリアルを表現する抽象文字 */
       private char materialCode;

       public ShapedRecipeMaterial(ItemStack itemStack, char materialCode) {
           this.setMaterial(itemStack);
           this.setMaterialCode(materialCode);
       }

       //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

       /** @return 素材マテリアル */
       @Override
       public ItemStack getMaterialItem() {
           return itemStack;
       }

       /** @return レシピ登録する際にマテリアルを表現する抽象文字 */
       public char getMaterialCode() {
           return materialCode;
       }

       //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

       /** @param material 素材マテリアル */
       public void setMaterial(ItemStack material) {
           this.itemStack = material;
       }

       /** @param materialCode レシピ登録する際にマテリアルを表現する抽象文字 */
       public void setMaterialCode(char materialCode) {
           this.materialCode = materialCode;
       }
   }
}
