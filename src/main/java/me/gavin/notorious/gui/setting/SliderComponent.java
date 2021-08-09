package me.gavin.notorious.gui.setting;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.gui.api.SettingComponent;
import me.gavin.notorious.hack.hacks.client.ClickGUI;
import me.gavin.notorious.hack.hacks.client.Font;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class SliderComponent extends SettingComponent {

    private final NumSetting setting;
    private float sliderWidth;
    private boolean draggingSlider;

    public SliderComponent(NumSetting setting, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setting = setting;
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        this.updateSliderLogic(mouseX, mouseY);
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
        Gui.drawRect(this.x, this.y, this.x + (int) this.sliderWidth, this.y + this.height, color);
        if (font.isEnabled()) {
            Notorious.INSTANCE.fontRenderer.drawStringWithShadow(this.setting.getName() + " <" + this.setting.getValue() + ">", (double) ((float) this.x + 9.0F), (double) ((float) this.y + 5.0F), Color.WHITE);
        } else {
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(this.setting.getName() + " <" + this.setting.getValue() + ">", (float) this.x + 9.0F, (float) this.y + 5.0F, (new Color(255, 255, 255)).getRGB());
        }

    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseInside(mouseX, mouseY) && mouseButton == 0) {
            this.draggingSlider = true;
        }

    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.draggingSlider) {
            this.draggingSlider = false;
        }

    }

    public void keyTyped(char keyChar, int keyCode) {}

    private void updateSliderLogic(int mouseX, int mouseY) {
        float diff = (float) Math.min(this.width, Math.max(0, mouseX - this.x));
        float min = this.setting.getMin();
        float max = this.setting.getMax();

        this.sliderWidth = (float) this.width * (this.setting.getValue() - min) / (max - min);
        if (this.draggingSlider) {
            if (diff == 0.0F) {
                this.setting.setValue(this.setting.getMin());
            } else {
                float value = this.roundToPlace(diff / (float) this.width * (max - min) + min, 1);

                this.setting.setValue(value);
            }
        }

    }

    private float roundToPlace(float value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            BigDecimal bd = new BigDecimal((double) value);

            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.floatValue();
        }
    }

    public int getTotalHeight() {
        return this.height;
    }
}
