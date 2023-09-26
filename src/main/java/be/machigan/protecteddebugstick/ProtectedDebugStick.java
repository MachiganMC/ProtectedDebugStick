package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.command.CommandPDS;
import be.machigan.protecteddebugstick.command.TabPDS;
import be.machigan.protecteddebugstick.event.OnClickInspector;
import be.machigan.protecteddebugstick.event.OnUpdate;
import be.machigan.protecteddebugstick.event.OnUse;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.LogUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.logging.Level;

public class ProtectedDebugStick extends JavaPlugin {
    private static ProtectedDebugStick instance;

    @Override
    public void onEnable() {
        instance = this;

        try {
            Config.checkReload();
        } catch (InvalidConfigurationException e) {
            ProtectedDebugStick.disable();
            return;
        }

        getServer().getPluginManager().registerEvents(new OnUse(), this);
        getServer().getPluginManager().registerEvents(new OnClickInspector(), this);
        getServer().getPluginManager().registerEvents(new OnUpdate(), this);
        getCommand("pds").setExecutor(new CommandPDS());
        getCommand("pds").setTabCompleter(new TabPDS());

        ProtectedDebugStick.generateFileIfNotExist("lang/messages_en.yml");
        ProtectedDebugStick.generateFileIfNotExist("lang/messages_fr.yml");
    }

    public static ProtectedDebugStick getInstance() {
        return instance;
    }

    public static boolean isPlaceHolderApiEnable() {
        try {
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)
                throw new ClassNotFoundException();

            Class.forName("me.clip.placeholderapi.PlaceholderAPI");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void disable() {
        ProtectedDebugStick.instance.getPluginLoader().disablePlugin(ProtectedDebugStick.instance);
    }

    public static void generateFileIfNotExist(@NotNull String fileName) {
        File file = new File(ProtectedDebugStick.getInstance().getDataFolder(), fileName);
        if (!file.exists()) {
            LogUtil.getLogger().log(Level.INFO, "Generating \"{0}\" ...", fileName);
            ProtectedDebugStick.getInstance().saveResource(fileName, false);
        }
    }
}
