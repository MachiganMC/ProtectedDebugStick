package be.machigan.protecteddebugstick.utils;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.lang3.StringUtils;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

public class Utils {
    final public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    final public static String LOG_INFO = "info";
    final public static String LOG_WARNING = "warning";
    final public static String LOG_SEVERE = "severe";


    /**
     * Transform your text into a colored text
     * @param text text you want to color
     * @return the text that bill be colored
     */
    public static String replaceColor(String text) {
        String b1;
        String b2;
        String w;
        while (true) {
            b1 = StringUtils.substringBetween(text, "<s:", ">");
            if (b1 == null) {
                break;
            }
            b2 = StringUtils.substringBetween(text, "<e:", ">");
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
            String colored = Utils.gradient(copy, b1, b2, subS.toString());
            text = text.replace("<s:" + b1 + ">" + w + "<e:" + b2 + ">", colored);
        }

        return text.replace("&0", "" + ChatColor.BLACK)
                .replace("&1", "" + ChatColor.DARK_BLUE)
                .replace("&2", "" + ChatColor.DARK_GREEN)
                .replace("&3", "" + ChatColor.DARK_AQUA)
                .replace("&4", "" + ChatColor.DARK_RED)
                .replace("&5", "" + ChatColor.DARK_PURPLE)
                .replace("&6", "" + ChatColor.GOLD)
                .replace("&7", "" + ChatColor.GRAY)
                .replace("&8", "" + ChatColor.DARK_GRAY)
                .replace("&9", "" + ChatColor.BLUE)
                .replace("&a", "" + ChatColor.GREEN)
                .replace("&b", "" + ChatColor.AQUA)
                .replace("&c", "" + ChatColor.RED)
                .replace("&d", "" + ChatColor.LIGHT_PURPLE)
                .replace("&e", "" + ChatColor.YELLOW)
                .replace("&f", "" + ChatColor.WHITE)
                .replace("&k", "" + ChatColor.MAGIC)
                .replace("&l", "" + ChatColor.BOLD)
                .replace("&m", "" + ChatColor.STRIKETHROUGH)
                .replace("&n", "" + ChatColor.UNDERLINE)
                .replace("&o", "" + ChatColor.ITALIC)
                .replace("&r", "" + ChatColor.RESET);
    }


    /**
     * Generates a gradient with 2 colors and apply this gradient at each char of the message you want
     * to color
     * @param message the message you want to color
     * @param from the fist RGB color code
     * @param to the second RGB color code
     * @param subS a string that you input between the hex color and your char (example: a format code)
     * @return the message colorized with the gradient
     */
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
            return sb.toString();

        } catch (IllegalArgumentException ignored) {
            return message;
        }
    }


    /**
     * Generates a gradient with 2 colors and apply this gradient at each char of the message you want
     * to color
     * @param message the message you want to color
     * @param from the fist RGB color code
     * @param to the second RGB color code
     * @return the message colorized with the gradient
     */
    public static String gradient(String message, String from, String to) {
        return gradient(message, from, to, "");
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
     * @throws java.text.ParseException If the format of the Date is bad
     */
    public static Date convertLocalDateTime(LocalDateTime ldt) throws java.text.ParseException {
        String dateS = ldt.getYear() + "-" + ldt.getMonthValue() + "-" + ldt.getDayOfMonth() +
                " " + ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond();

        return DATE_FORMAT.parse(dateS);
    }


    /**
     * Converts a LocalDate object into a Date object
     * @param ld The local date object you want to convert
     * @return The local date object converted into Date object
     * @throws java.text.ParseException If the format of the Date is bad
     */
    public static Date convertLocalDate(LocalDate ld) throws java.text.ParseException {
        String dateS = ld.getYear() + "-" + ld.getMonthValue() + "-" + ld.getDayOfMonth() + " 00:00:00";
        return DATE_FORMAT.parse(dateS);
    }


    /**
     * Search in the config file a string to color
     * @param path The path in the config to search
     * @return The string you want in the config
     */
    public static String configColor(String path) {
        try {
            return replaceColor(ProtectedDebugStick.instance.getConfig().getString(path));
        } catch (NullPointerException ignored) {
            log("Warning, the field \"" + path + "\" is empty in the configuration file", Utils.LOG_SEVERE);
            return Utils.replaceColor(ProtectedDebugStick.PREFIX + "&4Missing section &c&o" + path + " &4in the configuration file");
        }
    }


    /**
     * Make console execute a command
     * @param command The command you to execute
     */
    public void sudo(String command) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(ProtectedDebugStick.instance, () -> Bukkit.getServer().dispatchCommand(
                Bukkit.getServer().getConsoleSender(), command), 0);
    }


    /**
     * Log into the console a message
     * @param message The message you want to log
     * @param logType The type of log
     */
    public static void log(String message, String logType) {
        Logger log = Logger.getLogger("Minecraft");
        switch (logType) {
            case Utils.LOG_INFO:
                log.info(ProtectedDebugStick.NAME + " " + message);
                return;
            case Utils.LOG_WARNING:
                log.warning(ProtectedDebugStick.NAME + " " + message);
                return;
            case Utils.LOG_SEVERE:
                log.severe(ProtectedDebugStick.NAME + " " + message);

        }
    }


    /**
     * The type of the log will always be "info"
     * @param message message you want to log
     */
    public static void log(String message) {
        log(message, Utils.LOG_INFO);
    }

}
