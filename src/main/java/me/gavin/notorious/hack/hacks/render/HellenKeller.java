package me.gavin.notorious.hack.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Chat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "HellenKeller",
    description = "",
    category = Hack.Category.Render
)
public class HellenKeller extends Hack {

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + "Blind" + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onRender(Chat event) {
        GlStateManager.pushMatrix();
        Gui.drawRect(0, 0, HellenKeller.mc.displayWidth, HellenKeller.mc.displayHeight, (new Color(0, 0, 0, 255)).getRGB());
        GlStateManager.popMatrix();
    }
}
