package de.dashappylama.minecraftdiscordbridge;

import de.dashappylama.minecraftdiscordbridge.commands.PluginCommand;
import de.dashappylama.minecraftdiscordbridge.discordlistener.MessageReceivedListener;
import de.dashappylama.minecraftdiscordbridge.listener.AsyncPlayerChatListener;
import de.dashappylama.minecraftdiscordbridge.listener.PlayerJoinListener;
import de.dashappylama.minecraftdiscordbridge.listener.PlayerQuitListener;
import de.dashappylama.minecraftdiscordbridge.utils.BotUtils;
import de.dashappylama.minecraftdiscordbridge.utils.Config;
import de.dashappylama.minecraftdiscordbridge.utils.MessageConfig;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.sql.Timestamp;
import java.util.logging.Level;

@SuppressWarnings("deprecation")
public final class MinecraftDiscordBridgeExample extends JavaPlugin {

    private static MinecraftDiscordBridgeExample instance;

    public static MinecraftDiscordBridgeExample getInstance() {
        return instance;
    }

    private DefaultShardManagerBuilder builder;

    private ShardManager shardManager;

    public ShardManager getShardManager() {
        return shardManager;
    }

    @Override
    public void onEnable() {
        instance = this;


        MessageConfig.existAndCreate();
        MessageConfig.loadConfig();

        Config.loadConfig();
        Config.existAndCreate();

        register(Bukkit.getPluginManager());


        startBot();

        getLogger().log(Level.INFO, "Das Plugin wurde aktiviert.");


        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            TextChannel textChannel = MinecraftDiscordBridgeExample.getInstance().getShardManager().getTextChannelById(Config.getYamlConfiguration().getString("discord.mcChatID"));
            if (textChannel == null) {
                Bukkit.getLogger().log(Level.SEVERE, "Der Minecraft Discord Channel konnte nicht gefunden werden.");

            } else {
                textChannel.sendMessage(MessageConfig.getMessage("serverStarted")).queue();
            }
        }, 60);


    }


    private void register(PluginManager pluginManager) {
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new AsyncPlayerChatListener(), this);

        getCommand("mdbe").setExecutor(new PluginCommand());

    }

    private void startBot() {
        try {

            this.builder =  DefaultShardManagerBuilder.createDefault(Config.getYamlConfiguration().getString("bot.token"));




            this.builder.setBulkDeleteSplittingEnabled(false);
//            this.builder.setToken(Config.getYamlConfiguration().getString("bot.token"));


            this.builder.addEventListeners(new MessageReceivedListener());


            this.builder.setActivity(Activity.streaming("By Happy_Lama_", "dashappylama.de"));
            this.builder.setAutoReconnect(true);

            this.builder.setChunkingFilter(ChunkingFilter.ALL);
            this.builder.setMemberCachePolicy(MemberCachePolicy.ALL);
            this.builder.enableIntents(GatewayIntent.GUILD_MEMBERS);

            this.shardManager = this.builder.build();


            BotUtils.getInstance().setBotStarted(new Timestamp(System.currentTimeMillis()));

            getLogger().log(Level.INFO, "Der Bot wurde gestartet.");



        } catch (LoginException exception) {
            getLogger().log(Level.SEVERE, "Der Bot konnte nicht gestartet werden. Weitere Infos im StackTrace:");
            exception.printStackTrace();
        }
    }




    @Override
    public void onDisable() {
        getShardManager().setStatus(OnlineStatus.OFFLINE);



        TextChannel textChannel = MinecraftDiscordBridgeExample.getInstance().getShardManager().getTextChannelById(Config.getYamlConfiguration().getString("discord.mcChatID"));
        if (textChannel == null) {
            Bukkit.getLogger().log(Level.SEVERE, "Der Minecraft Discord Channel konnte nicht gefunden werden.");

        } else {
            textChannel.sendMessage(MessageConfig.getMessage("serverStopped")).queue();
        }


        getShardManager().shutdown();


        getLogger().log(Level.INFO, "Das Plugin wurde deaktiviert.");
    }
}
