package me.gavin.notorious.hack.hacks.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Iterator;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.AnimationUtil;
import me.gavin.notorious.util.ColorUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "ArrayList",
    description = "Shows enabled modules.",
    category = Hack.Category.Client
)
public class HackList extends Hack {

    @RegisterSetting
    public final ModeSetting listMode = new ModeSetting("ListMode", "Pretty", new String[] { "Pretty", "Basic"});
    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("Mode", "Flow", new String[] { "Flow", "RGB"});
    @RegisterSetting
    public final NumSetting length = new NumSetting("Length", 7.0F, 1.0F, 15.0F, 1.0F);
    @RegisterSetting
    public final NumSetting saturation = new NumSetting("Saturation", 0.5F, 0.1F, 1.0F, 0.1F);
    @RegisterSetting
    public final ColorSetting rgb = new ColorSetting("RGB", 255, 255, 255);

    public HackList() {
        this.toggle();
    }

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.mode.getMode() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onRender(Text event) {
        int yOffset = 2;
        Iterator iterator = this.notorious.hackManager.getSortedHacks().iterator();

        while (iterator.hasNext()) {
            Hack hack = (Hack) iterator.next();

            if (hack.isEnabled() || !hack.isEnabled() && System.currentTimeMillis() - hack.lastDisabledTime < 250L) {
                String n = hack.getName();
                String md = hack.getMetaData();
                String name = n + md;
                Font font = (Font) Notorious.INSTANCE.hackManager.getHack(Font.class);
                double startPos;

                if (font.isEnabled()) {
                    startPos = (double) (this.notorious.fontRenderer.getStringWidth(name) + 2);
                } else {
                    startPos = (double) (HackList.mc.fontRenderer.getStringWidth(name) + 2);
                }

                int color;

                if (this.mode.getMode().equals("Flow")) {
                    color = ColorUtil.getRGBWave(this.length.getValue(), this.saturation.getValue(), (long) yOffset * 20L);
                } else {
                    color = this.rgb.getAsColor().getRGB();
                }

                double x;

                if (hack.isEnabled()) {
                    x = -startPos + (startPos + 1.0D) * MathHelper.clamp((double) AnimationUtil.getSmooth2Animation(250.0F, (float) (System.currentTimeMillis() - hack.lastEnabledTime)), 0.0D, 1.0D);
                } else {
                    x = startPos * -MathHelper.clamp((double) AnimationUtil.getSmooth2Animation(250.0F, (float) (System.currentTimeMillis() - hack.lastDisabledTime)), 0.0D, 1.0D);
                }

                double y = (double) yOffset;

                if (((WaterMark) Notorious.INSTANCE.hackManager.getHack(WaterMark.class)).isEnabled()) {
                    y = (double) (yOffset + 9);
                } else {
                    y = (double) yOffset;
                }

                if (font.isEnabled()) {
                    if (this.listMode.getMode().equals("Pretty")) {
                        Gui.drawRect((int) x - 3, (int) y - 2, (int) (x + startPos + 1.0D), (int) (y + (double) this.notorious.fontRenderer.getHeight() + 2.0D), -1879048192);
                        Gui.drawRect((int) x - 4, (int) y - 2, (int) x + 1, (int) (y + (double) this.notorious.fontRenderer.getHeight() + 2.0D), color);
                    }
                } else if (this.listMode.getMode().equals("Pretty")) {
                    Gui.drawRect((int) x - 3, (int) y - 2, (int) (x + startPos + 1.0D), (int) (y + (double) HackList.mc.fontRenderer.FONT_HEIGHT + 2.0D), -1879048192);
                    Gui.drawRect((int) x - 4, (int) y - 2, (int) x + 1, (int) (y + (double) HackList.mc.fontRenderer.FONT_HEIGHT + 2.0D), color);
                }

                if (font.isEnabled()) {
                    this.notorious.fontRenderer.drawStringWithShadow(name, x + 2.0D, y, new Color(color));
                } else {
                    HackList.mc.fontRenderer.drawStringWithShadow(name, (float) ((int) x + 2), (float) ((int) y), color);
                }

                if (font.isEnabled()) {
                    yOffset += this.notorious.fontRenderer.getHeight() + 4;
                } else {
                    yOffset += HackList.mc.fontRenderer.FONT_HEIGHT + 4;
                }
            }
        }

    }
}
