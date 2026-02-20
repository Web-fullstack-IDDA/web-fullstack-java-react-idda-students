package com.ironhack.interfaces;

// Interface defining the contract for any media player.
// An interface can contain: constants, abstract methods, default methods, and static methods.
public interface Playable {

    // Constants — implicitly public static final. The only kind of "field" allowed in an interface.
    int MAX_VOLUME = 100;
    int MIN_VOLUME = 0;

    // Abstract methods — every implementing class MUST provide a body for these.
    void increaseVolume(int amount);

    int getVolume();

    String getPlayerName();

    // Default methods — have a body; implementing classes inherit them and MAY override.
    default void play() {
        System.out.println(getPlayerName() + ": Playing...");
    }

    default void pause() {
        System.out.println(getPlayerName() + ": Paused.");
    }

    // Default method that calls the abstract method getVolume() — works because
    // at runtime "this" is always a concrete implementing object.
    default void mute() {
        System.out.println(getPlayerName() + ": Muted. (Volume was " + getVolume() + ")");
    }

    // Static method — belongs to the interface itself, not to instances.
    // Call as: Playable.isVolumeValid(80)  — never on an instance variable.
    static boolean isVolumeValid(int volume) {
        return volume >= MIN_VOLUME && volume <= MAX_VOLUME;
    }
}
