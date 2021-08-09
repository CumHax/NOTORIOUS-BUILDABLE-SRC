package me.gavin.notorious.gui.setting;

import java.awt.Color;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.gui.api.SettingComponent;
import me.gavin.notorious.hack.hacks.client.ClickGUI;
import me.gavin.notorious.hack.hacks.client.Font;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class BooleanComponent extends SettingComponent {

    private final BooleanSetting setting;

    public BooleanComponent(BooleanSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        float time = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).length.getValue();
        float saturation = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).saturation.getValue();
        Font font = (Font) Notorious.INSTANCE.hackManager.getHack(Font.class);
        byte yOffset = 2;
        int color;

        if (((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).colorMode.getMode().equals("Rainbow")) {
            color = ColorUtil.getRGBWave(time, saturation, (long) yOffset * 20L);
        } else {
            color = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).guiColor.getAsColor().getRGB();
        }

        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.setting.isEnabled() ? color : -822083584);
        Gui.drawRect(this.x, this.y, this.x + 2, this.y + this.height, color);
        if (font.isEnabled()) {
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.setting.getName(), (double) ((float) this.x + 9.0F), (double) ((float) this.y + 5.0F), Color.WHITE);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.setting.getName(), (float) this.x + 9.0F, (float) this.y + 5.0F, (new Color(255, 255, 255)).getRGB());
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseInside(mouseX, mouseY) && mouseButton == 0) {
            this.setting.toggle();
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    public void keyTyped(char keyChar, int keyCode) {}

    public int getTotalHeight() {
        return this.height;
    }
}
