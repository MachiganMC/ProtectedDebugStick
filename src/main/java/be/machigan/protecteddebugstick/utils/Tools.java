package be.machigan.protecteddebugstick.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

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
}
