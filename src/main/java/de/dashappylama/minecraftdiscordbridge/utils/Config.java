package de.dashappylama.minecraftdiscordbridge.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Config {

    private static File folder = new File("plugins//MDBE");
    private static File config  = new File("plugins//MDBE", "config.yml");
    private static YamlConfiguration yamlConfiguration;


    public static void existAndCreate() {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!config.exists()) {
            try {
                config.createNewFile();
                yamlConfiguration = YamlConfiguration.loadConfiguration(config);
                generateDefaultEntries();
            } catch (IOException exception) {
                Bukkit.getLogger().log(Level.SEVERE, "Die Config konnte nicht erstellt werden. Weitere Infos im StackTrace:");
                exception.printStackTrace();
            }
        }
    }

    public static void loadConfig() {
        yamlConfiguration = YamlConfiguration.loadConfiguration(config);
    }

    public static YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public static void saveConfiguration() {
        try {
            yamlConfiguration.save(config);
        } catch (IOException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Die Config konnte nicht gespeichert werden. Weitere Infos im StackTrace:");
            exception.printStackTrace();
        }
    }


    private static void generateDefaultEntries() {
        yamlConfiguration.options().header("MinecraftDiscordBridgeExample by Happy_Lama_ // Alle \"&\" werden automatisch durch ein \"§\" ersetzt.");
        yamlConfiguration.set("bot.token", "BOT_TOKEN_HERE");
        yamlConfiguration.set("discord.mcChatID", "MC-CHAT-ID_HERE");

        saveConfiguration();

    }





}
