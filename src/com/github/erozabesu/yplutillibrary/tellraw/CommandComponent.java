package com.github.erozabesu.yplutillibrary.tellraw;

import java.util.HashSet;

import org.bukkit.ChatColor;

import com.github.erozabesu.yplutillibrary.tellraw.event.ClickEvent;
import com.github.erozabesu.yplutillibrary.tellraw.event.HoverEvent;
import com.google.common.collect.Sets;

public class CommandComponent {

    /** 実際に表示される文字列 */
    private String text;

    /** 文字列の色 */
    private String color;

    /** フォントスタイルセット */
    private HashSet<FontStyle> fontStyleSet = Sets.newHashSet();

    /** クリックイベント */
    private ClickEvent clickEvent;

    /** ホバーイベント */
    private HoverEvent hoverEvent;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public CommandComponent(String text) {
        this.setText(text);
        this.setColor(ChatColor.WHITE.name().toLowerCase());
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public void addFontStyle(FontStyle font) {
        this.getFontStyleSet().add(font);
    }

    public void setColorByChatColor(ChatColor chatColor) {
        this.setColor(chatColor.name().toLowerCase());
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return 実際に表示される文字列 */
    public String getText() {
        return text;
    }

    /** @return 文字列の色 */
    public String getColor() {
        return color;
    }

    /** @return フォントスタイルセット */
    public HashSet<FontStyle> getFontStyleSet() {
        return fontStyleSet;
    }

    /** @return クリックイベント */
    public ClickEvent getClickEvent() {
        return clickEvent;
    }

    /** @return ホバーイベント */
    public HoverEvent getHoverEvent() {
        return hoverEvent;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param text 実際に表示される文字列 */
    public void setText(String text) {
        this.text = text;
    }

    /** @param color 文字列の色 */
    public void setColor(String color) {
        this.color = color;
    }

    /** @param fontStyleSet フォントスタイルセット */
    public void setFontStyleSet(HashSet<FontStyle> fontStyleSet) {
        this.fontStyleSet = fontStyleSet;
    }

    /** @param clickEvent クリックイベント */
    public void setClickEvent(ClickEvent clickEvent) {
        this.clickEvent = clickEvent;
    }

    /** @param hoverEvent ホバーイベント */
    public void setHoverEvent(HoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
    }

    /**
     *
     * @author erozabesu
     *
     */
    public enum FontStyle {
        BOLD,

        ITALIC,

        UNDERLINED,

        STRIKETHROUGH,

        OBFUSCATED;

        public String getFontName() {
            return this.name().toLowerCase();
        }
    }
}
