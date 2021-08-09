package me.gavin.notorious.manager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.hack.hacks.chat.AutoSuicide;
import me.gavin.notorious.hack.hacks.chat.ChatModifications;
import me.gavin.notorious.hack.hacks.chat.PotionAlert;
import me.gavin.notorious.hack.hacks.chat.VisualRange;
import me.gavin.notorious.hack.hacks.client.ClickGUI;
import me.gavin.notorious.hack.hacks.client.DiscordRPC;
import me.gavin.notorious.hack.hacks.client.Font;
import me.gavin.notorious.hack.hacks.client.HackList;
import me.gavin.notorious.hack.hacks.client.WaterMark;
import me.gavin.notorious.hack.hacks.combat.AnvilBurrow;
import me.gavin.notorious.hack.hacks.combat.AutoCrystal;
import me.gavin.notorious.hack.hacks.combat.KillAura;
import me.gavin.notorious.hack.hacks.combat.Quiver;
import me.gavin.notorious.hack.hacks.combat.SmartOffhand;
import me.gavin.notorious.hack.hacks.misc.AutoLog;
import me.gavin.notorious.hack.hacks.misc.AutoRespawn;
import me.gavin.notorious.hack.hacks.misc.CopyIP;
import me.gavin.notorious.hack.hacks.misc.FakePlayer;
import me.gavin.notorious.hack.hacks.misc.GhastNotifier;
import me.gavin.notorious.hack.hacks.misc.WeaknessLog;
import me.gavin.notorious.hack.hacks.movement.AntiVoid;
import me.gavin.notorious.hack.hacks.movement.Sprint;
import me.gavin.notorious.hack.hacks.movement.Step;
import me.gavin.notorious.hack.hacks.movement.Strafe;
import me.gavin.notorious.hack.hacks.movement.Velocity;
import me.gavin.notorious.hack.hacks.player.ChestStealer;
import me.gavin.notorious.hack.hacks.player.FastPlace;
import me.gavin.notorious.hack.hacks.render.AntiFog;
import me.gavin.notorious.hack.hacks.render.BlockHighlight;
import me.gavin.notorious.hack.hacks.render.BreakESP;
import me.gavin.notorious.hack.hacks.render.ESP;
import me.gavin.notorious.hack.hacks.render.Fullbright;
import me.gavin.notorious.hack.hacks.render.HellenKeller;
import me.gavin.notorious.hack.hacks.render.PenisESP;
import me.gavin.notorious.hack.hacks.render.StorageESP;
import me.gavin.notorious.hack.hacks.render.ViewModel;
import me.gavin.notorious.hack.hacks.render.VoidESP;
import me.gavin.notorious.hack.hacks.render.Weather;
import me.gavin.notorious.hack.hacks.world.BedFucker;
import me.gavin.notorious.hack.hacks.world.Lawnmower;
import me.gavin.notorious.hack.hacks.world.ShulkerJew;
import me.gavin.notorious.setting.Setting;

public class HackManager {

    private final ArrayList hacks = new ArrayList();
    private final ArrayList sortedHacks = new ArrayList();

    public HackManager() {
        this.addHack(new AutoSuicide());
        this.addHack(new ChatModifications());
        this.addHack(new PotionAlert());
        this.addHack(new VisualRange());
        this.addHack(new ClickGUI());
        this.addHack(new DiscordRPC());
        this.addHack(new Font());
        this.addHack(new HackList());
        this.addHack(new WaterMark());
        this.addHack(new AnvilBurrow());
        this.addHack(new AutoCrystal());
        this.addHack(new KillAura());
        this.addHack(new Quiver());
        this.addHack(new SmartOffhand());
        this.addHack(new AutoLog());
        this.addHack(new AutoRespawn());
        this.addHack(new CopyIP());
        this.addHack(new FakePlayer());
        this.addHack(new GhastNotifier());
        this.addHack(new WeaknessLog());
        this.addHack(new AntiVoid());
        this.addHack(new Sprint());
        this.addHack(new Step());
        this.addHack(new Strafe());
        this.addHack(new Velocity());
        this.addHack(new ChestStealer());
        this.addHack(new FastPlace());
        this.addHack(new AntiFog());
        this.addHack(new BlockHighlight());
        this.addHack(new BreakESP());
        this.addHack(new ESP());
        this.addHack(new Fullbright());
        this.addHack(new HellenKeller());
        this.addHack(new PenisESP());
        this.addHack(new StorageESP());
        this.addHack(new ViewModel());
        this.addHack(new VoidESP());
        this.addHack(new Weather());
        this.addHack(new BedFucker());
        this.addHack(new Lawnmower());
        this.addHack(new ShulkerJew());
        this.hacks.sort(this::sortABC);
        this.sortedHacks.addAll(this.hacks);
    }

    public ArrayList getHacks() {
        return this.hacks;
    }

    public ArrayList getSortedHacks() {
        return this.sortedHacks;
    }

    public Hack getHack(Class clazz) {
        Iterator iterator = this.hacks.iterator();

        Hack hack;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            hack = (Hack) iterator.next();
        } while (hack.getClass() != clazz);

        return hack;
    }

    public ArrayList getHacksFromCategory(Hack.Category category) {
        ArrayList tempList = new ArrayList();
        Iterator iterator = this.hacks.iterator();

        while (iterator.hasNext()) {
            Hack hack = (Hack) iterator.next();

            if (hack.getCategory() == category) {
                tempList.add(hack);
            }
        }

        return tempList;
    }

    private void addHack(Hack hack) {
        if (hack.getClass().isAnnotationPresent(RegisterHack.class)) {
            RegisterHack annotation = (RegisterHack) hack.getClass().getAnnotation(RegisterHack.class);

            hack.setName(annotation.name());
            hack.setDescription(annotation.description());
            hack.setCategory(annotation.category());
            hack.setBind(hack.getClass() == ClickGUI.class ? 22 : 0);
            Field[] afield = hack.getClass().getDeclaredFields();
            int i = afield.length;

            for (int j = 0; j < i; ++j) {
                Field field = afield[j];

                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                if (Setting.class.isAssignableFrom(field.getType()) && field.isAnnotationPresent(RegisterSetting.class)) {
                    try {
                        hack.getSettings().add((Setting) field.get(hack));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }

            this.hacks.add(hack);
        }
    }

    private int sortABC(Hack hack1, Hack hack2) {
        return hack1.getName().compareTo(hack2.getName());
    }
}
