package me.gavin.notorious.hack.hacks.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.util.DiscordUtil;

@RegisterHack(
    name = "DiscordRPC",
    description = "ez",
    category = Hack.Category.Client
)
public class DiscordRPC extends Hack {

    public void onEnable() {
        DiscordUtil.startRPC();
        this.notorious.messageManager.sendMessage("Starting " + ChatFormatting.GREEN + "RPC" + ChatFormatting.RESET + "!");
    }

    public void onDisable() {
        DiscordUtil.stopRPC();
        this.notorious.messageManager.sendMessage("Stopping " + ChatFormatting.RED + "RPC" + ChatFormatting.RESET + "!");
    }
}
