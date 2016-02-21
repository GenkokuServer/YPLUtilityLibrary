package com.github.erozabesu.yplutillibrary.customsign;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.github.erozabesu.yplutillibrary.util.PacketUtil;

/**
 * 仮想看板編集ウィンドウクラス。
 * @author erozabesu
 */
public abstract class CustomSign {

    /** ウィンドウを開くプレイヤー */
    private Player player;

    /** 看板の各行の文字列 */
    private String[] lines = new String[]{"", "", "", ""};

    public CustomSign(Player player) {
        this.setPlayer(player);
    }

    /**
     * 看板編集ウィンドウを閉じた際に実行される処理
     */
    public abstract void onClose();

    /**
     * 仮想看板編集ウィンドウを引数playerに開かせる。
     * @param player ウィンドウを開くプレイヤー
     */
    public void openWindow() {
        Location location = this.getPlayer().getLocation();
        Block block = location.getBlock();

        this.getPlayer().sendBlockChange(location, Material.WALL_SIGN, (byte) 0);
        this.getPlayer().sendSignChange(location, new String[]{this.getLines()[0], this.getLines()[1], this.getLines()[2], this.getLines()[3]});
        PacketUtil.sendOpenSignEditorPacket(player, block.getX(), block.getY(), block.getZ());

        PacketUtil.sendBlockChangePacket(this.getPlayer(), location);

        SignManager.setUsingSign(this.getPlayer(), this);
    }

    public void setLine(String text, int index) {
        this.getLines()[index] = text;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return ウィンドウを開くプレイヤー */
    public Player getPlayer() {
        return player;
    }

    /** @return 看板の各行の文字列 */
    public String[] getLines() {
        return this.lines;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param player ウィンドウを開くプレイヤー */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /** @param lines 看板の各行の文字列 */
    public void setLines(String[] lines) {
        this.lines = lines;
    }
}
