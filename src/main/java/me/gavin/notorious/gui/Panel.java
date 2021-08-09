package me.gavin.notorious.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.gui.api.AbstractDragComponent;
import me.gavin.notorious.gui.api.AbstractToggleContainer;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.hacks.client.ClickGUI;
import me.gavin.notorious.hack.hacks.client.Font;
import me.gavin.notorious.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Panel extends AbstractDragComponent {

    private final ArrayList buttons = new ArrayList();
    private final Hack.Category category;

    public Panel(Hack.Category category, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.category = category;
        Iterator iterator = Notorious.INSTANCE.hackManager.getHacksFromCategory(category).iterator();

        while (iterator.hasNext()) {
            Hack hack = (Hack) iterator.next();

            this.buttons.add(new Button(hack, x, y, width, 15));
        }

    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        Font font = (Font) Notorious.INSTANCE.hackManager.getHack(Font.class);
        float time = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).length.getValue();
        float saturation = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).saturation.getValue();
        int color;

        if (((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).colorMode.getMode().equals("Rainbow")) {
            color = ColorUtil.getRainbow(time, saturation);
        } else {
            color = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).guiColor.getAsColor().getRGB();
        }

        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, color);
        if (font.isEnabled()) {
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.category.name(), (double) ((float) this.x + 3.0F), (double) ((float) this.y + 3.0F), Color.WHITE);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.category.name(), (float) this.x + 3.0F, (float) this.y + 3.0F, (new Color(255, 255, 255)).getRGB());
        }

        int yOffset = this.height;
        Iterator iterator = this.buttons.iterator();

        while (iterator.hasNext()) {
            AbstractToggleContainer button = (AbstractToggleContainer) iterator.next();

            button.x = this.x;
            button.y = this.y + yOffset;
            yOffset += button.getTotalHeight();
            button.render(mouseX, mouseY, partialTicks);
        }

        this.updateDragPosition(mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseInside(mouseX, mouseY) && mouseButton == 0) {
            this.startDragging(mouseX, mouseY);
        }

        Iterator iterator = this.buttons.iterator();

        while (iterator.hasNext()) {
            AbstractToggleContainer button = (AbstractToggleContainer) iterator.next();

            button.mouseClicked(mouseX, mouseY, mouseButton);
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.stopDragging(mouseX, mouseY);
        }

        Iterator iterator = this.buttons.iterator();

        while (iterator.hasNext()) {
            AbstractToggleContainer button = (AbstractToggleContainer) iterator.next();

            button.mouseReleased(mouseX, mouseY, mouseButton);
        }

    }

    public void keyTyped(char keyChar, int keyCode) {
        Iterator iterator = this.buttons.iterator();

        while (iterator.hasNext()) {
            AbstractToggleContainer button = (AbstractToggleContainer) iterator.next();

            button.keyTyped(keyChar, keyCode);
        }

    }

    public ArrayList getButtons() {
        return this.buttons;
    }
}
