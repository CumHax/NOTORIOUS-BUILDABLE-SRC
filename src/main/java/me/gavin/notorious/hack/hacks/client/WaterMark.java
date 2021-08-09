package me.gavin.notorious.hack.hacks.client;

import java.awt.Color;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.util.ColorUtil;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "WaterMark",
    description = "ez",
    category = Hack.Category.Client
)
public class WaterMark extends Hack {

    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("Mode", "Rainbow", new String[] { "Rainbow", "RGB"});
    @RegisterSetting
    public final ColorSetting rgb = new ColorSetting("RGB", 255, 255, 255);

    @SubscribeEvent
    public void onRender(Text event) {
        double x = 0.0D;
        Font font = (Font) Notorious.INSTANCE.hackManager.getHack(Font.class);
        byte yOffset;

        if (font.isEnabled()) {
            yOffset = 2;
        } else {
            yOffset = 1;
        }

        double y = (double) yOffset;
        String watermark = "Notorious beta-0.3";
        float time = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).length.getValue();
        float saturation = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).saturation.getValue();
        Color colorRainbow;

        if (this.mode.getMode().equals("Rainbow")) {
            colorRainbow = ColorUtil.colorRainbow((int) time, saturation, 1.0F);
        } else {
            colorRainbow = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).guiColor.getAsColor();
        }

        int intRainbow;

        if (this.mode.getMode().equals("Rainbow")) {
            intRainbow = ColorUtil.getRainbow(time, saturation);
        } else {
            intRainbow = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).guiColor.getAsColor().getRGB();
        }

        if (font.isEnabled()) {
            this.notorious.fontRenderer.drawStringWithShadow("Notorious beta-0.3", x + 2.0D, y, colorRainbow);
        } else {
            WaterMark.mc.fontRenderer.drawStringWithShadow("Notorious beta-0.3", (float) ((int) x + 2), (float) ((int) y), intRainbow);
        }

    }
}
