package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.command.CommandPDS;
import be.machigan.protecteddebugstick.command.TabPDS;
import be.machigan.protecteddebugstick.event.OnClickInspector;
import be.machigan.protecteddebugstick.event.OnUpdate;
import be.machigan.protecteddebugstick.event.OnUse;
import be.machigan.protecteddebugstick.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ProtectedDebugStick extends JavaPlugin {
    private static ProtectedDebugStick instance;

    @Override
    public void onEnable() {
        instance = this;

        try {
            Config.reload();
        } catch (InvalidConfigurationException e) {
            if (Config.Item.BASIC.getConfigSection() == null)
                this.getLogger().severe("The configuration of BasicDebugStick cannot be found. Disabling the plugin");
            if (Config.Item.INFINITY.getConfigSection() == null)
                this.getLogger().severe("The configuration of InfinityDebugStick cannot be found. Disabling the plugin");
            if (Config.Item.INSPECTOR.getConfigSection() == null)
                this.getLogger().severe("The configuration of Inspector cannot be found. Disabling the plugin");

            ProtectedDebugStick.getInstance().getPluginLoader().disablePlugin(ProtectedDebugStick.getInstance());
            return;
        }

        getServer().getPluginManager().registerEvents(new OnUse(), this);
        getServer().getPluginManager().registerEvents(new OnClickInspector(), this);
        getServer().getPluginManager().registerEvents(new OnUpdate(), this);
        getCommand("pds").setExecutor(new CommandPDS());
        getCommand("pds").setTabCompleter(new TabPDS());

        File file = new File(ProtectedDebugStick.getInstance().getDataFolder() + "/messages.yml");
        if (!file.exists()) {
            this.getLogger().info("Generating \"messages.yml\" ...");
            ProtectedDebugStick.getInstance().saveResource("messages.yml", false);
        }
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
}
