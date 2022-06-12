package be.machigan.protecteddebugstick.command;

import be.machigan.protecteddebugstick.ProtectedDebugStick;
import be.machigan.protecteddebugstick.def.DebugStick;
import be.machigan.protecteddebugstick.def.Durability;
import be.machigan.protecteddebugstick.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandPDS implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!commandSender.hasPermission("pds.command.use")) {
            commandSender.sendMessage(Utils.configColor("messages.noPerm.noPermCommand").replace("{prefix}", ProtectedDebugStick.prefix)
                    .replace("{player}", commandSender.getName()).replace("perm}", "pds.command.use"));
            return true;
        }
        if (strings.length == 0) {
            commandSender.sendMessage(Utils.configColor("messages.command.notEnoughArg").replace("{prefix}", ProtectedDebugStick.prefix)
                    .replace("{player}", commandSender.getName()));
            return true;
        }

        if (strings[0].equalsIgnoreCase("give")) {
            if (!commandSender.hasPermission("pds.command.give")) {
                commandSender.sendMessage(Utils.configColor("messages.noPerm.noPermArgGive").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", commandSender.getName()).replace("{perm}", "pds.command.give"));
                return true;
            }

            if (strings.length < 2) {
                commandSender.sendMessage(Utils.configColor("messages.command.arg.give.notEnoughArg").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", commandSender.getName()));
                return true;
            }

            Player player = Bukkit.getPlayer(strings[1]);
            if (player == null) {
                commandSender.sendMessage(Utils.configColor("messages.command.arg.give.playerNotFound").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", commandSender.getName()).replace("{arg}", strings[1]));
                return true;
            }
            if (strings.length < 3) {
                commandSender.sendMessage(Utils.configColor("messages.command.arg.give.whatToGive").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", commandSender.getName()).replace("{arg}", player.getName()));
                return true;
            }

            switch (strings[2].toLowerCase()) {
                case "basic":
                    if (strings.length < 4) {
                        commandSender.sendMessage(Utils.configColor("messages.command.arg.give.whatDurability")
                                .replace("{prefix}", ProtectedDebugStick.prefix).replace("{player}", commandSender.getName())
                                .replace("{arg}", player.getName()).replace("{item}", "basic debug-stick"));
                        return true;
                    }
                    try {
                        int durability = Integer.parseInt(strings[3]);
                        if (durability <= 0) {
                            throw new NumberFormatException("Durability below or equal to 0");
                        }
                        player.getInventory().addItem(DebugStick.getDebugStick(durability));
                        break;
                    } catch (NumberFormatException ignored) {
                        commandSender.sendMessage(Utils.configColor("messages.command.arg.give.badDurability")
                                .replace("{prefix}", ProtectedDebugStick.prefix).replace("{player}", commandSender.getName())
                                .replace("{arg}", player.getName()).replace("{item}", "basic debug-stick")
                                .replace("{durability}", strings[3]));
                        return true;
                    }

                case "infinity":
                    player.getInventory().addItem(DebugStick.getInfinityDebugStick());
                    break;

                case "inspector":
                    player.getInventory().addItem(DebugStick.inspector);
                    break;

                default:
                    commandSender.sendMessage(Utils.configColor("messages.command.arg.give.badItem").replace("{prefix}", ProtectedDebugStick.prefix)
                            .replace("{player}", commandSender.getName()).replace("{arg}", player.getName()).replace("{item}", strings[2]));
                    return true;
            }
            commandSender.sendMessage(Utils.configColor("messages.command.arg.give.success").replace("{prefix}", ProtectedDebugStick.prefix)
                    .replace("{player}", commandSender.getName()).replace("{arg}", player.getName())
                    .replace("{item}", strings[2].toLowerCase()));
            return true;

        }

        if (strings[0].equalsIgnoreCase("reloadconfig")) {
            if (!commandSender.hasPermission("pds.command.reloadConfig")) {
                commandSender.sendMessage(Utils.configColor("messages.noPerm.noPermReloadConfig").replace("{prefix}", ProtectedDebugStick.prefix)
                        .replace("{player}", commandSender.getName()).replace("{perm}", "pds.command.reloadConfig"));
                return true;
            }
            DebugStick.init();
            Durability.init();
            commandSender.sendMessage(Utils.configColor("messages.command.arg.reloadConfig.success").replace("{prefix}", ProtectedDebugStick.prefix)
                    .replace("{player}", commandSender.getName()));
            return true;
        }


        commandSender.sendMessage(Utils.configColor("messages.command.noCommandFound").replace("{prefix}", ProtectedDebugStick.prefix)
                .replace("{player}", commandSender.getName()));
        return true;
    }
}
