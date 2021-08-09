package me.gavin.notorious.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.stuff.IMinecraft;
import net.minecraft.util.text.TextComponentString;

public class MessageManager implements IMinecraft {

    public final String messagePrefix;
    public final String errorPrefix;

    public MessageManager() {
        this.messagePrefix = ChatFormatting.BLUE + "<" + "notorious" + "> " + ChatFormatting.RESET;
        this.errorPrefix = ChatFormatting.DARK_RED + "<" + "notorious" + "> " + ChatFormatting.RESET;
    }

    public void sendRawMessage(String message) {
        if (MessageManager.mc.player != null) {
            MessageManager.mc.player.sendMessage(new TextComponentString(message));
        }

    }

    public void sendMessage(String message) {
        this.sendRawMessage(this.messagePrefix + message);
    }

    public void sendError(String message) {
        this.sendRawMessage(this.errorPrefix + message);
    }
}
