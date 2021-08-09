package me.gavin.notorious.event;

import java.util.Iterator;
import me.gavin.notorious.Notorious;
import me.gavin.notorious.hack.Hack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;

public class EventProcessor {

    public EventProcessor() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 0) {
                return;
            }

            Iterator iterator = Notorious.INSTANCE.hackManager.getHacks().iterator();

            while (iterator.hasNext()) {
                Hack hack = (Hack) iterator.next();

                if (hack.getBind() == Keyboard.getEventKey()) {
                    hack.toggle();
                }
            }
        }

    }
}
