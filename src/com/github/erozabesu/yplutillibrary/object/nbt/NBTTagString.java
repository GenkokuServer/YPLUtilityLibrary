package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class NBTTagString extends NBTBase {

    public NBTTagString() {
    }

    public NBTTagString(String key, String value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagString, value));
    }

    public NBTTagString(String key, Object nmsNBTTagString) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagString);
    }

    @Override
    public int getClassTypeId() {
        return 8;
    }

    @Override
    public String getRawValue() {
        return (String) ReflectionUtil.getFieldValue(Fields.nmsNBTTagString_data, this.getNmsNBTBase());
    }

    public void setRawValue(String value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagString_data, this.getNmsNBTBase(), value);
    }
}
