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
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Message {

    /**
     * The file that contains all the messages.
     */
    private static FileConfiguration messagesFile;


    /**
     * The path to the message in the {@link #messagesFile}.
     */
    private String path;

    /**
     * Represents the content of the message, the text that the player will see.
     * If this attribute is {@code null}, it's because the message isn't necessary and so
     * the message hasn't been configured.
     */
    @Nullable private String content;

    /**
     * Represents the content after all modifications (<i>colors, variables, hover and click contents</i>) and
     * ready to be sent.
     */
    private BaseComponent[] finalContent;

    /**
     * Represents the message that the player will see if he puts his mouse cursor on the message.
     * If the hoverContent is {@code null}, the {@link #finalContent} will not have a
     * {@link net.md_5.bungee.api.chat.HoverEvent.Action#SHOW_TEXT}.
     */
    @Nullable private String hoverContent;

    /**
     * The be.machigan.protecteddebugstickl.command that the player will execute if he clicks on the message.
     * If the runCommand is {@code null}, the {@link #finalContent} will not have a
     * {@link net.md_5.bungee.api.chat.ClickEvent.Action#RUN_COMMAND}.
     */
    @Nullable private String runCommand;

    /**
     * The be.machigan.protecteddebugstickl.command that the player will be suggested if he clicks on the message.
     * If the suggestCommand is {@code null}, the {@link #finalContent} will not have a
     * {@link net.md_5.bungee.api.chat.ClickEvent.Action#SUGGEST_COMMAND}.
     */
    @Nullable private String suggestedCommand;

    /**
     * The message that will be sent above the hotbar of the player.
     * An hotbarContent can exist even the {@link #content} is {@code null}.
     */
    @Nullable private String hotbarContent;

    /**
     * The {@link #hotbarContent} after all modifications (<i>colors and replacements</i>) and ready to be sent.
     */
    @Nullable private BaseComponent[] finalHotbarContent;

    /**
     * If the message is necessary and if the plugin don't find a message it will display this message :
     * <br>
     * <i>"&3[&6&lProtected&e-DS&3] &cMessage &4&o" + path + " &cnot found"</i>.
     */
    boolean isNecessary;

    private Message() {}


    /**
     * @param path The path to find the message
     * @param player The player that concern the Placeholder of PlaceHolderAPI
     * @param necessary If the message is necessary or not ({@link #isNecessary})
     * @return The message with its additional contents
     * @see #getMessage(String, boolean)
     * @see #getMessage(String)
     */
    @NotNull
    public static Message getMessage(@NotNull String path, @Nullable Player player, boolean necessary) {
        messagesFile = Config.Lang.getMessageFile();
        Message message = new Message();
        message.path = path;
        message.additionalContent(player);
        message.isNecessary = necessary;

        try {
            message.content = messagesFile.getString(path);
            if (ProtectedDebugStick.isPlaceHolderApiEnable())
                message.content = PlaceholderAPI.setPlaceholders(player, message.content);
        } catch (NullPointerException e) {
            if (message.isNecessary) {
                LogUtil.getLogger().warning("The message from \"" + path + "\" doesn't exist.");
                message.content = Tools.replaceColor("&3[&6&lProtected&e-DS&3] &cMessage &4&o" + path + " &cnot found");
            }
        }
        return message;
    }

    /**
     * In this type of message, there is no PlaceHolders that concern an online player.
     * @param path The path to find the message
     * @param necessary If the message is necessary or not ({@link #isNecessary})
     * @return The message with its additional contents
     * @see #getMessage(String, Player, boolean)
     * @see #getMessage(String)
     */
    @NotNull
    public static Message getMessage(@NotNull String path, boolean necessary) {
        return getMessage(path, null, necessary);
    }

    /**
     * In this type of message, there is no PlaceHolders that concern an online player and 
     * the default value of {@link #isNecessary} is {@code true}.
     * @param path The path to find the message
     * @return The message with its additional contents
     * @see #getMessage(String, boolean)
     * @see #getMessage(String, Player, boolean) 
     */
    @NotNull
    public static Message getMessage(@NotNull String path) {
        return getMessage(path, null, true);
    }


    /**
     * @param player If {@code not null}, the player that concerns a PlaceHolder from PAPI.
     *               If {@code null}, no PlaceHolder that concerns an online player.
     */
    private void additionalContent(@Nullable Player player) {
        boolean isPlaceHolderAPIEnable = ProtectedDebugStick.isPlaceHolderApiEnable();

        try {
            this.hoverContent = messagesFile.getString(this.path + "Hover");
            if (isPlaceHolderAPIEnable)
                this.hoverContent = PlaceholderAPI.setPlaceholders(player, this.hoverContent);
        } catch (NullPointerException ignored) {}

        try {
            this.runCommand = messagesFile.getString(this.path + "Run");
            if (isPlaceHolderAPIEnable)
                this.runCommand = PlaceholderAPI.setPlaceholders(player, this.runCommand);
        } catch (NullPointerException ignored) {}

        try {
            this.suggestedCommand = messagesFile.getString(this.path + "Suggest");
            if (isPlaceHolderAPIEnable)
                this.suggestedCommand = PlaceholderAPI.setPlaceholders(player, this.suggestedCommand);
        } catch (NullPointerException ignored) {}

        try {
            this.hotbarContent = messagesFile.getString(this.path + "Hotbar");
            if (isPlaceHolderAPIEnable)
                this.hotbarContent = PlaceholderAPI.setPlaceholders(player, this.hotbarContent);
        } catch (NullPointerException ignored) {}
    }


    /**
     * Replace a string in {@link #content}, {@link #hoverContent} and {@link #hotbarContent} to another.
     * @param from The string to replace
     * @param to The string that will replace
     * @return The message with its {@link #content}, {@link #hoverContent} and {@link #hotbarContent} replaced.
     * @see #replace(Block) 
     * @see #replace(Property) 
     */
    public Message replace(String from, String to) {
        if (this.hotbarContent != null)
            this.hotbarContent = this.hotbarContent.replace(from, to);

        if (this.content == null)
            return this;

        this.content = this.content.replace(from, to);

        if (this.hoverContent != null)
            this.hoverContent = this.hoverContent.replace(from, to);

        if (this.suggestedCommand != null)
            this.suggestedCommand = this.suggestedCommand.replace(from, to);

        if (this.runCommand != null)
            this.runCommand = this.runCommand.replace(from, to);

        return this;
    }

    /**
     * Replace in {@link #content}, {@link #hoverContent} and {@link #hotbarContent} :
     * <ol>
     *     <li>{block_material} : with the materiel of the block</li>
     *     <li>{block_loc_x} : with the pos X of the block</li>
     *     <li>{block_loc_y} : with the pos Y of the block</li>
     *     <li>{block_loc_z} : with the pos Z of the block</li>
    *     <li>{block_loc_world} : with the world name of the pos of the block</li>
     * </ol>
     * @param block The block that will provide the data
     * @return The message with {@link #content}, {@link #hoverContent} and {@link #hotbarContent} modified.
     * @see #replace(String, String) 
     * @see #replace(Property)
     */
    public Message replace(Block block) {
        return this.replace("{block_material}", block.getType().name().toLowerCase().replace("_", " "))
                .replace("{block_loc_x}", Integer.toString(block.getLocation().getBlockX()))
                .replace("{block_loc_y}", Integer.toString(block.getLocation().getBlockY()))
                .replace("{block_loc_z}", Integer.toString(block.getLocation().getBlockZ()))
                .replace("{block_loc_world}", block.getLocation().getWorld().getName());
    }

    /**
     * Replace in {@link #content}, {@link #hoverContent} and {@link #hotbarContent} :
     * <ol>
     *     <li>{property_name} : with the {@link Property#toString()}</li>
     *     <li>{property_durability} : with the {@link Property#getDurability()}</li>
     *     <li>{property_perm} : with the {@link Property#getPermission()}</li>
     *     <li>{block_loc_world} : with the world name of the pos of the block</li>
     * </ol>
     * @param property The property that will provide the data
     * @return The message with {@link #content}, {@link #hoverContent} and {@link #hotbarContent} modified.
     * @see #replace(String, String)
     * @see #replace(Block)
     */
    public Message replace(Property property) {
        return this.replace("{property_name}", property.toString().toLowerCase())
                .replace("{property_durability}", Integer.toString(property.getDurability()))
                .replace("{property_perm}", property.getPermission().toString());
    }

    public Message replace(@NotNull Permission.Bypass permission) {
        return this.replace("{perm}", permission.toString());
    }

    public Message replace(@NotNull Permission.Property permission) {
        return this.replace("{perm}", permission.toString());
    }

    public Message replace(@NotNull Permission.Item permission) {
        return this.replace("{perm}", permission.toString());
    }

    public Message replace(@NotNull Permission.Command permission) {
        return this.replace("{perm}", permission.toString());
    }


    /**
     * Finalize the message by colorized and replace personal variable
     * his {@link #content}, {@link #hoverContent} and {@link #hotbarContent}.
     * <br>
     * And apply the {@link #hoverContent}, {@link #runCommand} and {@link #suggestedCommand} to
     * {@link #finalContent}.
     */
    private void complete() {
        Map<String, String> personalVariables = new HashMap<>();
        Set<String> variables;
        ConfigurationSection variableSection = messagesFile.getConfigurationSection("PersonalVariable");
        if (variableSection == null)
            variables = new HashSet<>();
        else
            variables = variableSection.getKeys(false);

        for (String variable : variables) {
            String value = messagesFile.getString("PersonalVariable." + variable);
            if (value != null)
                personalVariables.put("{" + variable + "}", value);
        }

        personalVariables.forEach(this::replace);

        if (this.hotbarContent != null)
            this.finalHotbarContent = Tools.replaceColor(new TextComponent(this.hotbarContent));

        if (this.content == null)
            return;

        this.finalContent = Tools.replaceColor(new TextComponent(this.content));

        if (this.hoverContent != null) {
            Content finalHoverContent = new Text(Tools.replaceColor(new TextComponent(this.hoverContent)));
            for (BaseComponent b : this.finalContent) {
                b.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, finalHoverContent));
            }
        }

        if (this.runCommand != null)
            for (BaseComponent bc : this.finalContent)
                bc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.runCommand));


        if (this.suggestedCommand != null) {
            for (BaseComponent bc : this.finalContent)
                bc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, this.suggestedCommand));
        }
    }


    /**
     * Send the {@link #finalContent} and (<i>if it exists</i>) the {@link #finalHotbarContent} to the {@code player}
     * @param player The player that will receive the message.
     * @see #send(Player)               
     */
    public void send(Player player) {
        this.complete();

        if (this.finalHotbarContent != null)
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, this.finalHotbarContent);

        if (this.content != null)
            player.spigot().sendMessage(this.finalContent);
    }

    /**
     * Send the {@link #finalContent} and (<i>if it exists</i>) the {@link #finalHotbarContent} to the {@code player}
     * or if {@code send} isn't a player only the {@link #content}.
     * @param sender The commandSender that will receive the message.
     * @see #send(CommandSender)               
     */
    public void send(CommandSender sender) {
        if (this.content == null)
            return;

        if (sender instanceof Player player) {
            this.send(player);
            return;
        }
        Bukkit.getConsoleSender().sendMessage(Tools.replaceColor(this.content));
    }
}
