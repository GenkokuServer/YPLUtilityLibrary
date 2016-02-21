package com.github.erozabesu.yplutillibrary.object.nbt;

import java.util.HashMap;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;


public class NBTTagCompound extends NBTBase {

    public NBTTagCompound() {
    }

    public NBTTagCompound(String key, HashMap<String, NBTBase> value) {
        this.setKey(key);

        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagCompound));
        for (String valueKey : value.keySet()) {
            this.put(valueKey, value.get(valueKey));
        }
    }

    public NBTTagCompound(String key, Object nmsNBTTagCompound) {
        this.setKey(key);

        if (nmsNBTTagCompound == null) {
            this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagCompound));
        } else {
            this.setNmsNBTBase(nmsNBTTagCompound);
        }
    }

    @Override
    public int getClassTypeId() {
        return 10;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HashMap<String, Object> getRawValue() {
        return (HashMap<String, Object>) ReflectionUtil.getFieldValue(Fields.nmsNBTTagCompound_map, this.getNmsNBTBase());
    }

    public void put(String key, NBTBase value) {
        ReflectionUtil.invoke(Methods.nmsNBTTagCompound_set, this.getNmsNBTBase(), key, value.getNmsNBTBase());
    }

    public Object get(String key) {
        return ReflectionUtil.invoke(Methods.nmsNBTTagCompound_get, this.getNmsNBTBase(), key);
    }

    public boolean getBoolean(String key) {
        return (Boolean) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getBoolean, this.getNmsNBTBase(), key);
    }

    public byte getByte(String key) {
        return (Byte) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getByte, this.getNmsNBTBase(), key);
    }

    public byte[] getByteArray(String key) {
        return (byte[]) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getByteArray, this.getNmsNBTBase(), key);
    }

    public double getDouble(String key) {
        return (Double) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getDouble, this.getNmsNBTBase(), key);
    }

    public float getFloat(String key) {
        return (Float) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getFloat, this.getNmsNBTBase(), key);
    }

    public int getInt(String key) {
        return (Integer) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getInt, this.getNmsNBTBase(), key);
    }

    public int[] getIntArray(String key) {
        return (int[]) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getIntArray, this.getNmsNBTBase(), key);
    }

    public long getLong(String key) {
        return (Long) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getLong, this.getNmsNBTBase(), key);
    }

    public short getShort(String key) {
        return (Short) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getShort, this.getNmsNBTBase(), key);
    }

    public String getString(String key) {
        return (String) ReflectionUtil.invoke(Methods.nmsNBTTagCompound_getString, this.getNmsNBTBase(), key);
    }
}
