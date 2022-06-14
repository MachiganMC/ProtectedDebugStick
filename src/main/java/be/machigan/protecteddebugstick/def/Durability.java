package be.machigan.protecteddebugstick.def;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.utils.Utils;

public enum Durability {

    ORIENTABLE(1),
    DIRECTIONAL(1),
    ROTATABLE(1),
    TYPE(1),
    BISECTED(1),
    SHAPE(1),
    PERSISTENT(3),
    MULTIPLEFACING(1),
    LIGHTABLE(5),
    REDSTONEWIRE(2),
    WATERLOGGED(3),
    POWERABLE(10),
    AGEABLE(20),
    SAPLING(10),
    BEEHIVE(5);


    private int durability;

    Durability(int durability) {
        this.durability = durability;
    }

    public int value() {
        return this.durability;
    }

    public static void init() {
        settingFromConfig(ORIENTABLE, "orientable");
        settingFromConfig(DIRECTIONAL, "directional");
        settingFromConfig(ROTATABLE, "rotatable");
        settingFromConfig(TYPE, "type");
        settingFromConfig(BISECTED, "bisected");
        settingFromConfig(SHAPE, "shape");
        settingFromConfig(PERSISTENT, "persistent");
        settingFromConfig(MULTIPLEFACING, "multipleFacing");
        settingFromConfig(LIGHTABLE, "lightable");
        settingFromConfig(REDSTONEWIRE, "redstoneWire");
        settingFromConfig(WATERLOGGED, "waterlogged");
        settingFromConfig(POWERABLE, "powerable");
        settingFromConfig(AGEABLE, "ageable");
        settingFromConfig(SAPLING, "sapling");
        settingFromConfig(BEEHIVE, "beehive");

    }

    private static void settingFromConfig(Durability d, String name) {
        String path = "settings.durability.";
        int dura = ProtectedDebugStick.config.getInt(path + name);
        if (dura < 0) {
            Utils.log("Cannot set the durability of " + name + " below 0. Setting by default.", Utils.LOG_WARNING);
            return;
        }
        d.durability = dura;
    }
}
