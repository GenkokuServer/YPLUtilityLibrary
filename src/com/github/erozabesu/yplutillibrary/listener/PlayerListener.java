package com.github.erozabesu.yplutillibrary.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.github.erozabesu.yplutillibrary.util.CommonUtil;

public class PlayerListener implements Listener {

    @EventHandler
    public void vanishPlayer(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (!otherPlayer.equals(player)) {
                // 透明化している他のプレイヤーを不可視にする
                if (CommonUtil.isVanishing.contains(otherPlayer.getUniqueId())) {
                    player.hidePlayer(otherPlayer);
                }

                // 自身が透明化している場合は他のプレイヤーから自身を不可視にする
                if (CommonUtil.isVanishing.contains(player.getUniqueId())) {
                    otherPlayer.hidePlayer(player);
                }
            }
        }
    }
}
