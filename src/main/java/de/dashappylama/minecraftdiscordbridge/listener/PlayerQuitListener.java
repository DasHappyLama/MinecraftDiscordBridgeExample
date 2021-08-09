package de.dashappylama.minecraftdiscordbridge.listener;

import de.dashappylama.minecraftdiscordbridge.MinecraftDiscordBridgeExample;
import de.dashappylama.minecraftdiscordbridge.utils.Config;
import de.dashappylama.minecraftdiscordbridge.utils.MessageConfig;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.logging.Level;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();


        TextChannel textChannel = MinecraftDiscordBridgeExample.getInstance().getShardManager().getTextChannelById(Objects.requireNonNull(Config.getYamlConfiguration().getString("discord.mcChatID")));
        if (textChannel == null) {
            Bukkit.getLogger().log(Level.SEVERE, "Der Minecraft Discord Channel konnte nicht gefunden werden.");

        } else {
            textChannel.sendMessage(MessageConfig.getMessage("playerQuit").replaceAll("%PLAYERNAME%", player.getName())).queue();
        }


    }

}
