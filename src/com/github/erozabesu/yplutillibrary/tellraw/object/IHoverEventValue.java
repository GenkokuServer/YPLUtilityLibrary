package com.github.erozabesu.yplutillibrary.tellraw.object;

import com.github.erozabesu.yplutillibrary.tellraw.event.HoverEvent.HoverActionType;

/**
 *
 * @author erozabesu
 *
 */
public interface IHoverEventValue {

    /**
     * テキスト部分にマウスオーバーした際に実行されるアクションタイプを返す。
     * @return アクションタイプ
     */
    public HoverActionType getActionType();

    /**
     * コマンドに利用可能なフォーマットでhoverEvent-valueの設定値を文字列で返す。
     * @return hoverEventの設定値
     */
    public String buildCommandParts();
}
