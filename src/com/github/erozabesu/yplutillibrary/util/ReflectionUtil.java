package com.github.erozabesu.yplutillibrary.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;

public class ReflectionUtil {

    //〓 Package Name 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    private static String bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    private static String nmsPackage = "net.minecraft.server." + getBukkitVersion();
    private static String craftPackage = "org.bukkit.craftbukkit." + getBukkitVersion();

    private static String yplKartPackage = "com.github.erozabesu.yplkart.override." + getBukkitVersion();
    private static String yplTitanPackage = "com.github.erozabesu.ypltitan.override." + getBukkitVersion();

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static String getBukkitVersion() {
        return bukkitVersion;
    }

    public static String getNMSPackageName() {
        return nmsPackage;
    }

    public static String getCraftPackageName() {
        return craftPackage;
    }

    public static String getYPLKartPackageName() {
        return yplKartPackage;
    }

    public static String getYPLTitanPackageName() {
        return yplTitanPackage;
    }

    public static Class<?> getNMSClass(String s) {
        try {
            return Class.forName(getNMSPackageName() + "." + s);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getCraftClass(String s) {
        try {
            return Class.forName(getCraftPackageName() + "." + s);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getYPLKartClass(String s) {
        try {
            return Class.forName(getYPLKartPackageName() + "." + s);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getYPLTitanClass(String s) {
        try {
            return Class.forName(getYPLTitanPackageName() + "." + s);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //〓 Java Reflection 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数instanceオブジェクトのクラスから引数fieldNameフィールドを取得し返す
     * private、publicを問わず、全てのスーパークラスを遡って取得する
     * @param instance フィールドを取得するオブジェクト
     * @param fieldName フィールド名
     * @return 取得したフィールド
     */
    public static Field getField(Object instance, String fieldName) {
        return getField(instance.getClass(), fieldName);
    }

    /**
     * 引数clazzクラスから引数fieldNameフィールドを取得し返す
     * private、publicを問わず、全てのスーパークラスを遡って取得する
     * @param clazz フィールドを取得するクラス
     * @param fieldName フィールド名
     * @return 取得したフィールド
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = null;
        while (clazz != null) {
            try {
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                break;
            } catch (Exception e) {
                clazz = clazz.getSuperclass();
            }
        }

        return field;
    }

    public static Object getFieldValue(Object instance, String fieldName){
        Field field = getField(instance, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Object getFieldValue(Field field, Object instance){
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Object getStaticFieldValue(Field field){
        if (field != null) {
            field.setAccessible(true);
            try {
                return field.get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 引数clazzクラスから引数methodNameメソッドを取得し返す
     * private、publicを問わず、全てのスーパークラスを遡って取得する
     * @param clazz フィールドを取得するクラス
     * @param methodName メソッド名
     * @param classes メソッド引数
     * @return 取得したメソッド
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class... classes) {
        Method method = null;
        while (clazz != null) {
            try {
                method = clazz.getDeclaredMethod(methodName, classes);
                method.setAccessible(true);
                break;
            } catch (Exception e) {
                clazz = clazz.getSuperclass();
            }
        }

        return method;
    }

    /**
     * 引数clazzクラスからコンストラクタを取得し返す
     * private、publicを問わず、全てのスーパークラスを遡って取得する
     * @param clazz コンストラクタを取得するクラス
     * @param classes コンストラクタ引数
     * @return 取得したコンストラクタ
     */
    public static Constructor getConstructor(Class<?> clazz, Class... classes) {
        Constructor constructor = null;
        while (clazz != null) {
            try {
                constructor = clazz.getConstructor(classes);
                constructor.setAccessible(true);
                break;
            } catch (Exception e) {
                clazz = clazz.getSuperclass();
            }
        }

        return constructor;
    }

    public static void setFieldValue(Field field, Object instance, Object value) {
        try {
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invoke(Method method, Object instance, Object... objects) {
        try {
            return method.invoke(instance, objects);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object newInstance(Constructor<?> constructor, Object... objects) {
        try {
            return constructor.newInstance(objects);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 引数instanceが引数clazzを継承しているクラスのインスタンスかどうかを返す。
     * @param instance チェックするインスタンス
     * @param clazz スーパークラス
     * @return 引数instanceが引数clazzを継承しているかどうか
     */
    public static boolean instanceOf(Object instance, Class<?> clazz) {
        Class<?> instanceClass = instance.getClass();

        while (instanceClass != null) {
            if (instanceClass.getSimpleName().equalsIgnoreCase(clazz.getSimpleName())) {
                return true;
            }

            instanceClass = instanceClass.getSuperclass();
        }

        return false;
    }
}
