package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
    final public static String LOG_INFO = "info";
    final public static String LOG_WARNING = "warning";
    final public static String LOG_SEVERE = "severe";


    public static String replaceColor(String text) {
        String b1;
        String b2;
        String w;
        while (true) {
            b1 = org.apache.commons.lang.StringUtils.substringBetween(text, "<s:", ">");
            if (b1 == null) {
                break;
            }
            b2 = org.apache.commons.lang.StringUtils.substringBetween(text, "<e:", ">");
            if (b2 == null) {
                break;
            }
            w = StringUtils.substringBetween(text, "<s:" + b1 + ">", "<e:" + b2 + ">");
            if (w == null) {
                break;
            }
            String copy = w;
            StringBuilder subS = new StringBuilder();
            if (copy.contains("&l")) {
                subS.append(ChatColor.BOLD);
                copy = copy.replace("&l", "");

            }
            if (copy.contains("&o")) {
                subS.append(ChatColor.ITALIC);
                copy = copy.replace("&o", "");
            }
            if (copy.contains("&n")) {
                subS.append(ChatColor.UNDERLINE);
                copy = copy.replace("&n", "");
            }
            if (copy.contains("&m")) {
                subS.append(ChatColor.STRIKETHROUGH);
                copy = copy.replace("&m", "");
            }
            if (copy.contains("&k")) {
                subS.append(ChatColor.MAGIC);
                copy = copy.replace("&k", "");
            }
            String colored = Tools.gradient(copy, b1, b2, subS.toString());
            text = text.replace("<s:" + b1 + ">" + w + "<e:" + b2 + ">", colored);
        }

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String hex = text.substring(matcher.start(), matcher.end());
            text = text.replace(hex, net.md_5.bungee.api.ChatColor.of(hex) + "");
            matcher = pattern.matcher(text);
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }


    public static BaseComponent[] replaceColor(TextComponent text) {
        List<TextComponent> bc = new ArrayList<>();
        String s = Tools.replaceColor(text.getText());

        if (!Pattern.compile("§x(§[a-fA-F0-9]){6}").matcher(s).find()) {
            bc.add(new TextComponent(s));
        } else {
            int i = 0;
            Matcher matcher = Pattern.compile("§x(§[a-fA-F0-9]){6}").matcher(s);
            bc.add(new TextComponent(s.split("§x(§[a-fA-F0-9]){6}")[0]));
            while (matcher.find()) {
                i++;
                String color = s.substring(matcher.start(), matcher.end());
                color = color.replace("§x", "").replace("§", "");
                TextComponent message = new TextComponent(s.split("§x(§[a-fA-F0-9]){6}")[i]);
                try {
                    message.setColor(net.md_5.bungee.api.ChatColor.of("#" + color));
                } catch (IllegalArgumentException ignored) {}
                bc.add(message);
            }
        }
        return bc.toArray(new BaseComponent[0]);
    }


    public static String gradient(String message, String from, String to, String subS) {
        if (message == null) {
            return message;
        }
        if (from.length() != 6 || to.length() != 6) {
            return message;
        }

        String[] fromA = from.split("(?<=\\G..)");
        String[] toA = to.split("(?<=\\G..)");
        int[] step = new int[3];

        try {
            int[] current = {Integer.parseInt(fromA[0], 16), Integer.parseInt(fromA[1], 16), Integer.parseInt(fromA[2], 16)};
            step[0] = (Integer.parseInt(fromA[0], 16) - Integer.parseInt(toA[0], 16)) / message.length();
            step[1] = (Integer.parseInt(fromA[1], 16) - Integer.parseInt(toA[1], 16)) / message.length();
            step[2] = (Integer.parseInt(fromA[2], 16) - Integer.parseInt(toA[2], 16)) / message.length();

            StringBuilder sb = new StringBuilder();
            for (Character c : message.toCharArray()) {
                String hex0 = Integer.toHexString(current[0]);
                String hex1 = Integer.toHexString(current[1]);
                String hex2 = Integer.toHexString(current[2]);
                if (hex0.length() == 1) {
                    hex0 = "0" + hex0;
                }
                if (hex1.length() == 1) {
                    hex1 = "0" + hex1;
                }
                if (hex2.length() == 1) {
                    hex2 = "0" + hex2;
                }

                sb.append(net.md_5.bungee.api.ChatColor.of("#" + hex0 + hex1 + hex2)).append(subS).append(c);
                current[0] -= step[0];
                current[1] -= step[1];
                current[2] -= step[2];
            }
            return sb.append(ChatColor.RESET).toString();

        } catch (IllegalArgumentException ignored) {
            return message;
        }
    }


    /**
     * Gets the UUID of a player with his username
     * @param name the username you want to get UUID
     * @return the UUID of the player
     * @throws IOException if the UUID isn't found
     */
    public static String getUUID(String name) throws IOException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
        URLConnection uc = url.openConnection();
        BufferedReader bf = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = bf.readLine()) != null) {
            response.append(inputLine);
        }
        bf.close();

        if (response.toString().isEmpty()) {
            throw new IOException("Username not found");
        }
        JSONParser parser = new JSONParser();
        Object object;
        try {
            object = parser.parse(response.toString());
        } catch (org.json.simple.parser.ParseException ignored) {
            throw new IOException("Parse error");
        }
        JSONObject jo = (JSONObject) object;
        String str = (String) jo.get("id");
        return str.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
    }


    /**
     * Gets a head with a custom texture
     * @param value The link of the texture head to get
     * @return The head with the texture
     */
    public static ItemStack getCustomTextureHead(String value) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            return head;
        }
        head.setItemMeta(meta);
        return head;
    }


    /**
     * Converts a LocalDateTime object into a Date object
     * @param ldt The local date time object you want to convert
     * @return The local date time object converted
     */
    public static Date convertLocalDateTime(LocalDateTime ldt) {
        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * Make console execute a command
     * @param command The command you to execute
     */
    public void sudo(String command) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(ProtectedDebugStick.getInstance(), () -> Bukkit.getServer().dispatchCommand(
                Bukkit.getServer().getConsoleSender(), command), 0);
    }


//    /**
//     * Log into the console a message
//     * @param message The message you want to log
//     * @param logType The type of log
//     */
//    public static void log(String message, String logType) {
//        Logger log = Logger.getLogger("Minecraft");
//        switch (logType) {
//            case Tools.LOG_INFO:
//                log.info(ProtectedDebugStick.NAME + " " + message);
//                return;
//            case Tools.LOG_WARNING:
//                log.warning(ProtectedDebugStick.NAME + " " + message);
//                return;
//            case Tools.LOG_SEVERE:
//                log.severe(ProtectedDebugStick.NAME + " " + message);
//
//        }
//    }
//
//
//    /**
//     * The type of the log will always be "info"
//     * @param message message you want to log
//     */
//    public static void log(String message) {
//        log(message, Tools.LOG_INFO);
//    }

}
