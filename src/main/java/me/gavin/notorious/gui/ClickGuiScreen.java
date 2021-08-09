package me.gavin.notorious.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import me.gavin.notorious.hack.Hack;
import net.minecraft.client.gui.GuiScreen;

public class ClickGuiScreen extends GuiScreen {

    private final ArrayList panels = new ArrayList();

    public ClickGuiScreen() {
        int xoffset = 0;
        Hack.Category[] ahack_category = Hack.Category.values();
        int i = ahack_category.length;

        for (int j = 0; j < i; ++j) {
            Hack.Category category = ahack_category[j];

            this.panels.add(new Panel(category, 10 + xoffset, 10, 100, 15));
            xoffset += 110;
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Iterator iterator = this.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            panel.render(mouseX, mouseY, partialTicks);
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        Iterator iterator = this.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            panel.mouseClicked(mouseX, mouseY, mouseButton);
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        Iterator iterator = this.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            panel.mouseReleased(mouseX, mouseY, mouseButton);
        }

    }

    public void keyTyped(char keychar, int keycode) throws IOException {
        Iterator iterator = this.panels.iterator();

        while (iterator.hasNext()) {
            Panel panel = (Panel) iterator.next();

            panel.keyTyped(keychar, keycode);
        }

        super.keyTyped(keychar, keycode);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
