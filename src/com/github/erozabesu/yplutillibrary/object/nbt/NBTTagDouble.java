package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;


public class NBTTagDouble extends NBTBase {

    public NBTTagDouble() {
    }

    public NBTTagDouble(String key, double value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagDouble, value));
    }

    public NBTTagDouble(String key, Object nmsNBTTagDouble) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagDouble);
    }

    @Override
    public int getClassTypeId() {
        return 6;
    }

    @Override
    public Double getRawValue() {
        return (Double) ReflectionUtil.getFieldValue(Fields.nmsNBTTagDouble_data, this.getNmsNBTBase());
    }

    public void setRawValue(double value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagDouble_data, this.getNmsNBTBase(), value);
    }
}
