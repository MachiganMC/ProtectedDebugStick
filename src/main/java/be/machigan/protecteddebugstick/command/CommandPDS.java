package be.machigan.protecteddebugstick.command;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.LogUtil;
import be.machigan.protecteddebugstick.utils.Message;
import be.machigan.protecteddebugstick.utils.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;


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
                    LogUtil.getLogger().severe("The configuration of BasicDebugStick cannot be found. Disabling the plugin");
                if (Config.Item.INFINITY.getConfigSection() == null)
                    LogUtil.getLogger().severe("The configuration of InfinityDebugStick cannot be found. Disabling the plugin");
                if (Config.Item.INSPECTOR.getConfigSection() == null)
                    LogUtil.getLogger().severe("The configuration of Inspector cannot be found. Disabling the plugin");

                ProtectedDebugStick.getInstance().getPluginLoader().disablePlugin(ProtectedDebugStick.getInstance());
                return true;
            }


            Message.getMessage("Command.PDS.Arg.ReloadConfig.Success")
                    .send(commandSender);
            return true;
        }

        Message.getMessage("Command.PDS.NoCommandFound", false)
                .replace("{arg}", strings[0])
                .send(commandSender);
        return true;
    }
}
