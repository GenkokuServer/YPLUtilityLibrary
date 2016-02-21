package com.github.erozabesu.yplutillibrary.enumdata;

import org.bukkit.inventory.ItemStack;

import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagByte;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagCompound;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.github.erozabesu.yplutillibrary.util.ItemUtil;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public enum IgnoreEventItemNBTType {

    /** 右クリックからブロックを設置できるかどうか */
    BLOCK_PLACE,

    /** かまどの燃料として利用できるかどうか */
    BURN_AS_FUEL,

    /** 金床で加工できるかどうか */
    ANVIL,

    /** エンチャントテーブルで加工できるかどうか */
    ENCHANT,

    /** ドロッパーで射出できるかどうか */
    DROPPER,

    /** ディスペンサーで射出できるかどうか */
    DISPENSER,

    /** アイテムフレームに挿入できるかどうか */
    INCERT_FRAME,

    /** エンドフレームに挿入できるかどうか */
    INCERT_END_FRAME,

    /** 乗り物エンティティをスポーンできるかどうか */
    SPAWN_VEHICLE,

    /** 生物エンティティをスポーンできるかどうか */
    SPAWN_LIVING,

    /** 投擲物として射出できるかどうか */
    LAUNCH,

    /** 弓を武器として利用出来るかどうか */
    DRAW_BOW,

    /** 食べる/飲むことが出来るかどうか */
    CONSUME,

    /** 液体を回収できるかどうか */
    BUCKET_FILL,

    /** 液体を設置できるかどうか */
    BUCKET_EMPTY;

    final public static String nbtKey = "IgnoreEvent";

    public static boolean hasCompoundTag(ItemStack item) {
        return (Boolean) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_hasKey, ItemUtil.getNmsTagCompound(item), nbtKey);
    }

    public boolean getValue(ItemStack item) {
        Object nmsTag = ItemUtil.getNmsTagCompound(item);
        Object nmsIgnoreCompound = ReflectionUtil.invoke(Methods.nmsNBTTagCompound_get, nmsTag, nbtKey);
        if (nmsIgnoreCompound == null) {
            return false;
        }

        Byte value = (Byte) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getByte, nmsIgnoreCompound, this.name());
        if (value == null) {
            return false;
        }

        return value == 1 ? true : false;
    }

    public ItemStack setTag(ItemStack item) {
        Object nmsTag = ItemUtil.getNmsTagCompound(item);
        Object nmsCompound = ReflectionUtil.invoke(Methods.nmsNBTTagCompound_get, nmsTag, nbtKey);

        NBTTagCompound compound = new NBTTagCompound(nbtKey, nmsCompound);
        compound.put(this.name(), new NBTTagByte("", (byte) 1));

        return ItemUtil.setTag(item, compound);
    }

    public ItemStack setTag(ItemStack item, boolean value) {
        Object nmsTag = ItemUtil.getNmsTagCompound(item);
        Object nmsCompound = ReflectionUtil.invoke(Methods.nmsNBTTagCompound_get, nmsTag, nbtKey);

        NBTTagCompound compound = new NBTTagCompound(nbtKey, nmsCompound);
        compound.put(this.name(), new NBTTagByte("", value ? (byte) 1 : (byte) 0));

        return ItemUtil.setTag(item, compound);
    }
}
