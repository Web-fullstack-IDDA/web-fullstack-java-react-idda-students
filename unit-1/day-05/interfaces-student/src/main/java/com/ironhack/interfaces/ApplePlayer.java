package com.ironhack.interfaces;

// Implements Playable for the Apple platform.
// Overrides the default play() method; inherits pause() and mute() from the interface.
public class ApplePlayer implements Playable {

    private int volume;
    private String name;

    public ApplePlayer(String name, int volume) {
        this.name = name;
        this.volume = Playable.isVolumeValid(volume) ? volume : Playable.MIN_VOLUME;
    }

    // Apple approach: SETS the volume directly (clamped between MIN and MAX).
    // Different logic from AndroidPlayer, which adds to the current volume.
    @Override
    public void increaseVolume(int amount) {
        this.volume = Math.max(Playable.MIN_VOLUME, Math.min(amount, Playable.MAX_VOLUME));
        System.out.println(name + ": Volume -> " + this.volume + " (set directly to " + amount + ")");
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public String getPlayerName() {
        return name;
    }

    // Overrides the default play() from Playable with an Apple-specific message.
    // @Override is not mandatory here either, but protects against signature mismatches.
    @Override
    public void play() {
        System.out.println(name + ": Playing with Apple Lossless Audio...");
    }
}
