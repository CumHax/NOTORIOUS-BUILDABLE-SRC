package me.gavin.notorious.setting;

import me.gavin.notorious.gui.api.Toggleable;

public class BooleanSetting extends Setting implements Toggleable {

    private boolean value;

    public BooleanSetting(String name, boolean value) {
        super(name);
        this.value = value;
    }

    public void toggle() {
        this.value = !this.value;
    }

    public boolean isEnabled() {
        return this.value;
    }

    public boolean getValue() {
        return this.isEnabled();
    }
}
