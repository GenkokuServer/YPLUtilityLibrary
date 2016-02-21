package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;


public class NBTTagFloat extends NBTBase {

    public NBTTagFloat() {
    }

    public NBTTagFloat(String key, float value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagFloat, value));
    }

    public NBTTagFloat(String key, Object nmsNBTTagFloat) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagFloat);
    }

    @Override
    public int getClassTypeId() {
        return 5;
    }

    @Override
    public Float getRawValue() {
        return (Float) ReflectionUtil.getFieldValue(Fields.nmsNBTTagFloat_data, this.getNmsNBTBase());
    }

    public void setRawValue(float value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagFloat_data, this.getNmsNBTBase(), value);
    }
}
