package me.gavin.notorious.hack.hacks.misc;

import java.util.Objects;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.NumSetting;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "AutoLog",
    description = "ez",
    category = Hack.Category.Misc
)
public class AutoLog extends Hack {

    @RegisterSetting
    public final NumSetting health = new NumSetting("HealthToLog", 16.0F, 1.0F, 36.0F, 1.0F);

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        if (AutoLog.mc.player.getHealth() <= this.health.getValue()) {
            ((NetHandlerPlayClient) Objects.requireNonNull(AutoLog.mc.getConnection())).handleDisconnect(new SPacketDisconnect(new TextComponentString("Logged at [" + AutoLog.mc.player.getHealth() + "]")));
            this.toggle();
        }

    }
}
