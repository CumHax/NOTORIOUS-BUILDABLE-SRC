package me.gavin.notorious.hack.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.text.SimpleDateFormat;
import java.util.Date;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ModeSetting;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "ChatModifications",
    description = "Modifies chat.",
    category = Hack.Category.Chat
)
public class ChatModifications extends Hack {

    @RegisterSetting
    public final BooleanSetting chatSuffix = new BooleanSetting("ChatSuffix", true);
    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("SuffixMode", "Unicode", new String[] { "Unicode", "Vanilla", "UnicodeVersion"});
    @RegisterSetting
    public final BooleanSetting colorChat = new BooleanSetting("ColorChat", false);
    @RegisterSetting
    public final ModeSetting chatColor = new ModeSetting("ChatColor", "Green", new String[] { "Green", "Red", "Cyan"});
    @RegisterSetting
    public final BooleanSetting chatTimestamps = new BooleanSetting("ChatTimestamps", true);
    public String suffix = "";
    public String color = "";

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        String originalMessage = event.getOriginalMessage();

        if (this.chatSuffix.isEnabled()) {
            if (this.mode.getMode().equals("Vanilla")) {
                this.suffix = " | Notorious";
            } else if (this.mode.getMode().equals("Unicode")) {
                this.suffix = " �?? ɴ�?ᴛ�?ʀɪᴜꜱ";
            } else {
                this.suffix = " �?? ɴ�?ᴛ�?ʀɪᴜꜱ beta-0.3";
            }

            if (event.getMessage().startsWith("!")) {
                return;
            }

            if (event.getMessage().startsWith(".")) {
                return;
            }

            if (event.getMessage().startsWith("/")) {
                return;
            }

            event.setMessage(originalMessage + this.suffix);
        }

        if (this.colorChat.isEnabled()) {
            if (this.chatColor.getMode().equals("Green")) {
                this.color = ">";
            } else if (this.chatColor.getMode().equals("Red")) {
                this.color = "@";
            } else if (this.chatColor.getMode().equals("Cyan")) {
                this.color = "^";
            }

            if (event.getMessage().startsWith("!")) {
                return;
            }

            if (event.getMessage().startsWith(".")) {
                return;
            }

            if (event.getMessage().startsWith("/")) {
                return;
            }

            event.setMessage(this.color + originalMessage);
        }

    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (this.chatTimestamps.isEnabled()) {
            String time = (new SimpleDateFormat("k:mm")).format(new Date());
            TextComponentString text = new TextComponentString(ChatFormatting.GRAY + "<" + time + "> " + ChatFormatting.RESET);

            event.setMessage(text.appendSibling(event.getMessage()));
        }

    }
}
