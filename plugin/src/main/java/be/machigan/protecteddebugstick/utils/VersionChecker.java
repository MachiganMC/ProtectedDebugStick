package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class VersionChecker {
    private static final String PLUGIN_URL = "https://api.spigotmc.org/legacy/update.php?resource=102630/~";
    private static String spigotVersion;

    public static void checkVersion() {
        Bukkit.getScheduler().runTaskAsynchronously(ProtectedDebugStick.getInstance(), () -> {
            spigotVersion = getLatestVersion();
            if (spigotVersion == null) {
                ProtectedDebugStick.getInstance().getLogger().warning("Unable to fetch for plugin update");
            } else {
                if (!spigotVersion.equals(ProtectedDebugStick.getInstance().getDescription().getVersion())) {
                    ProtectedDebugStick.getInstance().getLogger().info("There is a new version of the plugin !");
                }
            }
        });
    }

    private static String getLatestVersion() {
        try (
                InputStream is = new URL(PLUGIN_URL).openStream();
                Scanner scanner = new Scanner(is);
        ) {
            if (scanner.hasNext())
                return scanner.next();
        } catch (IOException ignore) {}
        return null;
    }

    public static String getSpigotVersion() {
        return spigotVersion;
    }
}
