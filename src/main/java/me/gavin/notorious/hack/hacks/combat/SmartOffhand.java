package me.gavin.notorious.hack.hacks.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@RegisterHack(
    name = "SmartOffhand",
    description = "Automates offhand use.",
    category = Hack.Category.Combat
)
public class SmartOffhand extends Hack {

    @RegisterSetting
    public final ModeSetting mode = new ModeSetting("Mode", "Strict", new String[] { "Strict", "Smart"});
    @RegisterSetting
    public final ModeSetting offhandMode = new ModeSetting("OffhandMode", "Crystal", new String[] { "Crystal", "Gapple"});
    @RegisterSetting
    public final NumSetting health = new NumSetting("HealthToSwitch", 14.0F, 0.5F, 36.0F, 0.5F);
    public int slot;

    public String getMetaData() {
        String heldItem = "";

        if (SmartOffhand.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
            heldItem = "EndCrystal";
        }

        if (SmartOffhand.mc.player.getHeldItemOffhand().getItem() == Items.GOLDEN_APPLE) {
            heldItem = "Gapple";
        }

        if (SmartOffhand.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            heldItem = "Totem";
        }

        return " [" + ChatFormatting.GRAY + "Mode: " + this.mode.getMode() + ChatFormatting.RESET + " | " + ChatFormatting.GRAY + "Holding: " + heldItem + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onUpdate(TickEvent event) {
        if (this.offhandMode.getMode().equals("Crystal") && this.mode.getMode().equals("Smart")) {
            this.doTheThing(Items.END_CRYSTAL);
        }

        if (this.offhandMode.getMode().equals("Gapple") && this.mode.getMode().equals("Smart")) {
            this.doTheThing(Items.GOLDEN_APPLE);
        }

        if (this.mode.getMode().equals("Strict")) {
            this.slot = InventoryUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
            if (SmartOffhand.mc.player.getHeldItemOffhand().getItem() != Items.TOTEM_OF_UNDYING) {
                this.slot = InventoryUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
                if (this.slot != -1) {
                    this.switchToShit();
                }
            }
        }

        this.slot = InventoryUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
        if (SmartOffhand.mc.player.inventory.currentItem != this.slot && SmartOffhand.mc.player.getHealth() > SmartOffhand.mc.player.getHealth()) {
            this.switchToShit();
        }

    }

    public void doTheThing(Item item) {
        if (SmartOffhand.mc.player.getHeldItemOffhand().getItem() != item) {
            this.slot = InventoryUtil.getItemSlot(item);
            if (this.slot != -1 && SmartOffhand.mc.player.getHealth() >= this.health.getValue()) {
                this.switchToShit();
            }
        }

        if (SmartOffhand.mc.player.getHeldItemOffhand().getItem() == item) {
            this.slot = InventoryUtil.getItemSlot(Items.TOTEM_OF_UNDYING);
            if (this.slot != -1 && SmartOffhand.mc.player.getHealth() <= this.health.getValue()) {
                this.switchToShit();
            }
        }

    }

    public void switchToShit() {
        SmartOffhand.mc.playerController.windowClick(SmartOffhand.mc.player.inventoryContainer.windowId, this.slot, 0, ClickType.PICKUP, SmartOffhand.mc.player);
        SmartOffhand.mc.playerController.windowClick(SmartOffhand.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, SmartOffhand.mc.player);
        SmartOffhand.mc.playerController.windowClick(SmartOffhand.mc.player.inventoryContainer.windowId, this.slot, 0, ClickType.PICKUP, SmartOffhand.mc.player);
        SmartOffhand.mc.playerController.updateController();
    }
}
