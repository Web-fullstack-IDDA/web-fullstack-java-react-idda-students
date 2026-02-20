package com.ironhack.interfaces;

// Implements Playable for the Android platform.
// Does NOT override default methods — inherits play(), pause(), mute() from the interface.
public class AndroidPlayer implements Playable {

    // Instance fields go in the class, not the interface (interfaces have no instance state).
    private int volume;
    private String name;

    public AndroidPlayer(String name, int volume) {
        this.name = name;
        // Use the interface's static utility method to validate the initial volume.
        this.volume = Playable.isVolumeValid(volume) ? volume : Playable.MIN_VOLUME;
    }

    // @Override is not mandatory but strongly recommended — it protects against typos
    // and makes it clear this method fulfills an interface contract.

    // Android approach: ADDS amount to the current volume, capped at MAX_VOLUME.
    @Override
    public void increaseVolume(int amount) {
        int newVolume = this.volume + amount;
        this.volume = Math.min(newVolume, Playable.MAX_VOLUME);
        System.out.println(name + ": Volume -> " + this.volume + " (added " + amount + ")");
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public String getPlayerName() {
        return name;
    }
}
