package de.dashappylama.minecraftdiscordbridge.utils;

import java.sql.Timestamp;

public class BotUtils {

    private static BotUtils instance;

    private Timestamp botStarted;

    public static BotUtils getInstance() {
        if (instance == null) {
            instance = new BotUtils();
        }

        return instance;
    }


    public void setBotStarted(Timestamp timestamp) {
        this.botStarted = timestamp;
    }

    @SuppressWarnings("unused")
    public Timestamp getBotStarted() {
        return this.botStarted;
    }
}
