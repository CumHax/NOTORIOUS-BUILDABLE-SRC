package me.gavin.notorious.hack.hacks.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import me.gavin.notorious.event.events.PlayerLivingUpdateEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.setting.SettingGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@RegisterHack(
    name = "KillAura",
    description = "Attacks entities for you",
    category = Hack.Category.Combat
)
public class KillAura extends Hack {

    public final SettingGroup targets = new SettingGroup("Targets");
    @RegisterSetting
    public final BooleanSetting attackDelay = new BooleanSetting("1.9 Delay", true);
    @RegisterSetting
    public final BooleanSetting players = new BooleanSetting("Players", true);
    @RegisterSetting
    public final BooleanSetting animals = new BooleanSetting("Animals", false);
    @RegisterSetting
    public final BooleanSetting mobs = new BooleanSetting("Mobs", false);
    @RegisterSetting
    public final NumSetting attackSpeed = new NumSetting("Attack Speed", 10.0F, 2.0F, 18.0F, 1.0F);
    @RegisterSetting
    public final NumSetting range = new NumSetting("Range", 4.0F, 1.0F, 6.0F, 0.25F);

    public KillAura() {
        this.players.setGroup(this.targets);
        this.animals.setGroup(this.targets);
        this.mobs.setGroup(this.targets);
    }

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.range.getValue() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onLivingUpdate(PlayerLivingUpdateEvent event) {
        Iterator iterator = KillAura.mc.world.loadedEntityList.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();

            if (!entity.equals(KillAura.mc.player)) {
                if (entity instanceof EntityPlayer && this.players.isEnabled()) {
                    this.attack(entity);
                }

                if (entity instanceof EntityAnimal && this.animals.isEnabled()) {
                    this.attack(entity);
                }

                if (entity instanceof EntityMob && this.mobs.isEnabled()) {
                    this.attack(entity);
                }
            }
        }

    }

    private void attack(Entity entity) {
        if (this.shouldAttack((EntityLivingBase) entity)) {
            if (this.attackDelay.isEnabled()) {
                if (KillAura.mc.player.getCooledAttackStrength(0.0F) >= 1.0F) {
                    KillAura.mc.playerController.attackEntity(KillAura.mc.player, entity);
                    KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
                }
            } else if ((double) ((float) KillAura.mc.player.ticksExisted % this.attackSpeed.getValue()) == 0.0D) {
                KillAura.mc.playerController.attackEntity(KillAura.mc.player, entity);
                KillAura.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }

    }

    private boolean shouldAttack(EntityLivingBase entity) {
        return entity.getDistance(KillAura.mc.player) <= this.range.getValue() && entity.getHealth() > 0.0F;
    }

    private boolean isHoldingSword() {
        return KillAura.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD;
    }
}
