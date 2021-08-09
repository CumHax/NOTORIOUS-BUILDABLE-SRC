package me.gavin.notorious.hack.hacks.client;

import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;

@RegisterHack(
    name = "ClickGUI",
    description = "Opens the click gui",
    category = Hack.Category.Client
)
public class ClickGUI extends Hack {

    @RegisterSetting
    public final ModeSetting colorMode = new ModeSetting("ColorMode", "Rainbow", new String[] { "Rainbow", "RGB"});
    @RegisterSetting
    public final ColorSetting guiColor = new ColorSetting("RGBColor", 255, 0, 0, 255);
    @RegisterSetting
    public final NumSetting length = new NumSetting("Length", 8.0F, 1.0F, 15.0F, 1.0F);
    @RegisterSetting
    public final NumSetting saturation = new NumSetting("Saturation", 0.6F, 0.1F, 1.0F, 0.1F);

    protected void onEnable() {
        ClickGUI.mc.displayGuiScreen(this.notorious.clickGuiScreen);
        this.disable();
    }
}
