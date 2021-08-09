package me.gavin.notorious.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

public class DiscordUtil {

    private static String discordID = "864316915505037352";
    private static DiscordRichPresence discordRichPresence = new DiscordRichPresence();
    private static DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static String clientVersion = "Notorious beta-0.3";

    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();

        eventHandlers.disconnected = (var1, var2) -> {
            System.out.println("Discord RPC disconnected, var1: " + i + ", var2: " + s);
        };
        DiscordUtil.discordRPC.Discord_Initialize(DiscordUtil.discordID, eventHandlers, true, (String) null);
        DiscordUtil.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordUtil.discordRichPresence.details = DiscordUtil.clientVersion;
        DiscordUtil.discordRichPresence.largeImageKey = "logo";
        DiscordUtil.discordRichPresence.largeImageText = "discord.gg/nPBPJRcuqP";
        DiscordUtil.discordRichPresence.state = null;
        DiscordUtil.discordRPC.Discord_UpdatePresence(DiscordUtil.discordRichPresence);
    }

    public static void stopRPC() {
        DiscordUtil.discordRPC.Discord_Shutdown();
        DiscordUtil.discordRPC.Discord_ClearPresence();
    }
}
