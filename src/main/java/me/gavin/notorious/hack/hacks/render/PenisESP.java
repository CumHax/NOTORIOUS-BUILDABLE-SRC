package me.gavin.notorious.hack.hacks.render;

import java.util.Iterator;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import me.gavin.notorious.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

@RegisterHack(
    name = "PenisESP",
    description = "ESP for your penis.",
    category = Hack.Category.Render
)
public class PenisESP extends Hack {

    private float pspin;
    private float pcumsize;
    private float pamount;

    public void onEnable() {
        this.pspin = 0.0F;
        this.pcumsize = 0.0F;
        this.pamount = 0.0F;
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        Iterator iterator = PenisESP.mc.world.loadedEntityList.iterator();

        while (iterator.hasNext()) {
            Object o = iterator.next();

            if (o instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) o;
                double x2 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) PenisESP.mc.getRenderPartialTicks();
                double x = x2 - PenisESP.mc.getRenderManager().viewerPosX;
                double y2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) PenisESP.mc.getRenderPartialTicks();
                double y = y2 - PenisESP.mc.getRenderManager().viewerPosY;
                double z2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) PenisESP.mc.getRenderPartialTicks();
                double z = z2 - PenisESP.mc.getRenderManager().viewerPosZ;

                GL11.glPushMatrix();
                RenderHelper.disableStandardItemLighting();
                RenderUtil.drawPenis(player, x, y, z, this.pspin, this.pcumsize, this.pamount);
                RenderHelper.enableStandardItemLighting();
                GL11.glPopMatrix();
            }
        }

    }
}
