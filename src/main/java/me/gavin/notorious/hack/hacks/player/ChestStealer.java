package me.gavin.notorious.hack.hacks.player;

import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "ChestStealer",
    description = "Steals items out of chests",
    category = Hack.Category.Player
)
public class ChestStealer extends Hack {

    @SubscribeEvent
    public void onEvent(PlayerLivingUpdateEvent event) {
        if (ChestStealer.mc.player.openContainer instanceof ContainerChest) {
            ContainerChest chest = (ContainerChest) ChestStealer.mc.player.openContainer;

            for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); ++i) {
                ItemStack stack = chest.getLowerChestInventory().getStackInSlot(i);

                if (stack != null) {
                    ChestStealer.mc.playerController.windowClick(chest.windowId, i, 0, ClickType.QUICK_MOVE, ChestStealer.mc.player);
                }

                if (this.isChestEmpty(chest)) {
                    ChestStealer.mc.player.closeScreen();
                }
            }
        }

    }

    private boolean isChestEmpty(ContainerChest chest) {
        for (int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); ++i) {
            ItemStack slot = chest.getLowerChestInventory().getStackInSlot(i);

            if (slot != null) {
                return false;
            }
        }

        return true;
    }
}
