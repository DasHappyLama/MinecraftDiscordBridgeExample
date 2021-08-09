package de.dashappylama.minecraftdiscordbridge.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class MessageConfig {

    private static File folder = new File("plugins//MDBE");
    private static File config  = new File("plugins//MDBE", "messages.yml");
    private static YamlConfiguration yamlConfiguration;


    @SuppressWarnings("all")
    public static void existAndCreate() {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        if (!config.exists()) {
            try {
                config.createNewFile();
                yamlConfiguration = YamlConfiguration.loadConfiguration(config);
                generateDefaultMessages();
            } catch (IOException exception) {
                Bukkit.getLogger().log(Level.SEVERE, "Die Message-Config konnte nicht erstellt werden. Weitere Infos im StackTrace:");
                exception.printStackTrace();
            }
        }
    }

    public static void loadConfig() {
        yamlConfiguration = YamlConfiguration.loadConfiguration(config);
    }

    @SuppressWarnings("unused")
    public static YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }

    public static void saveConfiguration() {
        try {
            yamlConfiguration.save(config);
        } catch (IOException exception) {
            Bukkit.getLogger().log(Level.SEVERE, "Die Message-Config konnte nicht gespeichert werden. Weitere Infos im StackTrace:");
            exception.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public static String getMessage(String messageName) {
        if (yamlConfiguration.getString("messages." + messageName) != null) {
            return yamlConfiguration.getString("messages." + messageName).replaceAll("&", "§");
        } else {
            return "Invalid message";
        }
    }

    private static void generateDefaultMessages() {
        yamlConfiguration.options().header("MinecraftDiscordBridgeExample by Happy_Lama_ // Alle \"&\" werden automatisch durch ein \"§\" ersetzt.");
        yamlConfiguration.set("messages.prefix", "&9MDBE &8| &7");
        yamlConfiguration.set("messages.noPerms", "&cDu hast nicht genug Rechte, um diesen Befehl auszuführen.");
//        yamlConfiguration.set("messages.noPlayer", "&cDu musst ein Spieler sein, um diesen Befehl auszuführen.");
//        yamlConfiguration.set("messages.playerNotFound", "&cDer angegebene Spieler wurde nicht gefunden.");
        yamlConfiguration.set("messages.mcChatFormat", "&9Discord &8| &3%USERNAME%&8: &f%MESSAGE%");
        yamlConfiguration.set("messages.dcChatFormat", "**%PLAYERNAME%**: %MESSAGE%");
        yamlConfiguration.set("messages.playerJoin", "**%PLAYERNAME%** hat den Server betreten.");
        yamlConfiguration.set("messages.playerQuit", "**%PLAYERNAME%** hat den Server verlassen.");
        yamlConfiguration.set("messages.serverStopped", "Der Server wurde gestoppt.");
        yamlConfiguration.set("messages.serverStarted", "Der Server wurde gestartet.");
        yamlConfiguration.set("messages.pluginReloaded", "Die Configurationen wurden neu geladen.");

        saveConfiguration();

    }





}
