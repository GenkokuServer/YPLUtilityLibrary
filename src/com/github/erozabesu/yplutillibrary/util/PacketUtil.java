package com.github.erozabesu.yplutillibrary.util;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import com.github.erozabesu.yplutillibrary.YPLUtilityLibrary;
import com.github.erozabesu.yplutillibrary.enumdata.Particle;
import com.github.erozabesu.yplutillibrary.reflection.Constructors;
import com.github.erozabesu.yplutillibrary.reflection.Fields;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.github.erozabesu.yplutillibrary.reflection.Objects;
import com.google.common.collect.Lists;

public class PacketUtil extends ReflectionUtil {

    /**
     * PlayerとPlayerConnectionを格納するハッシュマップ
     * Playerの再ログインと共に削除されるため、UUIDではなくPlayerを利用する
     */
    private static HashMap<Player, Object> playerConnectionMap = new HashMap<Player, Object>();

    /**
     * PlayerとNetworkManagerを格納するハッシュマップ
     * Playerの再ログインと共に削除されるため、UUIDではなくPlayerを利用する
     */
    private static HashMap<Player, Object> networkManagerMap = new HashMap<Player, Object>();

    /**
     * PlayerとChannelを格納するハッシュマップ
     * Playerの再ログインと共に削除されるため、UUIDではなくPlayerを利用する
     */
    private static HashMap<Player, Channel> channelMap = new HashMap<Player, Channel>();

    //〓 Getter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static HashMap<Player, Object> getPlayerConnectionMap() {
        return playerConnectionMap;
    }

    public static HashMap<Player, Object> getNetworkManagerMap() {
        return networkManagerMap;
    }

    public static HashMap<Player, Channel> getChannelMap() {
        return channelMap;
    }

    //〓 setter 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static void putPlayerConnection(Player player, Object playerConnection) {
        getPlayerConnectionMap().put(player, playerConnection);
    }

    public static void putNetworkManager(Player player, Object networkManager) {
        getNetworkManagerMap().put(player, networkManager);
    }

    public static void putChannel(Player player, Channel channel) {
        getChannelMap().put(player, channel);
    }

    public static void removeAllData(Player player) {
        playerConnectionMap.remove(player);
        networkManagerMap.remove(player);
        channelMap.remove(player);
    }

    //〓 Send Packet 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数disguisePlayerの姿を引数nmsEntityClassに偽装するパケットを引数adressに送信し、仮想スポーンさせたNmsEntityを返す。
     * @param adress パケット送信対象
     * @param disguisePlayer 外見を偽装するプレイヤー
     * @param nmsEntityClass 偽装に利用するNmsEntityクラス
     * @return スポーンさせた仮想NmsEntity
     */
    public static Object disguiseLivingEntity(Player adress, Player disguisePlayer, Class<?> nmsEntityClass) {
        if (nmsEntityClass == null) {
            return null;
        }

        //偽装するNmsEntityがNmsEntityHumanの場合プレイヤーのスポーンパケットを再送信しreturn
        String nmsEntityClassName = nmsEntityClass.getSimpleName();
        if (nmsEntityClassName.equalsIgnoreCase("EntityHuman")) {
            return sendPlayerSpawnPacket(disguisePlayer);
        }

        Object craftEntity = CommonUtil.getNewCraftEntityFromClass(disguisePlayer.getWorld(), nmsEntityClass);

        Object entitydestroypacket = getEntityDestroyPacket(disguisePlayer.getEntityId());
        Object spawnentitypacket = getDisguiseLivingEntityPacket(disguisePlayer, nmsEntityClass);
        Object attachentitypacket = null;
        if (disguisePlayer.getVehicle() != null) {
            attachentitypacket = getAttachEntityPacket(craftEntity, CommonUtil.getCraftEntity(disguisePlayer.getVehicle()));
        }

        Object handpacket = getEquipmentPacket(disguisePlayer, 0, new ItemStack(Material.AIR));
        Object helmetpacket = getEquipmentPacket(disguisePlayer, 4, new ItemStack(Material.AIR));
        Object chectpacket = getEquipmentPacket(disguisePlayer, 3, new ItemStack(Material.AIR));
        Object leggingspacket = getEquipmentPacket(disguisePlayer, 2, new ItemStack(Material.AIR));
        Object bootspacket = getEquipmentPacket(disguisePlayer, 1, new ItemStack(Material.AIR));

        if (adress == null) {
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (other.getUniqueId() == disguisePlayer.getUniqueId()) {
                    continue;
                }
                if (!other.getWorld().getName().equalsIgnoreCase(disguisePlayer.getWorld().getName())) {
                    continue;
                }

                sendPacket(other, entitydestroypacket);
                sendPacket(other, spawnentitypacket);
                if (attachentitypacket != null) {
                    sendPacket(other, attachentitypacket);
                }
                sendPacket(other, handpacket);
                sendPacket(other, helmetpacket);
                sendPacket(other, chectpacket);
                sendPacket(other, leggingspacket);
                sendPacket(other, bootspacket);
            }
        } else {
            sendPacket(adress, entitydestroypacket);
            sendPacket(adress, spawnentitypacket);
            if (attachentitypacket != null) {
                sendPacket(adress, attachentitypacket);
            }
            sendPacket(adress, handpacket);
            sendPacket(adress, helmetpacket);
            sendPacket(adress, chectpacket);
            sendPacket(adress, leggingspacket);
            sendPacket(adress, bootspacket);
        }

        return craftEntity;
    }

    /**
     * 引数playerが一度デスポーンするパケットを送信し、再度スポーンするパケットを送信しスポーンしたNmsHumanエンティティを返す。
     * @param player スポーンパケットを再送信するプレイヤー
     * @param NmsHumanエンティティ
     */
    public static Object sendPlayerSpawnPacket(Player player) {
        Object entityHuman = CommonUtil.getCraftEntity(player);

        Object entityDestroyPacket = getEntityDestroyPacket(player.getEntityId());
        Object spawnNamedEntityPacket = getSpawnNamedEntityPacket(entityHuman);
        Object attachEntityPacket = null;
        if (player.getVehicle() != null) {
            attachEntityPacket = getAttachEntityPacket(entityHuman, CommonUtil.getCraftEntity(player.getVehicle()));
        }
        Object handPacket = player.getItemInHand() == null ? null : getEquipmentPacket(player, 0, player.getItemInHand());
        Object helmetPacket = player.getEquipment().getHelmet() == null ? null : getEquipmentPacket(player, 4, player.getEquipment().getHelmet());
        Object chectPacket = player.getEquipment().getChestplate() == null ? null : getEquipmentPacket(player, 3, player.getEquipment().getChestplate());
        Object leggingsPacket = player.getEquipment().getLeggings() == null ? null : getEquipmentPacket(player, 2, player.getEquipment().getLeggings());
        Object bootsPacket = player.getEquipment().getBoots() == null ? null : getEquipmentPacket(player, 1, player.getEquipment().getBoots());

        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.getUniqueId() == player.getUniqueId()) {
                continue;
            }

            if (!other.getWorld().getName().equalsIgnoreCase(player.getWorld().getName())) {
                continue;
            }

            sendPacket(other, entityDestroyPacket);
            sendPacket(other, spawnNamedEntityPacket);

            if (attachEntityPacket != null) {
                sendPacket(other, attachEntityPacket);
            }

            if (handPacket != null) {
                sendPacket(other, handPacket);
            }

            if (helmetPacket != null) {
                sendPacket(other, helmetPacket);
            }

            if (chectPacket != null) {
                sendPacket(other, chectPacket);
            }

            if (leggingsPacket != null) {
                sendPacket(other, leggingsPacket);
            }

            if (bootsPacket != null) {
                sendPacket(other, bootsPacket);
            }
        }

        return entityHuman;
    }

    /**
     * 引数addressに引数textの文字列をアクションバーに表示するパケットを送信する
     * @param address 送信対象
     * @param text 送信するメッセージ
     */
    public static void sendActionBar(Player address, String text) {
        Object actionBarPacket = getActionBarPacket(text);

        sendPacket(address, actionBarPacket);
    }

    public static void sendTitle(Player address, String text, int fadein, int length, int fadeout, boolean issubtitle) {
        Object titlesendpacket = getTitlePacket(text, issubtitle);
        Object titlelengthpacket = getTitleLengthPacket(fadein, length, fadeout);

        sendPacket(address, titlesendpacket);
        sendPacket(address, titlelengthpacket);
    }

    /**
     * PlayerDeathEventで呼び出すと、引数playerのリスポーンウィンドウをスキップし、強制的にリスポーンさせることが可能。
     * @param address リスポーンウィンドウをスキップするプレイヤー
     */
    public static void skipRespawnScreen(Player address) {
        Object playerConnection = getPlayerConnection(address);
        Object packetPlayInClientCommnad = newInstance(Constructors.nmsPacketPlayInClientCommand, Objects.nmsEnumClientCommand_PerformRespawn);

        invoke(Methods.nmsPlayerConnection_skipRespawnWindow, playerConnection, packetPlayInClientCommnad);
    }

    public static void sendBlockBreakAnimationPacket(Player address, int breakerEntityId, Object nmsBlockPosition, int breakLevel) {
        Object blockBreakPacket = getBlockBreakAnimationPacket(breakerEntityId, nmsBlockPosition, breakLevel);

        if (address == null) {
            for (Player other : Bukkit.getOnlinePlayers()) {
                sendPacket(other, blockBreakPacket);
            }
        } else {
            sendPacket(address, blockBreakPacket);
        }
    }

    /**
     * 引数entityの視界をジャックするパケットを引数addressに送信する
     * @param address パケットを送信するプレイヤー
     * @param nmsEntity ジャックされるNmsエンティティ
     */
    public static void sendCameraPacket(Player address, Object nmsEntity) {
        Object packet = getCameraPacket(nmsEntity);
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    /**
     * 引数entityがデスポーンするパケットを引数addressに送信する
     * @param address パケットを送信するプレイヤー
     * @param entity デスポーンさせるエンティティ
     */
    public static void sendEntityDestroyPacket(Player address, Entity entity) {
        Object packet = getEntityDestroyPacket(entity.getEntityId());
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    /**
     * 数チック後に引数addressが搭乗しているエンティティに再搭乗するパケットを引数addressに送信する。<br>
     * プレイヤーをテレポート後即エンティティに搭乗させるとサーバーとクライアントで情報の食い違いが発生するため、こういった不具合が発生した場合に利用する。<br>
     * 具体的には、クライアント側では搭乗していない状態で描画されるため、搭乗状態のはずが自由移動できてしまう。<br>
     * ただし、サーバーでは搭乗状態になっているため移動しても元の場所に戻される。
     * @param address パケットを送信するプレイヤー
     */
    public static void sendOwnAttachEntityPacket(final Player address) {
        if (address.getVehicle() == null) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(YPLUtilityLibrary.getInstance(), new Runnable() {
            public void run() {
                if (address.isOnline()) {
                    Object attachentitypacket = getAttachEntityPacket(CommonUtil.getCraftEntity(address), CommonUtil.getCraftEntity(address.getVehicle()));
                    sendPacket(address, attachentitypacket);
                }
            }
        }, 5);
    }

    /**
     * 引数entityを引数locationへテレポートさせるパケットを引数addressへ送信する
     * @param address パケットを送信するプレイヤー
     * @param entity テレポートさせるエンティティ
     * @param location テレポートする座標
     */
    public static void sendEntityTeleportPacket(Player address, Entity entity, Location location) {
        Object packet = getEntityTeleportPacket(entity.getEntityId(), location);
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    public static void sendSpawnEntityPacket(Player address, Object craftEntity, int objectID, int objectData) {
        Object packet = getSpawnEntityPacket(craftEntity, objectID, objectData);
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    public static void sendBlockChangePacket(Player address, Location changeLocation) {
        Object nmsWorld = ReflectionUtil.invoke(Methods.craftWorld_getHandle, changeLocation.getWorld());
        Object nmsBlockPosition = ReflectionUtil.newInstance(Constructors.nmsBlockPosition, changeLocation.getBlockX(), changeLocation.getBlockY(), changeLocation.getBlockZ());

        Object packet = getBlockChangePacket(nmsWorld, nmsBlockPosition);
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    /**
     * エンティティがアイテムを装備するパケットを送信する<br>
     * 引数itemSlotで装備するスロットを指定する<br>
     * 0:手  1:ブーツ  2:レギンス  3:チェスト  4:ヘルメット
     * @param address パケットを送信するプレイヤー
     * @param entityId アイテムを装備するエンティティのエンティティID
     * @param itemSlot アイテムを装備するスロット
     * @param equipItemStack 装備するアイテムのNmsItemStack
     */
    public static void sendEntityEquipmentPacket(Player address, int entityId, int itemSlot, Object equipItemStack) {
        Object packet = getEntityEquipmentPacket(entityId, itemSlot, equipItemStack);
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    /**
     * 引数targetにパーティクル再生パケットを送信する
     * @param target パケットを送信するプレイヤー。nullの場合同じワールドの全プレイヤーに送信する
     * @param particle Particleクラスの要素
     * @param isLongDistance パーティクルの描画距離を広いモードで再生するかどうか
     * @param location 再生座標
     * @param randomOffsetX X方向のオフセット。与えられた数値以内の乱数を生成し再生する
     * @param randomOffsetY Y方向のオフセット。与えられた数値以内の乱数を生成し再生する
     * @param randomOffsetZ Z方向のオフセット。与えられた数値以内の乱数を生成し再生する
     * @param speed パーティクルの移動速度？
     * @param count 再生回数
     * @param particleData 基本的にnull。ブロックのID等が影響する場合に用いる。ITEMCRACK:配列数2:ID,Data / BLOCKCRACK:配列数1:ID+(Data<<12) / BLOCKDUST:配列数1:ID+(Data<<12)
     */
    public static void sendParticlePacket(Player target, Particle particle, Location location, float randomOffsetX, float randomOffsetY, float randomOffsetZ, float speed, int count, int[] particleData) {
        World world = location.getWorld();
        Object packet = getParticlePacket(particle, location, randomOffsetX, randomOffsetY, randomOffsetZ, speed, count, particleData);
        if (target == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getLocation().getWorld().equals(world)) {
                    sendPacket(player, packet);
                }
            }
        } else {
            sendPacket(target, packet);
        }
    }

    /**
     * 引数effectの属性を持った花火の炸裂パーティクルを引数locationの座標に引数repeatCountの回数だけ繰り返し生成する。<br>
     * ランダムオフセットを指定した場合は繰り返す毎にオフセットの数値以内のランダムな座標に生成される。
     * @param effect 花火のエフェクト
     * @param location 生成する座標
     * @param repeatCount 繰り返し再生する回数
     * @param randomOffsetX 生成座標のX方向のランダムオフセット
     * @param randomOffsetY 生成座標のY方向のランダムオフセット
     * @param randomOffsetZ 生成座標のZ方向のランダムオフセット
     */
    public static void playFireworksParticle(FireworkEffect effect, Location location, int randomOffsetX, int randomOffsetY, int randomOffsetZ) {
        World world = location.getWorld();

        // エフェクト再生用に一時的に花火エンティティをスポーン
        final Firework fireworksEntity = (Firework) world.spawn(location, Firework.class);

        // エフェクトの設定
        FireworkMeta data = (FireworkMeta) fireworksEntity.getFireworkMeta();
        data.clearEffects();
        data.setPower(1);
        data.addEffect(effect);
        fireworksEntity.setFireworkMeta(data);

        final Object nmsWorld = invoke(Methods.craftWorld_getHandle, world);
        final Object nmsFireworks = CommonUtil.getCraftEntity(fireworksEntity);

        // 遅延させないと動作しない
        Bukkit.getScheduler().runTaskLater(YPLUtilityLibrary.getInstance(), new Runnable() {
            public void run() {
                Methods.invoke(Methods.nmsWorld_broadcastEntityEffect, nmsWorld, nmsFireworks, (byte) 17);
                // 花火エンティティを削除
                fireworksEntity.remove();
            }
        }, 2);
    }

    public static void sendOpenBookPacket(Player player, ItemStack book) {
        ItemStack hand = player.getItemInHand();

        // 一時的にプレイヤーの手に持つアイテムを本と取り替える
        player.setItemInHand(book);

        // パケットの送信
        Object packetSerializer = ReflectionUtil.newInstance(Constructors.nmsPacketDataSerializer, Unpooled.buffer());
        Object payloadPacket = ReflectionUtil.newInstance(Constructors.nmsPacketPlayOutCustomPayload, "MC|BOpen", packetSerializer);
        sendPacket(player, payloadPacket);

        // プレイヤーの手に持つアイテムを元に戻す
        player.setItemInHand(hand);
    }

    public static void sendSetSlotPacket(Player address, int windowId, int slotNumber, ItemStack item) {
        Object packet = ReflectionUtil.newInstance(Constructors.nmsPacketPlayOutSetSlot, windowId, slotNumber, ItemUtil.createNmsItemStack(item));
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    public static void sendWindowItemsPacket(Player address, int windowId, List<ItemStack> itemList) {
        List<Object> nmsItemList = Lists.newArrayList();
        for (ItemStack item : itemList) {
            nmsItemList.add(ItemUtil.createNmsItemStack(item));
        }

        Object packet = ReflectionUtil.newInstance(Constructors.nmsPacketPlayOutWindowItems, windowId, nmsItemList);
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    /**
     * 看板編集ウィンドウを開くパケットを引数addressに送信する。<br>
     * 引数addressが{@code null}の場合はオンラインの全プレイヤーに送信される。<br>
     * 引数の座標にはパケット受信者の居るワールドに存在する看板ブロックの座標を渡すこと。<br>
     * 看板ブロックが取得できなかった場合は空白の看板編集ウィンドウが表示される。
     * @param address パケットを送信するプレイヤー
     * @param x 看板ブロックのX座標
     * @param y 看板ブロックのY座標
     * @param z 看板ブロックのZ座標
     * @return NmsPacketPlayOutOpenSignEditorインスタンス
     */
    public static void sendOpenSignEditorPacket(Player address, int x, int y, int z) {
        Object packet = getOpenSignEditorPacket(x, y, z);
        if (address == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendPacket(player, packet);
            }
        } else {
            sendPacket(address, packet);
        }
    }

    //〓 Get Packet 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    /**
     * 引数entityと同様の情報を持った、引数nmsEntityClassクラスのエンティティがスポーンするパケットを返す
     * @param entity スポーンさせるエンティティの基となる情報をもつエンティティ
     * @param nmsEntityClass スポーンさせるエンティティのNmsEntityClass
     * @return 引数entityと同様の情報を持った、引数nmsEntityClassクラスのエンティティがスポーンするパケット
     */
    public static Object getDisguiseLivingEntityPacket(Entity entity, Class<?> nmsEntityClass) {
        Object craftEntity = CommonUtil.getNewCraftEntityFromClass(entity.getWorld(), nmsEntityClass);
        Location location = entity.getLocation();

        invoke(Methods.nmsEntity_setLocation, craftEntity
                , location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        invoke(Methods.nmsEntity_setEntityID, craftEntity, entity.getEntityId());
        invoke(Methods.nmsEntity_setCustomName, craftEntity, entity.getName());
        invoke(Methods.nmsEntity_setCustomNameVisible, craftEntity, true);

        return getSpawnEntityLivingPacket(craftEntity);
    }

    // itemslot: 0-hand / 4-head / 3-chest / 2-leggings / 1-boots
    private static Object getEquipmentPacket(Entity entity, int itemslot, ItemStack equipment) {
        return newInstance(Constructors.nmsPacketPlayOutEntityEquipment, entity.getEntityId(), itemslot,
                invoke(Methods.static_craftItemStack_createNmsItemByBukkitItem, null, equipment));
    }

    /**
     * 引数textの文字列をアクションバーに表示するパケットを返す
     * @param text 表示するメッセージ
     * @return 引数textの文字列をアクションバーに表示するパケット
     */
    private static Object getActionBarPacket(String text) {
        Object actionBar = invoke(Methods.static_nmsChatSerializer_buildNmsIChatBaseComponent, null, "{\"text\": \"" + text + "\"}");

        return newInstance(Constructors.nmsPacketPlayOutChat, actionBar, (byte) 2);
    }

    private static Object getBlockBreakAnimationPacket(int breakerEntityId, Object nmsBlockPosition, int breakLevel) {
        return newInstance(Constructors.nmsPacketPlayOutBlockBreakAnimation, breakerEntityId, nmsBlockPosition, breakLevel);
    }

    private static Object getTitlePacket(String text, boolean issubtitle) {
        ChatColor color = CommonUtil.getChatColorFromText(text);
        text = ChatColor.stripColor(text);

        Object title = invoke(Methods.static_nmsChatSerializer_buildNmsIChatBaseComponent,
                null, "{\"text\": \"" + text + "\",color:" + color.name().toLowerCase() + "}");

        return newInstance(Constructors.nmsPacketPlayOutTitle, issubtitle ?
                Objects.nmsEnumTitleAction_PerformSubTitle : Objects.nmsEnumTitleAction_PerformTitle, title);
    }

    private static Object getTitleLengthPacket(int fadein, int length, int fadeout) {
        return newInstance(Constructors.nmsPacketPlayOutTitle_Length, fadein, length, fadeout);
    }

    private static Object getClientCommandPacket() {
        return newInstance(Constructors.nmsPacketPlayInClientCommand, Objects.nmsEnumClientCommand_PerformRespawn);
    }

    /**
     * @param nmsEntity 視界をジャックするNMSエンティティ
     * @return 引数nmsEntityの視界をジャックするパケット
     */
    public static Object getCameraPacket(Object nmsEntity){
        return newInstance(Constructors.nmsPacketPlayOutCamera, nmsEntity);
    }

    /**
     * @param entityId デスポーンさせるEntityのEntityID
     * @return 引数entityIdをEntityIDとして持つEntityをデスポーンさせるパケット
     */
    public static Object getEntityDestroyPacket(int entityId){
        return newInstance(Constructors.nmsPacketPlayOutEntityDestroy, new int[] { entityId });
    }

    /**
     * 引数craftPlayerをスポーンさせるパケットを返す<br>
     * HumanEntityのみ利用可能
     * @param craftPlayer スポーンさせるHumanEntity
     * @return 引数craftPlayerをスポーンさせるパケット
     */
    private static Object getSpawnNamedEntityPacket(Object craftPlayer) {
        return newInstance(Constructors.nmsPacketPlayOutNamedEntitySpawn, craftPlayer);
    }

    /**
     * 引数craftLivingEntityエンティティをスポーンさせるパケットを返す<br>
     * LivingEntityのみ利用可能
     * @param craftLivingEntity スポーンさせるCraftEntity
     * @return 引数craftLivingEntityをスポーンさせるパケット
     */
    private static Object getSpawnEntityLivingPacket(Object craftLivingEntity) {
        return newInstance(Constructors.nmsPacketPlayOutSpawnEntityLiving, craftLivingEntity);
    }

    /**
     * @param passenger 搭乗させるCraftEntity
     * @param vehicle 乗り物となるCraftEntity
     * @return 引数passengerを引数vehicleに搭乗させるパケット
     */
    private static Object getAttachEntityPacket(Object passenger, Object vehicle) {
        return newInstance(Constructors.nmsPacketPlayOutAttachEntity, 0, passenger, vehicle);
    }

    /**
     * @param entityId テレポートさせるEntityのEntityID
     * @param location テレポートする座標
     * @return 引数entityIdをEntityIDとして持つEntityを引数locationにテレポートするパケット
     */
    public static Object getEntityTeleportPacket(int entityId, Location location) {
        //パケットではdouble型を扱えないため
        //座標は、座標値を32倍した数値をint型に変換し利用する
        int x = (int) (location.getX() * 32.0D);
        int y = (int) (location.getY() * 32.0D);
        int z = (int) (location.getZ() * 32.0D);

        //パケットではfloat型を扱えないため
        //yaw・pitchはbyte型に変換し利用する
        //変換は、byteの最大値256をyaw・pitchの最大値360.0Fで割り、基になったyaw・pitchに掛け合わせる
        byte yaw = (byte) (location.getYaw() * (255.0F / 360.0F));
        byte pitch = (byte) (location.getPitch() * (255.0F / 360.0F));

        return newInstance(Constructors.nmsPacketPlayOutEntityTeleport, entityId, x, y, z, yaw, pitch, false);
    }

    public static Object getSpawnEntityPacket(Object craftEntity, int objectID, int objectData) {
        return newInstance(Constructors.nmsPacketPlayOutSpawnEntity, craftEntity, objectID, objectData);
    }

    public static Object getBlockChangePacket(Object nmsWorld, Object nmsBlockPosition) {
        return newInstance(Constructors.nmsPacketPlayOutBlockChange, nmsWorld, nmsBlockPosition);
    }

    /**
     * エンティティがアイテムを装備するパケットを返す
     * @param entityId アイテムを装備するエンティティのエンティティID
     * @param itemSlot アイテムを装備するスロット
     * @param equipItemStack 装備するアイテムのNmsItemStackオブジェクト
     * @return エンティティがアイテムを装備するパケット
     */
    public static Object getEntityEquipmentPacket(int entityId, int itemSlot, Object equipItemStack) {
        return newInstance(Constructors.nmsPacketPlayOutEntityEquipment, entityId, itemSlot, equipItemStack);
    }

    /**
     * パーティクル再生パケットを返す
     * @param particle Particleクラスの要素
     * @param location 再生座標
     * @param offsetX X方向のオフセット。与えられた数値以内の乱数を生成し再生する
     * @param offsetY Y方向のオフセット。与えられた数値以内の乱数を生成し再生する
     * @param offsetZ Z方向のオフセット。与えられた数値以内の乱数を生成し再生する
     * @param speed パーティクルの移動速度？
     * @param count 再生回数
     * @param particleData 基本的にnull。ブロックのID等が影響する場合に用いる。ITEMCRACK:配列数2:ID,Data / BLOCKCRACK:配列数1:ID+(Data<<12) / BLOCKDUST:配列数1:ID+(Data<<12)
     * @return パーティクル再生パケット
     */
    public static Object getParticlePacket(Particle particle, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, int[] particleData) {
        return newInstance(Constructors.nmsPacketPlayOutWorldParticles
                , particle.getNmsEnumParticle(), particle.isLongDistance(), (float) location.getX(), (float) location.getY()
                , (float) location.getZ(), offsetX, offsetY, offsetZ, speed, count, particleData);
    }

    /**
     * 花火エンティティの炸裂パーティクルを再生するパケットを返す。
     * @param fireworkEntity 炸裂させる花火エンティティ
     * @return 花火パーティクルパケット
     */
    public static Object getFireworksParticlePacket(Object nmsFireworksEntity) {
        return newInstance(Constructors.nmsPacketPlayOutEntityStatus, nmsFireworksEntity, (byte) 17);
    }

    /**
     * 看板編集ウィンドウを開くパケットを生成し返す。<br>
     * 引数の座標にはパケット受信者の居るワールドに存在する看板ブロックの座標を渡すこと。<br>
     * 看板ブロックが取得できなかった場合は空白の看板編集ウィンドウが表示される。
     * @param x 看板ブロックのX座標
     * @param y 看板ブロックのY座標
     * @param z 看板ブロックのZ座標
     * @return NmsPacketPlayOutOpenSignEditorインスタンス
     */
    public static Object getOpenSignEditorPacket(int x, int y, int z) {
        return newInstance(Constructors.nmsPacketPlayOutOpenSignEditor, newInstance(Constructors.nmsBlockPosition, x, y, z));
    }

    //〓 Util 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓

    public static void sendPacket(Player p, Object packet) {
        invoke(Methods.nmsPlayerConnection_sendPacket, getPlayerConnection(p), packet);
    }

    /**
     * 引数networkManagerと一致するNetworkManagerオブジェクトを所有するプレイヤーを返す
     * @param networkManager NetworkManagerオブジェクト
     * @return NetworkManagerオブジェクトが一致したプレイヤー
     */
    public static Player getPlayerByNetworkManager(Object networkManager) {
        for (Player key : getNetworkManagerMap().keySet()) {
            if (getNetworkManagerMap().get(key).equals(networkManager)) {
                return key;
            }
        }
        return null;
    }

    private static Object getPlayerConnection(Player player) {
        Object connection = getPlayerConnectionMap().get(player);

        if (connection == null) {
            Object craftPlayer = CommonUtil.getCraftEntity(player);

            connection = getFieldValue(Fields.nmsEntityPlayer_playerConnection, craftPlayer);
            putPlayerConnection(player, connection);
        }

        return connection;
    }

    private static Object getNetworkManager(Player player) {
        Object playerconnection = getPlayerConnection(player);
        Object network = getNetworkManagerMap().get(player);

        if (network == null) {
            network = getFieldValue(Fields.nmsPlayerConnection_networkManager, playerconnection);
            putNetworkManager(player, network);
        }

        return network;
    }

    public static Channel getChannel(Player player) {
        Object network = getNetworkManager(player);
        Channel channel = getChannelMap().get(player);

        if (channel == null) {
            channel = (Channel) getFieldValue(Fields.nmsNetworkManager_channel, network);
            putChannel(player, channel);
        }

        return channel;
    }
}
