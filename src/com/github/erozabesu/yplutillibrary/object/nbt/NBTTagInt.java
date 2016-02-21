package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;


public class NBTTagInt extends NBTBase {

    public NBTTagInt() {
    }

    public NBTTagInt(String key, int value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagInt, value));
    }

    public NBTTagInt(String key, Object nmsNBTTagInt) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagInt);
    }

    @Override
    public int getClassTypeId() {
        return 3;
    }

    @Override
    public Integer getRawValue() {
        return (Integer) ReflectionUtil.getFieldValue(Fields.nmsNBTTagInt_data, this.getNmsNBTBase());
    }

    public void setRawValue(int value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagInt_data, this.getNmsNBTBase(), value);
    }
}
