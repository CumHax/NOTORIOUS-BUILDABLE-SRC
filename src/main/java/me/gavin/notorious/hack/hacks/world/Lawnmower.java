package me.gavin.notorious.hack.hacks.world;

import java.util.ArrayList;
import java.util.Iterator;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "Lawnmower",
    description = "Mines tall grass and stuff around you",
    category = Hack.Category.World
)
public class Lawnmower extends Hack {

    @RegisterSetting
    public final NumSetting delay = new NumSetting("Delay", 5.0F, 1.0F, 10.0F, 1.0F);
    public ArrayList posList;
    private BlockPos targetBlock;

    @SubscribeEvent
    public void onLivingUpdate(PlayerLivingUpdateEvent event) {
        this.posList = BlockUtil.getSurroundingBlocks(4, true);
        Iterator iterator = this.posList.iterator();

        while (iterator.hasNext()) {
            BlockPos pos = (BlockPos) iterator.next();

            if (this.isMineable(Lawnmower.mc.world.getBlockState(pos).getBlock()) && (double) ((float) Lawnmower.mc.player.ticksExisted % this.delay.getValue()) == 0.0D) {
                BlockUtil.damageBlock(pos, false, true);
            }
        }

    }

    private boolean isMineable(Block block) {
        return block == Blocks.TALLGRASS || block == Blocks.DOUBLE_PLANT || block == Blocks.RED_FLOWER || block == Blocks.YELLOW_FLOWER;
    }
}
