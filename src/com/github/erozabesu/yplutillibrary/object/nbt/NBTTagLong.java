package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class NBTTagLong extends NBTBase {

    public NBTTagLong() {
    }

    public NBTTagLong(String key, long value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagLong, value));
    }

    public NBTTagLong(String key, Object nmsNBTTagLong) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagLong);
    }

    @Override
    public int getClassTypeId() {
        return 4;
    }

    @Override
    public Long getRawValue() {
        return (Long) ReflectionUtil.getFieldValue(Fields.nmsNBTTagLong_data, this.getNmsNBTBase());
    }

    public void setRawValue(long value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagLong_data, this.getNmsNBTBase(), value);
    }
}
