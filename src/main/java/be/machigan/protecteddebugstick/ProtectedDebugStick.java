package be.machigan.protecteddebugstick;

import be.machigan.protecteddebugstick.command.CommandPDS;
import be.machigan.protecteddebugstick.command.TabPDS;
import be.machigan.protecteddebugstick.def.RecipeHandler;
import be.machigan.protecteddebugstick.event.*;
import be.machigan.protecteddebugstick.utils.Utils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectedDebugStick extends JavaPlugin {
    public static ProtectedDebugStick instance;
    final public static String NAME = "[Protected-DS]";
    final public static String PREFIX = Utils.replaceColor("&3[&6&lProtected&e-DS&3]&r ");
    public static FileConfiguration config;
    public static String prefix;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        config = this.getConfig();

        getServer().getPluginManager().registerEvents(new ClickRotation(), this);
        getServer().getPluginManager().registerEvents(new ClickReversed(), this);
        getServer().getPluginManager().registerEvents(new OnClickInspector(), this);
        getServer().getPluginManager().registerEvents(new ClickSpecial(), this);
        getServer().getPluginManager().registerEvents(new ClickCreative(), this);
        getCommand("pds").setExecutor(new CommandPDS());
        getCommand("pds").setTabCompleter(new TabPDS());
        try {
            if (!GriefPrevention.instance.isEnabled()) {
                Utils.log("GriefPrevention not enabled", Utils.LOG_SEVERE);
                getPluginLoader().disablePlugin(this);
                return;
            }
            Utils.log("Link with GriefPrevention - OK");
        } catch (NoClassDefFoundError ignored) {
            Utils.log("GriefPrevention not found", Utils.LOG_SEVERE);
            getPluginLoader().disablePlugin(this);

        }
        try {
            ProtectedDebugStick.prefix = Utils.replaceColor(ProtectedDebugStick.config.getString("prefix"));
        } catch (NullPointerException ignored) {
            prefix = Utils.replaceColor(ProtectedDebugStick.PREFIX);
        }

        RecipeHandler.register();

        Utils.log("Enable");

    }

    @Override
    public void onDisable() {
        Utils.log("Disable");
    }








}
