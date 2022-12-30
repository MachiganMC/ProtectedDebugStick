package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Log {
    @Nullable
    public static CoreProtectAPI getCoreProtect() {
        CoreProtectAPI coreProtect = null;
        try {
            Class.forName("net.coreprotect.CoreProtect");
            Plugin plugin = ProtectedDebugStick.getInstance().getServer().getPluginManager().getPlugin("CoreProtect");
            if (!(plugin instanceof CoreProtect))
                throw new ClassNotFoundException();
            coreProtect = ((CoreProtect) plugin).getAPI();
            if (coreProtect.APIVersion() < 9) {
                Tools.log("API version of CoreProtect not compatible. Disabling the log with CoreProtect", Tools.LOG_WARNING);
                Config.Log.disableCoreProtect();
            }
        } catch (ClassNotFoundException e) {
            Tools.log("CoreProtect not found ! Disabling the log with CoreProtect", Tools.LOG_WARNING);
            Config.Log.disableCoreProtect();
        }
        return coreProtect;
    }

    public static void logCoreProtect(@NotNull Player player, @NotNull Block block) {
        CoreProtectAPI coreProtect = getCoreProtect();
        if (coreProtect == null)
            return;

        Bukkit.getPlayer("Machigan").sendMessage("1)" + block.getBlockData().getAsString());
        coreProtect.logPlacement(player.getName(), block.getLocation(), block.getType(), block.getBlockData());
    }

    public static void request(int time, @NotNull List<String> players) {

        CoreProtectAPI coreProtect = getCoreProtect();
        if (coreProtect == null)
            return;

        List<String[]> resultL = coreProtect.performLookup(time, players, null, null,
            null, Arrays.asList(1, 0), 0, null);
        for (String[] value : resultL) {
            CoreProtectAPI.ParseResult result = coreProtect.parseResult(value);

            Bukkit.getPlayer("Machigan").sendMessage("2)" + result.getBlockData().getAsString());
        }
    }
}
