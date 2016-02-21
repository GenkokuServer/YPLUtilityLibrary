package com.github.erozabesu.yplutillibrary.custominventory.object;

import java.io.File;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.erozabesu.yplutillibrary.custominventory.MenuManager;
import com.github.erozabesu.yplutillibrary.data.SimpleInventoryHolder;
import com.github.erozabesu.yplutillibrary.util.CommonUtil;

/**
 * 仮想チェストインベントリ、及びプレイヤーインベントリを利用したメニューインベントリクラス。
 * @author erozabesu
 */
public class WideMenu extends MenuAbstract {

    /**
     * 当メニューを利用するプラグインのインスタンス。<br>
     * プレイヤーコンテナを利用する都合上、コンテナのアイテムを一時的にローカルファイルに保管するため、<br>
     * 利用するプラグインのインスタンスが必要。
     */
    private JavaPlugin plugin;

    /** コンテナアイテムの保存、復元用インスタンス */
    private SimpleInventoryHolder holder;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * コンストラクタ
     * @param plugin 利用するプラグインのインスタンス
     * @param menuName メニューのタイトルネーム
     * @param slotSize インベントリサイズ。9～54間の9の倍数を指定すること。
     */
    public WideMenu(JavaPlugin plugin, String menuName, int slotSize) {
        super(menuName, slotSize);
        this.setPlugin(plugin);
    }

    /**
     * コンストラクタ
     * @param plugin 利用するプラグインのインスタンス
     * @param menuName メニューのタイトルネーム
     * @param slotSize インベントリサイズ。9～54間の9の倍数を指定すること。
     * @param pageSize ページサイズ
     */
    public WideMenu(JavaPlugin plugin, String menuName, int slotSize, int pageSize) {
        this(plugin, menuName, slotSize);
        this.setPageSize(pageSize);
    }

    /**
     * プレイヤーのコンテナアイテムをローカルファイルへ保存し、trueを返す。<br>
     * 既に保存済みの場合は何もせずfalseを返す。
     * @param player 保存するプレイヤー
     * @return 保存したかどうか
     */
    public boolean savePlayerInventory(Player player) {
        if (this.getHolder() == null) {
            this.setHolder(new SimpleInventoryHolder(this.getPlugin(), this.getPlugin().getDataFolder() + File.separator + "inventory_backup", player));
            this.getHolder().savePlayerInventoryData();
            return true;
        }
        return false;
    }

    /**
     * プレイヤーコンテナにメニュー用アイテムを配置する。
     * @param player
     * @param pageNumber
     */
    public void updatePlayerMenuItems(Player player, int pageNumber) {
        PlayerInventory inventory = player.getInventory();

        // 一時的にプレイヤーのインベントリを利用するため、インベントリのアイテムをローカルファイルへ保存
        this.savePlayerInventory(player);

        // プレイヤーインベントリのコンテナのみを空に(他のスロットは利用しないため触れない)
        CommonUtil.clearPlayerContainer(player);

        // pageNumberのページに配置されるアイテムを取得し、配置
        for (MenuItem menuItem : this.getItemsByPage(pageNumber)) {
            // 最大スロット数を超えるスロット番号を指定したMenuItemは配置しない
            if (this.isInPlayerMenuSlot(menuItem.getSlot())) {
                ItemStack itemStack = menuItem.getItem().clone();

                // スタック数のページ番号との同期設定が有効なMenuItemの場合は同期する
                if (menuItem.isSyncStackToPage()) {
                    itemStack.setAmount(this.getPageByRawPageNumber(this.getCurrentPage() + menuItem.getSyncStackRelativeOffset()));
                }

                // プレイヤーコンテナはスロット番号9～35のため、それに合わせたスロット番号の位置にアイテムをセット
                inventory.setItem(menuItem.getSlot() - this.getSlotSize() + 9, menuItem.getItem());
            }
        }

        // absolute指定のアイテムを配置
        for (MenuItem menuItem : this.getAbsoluteItemSet()) {
            // 最大スロット数を超えるスロット番号を指定したMenuItemは配置しない
            if (this.isInPlayerMenuSlot(menuItem.getSlot())) {
                ItemStack itemStack = menuItem.getItem().clone();

                // スタック数のページ番号との同期設定が有効なMenuItemの場合は同期する
                if (menuItem.isSyncStackToPage()) {
                    itemStack.setAmount(this.getPageByRawPageNumber(this.getCurrentPage() + menuItem.getSyncStackRelativeOffset()));
                }

                // プレイヤーコンテナはスロット番号9～35のため、それに合わせたスロット番号の位置にアイテムをセット
                inventory.setItem(menuItem.getSlot() - this.getSlotSize() + 9, menuItem.getItem());
            }
        }
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数slotがプレイヤーインベントリ内のスロット番号かどうかを返す。
     * @param slot スロット番号
     * @return
     */
    public boolean isInPlayerMenuSlot(int slot) {
        return this.getSlotSize() <= slot && slot < 81;
    }

    @Override
    public void openMenu(Player player) {
        // 先にクローズしなければ正しくアイテムの復元が行われない
        player.closeInventory();

        // チェストインベントリのメニュー生成
        super.updateMenuItems(this.getCurrentPage());

        // プレイヤーインベントリにアイテムの配置
        this.updatePlayerMenuItems(player, this.getCurrentPage());

        player.openInventory(this.getInventory());

        MenuManager.setUsingMenu(player, this);
        player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
    }

    @Override
    public void openMenu(Player player, int page) {
        // 先にクローズしなければ正しくアイテムの復元が行われない
        player.closeInventory();

        // チェストインベントリのメニュー生成
        super.updateMenuItems(this.getCurrentPage());

        // プレイヤーインベントリにアイテムの配置
        this.updatePlayerMenuItems(player, this.getPageByRawPageNumber(page));

        player.openInventory(this.getInventory());

        MenuManager.setUsingMenu(player, this);
        player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return 当メニューを利用するプラグインのインスタンス */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /** @return コンテナアイテムの保存、復元用インスタンス */
    public SimpleInventoryHolder getHolder() {
        return holder;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param plugin 当メニューを利用するプラグインのインスタンス */
    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /** @param holder コンテナアイテムの保存、復元用インスタンス */
    public void setHolder(SimpleInventoryHolder holder) {
        this.holder = holder;
    }
}
