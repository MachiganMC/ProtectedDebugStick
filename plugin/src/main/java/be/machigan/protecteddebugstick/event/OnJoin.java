package be.machigan.protecteddebugstick.event;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.Tools;
import be.machigan.protecteddebugstick.utils.VersionChecker;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener {
    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        String currentVersion = ProtectedDebugStick.getInstance().getDescription().getVersion();
        String spigotVersion = VersionChecker.getSpigotVersion();
        if (spigotVersion == null)return;
        if (spigotVersion.equals(currentVersion))return;
        if (!e.getPlayer().hasPermission("pds.version"))return;

        String lang = Config.getConfig().getString("Lang");
        if (lang == null)
            lang = "en";
        String message = switch (lang.toLowerCase()) {
            case "fr" -> "&aVotre serveur tourne avec une vielle version de &eProtected DebugStick &7(&c" + currentVersion + " &e-> &a" + spigotVersion + "&7)";
            default ->  "&aYou're running on an old version of &eProtected DebugStick &7(&c" + currentVersion + " &e-> &a " + spigotVersion + "&7)";
        };
        BaseComponent[] text = Tools.replaceColor(new TextComponent(message));
        for (BaseComponent c : text) {
            c.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/protected-debug-stick.102630/"));
        }
        e.getPlayer().spigot().sendMessage(text);
    }
}
