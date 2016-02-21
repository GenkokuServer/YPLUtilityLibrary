package com.github.erozabesu.yplutillibrary.tellraw;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.erozabesu.yplutillibrary.tellraw.CommandComponent.FontStyle;
import com.github.erozabesu.yplutillibrary.tellraw.event.ClickEvent;
import com.github.erozabesu.yplutillibrary.tellraw.event.HoverEvent;
import com.google.common.collect.Lists;

/**
 *
 * @author erozabesu
 *
 */
public class CommandBuilder {

    /** 追加されているコマンドコンポーネントリスト */
    private List<CommandComponent> componentList = Lists.newArrayList();

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public CommandBuilder() {}

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public void addComponent(CommandComponent component) {
        this.getComponentList().add(component);
    }

    public String buildCommand() {
        String command = " [\"\"";

        for (CommandComponent component : this.getComponentList()) {
            // ヘッダー
            command += ",{";

            // テキスト部分
            command += "text:\"" + component.getText() + "\"";

            // フォントカラー
            command += ",color:\"" + component.getColor() + "\"";

            // フォントスタイル
            for (FontStyle font : component.getFontStyleSet()) {
                command += "," + font.getFontName() + ":\"true\"";
            }

            // クリックアクション
            ClickEvent click = component.getClickEvent();
            if (click != null) {
                command += "," + click.buildCommandParts();
            }

            // ホバーアクション
            HoverEvent hover = component.getHoverEvent();
            if (hover != null) {
                command += "," + hover.buildCommandParts();
            }

            // フッター
            command += "}";
        }

        command += "]";

        return command;
    }

    public void sendTellrawCommand(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + this.buildCommand());
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return 追加されているコマンドコンポーネントリスト */
    private List<CommandComponent> getComponentList() {
        return componentList;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param textSet 追加されているコマンドコンポーネントリスト */
    private void setComponentList(List<CommandComponent> componentList) {
        this.componentList = componentList;
    }
}
