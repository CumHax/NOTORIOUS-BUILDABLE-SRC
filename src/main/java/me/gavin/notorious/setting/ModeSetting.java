package me.gavin.notorious.setting;

public class ModeSetting extends Setting {

    private int modeIndex;
    private String[] modes;

    public ModeSetting(String name, String value, String... modes) {
        super(name);
        this.modes = modes;
        this.modeIndex = this.getIndex(value);
    }

    public String getMode() {
        return this.modes[this.modeIndex];
    }

    public void setMode(String value) {
        this.modeIndex = this.getIndex(value);
    }

    public void cycle(boolean backwards) {
        if (!backwards) {
            if (this.modeIndex == this.modes.length - 1) {
                this.modeIndex = 0;
            } else {
                ++this.modeIndex;
            }
        } else if (this.modeIndex == 0) {
            this.modeIndex = this.modes.length - 1;
        } else {
            --this.modeIndex;
        }

    }

    private int getIndex(String value) {
        for (int i = 0; i < this.modes.length; ++i) {
            if (this.modes[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }
}
