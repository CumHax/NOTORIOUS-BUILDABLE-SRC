package me.gavin.notorious.util;

import java.awt.Color;
import me.gavin.notorious.stuff.IMinecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

public class RenderUtil implements IMinecraft {

    public static void prepare() {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
    }

    public static void release() {
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public static void renderFilledBB(AxisAlignedBB box, Color color) {
        renderBB(box, color, RenderUtil.RenderMode.FILLED);
    }

    public static void renderOutlineBB(AxisAlignedBB box, Color color) {
        renderBB(box, color, RenderUtil.RenderMode.OUTLINE);
    }

    public static void renderBB(AxisAlignedBB box, Color color, RenderUtil.RenderMode mode) {
        prepare();
        float r = (float) color.getRed() / 255.0F;
        float g = (float) color.getGreen() / 255.0F;
        float b = (float) color.getBlue() / 255.0F;
        float a = (float) color.getAlpha() / 255.0F;

        box = box.offset(-RenderUtil.mc.getRenderManager().viewerPosX, -RenderUtil.mc.getRenderManager().viewerPosY, -RenderUtil.mc.getRenderManager().viewerPosZ);
        if (mode == RenderUtil.RenderMode.FILLED) {
            RenderGlobal.renderFilledBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, r, g, b, a);
        } else {
            RenderGlobal.drawBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, r, g, b, a);
        }

        release();
    }

    public static void entityESPBox(Entity entity, Color boxC, Color outlineC, int lineWidth) {
        AxisAlignedBB ebox = entity.getEntityBoundingBox();
        double lerpX = MathUtil.lerp((double) RenderUtil.mc.getRenderPartialTicks(), entity.lastTickPosX, entity.posX);
        double lerpY = MathUtil.lerp((double) RenderUtil.mc.getRenderPartialTicks(), entity.lastTickPosY, entity.posY);
        double lerpZ = MathUtil.lerp((double) RenderUtil.mc.getRenderPartialTicks(), entity.lastTickPosZ, entity.posZ);
        AxisAlignedBB lerpBox = new AxisAlignedBB(ebox.minX - 0.05D - lerpX + (lerpX - RenderUtil.mc.getRenderManager().viewerPosX), ebox.minY - lerpY + (lerpY - RenderUtil.mc.getRenderManager().viewerPosY), ebox.minZ - 0.05D - lerpZ + (lerpZ - RenderUtil.mc.getRenderManager().viewerPosZ), ebox.maxX + 0.05D - lerpX + (lerpX - RenderUtil.mc.getRenderManager().viewerPosX), ebox.maxY + 0.1D - lerpY + (lerpY - RenderUtil.mc.getRenderManager().viewerPosY), ebox.maxZ + 0.05D - lerpZ + (lerpZ - RenderUtil.mc.getRenderManager().viewerPosZ));

        prepare();
        GL11.glLineWidth((float) lineWidth);
        RenderGlobal.renderFilledBox(lerpBox, (float) boxC.getRed() / 255.0F, (float) boxC.getGreen() / 255.0F, (float) boxC.getBlue() / 255.0F, (float) boxC.getAlpha() / 255.0F);
        RenderGlobal.drawSelectionBoundingBox(lerpBox, (float) outlineC.getRed() / 255.0F, (float) outlineC.getGreen() / 255.0F, (float) outlineC.getBlue() / 255.0F, (float) outlineC.getAlpha() / 255.0F);
        release();
    }

    public static void drawPenis(EntityPlayer player, double x, double y, double z, float pspin, float pcumsize, float pamount) {
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2929);
        GL11.glEnable(2848);
        GL11.glDepthMask(true);
        GL11.glLineWidth(1.0F);
        GL11.glTranslated(x, y, z);
        GL11.glRotatef(-player.rotationYaw, 0.0F, player.height, 0.0F);
        GL11.glTranslated(-x, -y, -z);
        GL11.glTranslated(x, y + (double) (player.height / 2.0F) - 0.22499999403953552D, z);
        GL11.glColor4f(1.38F, 0.55F, 2.38F, 1.0F);
        GL11.glRotated((double) ((float) (player.isSneaking() ? 35 : 0) + pspin), (double) (1.0F + pspin), 0.0D, (double) pcumsize);
        GL11.glTranslated(0.0D, 0.0D, 0.07500000298023224D);
        Cylinder shaft = new Cylinder();

        shaft.setDrawStyle(100013);
        shaft.draw(0.1F, 0.11F, 0.4F, 25, 20);
        GL11.glTranslated(0.0D, 0.0D, -0.12500000298023223D);
        GL11.glTranslated(-0.09000000074505805D, 0.0D, 0.0D);
        Sphere right = new Sphere();

        right.setDrawStyle(100013);
        right.draw(0.14F, 10, 20);
        GL11.glTranslated(0.16000000149011612D, 0.0D, 0.0D);
        Sphere left = new Sphere();

        left.setDrawStyle(100013);
        left.draw(0.14F, 10, 20);
        GL11.glColor4f(1.35F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslated(-0.07000000074505806D, 0.0D, 0.589999952316284D);
        Sphere tip = new Sphere();

        tip.setDrawStyle(100013);
        tip.draw(0.13F, 15, 20);
        GL11.glDepthMask(true);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
    }

    private static enum RenderMode {

        FILLED, OUTLINE;
    }
}
