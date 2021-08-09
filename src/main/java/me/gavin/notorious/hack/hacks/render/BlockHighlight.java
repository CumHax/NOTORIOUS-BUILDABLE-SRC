package me.gavin.notorious.hack.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.ColorUtil;
import me.gavin.notorious.util.NColor;
import me.gavin.notorious.util.RenderUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@RegisterHack(
    name = "BlockHighlight",
    description = "Draws a bounding box around boxes you are looking at.",
    category = Hack.Category.Render
)
public class BlockHighlight extends Hack {

    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("Mode", "Both", new String[] { "Both", "Outline", "Box"});
    @RegisterSetting
    public final ColorSetting outlineColor = new ColorSetting("OutlineColor", new NColor(255, 255, 255));
    @RegisterSetting
    public final ColorSetting boxColor = new ColorSetting("BoxColor", 255, 255, 255, 125);
    @RegisterSetting
    public final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    @RegisterSetting
    public final NumSetting saturation = new NumSetting("Saturation", 0.6F, 0.1F, 1.0F, 0.1F);
    @RegisterSetting
    public final NumSetting time = new NumSetting("RainbowLength", 8.0F, 1.0F, 15.0F, 1.0F);
    @RegisterSetting
    public final NumSetting lineWidth = new NumSetting("Line Width", 2.0F, 0.1F, 10.0F, 0.1F);
    boolean outline = false;
    boolean fill = false;

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.lineWidth.getValue() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        RayTraceResult result = BlockHighlight.mc.objectMouseOver;
        Color rainbowColor = ColorUtil.colorRainbow((int) this.time.getValue(), this.saturation.getValue(), 1.0F);

        if (this.mode.getMode().equals("Both")) {
            this.outline = true;
            this.fill = true;
        } else if (this.mode.getMode().equals("Outline")) {
            this.outline = true;
            this.fill = false;
        } else {
            this.fill = true;
            this.outline = false;
        }

        if (result != null && result.typeOfHit == Type.BLOCK) {
            AxisAlignedBB box = BlockHighlight.mc.world.getBlockState(result.getBlockPos()).getSelectedBoundingBox(BlockHighlight.mc.world, result.getBlockPos());

            if (result.typeOfHit == Type.BLOCK && BlockHighlight.mc.world.getBlockState(result.getBlockPos()).getBlock() != Blocks.AIR) {
                GL11.glLineWidth(this.lineWidth.getValue());
                if (this.rainbow.isEnabled()) {
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(box, rainbowColor);
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(box, rainbowColor);
                    }
                } else {
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(box, this.outlineColor.getAsColor());
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(box, this.boxColor.getAsColor());
                    }
                }
            }
        }

    }
}
