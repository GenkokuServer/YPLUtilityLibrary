package com.github.erozabesu.yplutillibrary.object.nbt;

import java.util.List;

import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class NBTTagList extends NBTBase {

    public NBTTagList() {
    }

    public NBTTagList(String key, List<NBTBase> value) {
        this.setKey(key);

        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagList));
        for (NBTBase nbt : value) {
            this.addValue(nbt);
        }
    }

    public NBTTagList(String key, Object nmsNBTTagList) {
        this.setKey(key);
        this.setNmsNBTBase(nmsNBTTagList == null ? ReflectionUtil.newInstance(Constructors.nmsNBTTagList) : nmsNBTTagList);
    }

    public NBTTagList(String key) {
        this.setKey(key);
        this.setNmsNBTBase(ReflectionUtil.newInstance(Constructors.nmsNBTTagList));
    }

    @Override
    public int getClassTypeId() {
        return 9;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getRawValue() {
        return (List<Object>) ReflectionUtil.getFieldValue(Fields.nmsNBTTagList_list, this.getNmsNBTBase());
    }

    /**
     * NmsNBTTagListに格納されている値の変数の型を示すIDを返す。
     * @return 変数のタイプID
     */
    public byte getCurrentTypeId() {
        return (Byte) ReflectionUtil.getFieldValue(Fields.nmsNBTTagList_type, this.getNmsNBTBase());
    }

    /**
     * 引数valueとNmsNBTTagListに格納されている値の変数の型が一致しているかどうかを返す。
     * @param rawValue チェックする値
     * @return 一致しているかどうか
     */
    public boolean isSamePrimitiveType(Object rawValue) {
        return this.getCurrentTypeId() == getClassTypeId(rawValue);
    }

    /**
     * 引数rawValueと同値の要素がNmsNBTTagListに格納されているかどうかを返す。<br>
     * NmsNBTTagListに格納されている各NmsNBTBase内の値との比較を行う。<br>
     * 値の比較はインスタンスではなく、数値、もしくは文字列の一致で判断する。<br>
     * Array、List型を引数に渡した場合に関しては、比較を諦めたので常にfalseを返す。
     * @param rawValue チェックする値
     * @return NmsNBTTagListに同値の値が含まれているかどうか
     */
    public boolean containsValue(Object rawValue) {
        // NmsNBTTagListに格納されている値の型と引数valueの型が一致しない場合falseを返す
        if (!this.isSamePrimitiveType(rawValue)) {
            return false;
        }

        int typeId = this.getCurrentTypeId();

        if (typeId == 0) {
            // Do nothing
        } else if(typeId == 1) {
            byte value = ((Byte) rawValue).byteValue();
            for (Object listValue : this.getRawValue()) {
                if (value == ((Byte) ReflectionUtil.getFieldValue(Fields.nmsNBTTagByte_data, listValue)).byteValue()) {
                    return true;
                }
            }
        } else if (typeId == 2) {
            short value = ((Short) rawValue).byteValue();
            for (Object listValue : this.getRawValue()) {
                if (value == ((Short) ReflectionUtil.getFieldValue(Fields.nmsNBTTagShort_data, listValue)).shortValue()) {
                    return true;
                }
            }
        } else if (typeId == 3) {
            int value = ((Integer) rawValue).byteValue();
            for (Object listValue : this.getRawValue()) {
                if (value == ((Integer) ReflectionUtil.getFieldValue(Fields.nmsNBTTagInt_data, listValue)).intValue()) {
                    return true;
                }
            }
        } else if (typeId == 4) {
            long value = ((Long) rawValue).byteValue();
            for (Object listValue : this.getRawValue()) {
                if (value == ((Long) ReflectionUtil.getFieldValue(Fields.nmsNBTTagLong_data, listValue)).longValue()) {
                    return true;
                }
            }
        } else if (typeId == 5) {
            float value = ((Float) rawValue).byteValue();
            for (Object listValue : this.getRawValue()) {
                if (value == ((Float) ReflectionUtil.getFieldValue(Fields.nmsNBTTagFloat_data, listValue)).floatValue()) {
                    return true;
                }
            }
        } else if (typeId == 6) {
            double value = ((Double) rawValue).byteValue();
            for (Object listValue : this.getRawValue()) {
                if (value == ((Double) ReflectionUtil.getFieldValue(Fields.nmsNBTTagDouble_data, listValue)).doubleValue()) {
                    return true;
                }
            }
        } else if (typeId == 7) {
            // Do nothing
        } else if (typeId == 8) {
            String value = (String) rawValue;
            for (Object listValue : this.getRawValue()) {
                if (value.equalsIgnoreCase((String) ReflectionUtil.getFieldValue(Fields.nmsNBTTagString_data, listValue))) {
                    return true;
                }
            }
        } else if (typeId == 9) {
            // Do nothing
        } else if (typeId == 10) {
            // Do nothing
        } else if (typeId == 11) {
            // Do nothing
        }

        return false;
    }

    /**
     * NmsNBTTagListにNmsNBTBaseインスタンスを追加する。
     * @param nbt 追加する値
     */
    public void addValue(NBTBase nbt) {
        ReflectionUtil.invoke(Methods.nmsNBTTagList_addNBTBase, this.getNmsNBTBase(), nbt.getNmsNBTBase());
    }
}
