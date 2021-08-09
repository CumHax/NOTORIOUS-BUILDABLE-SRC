package me.gavin.notorious.hack.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.function.BiConsumer;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.mixin.mixins.accessor.IRenderGlobal;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.ColorUtil;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "BreakESP",
    description = "shows break progress",
    category = Hack.Category.Render
)
public class BreakESP extends Hack {

    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("Mode", "Both", new String[] { "Both", "Outline", "Box"});
    @RegisterSetting
    public final ColorSetting outlineColor = new ColorSetting("OutlineColor", 255, 255, 255, 125);
    @RegisterSetting
    public final ColorSetting boxColor = new ColorSetting("BoxColor", 255, 255, 255, 125);
    @RegisterSetting
    public final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    @RegisterSetting
    public final NumSetting saturation = new NumSetting("Saturation", 0.6F, 0.1F, 1.0F, 0.1F);
    @RegisterSetting
    public final NumSetting time = new NumSetting("RainbowLength", 8.0F, 1.0F, 15.0F, 1.0F);
    @RegisterSetting
    public final NumSetting range = new NumSetting("Range", 15.0F, 1.0F, 20.0F, 1.0F);
    @RegisterSetting
    public final BooleanSetting fade = new BooleanSetting("Fade", true);
    boolean outline = false;
    boolean fill = false;

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.mode.getMode() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        ((IRenderGlobal) BreakESP.mc.renderGlobal).getDamagedBlocks().forEach((integer, destroyBlockProgress) -> {
            // $FF: Couldn't be decompiled
        });
    }
}
