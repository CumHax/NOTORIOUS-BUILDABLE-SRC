package me.gavin.notorious.event.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerModelRotationEvent extends Event {

    private float yaw;
    private float pitch;
    private float bodyYaw;

    public PlayerModelRotationEvent(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setBodyYaw(float yaw) {
        this.bodyYaw = yaw;
    }

    public float getBodyYaw() {
        return this.bodyYaw;
    }
}
