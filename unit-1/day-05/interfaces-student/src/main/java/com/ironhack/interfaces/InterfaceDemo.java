package com.ironhack.interfaces;

// Demonstrates all major interface features using media players.
public class InterfaceDemo {

    public static void run() {
        System.out.println("=== INTERFACE DEMO ===\n");

        // Variable type is the interface — we don't care if it's Android or Apple.
        Playable android = new AndroidPlayer("Android Player", 40);
        Playable apple   = new ApplePlayer("Apple Player", 60);

        // Cannot instantiate an interface:
        // Playable p = new Playable();  // COMPILE ERROR

        // Default methods: android uses Playable's default; apple uses its own override.
        System.out.println("--- play() — default vs overridden ---");
        android.play();
        apple.play();
        System.out.println();

        // pause() is not overridden in either class — both use Playable's default.
        System.out.println("--- pause() — both use the default ---");
        android.pause();
        apple.pause();
        System.out.println();

        // increaseVolume() is abstract — each class provides different logic.
        System.out.println("--- increaseVolume(30) — add vs set ---");
        android.increaseVolume(30);   // Android: 40 + 30 = 70
        apple.increaseVolume(30);     // Apple: set directly to 30
        System.out.println();

        // mute() is a default method that internally calls getVolume() (abstract).
        System.out.println("--- mute() — default method calls getVolume() ---");
        android.mute();
        apple.mute();
        System.out.println();

        // Interface constants — accessed on the interface name, not on an instance.
        System.out.println("--- Interface constants ---");
        System.out.println("MAX_VOLUME: " + Playable.MAX_VOLUME);
        System.out.println("MIN_VOLUME: " + Playable.MIN_VOLUME);
        System.out.println();

        // Static method — called on the interface, not on any object.
        System.out.println("--- Static method: Playable.isVolumeValid() ---");
        System.out.println("isVolumeValid(50):  " + Playable.isVolumeValid(50));
        System.out.println("isVolumeValid(150): " + Playable.isVolumeValid(150));
        System.out.println("isVolumeValid(-5):  " + Playable.isVolumeValid(-5));
        System.out.println();

        // Polymorphic array — any Playable implementation can go into a Playable[].
        System.out.println("--- Playable[] polymorphic array ---");
        Playable[] playlist = {
                new AndroidPlayer("Galaxy Player", 50),
                new ApplePlayer("Beats Player", 70),
                new AndroidPlayer("Pixel Player", 30)
        };

        for (Playable p : playlist) {
            p.play();
        }
        System.out.println();

        // A class can implement multiple interfaces at once:
        //     class AndroidPlayer implements Playable, Recordable { ... }
        // This is one of the key advantages of interfaces over abstract classes.

        System.out.println("=== END INTERFACE DEMO ===\n");
    }
}
