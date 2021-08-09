package me.gavin.notorious.hack.hacks.combat;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import me.gavin.notorious.event.events.PacketEvent;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.mixin.mixins.accessor.IMinecraftMixin;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.NColor;
import me.gavin.notorious.util.RenderUtil;
import me.gavin.notorious.util.TickTimer;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.potion.Potion;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@RegisterHack(
    name = "AutoGerald",
    description = "ez",
    category = Hack.Category.Combat
)
public class AutoCrystal extends Hack {

    @RegisterSetting
    private NumSetting attackDistance = new NumSetting("AttackRange", 4.0F, 1.0F, 6.0F, 1.0F);
    @RegisterSetting
    private NumSetting placeDistance = new NumSetting("PlaceRange", 4.0F, 1.0F, 6.0F, 1.0F);
    @RegisterSetting
    private NumSetting minDmg = new NumSetting("MinDmg", 4.0F, 0.1F, 10.0F, 1.0F);
    @RegisterSetting
    private NumSetting maxSelfDmg = new NumSetting("MaxSelfDmg", 15.0F, 1.0F, 30.0F, 1.0F);
    @RegisterSetting
    private NumSetting breakDelay = new NumSetting("BreakDelay", 2.0F, 0.0F, 20.0F, 1.0F);
    @RegisterSetting
    private NumSetting placeDelay = new NumSetting("PlaceDelay", 2.0F, 0.0F, 20.0F, 1.0F);
    @RegisterSetting
    private ModeSetting logic = new ModeSetting("Logic", "PlaceBreak", new String[] { "PlaceBreak", "BreakPlace"});
    @RegisterSetting
    public final ModeSetting renderMode = new ModeSetting("RenderMode", "Both", new String[] { "Both", "Outline", "Fill"});
    @RegisterSetting
    public final ColorSetting boxColor = new ColorSetting("Box", new NColor(255, 255, 255, 125));
    @RegisterSetting
    public final ColorSetting outlineColor = new ColorSetting("Outline", new NColor(255, 255, 255, 255));
    @RegisterSetting
    private BooleanSetting setDead = new BooleanSetting("SetDead", true);
    @RegisterSetting
    private BooleanSetting fastPlace = new BooleanSetting("FastPlace", true);
    private final TickTimer breakTickTimer = new TickTimer();
    private final TickTimer placeTickTimer = new TickTimer();
    private ArrayList placedCrystals = new ArrayList();
    private EntityEnderCrystal targetCrystal;
    private BlockPos lastPlacedPos;
    private boolean outline = false;
    private boolean fill = false;
    private BlockPos targetedBlock = null;
    private String targetName = "";
    EntityPlayer targetPlayer;

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.targetName + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onTick(TickEvent event) {
        if (this.fastPlace.getValue() && AutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
            ((IMinecraftMixin) AutoCrystal.mc).setRightClickDelayTimerAccessor(0);
        }

        if (this.logic.getMode().equals("PlaceBreak")) {
            this.doPlaceLogic();
            this.doBreakLogic();
        } else {
            this.doBreakLogic();
            this.doPlaceLogic();
        }

        this.targetedBlock = null;
    }

    @SubscribeEvent
    public void onUpdate(RenderWorldLastEvent event) {
        if (this.renderMode.getMode().equals("Both")) {
            this.outline = true;
            this.fill = true;
        } else if (this.renderMode.getMode().equals("Outline")) {
            this.outline = true;
            this.fill = false;
        } else {
            this.fill = true;
            this.outline = false;
        }

        if (this.targetedBlock != null) {
            AxisAlignedBB bb = new AxisAlignedBB(this.targetedBlock);

            if (this.fill) {
                RenderUtil.renderFilledBB(bb, this.boxColor.getAsColor());
            }

            if (this.outline) {
                RenderUtil.renderOutlineBB(bb, this.outlineColor.getAsColor());
            }
        }

    }

    @SubscribeEvent
    public void onPacket(PacketEvent.Receive event) {
        if (this.setDead.getValue()) {
            if (event.getPacket() instanceof SPacketSoundEffect) {
                SPacketSoundEffect sound = (SPacketSoundEffect) event.getPacket();

                if (sound.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE && this.canAttackCrystal(this.targetCrystal)) {
                    this.targetCrystal.setDead();
                }
            }

        }
    }

    private void doBreakLogic() {
        this.targetCrystal = (EntityEnderCrystal) AutoCrystal.mc.world.loadedEntityList.stream().filter((e) -> {
            return e.getDistance(AutoCrystal.mc.player) <= this.attackDistance.getValue();
        }).filter((e) -> {
            return e instanceof EntityEnderCrystal;
        }).map((e) -> {
            return (EntityEnderCrystal) e;
        }).filter(this::canAttackCrystal).findFirst().orElse((Object) null);
        if (this.targetCrystal != null) {
            if (this.canAttackCrystal(this.targetCrystal) && this.breakTickTimer.hasTicksPassed((long) this.breakDelay.getValue())) {
                AutoCrystal.mc.player.swingArm(EnumHand.MAIN_HAND);
                AutoCrystal.mc.player.connection.sendPacket(new CPacketUseEntity(this.targetCrystal));
                this.breakTickTimer.reset();
            }

        }
    }

    private void doPlaceLogic() {
        List crystalBlocks = (List) this.getBlocksAroundPlayer(this.placeDistance.getValue()).stream().filter(this::canPlaceCrystal).sorted(Comparator.comparing((blockPos) -> {
            return Double.valueOf(AutoCrystal.mc.player.getDistanceSq(blockPos));
        })).collect(Collectors.toList());
        BlockPos crystalPosition = null;
        Iterator iterator = AutoCrystal.mc.world.playerEntities.iterator();

        while (iterator.hasNext()) {
            EntityPlayer e = (EntityPlayer) iterator.next();

            if (e.getDistance(AutoCrystal.mc.player) <= this.placeDistance.getValue() && !e.equals(AutoCrystal.mc.player)) {
                this.targetPlayer = e;
                this.targetName = e.getDisplayNameString();
                double bestDamage = Double.MIN_VALUE;
                BlockPos bestPos = null;
                Iterator iterator1 = crystalBlocks.iterator();

                while (iterator1.hasNext()) {
                    BlockPos pos = (BlockPos) iterator1.next();
                    double targetDamage = (double) this.calculateDamage((double) pos.getX() + 0.5D, (double) (pos.getY() + 1), (double) pos.getZ() + 0.5D, this.targetPlayer);
                    double selfDmg = (double) this.calculateDamage((double) pos.getX() + 0.5D, (double) (pos.getY() + 1), (double) pos.getZ() + 0.5D, AutoCrystal.mc.player);

                    if (targetDamage >= (double) this.minDmg.getValue() && selfDmg <= (double) this.maxSelfDmg.getValue() && targetDamage > bestDamage) {
                        bestDamage = targetDamage;
                        bestPos = pos;
                        this.targetedBlock = pos;
                    }
                }

                if (bestPos != null) {
                    crystalPosition = bestPos;
                }
            }
        }

        if (crystalPosition != null) {
            assert crystalPosition != null;

            if (this.placeTickTimer.hasTicksPassed((long) this.placeDelay.getValue())) {
                AutoCrystal.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(crystalPosition, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0F, 0.0F, 0.0F));
                AutoCrystal.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.placeTickTimer.reset();
                this.lastPlacedPos = crystalPosition;
            }
        }

    }

    private boolean canPlaceCrystal(BlockPos pos) {
        if (this.getBlock(pos.add(0, 1, 0)) == Blocks.AIR && this.getBlock(pos.add(0, 2, 0)) == Blocks.AIR) {
            if (this.getBlock(pos) != Blocks.OBSIDIAN && this.getBlock(pos) != Blocks.BEDROCK) {
                return false;
            } else {
                BlockPos air1 = pos.add(0, 1, 0);
                BlockPos air2 = pos.add(0, 2, 0);

                return AutoCrystal.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(air1)).isEmpty() && AutoCrystal.mc.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(air2)).isEmpty();
            }
        } else {
            return false;
        }
    }

    private Block getBlock(BlockPos pos) {
        return AutoCrystal.mc.world.getBlockState(pos).getBlock();
    }

    private boolean canAttackCrystal(EntityEnderCrystal targetCrystal) {
        return targetCrystal != null && AutoCrystal.mc.player.getDistance(targetCrystal) <= this.attackDistance.getValue() ? !targetCrystal.isDead : false;
    }

    private ArrayList getBlocksAroundPlayer(float range) {
        ArrayList posList = new ArrayList();

        for (float x = -range; x < range; ++x) {
            for (float y = range + 1.0F; y > -range; --y) {
                for (float z = -range; z < range; ++z) {
                    BlockPos pos = new BlockPos((double) x, (double) y, (double) z);

                    posList.add(pos.add(AutoCrystal.mc.player.getPosition()));
                }
            }
        }

        return posList;
    }

    public float getBlastReduction(EntityLivingBase entity, float damage, Explosion explosion) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) entity;
            DamageSource ds = DamageSource.causeExplosionDamage(explosion);

            damage = CombatRules.getDamageAfterAbsorb(damage, (float) ep.getTotalArmorValue(), (float) ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            float f = MathHelper.clamp((float) k, 0.0F, 20.0F);

            damage *= 1.0F - f / 25.0F;
            if (entity.isPotionActive(Potion.getPotionById(11))) {
                damage -= damage / 4.0F;
            }

            damage = Math.max(damage, 0.0F);
            return damage;
        } else {
            damage = CombatRules.getDamageAfterAbsorb(damage, (float) entity.getTotalArmorValue(), (float) entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            return damage;
        }
    }

    private float getDamageMultiplied(float damage) {
        int diff = AutoCrystal.mc.world.getDifficulty().getId();

        return damage * (diff == 0 ? 0.0F : (diff == 2 ? 1.0F : (diff == 1 ? 0.5F : 1.5F)));
    }

    public float calculateDamage(double posX, double posY, double posZ, Entity entity) {
        float doubleExplosionSize = 12.0F;
        double distancedsize = entity.getDistance(posX, posY, posZ) / (double) doubleExplosionSize;
        Vec3d vec3d = new Vec3d(posX, posY, posZ);
        double blockDensity = (double) entity.world.getBlockDensity(vec3d, entity.getEntityBoundingBox());
        double v = (1.0D - distancedsize) * blockDensity;
        float damage = (float) ((int) ((v * v + v) / 2.0D * 7.0D * (double) doubleExplosionSize + 1.0D));
        double finald = 1.0D;

        if (entity instanceof EntityLivingBase) {
            finald = (double) this.getBlastReduction((EntityLivingBase) entity, this.getDamageMultiplied(damage), new Explosion(AutoCrystal.mc.world, (Entity) null, posX, posY, posZ, 6.0F, false, true));
        }

        return (float) finald;
    }
}
