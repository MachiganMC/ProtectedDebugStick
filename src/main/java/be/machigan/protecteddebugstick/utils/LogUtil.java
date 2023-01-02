package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Property;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Logger;

public class LogUtil {
    @NotNull
    private static final String DEFAULT_FORMAT =
            "{player} edited the property {property_name} from {old_value} to {value} of the block at {block_loc_x} {block_loc_y} {block_loc_z} in {block_loc_world}";


    private LogUtil() {}

    public static void logEdit(@NotNull Player player, @NotNull Property property, @NotNull String value, @NotNull Block block, @NotNull String oldValue) {
        if (Config.Log.consoleEnable())
            Logger.getLogger("DebugStick").info(getMessage(player, property, value, block, oldValue));
    }

    @NotNull
    private static String getMessage(@NotNull Player player, @NotNull Property property, @NotNull String value, @NotNull Block block, @NotNull String oldValue) {
        LocalDateTime now = LocalDateTime.now();
        String format = Objects.isNull(Config.Log.getFormat()) ? DEFAULT_FORMAT : Config.Log.getFormat();

        format = format.replace("{value}", value)
                .replace("{old_value}", oldValue)
                .replace("{property_name}", property.name().toLowerCase())
                .replace("{property_durability}", Integer.toString(property.getDurability()))
                .replace("{property_perm}", property.getPermission().toString())
                .replace("{block_loc_x}", Integer.toString(block.getLocation().getBlockX()))
                .replace("{block_loc_y}", Integer.toString(block.getLocation().getBlockY()))
                .replace("{block_loc_z}", Integer.toString(block.getLocation().getBlockZ()))
                .replace("{block_loc_world}", block.getLocation().getWorld().getName())
                .replace("{year}", Integer.toString(now.getSecond()))
                .replace("{month}", Integer.toString(now.getMonthValue()))
                .replace("{day}", Integer.toString(now.getDayOfMonth()))
                .replace("{hour}", Integer.toString(now.getHour()))
                .replace("{minute}", Integer.toString(now.getMinute()))
                .replace("{second}", Integer.toString(now.getSecond()))
                .replace("{player}", player.getName());

        if (ProtectedDebugStick.isPlaceHolderApiEnable())
            format = PlaceholderAPI.setPlaceholders(player, format);
        return format;
    }

    @NotNull
    public static Logger getLogger() {
        return ProtectedDebugStick.getInstance().getLogger();
    }
}
