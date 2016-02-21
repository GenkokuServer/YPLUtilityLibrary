package com.github.erozabesu.yplutillibrary.tellraw.object;

import java.util.List;

import com.github.erozabesu.yplutillibrary.tellraw.CommandComponent;
import com.github.erozabesu.yplutillibrary.tellraw.CommandComponent.FontStyle;
import com.github.erozabesu.yplutillibrary.tellraw.event.HoverEvent.HoverActionType;
import com.google.common.collect.Lists;

/**
 *
 * @author erozabesu
 *
 */
public class ShowTextValue implements IHoverEventValue {

    /** 追加されているコマンドコンポーネントリスト */
    private List<CommandComponent> componentList = Lists.newArrayList();

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public ShowTextValue() {}

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public void addComponent(CommandComponent component) {
        this.getComponentList().add(component);
    }

    @Override
    public HoverActionType getActionType() {
        return HoverActionType.SHOW_TEXT;
    }

    @Override
    public String buildCommandParts() {
        String parts = "\"text\":\"\",\"extra\":[";

        boolean init = false;
        for (CommandComponent component : this.getComponentList()) {
            // ヘッダー
            if (init) {
                parts += ",{";
            } else {
                init = true;
                parts += "{";
            }

            // テキスト部分
            parts += "\"text\":\"" + component.getText() + "\"";

            // フォントカラー
            parts += ",\"color\":\"" + component.getColor() + "\"";

            // フォントスタイル
            for (FontStyle font : component.getFontStyleSet()) {
                parts += "," + font.getFontName() + ":\"true\"";
            }

            // フッター
            parts += "}";
        }

        parts += "]";

        return parts;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return 追加されているコマンドコンポーネントリスト */
    private List<CommandComponent> getComponentList() {
        return componentList;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param componentList 追加されているコマンドコンポーネントリスト */
    private void setComponentList(List<CommandComponent> componentList) {
        this.componentList = componentList;
    }
}
