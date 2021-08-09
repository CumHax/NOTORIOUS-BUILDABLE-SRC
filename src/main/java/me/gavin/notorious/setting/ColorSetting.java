package me.gavin.notorious.setting;

import java.awt.Color;
import me.gavin.notorious.util.NColor;

public class ColorSetting extends Setting {

    private final NumSetting red;
    private final NumSetting green;
    private final NumSetting blue;
    private final NumSetting alpha;

    public ColorSetting(String name, NColor color) {
        super(name);
        this.red = new NumSetting("R", (float) color.getRed(), 0.0F, 255.0F, 1.0F);
        this.green = new NumSetting("G", (float) color.getGreen(), 0.0F, 255.0F, 1.0F);
        this.blue = new NumSetting("B", (float) color.getBlue(), 0.0F, 255.0F, 1.0F);
        this.alpha = new NumSetting("A", (float) color.getAlpha(), 0.0F, 255.0F, 1.0F);
    }

    public ColorSetting(String name, int red, int green, int blue, int alpha) {
        this(name, new NColor(red, green, blue, alpha));
    }

    public ColorSetting(String name, int red, int green, int blue) {
        this(name, new NColor(red, green, blue));
    }

    public ColorSetting(String name, Color color) {
        this(name, new NColor(color));
    }

    public NumSetting getRed() {
        return this.red;
    }

    public NumSetting getGreen() {
        return this.green;
    }

    public NumSetting getBlue() {
        return this.blue;
    }

    public NumSetting getAlpha() {
        return this.alpha;
    }

    public Color getAsColor() {
        return new Color((int) this.red.getValue(), (int) this.green.getValue(), (int) this.blue.getValue(), (int) this.alpha.getValue());
    }
}
