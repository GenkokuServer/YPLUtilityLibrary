package com.github.erozabesu.yplutillibrary.reflection;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;
import com.mojang.authlib.GameProfile;

public class Constructors extends ReflectionUtil{

    //〓 Nms 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * param NmsWorld<br>
     * param GameProfile
     */
    public static Constructor<?> nmsHuman = getConstructor(Classes.nmsEntityHuman, Classes.nmsWorld, GameProfile.class);

    /**
     * param NmsEntityInsentient<br>
     * param Integer<br>
     * param Integer<br>
     * param Integer
     */
    public static Constructor<?> nmsBiomeMeta = getConstructor(Classes.nmsBiomeMeta, Class.class, int.class, int.class, int.class);

    /**
     * param Double<br>
     * param Double<br>
     * param Double
     */
    public static Constructor<?> nmsBlockPosition = getConstructor(Classes.nmsBlockPosition, double.class, double.class, double.class);

    /**
     * param None
     */
    public static Constructor<?> nmsNBTTagCompound = getConstructor(Classes.nmsNBTTagCompound);

    /** param byte */
    public static Constructor<?> nmsNBTTagByte = getConstructor(Classes.nmsNBTTagByte, byte.class);

    /** param byte[] */
    public static Constructor<?> nmsNBTTagByteArray = getConstructor(Classes.nmsNBTTagByteArray, byte[].class);

    /** param double */
    public static Constructor<?> nmsNBTTagDouble = getConstructor(Classes.nmsNBTTagDouble, double.class);

    /** param float */
    public static Constructor<?> nmsNBTTagFloat = getConstructor(Classes.nmsNBTTagFloat, float.class);

    /** param int */
    public static Constructor<?> nmsNBTTagInt = getConstructor(Classes.nmsNBTTagInt, int.class);

    /** param int[] */
    public static Constructor<?> nmsNBTTagIntArray = getConstructor(Classes.nmsNBTTagIntArray, int[].class);

    /** param None */
    public static Constructor<?> nmsNBTTagList = getConstructor(Classes.nmsNBTTagList);

    /** param long */
    public static Constructor<?> nmsNBTTagLong = getConstructor(Classes.nmsNBTTagLong, long.class);

    /** param short */
    public static Constructor<?> nmsNBTTagShort = getConstructor(Classes.nmsNBTTagShort, short.class);

    /** param String */
    public static Constructor<?> nmsNBTTagString = getConstructor(Classes.nmsNBTTagString, String.class);

    /**
     * param Double<br>
     * param Double<br>
     * param Double
     */
    public static Constructor<?> nmsVec3D = getConstructor(Classes.nmsVec3D, double.class, double.class, double.class);

    /**
     * param Float<br>
     * param Float<br>
     * param Float
     */
    public static Constructor<?> nmsVector3f = getConstructor(Classes.nmsVector3f, float.class, float.class, float.class);

    /**
     * param ByteBuf
     */
    public static Constructor<?> nmsPacketDataSerializer = getConstructor(Classes.nmsPacketDataSerializer, ByteBuf.class);

    /**
     * param NmsIChatBaseComponent<br>
     * param Byte
     */
    public static Constructor<?> nmsPacketPlayOutChat = getConstructor(Classes.nmsPacketPlayOutChat, Classes.nmsIChatBaseComponent, byte.class);

    /**
     * param Integer<br>
     * param NmsEntity<br>
     * param NmsEntity
     */
    public static Constructor<?> nmsPacketPlayOutAttachEntity = getConstructor(Classes.nmsPacketPlayOutAttachEntity, int.class, Classes.nmsEntity, Classes.nmsEntity);

    /**
     * param Integer<br>
     * param NmsBlockPosition<br>
     * param Integer
     */
    public static Constructor<?> nmsPacketPlayOutBlockBreakAnimation = getConstructor(Classes.nmsPacketPlayOutBlockBreakAnimation, int.class, Classes.nmsBlockPosition, int.class);

    /**
     * param NmsWorld<br>
     * param NmsBlockPosition
     */
    public static Constructor<?> nmsPacketPlayOutBlockChange = getConstructor(Classes.nmsPacketPlayOutBlockChange, Classes.nmsWorld, Classes.nmsBlockPosition);

    /** param NmsEntity */
    public static Constructor<?> nmsPacketPlayOutCamera = getConstructor(Classes.nmsPacketPlayOutCamera, Classes.nmsEntity);

    /**
     * param String
     * param NmsPacketDataSerializer
     */
    public static Constructor<?> nmsPacketPlayOutCustomPayload = getConstructor(Classes.nmsPacketPlayOutCustomPayload, String.class, Classes.nmsPacketDataSerializer);

    /** param Integer[] */
    public static Constructor<?> nmsPacketPlayOutEntityDestroy = getConstructor(Classes.nmsPacketPlayOutEntityDestroy, int[].class);

    /**
     * param Integer<br>
     * param Integer<br>
     * param NmsItemStack
     */
    public static Constructor<?> nmsPacketPlayOutEntityEquipment = getConstructor(Classes.nmsPacketPlayOutEntityEquipment, int.class, int.class, Classes.nmsItemStack);

    /**
     * param Integer<br>
     * param Byte<br>
     * param Byte<br>
     * param Boolean
     */
    public static Constructor<?> nmsPacketPlayOutEntityLook = getConstructor(Classes.nmsPacketPlayOutEntityLook, int.class, byte.class, byte.class, boolean.class);

    /**
     * param NmsEntity<br>
     * param Byte
     */
    public static Constructor<?> nmsPacketPlayOutEntityStatus = getConstructor(Classes.nmsPacketPlayOutEntityStatus, Classes.nmsEntity, byte.class);

    /**
     * param Integer<br>
     * param Integer<br>
     * param Integer<br>
     * param Integer<br>
     * param Byte<br>
     * param Byte<br>
     * param Boolean<br>
     */
    public static Constructor<?> nmsPacketPlayOutEntityTeleport = getConstructor(Classes.nmsPacketPlayOutEntityTeleport, int.class, int.class, int.class, int.class, byte.class, byte.class, boolean.class);

    /** param NmsBlockPosition */
    public static Constructor<?> nmsPacketPlayOutOpenSignEditor = getConstructor(Classes.nmsPacketPlayOutOpenSignEditor, Classes.nmsBlockPosition);

    /**
     * param Integer<br>
     * param Byte<br>
     * param Byte<br>
     * param Byte<br>
     * param Byte<br>
     * param Byte<br>
     * param Boolean
     */
    public static Constructor<?> nmsPacketPlayOutRelEntityMoveLook = getConstructor(Classes.nmsPacketPlayOutRelEntityMoveLook, int.class, byte.class, byte.class, byte.class, byte.class, byte.class, boolean.class);

    /** param NmsEntityHuman */
    public static Constructor<?> nmsPacketPlayOutNamedEntitySpawn = getConstructor(Classes.nmsPacketPlayOutNamedEntitySpawn, Classes.nmsEntityHuman);

    /**
     * param Integer<br>
     * param Integer<br>
     * param NmsItemStack
     */
    public static Constructor<?> nmsPacketPlayOutSetSlot = getConstructor(Classes.nmsPacketPlayOutSetSlot, int.class, int.class, Classes.nmsItemStack);

    /**
     * param NmsEntity<br>
     * param Integer<br>
     * param Integer
     */
    public static Constructor<?> nmsPacketPlayOutSpawnEntity = getConstructor(Classes.nmsPacketPlayOutSpawnEntity, Classes.nmsEntity, int.class, int.class);

    /** param NmsEntityLiving */
    public static Constructor<?> nmsPacketPlayOutSpawnEntityLiving = getConstructor(Classes.nmsPacketPlayOutSpawnEntityLiving, Classes.nmsEntityLiving);

    /** param NmsIChatBaseComponent */
    public static Constructor<?> nmsPacketPlayOutTitle = getConstructor(Classes.nmsPacketPlayOutTitle, Classes.nmsEnumTitleAction, Classes.nmsIChatBaseComponent);

    /**
     * param Integer<br>
     * param Integer<br>
     * param Integer
     */
    public static Constructor<?> nmsPacketPlayOutTitle_Length = getConstructor(Classes.nmsPacketPlayOutTitle, int.class, int.class, int.class);

    /**
     * param Integer<br>
     * param List<NmsItemStack>
     */
    public static Constructor<?> nmsPacketPlayOutWindowItems = getConstructor(Classes.nmsPacketPlayOutWindowItems, int.class, List.class);

    /**
     * param NmsEnumParticle<br>
     * param Boolean<br>
     * param Float<br>
     * param Float<br>
     * param Float<br>
     * param Float<br>
     * param Float<br>
     * param Float<br>
     * param Float<br>
     * param Integer<br>
     * param Integer[]
     */
    public static Constructor<?> nmsPacketPlayOutWorldParticles = getConstructor(Classes.nmsPacketPlayOutWorldParticles, Classes.nmsEnumParticle, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);

    /** param NmsEnumClientCommand */
    public static Constructor<?> nmsPacketPlayInClientCommand = getConstructor(Classes.nmsPacketPlayInClientCommand, Classes.nmsEnumClientCommand);

    /** param EntityInsentient */
    public static Constructor<?> nmsPathfinderGoalFloat = getConstructor(Classes.nmsPathfinderGoalFloat, Classes.nmsEntityInsentient);

    /**
     * param NmsEntityCrerature<br>
     * param Class NmsEntityを継承したクラス<br>
     * param Double<br>
     * param Boolean
     */
    public static Constructor<?> nmsPathfinderGoalMeleeAttack = getConstructor(Classes.nmsPathfinderGoalMeleeAttack, Classes.nmsEntityCreature, Class.class, double.class, boolean.class);

    /**
     * param NmsEntityCrerature<br>
     * param Double<br>
     * param Boolean
     */
    public static Constructor<?> nmsPathfinderGoalMoveThroughVillage = getConstructor(Classes.nmsPathfinderGoalMoveThroughVillage, Classes.nmsEntityCreature, double.class, boolean.class);

    /**
     * param NmsEntityCrerature<br>
     * param Double
     */
    public static Constructor<?> nmsPathfinderGoalMoveTowardsRestriction = getConstructor(Classes.nmsPathfinderGoalMoveTowardsRestriction, Classes.nmsEntityCreature, double.class);

    /**
     * param NmsEntityCrerature<br>
     * param Double
     */
    public static Constructor<?> nmsPathfinderGoalRandomStroll = getConstructor(Classes.nmsPathfinderGoalRandomStroll, Classes.nmsEntityCreature, double.class);

    /**
     * param NmsEntityInsentient<br>
     * param Class NmsEntityを継承したクラス<br>
     * param Float
     */
    public static Constructor<?> nmsPathfinderGoalLookAtPlayer = getConstructor(Classes.nmsPathfinderGoalLookAtPlayer, Classes.nmsEntityInsentient, Class.class, float.class);

    /** param NmsEntityInsentient */
    public static Constructor<?> nmsPathfinderGoalRandomLookaround = getConstructor(Classes.nmsPathfinderGoalRandomLookaround, Classes.nmsEntityInsentient);

    /**
     * param NmsEntityCrerature<br>
     * param Boolean<br>
     * param Class[]
     */
    public static Constructor<?> nmsPathfinderGoalHurtByTarget = getConstructor(Classes.nmsPathfinderGoalHurtByTarget, Classes.nmsEntityCreature, boolean.class, Class[].class);

    /**
     * param NmsEntityCrerature<br>
     * param Class<br>
     * param Boolean
     */
    public static Constructor<?> nmsPathfinderGoalNearestAttackableTarget = getConstructor(Classes.nmsPathfinderGoalNearestAttackableTarget, Classes.nmsEntityCreature, Class.class, boolean.class);

    //〓 Nms List 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /** EntityXxxxxクラス毎のコンストラクタ */
    public static HashMap<String, Constructor<?>> nmsEntity_Constructor = new HashMap<String, Constructor<?>>();
}
