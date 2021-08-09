package me.gavin.notorious.hack.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Consumer;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.ColorUtil;
import me.gavin.notorious.util.RenderUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "VoidESP",
    description = "shows void holes",
    category = Hack.Category.Render
)
public class VoidESP extends Hack {

    @RegisterSetting
    public final NumSetting range = new NumSetting("Range", 15.0F, 1.0F, 20.0F, 1.0F);
    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("Mode", "Both", new String[] { "Both", "Outline", "Box"});
    @RegisterSetting
    public final ColorSetting outlineColor = new ColorSetting("OutlineColor", 255, 255, 255, 125);
    @RegisterSetting
    public final ColorSetting boxColor = new ColorSetting("BoxColor", 255, 255, 255, 125);
    @RegisterSetting
    public final BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
    @RegisterSetting
    public final NumSetting saturation = new NumSetting("Saturation", 0.6F, 0.1F, 1.0F, 0.1F);
    @RegisterSetting
    public final NumSetting time = new NumSetting("RainbowLength", 8.0F, 1.0F, 15.0F, 1.0F);
    public final ArrayList voidBlocks = new ArrayList();
    boolean outline = false;
    boolean fill = false;

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.range.getValue() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        if (VoidESP.mc.player != null) {
            this.voidBlocks.clear();
            Vec3i player_pos = new Vec3i(VoidESP.mc.player.posX, VoidESP.mc.player.posY, VoidESP.mc.player.posZ);

            for (int x = (int) ((float) player_pos.getX() - this.range.getValue()); (float) x < (float) player_pos.getX() + this.range.getValue(); ++x) {
                for (int z = (int) ((float) player_pos.getZ() - this.range.getValue()); (float) z < (float) player_pos.getZ() + this.range.getValue(); ++z) {
                    for (int y = (int) ((float) player_pos.getY() + this.range.getValue()); (float) y > (float) player_pos.getY() - this.range.getValue(); --y) {
                        BlockPos blockPos = new BlockPos(x, y, z);

                        if (this.is_void_hole(blockPos)) {
                            this.voidBlocks.add(blockPos);
                        }
                    }
                }
            }

        }
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
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

        (new ArrayList(this.voidBlocks)).forEach((blockPos) -> {
            AxisAlignedBB bb = VoidESP.mc.world.getBlockState(blockPos).getSelectedBoundingBox(VoidESP.mc.world, blockPos);
            Color rainbowColor = ColorUtil.colorRainbow((int) this.time.getValue(), this.saturation.getValue(), 1.0F);

            if (this.outline) {
                if (this.rainbow.isEnabled()) {
                    RenderUtil.renderOutlineBB(bb, rainbowColor);
                } else {
                    RenderUtil.renderOutlineBB(bb, this.outlineColor.getAsColor());
                }
            }

            if (this.fill) {
                if (this.rainbow.isEnabled()) {
                    RenderUtil.renderFilledBB(bb, rainbowColor);
                } else {
                    RenderUtil.renderFilledBB(bb, this.boxColor.getAsColor());
                }
            }

        });
    }

    public boolean is_void_hole(BlockPos blockPos) {
        return blockPos.getY() != 0 ? false : VoidESP.mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR;
    }
}
