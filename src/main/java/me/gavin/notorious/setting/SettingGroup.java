package me.gavin.notorious.setting;

import java.util.ArrayList;

public class SettingGroup {

    private final String name;
    private final ArrayList values;

    public SettingGroup(String name) {
        this.name = name;
        this.values = new ArrayList();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList getValues() {
        return this.values;
    }
}
