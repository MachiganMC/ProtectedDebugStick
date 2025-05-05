package be.machigan.protecteddebugstick.command;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.persistent.ChunkDataHandler;
import be.machigan.protecteddebugstick.persistent.LocationListDataType;
import be.machigan.protecteddebugstick.utils.Config;
import be.machigan.protecteddebugstick.utils.LogUtil;
import be.machigan.protecteddebugstick.utils.Message;
import be.machigan.protecteddebugstick.utils.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPhysicsEvent;

import java.io.File;


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

        if (strings[0].equalsIgnoreCase("load")) {
            if (!Permission.Command.LOAD.has(commandSender)) {
                Message.getMessage("Command.PDS.Arg.Load.NoPerm", false)
                        .replace(Permission.Command.LOAD)
                        .send(commandSender);
                return true;
            }

            if (strings.length < 2) {
                Message.getMessage("Command.PDS.Arg.Load.NotEnoughArg").send(commandSender);
                return true;
            }

            boolean overWrite = strings.length > 2 && strings[2].equalsIgnoreCase("over-write");

            String file = strings[1];
            if (!file.endsWith(".yml") && !file.endsWith(".MD"))
                file += ".yml";

            if (!overWrite && new File(ProtectedDebugStick.getInstance().getDataFolder(), "/" + file).exists()) {
                Message.getMessage("Command.PDS.Arg.Load.FileAlreadyExists")
                        .replace("{file}", file)
                        .send(commandSender);
                return true;
            }
            try {
                ProtectedDebugStick.getInstance().saveResource(file, overWrite);
                Message.getMessage("Command.PDS.Arg.Load.Success")
                        .replace("{file}", file)
                        .send(commandSender);
            } catch (IllegalArgumentException e) {
                Message.getMessage("Command.PDS.Arg.Load.FileNotExists")
                        .replace("{file}", strings[1])
                        .send(commandSender);
            }
            return true;
        }

        if (strings[0].equalsIgnoreCase("chunk")) {
            if (!(commandSender instanceof Player player)) {
                Message.getMessage("Command.PDS.Arg.Chunk.OnlyPlayer").send(commandSender);
                return true;
            }
            if (strings.length == 1) {
                Message.getMessage("Command.PDS.Arg.Chunk.NotEnoughArg").send(player);
                return  true;
            }
            if (strings[1].equalsIgnoreCase("info")) {
                Chunk chunk = player.getLocation().getChunk();
                if (!Permission.Command.CHUNK_INFO.has(player)) {
                    Message.getMessage("Command.PDS.Arg.Chunk.Arg.Info.NoPerm", false).replace(Permission.Command.CHUNK_INFO).send(player);
                    return true;
                }
                if (!ChunkDataHandler.hasData(chunk)) {
                    Message.getMessage("Command.PDS.Arg.Chunk.Arg.Info.NoInfo").send(player);
                    return true;
                }
                Message.getMessage("Command.PDS.Arg.Chunk.Arg.Info.FirstLine").send(player);
                ChunkDataHandler.get(chunk).stream()
                        .map(locationSerializable -> locationSerializable.toLocation().getBlock())
                        .forEach(block -> {
                            Message.getMessage("Command.PDS.Arg.Chunk.Arg.Info.Line").replace(block).send(player);
                });
                return true;
            }

            if (strings[1].equalsIgnoreCase("clear")) {
                if (!Permission.Command.CHUNK_CLEAR.has(player)) {
                    Message.getMessage("Command.PDS.Arg.Chunk.Arg.Clear.NoPerm", false).replace(Permission.Command.CHUNK_CLEAR).send(player);
                    return true;
                }
                ChunkDataHandler.clear(player.getLocation().getChunk());
                Message.getMessage("Command.PDS.Arg.Chunk.Arg.Clear.Success").send(player);
                return true;
            }
        }

        Message.getMessage("Command.PDS.NoCommandFound", false)
                .replace("{arg}", strings[0])
                .send(commandSender);
        return true;
    }
}
