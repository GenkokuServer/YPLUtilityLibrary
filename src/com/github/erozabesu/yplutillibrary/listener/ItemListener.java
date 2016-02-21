package com.github.erozabesu.yplutillibrary.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.github.erozabesu.yplutillibrary.YPLUtilityLibrary;
import com.github.erozabesu.yplutillibrary.enumdata.IgnoreEventItemNBTType;
import com.github.erozabesu.yplutillibrary.object.CraftWhiteList;
import com.github.erozabesu.yplutillibrary.object.IRecipe;
import com.github.erozabesu.yplutillibrary.object.IRecipe.IRecipeMaterial;
import com.github.erozabesu.yplutillibrary.util.CommonUtil;

public class ItemListener implements Listener {

    /*@EventHandler
    public void ffff(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        if (player.isSneaking()) {
            return;
        }

        CommandBuilder builder = new CommandBuilder();

        // サバイバル
        CommandComponent survivalComponent = new CommandComponent("SURVIVAL");
        survivalComponent.setColorByChatColor(ChatColor.GREEN);
        survivalComponent.addFontStyle(FontStyle.BOLD);
        survivalComponent.setClickEvent(new ClickEvent(ClickActionType.RUN_COMMAND, "/gamemode 0"));

        // スペース
        CommandComponent spaceComponent = new CommandComponent(" / ");

        // クリエイティブ
        CommandComponent creativeComponent = new CommandComponent("CREATIVE");
        creativeComponent.setColorByChatColor(ChatColor.DARK_AQUA);
        creativeComponent.addFontStyle(FontStyle.BOLD);
        creativeComponent.setClickEvent(new ClickEvent(ClickActionType.RUN_COMMAND, "/gamemode 1"));

        CommandComponent cHoverComponent = new CommandComponent("クリックでクリエイティブモード");
        cHoverComponent.setColorByChatColor(ChatColor.AQUA);
        ShowTextValue cHoverValue = new ShowTextValue();
        cHoverValue.addComponent(cHoverComponent);
        creativeComponent.setHoverEvent(new HoverEvent(cHoverValue));

        builder.addComponent(survivalComponent);
        builder.addComponent(spaceComponent);
        builder.addComponent(creativeComponent);

        builder.sendTellrawCommand(player);
    }*/

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        ItemStack placed = event.getItemInHand();
        if (IgnoreEventItemNBTType.BLOCK_PLACE.getValue(placed)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (IgnoreEventItemNBTType.BURN_AS_FUEL.getValue(event.getFuel())) {
            event.setCancelled(true);
        }
    }

    /**
     * クラフト結果がカスタムレシピのリザルトアイテムだった場合、<br>
     * 素材が正規のアイテムかどうかをチェックし、異なる場合はキャンセルする。
     * @param event
     */
    @EventHandler
    public void ignoreDeprecatedCraft(CraftItemEvent event) {
        ItemStack[] craftItems = event.getInventory().getContents();
        ItemStack resultItem = craftItems[0];

        // クラフト結果が一致するカスタムレシピを検索
        List<IRecipe> matchedIRecipe = new ArrayList<IRecipe>();
        for (IRecipe iRecipe : CommonUtil.customRecipeSet) {
            if (CommonUtil.isSimilar(resultItem, iRecipe.getResult(), 1)) {
                matchedIRecipe.add(iRecipe);
            }
        }

        // カスタムレシピではない場合return
        if (matchedIRecipe.isEmpty()) {
            return;
        }

        // クラフトの素材が正規の素材かどうかチェックし、一致した場合は繰り返し中でreturnする
        // 一致しない場合は処理の最後でキャンセルされる
        for (IRecipe iRecipe : matchedIRecipe) {
            if (CommonUtil.isSimilar(resultItem, iRecipe.getResult(), 1)) {

                // クラフトの素材を取得。リザルトスロット[0]は含まないようi = 1からスタート
                ArrayList<ItemStack> craftMaterials = new ArrayList<ItemStack>();
                for (int i = 1; i < craftItems.length; i++) {
                    if (craftItems[i].getType() != Material.AIR) {
                        craftMaterials.add(craftItems[i]);
                    }
                }

                // 素材が一致した回数
                int matchCount = 0;

                // 正規の素材がいくつ含まれているかカウント
                for (IRecipeMaterial iMaterial : iRecipe.getMaterialList()) {
                    for (ItemStack craftMaterial : craftMaterials) {
                        if (CommonUtil.isSimilar(iMaterial.getMaterialItem(), craftMaterial, 1)) {
                            matchCount++;
                        }
                    }
                }

                // 素材が一致した回数が正規の素材の数の場合return
                if (matchCount == iRecipe.getMaterialAmount()) {
                    return;
                }
            }
        }

        event.setCancelled(true);
        ((Player) event.getWhoClicked()).updateInventory();
    }

    /**
     * クラフトの素材にクラフトホワイトリストに登録されたアイテムが含まれている場合、<br>
     * 許可するリザルトアイテム以外のクラフトをキャンセルする。
     * @param event
     */
    @EventHandler
    public void ignoreUnWhiteListedCraft(CraftItemEvent event) {
        ItemStack[] craftItems = event.getInventory().getContents();
        ItemStack resultItem = craftItems[0];

        // クラフトの素材を取得。リザルトスロット[0]は含まないようi = 1からスタート
        ArrayList<ItemStack> craftMaterials = new ArrayList<ItemStack>();
        for (int i = 1; i < craftItems.length; i++) {
            if (craftItems[i].getType() != Material.AIR) {
                craftMaterials.add(craftItems[i]);
            }
        }

        for (CraftWhiteList whiteList : CommonUtil.craftWhiteListSet) {
            // ホワイトリスト登録アイテムが含まれている
            if (CommonUtil.containsSimilarItemStack(craftMaterials, whiteList.getCraftMaterial())) {
                // リザルトアイテムがホワイトリストに登録されていない場合イベントキャンセル
                if (!CommonUtil.containsSimilarItemStack(whiteList.getCraftResultList(), resultItem)) {
                    event.setCancelled(true);
                    ((Player) event.getWhoClicked()).updateInventory();
                    return;
                }
            }
        }
    }

    /**
     * クラフト結果がカスタムレシピのリザルトアイテムだった場合、<br>
     * 素材が正規のアイテムかどうかをチェックし、異なる場合はリザルトスロットを非表示にする。
     * @param event
     */
    @EventHandler
    public void ignoreDeprecatedRecipeDisplay(PrepareItemCraftEvent event) {
        ItemStack[] craftItems = event.getInventory().getContents();
        ItemStack resultItem = craftItems[0];

        // クラフト結果が一致するカスタムレシピを検索
        List<IRecipe> matchedIRecipe = new ArrayList<IRecipe>();
        for (IRecipe iRecipe : CommonUtil.customRecipeSet) {
            if (CommonUtil.isSimilar(resultItem, iRecipe.getResult(), 1)) {
                matchedIRecipe.add(iRecipe);
            }
        }

        // カスタムレシピではない場合return
        if (matchedIRecipe.isEmpty()) {
            return;
        }

        // クラフトの素材が正規の素材かどうかチェックし、一致した場合は繰り返し中でreturnする
        // 一致しない場合は処理の最後でリザルトスロットを非表示にする
        for (IRecipe iRecipe : matchedIRecipe) {
            if (CommonUtil.isSimilar(resultItem, iRecipe.getResult(), 1)) {

                // クラフトの素材を取得。リザルトスロット[0]は含まないようi = 1からスタート
                ArrayList<ItemStack> craftMaterials = new ArrayList<ItemStack>();
                for (int i = 1; i < craftItems.length; i++) {
                    if (craftItems[i].getType() != Material.AIR) {
                        craftMaterials.add(craftItems[i]);
                    }
                }

                // 素材が一致した回数
                int matchCount = 0;

                // 正規の素材がいくつ含まれているかカウント
                for (IRecipeMaterial iMaterial : iRecipe.getMaterialList()) {
                    for (ItemStack craftMaterial : craftMaterials) {
                        if (CommonUtil.isSimilar(iMaterial.getMaterialItem(), craftMaterial, 1)) {
                            matchCount++;
                        }
                    }
                }

                // 素材が一致した回数が正規の素材の数の場合return
                if (matchCount == iRecipe.getMaterialAmount()) {
                    return;
                }
            }
        }

        event.getInventory().setItem(0, new ItemStack(Material.AIR));
    }

    /**
     * クラフトの素材にクラフトホワイトリストに登録されたアイテムが含まれている場合、<br>
     * 許可するリザルトアイテム以外のクラフトをキャンセルする。
     * @param event
     */
    @EventHandler
    public void ignoreUnWhiteListedRecipeDisplay(PrepareItemCraftEvent event) {
        ItemStack[] craftItems = event.getInventory().getContents();
        ItemStack resultItem = craftItems[0];

        // クラフトの素材を取得。リザルトスロット[0]は含まないようi = 1からスタート
        ArrayList<ItemStack> craftMaterials = new ArrayList<ItemStack>();
        for (int i = 1; i < craftItems.length; i++) {
            if (craftItems[i].getType() != Material.AIR) {
                craftMaterials.add(craftItems[i]);
            }
        }

        for (CraftWhiteList whiteList : CommonUtil.craftWhiteListSet) {
            // ホワイトリスト登録アイテムが含まれている
            if (CommonUtil.containsSimilarItemStack(craftMaterials, whiteList.getCraftMaterial())) {
                // リザルトアイテムがホワイトリストに登録されていない場合イベントキャンセル
                if (!CommonUtil.containsSimilarItemStack(whiteList.getCraftResultList(), resultItem)) {
                    event.getInventory().setItem(0, new ItemStack(Material.AIR));
                    return;
                }
            }
        }
    }

    /**
     * 生物エンティティのスポーン禁止NBTの付与された生物エンティティをスポーンし得るアイテムの右クリック使用をキャンセルする。
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onSpawnLivingByItemClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack hand = event.getPlayer().getItemInHand();
            if (hand != null) {
                if (hand.getType().equals(Material.MONSTER_EGG)) {
                    if (IgnoreEventItemNBTType.SPAWN_LIVING.getValue(hand)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * 乗り物エンティティのスポーン禁止NBTの付与された乗り物エンティティをスポーンし得るアイテムの右クリック使用をキャンセルする。
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onSpawnVehicleByItemClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack hand = event.getPlayer().getItemInHand();
            if (hand != null) {
                if (hand.getType().equals(Material.BOAT)
                        || hand.getType().equals(Material.MINECART)
                        || hand.getType().equals(Material.STORAGE_MINECART)
                        || hand.getType().equals(Material.POWERED_MINECART)
                        || hand.getType().equals(Material.EXPLOSIVE_MINECART)
                        || hand.getType().equals(Material.HOPPER_MINECART)) {
                    if (IgnoreEventItemNBTType.SPAWN_VEHICLE.getValue(hand)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    /**
     * エンドフレームに挿入禁止NBTの付与されたエンダーアイのエンドフレームへの挿入をキャンセルする。
     * @param event PlayerInteractEvent
     */
    @EventHandler
    public void onIncertItemToEndFrame(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null) {
                if (clickedBlock.getType().equals(Material.ENDER_PORTAL_FRAME)) {
                    ItemStack hand = event.getPlayer().getItemInHand();
                    if (hand != null && hand.getType().equals(Material.EYE_OF_ENDER)) {
                        if (IgnoreEventItemNBTType.INCERT_FRAME.getValue(hand)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * アイテムフレームに挿入禁止NBTの付与されたアイテムの挿入をキャンセルする。
     * @param event PlayerInteractEntityEvent
     */
    @EventHandler
    public void onIncertItemToItemFrame(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame) {
            ItemFrame frame = (ItemFrame) event.getRightClicked();

            // 既にアイテムの展示されているフレームの場合は何もしない
            if (frame.getItem() != null && !frame.getItem().getType().equals(Material.AIR)) {
                return;
            }

            ItemStack hand = event.getPlayer().getItemInHand();
            if (hand != null && !hand.getType().equals(Material.AIR)) {
                if (IgnoreEventItemNBTType.INCERT_FRAME.getValue(hand)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * 液体の回収禁止NBTの付与されたアイテムの使用をキャンセルする。
     * @param event PlayerBucketFillEvent
     */
    @EventHandler
    public void onFillLiquid(final PlayerBucketFillEvent event) {
        final Player player = event.getPlayer();
        if (IgnoreEventItemNBTType.BUCKET_FILL.getValue(player.getItemInHand())) {
            event.setCancelled(true);

            player.updateInventory();

            // クライアントでは回収されたように描画されるため、クリックされた液体ブロックの状態を再送信
            Bukkit.getScheduler().runTaskLater(YPLUtilityLibrary.getInstance(), new Runnable() {
                public void run() {
                    if (YPLUtilityLibrary.getInstance().isEnabled()) {
                        Block clicked = event.getBlockClicked();
                        player.sendBlockChange(clicked.getLocation(), clicked.getType(), clicked.getData());
                    }
                }
            }, 20);
        }
    }

    /**
     * 液体の設置禁止NBTの付与されたアイテムの使用をキャンセルする。
     * @param event PlayerBucketEmptyEvent
     */
    @EventHandler
    public void onPlaceLiquid(final PlayerBucketEmptyEvent event) {
        final Player player = event.getPlayer();
        if (IgnoreEventItemNBTType.BUCKET_EMPTY.getValue(player.getItemInHand())) {
            event.setCancelled(true);

            player.updateInventory();

            // クライアントでは回収されたように描画されるため、クリックされた液体ブロックの状態を再送信
            Bukkit.getScheduler().runTaskLater(YPLUtilityLibrary.getInstance(), new Runnable() {
                public void run() {
                    if (YPLUtilityLibrary.getInstance().isEnabled()) {
                        Block clicked = event.getBlockClicked();
                        player.sendBlockChange(clicked.getLocation(), clicked.getType(), clicked.getData());
                    }
                }
            }, 20);
        }
    }

    /**
     * 飲食禁止NBTの付与されたアイテムの消費をキャンセルする。
     * @param event PlayerItemConsumeEvent
     */
    @EventHandler
    public void onEat(PlayerItemConsumeEvent event) {
        if (IgnoreEventItemNBTType.CONSUME.getValue(event.getItem())) {
            event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ITEM_BREAK, 1.0F, 1.0F);

            event.setCancelled(true);
        }
    }

    /**
     * 射出禁止NBTの付与されたアイテムの射出をキャンセルする。
     * @param event ProjectileLaunchEvent
     */
    @EventHandler
    public void onLaunchProjectile(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();
        ItemStack hand = player.getItemInHand();
        if (hand != null && !hand.getType().equals(Material.AIR)) {
            if (IgnoreEventItemNBTType.LAUNCH.getValue(hand)) {
                event.setCancelled(true);

                // ProjectileLaunchEventはキャンセルしてもアイテムは減るため手に持っているアイテムを1つ配布する
                // Player.getItemInHand()はアイテム使用後数量が0になっている場合でも、amount0状態のアイテムスタックとして使用前の情報が残っているため利用可能
                ItemStack addItem = hand.clone();
                addItem.setAmount(1);
                CommonUtil.addItemToPlayer(player, addItem, true);
            }
        }
    }


    /**
     * 弓としての利用禁止NBTの付与されている弓を用いて射出した場合キャンセルする。<br>
     * また、射出禁止NBTの付与されている矢を射出した場合キャンセルする。<br>
     * 実際には、イベントを強制的にキャンセルし、射出が許可されている矢をインベントリから取得出来た場合は手動で射出している。<br>
     * @param event EntityShootBowEvent
     */
    @EventHandler
    public void onShootArrow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        // 弓としての利用禁止NBTの付与されている弓を用いて射出した場合キャンセル
        if (IgnoreEventItemNBTType.DRAW_BOW.getValue(player.getItemInHand())) {
            event.setCancelled(true);
            player.updateInventory();
            return;
        }

        // イベントを強制的にキャンセル
        event.setCancelled(true);

        // 射出が許可された矢を所持している場合は矢を射出し、該当スロットのアイテムを1つ消費する
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < 36; i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType().equals(Material.ARROW)) {
                if (!IgnoreEventItemNBTType.LAUNCH.getValue(item)) {
                    player.launchProjectile(Arrow.class, event.getProjectile().getVelocity());
                    CommonUtil.decreaseItemInInventory(player, i, 1);
                    break;
                }
            }
        }

        player.updateInventory();
    }

    /**
     * ディスペンサー、もしくはドロッパー禁止NBTの付与されたアイテムの射出をキャンセルする。<br>
     * また、投擲物としての使用禁止NBTの付与されたアイテムの射出をキャンセルする。<br>
     * また、ディスペンサーから射出されたアイテムが乗り物エンティティのスポーン禁止NBTの付与された乗り物エンティティをスポーンし得るアイテムの場合、射出をキャンセルする。<br>
     * また、ディスペンサーから射出されたアイテムが生物エンティティのスポーン禁止NBTの付与された生物エンティティをスポーンし得るアイテムの場合、射出をキャンセルする。
     * @param event BlockDispenseEvent
     */
    @EventHandler
    public void onDispenseItem(BlockDispenseEvent event) {
        Material blockType = event.getBlock().getType();
        ItemStack item = event.getItem();
        if (blockType.equals(Material.DISPENSER)) {
            // ディスペンサー禁止NBTの付与されたアイテムの射出をキャンセル
            if (IgnoreEventItemNBTType.DISPENSER.getValue(event.getItem())) {
                event.setCancelled(true);
            } else {
                // 乗り物エンティティのスポーン禁止NBTの付与された、乗り物エンティティをスポーンし得るアイテムの射出をキャンセル
                if (item.getType().equals(Material.MINECART)
                        || item.getType().equals(Material.STORAGE_MINECART)
                        || item.getType().equals(Material.POWERED_MINECART)
                        || item.getType().equals(Material.EXPLOSIVE_MINECART)
                        || item.getType().equals(Material.HOPPER_MINECART)) {
                    if (IgnoreEventItemNBTType.SPAWN_VEHICLE.getValue(item)) {
                        event.setCancelled(true);
                    }

                // 生物エンティティのスポーン禁止NBTの付与された、生物エンティティをスポーンし得るアイテムの射出をキャンセル
                } else if (item.getType().equals(Material.MONSTER_EGG)) {
                    if (IgnoreEventItemNBTType.SPAWN_LIVING.getValue(item)) {
                        event.setCancelled(true);
                    }

                // 投擲物としての使用禁止NBTの付与されたアイテムの射出をキャンセル
                } else if (item.getType().equals(Material.ARROW)
                        || (item.getType().equals(Material.POTION) && (item.getDurability() & 0x4000) > 0)
                        || item.getType().equals(Material.EGG)
                        || item.getType().equals(Material.SNOW_BALL)) {
                    if (IgnoreEventItemNBTType.LAUNCH.getValue(item)) {
                        event.setCancelled(true);
                    }
                }
            }
        } else if (blockType.equals(Material.DROPPER)) {
            // ドロッパー禁止NBTの付与されたアイテムの射出をキャンセル
            if (IgnoreEventItemNBTType.DROPPER.getValue(event.getItem())) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * エンチャント禁止NBTの付与されたアイテムのエンチャントをキャンセルする。
     * @param event EnchantItemEvent
     */
    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if (IgnoreEventItemNBTType.ENCHANT.getValue(event.getItem())) {
            event.setCancelled(true);
        }
    }

    /**
     * エンチャント禁止NBTの付与されたアイテムのエンチャント候補の表示をキャンセルする。
     * @param event PrepareItemEnchantEvent
     */
    @EventHandler
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        if (IgnoreEventItemNBTType.ENCHANT.getValue(event.getItem())) {
            event.setCancelled(true);
        }
    }

    /**
     * 金床禁止NBTの付与されたアイテムが金床の素材スロットに対し配置された場合キャンセルする。
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void onMoveItemToAnvilInventory(InventoryClickEvent event) {
        // 金床でなければreturn
        if (!event.getInventory().getType().equals(InventoryType.ANVIL)) {
            return;
        }

        int rawSlot = event.getRawSlot();

        // 禁止アイテムをホットバースワップで移動させた場合
        if (event.getAction().equals(InventoryAction.HOTBAR_SWAP)) {
            if (rawSlot == 0 || rawSlot == 1) {
                // 押下した番号キーのスロットのアイテムを取得
                ItemStack hotbarItem = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
                if (hotbarItem != null && !hotbarItem.getType().equals(Material.AIR)) {
                    if (IgnoreEventItemNBTType.ANVIL.getValue(hotbarItem)) {
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

        // 禁止アイテムをシフトクリックしアイテムを移動させた場合
        if (event.isShiftClick()) {
            ItemStack cursor = event.getCurrentItem();
            if (cursor != null && !cursor.getType().equals(Material.AIR)) {
                if (IgnoreEventItemNBTType.ANVIL.getValue(cursor)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        // 禁止アイテムを通常のクリックで移動させた場合
        if (event.getRawSlot() == 0 || event.getRawSlot() == 1) {
            ItemStack cursor = event.getCursor();
            if (cursor != null && !cursor.getType().equals(Material.AIR)) {
                if (IgnoreEventItemNBTType.ANVIL.getValue(cursor)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }

    /**
     * 金床禁止NBTの付与されたアイテムの含まれるアイテムが金床の素材スロットに対しドラッグ配置された場合キャンセルする。
     * @param event InventoryDragEvent
     */
    @EventHandler
    public void onMoveItemToAnvilInventory(InventoryDragEvent event) {
        // 金床でなければreturn
        if (!event.getInventory().getType().equals(InventoryType.ANVIL)) {
            return;
        }

        for (int slot : event.getRawSlots()) {
            if (slot == 0 || slot == 1) {
                if (IgnoreEventItemNBTType.ANVIL.getValue(event.getOldCursor())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * 金床禁止NBTの付与されたアイテムの含まれるアイテムが金床の素材スロットに配置されている場合、
     * リザルトスロットのクリックをキャンセルする。
     * @param event InventoryClickEvent
     */
    @EventHandler
    public void onAnvilClickResultSlot(InventoryClickEvent event) {
        // 金床でなければreturn
        if (!event.getInventory().getType().equals(InventoryType.ANVIL)) {
            return;
        }

        /*
         * 金床の素材スロットは0、1
         * リザルトスロットは2だが、結果として返ってくるアイテムはクライアントでは表示されているものの
         * 内部的にはnullであるため、スロット2は常にnullを返す
         */

        // クリックしたのがリザルトスロットでない場合return
        if (event.getRawSlot() != 2) {
            return;
        }

        ItemStack[] materialList = event.getInventory().getContents();

        // 素材に金床禁止NBTの付与されたアイテムが含まれる場合イベントキャンセル
        for (int i = 0; i < materialList.length - 1; i++) {
            if (materialList[i] != null) {
                if (IgnoreEventItemNBTType.ANVIL.getValue(materialList[i])) {
                    event.setCancelled(true);
                    event.getInventory().setItem(2, new ItemStack(Material.AIR));
                    return;
                }
            }
        }
    }
}
