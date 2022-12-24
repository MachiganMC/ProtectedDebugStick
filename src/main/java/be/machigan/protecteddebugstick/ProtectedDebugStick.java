package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.command.CommandPDS;
import be.machigan.protecteddebugstick.command.TabPDS;
import be.machigan.protecteddebugstick.def.RecipeHandler;
import be.machigan.protecteddebugstick.event.OnClickInspector;
import be.machigan.protecteddebugstick.event.OnUse;
import be.machigan.protecteddebugstick.utils.Tools;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ProtectedDebugStick extends JavaPlugin {
    private static ProtectedDebugStick instance;
    final public static String NAME = "[Protected-DS]";
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        config = this.getConfig();
        getServer().getPluginManager().registerEvents(new OnUse(), this);
        getServer().getPluginManager().registerEvents(new OnClickInspector(), this);
        getCommand("pds").setExecutor(new CommandPDS());
        getCommand("pds").setTabCompleter(new TabPDS());

        File file = new File(ProtectedDebugStick.getInstance().getDataFolder() + "/messages.yml");
        if (!file.exists())
            ProtectedDebugStick.getInstance().saveResource("messages.yml", false);

        RecipeHandler.register();
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
