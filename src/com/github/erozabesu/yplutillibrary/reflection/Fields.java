package com.github.erozabesu.yplutillibrary.reflection;

import java.lang.reflect.Field;

import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class Fields extends ReflectionUtil {

    public static Field nmsAxisAlignedBB_locYBottom = getField(Classes.nmsAxisAlignedBB, "b");
    public static Field nmsBiomeBase_animalEntityList = getField(Classes.nmsBiomeBase, "au");
    public static Field nmsBiomeBase_monsterEntityList = getField(Classes.nmsBiomeBase, "at");
    public static Field nmsBiomeBase_waterAnimalEntityList = getField(Classes.nmsBiomeBase, "av");
    public static Field nmsBiomeBase_ambientEntityList = getField(Classes.nmsBiomeBase, "aw");
    public static Field nmsBiomeMeta_registeredEntityClass = getField(Classes.nmsBiomeMeta, "b");

    public static Field nmsWorld_isClientSide;

    /** return double ブロックの高さ。通常ブロックなら1.0D、半ブロックなら0.5D */
    public static Field nmsBlock_maxY = getField(Classes.nmsBlock, "maxY");
    public static Field nmsBlock_strength = getField(Classes.nmsBlock, "strength");

    public static Field nmsEntity_dead = getField(Classes.nmsEntity, "dead");
    public static Field nmsEntity_onGround = getField(Classes.nmsEntity, "onGround");
    public static Field nmsEntity_noclip;
    public static Field nmsEntity_positionChanged = getField(Classes.nmsEntity, "positionChanged");
    public static Field nmsEntity_climbableHeight = getField(Classes.nmsEntity, "S");
    public static Field nmsEntity_fallDistance = getField(Classes.nmsEntity, "fallDistance");
    public static Field nmsEntity_justCreated = getField(Classes.nmsEntity, "justCreated");
    public static Field nmsEntity_ticksLived = getField(Classes.nmsEntity, "ticksLived");
    public static Field nmsEntity_locX = getField(Classes.nmsEntity, "locX");
    public static Field nmsEntity_locY = getField(Classes.nmsEntity, "locY");
    public static Field nmsEntity_locZ = getField(Classes.nmsEntity, "locZ");
    public static Field nmsEntity_yaw = getField(Classes.nmsEntity, "yaw");
    public static Field nmsEntity_lastYaw = getField(Classes.nmsEntity, "lastYaw");
    public static Field nmsEntity_pitch = getField(Classes.nmsEntity, "pitch");
    public static Field nmsEntity_finalYaw;
    public static Field nmsEntity_lastX = getField(Classes.nmsEntity, "lastX");
    public static Field nmsEntity_lastY = getField(Classes.nmsEntity, "lastY");
    public static Field nmsEntity_lastZ = getField(Classes.nmsEntity, "lastZ");
    public static Field nmsEntity_motX = getField(Classes.nmsEntity, "motX");
    public static Field nmsEntity_motY = getField(Classes.nmsEntity, "motY");
    public static Field nmsEntity_motZ = getField(Classes.nmsEntity, "motZ");

    /** return float */
    public static Field nmsEntity_width = getField(Classes.nmsEntity, "width");

    /** return float */
    public static Field nmsEntity_length = getField(Classes.nmsEntity, "length");
    public static Field nmsEntity_passenger = getField(Classes.nmsEntity, "passenger");
    public static Field nmsEntity_vehicle = getField(Classes.nmsEntity, "vehicle");

    public static Field nmsEntityLiving_forwardMotionInput;
    public static Field nmsEntityLiving_sideMotionInput;
    public static Field nmsEntityLiving_isJumping;

    public static Field nmsEntityArrow_blockPositionX = getField(Classes.nmsEntityArrow, "d");
    public static Field nmsEntityArrow_blockPositionY = getField(Classes.nmsEntityArrow, "e");
    public static Field nmsEntityArrow_blockPositionZ = getField(Classes.nmsEntityArrow, "f");
    public static Field nmsEntityArrow_inGround = getField(Classes.nmsEntityArrow, "inGround");
    public static Field nmsEntityArrow_hitBlock = getField(Classes.nmsEntityArrow, "g");

    public static Field nmsEntityFishingHook_owner = getField(Classes.nmsEntityFishingHook, "owner");

    /** return boolean */
    public static Field nmsEntityInsentient_unremoveWhenFarAway = getField(Classes.nmsEntityInsentient, "persistent");

    /** return NmsPathFinderGoalSelector */
    public static Field nmsEntityInsentient_goalSelector = getField(Classes.nmsEntityInsentient, "goalSelector");

    /** return NmsPathFinderGoalSelector */
    public static Field nmsEntityInsentient_targetSelector = getField(Classes.nmsEntityInsentient, "targetSelector");

    /** return PlayerConnection */
    public static Field nmsEntityPlayer_playerConnection = getField(Classes.nmsEntityPlayer, "playerConnection");

    /** return NetworkManager */
    public static Field nmsPlayerConnection_networkManager = getField(Classes.nmsPlayerConnection, "networkManager");

    /** return Channel */
    public static Field nmsNetworkManager_channel;

    public static Field nmsNBTTagCompound_map = getField(Classes.nmsNBTTagByte, "map");
    public static Field nmsNBTTagByte_data = getField(Classes.nmsNBTTagByte, "data");
    public static Field nmsNBTTagByteArray_data = getField(Classes.nmsNBTTagByteArray, "data");
    public static Field nmsNBTTagDouble_data = getField(Classes.nmsNBTTagDouble, "data");
    public static Field nmsNBTTagFloat_data = getField(Classes.nmsNBTTagFloat, "data");
    public static Field nmsNBTTagInt_data = getField(Classes.nmsNBTTagInt, "data");
    public static Field nmsNBTTagIntArray_data = getField(Classes.nmsNBTTagIntArray, "data");
    public static Field nmsNBTTagLong_data = getField(Classes.nmsNBTTagLong, "data");
    public static Field nmsNBTTagShort_data = getField(Classes.nmsNBTTagShort, "data");
    public static Field nmsNBTTagString_data = getField(Classes.nmsNBTTagString, "data");
    public static Field nmsNBTTagList_list = getField(Classes.nmsNBTTagList, "list");
    public static Field nmsNBTTagList_type = getField(Classes.nmsNBTTagList, "type");

    public static Field nmsPacketPlayInUpdateSign_getComponentArray = getField(Classes.nmsPacketPlayInUpdateSign, "b");
    public static Field nmsPacketPlayInSteerVehicle_isUnmount = getField(Classes.nmsPacketPlayInSteerVehicle, "d");
    public static Field nmsPacketPlayOutEntityMetadata_EntityId = getField(Classes.nmsPacketPlayOutEntityMetadata, "a");
    public static Field nmsPacketPlayOutEntityMetadata_WatchableObject = getField(Classes.nmsPacketPlayOutEntityMetadata, "b");
    public static Field nmsPacketPlayOutNamedEntitySpawn_UUID = getField(Classes.nmsPacketPlayOutNamedEntitySpawn, "b");
    public static Field nmsPacketPlayOutEntityEquipment_EntityId = getField(Classes.nmsPacketPlayOutEntityEquipment, "a");
    public static Field nmsPacketPlayOutEntityEquipment_ItemSlot = getField(Classes.nmsPacketPlayOutEntityEquipment, "b");
    public static Field nmsPacketPlayOutEntityEquipment_ItemStack = getField(Classes.nmsPacketPlayOutEntityEquipment, "c");
    public static Field nmsPacketPlayOutEntityTeleport_EntityId = getField(Classes.nmsPacketPlayOutEntityTeleport, "a");
    public static Field nmsPacketPlayOutEntityTeleport_LocationY = getField(Classes.nmsPacketPlayOutEntityTeleport, "c");
    public static Field nmsPacketPlayOutEntityTeleport_LocationYaw = getField(Classes.nmsPacketPlayOutEntityTeleport, "e");
    public static Field nmsPacketPlayOutSpawnEntity_EntityId = getField(Classes.nmsPacketPlayOutSpawnEntity, "a");
    public static Field nmsPacketPlayOutSpawnEntity_LocationY = getField(Classes.nmsPacketPlayOutSpawnEntity, "c");
    public static Field nmsPacketPlayOutSpawnEntity_LocationYaw = getField(Classes.nmsPacketPlayOutSpawnEntity, "i");

    public static Field nmsPathfinderGoalSelector_itemList = getField(Classes.nmsPathfinderGoalSelector, "b");

    public static Field craftItemStack_handle = getField(Classes.craftItemStack, "handle");

    public static Field static_nmsBiomeBase_biomes = getField(Classes.nmsBiomeBase, "biomes");

    public static Field static_nmsGenericAttributes_maxHealth = getField(Classes.nmsGenericAttributes, "maxHealth");
    public static Field static_nmsGenericAttributes_followRange;
    public static Field static_nmsGenericAttributes_knockbackResistance = getField(Classes.nmsGenericAttributes, "c");
    public static Field static_nmsGenericAttributes_movementSpeed;
    public static Field static_nmsGenericAttributes_attackDamage;

    public static Field static_nmsEntityTypes_entityMap_EntityName_EntityClass = getField(Classes.nmsEntityTypes, "c");
    public static Field static_nmsEntityTypes_entityMap_EntityClass_EntityName = getField(Classes.nmsEntityTypes, "d");
    public static Field static_nmsEntityTypes_entityMap_EntityId_EntityClass = getField(Classes.nmsEntityTypes, "e");
    public static Field static_nmsEntityTypes_entityMap_EntityClass_EntityId = getField(Classes.nmsEntityTypes, "f");
    public static Field static_nmsEntityTypes_entityMap_EntityName_EntityId = getField(Classes.nmsEntityTypes, "g");

    //XXX: CraftBukkit Unstable
    static {
        if (getBukkitVersion().equalsIgnoreCase("v1_8_R1")) {
            nmsWorld_isClientSide = getField(Classes.nmsWorld, "isStatic");
            nmsEntity_noclip = getField(Classes.nmsEntity, "T");
            nmsEntityLiving_forwardMotionInput = getField(Classes.nmsEntityLiving, "aY");
            nmsEntityLiving_sideMotionInput = getField(Classes.nmsEntityLiving, "aX");
            nmsEntityLiving_isJumping = getField(Classes.nmsEntityLiving, "aW");
            nmsNetworkManager_channel = getField(Classes.nmsNetworkManager, "i");
            static_nmsGenericAttributes_followRange = getField(Classes.nmsGenericAttributes, "b");
            static_nmsGenericAttributes_movementSpeed = getField(Classes.nmsGenericAttributes, "d");
            static_nmsGenericAttributes_attackDamage = getField(Classes.nmsGenericAttributes, "e");
        } else if (getBukkitVersion().equalsIgnoreCase("v1_8_R2")) {
            nmsWorld_isClientSide = getField(Classes.nmsWorld, "isClientSide");
            nmsEntity_noclip = getField(Classes.nmsEntity, "noclip");
            nmsEntityLiving_forwardMotionInput = getField(Classes.nmsEntityLiving, "ba");
            nmsEntityLiving_sideMotionInput = getField(Classes.nmsEntityLiving, "aZ");
            nmsEntityLiving_isJumping = getField(Classes.nmsEntityLiving, "aY");
            nmsNetworkManager_channel = getField(Classes.nmsNetworkManager, "k");
            static_nmsGenericAttributes_followRange = getField(Classes.nmsGenericAttributes, "b");
            static_nmsGenericAttributes_movementSpeed = getField(Classes.nmsGenericAttributes, "d");
            static_nmsGenericAttributes_attackDamage = getField(Classes.nmsGenericAttributes, "e");
        } else if (getBukkitVersion().equalsIgnoreCase("v1_8_R3")) {
            nmsWorld_isClientSide = getField(Classes.nmsWorld, "isClientSide");
            nmsEntity_noclip = getField(Classes.nmsEntity, "noclip");
            nmsEntityLiving_forwardMotionInput = getField(Classes.nmsEntityLiving, "ba");
            nmsEntityLiving_sideMotionInput = getField(Classes.nmsEntityLiving, "aZ");
            nmsEntityLiving_isJumping = getField(Classes.nmsEntityLiving, "aY");
            nmsNetworkManager_channel = getField(Classes.nmsNetworkManager, "channel");
            static_nmsGenericAttributes_followRange = getField(Classes.nmsGenericAttributes, "FOLLOW_RANGE");
            static_nmsGenericAttributes_movementSpeed = getField(Classes.nmsGenericAttributes, "MOVEMENT_SPEED");
            static_nmsGenericAttributes_attackDamage = getField(Classes.nmsGenericAttributes, "ATTACK_DAMAGE");
        } else {
            // Do nothing
        }
    }
}
