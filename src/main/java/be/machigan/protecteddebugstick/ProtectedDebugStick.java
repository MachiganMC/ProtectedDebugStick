package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.command.CommandPDS;
import be.machigan.protecteddebugstick.command.TabPDS;
import be.machigan.protecteddebugstick.def.RecipeHandler;
import be.machigan.protecteddebugstick.event.*;
import be.machigan.protecteddebugstick.utils.Tools;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectedDebugStick extends JavaPlugin {
    private static ProtectedDebugStick instance;
    final public static String NAME = "[Protected-DS]";
    final public static String PREFIX = Tools.replaceColor("&3[&6&lProtected&e-DS&3]&r ");
    public static FileConfiguration config;
    public static String prefix;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        config = this.getConfig();
        getServer().getPluginManager().registerEvents(new OnUse(), this);
        getServer().getPluginManager().registerEvents(new OnClickInspector(), this);
        getCommand("pds").setExecutor(new CommandPDS());
        getCommand("pds").setTabCompleter(new TabPDS());

        try {
            ProtectedDebugStick.prefix = Tools.replaceColor(ProtectedDebugStick.config.getString("prefix"));
        } catch (NullPointerException ignored) {
            prefix = Tools.replaceColor(ProtectedDebugStick.PREFIX);
        }

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
