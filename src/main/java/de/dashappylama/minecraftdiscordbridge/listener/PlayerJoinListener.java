package de.dashappylama.minecraftdiscordbridge.listener;

import de.dashappylama.minecraftdiscordbridge.MinecraftDiscordBridgeExample;
import de.dashappylama.minecraftdiscordbridge.utils.Config;
import de.dashappylama.minecraftdiscordbridge.utils.MessageConfig;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Level;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();


        TextChannel textChannel = MinecraftDiscordBridgeExample.getInstance().getShardManager().getTextChannelById(Config.getYamlConfiguration().getString("discord.mcChatID"));
        if (textChannel == null) {
            Bukkit.getLogger().log(Level.SEVERE, "Der Minecraft Discord Channel konnte nicht gefunden werden.");

        } else {
            textChannel.sendMessage(MessageConfig.getMessage("playerJoin").replaceAll("%PLAYERNAME%", player.getName())).queue();
        }


    }

}
