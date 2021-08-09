package me.gavin.notorious.hack.hacks.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "GhastNotifier",
    description = "",
    category = Hack.Category.Misc
)
public class GhastNotifier extends Hack {

    @RegisterSetting
    public final BooleanSetting playSound = new BooleanSetting("PlaySound", true);
    @RegisterSetting
    public final BooleanSetting glow = new BooleanSetting("Glow", true);
    private Set ghasts = new HashSet();

    public void onEnable() {
        if (!this.ghasts.isEmpty()) {
            this.ghasts.clear();
        }

    }

    @SubscribeEvent
    public void onUpdate(LivingUpdateEvent event) {
        Iterator iterator = GhastNotifier.mc.world.loadedEntityList.iterator();

        while (iterator.hasNext()) {
            Entity e = (Entity) iterator.next();

            if (e instanceof EntityGhast && !this.ghasts.contains(e)) {
                this.notorious.messageManager.sendMessage("Ghast detected at X: " + ChatFormatting.RED + ChatFormatting.BOLD + e.getPosition().getX() + ChatFormatting.RESET + " Y: " + ChatFormatting.RED + ChatFormatting.BOLD + e.getPosition().getY() + ChatFormatting.RESET + " Z: " + ChatFormatting.RED + ChatFormatting.BOLD + e.getPosition().getZ() + ChatFormatting.RESET + "!");
                this.ghasts.add(e);
                if (this.playSound.isEnabled()) {
                    GhastNotifier.mc.player.playSound(SoundEvents.BLOCK_ANVIL_DESTROY, 1.0F, 1.0F);
                }

                if (this.glow.isEnabled()) {
                    e.setGlowing(true);
                }
            }
        }

    }
}
