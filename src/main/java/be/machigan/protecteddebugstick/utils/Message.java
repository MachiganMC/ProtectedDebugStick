package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class Message {
    final public static File FILE = new File(ProtectedDebugStick.getInstance().getDataFolder(), "/messages.yml");
    private static YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(FILE);
    private String path;
    private TextComponent content;
    @Nullable private String hoverContent;
    private ClickEvent clickEvent;
    private BaseComponent[] finalContent;
    private Content finalHoverContent;

    private Message() {}


    public static Message getMessage(String path, Player player) {
        configMessages = YamlConfiguration.loadConfiguration(new File(ProtectedDebugStick.getInstance().getDataFolder(), "/messages.yml"));
        Message message = new Message();
        message.path = path;
        message = message.hover(player);
        try {
            String s = configMessages.getString(path);
            s = PlaceholderAPI.setPlaceholders(player, s);
            message.content = new TextComponent(s.replace("{prefix}", ProtectedDebugStick.prefix));
        } catch (NullPointerException e) {
            ProtectedDebugStick.getInstance().getLogger().warning("The message from \"" + path + "\" doesn't exist.");
            message.content = new TextComponent(Tools.replaceColor("&3[&6&lProtected&e-DS&3] &cMessage &4&o" + path + " &cnot found"));
        }
        return message;
    }

    public static Message getMessage(String path, OfflinePlayer player) {
        configMessages = YamlConfiguration.loadConfiguration(new File(ProtectedDebugStick.getInstance().getDataFolder(), "/messages.yml"));
        Message message = new Message();
        message.path = path;
        message = message.hover(player);
        try {
            String s = configMessages.getString(path);
            s = PlaceholderAPI.setPlaceholders(player, s);
            message.content = new TextComponent(s.replace("{prefix}", ProtectedDebugStick.prefix));
        } catch (NullPointerException e) {
            ProtectedDebugStick.getInstance().getLogger().warning("The message from \"" + path + "\" doesn't exist.");
            message.content = new TextComponent(Tools.replaceColor("&3[&6&lProtected&e-DS&3] &cMessage &4&o" + path + " &cnot found"));
        }
        return message;
    }

    public static Message getMessage(String path) {
        return getMessage(path, null);
    }

    private Message hover(Player player) {
        try {
            this.hoverContent = configMessages.getString(this.path + "Hover")
                    .replace("{prefix}", ProtectedDebugStick.prefix);
            this.hoverContent = PlaceholderAPI.setPlaceholders(player, this.hoverContent);
        } catch (NullPointerException ignored) {}
        return this;
    }

    private Message hover(OfflinePlayer player) {
        try {
            this.hoverContent = configMessages.getString(this.path + "Hover")
                    .replace("{prefix}", ProtectedDebugStick.prefix);
            this.hoverContent = PlaceholderAPI.setPlaceholders(player, this.hoverContent);
        } catch (NullPointerException ignored) {}
        return this;
    }

    public Message replace(String from, String to) {
        this.content = new TextComponent(this.content.getText().replace(from, to));
        if (this.hoverContent != null) {
            this.hoverContent = this.hoverContent.replace(from, to);
        }
        return this;
    }

    public Message click(String text, ClickEvent.Action action) {
        this.clickEvent = new ClickEvent(action, text);
        return this;
    }

    private void color() {
        this.finalContent = Tools.replaceColor(this.content);
        if (this.hoverContent != null)
            this.finalHoverContent = new Text(Tools.replaceColor(new TextComponent(this.hoverContent)));
    }

    public void send(Player player) {
        this.color();
        if (this.hoverContent != null) {
            for (BaseComponent b : this.finalContent) {

                b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.finalHoverContent));
            }
        }
        if (this.clickEvent != null) {
            for (BaseComponent b : this.finalContent) {
                b.setClickEvent(this.clickEvent);
            }
        }
        player.spigot().sendMessage(this.finalContent);
    }

    public void send(CommandSender sender) {
        if (sender instanceof Player) {
            this.send((Player) sender);
            return;
        }
        this.console();
    }

    public void broadcast() {
        this.color();
        if (this.hoverContent != null) {
            for (BaseComponent b : this.finalContent) {
                b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, this.finalHoverContent));
            }
        }
        if (this.clickEvent != null) {
            for (BaseComponent b : this.finalContent) {
                b.setClickEvent(this.clickEvent);
            }
        }
        Bukkit.getServer().spigot().broadcast(this.finalContent);
    }

    public void console() {
        Bukkit.getConsoleSender().sendMessage(Tools.replaceColor(this.content.getText()));
    }
}
