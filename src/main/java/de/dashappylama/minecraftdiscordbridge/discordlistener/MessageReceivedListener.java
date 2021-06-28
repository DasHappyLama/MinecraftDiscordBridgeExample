package de.dashappylama.minecraftdiscordbridge.discordlistener;

import de.dashappylama.minecraftdiscordbridge.utils.Config;
import de.dashappylama.minecraftdiscordbridge.utils.MessageConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;

public class MessageReceivedListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild()) {
            return;
        }

        User author = event.getAuthor();
        String messageContent = event.getMessage().getContentRaw();
        Message message = event.getMessage();
        TextChannel textChannel = event.getTextChannel();

        if (author.isBot()) {
            return;
        }

        if (textChannel.getId().equalsIgnoreCase(Config.getYamlConfiguration().getString("discord.mcChatID"))) {
            Bukkit.broadcastMessage(MessageConfig.getMessage("mcChatFormat").replaceAll("%USERNAME%", author.getName()).replaceAll("%MESSAGE%", messageContent));
            message.addReaction("U+1F44C").queue();

        }


    }

}
