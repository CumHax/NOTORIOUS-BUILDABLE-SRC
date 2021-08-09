package me.gavin.notorious.hack.hacks.combat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.mixin.mixins.accessor.IMinecraftMixin;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.util.BlockUtil;
import me.gavin.notorious.util.InventoryUtil;
import me.gavin.notorious.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

@RegisterHack(
    name = "AnvilBurrow",
    description = "Drops a anvil inside of you to act as a burrow.",
    category = Hack.Category.Combat
)
public class AnvilBurrow extends Hack {

    @RegisterSetting
    public final BooleanSetting rotate = new BooleanSetting("Rotate", true);
    @RegisterSetting
    public final BooleanSetting packet = new BooleanSetting("Packet", true);
    @RegisterSetting
    public final BooleanSetting switchToAnvil = new BooleanSetting("SwitchToAnvil", true);
    int slot;
    public float yaw = 0.0F;
    public float pitch = 0.0F;
    public boolean rotating = false;
    private BlockPos placeTarget;
    public ItemAnvilBlock anvil;
    private EntityPlayer finalTarget;

    public void onEnable() {
        this.doAnvilBurrow();
        this.toggle();
    }

    private void doAnvilBurrow() {
        this.finalTarget = AnvilBurrow.mc.player;
        if (this.finalTarget != null) {
            this.placeTarget = this.getTargetPos(this.finalTarget);
        }

        if (this.placeTarget != null && this.finalTarget != null) {
            this.placeAnvil(this.placeTarget);
        }

    }

    public void placeAnvil(BlockPos pos) {
        this.rotateToPos(pos);
        if (this.switchToAnvil.isEnabled() && !this.isHoldingAnvil()) {
            this.switchToAnvil();
        }

        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.isEnabled(), AnvilBurrow.mc.player.isSneaking());
    }

    private boolean isHoldingAnvil() {
        return AnvilBurrow.mc.player.getHeldItemMainhand().getItem() == this.anvil;
    }

    private void switchToAnvil() {
        this.slot = InventoryUtil.getItemSlot(this.anvil);
        if (AnvilBurrow.mc.player.getHeldItemMainhand().getItem() != this.anvil && this.slot != -1) {
            InventoryUtil.switchToSlot(this.slot);
        }

    }

    public List getPlaceableBlocksAboveEntity(Entity target) {
        new BlockPos(Math.floor(AnvilBurrow.mc.player.posX), Math.floor(AnvilBurrow.mc.player.posY), Math.floor(AnvilBurrow.mc.player.posZ));
        ArrayList positions = new ArrayList();

        BlockPos pos;

        for (int i = (int) Math.floor(AnvilBurrow.mc.player.posY + 2.0D); i <= 256 && BlockUtil.isPositionPlaceable(pos = new BlockPos(Math.floor(AnvilBurrow.mc.player.posX), (double) i, Math.floor(AnvilBurrow.mc.player.posZ)), false) != 0 && BlockUtil.isPositionPlaceable(pos, false) != -1 && BlockUtil.isPositionPlaceable(pos, false) != 2; ++i) {
            positions.add(pos);
        }

        return positions;
    }

    public BlockPos getTargetPos(Entity target) {
        double distance = -1.0D;
        BlockPos finalPos = null;
        Iterator iterator = this.getPlaceableBlocksAboveEntity(target).iterator();

        while (iterator.hasNext()) {
            BlockPos pos = (BlockPos) iterator.next();

            if (distance == -1.0D || AnvilBurrow.mc.player.getDistanceSq(pos) < MathUtil.square(distance)) {
                finalPos = pos;
                distance = AnvilBurrow.mc.player.getDistance((double) pos.getX(), (double) pos.getY(), (double) pos.getZ());
            }
        }

        return finalPos;
    }

    public void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
        if (packet) {
            float f = (float) (vec.x - (double) pos.getX());
            float f1 = (float) (vec.y - (double) pos.getY());
            float f2 = (float) (vec.z - (double) pos.getZ());

            AnvilBurrow.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
        } else {
            AnvilBurrow.mc.playerController.processRightClickBlock(AnvilBurrow.mc.player, AnvilBurrow.mc.world, pos, direction, vec, hand);
        }

        AnvilBurrow.mc.player.swingArm(EnumHand.MAIN_HAND);
        ((IMinecraftMixin) AnvilBurrow.mc).setRightClickDelayTimerAccessor(4);
    }

    private void rotateToPos(BlockPos pos) {
        if (this.rotate.isEnabled()) {
            float[] angle = MathUtil.calcAngle(AnvilBurrow.mc.player.getPositionEyes(AnvilBurrow.mc.getRenderPartialTicks()), new Vec3d((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() - 0.5F), (double) ((float) pos.getZ() + 0.5F)));

            this.yaw = angle[0];
            this.pitch = angle[1];
            this.rotating = true;
        }

    }
}
