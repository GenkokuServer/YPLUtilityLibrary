package com.github.erozabesu.yplutillibrary.reflection;

import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class Classes extends ReflectionUtil{

    //〓 Nms 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static Class<?> nmsAttributeInstance = getNMSClass("AttributeInstance");
    public static Class<?> nmsAxisAlignedBB = getNMSClass("AxisAlignedBB");
    public static Class<?> nmsBiomeBase = getNMSClass("BiomeBase");
    public static Class<?> nmsBiomeMeta;
    public static Class<?> nmsBlock = getNMSClass("Block");
    public static Class<?> nmsBlockPosition = getNMSClass("BlockPosition");
    public static Class<?> nmsChatComponentText = getNMSClass("ChatComponentText");
    public static Class<?> nmsChatSerializer = null;
    public static Class<?> nmsDamageSource = getNMSClass("DamageSource");
    public static Class<?> nmsEnumClientCommand = null;
    public static Class<?> nmsEnumParticle = getNMSClass("EnumParticle");
    public static Class<?> nmsEnumTitleAction = null;
    public static Class<?> nmsGenericAttributes = getNMSClass("GenericAttributes");
    public static Class<?> nmsEntity = getNMSClass("Entity");
    public static Class<?> nmsEntityArrow = getNMSClass("EntityArrow");
    public static Class<?> nmsEntityAmbient = getNMSClass("EntityAmbient");
    public static Class<?> nmsEntityAnimal = getNMSClass("EntityAnimal");
    public static Class<?> nmsEntityArmorStand = getNMSClass("EntityArmorStand");
    public static Class<?> nmsEntityCreature = getNMSClass("EntityCreature");
    public static Class<?> nmsEntityGiantZombie = getNMSClass("EntityGiantZombie");
    public static Class<?> nmsEntityGolem = getNMSClass("EntityGolem");
    public static Class<?> nmsEntityFishingHook = getNMSClass("EntityFishingHook");
    public static Class<?> nmsEntityHuman = getNMSClass("EntityHuman");
    public static Class<?> nmsEntityInsentient = getNMSClass("EntityInsentient");
    public static Class<?> nmsEntityLiving = getNMSClass("EntityLiving");
    public static Class<?> nmsEntityMonster = getNMSClass("EntityMonster");
    public static Class<?> nmsEntityVillager = getNMSClass("EntityVillager");
    public static Class<?> nmsEntityPlayer = getNMSClass("EntityPlayer");
    public static Class<?> nmsEntityWaterAnimal = getNMSClass("EntityWaterAnimal");
    public static Class<?> nmsEntityTypes = getNMSClass("EntityTypes");
    public static Class<?> nmsIAttribute = getNMSClass("IAttribute");
    public static Class<?> nmsIBlockData = getNMSClass("IBlockData");
    public static Class<?> nmsIChatBaseComponent = getNMSClass("IChatBaseComponent");
    public static Class<?> nmsItem = getNMSClass("Item");
    public static Class<?> nmsItemStack = getNMSClass("ItemStack");
    public static Class<?> nmsMaterial = getNMSClass("Material");
    public static Class<?> nmsMathHelper = getNMSClass("MathHelper");
    public static Class<?> nmsMojangsonParser = getNMSClass("MojangsonParser");
    public static Class<?> nmsNavigationAbstract = getNMSClass("NavigationAbstract");
    public static Class<?> nmsNBTBase = getNMSClass("NBTBase");
    public static Class<?> nmsNBTTagCompound = getNMSClass("NBTTagCompound");
    public static Class<?> nmsNBTTagByte = getNMSClass("NBTTagByte");
    public static Class<?> nmsNBTTagByteArray = getNMSClass("NBTTagByteArray");
    public static Class<?> nmsNBTTagDouble = getNMSClass("NBTTagDouble");
    public static Class<?> nmsNBTTagFloat = getNMSClass("NBTTagFloat");
    public static Class<?> nmsNBTTagInt = getNMSClass("NBTTagInt");
    public static Class<?> nmsNBTTagIntArray = getNMSClass("NBTTagIntArray");
    public static Class<?> nmsNBTTagList = getNMSClass("NBTTagList");
    public static Class<?> nmsNBTTagLong = getNMSClass("NBTTagLong");
    public static Class<?> nmsNBTTagShort = getNMSClass("NBTTagShort");
    public static Class<?> nmsNBTTagString = getNMSClass("NBTTagString");
    public static Class<?> nmsNetworkManager = getNMSClass("NetworkManager");
    public static Class<?> nmsPacket = getNMSClass("Packet");
    public static Class<?> nmsPacketDataSerializer = getNMSClass("PacketDataSerializer");
    public static Class<?> nmsPacketPlayInSteerVehicle = getNMSClass("PacketPlayInSteerVehicle");
    public static Class<?> nmsPacketPlayInUpdateSign = getNMSClass("PacketPlayInUpdateSign");
    public static Class<?> nmsPacketPlayInUseEntity = getNMSClass("PacketPlayInUseEntity");
    public static Class<?> nmsPacketPlayOutAttachEntity = getNMSClass("PacketPlayOutAttachEntity");
    public static Class<?> nmsPacketPlayOutBlockBreakAnimation = getNMSClass("PacketPlayOutBlockBreakAnimation");
    public static Class<?> nmsPacketPlayOutBlockChange = getNMSClass("PacketPlayOutBlockChange");
    public static Class<?> nmsPacketPlayOutChat = getNMSClass("PacketPlayOutChat");
    public static Class<?> nmsPacketPlayOutCamera = getNMSClass("PacketPlayOutCamera");
    public static Class<?> nmsPacketPlayOutCustomPayload = getNMSClass("PacketPlayOutCustomPayload");
    public static Class<?> nmsPacketPlayOutEntityDestroy = getNMSClass("PacketPlayOutEntityDestroy");
    public static Class<?> nmsPacketPlayOutEntityEquipment = getNMSClass("PacketPlayOutEntityEquipment");
    public static Class<?> nmsPacketPlayOutEntityLook = null;
    public static Class<?> nmsPacketPlayOutEntityMetadata = getNMSClass("PacketPlayOutEntityMetadata");
    public static Class<?> nmsPacketPlayOutEntityStatus = getNMSClass("PacketPlayOutEntityStatus");
    public static Class<?> nmsPacketPlayOutEntityTeleport = getNMSClass("PacketPlayOutEntityTeleport");
    public static Class<?> nmsPacketPlayOutNamedEntitySpawn = getNMSClass("PacketPlayOutNamedEntitySpawn");
    public static Class<?> nmsPacketPlayOutOpenSignEditor = getNMSClass("PacketPlayOutOpenSignEditor");
    public static Class<?> nmsPacketPlayOutRelEntityMoveLook = null;
    public static Class<?> nmsPacketPlayOutSetSlot = getNMSClass("PacketPlayOutSetSlot");
    public static Class<?> nmsPacketPlayOutSpawnEntity = getNMSClass("PacketPlayOutSpawnEntity");
    public static Class<?> nmsPacketPlayOutSpawnEntityLiving = getNMSClass("PacketPlayOutSpawnEntityLiving");
    public static Class<?> nmsPacketPlayOutTitle = getNMSClass("PacketPlayOutTitle");
    public static Class<?> nmsPacketPlayOutWindowItems = getNMSClass("PacketPlayOutWindowItems");
    public static Class<?> nmsPacketPlayOutWorldParticles = getNMSClass("PacketPlayOutWorldParticles");
    public static Class<?> nmsPacketPlayInClientCommand = getNMSClass("PacketPlayInClientCommand");

    public static Class<?> nmsPathEntity = getNMSClass("PathEntity");
    public static Class<?> nmsPathfinderGoal = getNMSClass("PathfinderGoal");
    public static Class<?> nmsPathfinderGoalSelector = getNMSClass("PathfinderGoalSelector");
    public static Class<?> nmsPathfinderGoalFloat = getNMSClass("PathfinderGoalFloat");
    public static Class<?> nmsPathfinderGoalMeleeAttack = getNMSClass("PathfinderGoalMeleeAttack");
    public static Class<?> nmsPathfinderGoalMoveThroughVillage = getNMSClass("PathfinderGoalMoveThroughVillage");
    public static Class<?> nmsPathfinderGoalMoveTowardsRestriction = getNMSClass("PathfinderGoalMoveTowardsRestriction");
    public static Class<?> nmsPathfinderGoalRandomStroll = getNMSClass("PathfinderGoalRandomStroll");
    public static Class<?> nmsPathfinderGoalLookAtPlayer = getNMSClass("PathfinderGoalLookAtPlayer");
    public static Class<?> nmsPathfinderGoalRandomLookaround = getNMSClass("PathfinderGoalRandomLookaround");
    public static Class<?> nmsPathfinderGoalHurtByTarget = getNMSClass("PathfinderGoalHurtByTarget");
    public static Class<?> nmsPathfinderGoalNearestAttackableTarget = getNMSClass("PathfinderGoalNearestAttackableTarget");

    public static Class<?> nmsPlayerConnection = getNMSClass("PlayerConnection");
    public static Class<?> nmsVec3D = getNMSClass("Vec3D");
    public static Class<?> nmsVector3f = getNMSClass("Vector3f");
    public static Class<?> nmsWatchableObject;
    public static Class<?> nmsWorld = getNMSClass("World");

    //XXX: CraftBukkit Unstable
    static {
        if (getBukkitVersion().equalsIgnoreCase("v1_8_R1")) {
            nmsBiomeMeta = getNMSClass("BiomeMeta");
            nmsChatSerializer = getNMSClass("ChatSerializer");
            nmsEnumTitleAction = getNMSClass("EnumTitleAction");
            nmsEnumClientCommand = getNMSClass("EnumClientCommand");
            nmsPacketPlayOutEntityLook = getNMSClass("PacketPlayOutEntityLook");
            nmsPacketPlayOutRelEntityMoveLook = getNMSClass("PacketPlayOutRelEntityMoveLook");
            nmsWatchableObject = getNMSClass("WatchableObject");
        } else if (getBukkitVersion().equalsIgnoreCase("v1_8_R2") || getBukkitVersion().equalsIgnoreCase("v1_8_R3")) {
            nmsBiomeMeta = getNMSClass("BiomeBase$BiomeMeta");
            nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
            nmsEnumTitleAction = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
            nmsEnumClientCommand = getNMSClass("PacketPlayInClientCommand$EnumClientCommand");
            nmsPacketPlayOutEntityLook = getNMSClass("PacketPlayOutEntity$PacketPlayOutEntityLook");
            nmsPacketPlayOutRelEntityMoveLook = getNMSClass("PacketPlayOutEntity$PacketPlayOutRelEntityMoveLook");
            nmsWatchableObject = getNMSClass("DataWatcher$WatchableObject");
        } else {
            // Do nothing
        }
    }

    //〓 Craft 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static Class<?> craftWorld = getCraftClass("CraftWorld");
    public static Class<?> craftBlock = getCraftClass("block.CraftBlock");
    public static Class<?> craftEntity = getCraftClass("entity.CraftEntity");
    public static Class<?> craftItemStack = getCraftClass("inventory.CraftItemStack");
}
