package me.gavin.notorious.gui.setting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.gui.api.SettingComponent;
import me.gavin.notorious.hack.hacks.client.Font;
import me.gavin.notorious.setting.ColorSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ColorComponent extends SettingComponent {

    private final ColorSetting setting;
    private final ArrayList sliderComponents;
    private boolean open;
    private Font font;

    public ColorComponent(ColorSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.font = (Font) Notorious.INSTANCE.hackManager.getHack(Font.class);
        this.setting = setting;
        this.sliderComponents = new ArrayList();
        this.sliderComponents.add(new SliderComponent(setting.getRed(), x, y, width, height));
        this.sliderComponents.add(new SliderComponent(setting.getGreen(), x, y, width, height));
        this.sliderComponents.add(new SliderComponent(setting.getBlue(), x, y, width, height));
        this.sliderComponents.add(new SliderComponent(setting.getAlpha(), x, y, width, height));
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.setting.getAsColor().getRGB());
        if (this.font.isEnabled()) {
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.setting.getName(), (double) (this.x + 3), (double) (this.y + 3), Color.WHITE);
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.open ? "-" : "+", (double) (this.x + this.width - 8), (double) (this.y + 3), Color.WHITE);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.setting.getName(), (float) (this.x + 3), (float) (this.y + 3), -1);
            String s = this.open ? "-" : "+";
            float f = (float) (this.x + this.width - 8);
            float f1 = (float) (this.y + 3);

            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(s, f, f1, -1);
        }

        if (this.open) {
            int yOffset = this.y + this.height;

            SliderComponent component;

            for (Iterator iterator = this.sliderComponents.iterator(); iterator.hasNext(); yOffset += component.height) {
                component = (SliderComponent) iterator.next();
                component.x = this.x;
                component.y = yOffset;
                component.render(mouseX, mouseY, partialTicks);
            }
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseInside(mouseX, mouseY)) {
            this.open = !this.open;
        }

        if (this.open) {
            Iterator iterator = this.sliderComponents.iterator();

            while (iterator.hasNext()) {
                SliderComponent component = (SliderComponent) iterator.next();

                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.open) {
            Iterator iterator = this.sliderComponents.iterator();

            while (iterator.hasNext()) {
                SliderComponent component = (SliderComponent) iterator.next();

                component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }

    }

    public void keyTyped(char keyChar, int keyCode) {}

    public int getTotalHeight() {
        return this.open ? this.height + this.height * 4 : this.height;
    }
}
