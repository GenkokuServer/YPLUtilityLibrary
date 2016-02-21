package com.github.erozabesu.yplutillibrary.object;

import org.bukkit.Sound;

/**
 * @author erozabesu
 *
 */
public class SoundEffect {

    private Sound sound;
    private float volume;
    private float pitch;

    public SoundEffect(Sound sound, float volume, float pitch) {
        this.setSound(sound);
        this.setVolume(volume);
        this.setPitch(pitch);
    }

    /** @return サウンド */
    public Sound getSound() {
        return sound;
    }

    /** @return ボリューム */
    public float getVolume() {
        return volume;
    }

    /** @return ピッチ */
    public float getPitch() {
        return pitch;
    }

    /** @param sound サウンド */
    private void setSound(Sound sound) {
        this.sound = sound;
    }

    /** @param volume ボリューム */
    private void setVolume(float volume) {
        this.volume = volume;
    }

    /** @param pitch ピッチ */
    private void setPitch(float pitch) {
        this.pitch = pitch;
    }
}
