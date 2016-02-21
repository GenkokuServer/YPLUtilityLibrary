package com.github.erozabesu.yplutillibrary.tellraw.object;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.erozabesu.yplutillibrary.tellraw.event.HoverEvent.HoverActionType;

/**
 *
 * @author erozabesu
 *
 */
public class ShowItemValue implements IHoverEventValue {

    /** テキスト部分にマウスオーバー時に表示するアイテム */
    private ItemStack showItem;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public ShowItemValue(ItemStack showItem) {
        this.setShowItem(showItem);
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    @Override
    public HoverActionType getActionType() {
        return HoverActionType.SHOW_ITEM;
    }

    /*
     * id:minecraft:stone,
     * tag:{
     *   ench:[
     *     {
     *       id:16,
     *       lvl:1
     *     },
     *     {
     *       id:17,
     *       lvl:1
     *     }
     *   ],
     *   display:{
     *     Name:ITEM_NAME,
     *     Lore:[
     *       \"ITEMLORE\",
     *       \"ITEMLORE2\"
     *     ]
     *   }
     * }
     */
    @Override
    public String buildCommandParts() {
        String parts = "";

        String id = this.getID();
        String enchant = this.getEnchant();
        String display = this.getDisplay();

        // IDの記述
        parts += id;

        // 以降のタグがない場合はここでreturn
        if (enchant.length() == 0 && display.length() == 0) {
            return parts;
        }

        // tagのヘッダーの記述
        parts += ",tag:{";

        // エンチャントの記述
        parts += enchant;

        // 前項のエンチャント、次項のディスプレイが空でない場合は","を記述
        if (enchant.length() != 0 && display.length() != 0) {
            parts += ",";
        }

        // ディスプレイの記述
        parts += display;

        // tagのフッターの記述
        parts += "}";

        return parts;
    }

    /**
     * show_itemで利用するアイテムIDに関する設定値を返す。<br>
     * 例) id:minecraft:stone
     * @return アイテムIDに関する設定値
     */
    public String getID() {
        return "id:minecraft:" + this.getShowItem().getType().name().toLowerCase();
    }

    /**
     * show_itemで利用するエンチャント情報に関する設定値を返す。<br>
     * 例) ench:[{id:16,lvl:1},{id:17,lvl:1}]
     * @return エンチャント情報に関する設定値
     */
    public String getEnchant() {
        if (this.getShowItem().getEnchantments().isEmpty()) {
            return "";
        }

        String value = "ench:[";
        boolean init = false;

        Map<Enchantment, Integer> enchantSet = this.getShowItem().getEnchantments();
        for (Enchantment enchant : enchantSet.keySet()) {
            if (init) {
                value += ",{";
            } else {
                init = true;
                value += "{";
            }
            value += "id:" + enchant.getId();
            value += ",lvl:" + enchantSet.get(enchant);
            value += "}";
        }

        value += "]";

        return value;
    }

    /**
     * show_itemで利用するアイテム名に関する設定値を返す。<br>
     * 例) display:{Name:ITEM_NAME,Lore:[\"ITEMLORE\",\"ITEMLORE2\"]}
     * @return アイテム名に関する設定値
     */
    public String getDisplay() {
        ItemMeta meta = this.getShowItem().getItemMeta();
        if (!meta.hasDisplayName() && !meta.hasLore()) {
            return "";
        }

        String value = "display:{";

        if (meta.hasDisplayName()) {
            value += "Name:" + meta.getDisplayName();
        }

        if (meta.hasLore()) {
            if (meta.hasDisplayName()) {
                value += ",";
            }

            value += "Lore:[";
            boolean init = false;
            for (String lore : meta.getLore()) {
                if (init) {
                    value += ",\\\"";
                } else {
                    init = true;
                    value += "\\\"";
                }
                value += lore + "\\\"";
            }
            value += "]";
        }

        value += "}";

        return value;
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return テキスト部分にマウスオーバー時に表示するアイテム */
    public ItemStack getShowItem() {
        return showItem;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param showItem テキスト部分にマウスオーバー時に表示するアイテム */
    public void setShowItem(ItemStack showItem) {
        this.showItem = showItem;
    }
}
