package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class NBTTagShort extends NBTBase {

    public NBTTagShort() {
    }

    public NBTTagShort(String key, short value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagShort, value));
    }

    public NBTTagShort(String key, Object nmsNBTTagShort) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagShort);
    }

    @Override
    public int getClassTypeId() {
        return 2;
    }

    @Override
    public Short getRawValue() {
        return (Short) ReflectionUtil.getFieldValue(Fields.nmsNBTTagShort_data, this.getNmsNBTBase());
    }

    public void setRawValue(short value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagShort_data, this.getNmsNBTBase(), value);
    }
}
