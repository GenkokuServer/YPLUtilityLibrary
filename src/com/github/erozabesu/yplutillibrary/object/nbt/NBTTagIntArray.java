package com.github.erozabesu.yplutillibrary.object.nbt;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;


public class NBTTagIntArray extends NBTBase {

    public NBTTagIntArray() {
    }

    public NBTTagIntArray(String key, int[] value) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagIntArray, value));
    }

    public NBTTagIntArray(String key, Object nmsNBTTagIntArray) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagIntArray);
    }

    @Override
    public int getClassTypeId() {
        return 11;
    }

    @Override
    public int[] getRawValue() {
        return (int[]) ReflectionUtil.getFieldValue(Fields.nmsNBTTagIntArray_data, this.getNmsNBTBase());
    }

    public void setRawValue(int[] value) {
        ReflectionUtil.setFieldValue(Fields.nmsNBTTagIntArray_data, this.getNmsNBTBase(), value);
    }
}
