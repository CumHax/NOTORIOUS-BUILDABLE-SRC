package me.gavin.notorious.hack.hacks.chat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "VisualRange",
    description = "Sends a message in chat when a player enters your range.",
    category = Hack.Category.Chat
)
public class VisualRange extends Hack {

    private Set players = new HashSet();

    public void onEnable() {
        if (!this.players.isEmpty()) {
            this.players.clear();
        }

    }

    @SubscribeEvent
    public void onUpdate(LivingUpdateEvent event) {
        Iterator iterator = VisualRange.mc.world.loadedEntityList.iterator();

        while (iterator.hasNext()) {
            Entity e = (Entity) iterator.next();

            if (e instanceof EntityPlayer && !this.players.contains(e)) {
                this.notorious.messageManager.sendMessage(ChatFormatting.RED + e.getName() + ChatFormatting.RESET + " has entered your range at X: " + ChatFormatting.RED + ChatFormatting.BOLD + e.getPosition().getX() + ChatFormatting.RESET + " Y: " + ChatFormatting.RED + ChatFormatting.BOLD + e.getPosition().getY() + ChatFormatting.RESET + " Z: " + ChatFormatting.RED + ChatFormatting.BOLD + e.getPosition().getZ() + ChatFormatting.RESET + "!");
                this.players.add(e);
            }
        }

    }
}
