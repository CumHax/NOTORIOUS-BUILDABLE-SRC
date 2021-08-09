package me.gavin.notorious.hack.hacks.render;

import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.hack.RegisterSetting;
import me.gavin.notorious.setting.BooleanSetting;
import me.gavin.notorious.setting.ColorSetting;
import me.gavin.notorious.setting.ModeSetting;
import me.gavin.notorious.setting.NumSetting;
import me.gavin.notorious.util.NColor;
import me.gavin.notorious.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@RegisterHack(
    name = "ESP",
    description = "Draws a box around entities.",
    category = Hack.Category.Render
)
public class ESP extends Hack {

    @RegisterSetting
    public final ModeSetting espMode = new ModeSetting("ESPMode", "RotateBox", new String[] { "RotateBox", "Glow"});
    @RegisterSetting
    public final ColorSetting outlineColor = new ColorSetting("Outline", new NColor(255, 255, 255, 255));
    @RegisterSetting
    public final ColorSetting boxColor = new ColorSetting("Box", new NColor(255, 255, 255, 125));
    @RegisterSetting
    public final NumSetting lineWidth = new NumSetting("LineWidth", 2.0F, 0.1F, 4.0F, 0.1F);
    @RegisterSetting
    public final BooleanSetting players = new BooleanSetting("Players", true);
    @RegisterSetting
    public final BooleanSetting animals = new BooleanSetting("Animals", true);
    @RegisterSetting
    public final BooleanSetting mobs = new BooleanSetting("Mobs", true);
    @RegisterSetting
    public final BooleanSetting items = new BooleanSetting("Items", true);

    public String getMetaData() {
        return " [" + ChatFormatting.GRAY + this.lineWidth.getValue() + ChatFormatting.RESET + "]";
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event) {
        Iterator iterator = ESP.mc.world.loadedEntityList.iterator();

        while (iterator.hasNext()) {
            Entity e = (Entity) iterator.next();
            AxisAlignedBB box = e.getEntityBoundingBox();
            double x = (e.posX - e.lastTickPosX) * (double) event.getPartialTicks();
            double y = (e.posY - e.lastTickPosY) * (double) event.getPartialTicks();
            double z = (e.posZ - e.lastTickPosZ) * (double) event.getPartialTicks();

            new AxisAlignedBB(box.minX + x, box.minY + y, box.minZ + z, box.maxX + x, box.maxY + y, box.maxZ + z);

            if (e != ESP.mc.player || ESP.mc.gameSettings.thirdPersonView != 0) {
                if (e instanceof EntityPlayer && this.players.isEnabled()) {
                    if (this.espMode.getMode().equals("RotateBox")) {
                        this.render(e);
                    } else {
                        e.setGlowing(true);
                    }
                } else if (e instanceof EntityAnimal && this.animals.isEnabled()) {
                    if (this.espMode.getMode().equals("RotateBox")) {
                        this.render(e);
                    } else {
                        e.setGlowing(true);
                    }
                } else if ((e instanceof EntityMob || e instanceof EntitySlime) && this.mobs.isEnabled()) {
                    if (this.espMode.getMode().equals("RotateBox")) {
                        this.render(e);
                    } else {
                        e.setGlowing(true);
                    }
                } else if (e instanceof EntityItem && this.items.isEnabled()) {
                    if (this.espMode.getMode().equals("RotateBox")) {
                        this.render(e);
                    } else {
                        e.setGlowing(true);
                    }
                }
            }
        }

    }

    private void render(Entity entity) {
        GlStateManager.pushMatrix();
        AxisAlignedBB b = entity.getEntityBoundingBox().offset(-ESP.mc.getRenderManager().viewerPosX, -ESP.mc.getRenderManager().viewerPosY, -ESP.mc.getRenderManager().viewerPosZ);
        double x = (b.maxX - b.minX) / 2.0D + b.minX;
        double y = (b.maxY - b.minY) / 2.0D + b.minY;
        double z = (b.maxZ - b.minZ) / 2.0D + b.minZ;

        GL11.glTranslated(x, y, z);
        GL11.glRotated(-MathHelper.clampedLerp((double) entity.prevRotationYaw, (double) entity.rotationYaw, (double) ESP.mc.getRenderPartialTicks()), 0.0D, 1.0D, 0.0D);
        GL11.glTranslated(-x, -y, -z);
        RenderUtil.entityESPBox(entity, this.boxColor.getAsColor(), this.outlineColor.getAsColor(), (int) this.lineWidth.getValue());
        GlStateManager.popMatrix();
    }
}
