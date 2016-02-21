package com.github.erozabesu.yplutillibrary.custominventory.object;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.erozabesu.yplutillibrary.custominventory.MenuManager;

/**
 * 仮想チェストタイプインベントリを利用したメニューインベントリクラス。
 * @author erozabesu
 */
public class ChestMenu extends MenuAbstract {

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * コンストラクタ
     * @param menuName メニューのタイトルネーム
     * @param slotSize インベントリサイズ。9～54間の9の倍数を指定すること。
     */
    public ChestMenu(String menuName, int slotSize) {
        super(menuName, slotSize);
    }

    /**
     * コンストラクタ
     * @param menuName メニューのタイトルネーム
     * @param slotSize インベントリサイズ。9～54間の9の倍数を指定すること。
     * @param pageSize ページサイズ
     */
    public ChestMenu(String menuName, int slotSize, int pageSize) {
        this(menuName, slotSize);
        this.setPageSize(pageSize);
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    @Override
    public void openMenu(Player player) {
        super.openMenu(player);

        player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
        MenuManager.setUsingMenu(player, this);
    }

    @Override
    public void openMenu(Player player, int page) {
        super.openMenu(player);

        player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
        MenuManager.setUsingMenu(player, this);
    }
}
