package com.github.erozabesu.yplutillibrary.enumdata;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.github.erozabesu.yplutillibrary.object.SoundEffect;

/**
 * @author erozabesu
 *
 */
public enum SoundEffectType {

    ATTACK_FAIL(new SoundEffect(Sound.ITEM_BREAK, 0.4F, 2.0F), new SoundEffect(Sound.FIRE_IGNITE, 1.0F, 2.0F)),
    BLOCK_BREAK_HEAVY(new SoundEffect(Sound.ZOMBIE_WOOD, 1.0F, 0.2F)),
    EXPLODE_HEAVY(new SoundEffect(Sound.EXPLODE, 1.0F, 0.2F)),
    GUS_BLOW(new SoundEffect(Sound.GHAST_FIREBALL, 0.1F, 1.5F), new SoundEffect(Sound.FIZZ, 0.2F, 1.0F)),
    HURT(new SoundEffect(Sound.HURT_FLESH, 1.0F, 1.2F)),
    HURT_HEAVY(new SoundEffect(Sound.HURT_FLESH, 1.0F, 0.7F)),
    ITEM_BREAK(new SoundEffect(Sound.ITEM_BREAK, 1.0F, 1.0F)),
    ITEM_RELOAD_LIGHT(new SoundEffect(Sound.DOOR_OPEN, 1.0F, 1.5F)),
    ITEM_RELOAD_HEAVY(new SoundEffect(Sound.DOOR_CLOSE, 1.0F, 0.8F)),
    METAL_IDLE(new SoundEffect(Sound.FIRE_IGNITE, 0.3F, 0.1F)),
    METAL_HIT(new SoundEffect(Sound.BLAZE_HIT, 1.0F, 1.5F)),
    METAL_HIT_FAIL(new SoundEffect(Sound.BLAZE_HIT, 0.6F, 4.0F)),
    METAL_HIT_HEAVY(new SoundEffect(Sound.BLAZE_HIT, 1.0F, 0.5F)),
    SMOKE_SPREAD(new SoundEffect(Sound.FIZZ, 1.0F, 0.1F));

    List<SoundEffect> soundEffects;

    private SoundEffectType(SoundEffect... sound) {
        this.soundEffects = Arrays.asList(sound);
    }

    public void playSoundToPlayer(Player player) {
        for (SoundEffect soundEffect : this.soundEffects) {
            player.playSound(player.getLocation(), soundEffect.getSound(), soundEffect.getVolume(), soundEffect.getPitch());
        }
    }

    public void playSoundToPlayer(Player player, float volume, float pitch) {
        for (SoundEffect soundEffect : this.soundEffects) {
            player.playSound(player.getLocation(), soundEffect.getSound(), volume, pitch);
        }
    }

    public void playSoundToLocation(Location location) {
        for (SoundEffect soundEffect : this.soundEffects) {
            location.getWorld().playSound(location, soundEffect.getSound(), soundEffect.getVolume(), soundEffect.getPitch());
        }
    }

    public void playSoundToLocation(Location location, float volume, float pitch) {
        for (SoundEffect soundEffect : this.soundEffects) {
            location.getWorld().playSound(location, soundEffect.getSound(), volume, pitch);
        }
    }
}
