package com.github.erozabesu.yplutillibrary.tellraw.event;

import com.github.erozabesu.yplutillibrary.tellraw.object.IHoverEventValue;

/**
 *
 * @author erozabesu
 *
 */
public class HoverEvent {

    /** テキスト部分にマウスオーバーした際に表示される値 */
    private IHoverEventValue hoverValue;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public HoverEvent(IHoverEventValue extraValueSet) {
        this.setHoverValue(extraValueSet);
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * コマンドに利用可能なフォーマットでhoverEventの値を文字列で返す。
     * @return hoverEventの値
     */
    public String buildCommandParts() {
        String parts = "";

        // ヘッダー
        parts += "\"hoverEvent\":{";

        // アクション
        parts += "\"action\":\"" + this.getHoverValue().getActionType().getActionName() + "\"";

        // アクション値
        parts += ",\"value\":{" + this.getHoverValue().buildCommandParts() + "}";

        // フッター
        parts += "}";

        return parts;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return テキストにマウスオーバーした際に表示される値 */
    private IHoverEventValue getHoverValue() {
        return hoverValue;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param value テキスト部分にマウスオーバーした際に表示される値 */
    private void setHoverValue(IHoverEventValue extraValueSet) {
        this.hoverValue = extraValueSet;
    }

    /**
     * テキスト部分にマウスオーバーした際に実行されるアクションタイプ。
     * @author erozabesu
     */
    public static enum HoverActionType {
        SHOW_TEXT,

        SHOW_ITEM,

        SHOW_ENTITY,

        SHOW_ACHIEVEMENT;

        public String getActionName() {
            return this.name().toLowerCase();
        }
    }
}
