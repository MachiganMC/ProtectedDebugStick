package be.machigan.protecteddebugstick.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Permission {
    public enum Command {
        USE("pds.command.use"),
        GIVE("pds.command.give"),
        RELOAD_CONFIG("pds.command.reload-config");

        @NotNull final private String perm;
        Command(@NotNull String perm) {
            this.perm = perm;
        }

        public boolean has(@NotNull CommandSender commandSender) {
            return commandSender.hasPermission(this.perm);
        }


        @Override
        public String toString() {
            return this.perm;
        }
    }


    public enum Item {
        BASIC_EDIT("pds.item.basic.edit"),
        BASIC_SEE("pds.item.basic.see"),
        INFINITE_EDIT("pds.item.infinite.edit"),
        INFINITE_SEE("pds.item.infinite.see"),
        INSPECTOR_IMPLEMENTATION("pds.item.inspector.implementation"),
        INSPECTOR_PROPERTIES("pds.item.inspector.properties");

        @NotNull final private String perm;
        Item(@NotNull String perm) {
            this.perm = perm;
        }

        public boolean has(@NotNull Player player) {
            return player.hasPermission(this.perm);
        }

        @Override
        public String toString() {
            return this.perm;
        }
    }


    public enum Bypass {
        PLUGIN_BLOCK("pds.bypass.plugin-block"),
        BLACKLIST_MATERIAL("pds.bypass.blacklist.material"),
        BLACKLIST_WORLD("pds.bypass.blacklist.world");

        @NotNull final private String perm;
        Bypass(@NotNull String perm) {
            this.perm = perm;
        }

        public boolean has(@NotNull Player player) {
            return player.hasPermission(this.perm);
        }

        @Override
        public String toString() {
            return this.perm;
        }
    }


    public enum Property {
        ORIENTABLE("pds.property.orientable"),
        DIRECTIONAL("pds.property.directional"),
        ROTATABLE("pds.property.rotatable"),
        SLAB("pds.property.slab"),
        BISECTED("pds.property.bisected"),
        SHAPE_STAIRS("pds.property.shape-stairs"),
        SHAPE_RAIL("pds.property.shape-rail"),
        PERSISTENT("pds.property.persistent"),
        MULTIPLE_FACING("pds.property.multiple-facing"),
        LIGHTABLE("pds.property.lightable"),
        REDSTONE_WIRE("pds.property.redstone-wire"),
        WATER_LOGGED("pds.property.water-logged"),
        POWERABLE("pds.property.powerable"),
        AGEABLE("pds.property.ageable"),
        SAPLING("pds.property.sapling"),
        BEEHIVE("pds.property.beehive"),
        WALL("pds.property.wall"),
        SNOWABLE("pds.property.snowable"),
        DISTANCE("pds.property.distance"),
        LAYERS("pds.property.layers");

        @NotNull final private String perm;
        Property(@NotNull String perm) {
            this.perm = perm;
        }

        public boolean has(@NotNull Player player) {
            return player.hasPermission(this.perm);
        }

        @Override
        public String toString() {
            return this.perm;
        }
    }
}
