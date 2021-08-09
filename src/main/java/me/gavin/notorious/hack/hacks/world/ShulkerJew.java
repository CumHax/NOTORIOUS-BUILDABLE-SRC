package me.gavin.notorious.hack.hacks.world;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.BlockUtil;
import me.gavin.notorious.util.NColor;
import me.gavin.notorious.util.RenderUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "ShulkerJew",
    description = "Automatically breaks shulkers.",
    category = Hack.Category.World
)
public class ShulkerJew extends Hack {

    @RegisterSetting
    public final NumSetting range = new NumSetting("Range", 5.0F, 0.0F, 6.0F, 0.5F);
    @RegisterSetting
    public final ColorSetting boxColor = new ColorSetting("Box", new NColor(255, 255, 255, 125));
    @RegisterSetting
    public final ColorSetting outlineColor = new ColorSetting("Outline", new NColor(255, 255, 255, 255));
    private BlockPos targetedBlock = null;

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.range.getValue() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onLivingUpdate(PlayerLivingUpdateEvent event) {
        if (this.targetedBlock == null) {
            Iterator iterator = BlockUtil.getSurroundingBlocks(5, true).iterator();

            while (iterator.hasNext()) {
                BlockPos pos = (BlockPos) iterator.next();
                Block block = ShulkerJew.mc.world.getBlockState(pos).getBlock();

                if (block instanceof BlockShulkerBox && ShulkerJew.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE) {
                    this.targetedBlock = pos;
                    break;
                }
            }
        } else {
            if (ShulkerJew.mc.world.getBlockState(this.targetedBlock).getBlock() == Blocks.AIR) {
                this.targetedBlock = null;
                return;
            }

            if (this.targetedBlock.getDistance(ShulkerJew.mc.player.getPosition().getX(), ShulkerJew.mc.player.getPosition().getY(), ShulkerJew.mc.player.getPosition().getZ()) > (double) this.range.getValue()) {
                this.targetedBlock = null;
                return;
            }

            BlockUtil.damageBlock(this.targetedBlock, false, true);
        }

    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        if (this.targetedBlock != null) {
            AxisAlignedBB bb = new AxisAlignedBB(this.targetedBlock);

            RenderUtil.renderFilledBB(bb, this.boxColor.getAsColor());
            RenderUtil.renderOutlineBB(bb, this.outlineColor.getAsColor());
        }

    }
}
