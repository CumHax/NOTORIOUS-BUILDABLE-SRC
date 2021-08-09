package me.gavin.notorious.hack.hacks.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "Quiver",
    description = "Automatically places a totem in your offhand.",
    category = Hack.Category.Combat
)
public class Quiver extends Hack {

    @RegisterSetting
    public final NumSetting tickDelay = new NumSetting("HoldTime", 3.0F, 0.0F, 8.0F, 0.5F);
    @RegisterSetting
    public final BooleanSetting autoEffect = new BooleanSetting("AutoEffect", true);
    public int slot;

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.tickDelay.getValue() + ChatFormatting.RESET + "]";
    }

    public void onEnable() {
        this.notorious.messageManager.sendMessage("When you try to use the bow let go quickly.");
    }

    @SubscribeEvent
    public void onUpdate(PlayerLivingUpdateEvent event) {
        if (Quiver.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && Quiver.mc.player.getItemInUseMaxCount() >= 3) {
            if (this.autoEffect.isEnabled()) {
                this.switchToArrow();
            }

            Quiver.mc.player.connection.sendPacket(new Rotation(Quiver.mc.player.cameraYaw, -90.0F, true));
            Quiver.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem());
            this.toggle();
            Quiver.mc.player.stopActiveHand();
        }

    }

    public void switchToArrow() {
        this.slot = InventoryUtil.getItemSlot(Items.TIPPED_ARROW);
        Quiver.mc.playerController.windowClick(Quiver.mc.player.inventoryContainer.windowId, this.slot, 0, ClickType.PICKUP, Quiver.mc.player);
        Quiver.mc.playerController.windowClick(Quiver.mc.player.inventoryContainer.windowId, 9, 0, ClickType.PICKUP, Quiver.mc.player);
        Quiver.mc.playerController.windowClick(Quiver.mc.player.inventoryContainer.windowId, this.slot, 0, ClickType.PICKUP, Quiver.mc.player);
        Quiver.mc.playerController.updateController();
    }
}
