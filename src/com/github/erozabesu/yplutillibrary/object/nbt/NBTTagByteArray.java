package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;


public class NBTTagByteArray extends NBTBase {

    public NBTTagByteArray() {
    }

    public NBTTagByteArray(String key, byte[] value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagByteArray, value));
    }

    public NBTTagByteArray(String key, Object nmsNBTTagByteArray) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagByteArray);
    }

    @Override
    public int getClassTypeId() {
        return 7;
    }

    @Override
    public byte[] getRawValue() {
        return (byte[]) ReflectionUtil.getFieldValue(Fields.nmsNBTTagByteArray_data, this.getNmsNBTBase());
    }

    public void setRawValue(byte[] value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagByteArray_data, this.getNmsNBTBase(), value);
    }
}
