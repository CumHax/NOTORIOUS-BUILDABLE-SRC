package me.gavin.notorious.fakegui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.hack.Hack;
import net.minecraft.client.gui.Gui;

public class Frame {

    int x;
    int y;
    int width;
    int height;
    Hack.Category category;
    ArrayList buttons;

    public Frame(Hack.Category category, int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 240;
        this.category = category;
        this.buttons = new ArrayList();
        int offsetY = 14;

        for (Iterator iterator = Notorious.INSTANCE.hackManager.getHacksFromCategory(category).iterator(); iterator.hasNext(); offsetY += 14) {
            Hack hack = (Hack) iterator.next();

            this.buttons.add(new ModuleButton(hack, x, y + offsetY, this));
        }

        this.height = offsetY;
    }

    public void render(int mouseX, int mouseY) {
        Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.category.toString(), (double) (this.x + 2), (double) (this.y + 2), new Color(255, 255, 255, 255));
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, (new Color(0, 0, 0, 100)).getRGB());
        Iterator iterator = this.buttons.iterator();

        while (iterator.hasNext()) {
            ModuleButton button = (ModuleButton) iterator.next();

            button.draw(mouseX, mouseY);
        }

    }

    public void onClick(int x, int y, int moduleButton) {
        Iterator iterator = this.buttons.iterator();

        while (iterator.hasNext()) {
            ModuleButton button = (ModuleButton) iterator.next();

            button.onClick(x, y, moduleButton);
        }

    }
}
