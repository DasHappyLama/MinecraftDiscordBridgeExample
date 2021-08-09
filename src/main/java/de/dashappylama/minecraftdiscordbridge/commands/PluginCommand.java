package de.dashappylama.minecraftdiscordbridge.commands;

import de.dashappylama.minecraftdiscordbridge.utils.Config;
import de.dashappylama.minecraftdiscordbridge.utils.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PluginCommand implements CommandExecutor {



    @Override
    @SuppressWarnings("all")
    public boolean onCommand(CommandSender sender,Command command,String label, String[] args) {

        if (sender.hasPermission("mdbe.reload")) {
            Config.loadConfig();
            MessageConfig.loadConfig();

            sender.sendMessage(MessageConfig.getMessage("prefix") + MessageConfig.getMessage("pluginReloaded"));

        } else {
            sender.sendMessage(MessageConfig.getMessage("prefix") + MessageConfig.getMessage("noPerms"));
        }

        return false;
    }
}
