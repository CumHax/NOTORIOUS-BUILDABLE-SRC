package me.gavin.notorious.hack.hacks.render;

import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.NumSetting;

@RegisterHack(
    name = "Weather",
    description = "ez",
    category = Hack.Category.Render
)
public class Weather extends Hack {

    @RegisterSetting
    public final NumSetting rainStrength = new NumSetting("RainStrength", 0.0F, 0.0F, 3.0F, 1.0F);
}
