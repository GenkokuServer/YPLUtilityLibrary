package com.github.erozabesu.yplutillibrary.custominventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import com.github.erozabesu.yplutillibrary.custominventory.object.MenuAbstract;
import com.github.erozabesu.yplutillibrary.custominventory.object.MenuItem;
import com.github.erozabesu.yplutillibrary.custominventory.object.WideMenu;

public class MenuListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        // クリックしたアイテムがない場合はreturn
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        // メニューインスタンスの取得
        MenuAbstract menu = MenuManager.getUsingMenu(player);
        if (menu == null) {
            return;
        }

        event.setCancelled(true);

        // メニューアイテムの取得
        MenuItem menuItem = menu.getItemBySlot(event.getRawSlot());
        if (menuItem == null) {
            return;
        }

        // メソッドの実行
        if (event.getClick().equals(ClickType.SHIFT_LEFT) || event.getClick().equals(ClickType.SHIFT_RIGHT)) {
            menuItem.onShiftClick();
        } else {
            menuItem.onClick();
        }
    }

    /**
     * インベントリクローズ時にオープン中のメニューインスタンスを破棄する。<br>
     * また、WideMenuの場合、メニューオープン時にプレイヤーコンテナを一時的に差し替えるため、元のアイテムに戻す。
     * @param event InventoryCloseEvent
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        // メニューインスタンスの取得
        MenuAbstract menu = MenuManager.getUsingMenu(player);
        if (menu == null) {
            return;
        }

        if (menu instanceof WideMenu) {
            player.getInventory().clear();

            // TODO: v1.8で復元できない不具合 1.7.10でも要検証
            ((WideMenu) menu).getHolder().recoveryInventory(true);
        }

        // メニューインスタンスの破棄
        MenuManager.clearUsingMenu(player);
    }

    /**
     * プレイヤーダメージ時にオープン中のメニューインスタンスを破棄する。<br>
     * また、WideMenuの場合、メニューオープン時にプレイヤーコンテナを一時的に差し替えるため、元のアイテムに戻す。
     * @param event InventoryCloseEvent
     */
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        // メニューインスタンスの取得
        MenuAbstract menu = MenuManager.getUsingMenu(player);
        if (menu == null) {
            return;
        }

        // メニューインスタンスの破棄
        MenuManager.clearUsingMenu(player);

        if (menu instanceof WideMenu) {
            player.getInventory().clear();
            ((WideMenu) menu).getHolder().recoveryInventory(true);
            player.closeInventory();
        }
    }

    /**
     * メニューオープン中のアイテムピックアップをキャンセルする。
     * @param event PlayerPickupItemEvent
     */
    @EventHandler
    public void onPickUp(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();

        // メニューインスタンスの取得
        MenuAbstract menu = MenuManager.getUsingMenu(player);
        if (menu == null) {
            return;
        }

        event.setCancelled(true);
    }
}
