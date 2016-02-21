package com.github.erozabesu.yplutillibrary.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragonPart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.github.erozabesu.yplutillibrary.enumdata.EntityGroup;
import com.github.erozabesu.yplutillibrary.object.CraftWhiteList;
import com.github.erozabesu.yplutillibrary.object.IRecipe;
import com.github.erozabesu.yplutillibrary.reflection.Classes;
import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.google.common.collect.Lists;

/**
 * 細かいユーティリティを集めたクラス。
 * @author erozabesu
 */
public class CommonUtil {

    public static HashSet<IRecipe> customRecipeSet = new HashSet<IRecipe>();
    public static HashSet<CraftWhiteList> craftWhiteListSet = new HashSet<CraftWhiteList>();

    public static HashSet<UUID> isVanishing = new HashSet<UUID>();

    public static List<PotionEffectType> positivePotionEffectTypes = Arrays.asList(new PotionEffectType[]{
        PotionEffectType.ABSORPTION,
        PotionEffectType.DAMAGE_RESISTANCE,
        PotionEffectType.FAST_DIGGING,
        PotionEffectType.FIRE_RESISTANCE,
        PotionEffectType.HEAL,
        PotionEffectType.HEALTH_BOOST,
        PotionEffectType.INCREASE_DAMAGE,
        PotionEffectType.INVISIBILITY,
        PotionEffectType.JUMP,
        PotionEffectType.NIGHT_VISION,
        PotionEffectType.REGENERATION,
        PotionEffectType.SATURATION,
        PotionEffectType.SPEED,
        PotionEffectType.WATER_BREATHING
        });

    public static List<PotionEffectType> negativePotionEffectTypes = Arrays.asList(new PotionEffectType[]{
        PotionEffectType.BLINDNESS,
        PotionEffectType.CONFUSION,
        PotionEffectType.HARM,
        PotionEffectType.HUNGER,
        PotionEffectType.POISON,
        PotionEffectType.SLOW,
        PotionEffectType.SLOW_DIGGING,
        PotionEffectType.WEAKNESS,
        PotionEffectType.WITHER
        });

    public static List<PotionEffectType> interferencePotionEffectTypes = Arrays.asList(new PotionEffectType[]{
            PotionEffectType.BLINDNESS,
            PotionEffectType.CONFUSION,
            PotionEffectType.HUNGER,
            PotionEffectType.SLOW,
            PotionEffectType.SLOW_DIGGING,
            PotionEffectType.WEAKNESS
            });

    public static List<PotionEffectType> damageablePotionEffectTypes = Arrays.asList(new PotionEffectType[]{
            PotionEffectType.HARM,
            PotionEffectType.POISON,
            PotionEffectType.WITHER
            });

    //〓 Server 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * カスタムレシピを登録する。
     * @param recipe
     */
    public static void registerRecipe(IRecipe recipe) {
        Bukkit.addRecipe(recipe.getRecipe());
        customRecipeSet.add(recipe);
    }

    /**
     * クラフトホワイトリストを登録する。<br>
     * 指定した素材アイテムを含むクラフトにおいて、クラフトリザルトが指定したアイテムでない場合クラフトがキャンセルされる。
     * @param craftWhiteList CraftWhiteListインスタンス
     */
    public static void registerCraftWhiteListItem(CraftWhiteList craftWhiteList) {
        craftWhiteListSet.add(craftWhiteList);
    }

    /**
     * クラフトホワイトリストに登録されている素材アイテムリストを返す。
     * @return 素材アイテムリスト
     */
    public static List<ItemStack> getWhiteListedItemList() {
        List<ItemStack> itemList = new ArrayList<ItemStack>();
        for (CraftWhiteList whiteList : craftWhiteListSet) {
            itemList.add(whiteList.getCraftMaterial());
        }

        return itemList;
    }

    /**
     * NmsEntityTypesに新たなエンティティを登録、もしくは上書きを行う。
     * @param nmsEntityClass 登録するNmsEntityクラス
     * @param entityTypeName 登録するEntityType名
     * @param entityId 登録するエンティティID
     */
    @SuppressWarnings("unchecked")
    public static void registerEntityType(Class nmsEntityClass, String entityTypeName, int entityId) {
        ((Map<String, Class<?>>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityName_EntityClass)).put(entityTypeName, nmsEntityClass);
        ((Map<Class<?>, String>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityClass_EntityName)).put(nmsEntityClass, entityTypeName);
        ((Map<Integer, Class<?>>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityId_EntityClass)).put(entityId, nmsEntityClass);
        ((Map<Class<?>, Integer>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityClass_EntityId)).put(nmsEntityClass, entityId);
        ((Map<String, Integer>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityName_EntityId)).put(entityTypeName, entityId);
    }

    /**
     * NmsEntityTypesに登録されているエンティティを削除する。
     * @param nmsEntityClass 削除するNmsEntityクラス
     * @param entityTypeName 削除するEntityType名
     * @param entityId 削除するエンティティID
     */
    @SuppressWarnings("unchecked")
    public static void unregisterEntityType(Class nmsEntityClass, String entityTypeName, int entityId) {
        ((Map<String, Class<?>>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityName_EntityClass)).remove(entityTypeName);
        ((Map<Class<?>, String>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityClass_EntityName)).remove(nmsEntityClass);
        ((Map<Integer, Class<?>>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityId_EntityClass)).remove(entityId);
        ((Map<Class<?>, Integer>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityClass_EntityId)).remove(nmsEntityClass);
        ((Map<String, Integer>) ReflectionUtil.getStaticFieldValue(Fields.static_nmsEntityTypes_entityMap_EntityName_EntityId)).remove(entityTypeName);
    }

    /**
     * NmsEntityTypes、全バイオームに登録されているNmsEntityを引数nmsCustomEntityClassクラスで上書きし、スポーンするエンティティを差し替える。
     * @param nmsDefaultEntityClass 上書きされるデフォルトのNmsEntityクラス
     * @param nmsCustomEntityClass カスタムNmsEntityクラス
     * @param entityTypeName カスタムNmsEntityのEntityType名
     * @param entityId カスタムNmsEntityのエンティティID
     */
    public static void registerEntityToBiome(Class nmsDefaultEntityClass, Class nmsCustomEntityClass, String entityTypeName, int entityId) {
        // NmsEntityTypesにカスタムエンティティクラスを登録、もしくは上書き
        registerEntityType(nmsCustomEntityClass, entityTypeName, entityId);

        // 全バイオームの取得、繰り返し
        for (Object biomeBase : (Object[]) ReflectionUtil.getStaticFieldValue(Fields.static_nmsBiomeBase_biomes)) {
            if (biomeBase == null) {
                break;
            }

            // バイオームに登録されている動物エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_animalEntityList, biomeBase)) {
                // 引数nmsDefaultEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合カスタムクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsDefaultEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsCustomEntityClass);
                }
            }

            // バイオームに登録されているモンスターエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_monsterEntityList, biomeBase)) {
                // 引数nmsDefaultEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合カスタムクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsDefaultEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsCustomEntityClass);
                }
            }

            // バイオームに登録されている水生エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_waterAnimalEntityList, biomeBase)) {
                // 引数nmsDefaultEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合カスタムクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsDefaultEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsCustomEntityClass);
                }
            }

            // バイオームに登録されているアンビエントエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_ambientEntityList, biomeBase)) {
                // 引数nmsDefaultEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合カスタムクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsDefaultEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsCustomEntityClass);
                }
            }
        }
    }

    /**
     * NmsEntityTypes、全バイオームに新しく引数nmsCustomEntityClassクラスを登録する。<br>
     * 引数entityGroupには登録先のエンティティグループを指定する。
     * @param nmsDefaultEntityClass 上書きされるデフォルトのNmsEntityクラス
     * @param nmsCustomEntityClass カスタムNmsEntityクラス
     * @param entityTypeName カスタムNmsEntityのEntityType名
     * @param entityId カスタムNmsEntityのエンティティID
     * @param entityGroup 登録先のエンティティグループ
     */
    @SuppressWarnings("unchecked")
    public static void registerNewEntityToBiome(Class nmsCustomEntityClass, String entityTypeName, int entityId, EntityGroup entityGroup, int spawnRatio, int spawnAmount, int spawnChunkRange) {

        // NmsEntityTypesにカスタムエンティティクラスを登録、もしくは上書き
        registerEntityType(nmsCustomEntityClass, entityTypeName, entityId);

        List<EntityGroup> entityGroupList = Arrays.asList(entityGroup);
        Object biomeMeta = ReflectionUtil.newInstance(Constructors.nmsBiomeMeta, nmsCustomEntityClass, spawnRatio, spawnAmount, spawnChunkRange);

        // 全バイオームの取得、繰り返し
        for (Object biomeBase : (Object[]) ReflectionUtil.getStaticFieldValue(Fields.static_nmsBiomeBase_biomes)) {
            if (biomeBase == null) {
                break;
            }

            // バイオームに登録されている動物エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            if (entityGroupList.contains(EntityGroup.Animal)) {
                ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_animalEntityList, biomeBase)).add(biomeMeta);
            }

            // バイオームに登録されているモンスターエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            if (entityGroupList.contains(EntityGroup.Monster)) {
                ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_monsterEntityList, biomeBase)).add(biomeMeta);
            }

            // バイオームに登録されている水生エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            if (entityGroupList.contains(EntityGroup.WaterAnimal)) {
                ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_waterAnimalEntityList, biomeBase)).add(biomeMeta);
            }

            // バイオームに登録されているアンビエントエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            if (entityGroupList.contains(EntityGroup.Ambient)) {
                ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_ambientEntityList, biomeBase)).add(biomeMeta);
            }
        }
    }

    /**
     * NmsEntityTypes、全バイオームに登録されているカスタムNmsEntityをデフォルトのNmsEntityクラスに戻す。
     * @param nmsDefaultEntityClass デフォルトのNmsEntityクラス
     * @param nmsCustomEntityClass カスタムNmsEntityクラス
     * @param entityTypeName カスタムNmsEntityのEntityType名
     * @param entityId カスタムNmsEntityのエンティティID
     */
    public static void unregisterEntityFromBiome(Class nmsDefaultEntityClass, Class nmsCustomEntityClass, String entityTypeName, int entityId) {
        // NmsEntityTypesからカスタムNmsEntityクラスを削除し、デフォルトのNmsEntityクラスに戻す
        unregisterEntityType(nmsCustomEntityClass, entityTypeName, entityId);
        registerEntityType(nmsDefaultEntityClass, entityTypeName, entityId);

        // 全バイオームの取得、繰り返し
        for (Object biomeBase : (Object[]) ReflectionUtil.getStaticFieldValue(Fields.static_nmsBiomeBase_biomes)) {
            if (biomeBase == null) {
                break;
            }

            // バイオームに登録されている動物エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_animalEntityList, biomeBase)) {
                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合デフォルトのNmsEntityクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsDefaultEntityClass);
                }
            }

            // バイオームに登録されているモンスターエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_monsterEntityList, biomeBase)) {
                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合デフォルトのNmsEntityクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsDefaultEntityClass);
                }
            }

            // バイオームに登録されている水生エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_waterAnimalEntityList, biomeBase)) {
                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合デフォルトのNmsEntityクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsDefaultEntityClass);
                }
            }

            // バイオームに登録されているアンビエントエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            for (Object biomeMeta : (List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_ambientEntityList, biomeBase)) {
                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合デフォルトのNmsEntityクラスで上書き
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    ReflectionUtil.setFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, biomeMeta, nmsDefaultEntityClass);
                }
            }
        }
    }

    /**
     * NmsEntityTypes、全バイオームに登録されているカスタムNmsEntityを削除する。
     * @param nmsDefaultEntityClass デフォルトのNmsEntityクラス
     * @param nmsCustomEntityClass カスタムNmsEntityクラス
     * @param entityTypeName カスタムNmsEntityのEntityType名
     * @param entityId カスタムNmsEntityのエンティティID
     */
    public static void unregisterNewEntityFromBiome(Class nmsDefaultEntityClass, Class nmsCustomEntityClass, String entityTypeName, int entityId) {
        // NmsEntityTypesからカスタムNmsEntityクラスを削除し、デフォルトのNmsEntityクラスに戻す
        unregisterEntityType(nmsCustomEntityClass, entityTypeName, entityId);
        registerEntityType(nmsDefaultEntityClass, entityTypeName, entityId);

        // 全バイオームの取得、繰り返し
        for (Object biomeBase : (Object[]) ReflectionUtil.getStaticFieldValue(Fields.static_nmsBiomeBase_biomes)) {
            if (biomeBase == null) {
                break;
            }

            // バイオームに登録されている動物エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            Iterator animalIterator = ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_animalEntityList, biomeBase)).iterator();
            Object animalBiomeMeta;
            while (animalIterator.hasNext()) {
                animalBiomeMeta = animalIterator.next();

                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合配列から削除
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, animalBiomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    animalIterator.remove();
                }
            }

            // バイオームに登録されているモンスターエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            Iterator monsterIterator = ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_monsterEntityList, biomeBase)).iterator();
            Object monsterBiomeMeta;
            while (monsterIterator.hasNext()) {
                monsterBiomeMeta = monsterIterator.next();

                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合配列から削除
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, monsterBiomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    monsterIterator.remove();
                }
            }

            // バイオームに登録されている水生エンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            Iterator waterAnimalIterator = ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_waterAnimalEntityList, biomeBase)).iterator();
            Object waterAnimalBiomeMeta;
            while (waterAnimalIterator.hasNext()) {
                waterAnimalBiomeMeta = waterAnimalIterator.next();

                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合配列から削除
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, waterAnimalBiomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    waterAnimalIterator.remove();
                }
            }

            // バイオームに登録されているアンビエントエンティティを扱うNmsBiomeMetaの配列を取得、繰り返し
            Iterator ambientIterator = ((List) ReflectionUtil.getFieldValue(Fields.nmsBiomeBase_waterAnimalEntityList, biomeBase)).iterator();
            Object ambientBiomeMeta;
            while (ambientIterator.hasNext()) {
                ambientBiomeMeta = ambientIterator.next();

                // 引数nmsCustomEntityClassと一致するNmsEntityを格納しているNmsBiomeMetaがあった場合配列から削除
                Class<?> registeredClass = (Class<?>) ReflectionUtil.getFieldValue(Fields.nmsBiomeMeta_registeredEntityClass, ambientBiomeMeta);
                if (registeredClass.equals(nmsCustomEntityClass)) {
                    ambientIterator.remove();
                }
            }
        }
    }

    //〓 Config 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * pathがキーのObject値をconfigから取得し返す。<br>
     * configに値が存在しない場合はdefaultConfigから取得した値をconfigにセットし返す。
     * @param path コンフィグキー
     * @param config コンフィグ
     * @param defaultConfig デフォルトコンフィグ
     * @return
     */
    public static Object getObjectFromConfig(String path, YamlConfiguration config, YamlConfiguration defaultConfig) {
        if (!config.contains(path)) {
            config.set(path, defaultConfig.get(path));
        }
        return config.get(path);
    }

    /**
     * pathがキーのString値をconfigから取得し返す。<br>
     * configに値が存在しない場合はdefaultConfigから取得した値をconfigにセットし返す。
     * @param path コンフィグキー
     * @param config コンフィグ
     * @param defaultConfig デフォルトコンフィグ
     * @return
     */
    public static String getStringFromConfig(String path, YamlConfiguration config, YamlConfiguration defaultConfig) {
        if (!config.contains(path)) {
            config.set(path, defaultConfig.getString(path));
        }
        return config.getString(path);
    }

    /**
     * pathがキーのStringリストをconfigから取得し返す。<br>
     * configに値が存在しない場合はdefaultConfigから取得した値をconfigにセットし返す。
     * @param path コンフィグキー
     * @param config コンフィグ
     * @param defaultConfig デフォルトコンフィグ
     * @return
     */
    public static List<String> getStringListFromConfig(String path, YamlConfiguration config, YamlConfiguration defaultConfig) {
        if (!config.contains(path)) {
            config.set(path, defaultConfig.getStringList(path));
        }
        return config.getStringList(path);
    }

    /**
     * pathがキーのBoolean値をconfigから取得し返す。<br>
     * configに値が存在しない場合はdefaultConfigから取得した値をconfigにセットし返す。
     * @param path コンフィグキー
     * @param config コンフィグ
     * @param defaultConfig デフォルトコンフィグ
     * @return
     */
    public static Boolean getBooleanFromConfig(String path, YamlConfiguration config, YamlConfiguration defaultConfig) {
        if (!config.contains(path)) {
            config.set(path, defaultConfig.getBoolean(path));
        }
        return config.getBoolean(path);
    }

    /**
     * pathがキーのInteger値をconfigから取得し返す。<br>
     * configに値が存在しない場合はdefaultConfigから取得した値をconfigにセットし返す。
     * @param path コンフィグキー
     * @param config コンフィグ
     * @param defaultConfig デフォルトコンフィグ
     * @return
     */
    public static Integer getIntegerFromConfig(String path, YamlConfiguration config, YamlConfiguration defaultConfig) {
        if (!config.contains(path)) {
            config.set(path, defaultConfig.getInt(path));
        }
        return config.getInt(path);
    }

    //〓 Player 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static boolean isPlayer(Object object) {
        return (object instanceof Player);
    }

    public static Boolean isOnline(String name) {
        return Bukkit.getPlayerExact(name) != null;
    }

    public static Boolean isOnline(UUID id) {
        return Bukkit.getPlayer(id) != null;
    }

    public static Boolean isVanishing(Player player) {
        return isVanishing.contains(player.getUniqueId());
    }

    public static void setHide(Player player) {
        if (player.isOnline()) {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if (!otherPlayer.equals(player)) {
                    otherPlayer.hidePlayer(player);
                }
            }
        }

        isVanishing.add(player.getUniqueId());
    }

    public static void setUnHide(Player player) {
        if (player.isOnline()) {
            for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                if (!otherPlayer.equals(player)) {
                    otherPlayer.showPlayer(player);
                }
            }
        }

        isVanishing.remove(player.getUniqueId());
    }

    /**
     * 引数locationに生成した音声を、引数locaionから半径radiusブロック以内のプレイヤーに対し再生する。
     * @param location 音声を再生する座標
     * @param radius 半径
     * @param sound 音声
     * @param volume 音量
     * @param pitch ピッチ
     */
    public static void playSoundToLocation(Location location, double radius, Sound sound, float volume, float pitch) {
        for (Player player : getNearbyPlayers(location, radius)) {
            player.playSound(location, sound, volume, pitch);
        }
    }

    /**
     * playerの経験値バーからtakePercentの数値のパーセント値だけ経験値を差し引く。<br>
     * 差し引く経験値バーがない場合はレベルを1つ消費し経験値バー100％の状態から更に差し引く。<br>
     * 差し引く経験値が足りない場合は何もせずfalseを返す。<br>
     * 経験値オーヴ数ではなく経験値バーのパーセント値で処理する点に注意。<br>
     * BukkitのAPIでは経験値オーヴ数を差し引く処理は実装されていない。
     * @param player 差し引くプレイヤー
     * @param takePercent 差し引くパーセント値
     * @return 経験値を差し引いたかどうか
     */
    public static boolean takeExpByPercent(Player player, float takePercent) {
        int currentLevel = player.getLevel();
        float currentPercent = player.getExp();
        float takedPercent = currentPercent - takePercent;

        // 経験値を差し引いた結果マイナスになった場合、レベルダウン
        while (takedPercent < 0) {
            // レベルダウンするレベルを所持していない場合return false
            if (currentLevel < 1) {
                return false;
            }

            currentLevel--;
            takedPercent = 1.0F + takedPercent;
        }

        player.setLevel(currentLevel);
        player.setExp(takedPercent);

        return true;
    }

    //〓 Entity 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数nmsEntityオブジェクトからBukkitEntityを取得し返す
     * @param nmsEntity BukkitEntityを取得するNmsEntity
     * @return BukkitEntity
     */
    public static Entity getBukkitEntityFromNmsEntity(Object nmsEntity) {
        return (Entity) ReflectionUtil.invoke(Methods.nmsEntity_getBukkitEntity, nmsEntity);
    }

    public static boolean isInVoid(Entity entity) {
        double locationY = entity.getLocation().getY();
        return locationY < 0 || entity.getWorld().getMaxHeight() < locationY;
    }

    /**
     * entityが動物かどうかを返す。<br>
     * 動物とは、牛・豚等の非敵対生物、イカ等の非敵対水生生物、コウモリ等の非敵対飛行生物を指す。
     * @param entity チェックするエンティティ
     * @return 動物かどうか
     */
    public static boolean isAnimal(Entity entity) {
        Object nmsEntity = getCraftEntity(entity);
        return ReflectionUtil.instanceOf(nmsEntity, Classes.nmsEntityAnimal)
                || ReflectionUtil.instanceOf(nmsEntity, Classes.nmsEntityWaterAnimal)
                || ReflectionUtil.instanceOf(nmsEntity, Classes.nmsEntityAmbient);
    }

    /**
     * entityがモンスターかどうかを返す。<br>
     * モンスターとは、パッシブでプレイヤーをターゲットする生物全般を指す。
     * @param entity
     * @return
     */
    public static boolean isMonster(Entity entity) {
        Object nmsEntity = getCraftEntity(entity);
        return ReflectionUtil.instanceOf(nmsEntity, Classes.nmsEntityMonster)
                || entity instanceof EnderDragon
                || entity instanceof EnderDragonPart;
    }

    /**
     * entityが中立モブかどうかを返す。<br>
     * 中立モブとは、ゴーレム系の中立生物、村人を指す。
     * @param entity
     * @return
     */
    public static boolean isNeutralCreature(Entity entity) {
        Object nmsEntity = getCraftEntity(entity);
        return ReflectionUtil.instanceOf(nmsEntity, Classes.nmsEntityGolem)
                || entity instanceof Villager;
    }

    /**
     * 引数entityが固形ブロックに対して正面から接しているかどうかを返す。
     * @param entity チェックするエンティティ
     * @param forwardOffset 前方向のオフセット
     * @param heightOffset 地面からの高さのオフセット
     * @param yawOffset Yawのオフセット
     * @return 固形ブロックに接しているかどうか
     */
    public static boolean isStandingBySolidBlock(LivingEntity entity, double forwardOffset, double heightOffset, float yawOffset) {
        Location eyeLocation = entity.getEyeLocation().clone();
        eyeLocation.setYaw(eyeLocation.getYaw() + yawOffset);

        Vector direction = eyeLocation.getDirection().clone().multiply(1.0D + forwardOffset);
        eyeLocation.add(direction.getX(), direction.getY() + heightOffset - entity.getEyeHeight(), direction.getZ());

        return isSolidBlock(eyeLocation);
    }

    /**
     * 引数locationから半径radiusブロック以内のエンティティを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 引数locationから半径radiusブロック以内のエンティティ
     */
    public static List<Entity> getNearbyEntities(Location location, double radius) {
        List<Entity> entities = new ArrayList<Entity>();

        List<Chunk> nearbyChunks = getNearbyChunks(location, radius);
        if (nearbyChunks.isEmpty()) {
            return entities;
        }

        for (Chunk chunk : nearbyChunks) {
            for (Entity entity : chunk.getEntities()) {
                if (location.getWorld().equals(entity.getWorld())) {
                    if (location.distanceSquared(entity.getLocation()) < radius * radius) {
                        entities.add(entity);
                    }
                }
            }
        }

        return entities;
    }

    /**
     * 引数locationから半径radiusブロック以内の生物エンティティを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 引数locationから半径radiusブロック以内の生物エンティティ
     */
    public static List<LivingEntity> getNearbyLivingEntities(Location location, double radius) {
        List<Entity> entities = getNearbyEntities(location, radius);
        List<LivingEntity> livingEntities = new ArrayList<LivingEntity>();
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity)
                livingEntities.add((LivingEntity) entity);
        }
        return livingEntities;
    }

    /**
     * 引数locationから半径radiusブロック以内の最寄の生物エンティティを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 最寄の生物エンティティ。存在しない場合は{@code null}を返す。
     */
    public static Entity getNearestLivingEntity(Location location, double radius) {
        return getNearestEntity(getNearbyLivingEntities(location, radius), location);
    }

    /**
     * 引数locationから半径radiusブロック以内のプレイヤーを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 引数locationから半径radiusブロック以内のチャンクに存在するプレイヤー
     */
    public static List<Player> getNearbyPlayers(Location location, double radius) {
        List<Entity> entities = getNearbyEntities(location, radius);
        List<Player> humanEntities = new ArrayList<Player>();
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                humanEntities.add((Player) entity);
            }
        }
        return humanEntities;
    }

    /**
     * 引数locationから半径radiusブロック以内の最寄のプレイヤーを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 最寄のプレイヤー。存在しない場合は{@code null}を返す。
     */
    public static Player getNearestPlayer(Location location, double radius) {
        return (Player) getNearestEntity(getNearbyPlayers(location, radius), location);
    }

    /**
     * 引数locationから半径radiusブロック以内のサバイバルプレイヤーを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 引数locationから半径radiusブロック以内のサバイバルプレイヤー
     */
    public static List<Player> getNearbySurvivalPlayers(Location location, double radius) {
        List<Entity> entities = getNearbyEntities(location, radius);
        List<Player> humanEntities = new ArrayList<Player>();
        for (Entity entity : entities) {
            if (entity instanceof Player) {
                if (((Player) entity).getGameMode().equals(GameMode.SURVIVAL)) {
                    humanEntities.add((Player) entity);
                }
            }
        }
        return humanEntities;
    }

    /**
     * 引数locationから半径radiusブロック以内の最寄のサバイバルプレイヤーを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 最寄のプレイヤー。存在しない場合は{@code null}を返す。
     */
    public static Player getNearestSurvivalPlayer(Location location, double radius) {
        return (Player) getNearestEntity(getNearbySurvivalPlayers(location, radius), location);
    }

    /**
     * 引数entitiesの配列中のエンティティの内、最も引数locationとの直線距離が近いエンティティを返す。
     * @param entities エンティティリスト
     * @param location 基点となる座標
     * @return 最寄のエンティティ。存在しない場合は{@code null}を返す。
     */
    public static Entity getNearestEntity(List<? extends Entity> entities, Location location) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }

        Iterator<? extends Entity> iterator = entities.iterator();
        Entity entity = null;
        Entity tempEntity;
        while (iterator.hasNext()) {
            tempEntity = iterator.next();
            if (entity != null) {
                if (entity.getWorld().getName().equalsIgnoreCase(tempEntity.getWorld().getName())) {
                    if (entity.getLocation().distanceSquared(location) < tempEntity.getLocation().distanceSquared(location)) {
                        continue;
                    }
                }
            }
            entity = tempEntity;
        }

        return entity;
    }

    /**
     * @param entity
     * @return 引数entityが搭乗している一番下のエンティティ
     */
    public static Entity getEndVehicle(Entity entity) {
        Entity vehicle = entity.getVehicle();

        if (vehicle == null) {
            return entity;
        }

        while (vehicle.getVehicle() != null) {
            vehicle = vehicle.getVehicle();
        }

        return vehicle;
    }

    /**
     * passengerが搭乗している全Entityを返す
     * passengerが搭乗しているEntityが更に別のEntityに搭乗している場合を指す
     * @param passenger
     * @return passengerが搭乗している全EntityのList
     */
    public static List<Entity> getAllVehicle(Entity passenger) {
        List<Entity> entitylist = new ArrayList<Entity>();

        Entity vehicle = passenger.getVehicle() != null ? passenger.getVehicle() : null;
        while (vehicle != null) {
            entitylist.add(vehicle);
            vehicle = vehicle.getVehicle() != null ? vehicle.getVehicle() : null;
        }

        return entitylist;
    }

    /**
     * @param entity
     * @return 引数entityが搭乗している一番上のエンティティ
     */
    public static Entity getEndPassenger(Entity entity) {
        Entity passenger = entity.getPassenger();

        if (passenger == null) {
            return entity;
        }

        while (passenger.getPassenger() != null) {
            passenger = passenger.getPassenger();
        }

        return passenger;
    }

    /**
     * vehicleに搭乗している全Entityを返す
     * vehicleに搭乗しているEntityが更に別のEntityに搭乗されている場合を指す
     * @param vehicle
     * @return vehicleに搭乗している全EntityのList
     */
    public static ArrayList<Entity> getAllPassenger(Entity vehicle) {
        ArrayList<Entity> entitylist = new ArrayList<Entity>();

        Entity passenger = vehicle.getPassenger() != null ? vehicle.getPassenger() : null;
        while (passenger != null) {
            entitylist.add(passenger);
            passenger = passenger.getPassenger() != null ? passenger.getPassenger() : null;
        }
        return entitylist;
    }

    /**
     * 引数vehicleに搭乗している一番上のエンティティに引数riderを搭乗させる。
     * @param vehicle 搭乗されるエンティティ
     * @param rider 搭乗するエンティティ
     */
    public static void setPassengerToEnd(Entity vehicle, Entity rider){
        Entity endPassenegr = getEndPassenger(vehicle);
        if(endPassenegr != null){
            endPassenegr.setPassenger(rider);
        }
    }

    /**
     * 引数riderを搭乗しているエンティティから降ろし、引数riderに搭乗しているエンティティを代わりに搭乗させる。
     * @param rider 搭乗を解除するエンティティ
     */
    public static void leaveVehicle(Entity rider){
        Entity vehicle = rider.getVehicle();
        Entity passenger = rider.getPassenger();

        if(passenger != null && vehicle != null){
            rider.leaveVehicle();
            passenger.leaveVehicle();
            vehicle.setPassenger(passenger);
        }
    }

    /**
     * 引数vehicleに搭乗している全エンティティをデスポーンさせる。
     * @param vehicle 搭乗者をデスポーンさせるエンティティ
     * @return 1つ以上のエンティティをデスポーンさせたかどうか
     */
    public static boolean removeAllPassenger(Entity vehicle){
        List<Entity> passengerList = getAllPassenger(vehicle);
        if(passengerList == null || passengerList.isEmpty()) {
            return false;
        }

        for (Entity entity : passengerList){
            if(!(entity instanceof Player)) {
                entity.leaveVehicle();
                entity.remove();
            }
        }

        return true;
    }

    /**
     * 引数passengerが搭乗している全エンティティをデスポーンさせる。
     * @param passenger 搭乗しているエンティティをデスポーンさせるエンティティ
     * @return 1つ以上のエンティティをデスポーンさせたかどうか
     */
    public static boolean removeAllVehicle(Entity passenger){
        List<Entity> vehicleList = getAllVehicle(passenger);
        if(vehicleList == null || vehicleList.isEmpty()) {
            return false;
        }

        passenger.leaveVehicle();

        for (Entity entity : vehicleList){
            if(!(entity instanceof Player)) {
                entity.leaveVehicle();
                entity.remove();
            }
        }

        return true;
    }

    /**
     * 引数entityに付与されているポジティブなポーションエフェクトを除去する。
     * @param entity 除去するエンティティ
     */
    public static void removePositivePotionEffect(LivingEntity entity) {
        for (PotionEffectType potion : positivePotionEffectTypes) {
            entity.removePotionEffect(potion);
        }
    }

    /**
     * 引数entityに付与されているネガティブなポーションエフェクトを除去する。
     * @param entity 除去するエンティティ
     */
    public static void removeNegativePotionEffect(LivingEntity entity) {
        for (PotionEffectType potion : negativePotionEffectTypes) {
            entity.removePotionEffect(potion);
        }
    }

    /**
     * 引数entityに付与されている行動制限、阻害系のポーションエフェクトを除去する。
     * @param entity 除去するエンティティ
     */
    public static void removeInterferencePotionEffect(LivingEntity entity) {
        for (PotionEffectType potion : interferencePotionEffectTypes) {
            entity.removePotionEffect(potion);
        }
    }

    /**
     * 引数entityに付与されているダメージを伴うポーションエフェクトを除去する。
     * @param entity 除去するエンティティ
     */
    public static void removeDamageablePotionEffect(LivingEntity entity) {
        for (PotionEffectType potion : damageablePotionEffectTypes) {
            entity.removePotionEffect(potion);
        }
    }

    /**
     * entityの物理判定を無効にする
     * 無効に設定されたEntityはBlockへの接触判定が行われない
     * @param entity
     */
    public static void removeEntityCollision(Entity entity) {
        Object craftentity = getCraftEntity(entity);
        Field noclip = ReflectionUtil.getField(craftentity, "noclip");
        if (noclip != null) {
            try {
                noclip.setBoolean(craftentity, true);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 引数entityの体力の最大値を引数valueに変更する。
     * @param entity 値を変更するLivingEntity
     * @param value セットする値
     */
    public static void setAttributeMaxHealth(LivingEntity entity, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_maxHealth);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, getCraftEntity(entity), genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数nmsEntityLivingの体力の最大値を引数valueに変更する。
     * @param nmsEntityLiving 値を変更するNmsEntityLiving
     * @param value セットする値
     */
    public static void setAttributeMaxHealth(Object nmsEntityLiving, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_maxHealth);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, nmsEntityLiving, genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数entityの敵視範囲を引数valueに変更する。
     * @param entity 値を変更するLivingEntity
     * @param value セットする値
     */
    public static void setAttributeFollowRange(LivingEntity entity, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_followRange);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, getCraftEntity(entity), genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数nmsEntityLivingの敵視範囲を引数valueに変更する。
     * @param nmsEntityLiving 値を変更するNmsEntityLiving
     * @param value セットする値
     */
    public static void setAttributeFollowRange(Object nmsEntityLiving, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_followRange);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, nmsEntityLiving, genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数entityのノックバック耐性を引数valueに変更する。
     * @param entity 値を変更するLivingEntity
     * @param value セットする値
     */
    public static void setAttributeKnockbackResistance(LivingEntity entity, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_knockbackResistance);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, getCraftEntity(entity), genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数nmsEntityLivingのノックバック耐性を引数valueに変更する。
     * @param nmsEntityLiving 値を変更するNmsEntityLiving
     * @param value セットする値
     */
    public static void setAttributeKnockbackResistance(Object nmsEntityLiving, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_knockbackResistance);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, nmsEntityLiving, genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数entityの移動速度を引数valueに変更する。
     * @param entity 値を変更するLivingEntity
     * @param value セットする値
     */
    public static void setAttributeMovementSpeed(LivingEntity entity, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_movementSpeed);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, getCraftEntity(entity), genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数nmsEntityLivingの移動速度を引数valueに変更する。
     * @param nmsEntityLiving 値を変更するNmsEntityLiving
     * @param value セットする値
     */
    public static void setAttributeMovementSpeed(Object nmsEntityLiving, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_movementSpeed);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, nmsEntityLiving, genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数entityの攻撃力を引数valueに変更する。
     * @param entity 値を変更するLivingEntity
     * @param value セットする値
     */
    public static void setAttributeAttackDamage(LivingEntity entity, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_attackDamage);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, getCraftEntity(entity), genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数nmsEntityLivingの攻撃力を引数valueに変更する。
     * @param nmsEntityLiving 値を変更するNmsEntityLiving
     * @param value セットする値
     */
    public static void setAttributeAttackDamage(Object nmsEntityLiving, double value) {
        Object genericAttributes = ReflectionUtil.getStaticFieldValue(Fields.static_nmsGenericAttributes_attackDamage);
        Object attributeInstance = ReflectionUtil.invoke(Methods.nmsEntityLiving_getAttributeInstance, nmsEntityLiving, genericAttributes);

        ReflectionUtil.invoke(Methods.nmsAttributeInstance_setValue, attributeInstance, value);
    }

    /**
     * 引数locationを中心に半径radiusブロック以内の生物エンティティに引数damageの値だけダメージを付与する。
     * @param location 中心座標
     * @param radius 半径
     * @param damage ダメージ値
     */
    public static void addDamageNearbyEntities(Location location, double radius, double damage) {
        for (LivingEntity entity : getNearbyLivingEntities(location, radius)) {
            entity.damage(damage);
        }
    }

    /**
     * 引数locationを中心に半径radiusブロック以内の生物エンティティに引数executorを実行者としたダメージを引数damageの値だけ付与する。
     * @param executor 攻撃実行者
     * @param location 中心座標
     * @param radius 半径
     * @param damage ダメージ値
     */
    public static void addDamageNearbyEntities(Entity executor, Location location, double radius, double damage) {
        for (LivingEntity entity : getNearbyLivingEntities(location, radius)) {
            entity.damage(damage, executor);
        }
    }

    /**
     * 引数entityに格納されているMetaDataのうち引数pluginに関連するMetaDataを取得し、引数extractClassNameに一致するクラス名のMetaDataValueを抽出し返す。
     * @param entity 取得するエンティティ
     * @param plugin 取得するMetaDataに関連するJavaPluginを継承したインスタンス
     * @param extractClassName 抽出するMetaDataValueのクラス名と一致させるクラス名
     * @return 取得したObject。取得できなかった場合は{@code null}を返す。
     */
    public static Object getMetaDataValueByClassName(Entity entity, JavaPlugin plugin, String extractClassName) {
        // 引数pluginに関連する全MetaDataと照合
        for (MetadataValue metaData : entity.getMetadata(plugin.getName())) {
            Object metaDataValue = metaData.value();

            //metaDataValueが配列の場合
            if (metaDataValue.getClass().isArray()) {
                for (Object value : (Object[]) metaDataValue) {
                    if (value.getClass().getSimpleName().equalsIgnoreCase(extractClassName)) {
                        return value;
                    }
                }

            //MetaDataが単数の場合
            } else {
                if (metaDataValue.getClass().getSimpleName().equalsIgnoreCase(extractClassName)) {
                    return metaDataValue;
                }
            }
        }

        return null;
    }

    //〓 ItemStack 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数playerNameがオーナーのプレイヤースカルItemStackを返す。
     * @param playerName オーナーネーム
     * @return スカルアイテム
     */
    public static ItemStack createPlayerSkullByName(String playerName) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(playerName);
        item.setItemMeta(meta);

        return item;
    }

    /**
     * 引数materialがツール系アイテムかどうかを返す。
     * @param material チェックするマテリアル
     * @return ツール系アイテムかどうか
     */
    public static boolean isBreakableItem(Material material) {
        return material.getMaxDurability() != 0;
    }

    /**
     * 引数itemStackがツール系アイテムかどうかを返す。
     * @param material チェックするマテリアル
     * @return ツール系アイテムかどうか
     */
    public static boolean isBreakableItem(ItemStack itemStack) {
        return isBreakableItem(itemStack.getType());
    }

    /**
     * 引数itemと引数item2のマテリアルタイプ、メタデータ、ディスプレイネーム、ロアを比較し一致するかどうかを返す。<br>
     * ロアの比較は引数compareLoarLengthで指定された行数までを比較する。<br>
     * 数量、ダメージ値は考慮しない。
     * @param item 比較するアイテムスタック
     * @param item2 比較するアイテムスタック
     * @param compareLoarLength ロアを比較する行数
     * @return 一致したかどうか
     */
    public static boolean isSimilar(ItemStack item, ItemStack item2, int compareLoarLength) {
        if (item.getType().equals(item2.getType())) {
            byte data = item.getData().getData();
            byte data2 = item2.getData().getData();
            if (isBreakableItem(item) || data == data2) {
                ItemMeta meta = item.getItemMeta();
                ItemMeta meta2 = item2.getItemMeta();
                return compareItemDisplayName(meta, meta2) && compareItemLoar(meta, meta2, compareLoarLength);
            }
        }

        return false;
    }

    /**
     * itemListにcheckItemと一致するItemStackが含まれているかどうかを返す。
     * @param itemList チェックするアイテムリスト
     * @param checkItem チェックするアイテム
     * @return リストに含まれるかどうか
     */
    public static boolean containsSimilarItemStack(List<ItemStack> itemList, ItemStack checkItem) {
        for (ItemStack item : itemList) {
            if (isSimilar(checkItem, item, 1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * itemSetにcheckItemと一致するItemStackが含まれているかどうかを返す。
     * @param itemSet チェックするアイテムセット
     * @param checkItem チェックするアイテム
     * @return セットに含まれるかどうか
     */
    public static boolean containsSimilarItemStack(HashSet<ItemStack> itemSet, ItemStack checkItem) {
        for (ItemStack item : itemSet) {
            if (isSimilar(checkItem, item, 1)) {
                return true;
            }
        }

        return false;
    }

    /**
     * originalListからremoveListに含まれるアイテムを削除する。
     * @param originalList 操作するアイテムリスト
     * @param removeList 削除アイテムリスト
     */
    public static void removeSimilarItemStack(List<ItemStack> originalList, List<ItemStack> removeList) {
        Iterator<ItemStack> iterator = originalList.iterator();
        ItemStack original;
        while (iterator.hasNext()) {
            original = iterator.next();
            for (ItemStack remove : removeList) {
                if (isSimilar(original, remove, 1)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * originalListからcheckItemと一致するItemStackを削除する。
     * @param originalList 操作するアイテムリスト
     * @param removeItem 削除するアイテム
     */
    public static void removeSimilarItemStack(List<ItemStack> originalList, ItemStack removeItem) {
        Iterator<ItemStack> iterator = originalList.iterator();
        ItemStack original;
        while (iterator.hasNext()) {
            original = iterator.next();
            if (isSimilar(removeItem, original, 1)) {
                iterator.remove();
            }
        }
    }

    /**
     * originalSetからcheckItemと一致するItemStackを削除する。
     * @param originalSet 操作するアイテムセット
     * @param removeItem 削除するアイテム
     */
    public static void removeSimilarItemStack(HashSet<ItemStack> originalSet, ItemStack removeItem) {
        Iterator<ItemStack> iterator = originalSet.iterator();
        ItemStack original;
        while (iterator.hasNext()) {
            original = iterator.next();
            if (isSimilar(removeItem, original, 1)) {
                iterator.remove();
            }
        }
    }

    public static boolean compareItemDisplayName(ItemMeta meta, ItemMeta meta2) {
        // 双方ディスプレイネームを所有している場合
        if (meta.hasDisplayName() && meta2.hasDisplayName()) {
            return meta.getDisplayName().equalsIgnoreCase(meta2.getDisplayName());

        // 双方ディスプレイネームを所有していない場合
        } else if (meta.hasDisplayName() == meta2.hasDisplayName()) {
            return true;
        }

        return false;
    }

    public static boolean compareItemLoar(ItemMeta meta, ItemMeta meta2, int compareLength) {
        // 双方ロアを所有している場合
        if (meta.hasLore() && meta2.hasLore()) {
            List<String> loar = meta.getLore();
            List<String> loar2 = meta2.getLore();
            if (loar.size() == loar2.size()) {
                for (int i = 0; i < compareLength; i++) {
                    if (loar.get(i) == null) {
                        break;
                    }
                    if (!loar.get(i).equalsIgnoreCase(loar2.get(i))) {
                        return false;
                    }
                }

                return true;
            }

            return false;

        // 双方ロアを所有していない場合
        } else if (meta.hasLore() == meta2.hasLore()) {
            return true;
        }

        return false;
    }

    //〓 Inventory 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数playerのインベントリ内のアーマーコンテンツのアイテムを全て削除する。
     * @param player
     */
    public static void clearPlayerArmor(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.setArmorContents(new ItemStack[]{new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)});
    }

    /**
     * 引数playerのインベントリ内のコンテナのアイテムを全て削除する。
     * @param player
     */
    public static void clearPlayerContainer(Player player) {
        PlayerInventory inventory = player.getInventory();
        for (int i = 9; i < 36; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
    }

    /**
     * 引数playerのインベントリ内のワークスペースのアイテムを全て削除する。
     * @param player
     */
    public static void clearPlayerWorkSpace(Player player) {
        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, new ItemStack(Material.AIR));
        }
    }

    /**
     * 引数playerが手に所持しているアイテムの数量を引数decreaseAmountの数だけ減らしtrueを返す。<br>
     * 手にアイテムを所持していない場合、引数decreaseAmountに0以下の数値を指定した場合は何もせずfalseを返す。
     * @param player アイテムを減らすプレイヤー
     * @param decreaseAmount 減らす数量
     * @return アイテムを減らしたかどうか
     */
    public static boolean decreaseItemInHand(Player player, int decreaseAmount) {
        ItemStack handItem = player.getItemInHand();
        if (handItem == null || decreaseAmount <= 0) {
            return false;
        }

        int itemAmount = handItem.getAmount();

        // 減らした結果アイテムが残らない場合
        if (itemAmount - decreaseAmount <= 0) {
            player.setItemInHand(new ItemStack(Material.AIR));

        // 減らした結果アイテムが残る場合
        } else {
            player.getItemInHand().setAmount(itemAmount - decreaseAmount);
        }

        player.updateInventory();

        return true;
    }

    /**
     * 引数playerのインベントリから引数itemStackに一致するアイテムの数量を1つ減らしtrueを返す。<br>
     * 一致するアイテムがない場合、引数decreaseAmountに0以下の数値を指定した場合は何もせずfalseを返す。
     * @param player アイテムを減らすプレイヤー
     * @param itemStack 減らすアイテム
     * @param decreaseAmount 減らす数量
     * @return アイテムを減らしたかどうか
     */
    public static Boolean decreaseItemInInventory(Player player, ItemStack itemStack, int decreaseAmount) {
        if (decreaseAmount <= 0) {
            return false;
        }

        ItemStack[] inventory = player.getInventory().getContents();
        for(int slot = 0; slot < inventory.length; slot++){
            if(inventory[slot] != null){
                if(inventory[slot].isSimilar(itemStack)){

                    int itemAmount = inventory[slot].getAmount();

                    // 減らした結果アイテムが残らない場合
                    if (itemAmount - decreaseAmount <= 0) {
                        player.getInventory().setItem(slot, new ItemStack(Material.AIR));

                    // 減らした結果アイテムが残る場合
                    } else {
                        inventory[slot].setAmount(itemAmount - decreaseAmount);
                    }

                    player.updateInventory();

                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 引数playerのインベントリから引数inventorySlotに一致するスロットのアイテムの数量を1つ減らしtrueを返す。<br>
     * 一致するアイテムがない場合、引数decreaseAmountに0以下の数値を指定した場合は何もせずfalseを返す。
     * @param player アイテムを減らすプレイヤー
     * @param inventorySlot 減らすインベントリのスロット番号
     * @param decreaseAmount 減らす数量
     * @return アイテムを減らしたかどうか
     */
    public static Boolean decreaseItemInInventory(Player player, int inventorySlot, int decreaseAmount) {
        if (decreaseAmount <= 0) {
            return false;
        }

        Inventory inventory = player.getInventory();
        ItemStack slotItem = inventory.getItem(inventorySlot);
        if(slotItem != null && !slotItem.getType().equals(Material.AIR)){
            int itemAmount = slotItem.getAmount();

            // 減らした結果アイテムが残らない場合
            if (itemAmount - decreaseAmount <= 0) {
                player.getInventory().setItem(inventorySlot, new ItemStack(Material.AIR));

            // 減らした結果アイテムが残る場合
            } else {
                slotItem.setAmount(itemAmount - decreaseAmount);
            }

            player.updateInventory();

            return true;
        }

        return false;
    }

     /**
     * 引数playerのインベントリに引数itemStackを配布する。<br>
     * 引数dropOverflowがtrueの場合は、インベントリから溢れたアイテムをプレイヤーの足元にスポーンさせる。<br>
     * アイテムを溢れさせず配布できた場合はtrueを返す。
     * @param player アイテムを配布するプレイヤー
     * @param itemStack 配布するアイテム
     * @param dropOverflow 溢れたアイテムをスポーンさせるかどうか
     * @return アイテムを溢れさせず配布できたかどうか
     */
    public static boolean addItemToPlayer(Player player, ItemStack itemStack, boolean dropOverflow) {
        HashMap<Integer, ItemStack> overflowMap = player.getInventory().addItem(itemStack);

        if (overflowMap.isEmpty()) {
            return true;
        }

        for (ItemStack overflow : overflowMap.values()) {
            player.getWorld().dropItem(player.getEyeLocation(), overflow);
        }

        player.updateInventory();

        return false;
    }

    /**
     * 引数itemStackの耐久値を最大値にセットする。
     * @param itemStack 耐久値をセットするItemStack
     */
    public static void repairItemStackDurability(ItemStack itemStack) {
        itemStack.setDurability((short) 0);
    }

    /**
     * 引数itemStackの耐久値を百分率に換算し返す。
     * @param itemStack 取得するItemStack
     * @return 百分率で表した耐久値
     */
    public static Integer convertDurabilityToPercent(ItemStack itemStack){
        Material itemMaterial = itemStack.getType();

        double maxDurability = itemMaterial.getMaxDurability();
        double currentDurability = itemStack.getDurability();

        return (int) ((currentDurability / maxDurability) * 100);
    }

    //〓 Chunk 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数locationから半径radiusブロック以内のチャンクを返す。
     * @param location 基点となる座標
     * @param radius 半径
     * @return 取得したチャンクのList
     */
    public static List<Chunk> getNearbyChunks(Location location, double radius) {
        List<Chunk> nearbyChunks = Lists.newArrayList();

        Location cornerChunkLocation = location.clone().add(radius/2, 0, radius/2);

        // 半径radiusを1チャンク(16ブロック)毎に区切った場合に何度for文を繰り返すかどうか
        double repeatTimes = (radius / 16) + 1;
        for (int x = 0; x < repeatTimes; x++) {
            for (int z = 0; z < repeatTimes; z++) {
                Chunk chunk = cornerChunkLocation.getWorld().getChunkAt(cornerChunkLocation.clone().add(-16 * x, 0, -16 * z));
                nearbyChunks.add(chunk);
            }
        }

        return nearbyChunks;
    }

    /**
     * 引数locationから半径radiusブロック以内のチャンクを返す。
     * @param location 基点となる座標
     * @param xRadius X方向の半径
     * @param zRadius Z方向の半径
     * @return 取得したチャンクのList
     */
    public static List<Chunk> getNearbyChunks(Location location, double xRadius, double zRadius) {
        List<Chunk> nearbyChunks = Lists.newArrayList();

        Location cornerChunkLocation = location.clone().add(xRadius/2, 0, zRadius/2);

        // 半径radiusを1チャンク(16ブロック)毎に区切った場合に何度for文を繰り返すかどうか
        double xRepeatTimes = (xRadius / 16) + 1;
        double zRepeatTimes = (zRadius / 16) + 1;
        for (int x = 0; x < xRepeatTimes; x++) {
            for (int z = 0; z < zRepeatTimes; z++) {
                Chunk chunk = cornerChunkLocation.getWorld().getChunkAt(cornerChunkLocation.clone().add(-16 * x, 0, -16 * z));
                nearbyChunks.add(chunk);
            }
        }

        return nearbyChunks;
    }

    //〓 Location 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * locationを基点にoffsetの数値だけ前後に移動した座標を返す
     * offsetが正の数値であれば前方、負の数値であれば後方へ移動する
     * 前後の方角はlocationの偏揺れ角yawから算出している
     * @param location 基点となる座標
     * @param offset オフセット
     * @return offsetの数値だけ前後に移動した座標
     */
    public static Location getForwardLocationFromYaw(Location location, double offset) {
        Vector direction = location.getDirection();
        double x = direction.getX();
        double z = direction.getZ();

        return new Location(location.getWorld(),
                location.getX() + x * offset,
                location.getY(),
                location.getZ() + z * offset,
                location.getYaw(), location.getPitch());
    }

    /**
     * locationを基点にoffsetの数値だけ左右に移動した座標を返す
     * offsetが正の数値であれば左方、負の数値であれば右方へ移動する
     * 左右の方角はlocationの偏揺れ角yawから算出している
     * @param location 基点となる座標
     * @param offset オフセット
     * @return offsetの数値だけ左右に移動した座標
     */
    public static Location getSideLocationFromYaw(Location location, double offset) {
        Location adjustlocation = adjustLocationToBlockCenter(location);
        float yaw = adjustlocation.getYaw();
        double x = Math.cos(Math.toRadians(yaw));
        double z = Math.sin(Math.toRadians(yaw));

        return new Location(adjustlocation.getWorld(),
                adjustlocation.getX() + x * offset,
                adjustlocation.getY(),
                adjustlocation.getZ() + z * offset,
                yaw, adjustlocation.getPitch());
    }

    public static Location getStandingLocationByBoudingBox(Entity entity) {
        Location location = entity.getLocation();
        Object boudingBox = ReflectionUtil.invoke(Methods.nmsEntity_getBoundingBox, entity);

        location.setX((Double) ReflectionUtil.getFieldValue(Fields.nmsEntity_locX, entity));
        location.setY((Double) ReflectionUtil.getFieldValue(Fields.nmsAxisAlignedBB_locYBottom, boudingBox));
        location.setZ((Double) ReflectionUtil.getFieldValue(Fields.nmsEntity_locZ, entity));

        return location;
    }

    /**
     * 引数locationに設置されたブロックの中心座標を返す。
     * @param location 基点となる座標
     * @return 引数locationに設置されたブロックの中心座標
     */
    public static Location adjustLocationToBlockCenter(Location location) {
        Location cloneLocation = location.clone();
        double x = cloneLocation.getBlockX() + 0.5D;
        double z = cloneLocation.getBlockZ() + 0.5D;

        return new Location(cloneLocation.getWorld(), x, cloneLocation.getY(), z, cloneLocation.getYaw(), cloneLocation.getPitch());
    }

    /**
     * 引数fromから引数targetの座標間に固形ブロックが存在しておらず、引数targetの座標を視認できるかどうかを返す。
     * @param from チェックする座標
     * @param target チェックする座標
     * @return 引数fromから引数targetの座標を視認できるかどうか
     */
    public static boolean canSeeLocation(Location from, Location target) {
        if (!from.getWorld().equals(target.getWorld())) {
            return false;
        }

        Object nmsWorld = ReflectionUtil.invoke(Methods.craftWorld_getHandle, from.getWorld());
        Object fromVec3D = ReflectionUtil.newInstance(Constructors.nmsVec3D, from.getX(), from.getY(), from.getZ());
        Object targetVec3D = ReflectionUtil.newInstance(Constructors.nmsVec3D, target.getX(), target.getY(), target.getZ());

        return ReflectionUtil.invoke(Methods.nmsWorld_rayTrace, nmsWorld, fromVec3D, targetVec3D) == null;
    }

    //〓 Block 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数locationから半径radiusブロック、高さheightブロック以内のブロックを立方体で取得しListで返す。<br>
     * 引数ignoreAirにtrueが渡された場合は空気ブロックを除外する。
     * @param location 基点となる座標
     * @param radius 半径
     * @param height 高さ
     * @param ignoreAir 空気ブロックを除外するかどうか
     * @param ignoreUnsolidBlock 固形ではないブロックを除外するかどうか
     * @return ブロックList
     */
    public static List<Block> getSquareBlocks(Location location, int radius, int height, boolean ignoreAir, boolean ignoreUnsolidBlock){
        List<Block> blocks = new ArrayList<Block>();

        World world = location.getWorld();
        int locationX = location.getBlockX();
        int locationY = location.getBlockY();
        int locationZ = location.getBlockZ();

        for(int currentY = locationY; currentY < locationY + height; currentY++){
            for (int currentX = locationX - radius; currentX <= locationX + radius; currentX++) {
                for (int currentZ = locationZ - radius; currentZ <= locationZ + radius; currentZ++) {
                    Block tempBlock = new Location(world, currentX + 0.5, currentY, currentZ + 0.5).getBlock();
                    if (ignoreAir) {
                        if (tempBlock.getType().equals(Material.AIR)) {
                            continue;
                        }
                    }
                    if (ignoreUnsolidBlock) {
                        if (!isSolidBlock(tempBlock)) {
                            continue;
                        }
                    }

                    blocks.add(tempBlock);
                }
            }
        }

        return blocks;
    }

    /**
     * 引数locationから半径radiusブロック以内のブロックを球体で取得しListで返す。<br>
     * 引数hollowRadiusが1以上の場合は、中心から半径hollowRadiusブロック以内のブロックを除外する。<br>
     * 引数ignoreAirにtrueが渡された場合は、空気ブロックを除外する。
     * @param location 基点となる座標
     * @param radius 半径
     * @param hollowRadius 空洞にする半径
     * @param ignoreAir 空気ブロックを含めるかどうか
     * @param ignoreUnsolidBlock 固形ではないブロックを除外するかどうか
     * @return ブロックList
     */
    public static List<Block> getSphereBlocks(Location location, int radius, int hollowRadius, boolean ignoreAir, boolean ignoreUnsolidBlock) {
        List<Block> blocks = new ArrayList<Block>();

        World world = location.getWorld();
        int locationX = location.getBlockX();
        int locationY = location.getBlockY();
        int locationZ = location.getBlockZ();

        for (int currentX = locationX - radius; currentX <= locationX + radius; currentX++){
            for (int currentZ = locationZ - radius; currentZ <= locationZ + radius; currentZ++){
                for (int currentY = locationY - radius; currentY < locationY + radius; currentY++){
                    double dist = (locationX - currentX) * (locationX - currentX) + (locationZ - currentZ) * (locationZ - currentZ) + (locationY - currentY) * (locationY - currentY);
                    if (dist < radius * radius){
                        if (0 < hollowRadius && hollowRadius < radius) {
                            if (dist < (hollowRadius) * (hollowRadius)) {
                                continue;
                            }
                        }

                        Block tempBlock = new Location(world, currentX, currentY, currentZ).getBlock();
                        if (ignoreAir) {
                            if (tempBlock.getType().equals(Material.AIR)) {
                                continue;
                            }
                        }
                        if (ignoreUnsolidBlock) {
                            if (!isSolidBlock(tempBlock)) {
                                continue;
                            }
                        }

                        blocks.add(tempBlock);
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * 引数locationから半径radiusブロック以内のブロックを円柱で取得しListで返す。<br>
     * 引数hollowRadiusが1以上の場合は、中心から半径hollowRadiusブロック以内のブロックを除外する。<br>
     * 引数ignoreAirにtrueが渡された場合は、空気ブロックを除外する。
     * @param location 基点となる座標
     * @param radius 半径
     * @param height 高さ
     * @param hollowRadius 空洞にする半径
     * @param ignoreAir 空気ブロックを含めるかどうか
     * @param ignoreUnsolidBlock 固形ではないブロックを除外するかどうか
     * @return ブロックList
     */
    public static List<Block> getCylinderBlocks(Location location, int radius, int hollowRadius, int height, boolean ignoreAir, boolean ignoreUnsolidBlock) {
        List<Block> blocks = new ArrayList<Block>();

        World world = location.getWorld();
        int locationX = location.getBlockX();
        int locationY = location.getBlockY();
        int locationZ = location.getBlockZ();

        for (int currentX = locationX - radius; currentX <= locationX + radius; currentX++){
            for (int currentZ = locationZ - radius; currentZ <= locationZ + radius; currentZ++){
                for (int currentY = locationY; currentY < locationY + height; currentY++){
                    double dist = (locationX - currentX) * (locationX - currentX) + (locationZ - currentZ) * (locationZ - currentZ);

                    if (dist < radius * radius){
                        if (0 < hollowRadius && hollowRadius < radius) {
                            if (dist < (hollowRadius) * (hollowRadius)) {
                                continue;
                            }
                        }
                        Block tempBlock = new Location(world, currentX, currentY, currentZ).getBlock();
                        if (ignoreAir) {
                            if (tempBlock.getType().equals(Material.AIR)) {
                                continue;
                            }
                        }
                        if (ignoreUnsolidBlock) {
                            if (!isSolidBlock(tempBlock)) {
                                continue;
                            }
                        }

                        blocks.add(tempBlock);
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * 引数locationを基点に、引数heightの数値だけ下方の最も近い固形Blockを返す。
     * @param location 基点となる座標
     * @param height 高さ
     * @return 直下の固形ブロック。固形ブロックが存在しない場合は{@code null}を返す。
     */
    public static Block getGroundBlock(Location location, int height) {
        Location cloneLocation = location.clone();
        for (int i = 0; i <= height; i++) {
            if (isSolidBlock(cloneLocation)) {
                return cloneLocation.getBlock();
            }
            cloneLocation.add(0, -1, 0);
        }
        return null;
    }

    /**
     * locationを基点に、引数heightの数値だけ下方の最も近い固形BlockのIDを返す。
     * @param location 基点となる座標
     * @param height 高さ
     * @return BlockのID、データのString
     */
    public static String getGroundBlockID(Location location, int height) {
        Block block = getGroundBlock(location, height);
        if (block == null) {
            return "0:0";
        }

        return String.valueOf(block.getTypeId()) + ":" + String.valueOf(block.getData());
    }

    /**
     * locationを基点に、引数heightの数値だけ下方の最も近い固形BlockのMaterialを返す
     * @param location 基点となる座標
     * @param height 高さ
     * @return BlockのMaterial
     */
    public static Material getGroundBlockMaterial(Location location, int height) {
        Block block = getGroundBlock(location, height);
        if (block == null) {
            return Material.AIR;
        }

        return block.getType();
    }

    /**
     * 引数locationのブロックがよじ登ることができるブロックかどうかを判別する
     * @param location 座標
     * @return よじ登ることができるブロックかどうか
     */
    public static boolean isClimbableBlock(Location location) {
        Material blockMaterial = location.getBlock().getType();
        return (blockMaterial == Material.LADDER || blockMaterial == Material.VINE);
    }

    /**
     * 引数blockが下半分に設置された半ブロックかどうかを返す。<br>
     * 上半分に設置された半ブロックの場合、もしくは半ブロックではない場合はfalseを返す。
     * @param block チェックするブロック
     * @return 引数blockが下半分に設置された半ブロックかどうか
     */
    public static Boolean isBottomSlabBlock(Block block) {
        if (!isSlabBlock(block.getLocation())) {
            return false;
        }

        if (8 <= block.getData()) {
            return false;
        }

        return true;
    }

    /**
     * locationのBlockが固形Blockか判別する
     * @param location 判別するBlockの座標
     * @return 固形Blockかどうか
     */
    public static Boolean isSolidBlock(Location location) {
        Object nmsBlock = ReflectionUtil.invoke(Methods.static_nmsBlock_getById, null, location.getBlock().getTypeId());
        Object nmsMaterial = ReflectionUtil.invoke(Methods.nmsBlock_getMaterial, nmsBlock);
        return (Boolean) ReflectionUtil.invoke(Methods.nmsMaterial_isSolid, nmsMaterial);
    }

    /**
     * blockが固形Blockか判別する
     * @param location 判別するBlock
     * @return 固形Blockかどうか
     */
    public static Boolean isSolidBlock(Block block) {
        Object nmsBlock = ReflectionUtil.invoke(Methods.static_nmsBlock_getById, null, block.getTypeId());
        Object nmsMaterial = ReflectionUtil.invoke(Methods.nmsBlock_getMaterial, nmsBlock);
        return (Boolean) ReflectionUtil.invoke(Methods.nmsMaterial_isSolid, nmsMaterial);
    }

    /**
     * 引数locationに設置されているブロックが半ブロックかどうかを返す。
     * @param location ブロックをチェックする座標
     * @return 半ブロックかどうか
     */
    public static Boolean isSlabBlock(Location location) {
        int id = location.getBlock().getTypeId();
        if (id == 44 || id == 126 || id == 182)
            return true;
        return false;
    }

    //〓 Vector 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数fromが現在向いているYaw方向へのベクターを返す。
     * @param from 取得する座標
     * @return 引数fromが現在向いているYaw方向へのベクター
     */
    public static Vector getVectorByYaw(Location from){
        float yaw = from.getYaw();
        double x = -Math.sin(Math.toRadians(yaw < 0 ? yaw + 360 : yaw));
        double z = Math.cos(Math.toRadians(yaw < 0 ? yaw + 360 : yaw));

        Location to = new Location(from.getWorld(), from.getX()+x, from.getY(), from.getZ()+z);
        return getVectorToLocation(to, from);
    }

    /**
     * 引数fromの現在向いている方向へのベクターを返す。
     * @param from 取得する座標
     * @return 引数fromが現在向いている方向へのベクター
     */
    public static Vector getVectorByYawPitch(Location from) {
        Location to = from.clone().add(from.getDirection().normalize());
        return getVectorToLocation(from, to);
    }

    /**
     * Location fromからtoへのVectorを返す
     * @param from
     * @param to
     * @return Location fromからtoへのVector
     */
    public static Vector getVectorToLocation(Location from, Location to) {
        Vector vector = to.toVector().subtract(from.toVector());
        return vector.normalize();
    }

    /**
     * vectorから偏揺れ角Yawを算出し返す
     * 偏揺れ角はLocation型変数に用いられる水平方向の向きを表す方位角
     * @see org.bukkit.Location
     * @param vector ベクター
     * @return 偏揺れ角
     */
    public static float getYawFromVector(Vector vector) {
        double dx = vector.getX();
        double dz = vector.getZ();
        double yaw = 0;

        if (dx != 0) {
            if (dx < 0) {
                yaw = 1.5 * Math.PI;
            } else {
                yaw = 0.5 * Math.PI;
            }
            yaw -= Math.atan(dz / dx);
        } else if (dz < 0) {
            yaw = Math.PI;
        }

        return (float) (-yaw * 180 / Math.PI - 90);
    }

    /**
     * 引数eyeLocationからの視界に引数targetの座標が含まれているかどうかを返す。
     * @param eyeLocation チェックする視点の座標
     * @param target 視界に入っているかチェックする座標
     * @param threshold 視野の広さ。0.0F～360.0Fの数値を指定する
     * @return 引数fromからの視界に引数toの座標が含まれているかどうか
     */
    public static boolean isLocationInSight(Location eyeLocation, Location target, float threshold) {
        // 360.0F以上を指定された場合は無条件でtrueを返す
        if (360.0F <= threshold) {
            return true;
        }

        // 座標の取得。playerがカートに搭乗している場合はカートの座標を格納。
        float eyeYaw = eyeLocation.getYaw();

        // マイナスの値になる場合があるため正の数に変換
        if (eyeYaw < 0) {
            eyeYaw += 360.0F;
        }

        // vectorYawと同じ逆時計周りに変更
        eyeYaw -= 360.0F;
        eyeYaw = Math.abs(eyeYaw);

        float vectorYaw = getYawFromVector(getVectorToLocation(eyeLocation, target));
        vectorYaw = Math.abs(vectorYaw) - 90.0F;

        return isInSight(eyeYaw, vectorYaw, threshold);
    }

    /**
     * 引数entityからの視界に引数targetの座標が含まれているかどうかを返す。
     * @param entity チェックするエンティティ
     * @param target 視界に入っているかチェックする座標
     * @param threshold 視野の広さ。0.0F～360.0Fの数値を指定する
     * @return 引数fromからの視界に引数toの座標が含まれているかどうか
     */
    public static boolean isLocationInSight(Entity entity, Location target, float threshold) {
        // 座標の取得。entityが乗り物に搭乗している場合は乗り物の座標を格納。
        Location eyeLocation = entity.getVehicle() == null ? entity.getLocation().clone() : entity.getVehicle().getLocation().clone();

        return isLocationInSight(eyeLocation, target, threshold);
    }

    private static boolean isInSight(float baseYaw, float targetYaw, float threshold) {
        // 360.0F以上を指定された場合は無条件でtrueを返す
        if (360.0F <= threshold) {
            return true;
        }

        float positiveThreshold = baseYaw + (threshold) / 2.0F;

        // 基準点に閾値を加算した結果が360未満の場合
        if (positiveThreshold <= 360) {
            if (baseYaw <= targetYaw && targetYaw <= positiveThreshold) {
                return true;
            }

        // 基準点に閾値を加算した結果が360を超えた場合
        } else {
            if (baseYaw <= targetYaw && targetYaw <= 360.0F) {
                return true;
            } else{
                positiveThreshold -= 360.0F;
                if (0.0F <= targetYaw && targetYaw <= positiveThreshold) {
                    return true;
                }
            }
        }

        float negativeThreshold = baseYaw - (threshold) / 2.0F;
        // 基準点に閾値を減算した結果が0以上の場合
        if (0 <= negativeThreshold) {
            if (negativeThreshold <= targetYaw && targetYaw <= baseYaw) {
                return true;
            }

        // 基準点に閾値を減算した結果が0未満の場合
        } else {
            if (0.0F <= targetYaw && targetYaw <= baseYaw) {
                return true;
            } else{
                negativeThreshold += 360.0F;
                if (negativeThreshold <= targetYaw && targetYaw <= 360.0F) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 引数yawを-180.0F～180.0Fの数値に換算した場合に、負の数値に換算されるかどうかを返す。
     * @param yaw チェックするYaw
     * @return 引数yawが負の数値に換算されるかどうか
     */
    private static boolean isNegativeRoundDegree(float yaw) {
        if ((0.0F <= yaw && yaw <= 180.0F) || yaw < -180.0F) {
            return false;
        }

        return true;
    }

    /**
     * 引数yawが180.0Fを超えていた場合、0～-180.0Fの間の数値に変換し返す。<br>
     * 180.0Fを超えていない場合は引数yawをそのまま返す。
     * @param yaw 変換するYaw
     * @return 変換したYaw
     */
    private static float calcYawToRoundDegree(float yaw) {
        if (180.0F <= yaw) {
            return (180.0F - (yaw - 180.0F)) * -1;
        } else if (yaw <= -180.0F) {
            return 180.0F + (yaw + 180.0F);
        }

        return yaw;
    }

    /**
     * 引数entityがNmsEntityCreatureを継承しているインスタンスの場合に限り、引数targetLocationの座標に対し歩行させる。<br>
     * ただし、一定距離以上離れている座標を指定した場合NmsPathEntityは機能しないため、
     * 引数entityと引数targetLocationの距離が10ブロック以上離れている場合は、
     * 引数entityの座標から引数targetLocationの方向へ10ブロック進んだ先の座標をNmsPathEntityとして適用する。
     * @param entity 歩行させるエンティティ
     * @param targetLocation 目標座標
     */
    public static void walkEntityToLocation(LivingEntity entity, Location targetLocation) {
        Object nmsEntity = getCraftEntity(entity);

        // 引数entityがNmsEntityCreatureを継承指定ない場合return
        if (!ReflectionUtil.instanceOf(nmsEntity, Classes.nmsEntityCreature)) {
            return;
        }

        Object nmsNavigation = ReflectionUtil.invoke(Methods.nmsEntityInsentient_getNavigation, nmsEntity);
        if (nmsNavigation == null) {
            return;
        }

        Object nmsPath;
        Location entityLocation = entity.getLocation().clone();

        // 距離が10ブロック以内の場合
        if (entityLocation.distanceSquared(targetLocation) < 100) {
            nmsPath = ReflectionUtil.invoke(Methods.nmsNavigationAbstract_createPathEntity, nmsNavigation, targetLocation.getX(), targetLocation.getY(), targetLocation.getZ());

        // 距離が10ブロック以上の場合
        } else {
            // 現在の座標から目標座標方向へのベクターを10ブロック分の長さまで拡張したもの
            Vector vector = getVectorToLocation(entityLocation, targetLocation).normalize().multiply(10.0D);
            Location newTargetLocation = entityLocation.clone().add(vector.getX(), vector.getY(), vector.getZ());

            nmsPath = ReflectionUtil.invoke(Methods.nmsNavigationAbstract_createPathEntity, nmsNavigation, newTargetLocation.getX(), newTargetLocation.getY(), newTargetLocation.getZ());
        }

        if (nmsPath != null) {
            ReflectionUtil.invoke(Methods.nmsNavigationAbstract_applyPathEntity, nmsNavigation, nmsPath, 1.0D);
        }
    }

    //〓 Nms Object 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static Object getBlockPosition(Location location) {
        double locX = Math.floor(location.getX());
        double locY = Math.floor(location.getY());
        double locZ = Math.floor(location.getZ());

        return ReflectionUtil.newInstance(Constructors.nmsBlockPosition, locX, locY, locZ);
    }

    public static float getBlockStrength(Location blockLocation) {
        Object nmsWorld = ReflectionUtil.invoke(Methods.craftWorld_getHandle, blockLocation.getWorld());
        Object blockPosition = getBlockPosition(blockLocation);
        Object iBlockData = ReflectionUtil.invoke(Methods.nmsWorld_getBlockData, nmsWorld, blockPosition);
        Object nmsBlock = ReflectionUtil.invoke(Methods.nmsIBlockData_getBlock, iBlockData);
        return (Float) ReflectionUtil.getFieldValue(Fields.nmsBlock_strength, nmsBlock);
    }

    public static float getBlockStrength(Block block) {
        return getBlockStrength(block.getLocation());
    }

    public static Object getCraftEntity(Entity entity) {
        String className = entity.getClass().getSimpleName();
        Method getHandle = Methods.craftEntity_getHandle.get(className);

        if (getHandle == null) {
            getHandle = ReflectionUtil.getMethod(entity.getClass(), "getHandle");
            Methods.craftEntity_getHandle.put(className, getHandle);
        }

        return ReflectionUtil.invoke(getHandle, entity);
    }

    public static Object getNewCraftEntityFromClass(World world, Class<?> nmsEntityClass) {
        Constructor<?> constructor = Constructors.nmsEntity_Constructor.get(nmsEntityClass.getSimpleName());

        if (constructor == null) {
            constructor = ReflectionUtil.getConstructor(nmsEntityClass, Classes.nmsWorld);
            Constructors.nmsEntity_Constructor.put(nmsEntityClass.getSimpleName(), constructor);
        }

        return ReflectionUtil.newInstance(constructor, ReflectionUtil.invoke(Methods.craftWorld_getHandle, world));
    }

    public static Object getNewCraftEntityFromClassName(World world, String classname) {
        Constructor<?> constructor = Constructors.nmsEntity_Constructor.get(classname);

        if (constructor == null) {
            constructor = ReflectionUtil.getConstructor(ReflectionUtil.getNMSClass(classname), Classes.nmsWorld);
            Constructors.nmsEntity_Constructor.put(classname, constructor);
        }

        return ReflectionUtil.newInstance(constructor, ReflectionUtil.invoke(Methods.craftWorld_getHandle, world));
    }

    //〓 Java Class Format 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数valueが数値、booleanに一致しない文字列かどうかを返す。
     * @param value チェックする文字列
     * @return 引数valueが数値、booleanに一致しない文字列かどうかを返す。
     */
    public static boolean isString(String value) {
        return !isInteger(value) && !isFloat(value) && !isBoolean(value);
    }

    /**
     * 引数valueがIntegerに変換できるかどうかを返す
     * @param value 調べるString
     * @return 引数valueがIntegerに変換できるかどうか
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 引数valueがFloatに変換できるかどうかを返す。
     * @param value チェックする文字列
     * @return 引数valueがFloatに変換できるかどうか
     */
    public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 引数valueがBooleanに変換できるかどうかを返す。
     * @return 引数valueがBooleanに変換できるかどうか
     */
    public static boolean isBoolean(String value) {
        if (value.equals("true") || value.equals("false")) {
            return true;
        } else {
            return false;
        }
    }

    //〓 Math 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static int limit(int value, int min, int max) {
        if (value < min) {
            value = min;
        } else if (max < value) {
            value = max;
        }

        return value;
    }

    public static float limit(float value, float min, float max) {
        if (value < min) {
            value = min;
        } else if (max < value) {
            value = max;
        }

        return value;
    }

    public static double limit(double value, double min, double max) {
        if (value < min) {
            value = min;
        } else if (max < value) {
            value = max;
        }

        return value;
    }

    /**
     * 引数denominatorを上限とした自然数の乱数を生成し、生成した乱数より引数numeratorが高い場合trueを返す。
     * @param denominator 分母
     * @param numerator 分子
     * @return 引数numeratorを引数denominatorで割った確率のBoolean値
     */
    public static boolean roulette(int denominator, int numerator){
        int ram = new Random().nextInt(denominator) + 1;

        if(ram <= numerator) {
            return true;
        }

        return false;
    }

    /**
     * value以下の乱数を生成する
     * 生成した乱数は50％の確率で負の値に変換される
     * @param value 乱数の上限・下限数値
     * @return 生成した乱数
     */
    public static int getRandom(int value) {
        int newvalue;
        Random random = new Random();
        newvalue = random.nextBoolean() ? random.nextInt(value) : -random.nextInt(value);
        return newvalue;
    }

    /**
     * 引数valueと引数value2の符号を考慮した差を返す。<br>
     * お互いの符号が一致する場合は、双方の絶対値の差を返す。<br>
     * 符号が一致しない場合は、双方の絶対値の和を返す。
     * @param value 差を求める数値
     * @param value2 差を求める数値
     * @return 差
     */
    public static double getDoubleDifference(double value, double value2) {
        double absValue = Math.abs(value);
        double absValue2 = Math.abs(value2);

        // お互いの符号が一致している場合
        if ((Math.signum(value) == 1.0D && Math.signum(value2) == 1.0D)
                || (Math.signum(value) == -1.0D && Math.signum(value2) == -1.0D)) {

            return Math.abs(absValue - absValue2);

        // お互いの符号が一致していない場合
        } else {
            return absValue + absValue2;
        }
    }

    //〓 String 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数textの文字列から整数値を抽出し返す。<br>
     * 抽出できなかった場合は0を返す。
     * @param text 抽出する文字列
     * @return 抽出した整数値
     */
    public static int extractIntegerFromString(String text) {
        text = ChatColor.stripColor(text);
        text = text.replaceAll("[^0-9]", "");
        try {
            return Integer.valueOf(text);
        } catch(NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * textに含まれるChatColorを返す
     * 複数のChatColorが含まれていた場合文字列の最後に適応されているChatColorを返す
     * ChatColorが含まれていない場合はChatColor.WHITEを返す
     * @param text ChatColorを抽出する文字列
     * @return 抽出されたChatColor
     */
    public static ChatColor getChatColorFromText(String text) {
        String color = ChatColor.getLastColors(text);
        if (color == null)
            return ChatColor.WHITE;
        if (color.length() == 0)
            return ChatColor.WHITE;

        return ChatColor.getByChar(color.substring(1));
    }

    public static String convertSignNumber(int number) {
        return 0 <= number ? "<gold>+" + String.valueOf(number) : "<blue>" + String.valueOf(number);
    }

    public static String convertSignNumber(float number) {
        return 0 <= number ? "<gold>+" + String.valueOf(number) : "<blue>" + String.valueOf(number);
    }

    public static String convertSignNumber(double number) {
        return 0 <= number ? "<gold>+" + String.valueOf(number) : "<blue>" + String.valueOf(number);
    }

    public static String convertSignNumberR(int number) {
        String text = "";
        if (number == 0)
            text = "<gold>+" + String.valueOf(number);
        else
            text = 0 < number ? "<blue>+" + String.valueOf(number) : "<gold>" + String.valueOf(number);
        return text;
    }

    public static String convertSignNumberR(float number) {
        String text = "";
        if (number == 0)
            text = "<gold>+" + String.valueOf(number);
        else
            text = 0 < number ? "<blue>+" + String.valueOf(number) : "<gold>" + String.valueOf(number);
        return text;
    }

    public static String convertSignNumberR(double number) {
        String text = "";
        if (number == 0)
            text = "<gold>+" + String.valueOf(number);
        else
            text = 0 < number ? "<blue>+" + String.valueOf(number) : "<gold>" + String.valueOf(number);
        return text;
    }

    public static String convertInitialUpperString(String string) {
        if (string == null) {
            return null;
        }
        if (string == "") {
            return "";
        }
        if (string.length() == 1) {
            return string.toUpperCase();
        }
        String initial = string.substring(0, 1).toUpperCase();
        String other = string.substring(1, string.length()).toLowerCase();

        return initial + other;
    }
}
