package me.gavin.notorious.hack.hacks.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "AutoRespawn",
    description = "Respawn automatically",
    category = Hack.Category.Misc
)
public class AutoRespawn extends Hack {

    @RegisterSetting
    public final BooleanSetting antiDeathScreen = new BooleanSetting("Respawn", true);
    @RegisterSetting
    public final BooleanSetting deathCoord = new BooleanSetting("DeathCoords", true);
    String deathCoords = "X:0 Y:0 Z:0";

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.deathCoords + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onDeath(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            int x = AutoRespawn.mc.player.getPosition().getX();
            int y = AutoRespawn.mc.player.getPosition().getY();
            int z = AutoRespawn.mc.player.getPosition().getZ();

            if (this.antiDeathScreen.isEnabled()) {
                event.setCanceled(true);
            }

            if (AutoRespawn.mc.player.getHealth() <= 0.0F) {
                AutoRespawn.mc.player.respawnPlayer();
            }

            if (this.deathCoord.isEnabled()) {
                if (AutoRespawn.mc.player.dimension == -1) {
                    this.notorious.messageManager.sendMessage("You died at X: " + ChatFormatting.RED + ChatFormatting.BOLD + x + ChatFormatting.RESET + " Y: " + ChatFormatting.RED + ChatFormatting.BOLD + y + ChatFormatting.RESET + " Z: " + ChatFormatting.RED + ChatFormatting.BOLD + z + ChatFormatting.RESET + " Dimension: " + ChatFormatting.RED + ChatFormatting.BOLD + "Nether");
                }

                if (AutoRespawn.mc.player.dimension == 0) {
                    this.notorious.messageManager.sendMessage("You died at X: " + ChatFormatting.RED + ChatFormatting.BOLD + x + ChatFormatting.RESET + " Y: " + ChatFormatting.RED + ChatFormatting.BOLD + y + ChatFormatting.RESET + " Z: " + ChatFormatting.RED + ChatFormatting.BOLD + z + ChatFormatting.RESET + " Dimension: " + ChatFormatting.GREEN + ChatFormatting.BOLD + "Overworld");
                }

                if (AutoRespawn.mc.player.dimension == 1) {
                    this.notorious.messageManager.sendMessage("You died at X: " + ChatFormatting.RED + ChatFormatting.BOLD + x + ChatFormatting.RESET + " Y: " + ChatFormatting.RED + ChatFormatting.BOLD + y + ChatFormatting.RESET + " Z: " + ChatFormatting.RED + ChatFormatting.BOLD + z + ChatFormatting.RESET + " Dimension: " + ChatFormatting.DARK_PURPLE + ChatFormatting.BOLD + "End");
                }
            }

            this.deathCoords = "X:" + x + " Y:" + y + " Z:" + z;
        }

    }
}
