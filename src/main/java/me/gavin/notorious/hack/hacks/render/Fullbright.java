package me.gavin.notorious.hack.hacks.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;

@RegisterHack(
    name = "Fullbright",
    description = "Makes it fully bright",
    category = Hack.Category.Render
)
public class Fullbright extends Hack {

    private final List previousLevels = new ArrayList();

    public void onEnable() {
        float[] table = Fullbright.mc.world.provider.getLightBrightnessTable();

        if (Fullbright.mc.world.provider != null) {
            float[] afloat = table;
            int i = table.length;

            for (int j = 0; j < i; ++j) {
                float f = afloat[j];

                this.previousLevels.add(Float.valueOf(f));
            }

            Arrays.fill(table, 1.0F);
        }

    }

    public void onDisable() {
        float[] table = Fullbright.mc.world.provider.getLightBrightnessTable();

        for (int i = 0; i < table.length; ++i) {
            table[i] = ((Float) this.previousLevels.get(i)).floatValue();
        }

        this.previousLevels.clear();
    }
}
