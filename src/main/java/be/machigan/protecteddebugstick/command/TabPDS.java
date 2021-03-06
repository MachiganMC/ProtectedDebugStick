package be.machigan.protecteddebugstick.command;

import be.machigan.protecteddebugstick.def.DebugStick;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TabPDS implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> arg = new ArrayList<>();
        List<String> tab = new ArrayList<>();
        if (!commandSender.hasPermission("pds.command.use")) {
            return tab;
        }

        if (strings.length == 1) {
            if (commandSender.hasPermission("pds.command.give")) {
                arg.add("give");
            }
            if (commandSender.hasPermission("pds.command.reloadConfig")) {
                arg.add("reloadconfig");
            }
            StringUtil.copyPartialMatches(strings[0], arg, tab);
            return tab;
        }

        if (strings.length == 2) {
            if (strings[0].equalsIgnoreCase("give")) {
                return null;
            }
        }

        if (strings.length == 3) {
            if (strings[0].equalsIgnoreCase("give") && commandSender.hasPermission("pds.command.give")) {
                StringUtil.copyPartialMatches(strings[2], DebugStick.ITEMS, tab);
                return tab;
            }
        }

        if (strings.length == 4) {
            if (strings[0].equalsIgnoreCase("give") && strings[2].equalsIgnoreCase("basic")
                    && commandSender.hasPermission("pds.command.give")) {
                arg.add("5");
                arg.add("20");
                arg.add("50");
                arg.add("100");
                arg.add("500");
                arg.add("1000");
                StringUtil.copyPartialMatches(strings[3], arg, tab);
                return tab;
            }
        }



        return tab;
    }
}
