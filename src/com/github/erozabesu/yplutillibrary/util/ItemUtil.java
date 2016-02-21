package com.github.erozabesu.yplutillibrary.util;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.erozabesu.yplutillibrary.enumdata.ItemNBTType;
import com.github.erozabesu.yplutillibrary.enumdata.ItemNBTType.ItemNBTAttributeType;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTBase;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagByte;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagCompound;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagDouble;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagInt;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagList;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagLong;
import com.github.erozabesu.yplutillibrary.object.nbt.NBTTagString;
import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ItemUtil {

    public static ItemStack setDisplayName(ItemStack item, String displayName) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack addLore(ItemStack item, String lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> loreList = meta.getLore();
        if (loreList == null) {
            loreList = Lists.newArrayList();
        }
        loreList.add(lore);
        meta.setLore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack addLore(ItemStack item, List<String> loreList) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);

        return item;
    }

    /**
     * (Craft)ItemStackからNmsItemStackを取得し返す。<br>
     * 既にゲーム内に出現しているItemStackのみ利用可能。<br>
     * 新規に生成したItemStackインスタンスは利用できないため、createNmsItemStack(ItemStack item)を利用すること。
     * @param item 取得するアイテム
     * @return NmsItemStack
     */
    public static Object getHandle(ItemStack item) {
        return ReflectionUtil.getFieldValue(Fields.craftItemStack_handle, item);
    }

    /**
     * ItemStackからNmsItemStackインスタンスを新規に生成し返す。
     * @param item BukkitItemStack
     * @return NmsItemStack
     */
    public static Object createNmsItemStack(ItemStack item) {
        return ReflectionUtil.invoke(Methods.static_craftItemStack_createNmsItemByBukkitItem, null, item);
    }

    /**
     * NmsItemStackから(Craft)ItemStackインスタンスを新規に生成し返す。
     * @param item NmsItemStack
     * @return (Craft)ItemStack
     */
    public static ItemStack createCraftItemStack(Object nmsItem) {
        return (ItemStack) ReflectionUtil.invoke(Methods.static_craftItemStack_createCraftItemByNmsItem, null, nmsItem);
    }

    /**
     * ItemStackからCraftItemStackインスタンスを新規に生成し返す。
     * @param item NmsItemStack
     * @return (Craft)ItemStack
     */
    public static ItemStack createCraftItemStack(ItemStack item) {
        return (ItemStack) ReflectionUtil.invoke(Methods.static_craftItemStack_createCraftItemByBukkitItem, null, item);
    }

    /**
     * itemからNBTTagCompoundを取得し返す。
     * @param item 取得するアイテム
     * @return NBTTag
     */
    public static Object getNmsTagCompound(ItemStack item) {
        // ItemStack --> NmsItemStack
        Object nmsItem = getHandle(item);

        // NmsItemStackからNBTTagを取得
        Object nmsTag = ReflectionUtil.invoke(Methods.nmsItemStack_getTag, nmsItem);
        if (nmsTag == null) {
            nmsTag = ReflectionUtil.newInstance(Constructors.nmsNBTTagCompound);
        }

        return nmsTag;
    }

    /**
     * 引数itemのNBTTagCompoundに引数nbtTagを上書きした新たなItemStackを返す。
     * @param item アイテム
     * @param nbtTag NBTTag
     * @return NBTTagを上書きしたアイテム
     */
    public static ItemStack setNmsTagCompound(ItemStack item, Object nbtTag) {
        // ItemStack --> NmsItemStack
        Object nmsItem = getHandle(item);

        // NmsItemStackに新しいNBTTagとして上書き
        ReflectionUtil.invoke(Methods.nmsItemStack_setTag, nmsItem, nbtTag);

        return item;
    }

    /**
     * 引数itemのNBTTagの値を書き換えた新たなItemStackを返す。
     * @param item 書き換えるItemStack
     * @param nbtBaseArray 新しい値を格納したNBTBaseインスタンス
     * @return NBTTagを書き換えたItemStack
     */
    public static ItemStack setTag(ItemStack item, NBTBase... nbtBaseArray) {
        Object compound = getNmsTagCompound(item);

        // 値を書き換える
        for (NBTBase nbtBase : nbtBaseArray) {
            ReflectionUtil.invoke(Methods.nmsNBTTagCompound_set, compound, nbtBase.getKey(), nbtBase.getNmsNBTBase());
        }

        return setNmsTagCompound(item, compound);
    }

    public static ItemStack setUnbreakableNBT(ItemStack item, boolean value) {
        NBTTagByte nbt = new NBTTagByte(ItemNBTType.UNBREAKABLE.getTagName(), (byte) (value ? 1 : 0));

        return setTag(item, nbt);
    }

    public static ItemStack setCanDestroyNBT(ItemStack item, String... valueArray) {
        Object nmsCompound = getNmsTagCompound(item);

        NBTTagList nbtList = new NBTTagList(ItemNBTType.CAN_DESTROY.getTagName(), ReflectionUtil.invoke(Methods.nmsNBTTagCompound_get, nmsCompound, ItemNBTType.CAN_DESTROY.getTagName()));

        for (String value : valueArray) {
            if (!nbtList.containsValue(value)) {
                nbtList.addValue(NBTBase.createTag(ItemNBTType.CAN_DESTROY.getTagName(), value));
            }
        }

        return setTag(item, nbtList);
    }

    public static ItemStack setCanPlaceOnNBT(ItemStack item, String... valueArray) {
        Object nmsCompound = getNmsTagCompound(item);

        NBTTagList nbtList = new NBTTagList(ItemNBTType.CAN_PLACE_ON.getTagName(), ReflectionUtil.invoke(Methods.nmsNBTTagCompound_get, nmsCompound, ItemNBTType.CAN_PLACE_ON.getTagName()));

        for (String value : valueArray) {
            if (!nbtList.containsValue(value)) {
                nbtList.addValue(NBTBase.createTag(ItemNBTType.CAN_PLACE_ON.getTagName(), value));
            }
        }

        return setTag(item, nbtList);
    }

    public static ItemStack setAttributeModifiersNBT(ItemStack item, String uniqueName, ItemNBTAttributeType attributeName, double attributeValue, int operation) {
        Object nmsCompound = getNmsTagCompound(item);

        Object nmsList = ReflectionUtil.invoke(Methods.nmsNBTTagCompound_get, nmsCompound, ItemNBTType.ATTRIBUTE_MODIFIERS.getTagName());
        // NmsNBTTagListが存在しない場合は新規インスタンスを生成する
        if (nmsList == null) {
            nmsList = ReflectionUtil.newInstance(Constructors.nmsNBTTagList);
        }

        HashMap<String, NBTBase> map = Maps.newHashMap();

        NBTTagString tagUniqueName = new NBTTagString("", uniqueName);
        map.put("Name", tagUniqueName);

        NBTTagString tagAttributeName = new NBTTagString("", attributeName.getTagName());
        map.put("AttributeName", tagAttributeName);

        NBTTagDouble tagAttributeValue = new NBTTagDouble("", attributeValue);
        map.put("Amount", tagAttributeValue);

        NBTTagInt tagOperation = new NBTTagInt("", operation);
        map.put("Operation", tagOperation);

        NBTTagLong tagUUIDLeast = new NBTTagLong("", UUID.randomUUID().getLeastSignificantBits());
        map.put("UUIDLeast", tagUUIDLeast);

        NBTTagLong tagUUIDMost = new NBTTagLong("", UUID.randomUUID().getMostSignificantBits());
        map.put("UUIDMost", tagUUIDMost);

        NBTTagCompound compound = new NBTTagCompound(ItemNBTType.ATTRIBUTE_MODIFIERS.getTagName(), map);

        ReflectionUtil.invoke(Methods.nmsNBTTagList_addNBTBase, nmsList, compound.getNmsNBTBase());
        ReflectionUtil.invoke(Methods.nmsNBTTagCompound_set, nmsCompound, ItemNBTType.ATTRIBUTE_MODIFIERS.getTagName(), nmsList);

        return setNmsTagCompound(item, nmsCompound);
    }
}
