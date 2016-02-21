package com.github.erozabesu.yplutillibrary.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.ItemStack;

import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class Methods extends ReflectionUtil {

    //〓 Nms 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * AttributeInstanceを実装したクラスのフィジカル値をセットする。<br>
     * param Instance AttributeInstanceを実装したクラスのインスタンス<br>
     * param Double セットする値
     */
    public static Method nmsAttributeInstance_setValue = getMethod(Classes.nmsAttributeInstance, "setValue", double.class);

    /**
     * BoundingBoxの全面を外側へ拡張する<br>
     * param Instance 拡張するNmsAxisAlignedBB<br>
     * param Double X方向の面の拡張値<br>
     * param Double Y方向の面の拡張値<br>
     * param Double Z方向の面の拡張値
     */
    public static Method nmsAxisAlignedBB_grow = getMethod(Classes.nmsAxisAlignedBB, "grow", double.class, double.class, double.class);

    /**
     * NmsBlockからNmsMaterialを取得する<br>
     * param Instance 取得するNmsBlock<br>
     * return 取得したNmsMaterial
     */
    public static Method nmsBlock_getMaterial = getMethod(Classes.nmsBlock, "getMaterial");

    /**
     * NmsChatComponentTextから格納されている文字列を取得し返す。
     * param Instance NmsChatComonentText
     * return 取得した文字列
     */
    public static Method nmsChatComponentText_getText = getMethod(Classes.nmsChatComponentText, "getText");

    /**
     * NmsDamageSourceからダメージを発生させたNmsEntityを取得する<br>
     * param Instance 取得するNmsDamageSource<br>
     * return 取得したNmsEntity
     */
    public static Method nmsDamageSource_getEntity = getMethod(Classes.nmsDamageSource, "getEntity");

    /**
     * NmsMaterialが固形ブロックかどうかを取得する<br>
     * param Instance 取得するNmsMaterial<br>
     * return NmsMaterialが固形ブロックかどうか
     */
    public static Method nmsMaterial_isSolid = getMethod(Classes.nmsMaterial, "isSolid");

    /**
     * NmsEntityのNmsAxisAlignedBBを取得する<br>
     * param Instance 取得するNmsEntity<br>
     * return 取得したNmsAxisAlignedBB
     */
    public static Method nmsEntity_getBoundingBox = getMethod(Classes.nmsEntity, "getBoundingBox");

    /**
     * NmsEntityからBukkitEntityを取得する<br>
     * param Instance 取得するNmsEntity<br>
     * return 取得したBukkitEntity
     */
    public static Method nmsEntity_getBukkitEntity = getMethod(Classes.nmsEntity, "getBukkitEntity");

    /**
     * NmsEntityからカスタムネームを取得する<br>
     * param Instance 取得するNmsEntity<br>
     * return 取得したカスタムネーム
     */
    public static Method nmsEntity_getCustomName = getMethod(Classes.nmsEntity, "getCustomName");

    /**
     * NmsEntityからEntityIDを取得する<br>
     * param Instance 取得するNmsEntity<br>
     * return 取得したEntityID
     */
    public static Method nmsEntity_getId = getMethod(Classes.nmsEntity, "getId");

    /**
     * NmsEntityと引数NmsEntityの座標間の距離を2乗した値を返す。<br>
     * param Instance 取得するNmsEntity<br>
     * param NmsEntity 対象のNmsEntity<br>
     * return 座標間の距離を2乗した値
     */
    public static Method nmsEntity_getDistanceSquared = getMethod(Classes.nmsEntity, "h", Classes.nmsEntity);;

    /**
     * NmsEntityからNmsWorldを取得する<br>
     * param Instance 取得するNmsEntity<br>
     * return 取得したNmsWorld
     */
    public static Method nmsEntity_getWorld = getMethod(Classes.nmsEntity, "getWorld");

    /**
     * NmsEntityのEntityIDを引数Integerにセットする<br>
     * param Instance セットするNmsEntity<br>
     * param Integer セットするEntityID
     */
    public static Method nmsEntity_setEntityID = getMethod(Classes.nmsEntity, "d", int.class);

    /**
     * NmsEntityの現在座標をセットする<br>
     * param Instance セットするNmsEntity<br>
     * param Double セットするX座標<br>
     * param Double セットするY座標<br>
     * param Double セットするZ座標<br>
     * param Float セットするYaw<br>
     * param Float セットするPitch
     */
    public static Method nmsEntity_setLocation = getMethod(Classes.nmsEntity, "setLocation", double.class, double.class, double.class, float.class, float.class);

    /**
     * NmsEntityのカスタムネームをセットする<br>
     * param Instance セットするNmsEntity<br>
     * param String セットするカスタムネーム
     */
    public static Method nmsEntity_setCustomName = getMethod(Classes.nmsEntity, "setCustomName", String.class);

    /**
     * NmsEntityのカスタムネームを表示するかどうかをセットする<br>
     * param Instance セットするNmsEntity<br>
     * param Boolean カスタムネームを表示するかどうか
     */
    public static Method nmsEntity_setCustomNameVisible = getMethod(Classes.nmsEntity, "setCustomNameVisible", boolean.class);

    /**
     * NmsEntityのAxisAlignedBBを引数Float1*引数Float1*引数Float2の値にセットし、それに伴う座標の移動を行う<br>
     * param Instance セットするNmsEntity<br>
     * param Float セットする横幅<br>
     * param Float セットする高さ
     */
    public static Method nmsEntity_setSize;

    /**
     * NmsEntityのYaw、Pitchをセットする<br>
     * param Instance セットするNmsEntity<br>
     * param Float セットするYaw<br>
     * param Float セットするPitch
     */
    public static Method nmsEntity_setYawPitch = getMethod(Classes.nmsEntity, "setYawPitch", float.class, float.class);

    /**
     * メソッドの処理を見ても特に具体的なものは実行していない用途不明のメソッド<br>
     * param Instance 実行するNmsEntity
     */
    public static Method nmsEntity_checkBlockCollisions = getMethod(Classes.nmsEntity, "checkBlockCollisions");

    /**
     * NmsEntityの当たり判定に基づく衝突モーションを、自身と引数NmsEntityに対し適用する<br>
     * param Instance 実行するNmsEntity<br>
     * param NmsEntity 衝突対象のNmsEntity
     */
    public static Method nmsEntity_collide = getMethod(Classes.nmsEntity, "collide", Classes.nmsEntity);

    /**
     * NmsEntityのデッドフラグをtrueにセットする<br>
     * param Instance セットするNmsEntity
     */
    public static Method nmsEntity_die = getMethod(Classes.nmsEntity, "die");

    /**
     * NmsEntityに引数NmsEntityへの搭乗を試みさせる<br>
     * param Instance セットするNmsEntity<br>
     * param NmsEntity 搭乗対象のNmsEntity
     */
    public static Method nmsEntity_mount = getMethod(Classes.nmsEntity, "mount", Classes.nmsEntity);

    /**
     * NmsEntityの移動を試みる<br>
     * param Instance 移動させるNmsEntity<br>
     * param Double セットするXモーション<br>
     * param Double セットするYモーション<br>
     * param Double セットするZモーション
     */
    public static Method nmsEntity_move = getMethod(Classes.nmsEntity, "move", double.class, double.class, double.class);

    /**
     * NmsEntityのモーション値をセットする<br>
     * param Instance セットするNmsEntity<br>
     * param Double セットするXモーション<br>
     * param Double セットするYモーション<br>
     * param Double セットするZモーション
     */
    public static Method nmsEntity_moveAbsolute = getMethod(Classes.nmsEntity, "g", double.class, double.class, double.class);

    /**
     * NmsEntityにダメージを与える<br>
     * param Instance NmsEntity<br>
     * param NmsDamageSource ダメージソース<br>
     * param Float 与えるダメージ
     */
    public static Method nmsEntity_damageEntity = getMethod(Classes.nmsEntity, "damageEntity", Classes.nmsDamageSource, float.class);

    /**
     * NmsEntityInsentientのターゲットしているNmsEntityLivingを取得する<br>
     * param Instance 取得するNmsEntityInsentient<br>
     * return NmsEntityLiving
     */
    public static Method nmsEntityInsentient_getGoalTarget = getMethod(Classes.nmsEntityInsentient, "getGoalTarget");

    /**
     * NmsEntityInsentientのNmsNavigationAbstractインスタンスを取得する<br>
     * param Instance 取得するNmsEntityInsentient<br>
     * return NmsNavigationAbstractインスタンス
     */
    public static Method nmsEntityInsentient_getNavigation = getMethod(Classes.nmsEntityInsentient, "getNavigation");

    /**
     * NmsEntityInsentientの目標移動地点を引数NmsEntityLivingにセットする。<br>
     * param Instance セットするNmsEntityInsentient<br>
     * param NmsEntityLiving 目標移動地点のエンティティ<br>
     * param TargetRason ターゲットにした理由<br>
     * param Boolean
     */
    public static Method nmsEntityInsentient_setGoalTarget = getMethod(Classes.nmsEntityInsentient, "setGoalTarget", Classes.nmsEntityLiving, TargetReason.class, boolean.class);

    /**
     * NmsEntityInsentientに搭乗しているエンティティのモーション入力係数をNmsEntityInsentientのモーションにどの程度適用するかの倍率をセットする。<br>
     * param Instance セットするNmsEntityInsentient<br>
     * param Float 倍率
     */
    public static Method nmsEntityInsentient_setPassengerMotionInputMultiply;

    /**
     * NmsEntityLivingのフィジカル値を取得する<br>
     * param Instance 取得するNmsEntityLiving<br>
     * param NmsIAttribute IAttributeインターフェースを実装したインスタンス
     */
    public static Method nmsEntityLiving_getAttributeInstance = getMethod(Classes.nmsEntityLiving, "getAttributeInstance", Classes.nmsIAttribute);

    /**
     * NmsEntityLivingが生存しているかどうかを返す。<br>
     * param Instance 取得するNmsEntityLiving<br>
     * return Boolean 生存しているかどうか
     */
    public static Method nmsEntityLiving_isAlive = getMethod(Classes.nmsEntityLiving, "isAlive");

    /**
     * NmsEntityHumanの歩行速度のフィジカル値を取得する<br>
     * param Instance 取得するNmsEntityHuman
     */
    public static Method nmsEntityHuman_getAttributesMovementSpeed;

    /**
     * NmsEntityHumanの手に所持しているNmsItemStackを返す<br>
     * param Instance 取得するNmsEntityHuman<br>
     * return 手に所持しているNmsItemStack
     */
    public static Method nmsEntityHuman_getItemInHand;

    /**
     * NmsEntityArmorStandを透明化する。<br>
     * param Instance セットするNmsEntityArmorStand<br>
     * param boolean 透明化するかどうか
     */
    public static Method nmsEntityArmorStand_setInvisible = getMethod(Classes.nmsEntityArmorStand, "setInvisible", boolean.class);

    /**
     * NmsEntityArmorStandのベースプレートを表示する。<br>
     * param Instance セットするNmsEntityArmorStand<br>
     * param boolean 表示するかどうか
     */
    public static Method nmsEntityArmorStand_setBasePlate = getMethod(Classes.nmsEntityArmorStand, "setBasePlate", boolean.class);

    /**
     * NmsEntityArmorStandの重力を有効にする。<br>
     * param Instance セットするNmsEntityArmorStand<br>
     * param boolean 有効にするかどうか
     */
    public static Method nmsEntityArmorStand_setGravity = getMethod(Classes.nmsEntityArmorStand, "setGravity", boolean.class);

    /**
     * NmsEntityArmorStandの右腕のポーズをセットする<br>
     * param Instance セットするNmsEntityArmorStand<br>
     * param NmsVector3f セットするポーズ
     */
    public static Method nmsEntityArmorStand_setRightArmPose = getMethod(Classes.nmsEntityArmorStand, "setRightArmPose", Classes.nmsVector3f);

    /**
     * NmsIBlockDataからNmsBlockを取得し返す。<br>
     * param Instance NmsIBlockData<br>
     * return NmsBlock 取得したNmsBlock
     */
    public static Method nmsIBlockData_getBlock = getMethod(Classes.nmsIBlockData, "getBlock");

    /**
     * NmsItemの最大耐久値を引数Integerに変更する<br>
     * param Instance セットするNmsItem<br>
     * param Integer セットする最大耐久値
     */
    public static Method nmsItem_setMaxDurability = getMethod(Classes.nmsItem, "setMaxDurability", int.class);

    /**
     * NmsItemStackからNmsItemを取得し返す<br>
     * param Instance NmsItemStack<br>
     * return NmsItem
     */
    public static Method nmsItemStack_getItem = getMethod(Classes.nmsItemStack, "getItem");

    /**
     * NmsItemStackからNmsNBTTagCompoundを取得し返す<br>
     * param Instance NmsItemStack<br>
     * return NmsNBTTagCompound
     */
    public static Method nmsItemStack_getTag = getMethod(Classes.nmsItemStack, "getTag");

    /**
     * NmsItemStackに新たなNmsNBTTagCompoundをセットする。<br>
     * param Instance NmsItemStack<br>
     * param NBTTagCompound 新たにセットするNmsNBTTagCompound
     */
    public static Method nmsItemStack_setTag = getMethod(Classes.nmsItemStack, "setTag", Classes.nmsNBTTagCompound);

    /**
     * 指定した座標に対するNmsPathEntityインスタンスを返す。<br>
     * param Instance NmsNavigationAbstractインスタンス<br>
     * param Double X座標<br>
     * param Double Y座標<br>
     * param Double Z座標<br>
     * return NmsPathEntity
     */
    public static Method nmsNavigationAbstract_createPathEntity = getMethod(Classes.nmsNavigationAbstract, "a", double.class, double.class, double.class);

    /**
     * 指定した座標に対するNmsPathEntityインスタンスを適用する。<br>
     * param Instance NmsNavigationAbstractインスタンス<br>
     * param Double 移動速度?
     */
    public static Method nmsNavigationAbstract_applyPathEntity = getMethod(Classes.nmsNavigationAbstract, "a", Classes.nmsPathEntity, double.class);

    /**
     * NmsNBTTagCompoundの引数StringがキーのNBTBaseが格納されているかどうかを返す。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー
     * return boolean
     */
    public static Method nmsNBTTagCompound_hasKey = getMethod(Classes.nmsNBTTagCompound, "hasKey", String.class);

    /**
     * NmsNBTTagCompoundの引数StringがキーのNBTBaseを取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー
     * return NmsNBTBase
     */
    public static Method nmsNBTTagCompound_get = getMethod(Classes.nmsNBTTagCompound, "get", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をbyte型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagByte
     */
    public static Method nmsNBTTagCompound_getByte = getMethod(Classes.nmsNBTTagCompound, "getByte", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をshort型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagShort
     */
    public static Method nmsNBTTagCompound_getShort = getMethod(Classes.nmsNBTTagCompound, "getShort", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をint型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagInt
     */
    public static Method nmsNBTTagCompound_getInt = getMethod(Classes.nmsNBTTagCompound, "getInt", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をlong型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagLong
     */
    public static Method nmsNBTTagCompound_getLong = getMethod(Classes.nmsNBTTagCompound, "getLong", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をfloat型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagFloat
     */
    public static Method nmsNBTTagCompound_getFloat = getMethod(Classes.nmsNBTTagCompound, "getFloat", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をdouble型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagDouble
     */
    public static Method nmsNBTTagCompound_getDouble = getMethod(Classes.nmsNBTTagCompound, "getDouble", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をbyte[]型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagByteArray
     */
    public static Method nmsNBTTagCompound_getByteArray = getMethod(Classes.nmsNBTTagCompound, "getByteArray", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をint[]型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagIntArray
     */
    public static Method nmsNBTTagCompound_getIntArray = getMethod(Classes.nmsNBTTagCompound, "getIntArray", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をString型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagString
     */
    public static Method nmsNBTTagCompound_getString = getMethod(Classes.nmsNBTTagCompound, "getString", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値をboolean型で取得する。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * return NmsNBTTagBoolean
     */
    public static Method nmsNBTTagCompound_getBoolean = getMethod(Classes.nmsNBTTagCompound, "getBoolean", String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数NmsNBTBaseにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param NmsNBTBase 新たにセットする値
     */
    public static Method nmsNBTTagCompound_set = getMethod(Classes.nmsNBTTagCompound, "set", String.class, Classes.nmsNBTBase);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Byteにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Byte 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setByte = getMethod(Classes.nmsNBTTagCompound, "setByte", String.class, byte.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Shortにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Short 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setShort = getMethod(Classes.nmsNBTTagCompound, "setShort", String.class, short.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Integerにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Integer 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setInt = getMethod(Classes.nmsNBTTagCompound, "setInt", String.class, int.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Longにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Long 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setLong = getMethod(Classes.nmsNBTTagCompound, "setLong", String.class, long.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Floatにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Float 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setFloat = getMethod(Classes.nmsNBTTagCompound, "setFloat", String.class, float.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Doubleにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Double 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setDouble = getMethod(Classes.nmsNBTTagCompound, "setDouble", String.class, double.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Byte[]にセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Byte[] 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setByteArray = getMethod(Classes.nmsNBTTagCompound, "setByteArray", String.class, byte[].class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Integer[]にセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Integer[] 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setIntArray = getMethod(Classes.nmsNBTTagCompound, "setIntArray", String.class, int[].class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Stringにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param String 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setString = getMethod(Classes.nmsNBTTagCompound, "setString", String.class, String.class);

    /**
     * NmsNBTTagCompoundの引数Stringがキーの値を引数Booleanにセットする。<br>
     * param Instance NmsNBTTagCompound<br>
     * param String キー<br>
     * param Boolean 新たにセットする値
     */
    public static Method nmsNBTTagCompound_setBoolean = getMethod(Classes.nmsNBTTagCompound, "setBoolean", String.class, boolean.class);

    /**
     * NmsNBTTagListの配列にNmsNBTBaseを追加する。<br>
     * param Instance NmsNBTTagList<br>
     * param NmsNBTBase 追加する値
     */
    public static Method nmsNBTTagList_addNBTBase = getMethod(Classes.nmsNBTTagList, "add", Classes.nmsNBTBase);

    /**
     * param Instance NmsPathfinderGoalSelector<br>
     * param Integer インデックス<br>
     * param NmsPathfinderGoal
     */
    public static Method nmsPathfinderGoalSelector_addPathfinderGoal = getMethod(Classes.nmsPathfinderGoalSelector, "a", int.class, Classes.nmsPathfinderGoal);

    /**
     * NmsPlayerConnectionに対しリスポーンウィンドウのリスポーンボタン押下を強制するパケットを送信する<br>
     * param Instance 送信対象のプレイヤーのNmsPlayerConnection<br>
     * param NmsPacketPlayInClientCommand 送信するパケット
     */
    public static Method nmsPlayerConnection_skipRespawnWindow = getMethod(Classes.nmsPlayerConnection, "a", Classes.nmsPacketPlayInClientCommand);

    /**
     * NmsPlayerConnectionに対し引数NmsPacketのパケットを送信する<br>
     * param Instance 送信対象のプレイヤーのNmsPlayerConnection<br>
     * param NmsPacket 送信するパケット
     */
    public static Method nmsPlayerConnection_sendPacket = getMethod(Classes.nmsPlayerConnection, "sendPacket", Classes.nmsPacket);

    /**
     * NmsWatchableObjectのNmsDataWatcherにおける配列Indexを取得する<br>
     * param Instance 取得するNmsWatchableObject<br>
     * param NmsWatchableObjectのNmsDataWatcherにおける配列Index
     */
    public static Method nmsWatchableObject_getIndex = getMethod(Classes.nmsWatchableObject, "a");

    /**
     * 引数NmsBlockPositionの座標のNmsIBlockDataを返す<br>
     * param Instance 取得するNmsWorld<br>
     * param NmsBlockPosition 取得する座標<br>
     * return NmsIBlockData 取得したブロックのNmsIBlockData
     */
    public static Method nmsWorld_getBlockData = getMethod(Classes.nmsWorld, "getType", Classes.nmsBlockPosition);

    /**
     * 引数NmsEntityのBoundingBox内のNmsEntityを返す<br>
     * param Instance 取得するNmsWorld<br>
     * param NmsEntity 取得するNmsEntity<br>
     * param NmsAxisAlignedBB 引数NmsEntityのBoundingBox
     */
    public static Method nmsWorld_getEntities = getMethod(Classes.nmsWorld, "getEntities", Classes.nmsEntity, Classes.nmsAxisAlignedBB);

    /**
     * 引数IntegerのIDを持つNmsEntityを返す<br>
     * param Instance 取得するNmsWorld<br>
     * param EntityId<br>
     * return NmsEntity
     */
    public static Method nmsWorld_getNmsEntityById = getMethod(Classes.nmsWorld, "a", int.class);

    /**
     * NmsWorldからCraftWorldを取得し返す。<br>
     * param Instance 取得するNmsWorld<br>
     * return CraftWorld
     */
    public static Method nmsWorld_getWorld = getMethod(Classes.nmsWorld, "getWorld");

    /**
     * param NmsVec3D<br>
     * param NmsVec3D<br>
     * return NmsMovingObjectPosition
     */
    public static Method nmsWorld_rayTrace = getMethod(Classes.nmsWorld, "rayTrace", Classes.nmsVec3D, Classes.nmsVec3D);

    /**
     * 引数NmsEntityをスポーンさせる<br>
     * param Instance NmsEntityをスポーンさせるNmsWorld<br>
     * param NmsEntity スポーンさせるNmsEntity
     */
    public static Method nmsWorld_addEntity = getMethod(Classes.nmsWorld, "addEntity", Classes.nmsEntity);

    /**
     * param Instance NmsWorld<br>
     * param NmsEntity<br>
     * param Byte
     */
    public static Method nmsWorld_broadcastEntityEffect = getMethod(Classes.nmsWorld, "broadcastEntityEffect", Classes.nmsEntity, byte.class);

    /**
     * NmsEntityHumanが攻撃者として設定されたNmsDamageSourceを生成し返す<br>
     * param Instance {@code null}<br>
     * param NmsEntityHuman<br>
     * return NmsDamageSource
     */
    public static Method static_nmsDamageSource_playerAttack = getMethod(Classes.nmsDamageSource, "playerAttack", Classes.nmsEntityHuman);

    /**
     * NmsEntityLivingが攻撃者として設定されたNmsDamageSourceを生成し返す<br>
     * param Instance {@code null}<br>
     * param NmsEntityLiving<br>
     * return NmsDamageSource
     */
    public static Method static_nmsDamageSource_mobAttack = getMethod(Classes.nmsDamageSource, "mobAttack", Classes.nmsEntityLiving);

    /**
     * 引数Double1、引数Double2の絶対値を比較し、高い方の数値を返す<br>
     * param Instance {@code null}<br>
     * param Double<br>
     * param Double<br>
     * return 引数Double1、引数Double2の絶対値を比較し、高い方のDouble値
     */
    public static Method static_nmsMathHelper_a = getMethod(Classes.nmsMathHelper, "a", double.class, double.class);

    /**
     * 引数Double1、引数Double2、引数Double3の値を比較し、2番目に大きい数値を返す<br>
     * param Instance {@code null}<br>
     * param Double<br>
     * param Double<br>
     * param Double<br>
     * param 引数Double1、引数Double2、引数Double3の値を比較し、2番目に大きいDouble値
     */
    public static Method static_nmsMathHelper_a2 = getMethod(Classes.nmsMathHelper, "a", double.class, double.class, double.class);

    /**
     * 引数Doubleの小数点以下を切り捨てたIntegerを返す<br>
     * param Instance {@code null}<br>
     * param Double<br>
     * return 引数Doubleの小数点以下を切り捨てたInteger値
     */
    public static Method static_nmsMathHelper_floor = getMethod(Classes.nmsMathHelper, "floor", double.class);

    /**
     * 引数Floatのコサイン値を返す<br>
     * param Instance {@code null}<br>
     * param Float<br>
     * return 引数Floatのコサイン値
     */
    public static Method static_nmsMathHelper_cos = getMethod(Classes.nmsMathHelper, "cos", float.class);

    /**
     * 引数Floatのサイン値を返す<br>
     * param Instance {@code null}<br>
     * param Float<br>
     * return 引数Floatのサイン値
     */
    public static Method static_nmsMathHelper_sin = getMethod(Classes.nmsMathHelper, "sin", float.class);

    /**
     * Jsonフォーマットで記述されたStringからNmsNBTTagCompoundを生成し返す。<br>
     * param Instance {@code null}<br>
     * param String Json<br>
     * return NmsNBTTagCompound
     */
    public static Method static_nmsMojangsonParser_parse = getMethod(Classes.nmsMojangsonParser, "parse", String.class);

    /**
     * 引数Stringをタイトルパケットに利用するIChatBaseComponentに変換し返す<br>
     * param Instance {@code null}<br>
     * param String 変換する文字列<br>
     * return 取得したIChatBaseComponent
     */
    public static Method static_nmsChatSerializer_buildNmsIChatBaseComponent = getMethod(Classes.nmsChatSerializer, "a", String.class);

    /**
     * 引数IntegerをIDにもつNmsBlockを返す<br>
     * param Instance {@code null}<br>
     * param Integer ブロックID<br>
     * param 取得したNmsBlock
     */
    public static Method static_nmsBlock_getById = getMethod(Classes.nmsBlock, "getById", int.class);

    //XXX: CraftBukkit Unstable
    static {
        if (getBukkitVersion().equalsIgnoreCase("v1_8_R1")) {
            nmsEntity_setSize = getMethod(Classes.nmsEntity, "a", float.class, float.class);
            nmsEntityHuman_getAttributesMovementSpeed = getMethod(Classes.nmsEntityHuman, "bH");
            nmsEntityHuman_getItemInHand = getMethod(Classes.nmsEntityHuman, "bY");
            nmsEntityInsentient_setPassengerMotionInputMultiply = getMethod(Classes.nmsEntityInsentient, "j", float.class);
        } else if (getBukkitVersion().equalsIgnoreCase("v1_8_R2")) {
            nmsEntity_setSize = getMethod(Classes.nmsEntity, "setSize", float.class, float.class);
            nmsEntityHuman_getAttributesMovementSpeed = getMethod(Classes.nmsEntityHuman, "bI");
            nmsEntityHuman_getItemInHand = getMethod(Classes.nmsEntityHuman, "bZ");
            nmsEntityInsentient_setPassengerMotionInputMultiply = getMethod(Classes.nmsEntityInsentient, "k", float.class);
        } else if (getBukkitVersion().equalsIgnoreCase("v1_8_R3")) {
            nmsEntity_setSize = getMethod(Classes.nmsEntity, "setSize", float.class, float.class);
            nmsEntityHuman_getAttributesMovementSpeed = getMethod(Classes.nmsEntityHuman, "bI");
            nmsEntityHuman_getItemInHand = getMethod(Classes.nmsEntityHuman, "bZ");
            nmsEntityInsentient_setPassengerMotionInputMultiply = getMethod(Classes.nmsEntityInsentient, "k", float.class);
        } else {
            // Do nothing
        }
    }

    //〓 Craft 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * CraftWorldからNmsWorldServerを取得する
     * param Instance CraftWorld
     * return 取得したNmsWorldServer
     */
    public static Method craftWorld_getHandle =  getMethod(Classes.craftWorld, "getHandle");

    /**
     * CraftWorldからワールド名を取得する
     * param Instance CraftWorld
     * return String ワールド名
     */
    public static Method craftWorld_getName =  getMethod(Classes.craftWorld, "getName");

    /**
     * CraftBlockからNmsBlockを取得する
     * param Instance CraftBlock
     * return 取得したNmsBlock
     */
    public static Method craftBlock_getNMSBlock =  getMethod(Classes.craftBlock, "getNMSBlock");

    /**
     * ItemStackからNmsItemStackインスタンスを新規に生成し返す。
     * param Instance {@code null}
     * param ItemStack ベースのItemStack
     * return 生成したNmsItemStack
     */
    public static Method static_craftItemStack_createNmsItemByBukkitItem = getMethod(Classes.craftItemStack, "asNMSCopy", ItemStack.class);

    /**
     * NmsItemStackからItemStackインスタンスを新規に生成し返す。
     * param Instance {@code null}
     * param NmsItemStack ベースのNmsItemStack
     * return 生成したItemStack
     */
    public static Method static_craftItemStack_createBukkitItemByNmsItem =  getMethod(Classes.craftItemStack, "asBukkitCopy", Classes.nmsItemStack);

    /**
     * ItemStackからCraftItemStackインスタンスを新規に生成し返す。
     * param Instance {@code null}
     * param ItemStack ベースのItemStack
     * return 生成したCraftItemStack
     */
    public static Method static_craftItemStack_createCraftItemByBukkitItem =  getMethod(Classes.craftItemStack, "asCraftCopy", ItemStack.class);

    /**
     * NmsItemStackからCraftItemStackインスタンスを新規に生成し返す。
     * param Instance {@code null}
     * param ItemStack ベースのNmsItemStack
     * return 生成したCraftItemStack
     */
    public static Method static_craftItemStack_createCraftItemByNmsItem =  getMethod(Classes.craftItemStack, "asCraftMirror", ItemStack.class);

    //〓 Craft List 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** EntityXxxxxクラス毎のgetHandleメソッド */
    public static HashMap<String, Method> craftEntity_getHandle = new HashMap<String, Method>();
}
