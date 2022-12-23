package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.property.Property;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.File;

public class Message {
    private static YamlConfiguration messagesFile;
    private String path;

    /**
     * Represents the content of the message, the text that the player will see.
     * If this attribute is {@code null}, it's because the message isn't necessary and so
     * the message hasn't been configured.
     */
    @Nullable private String content;
    @Nullable private String hoverContent;
    private BaseComponent[] finalContent;
    @Nullable private String runCommand;
    @Nullable private String suggestedCommand;
    @Nullable private String hotbarContent;
    @Nullable private BaseComponent[] finalHotbarContent;
    boolean necessary = true;

    private Message() {}


    public static Message getMessage(String path, Player player, boolean necessary) {
        messagesFile = YamlConfiguration.loadConfiguration(new File(ProtectedDebugStick.getInstance().getDataFolder(), "/messages.yml"));
        Message message = new Message();
        message.path = path;
        message.additionalContent(player);
        message.necessary = necessary;

        try {
            message.content = messagesFile.getString(path);
            message.content = PlaceholderAPI.setPlaceholders(player, message.content);
            message.content = message.content.replace("{prefix}", ProtectedDebugStick.prefix);
        } catch (NullPointerException e) {
            if (message.necessary) {
                ProtectedDebugStick.getInstance().getLogger().warning("The message from \"" + path + "\" doesn't exist.");
                message.content = Tools.replaceColor("&3[&6&lProtected&e-DS&3] &cMessage &4&o" + path + " &cnot found");
            }
        }
        return message;
    }

    public static Message getMessage(String path, boolean necessary) {
        return getMessage(path, null, necessary);
    }

    public static Message getMessage(String path) {
        return getMessage(path, null, true);
    }

    private void additionalContent(Player player) {
        try {
            this.hoverContent = messagesFile.getString(this.path + "Hover")
                    .replace("{prefix}", ProtectedDebugStick.prefix);
            this.hoverContent = PlaceholderAPI.setPlaceholders(player, this.hoverContent);
        } catch (NullPointerException ignored) {}

        try {
            this.runCommand = messagesFile.getString(this.path + "Run");
            this.runCommand = PlaceholderAPI.setPlaceholders(player, this.runCommand);
        } catch (NullPointerException ignored) {}

        try {
            this.suggestedCommand = messagesFile.getString(this.path + "Suggest");
            this.suggestedCommand = PlaceholderAPI.setPlaceholders(player, this.suggestedCommand);
        } catch (NullPointerException ignored) {}

        try {
            this.hotbarContent = messagesFile.getString(this.path + "Hotbar")
                    .replace("{prefix}", ProtectedDebugStick.prefix);
            this.hotbarContent = PlaceholderAPI.setPlaceholders(player, this.hotbarContent);
        } catch (NullPointerException ignored) {}
    }

    private void additionalContent(OfflinePlayer player) {
        try {
            this.hoverContent = messagesFile.getString(this.path + "Hover")
                    .replace("{prefix}", ProtectedDebugStick.prefix);
            this.hoverContent = PlaceholderAPI.setPlaceholders(player, this.hoverContent);
        } catch (NullPointerException ignored) {}

        try {
            this.runCommand = messagesFile.getString(this.path + "Run");
            this.runCommand = PlaceholderAPI.setPlaceholders(player, this.runCommand);
        } catch (NullPointerException ignored) {}

        try {
            this.suggestedCommand = messagesFile.getString(this.path + "Suggest");
            this.suggestedCommand = PlaceholderAPI.setPlaceholders(player, this.suggestedCommand);
        } catch (NullPointerException ignored) {}

        try {
            this.hotbarContent = messagesFile.getString(this.path + "Hotbar")
                    .replace("{prefix}", ProtectedDebugStick.prefix);
            this.hotbarContent = PlaceholderAPI.setPlaceholders(player, this.hotbarContent);
        } catch (NullPointerException ignored) {}
    }


    public Message replace(String from, String to) {
        if (this.hotbarContent != null)
            this.hotbarContent = this.hotbarContent.replace(from, to);

        if (this.content == null)
            return this;

        this.content = this.content.replace(from, to);

        if (this.hoverContent != null)
            this.hoverContent = this.hoverContent.replace(from, to);

        return this;
    }

    public Message replace(Block block) {
        return this.replace("{block_material}", block.getType().name())
                .replace("{block_loc_x}", Integer.toString(block.getLocation().getBlockX()))
                .replace("{block_loc_y}", Integer.toString(block.getLocation().getBlockY()))
                .replace("{block_loc_z}", Integer.toString(block.getLocation().getBlockZ()))
                .replace("{block_loc_world}", block.getLocation().getWorld().getName());
    }

    public Message replace(Property property) {
        return this.replace("{property_name}", property.name())
                .replace("{property_durability}", Integer.toString(property.getDurability()))
                .replace("{property_perm}", property.getPermission());
    }

    private void complete() {
        if (this.hotbarContent != null)
            this.finalHotbarContent = Tools.replaceColor(new TextComponent(this.hotbarContent));

        if (this.content == null)
            return;

        this.finalContent = Tools.replaceColor(new TextComponent(this.content));

        if (this.hoverContent != null) {
            Content finalHoverContent = new Text(Tools.replaceColor(new TextComponent(this.hoverContent)));
            for (BaseComponent b : this.finalContent)
                b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, finalHoverContent));
        }

        if (this.runCommand != null)
            for (BaseComponent bc : this.finalContent)
                bc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.runCommand));


        if (this.suggestedCommand != null) {
            for (BaseComponent bc : this.finalContent)
                bc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.suggestedCommand));
        }
    }

    public void send(Player player) {
        this.complete();

        if (this.finalHotbarContent != null)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, this.finalHotbarContent);

        if (this.content != null)
            player.spigot().sendMessage(this.finalContent);
    }

    public void send(CommandSender sender) {
        if (this.content == null)
            return;

        if (sender instanceof Player) {
            this.send((Player) sender);
            return;
        }
        this.console();
    }

    public void broadcast() {
        if (this.content == null)
            return;

        this.complete();

        Bukkit.getServer().spigot().broadcast(this.finalContent);
    }

    public void console() {
        if (this.content != null)
            Bukkit.getConsoleSender().sendMessage(Tools.replaceColor(this.content));
    }
}
