package com.github.erozabesu.yplutillibrary.task;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.erozabesu.yplutillibrary.enumdata.SoundEffectType;
import com.github.erozabesu.yplutillibrary.reflection.Methods;
import com.github.erozabesu.yplutillibrary.util.CommonUtil;
import com.github.erozabesu.yplutillibrary.util.PacketUtil;
import com.github.erozabesu.yplutillibrary.util.ReflectionUtil;

public class BlockBreakGraduallyTask extends BukkitRunnable {

    private LivingEntity blockBreaker;
    private Location breakerLastLocation;

    private Material blockType;
    private byte blockData;

    private int entityId;
    private Location blockLocation;

    private Object nmsBlockPosition;
    private Object nmsWorld;
    private float maxStrength;

    private boolean isBreakerLocationSensitive;

    private int currentDamage = 0;
    private int currentDamageLevel = -1;

    public BlockBreakGraduallyTask(LivingEntity blockBreaker, Location blockLocation, float strengthMultiply, boolean isBreakerLocationSensitive) {
        this.blockBreaker = blockBreaker;
        this.breakerLastLocation = blockBreaker.getLocation();

        this.blockType = blockLocation.getBlock().getType();
        this.blockData = blockLocation.getBlock().getData();

        this.entityId = Integer.MAX_VALUE - new Random().nextInt(10000);
        this.blockLocation = CommonUtil.adjustLocationToBlockCenter(blockLocation);

        this.nmsBlockPosition = CommonUtil.getBlockPosition(blockLocation);
        this.nmsWorld = ReflectionUtil.invoke(Methods.craftWorld_getHandle, blockLocation.getWorld());
        this.maxStrength = CommonUtil.getBlockStrength(blockLocation) * 120 * strengthMultiply;
        this.maxStrength = Math.round(this.maxStrength);

        this.isBreakerLocationSensitive = isBreakerLocationSensitive;
    }

    @Override
    public void run() {
        ++this.currentDamage;

        if (this.blockType == Material.AIR
                || this.maxStrength < 0
                || this.blockBreaker.isDead()
                || this.blockLocation.getBlock().getType() != this.blockType) {
            this.cancel();
            return;
        }

        if (this.isBreakerLocationSensitive) {
            if (this.currentDamage % 20 == 0) {
                Location currentLocation = this.blockBreaker.getLocation();
                if (this.breakerLastLocation.getX() != currentLocation.getX()
                        || this.breakerLastLocation.getY() != currentLocation.getY()
                        || this.breakerLastLocation.getZ() != currentLocation.getZ()) {
                    this.cancel();
                    return;
                }
            }
        }

        int i = (int) ((float) this.currentDamage / this.maxStrength * 10.0F);

        if (i != this.currentDamageLevel) {
            PacketUtil.sendBlockBreakAnimationPacket(null, this.entityId, this.nmsBlockPosition, i);
            this.currentDamageLevel = i;
        }

        if (this.currentDamage == this.maxStrength) {
            this.blockLocation.getWorld().playEffect(this.blockLocation, Effect.STEP_SOUND, this.blockType.getId(), this.blockData);
            this.blockLocation.getBlock().breakNaturally();
            SoundEffectType.BLOCK_BREAK_HEAVY.playSoundToLocation(this.blockLocation);
            this.cancel();
        }
    }
}
