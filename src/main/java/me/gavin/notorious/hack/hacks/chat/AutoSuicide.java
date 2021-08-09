package me.gavin.notorious.hack.hacks.chat;

import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;

@RegisterHack(
    name = "AutoSuicide",
    description = "Automatically kills you.",
    category = Hack.Category.Chat
)
public class AutoSuicide extends Hack {

    public void onEnable() {
        if (AutoSuicide.mc.player != null) {
            AutoSuicide.mc.player.sendChatMessage("/kill");
        }

        this.toggle();
    }
}
