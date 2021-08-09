package me.gavin.notorious.fakegui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import me.gavin.notorious.hack.Hack;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUIScreen extends GuiScreen {

    public static ClickGUIScreen INSTANCE = new ClickGUIScreen();
    ArrayList frames = new ArrayList();

    public ClickGUIScreen() {
        int offset = 0;
        Hack.Category[] ahack_category = Hack.Category.values();
        int i = ahack_category.length;

        for (int j = 0; j < i; ++j) {
            Hack.Category category = ahack_category[j];

            this.frames.add(new Frame(category, 10 + offset, 20));
            offset += 110;
        }

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        Iterator iterator = this.frames.iterator();

        while (iterator.hasNext()) {
            Frame frame = (Frame) iterator.next();

            frame.render(mouseX, mouseY);
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        Iterator iterator = this.frames.iterator();

        while (iterator.hasNext()) {
            Frame frame = (Frame) iterator.next();

            frame.onClick(mouseX, mouseY, mouseButton);
        }

    }
}
