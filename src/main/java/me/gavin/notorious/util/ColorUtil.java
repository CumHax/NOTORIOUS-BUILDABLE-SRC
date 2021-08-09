package me.gavin.notorious.util;

import java.awt.Color;

public class ColorUtil {

    public static int getRainbow(float time, float saturation) {
        float hue = (float) (System.currentTimeMillis() % (long) ((int) (time * 1000.0F))) / (time * 1000.0F);

        return Color.HSBtoRGB(hue, saturation, 1.0F);
    }

    public static int getRGBWave(float seconds, float saturation, long index) {
        float hue = (float) ((System.currentTimeMillis() + index) % (long) ((int) (seconds * 1000.0F))) / (seconds * 1000.0F);

        return Color.HSBtoRGB(hue, saturation, 1.0F);
    }

    public static Color colorRainbow(int delay, float saturation, float brightness) {
        double rainbowState = Math.ceil((double) (System.currentTimeMillis() + (long) delay) / 20.0D);

        return Color.getHSBColor((float) ((rainbowState %= 360.0D) / 360.0D), saturation, brightness);
    }

    public static Color normalizedFade(float value) {
        float green = 1.0F - value;

        return new Color(value, green, 0.0F);
    }
}
