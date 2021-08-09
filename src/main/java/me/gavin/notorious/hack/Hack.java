package me.gavin.notorious.hack;

import java.util.ArrayList;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.gui.api.Bindable;
import me.gavin.notorious.gui.api.Toggleable;
import me.gavin.notorious.stuff.IMinecraft;
import net.minecraftforge.common.MinecraftForge;

public abstract class Hack implements Toggleable, Bindable, IMinecraft {

    protected final Notorious notorious;
    private String name;
    private String description;
    private Hack.Category category;
    public long lastEnabledTime;
    public long lastDisabledTime;
    private int keybind;
    private boolean enabled;
    private final ArrayList settings;

    public Hack() {
        this.notorious = Notorious.INSTANCE;
        this.lastEnabledTime = -1L;
        this.lastDisabledTime = -1L;
        this.settings = new ArrayList();
    }

    public ArrayList getSettings() {
        return this.settings;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void toggle() {
        if (this.enabled) {
            this.disable();
        } else {
            this.enable();
        }

    }

    public void enable() {
        this.enabled = true;
        MinecraftForge.EVENT_BUS.register(this);
        this.onEnable();
        this.lastEnabledTime = System.currentTimeMillis();
    }

    public void disable() {
        this.enabled = false;
        MinecraftForge.EVENT_BUS.unregister(this);
        this.onDisable();
        this.lastDisabledTime = System.currentTimeMillis();
    }

    protected void onEnable() {}

    protected void onDisable() {}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hack.Category getCategory() {
        return this.category;
    }

    public int getBind() {
        return this.keybind;
    }

    public void setBind(int keybind) {
        this.keybind = keybind;
    }

    public String getMetaData() {
        return "";
    }

    public void setCategory(Hack.Category category) {
        this.category = category;
    }

    public static enum Category {

        Combat, Player, Movement, Render, Misc, World, Chat, Client;
    }
}
