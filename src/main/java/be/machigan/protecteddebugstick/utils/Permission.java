package be.machigan.protecteddebugstick.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Permission {
    public enum Command {
        USE("pds.command.use"),
        GIVE("pds.command.give"),
        RELOAD_CONFIG("pds.command.reload-config"),
        LOAD("pds.command.load");

        @NotNull
        private final String perm;
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

        @NotNull
        private final String perm;
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

        @NotNull
        private final String perm;
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
        ANALOGUE_POWERABLE("pds.property.analogue-powerable"),
        POWERABLE("pds.property.powerable"),
        AGEABLE("pds.property.ageable"),
        STAGE("pds.property.stage"),
        HONEY_LEVEL("pds.property.honey-level"),
        WALL("pds.property.wall"),
        SNOWABLE("pds.property.snowable"),
        DISTANCE("pds.property.distance"),
        LAYERS("pds.property.layers"),
        BAMBOO_LEAVES("pds.property.bamboo-leaves"),
        TILT("pds.property.til"),
        EGGS("pds.property.eggs"),
        HATCH("pds.property.hatch"),
        SIGNAL_FIRE("pds.property.signal-fire"),
        ATTACHABLE("pds.property.attachable"),
        EXTENDABLE("pds.property.extendable"),
        HANGABLE("pds.property.hangable"),
        CAKE("pds.property.cake"),
        BERRY("pds.property.berry"),
        CONDITIONAL("pds.property.conditional"),
        OPENABLE("pds.property.openable"),
        END_PORTAL_FRAME("pds.property.end-portal-frame"),
        FACE_ATTACHABLE("pds.property.face-attachable"),
        MOISTURE("pds.property.moisture"),
        GATE("pds.property.gate"),
        LEVELLED("pds.property.levelled"),
        THICNKESS("pds.property.thickness"),
        VERTICAL_DIRECTION("pds.property.vertical-direction"),
        LOCKED("pds.property.locked"),
        CHARGES("pds.property.charges"),
        BLOOM("pds.property.bloom"),
        PHASE("pds.property.phase"),
        SUMMON("pds.property.summon"),
        SHRIEKING("pds.property.shrieking"),
        PICKLES("pds.property.pickles"),
        DISARMED("pds.property.disarmed"),
        BRUSHABLE("pds.property.brushable"),
        OCCUPIED_SLOT("pds.property.occupied-slot"),
        PETALS("pds.property.petals");


        @NotNull
        private final String perm;
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

    public enum Chunk {
        INFO("pds.chunk.info"),
        CLEAR("pds.chunk.clear");

        @NotNull
        private final String perm;

        Chunk(@NotNull String perm) {
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
