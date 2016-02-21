package com.github.erozabesu.yplutillibrary;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.erozabesu.yplutillibrary.custominventory.MenuListener;
import com.github.erozabesu.yplutillibrary.listener.ItemListener;
import com.github.erozabesu.yplutillibrary.listener.PlayerListener;
import com.github.erozabesu.yplutillibrary.packet.NettyListener;

/**
 * ライブラリのメインクラス。
 * @author erozabesu
 */
public class YPLUtilityLibrary extends JavaPlugin {

    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        Bukkit.getPluginManager().registerEvents(new ItemListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new NettyListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getPluginManager().disablePlugin(this);
    }

    /**
     * メインクラスのインスタンスを返す。
     * @return メインクラスのインスタンス
     */
    public static JavaPlugin getInstance() {
        return plugin;
    }
}
