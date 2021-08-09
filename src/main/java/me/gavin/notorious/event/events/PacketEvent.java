package me.gavin.notorious.event.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketEvent extends Event {

    private final Packet packet;

    private PacketEvent(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }

    PacketEvent(Packet x0, Object x1) {
        this(x0);
    }

    public static class Receive extends PacketEvent {

        public Receive(Packet packet) {
            super(packet, null);
        }
    }

    public static class Send extends PacketEvent {

        public Send(Packet packet) {
            super(packet, null);
        }
    }
}
