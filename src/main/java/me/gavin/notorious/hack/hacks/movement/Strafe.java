package me.gavin.notorious.hack.hacks.movement;

import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "AutoHop",
    description = "ching chong strafe",
    category = Hack.Category.Movement
)
public class Strafe extends Hack {

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        if (Strafe.mc.player.onGround && !Strafe.mc.player.isSneaking() && !Strafe.mc.player.isInLava() && !Strafe.mc.player.isInWater()) {
            Strafe.mc.player.jump();
        }

    }
}
