package me.gavin.notorious.gui;

import java.awt.Color;
import java.util.Iterator;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.gui.api.AbstractToggleContainer;
import me.gavin.notorious.gui.api.SettingComponent;
import me.gavin.notorious.gui.setting.BooleanComponent;
import me.gavin.notorious.gui.setting.ColorComponent;
import me.gavin.notorious.gui.setting.KeybindComponent;
import me.gavin.notorious.gui.setting.ModeComponent;
import me.gavin.notorious.gui.setting.SliderComponent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.hacks.client.ClickGUI;
import me.gavin.notorious.hack.hacks.client.Font;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.setting.Setting;
import me.gavin.notorious.stuff.IMinecraft;
import me.gavin.notorious.util.ColorUtil;
import net.minecraft.client.gui.Gui;

public class Button extends AbstractToggleContainer implements IMinecraft {

    private final Hack hack;

    public Button(Hack hack, int x, int y, int width, int height) {
        super(hack, x, y, width, height);
        this.hack = hack;
        Iterator iterator = hack.getSettings().iterator();

        while (iterator.hasNext()) {
            Setting setting = (Setting) iterator.next();

            if (setting instanceof BooleanSetting) {
                this.components.add(new BooleanComponent((BooleanSetting) setting, x, y, width, height));
            } else if (setting instanceof ModeSetting) {
                this.components.add(new ModeComponent((ModeSetting) setting, x, y, width, height));
            } else if (setting instanceof NumSetting) {
                this.components.add(new SliderComponent((NumSetting) setting, x, y, width, height));
            } else if (setting instanceof ColorSetting) {
                this.components.add(new ColorComponent((ColorSetting) setting, x, y, width, height));
            }
        }

        this.components.add(new KeybindComponent(hack, x + 5, y, width, height));
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        Font font = (Font) Notorious.INSTANCE.hackManager.getHack(Font.class);
        int renderYOffset = this.height;
        float time = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).length.getValue();
        float saturation = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).saturation.getValue();
        int intRainbow;

        if (((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).colorMode.getMode().equals("Rainbow")) {
            intRainbow = ColorUtil.getRainbow(time, saturation);
        } else {
            intRainbow = ((ClickGUI) Notorious.INSTANCE.hackManager.getHack(ClickGUI.class)).guiColor.getAsColor().getRGB();
        }

        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.isMouseInside(mouseX, mouseY) ? -871625716 : -872415232);
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.hack.isEnabled() ? intRainbow : -872415232);
        if (this.open) {
            if (font.isEnabled()) {
                Notorious.INSTANCE.fontRenderer.drawStringWithShadow("-", (double) ((float) (this.x + this.width) - 8.0F), (double) ((float) this.y + 5.0F), Color.WHITE);
            } else {
                Button.mc.fontRenderer.drawStringWithShadow("-", (float) (this.x + this.width) - 8.0F, (float) this.y + 5.0F, (new Color(255, 255, 255)).getRGB());
            }
        } else if (font.isEnabled()) {
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow("+", (double) ((float) (this.x + this.width) - 8.0F), (double) ((float) this.y + 5.0F), Color.WHITE);
        } else {
            Button.mc.fontRenderer.drawStringWithShadow("+", (float) (this.x + this.width) - 8.0F, (float) this.y + 5.0F, (new Color(255, 255, 255)).getRGB());
        }

        if (font.isEnabled()) {
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.hack.getName(), (double) ((float) this.x + 2.0F), (double) ((float) this.y + 5.0F), Color.WHITE);
        } else {
            Button.mc.fontRenderer.drawStringWithShadow(this.hack.getName(), (float) this.x + 2.0F, (float) this.y + 5.0F, (new Color(255, 255, 255)).getRGB());
        }

        if (this.open) {
            Iterator iterator = this.components.iterator();

            while (iterator.hasNext()) {
                SettingComponent component = (SettingComponent) iterator.next();

                component.x = this.x;
                component.y = this.y + renderYOffset;
                renderYOffset += component.getTotalHeight();
                component.render(mouseX, mouseY, partialTicks);
            }
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        Font font = (Font) Notorious.INSTANCE.hackManager.getHack(Font.class);

        if (this.isMouseInside(mouseX, mouseY)) {
            if (font.isEnabled()) {
                Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.hack.getDescription(), (double) ((float) mouseX + 2.0F), (double) ((float) mouseY + 2.0F), Color.WHITE);
            } else {
                Button.mc.fontRenderer.drawStringWithShadow(this.hack.getDescription(), (float) mouseX + 2.0F, (float) mouseY + 2.0F, (new Color(255, 255, 255)).getRGB());
            }

            if (mouseButton == 0) {
                this.hack.toggle();
            } else if (mouseButton == 1) {
                this.open = !this.open;
            }
        }

        if (this.open) {
            Iterator iterator = this.components.iterator();

            while (iterator.hasNext()) {
                SettingComponent component = (SettingComponent) iterator.next();

                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.open) {
            Iterator iterator = this.components.iterator();

            while (iterator.hasNext()) {
                SettingComponent component = (SettingComponent) iterator.next();

                component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }

    }

    public void keyTyped(char keyChar, int keyCode) {
        if (this.open) {
            Iterator iterator = this.components.iterator();

            while (iterator.hasNext()) {
                SettingComponent component = (SettingComponent) iterator.next();

                component.keyTyped(keyChar, keyCode);
            }
        }

    }

    public int getTotalHeight() {
        if (!this.open) {
            return this.height;
        } else {
            int h = 0;

            SettingComponent component;

            for (Iterator iterator = this.components.iterator(); iterator.hasNext(); h += component.getTotalHeight()) {
                component = (SettingComponent) iterator.next();
            }

            return this.height + h;
        }
    }
}
