package net.buycraft.plugin.bukkit;

import lombok.Getter;
import net.buycraft.plugin.bukkit.command.Subcommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class BuycraftCommand implements CommandExecutor {
    @Getter
    private final Map<String, Subcommand> subcommandMap = new LinkedHashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("buycraft.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use Buycraft administrative commands.");
            return true;
        }

        if (args.length == 0) {
            showHelp(sender);
            return true;
        }

        if (subcommandMap.containsKey(args[0])) {
            String[] withoutSubcommand = Arrays.copyOfRange(args, 1, args.length);
            subcommandMap.get(args[0]).execute(sender, withoutSubcommand);
        } else {
            showHelp(sender);
        }

        return true;
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Usage for the Buycraft plugin:");

        for (Map.Entry<String, Subcommand> entry : subcommandMap.entrySet()) {
            sender.sendMessage(ChatColor.GREEN + "/buycraft " + entry.getKey() + ChatColor.GRAY + ": " + entry.getValue().getDescription());
        }
    }
}
