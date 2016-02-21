package com.github.erozabesu.yplutillibrary.custominventory;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.github.erozabesu.yplutillibrary.custominventory.object.MenuAbstract;
import com.google.common.collect.Maps;

/**
 * カスタムメニュークラスの登録、及びメニューインスタンスの管理を行うクラス。<br>
 * @author erozabesu
 */
public class MenuManager {

    /** 登録されたメニューインスタンスのハッシュマップ */
    private static HashMap<String, MenuAbstract> registeredMenu = Maps.newHashMap();

    /** プレイヤーと生成したメニューインスタンスのハッシュマップ */
    private static HashMap<Player, MenuAbstract> menuInstanceMap = Maps.newHashMap();

    /**
     * メニューインスタンスをハッシュマップに格納する。<br>
     * 主に、プラグインがリロードされるまで使い回す用途のメニューを登録する。<br>
     * 動的データを扱う一時的な用途のメニューは登録する必要はない。
     * @param menu メニューインスタンス
     */
    public static void registerMenu(MenuAbstract menu) {
        registeredMenu.put(menu.getMenuName(), menu);
    }

    public static void unregisterMenu(MenuAbstract menu) {
        registeredMenu.remove(menu.getMenuName());
    }

    public static boolean isRegisteredMenu(String menuName) {
        return registeredMenu.containsKey(menuName);
    }

    public static MenuAbstract getMenuByName(String menuName) {
        return registeredMenu.get(menuName);
    }

    /**
     * プレイヤーが現在オープンしているメニューインスタンスをハッシュマップに格納する。<br>
     * プレイヤーがメニューを閉じた際にハッシュマップからは削除される。
     * @param player
     * @param menu
     */
    public static void setUsingMenu(Player player, MenuAbstract menu) {
        menuInstanceMap.put(player, menu);
    }

    /**
     * プレイヤーが現在オープンしているメニューインスタンスを返す。<br>
     * オープンしているメニューがない場合は{@code null}を返す。
     * @param player
     * @return
     */
    public static MenuAbstract getUsingMenu(Player player) {
        return menuInstanceMap.get(player);
    }

    public static void clearUsingMenu(Player player) {
        menuInstanceMap.remove(player);
    }
}
