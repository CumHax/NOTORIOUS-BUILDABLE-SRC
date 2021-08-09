package me.gavin.notorious.hack.hacks.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import net.minecraft.client.multiplayer.ServerData;

@RegisterHack(
    name = "CopyIP",
    description = "Copies the current server IP to clipboard",
    category = Hack.Category.Misc
)
public class CopyIP extends Hack {

    protected void onEnable() {
        if (CopyIP.mc.getConnection() != null && CopyIP.mc.getCurrentServerData() != null && CopyIP.mc.getCurrentServerData().serverIP != null) {
            ServerData data = CopyIP.mc.getCurrentServerData();

            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(data.serverIP), (ClipboardOwner) null);
            this.notorious.messageManager.sendMessage("Copied IP " + ChatFormatting.GRAY + "[" + ChatFormatting.GREEN + data + ChatFormatting.GRAY + "]" + ChatFormatting.RESET + "to clipboard");
        } else {
            this.notorious.messageManager.sendError("Unable to copy server IP.");
        }

        this.disable();
    }
}
