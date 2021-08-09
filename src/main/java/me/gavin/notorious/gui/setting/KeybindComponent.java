package me.gavin.notorious.gui.setting;

import java.awt.Color;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.gui.api.Bindable;
import me.gavin.notorious.gui.api.SettingComponent;
import me.gavin.notorious.hack.hacks.client.ClickGUI;
import me.gavin.notorious.hack.hacks.client.Font;
import me.gavin.notorious.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

public class KeybindComponent extends SettingComponent {

    private final Bindable setting;
    private boolean listening = false;

    public KeybindComponent(Bindable setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
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

        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -822083584);
        Gui.drawRect(this.x, this.y, this.x + 2, this.y + this.height, color);
        if (this.listening) {
            if (font.isEnabled()) {
                Notorious.INSTANCE.fontRenderer.drawStringWithShadow("Bind: Listening...", (double) ((float) this.x + 9.0F), (double) ((float) this.y + 5.0F), Color.WHITE);
            } else {
                float f = (float) this.x + 9.0F;

                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Bind: Listening...", f, (float) this.y + 5.0F, (new Color(255, 255, 255)).getRGB());
            }
        } else if (font.isEnabled()) {
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow("Bind: <" + Keyboard.getKeyName(this.setting.getBind()) + ">", (double) ((float) this.x + 9.0F), (double) ((float) this.y + 5.0F), Color.WHITE);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("Bind: <" + Keyboard.getKeyName(this.setting.getBind()) + ">", (float) this.x + 9.0F, (float) this.y + 5.0F, (new Color(255, 255, 255)).getRGB());
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseInside(mouseX, mouseY) && mouseButton == 0) {
            this.listening = !this.listening;
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {}

    public void keyTyped(char keyChar, int keyCode) {
        if (this.listening) {
            this.listening = false;
            if (keyCode == 211 || keyCode == 14) {
                this.setting.setBind(0);
                return;
            }

            this.setting.setBind(keyCode);
        }

    }

    public int getTotalHeight() {
        return this.height;
    }
}
