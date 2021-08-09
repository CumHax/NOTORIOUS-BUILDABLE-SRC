package me.gavin.notorious.hack.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.Iterator;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@RegisterHack(
    name = "StorageESP",
    description = "Draws a box around storage stuff.",
    category = Hack.Category.Render
)
public class StorageESP extends Hack {

    @RegisterSetting
    public final ModeSetting renderMode = new ModeSetting("RenderMode", "Both", new String[] { "Both", "Outline", "Box"});
    @RegisterSetting
    public final ModeSetting colorMode = new ModeSetting("ColorMode", "Custom", new String[] { "Static", "Custom"});
    @RegisterSetting
    public final ColorSetting outlineColor = new ColorSetting("Outline", new Color(255, 255, 255, 255));
    @RegisterSetting
    public final ColorSetting boxColor = new ColorSetting("Box", new Color(255, 255, 255, 125));
    @RegisterSetting
    public final NumSetting lineWidth = new NumSetting("LineWidth", 2.0F, 0.1F, 4.0F, 0.1F);
    @RegisterSetting
    public final BooleanSetting chest = new BooleanSetting("Chest", true);
    @RegisterSetting
    public final BooleanSetting enderChest = new BooleanSetting("EnderChest", true);
    @RegisterSetting
    public final BooleanSetting hopper = new BooleanSetting("Hopper", true);
    @RegisterSetting
    public final BooleanSetting shulkerBox = new BooleanSetting("ShulkerBox", true);
    private boolean outline = false;
    private boolean fill = false;
    public final Color chestOutlineStatic = new Color(139, 69, 19, 255);
    public final Color chestBoxStatic = new Color(205, 133, 63, 125);
    public final Color enderChestOutlineStatic = new Color(75, 0, 130, 255);
    public final Color enderChestBoxStatic = new Color(138, 43, 226, 125);
    public final Color hopperOutlineStatic = new Color(105, 105, 105, 255);
    public final Color hopperBoxStatic = new Color(169, 169, 169, 125);
    public final Color shulkerOutlineStatic = new Color(199, 21, 133, 255);
    public final Color shulkerBoxStatic = new Color(234, 16, 130, 125);

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.renderMode.getMode() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        Iterator iterator = StorageESP.mc.world.loadedTileEntityList.iterator();

        while (iterator.hasNext()) {
            TileEntity e = (TileEntity) iterator.next();
            AxisAlignedBB bb = new AxisAlignedBB(e.getPos());

            if (this.renderMode.getMode().equals("Both")) {
                this.outline = true;
                this.fill = true;
            } else if (this.renderMode.getMode().equals("Outline")) {
                this.outline = true;
                this.fill = false;
            } else {
                this.fill = true;
                this.outline = false;
            }

            if (e instanceof TileEntityChest && this.chest.isEnabled()) {
                if (this.colorMode.getMode().equals("Custom")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.outlineColor.getAsColor());
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.boxColor.getAsColor());
                    }
                }

                if (this.colorMode.getMode().equals("Static")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.chestOutlineStatic);
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.chestBoxStatic);
                    }
                }
            }

            if (e instanceof TileEntityEnderChest && this.enderChest.isEnabled()) {
                if (this.colorMode.getMode().equals("Custom")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.outlineColor.getAsColor());
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.boxColor.getAsColor());
                    }
                }

                if (this.colorMode.getMode().equals("Static")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.enderChestOutlineStatic);
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.enderChestBoxStatic);
                    }
                }
            }

            if (e instanceof TileEntityHopper && this.hopper.isEnabled()) {
                if (this.colorMode.getMode().equals("Custom")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.outlineColor.getAsColor());
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.boxColor.getAsColor());
                    }
                }

                if (this.colorMode.getMode().equals("Static")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.hopperOutlineStatic);
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.hopperBoxStatic);
                    }
                }
            }

            if (e instanceof TileEntityShulkerBox && this.shulkerBox.isEnabled()) {
                if (this.colorMode.getMode().equals("Custom")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.outlineColor.getAsColor());
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.boxColor.getAsColor());
                    }
                }

                if (this.colorMode.getMode().equals("Static")) {
                    GL11.glLineWidth(this.lineWidth.getValue());
                    if (this.outline) {
                        RenderUtil.renderOutlineBB(bb, this.shulkerOutlineStatic);
                    }

                    if (this.fill) {
                        RenderUtil.renderFilledBB(bb, this.shulkerBoxStatic);
                    }
                }
            }
        }

    }
}
