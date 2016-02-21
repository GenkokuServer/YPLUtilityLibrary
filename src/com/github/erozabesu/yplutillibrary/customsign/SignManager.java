package com.github.erozabesu.yplutillibrary.customsign;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

/**
 * サインインスタンスの管理を行うクラス。<br>
 * @author erozabesu
 */
public class SignManager {

    /** プレイヤーと生成したサインインスタンスのハッシュマップ */
    private static HashMap<Player, CustomSign> signInstanceMap = Maps.newHashMap();

    /**
     * プレイヤーが現在オープンしているサインインスタンスをハッシュマップに格納する。<br>
     * プレイヤーがサインを閉じた際にハッシュマップからは削除される。
     * @param player
     * @param menu
     */
    public static void setUsingSign(Player player, CustomSign menu) {
        signInstanceMap.put(player, menu);
    }

    /**
     * プレイヤーが現在オープンしているサインインスタンスを返す。<br>
     * オープンしているサインがない場合は{@code null}を返す。
     * @param player
     * @return
     */
    public static CustomSign getUsingSign(Player player) {
        return signInstanceMap.get(player);
    }

    public static void clearUsingSign(Player player) {
        signInstanceMap.remove(player);
    }
}
