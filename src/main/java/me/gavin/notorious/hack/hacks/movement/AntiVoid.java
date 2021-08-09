package me.gavin.notorious.hack.hacks.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ModeSetting;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "AntiVoid",
    description = "ez",
    category = Hack.Category.Movement
)
public class AntiVoid extends Hack {

    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("Mode", "TP", new String[] { "TP", "Jump", "Freeze"});

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.mode.getMode() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        double yLevel = AntiVoid.mc.player.posY;

        if (this.mode.getMode().equals("TP")) {
            if (yLevel <= 0.5D) {
                AntiVoid.mc.player.setPosition(AntiVoid.mc.player.posX, AntiVoid.mc.player.posY + 2.0D, AntiVoid.mc.player.posZ);
                this.notorious.messageManager.sendMessage("Attempting to TP out of void hole.");
            }
        } else if (this.mode.getMode().equals("Jump")) {
            if (yLevel <= 0.9D) {
                AntiVoid.mc.player.jump();
                this.notorious.messageManager.sendMessage("Attempting to jump out of void hole.");
            }
        } else if (!AntiVoid.mc.player.noClip && yLevel <= 0.5D) {
            RayTraceResult trace = AntiVoid.mc.world.rayTraceBlocks(AntiVoid.mc.player.getPositionVector(), new Vec3d(AntiVoid.mc.player.posX, 0.0D, AntiVoid.mc.player.posZ), false, false, false);

            if (trace != null && trace.typeOfHit == Type.BLOCK) {
                return;
            }

            AntiVoid.mc.player.setVelocity(0.0D, 0.0D, 0.0D);
            if (AntiVoid.mc.player.getRidingEntity() != null) {
                AntiVoid.mc.player.getRidingEntity().setVelocity(0.0D, 0.0D, 0.0D);
            }
        }

    }
}
