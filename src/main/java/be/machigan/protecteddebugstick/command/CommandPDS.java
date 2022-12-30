package be.machigan.protecteddebugstick.command;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


public class CommandPDS implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!Permission.Command.USE.has(commandSender)) {
            Player player = (Player) commandSender;
            Message.getMessage("Command.PDS.NoPerm", (Player) commandSender, false)
                    .replace(Permission.Command.USE)
                    .send(player);
            return true;
        }
        if (strings.length == 0) {
            Message.getMessage("Command.PDS.NotEnoughArg", true)
                    .send(commandSender);
            return true;
        }

        if (strings[0].equalsIgnoreCase("give")) {
            if (!Permission.Command.GIVE.has(commandSender)) {
                Player player = (Player) commandSender;
                Message.getMessage("Command.PDS.Arg.Give.NoPerm", player, false)
                        .replace(Permission.Command.GIVE)
                        .send(player);
                return true;
            }

            if (strings.length < 2) {
                Message.getMessage("Command.PDS.Arg.Give.NoPlayer")
                        .send(commandSender);
                return true;
            }

            Player player = Bukkit.getPlayer(strings[1]);
            if (player == null) {
                Message.getMessage("Command.PDS.Arg.Give.UnknownPlayer")
                        .replace("{player}", strings[1])
                        .send(commandSender);
                return true;
            }
            if (strings.length < 3) {
                Message.getMessage("Command.PDS.Arg.Give.WhatToGive")
                        .replace("{player}", player.getName())
                        .send(commandSender);
                return true;
            }

            switch (strings[2].toLowerCase()) {
                case "basic":
                    if (strings.length < 4) {
                        Message.getMessage("Command.PDS.Arg.Give.NoDurability")
                                .replace("{player}", player.getName())
                                .send(commandSender);
                        return true;
                    }
                    try {
                        int durability = Integer.parseInt(strings[3]);
                        if (durability <= 0) {
                            throw new NumberFormatException("Durability below or equal to 0");
                        }
                        player.getInventory().addItem(DebugStick.getBasicDebugStick(durability));
                        break;
                    } catch (NumberFormatException ignored) {
                        Message.getMessage("Command.PDS.Arg.Give.InvalidDurability")
                                .replace("{player}", player.getName())
                                .replace("{durability}", strings[3])
                                .send(commandSender);
                        return true;
                    }

                case "infinity":
                    player.getInventory().addItem(DebugStick.getInfiniteDebugStick());
                    break;

                case "inspector":
                    player.getInventory().addItem(DebugStick.getInspector());
                    break;

                default:
                    Message.getMessage("Command.PDS.Arg.Give.UnknownItem")
                            .replace("{player}", player.getName())
                            .replace("{item}", strings[2])
                            .send(commandSender);
                    return true;
            }
            Message.getMessage("Command.PDS.Arg.Give.Success")
                    .replace("{player}", player.getName())
                    .replace("{item}", strings[2])
                    .send(commandSender);
            return true;

        }

        if (strings[0].equalsIgnoreCase("reload-config")) {
            if (!Permission.Command.RELOAD_CONFIG.has(commandSender)) {
                Player player = (Player) commandSender;
                Message.getMessage("Command.PDS.Arg.ReloadConfig.NoPerm", player, false)
                        .replace(Permission.Command.RELOAD_CONFIG)
                        .send(player);
                return true;
            }

            try {
                Config.reload();
            } catch (InvalidConfigurationException e) {
                Message.getMessage("Command.PDS.Arg.ReloadConfig.Error").send(commandSender);
                if (Config.Item.BASIC.getConfigSection() == null)
                    Tools.log("The configuration of BasicDebugStick cannot be found. Disabling the plugin", Tools.LOG_SEVERE);
                if (Config.Item.INFINITY.getConfigSection() == null)
                    Tools.log("The configuration of InfinityDebugStick cannot be found. Disabling the plugin", Tools.LOG_SEVERE);
                if (Config.Item.INSPECTOR.getConfigSection() == null)
                    Tools.log("The configuration of Inspector cannot be found. Disabling the plugin", Tools.LOG_SEVERE);

                ProtectedDebugStick.getInstance().getPluginLoader().disablePlugin(ProtectedDebugStick.getInstance());
                return true;
            }


            Message.getMessage("Command.PDS.Arg.ReloadConfig.Success")
                    .send(commandSender);
            return true;
        }

        if (strings[0].equalsIgnoreCase("core-protect")) {
            List<String> restrict = null;
            int time = 0;
            for (String arg : strings) {
                if (arg.equals(strings[0]))
                    continue;

                if (arg.startsWith("time:")) {
                    String substring = arg.substring("time:".length());
                    String lastChar = substring.substring(substring.length() - 1).toLowerCase();
                    int multiplicator = 1;
                    if (Arrays.asList("d", "h", "m", "s", "w").contains(lastChar)) {
                        switch (lastChar) {
                            case "w":
                                multiplicator *= 7;
                            case "d":
                                multiplicator *= 24;
                            case "h":
                                multiplicator *= 60;
                            case "m":
                                multiplicator *= 60;
                        }
                    }
                    substring = new StringBuffer(substring).deleteCharAt(substring.length() - 1).toString();
                    try {
                        time = Integer.parseInt(substring) * multiplicator;
                        continue;
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage("Mauvais time");
                        return true;
                    }
                }
                if (arg.startsWith("restrict:")) {
                    String substring = arg.substring("restrict:".length());
                    restrict = Arrays.asList(substring.split(","));
                    continue;
                }
            }
            if (time == 0) {
                commandSender.sendMessage("Mauvais time");
                return true;
            }
            if (restrict == null) {
                commandSender.sendMessage("Restrict manquant ou mauvais");
                return true;
            }
            Log.request(time, restrict);
        }

        Message.getMessage("Command.PDS.NoCommandFound", false)
                .replace("{arg}", strings[0])
                .send(commandSender);
        return true;
    }
}
