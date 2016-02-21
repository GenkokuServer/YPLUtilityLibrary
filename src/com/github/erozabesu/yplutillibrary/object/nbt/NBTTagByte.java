package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;


public class NBTTagByte extends NBTBase {

    public NBTTagByte() {
    }

    public NBTTagByte(String key, byte value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagByte, value));
    }

    public NBTTagByte(String key, Object nmsNBTTagByte) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagByte);
    }

    @Override
    public int getClassTypeId() {
        return 1;
    }

    @Override
    public Byte getRawValue() {
        return (Byte) ReflectionUtil.getFieldValue(Fields.nmsNBTTagByte_data, this.getNmsNBTBase());
    }

    public void setRawValue(byte value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagByte_data, this.getNmsNBTBase(), value);
    }
}
