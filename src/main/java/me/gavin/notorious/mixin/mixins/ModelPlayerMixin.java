package me.gavin.notorious.mixin.mixins;

import me.gavin.notorious.event.events.PlayerModelRotationEvent;
import me.gavin.notorious.stuff.IMinecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ ModelPlayer.class})
public class ModelPlayerMixin implements IMinecraft {

    @Inject(
        method = { "setRotationAngles"},
        at = {             @At("INVOKE")}
    )
    public void setRotationAnglesInject(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, CallbackInfo ci) {
        if (entityIn == ModelPlayerMixin.mc.player) {
            PlayerModelRotationEvent event = new PlayerModelRotationEvent(netHeadYaw, headPitch);

            MinecraftForge.EVENT_BUS.post(event);
            ((ModelPlayer) this).bipedHead.rotateAngleX = event.getPitch() * 0.017453292F;
            ((ModelPlayer) this).bipedHead.rotateAngleY = event.getYaw() * 0.017453292F;
            ((ModelPlayer) this).bipedHeadwear.rotateAngleX = event.getYaw() * 0.017453292F;
            ((ModelPlayer) this).bipedHeadwear.rotateAngleY = event.getYaw() * 0.017453292F;
        }

    }
}
