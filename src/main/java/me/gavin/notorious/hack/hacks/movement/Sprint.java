package me.gavin.notorious.hack.hacks.movement;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ModeSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "Sprint",
    description = "Holds sprint.",
    category = Hack.Category.Movement
)
public class Sprint extends Hack {

    @RegisterSetting
    public final ModeSetting sprintMode = new ModeSetting("SprintMode", "Legit", new String[] { "Legit", "Rage"});

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.sprintMode.getMode() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onLivingUpdate(PlayerLivingUpdateEvent event) {
        if (this.sprintMode.getMode().equals("Legit")) {
            if (!Sprint.mc.player.isSneaking() && !Sprint.mc.player.isHandActive() && !Sprint.mc.player.collidedHorizontally && !Sprint.mc.player.isSprinting()) {
                Sprint.mc.player.setSprinting(true);
            }
        } else if ((Sprint.mc.player.moveForward != 0.0F || Sprint.mc.player.moveStrafing != 0.0F) && !Sprint.mc.player.isSprinting()) {
            Sprint.mc.player.setSprinting(true);
        }

    }
}
