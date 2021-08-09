package me.gavin.notorious.hack.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@RegisterHack(
    name = "PotionAlert",
    description = "Tells you in chat when you are hit by a arrow.",
    category = Hack.Category.Chat
)
public class PotionAlert extends Hack {

    @RegisterSetting
    public final BooleanSetting weakness = new BooleanSetting("Weakness", true);
    @RegisterSetting
    public final BooleanSetting slowness = new BooleanSetting("Slowness", true);
    private boolean hasAnnouncedWeakness = false;
    private boolean hasAnnouncedSlowness = false;

    public String getMetaData() {
        String weakness;

        if (PotionAlert.mc.player.isPotionActive(MobEffects.WEAKNESS)) {
            weakness = ChatFormatting.GREEN + "Weakness";
        } else {
            weakness = ChatFormatting.GRAY + "Weakness";
        }

        String slowness;

        if (PotionAlert.mc.player.isPotionActive(MobEffects.SLOWNESS)) {
            slowness = ChatFormatting.GREEN + "Slowness";
        } else {
            slowness = ChatFormatting.GRAY + "Slowness";
        }

        return " [" + weakness + ChatFormatting.GRAY + ChatFormatting.RESET + " | " + ChatFormatting.GRAY + slowness + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onUpdate(PlayerTickEvent event) {
        if (this.weakness.isEnabled()) {
            if (PotionAlert.mc.player.isPotionActive(MobEffects.WEAKNESS) && !this.hasAnnouncedWeakness) {
                this.notorious.messageManager.sendMessage("RIP bro you now have " + ChatFormatting.GRAY + ChatFormatting.BOLD + "WEAKNESS" + ChatFormatting.RESET + "!");
                this.hasAnnouncedWeakness = true;
            }

            if (!PotionAlert.mc.player.isPotionActive(MobEffects.WEAKNESS) && this.hasAnnouncedWeakness) {
                this.notorious.messageManager.sendMessage("Ey bro good job you don\'t have " + ChatFormatting.GRAY + ChatFormatting.BOLD + "WEAKNESS" + ChatFormatting.RESET + " anymore!");
                this.hasAnnouncedWeakness = false;
            }
        }

        if (this.slowness.isEnabled()) {
            if (PotionAlert.mc.player.isPotionActive(MobEffects.SLOWNESS) && !this.hasAnnouncedSlowness) {
                this.notorious.messageManager.sendMessage("RIP bro you now have " + ChatFormatting.AQUA + ChatFormatting.BOLD + "SLOWNESS" + ChatFormatting.RESET + "!");
                this.hasAnnouncedSlowness = true;
            }

            if (!PotionAlert.mc.player.isPotionActive(MobEffects.SLOWNESS) && this.hasAnnouncedSlowness) {
                this.notorious.messageManager.sendMessage("Ey bro good job you don\'t have " + ChatFormatting.AQUA + ChatFormatting.BOLD + "SLOWNESS" + ChatFormatting.RESET + " anymore!");
                this.hasAnnouncedSlowness = false;
            }
        }

    }
}
