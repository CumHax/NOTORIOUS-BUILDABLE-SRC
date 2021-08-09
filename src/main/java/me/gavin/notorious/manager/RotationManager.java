package me.gavin.notorious.manager;

import me.gavin.notorious.event.events.PlayerWalkingUpdateEvent;
import me.gavin.notorious.stuff.IMinecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RotationManager implements IMinecraft {

    private float previousYaw;
    private float previousPitch;
    public float desiredYaw;
    public float desiredPitch;

    public RotationManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTickPre(PlayerWalkingUpdateEvent.Pre event) {
        this.previousYaw = RotationManager.mc.player.rotationYaw;
        this.previousPitch = RotationManager.mc.player.rotationPitch;
        RotationManager.mc.player.rotationYaw = this.desiredYaw;
        RotationManager.mc.player.rotationPitch = this.desiredPitch;
    }

    @SubscribeEvent
    public void onTickPost(PlayerWalkingUpdateEvent.Post event) {
        RotationManager.mc.player.rotationPitch = this.previousPitch;
        RotationManager.mc.player.rotationYaw = this.previousYaw;
        this.desiredYaw = RotationManager.mc.player.rotationYaw;
        this.desiredPitch = RotationManager.mc.player.rotationPitch;
    }
}
