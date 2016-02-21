package com.github.erozabesu.yplutillibrary.object.nbt;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author erozabesu
 *
 */
public abstract class NBTBase {

    /** NBTタグキー */
    private String key;

    /** NmsNBTBaseインスタンス */
    private Object nmsNBTBase;

    //〓 Main 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public NBTBase() {
    }

    /**
     * NmsNBTBaseを継承した各型のクラスの変数の型を示すID
     * @return 変数の型を示すID
     */
    public abstract int getClassTypeId();

    /**
     * NmsNBTBaseを継承した各型のクラスに格納されている設定値を返す。
     * @return 設定値
     */
    public abstract Object getRawValue();

    /**
     * 引数rawValueの変数の型を示すIDを取得し返す。
     * @param rawValue IDを取得するインスタンス
     * @return 変数の型を示すID
     */
    public static byte getClassTypeId(Object rawValue) {
        if (rawValue instanceof Byte)
            return 1;
        else if (rawValue instanceof Short)
            return 2;
        else if (rawValue instanceof Integer)
            return 3;
        else if (rawValue instanceof Long)
            return 4;
        else if (rawValue instanceof Float)
            return 5;
        else if (rawValue instanceof Double)
            return 6;
        else if (rawValue instanceof byte[])
            return 7;
        else if (rawValue instanceof String)
            return 8;
        else if (rawValue instanceof List)
            return 9;
        else if (rawValue instanceof HashMap)
            return 10;
        else if (rawValue instanceof int[])
            return 11;
        else
            return 0;
    }

    @SuppressWarnings("unchecked")
    public static NBTBase convertNativeTag(NBTBase nbt) {
        switch (nbt.getClassTypeId()) {
        case 0:
            // NBTTagEnd
            return null;
        case 1:
            return new NBTTagByte(nbt.getKey(), (Byte) nbt.getRawValue());
        case 2:
            return new NBTTagShort(nbt.getKey(), (Short) nbt.getRawValue());
        case 3:
            return new NBTTagInt(nbt.getKey(), (Integer) nbt.getRawValue());
        case 4:
            return new NBTTagLong(nbt.getKey(), (Long) nbt.getRawValue());
        case 5:
            return new NBTTagFloat(nbt.getKey(), (Float) nbt.getRawValue());
        case 6:
            return new NBTTagDouble(nbt.getKey(), (Double) nbt.getRawValue());
        case 7:
            return new NBTTagByteArray(nbt.getKey(), (byte[]) nbt.getRawValue());
        case 8:
            return new NBTTagString(nbt.getKey(), (String) nbt.getRawValue());
        case 9:
            return new NBTTagList(nbt.getKey(), (List<NBTBase>) nbt.getRawValue());
        case 10:
            return new NBTTagCompound(nbt.getKey(), (HashMap<String, NBTBase>) nbt.getRawValue());
        case 11:
            return new NBTTagIntArray(nbt.getKey(), (int[]) nbt.getRawValue());
        default:
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static NBTBase createTag(String key, Object rawValue) {
        switch (getClassTypeId(rawValue)) {
        case 0:
            // NBTTagEnd
            return null;
        case 1:
            return new NBTTagByte(key, (Byte) rawValue);
        case 2:
            return new NBTTagShort(key, (Short) rawValue);
        case 3:
            return new NBTTagInt(key, (Integer) rawValue);
        case 4:
            return new NBTTagLong(key, (Long) rawValue);
        case 5:
            return new NBTTagFloat(key, (Float) rawValue);
        case 6:
            return new NBTTagDouble(key, (Double) rawValue);
        case 7:
            return new NBTTagByteArray(key, (byte[]) rawValue);
        case 8:
            return new NBTTagString(key, (String) rawValue);
        case 9:
            return new NBTTagList(key, (List<NBTBase>) rawValue);
        case 10:
            return new NBTTagCompound(key, (HashMap<String, NBTBase>) rawValue);
        case 11:
            return new NBTTagIntArray(key, (int[]) rawValue);
        default:
            return null;
        }
    }

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @return NBTタグキー */
    public String getKey() {
        return key;
    }

    /** @return NmsNBTBaseインスタンス */
    public Object getNmsNBTBase() {
        return nmsNBTBase;
    }

    //〓 Setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** @param key NBTタグキー */
    public void setKey(String key) {
        this.key = key;
    }

    /** @param nmsNBTBase NmsNBTBaseインスタンス */
    public void setNmsNBTBase(Object nmsNBTBase) {
        this.nmsNBTBase = nmsNBTBase;
    }
}
