package me.gavin.notorious.hack.hacks.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import net.minecraft.network.play.client.CPacketPlayer.Position;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "Step",
    description = "Automatically moves you up a block",
    category = Hack.Category.Movement
)
public class Step extends Hack {

    @RegisterSetting
    public final ModeSetting stepType = new ModeSetting("StepType", "NCP", new String[] { "NCP", "Vanilla"});
    @RegisterSetting
    public final NumSetting stepHeight = new NumSetting("Height", 2.0F, 0.5F, 3.0F, 0.5F);
    private int ticks;

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.stepType.getMode() + ChatFormatting.RESET + " | " + ChatFormatting.GRAY + this.stepHeight.getValue() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onLivingUpdate(PlayerLivingUpdateEvent event) {
        if (this.stepType.getMode().equals("NCP")) {
            double[] dir = forward(0.1D);
            boolean twofive = false;
            boolean two = false;
            boolean onefive = false;
            boolean one = false;

            if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.6D, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.4D, dir[1])).isEmpty()) {
                twofive = true;
            }

            if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.1D, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.9D, dir[1])).isEmpty()) {
                two = true;
            }

            if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.6D, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.4D, dir[1])).isEmpty()) {
                onefive = true;
            }

            if (Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.0D, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes(Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 0.6D, dir[1])).isEmpty()) {
                one = true;
            }

            if (Step.mc.player.collidedHorizontally && (Step.mc.player.moveForward != 0.0F || Step.mc.player.moveStrafing != 0.0F) && Step.mc.player.onGround) {
                double[] twoFiveOffset;
                int i;

                if (one && (double) this.stepHeight.getValue() >= 1.0D) {
                    twoFiveOffset = new double[] { 0.42D, 0.753D};

                    for (i = 0; i < twoFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket(new Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }

                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0D, Step.mc.player.posZ);
                    this.ticks = 1;
                }

                if (onefive && (double) this.stepHeight.getValue() >= 1.5D) {
                    twoFiveOffset = new double[] { 0.42D, 0.75D, 1.0D, 1.16D, 1.23D, 1.2D};

                    for (i = 0; i < twoFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket(new Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }

                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.5D, Step.mc.player.posZ);
                    this.ticks = 1;
                }

                if (two && (double) this.stepHeight.getValue() >= 2.0D) {
                    twoFiveOffset = new double[] { 0.42D, 0.78D, 0.63D, 0.51D, 0.9D, 1.21D, 1.45D, 1.43D};

                    for (i = 0; i < twoFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket(new Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }

                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.0D, Step.mc.player.posZ);
                    this.ticks = 2;
                }

                if (twofive && (double) this.stepHeight.getValue() >= 2.5D) {
                    twoFiveOffset = new double[] { 0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D};

                    for (i = 0; i < twoFiveOffset.length; ++i) {
                        Step.mc.player.connection.sendPacket(new Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                    }

                    Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.5D, Step.mc.player.posZ);
                    this.ticks = 2;
                }
            }
        } else {
            Step.mc.player.stepHeight = this.stepHeight.getValue();
        }

    }

    public static double[] forward(double speed) {
        float forward = Step.mc.player.movementInput.moveForward;
        float side = Step.mc.player.movementInput.moveStrafe;
        float yaw = Step.mc.player.prevRotationYaw + (Step.mc.player.rotationYaw - Step.mc.player.prevRotationYaw) * Step.mc.getRenderPartialTicks();

        if (forward != 0.0F) {
            if (side > 0.0F) {
                yaw += (float) (forward > 0.0F ? -45 : 45);
            } else if (side < 0.0F) {
                yaw += (float) (forward > 0.0F ? 45 : -45);
            }

            side = 0.0F;
            if (forward > 0.0F) {
                forward = 1.0F;
            } else if (forward < 0.0F) {
                forward = -1.0F;
            }
        }

        double sin = Math.sin(Math.toRadians((double) (yaw + 90.0F)));
        double cos = Math.cos(Math.toRadians((double) (yaw + 90.0F)));
        double posX = (double) forward * speed * cos + (double) side * speed * sin;
        double posZ = (double) forward * speed * sin - (double) side * speed * cos;

        return new double[] { posX, posZ};
    }

    public void onDisable() {
        Step.mc.player.stepHeight = 0.5F;
    }
}
