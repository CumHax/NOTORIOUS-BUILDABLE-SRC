package me.gavin.notorious;

import java.util.Comparator;
import java.util.function.Function;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(
    modid = "notorious",
    name = "Notorious",
    version = "beta-0.3",
    clientSideOnly = true
)
public class NotoriousMod {

    public static final String MOD_ID = "notorious";
    public static final String MOD_NAME = "Notorious";
    public static final String VERSION = "beta-0.3";
    public static final String NAME_VERSION = "Notorious beta-0.3";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        new Notorious();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onTick(PlayerLivingUpdateEvent event) {
        Notorious.INSTANCE.hackManager.getSortedHacks().sort(Comparator.comparing((hack) -> {
            return Integer.valueOf(-Notorious.INSTANCE.fontRenderer.getStringWidth(hack.getName() + hack.getMetaData()));
        }));
    }
}
