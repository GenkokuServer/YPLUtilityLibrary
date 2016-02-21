package com.github.erozabesu.yplutillibrary.tellraw.event;

/**
 *
 * @author erozabesu
 *
 */
public class ClickEvent {

    /** テキストクリック時に実行するアクションタイプ */
    private ClickActionType clickAction;

    /** アクション実行時に利用する値 */
    private String value;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public ClickEvent() {}

    public ClickEvent(ClickActionType clickAction, String value) {
        this.setClickAction(clickAction);
        this.setValue(value);
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * コマンドに利用可能なフォーマットでclickEventの値を文字列で返す。
     * @return clickEventの値
     */
    public String buildCommandParts() {
        String parts = "";

        // ヘッダー
        parts += "clickEvent:{";

        // アクション
        parts += "action:\"" + this.getClickAction().getActionName() + "\"";

        // アクション値
        parts += ",value:\"" + this.getValue() + "\"";

        // フッター
        parts += "}";

        return parts;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return テキストクリック時に実行するアクションタイプ */
    public ClickActionType getClickAction() {
        return clickAction;
    }

    /** @return アクション実行時に利用する値 */
    public String getValue() {
        return value;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param clickAction テキストクリック時に実行するアクションタイプ */
    public void setClickAction(ClickActionType clickAction) {
        this.clickAction = clickAction;
    }

    /** @param value アクション実行時に利用する値 */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @author erozabesu
     *
     */
    public static enum ClickActionType {
        RUN_COMMAND,

        SUGGEST_COMMAND,

        OPEN_URL,

        CHANGE_PAGE;

        public String getActionName() {
            return this.name().toLowerCase();
        }
    }
}
