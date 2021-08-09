package me.gavin.notorious.hack.hacks.player;

import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.mixin.mixins.accessor.IMinecraftMixin;
import me.gavin.notorious.setting.BooleanSetting;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "FastPlace",
    description = "Use items faster",
    category = Hack.Category.Player
)
public class FastPlace extends Hack {

    @RegisterSetting
    public final BooleanSetting xp = new BooleanSetting("XP", true);
    @RegisterSetting
    public final BooleanSetting crystals = new BooleanSetting("Crystals", true);
    @RegisterSetting
    public final BooleanSetting all = new BooleanSetting("All", false);

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        if (FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE && this.xp.isEnabled()) {
            ((IMinecraftMixin) FastPlace.mc).setRightClickDelayTimerAccessor(0);
        }

        if (FastPlace.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && this.crystals.isEnabled()) {
            ((IMinecraftMixin) FastPlace.mc).setRightClickDelayTimerAccessor(0);
        }

        if (this.all.isEnabled()) {
            ((IMinecraftMixin) FastPlace.mc).setRightClickDelayTimerAccessor(0);
        }

    }
}
