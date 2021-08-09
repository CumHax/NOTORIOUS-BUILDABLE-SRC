package me.gavin.notorious.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TickTimer {

    private long ticksPassed = 0L;
    private long lastTicks;

    public TickTimer() {
        this.lastTicks = this.ticksPassed;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        ++this.ticksPassed;
    }

    public boolean hasTicksPassed(long ticks) {
        return this.ticksPassed - this.lastTicks > ticks;
    }

    public void reset() {
        this.lastTicks = this.ticksPassed;
    }
}
