package me.gavin.notorious.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class InventoryUtil {

    private static Boolean found = Boolean.valueOf(false);
    private static int i;

    public static void checkSlots(Item item) {
        for (InventoryUtil.i = 9; InventoryUtil.i <= 36; ++InventoryUtil.i) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(InventoryUtil.i).getItem().equals(item)) {
                InventoryUtil.found = Boolean.valueOf(true);
                break;
            }
        }

    }

    public static int checkSlotsBlock(Block block) {
        for (InventoryUtil.i = 9; InventoryUtil.i <= 36; ++InventoryUtil.i) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(InventoryUtil.i).getItem().equals(block)) {
                InventoryUtil.found = Boolean.valueOf(true);
                break;
            }
        }

        return -1;
    }

    public static void switchToSlot(int slot) {
        Minecraft.getMinecraft().player.connection.sendPacket(new CPacketHeldItemChange(slot));
        Minecraft.getMinecraft().player.inventory.currentItem = slot;
        Minecraft.getMinecraft().playerController.updateController();
    }

    public static boolean isChestEmpty(ContainerChest c) {
        for (int i = 0; i < c.getLowerChestInventory().getSizeInventory(); ++i) {
            ItemStack slot = c.getLowerChestInventory().getStackInSlot(i);

            if (slot != null) {
                return false;
            }
        }

        return true;
    }

    public static int getItemSlot(Item items) {
        for (int i = 0; i < 36; ++i) {
            Item item = Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem();

            if (item == items) {
                if (i < 9) {
                    i += 36;
                }

                return i;
            }
        }

        return -1;
    }

    public static void moveItemToSlot(Integer startSlot, Integer endSlot) {
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, startSlot.intValue(), 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
        Minecraft.getMinecraft().playerController.windowClick(Minecraft.getMinecraft().player.inventoryContainer.windowId, endSlot.intValue(), 0, ClickType.PICKUP, Minecraft.getMinecraft().player);
    }
}
