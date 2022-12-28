package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.command.CommandPDS;
import be.machigan.protecteddebugstick.command.TabPDS;
import be.machigan.protecteddebugstick.event.OnClickInspector;
import be.machigan.protecteddebugstick.event.OnUpdate;
import be.machigan.protecteddebugstick.event.OnUse;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Tools;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ProtectedDebugStick extends JavaPlugin {
    private static ProtectedDebugStick instance;
    final public static String NAME = "[Protected-DS]";

    @Override
    public void onEnable() {

        instance = this;
        try {
            Config.reload();
        } catch (InvalidConfigurationException e) {
            if (Config.Item.BASIC.getConfigSection() == null)
                Tools.log("The configuration of BasicDebugStick cannot be found. Disabling the plugin", Tools.LOG_SEVERE);
            if (Config.Item.INFINITY.getConfigSection() == null)
                Tools.log("The configuration of InfinityDebugStick cannot be found. Disabling the plugin", Tools.LOG_SEVERE);
            if (Config.Item.INSPECTOR.getConfigSection() == null)
                Tools.log("The configuration of Inspector cannot be found. Disabling the plugin", Tools.LOG_SEVERE);

            ProtectedDebugStick.getInstance().getPluginLoader().disablePlugin(ProtectedDebugStick.getInstance());
            return;
        }

        getServer().getPluginManager().registerEvents(new OnUse(), this);
        getServer().getPluginManager().registerEvents(new OnClickInspector(), this);
        getServer().getPluginManager().registerEvents(new OnUpdate(), this);
        getCommand("pds").setExecutor(new CommandPDS());
        getCommand("pds").setTabCompleter(new TabPDS());

        File file = new File(ProtectedDebugStick.getInstance().getDataFolder() + "/messages.yml");
        if (!file.exists())
            ProtectedDebugStick.getInstance().saveResource("messages.yml", false);

        Tools.log("Enabled");
    }

    public static ProtectedDebugStick getInstance() {
        return instance;
    }


    @Override
    public void onDisable() {
        Tools.log("Disable");
    }
}
