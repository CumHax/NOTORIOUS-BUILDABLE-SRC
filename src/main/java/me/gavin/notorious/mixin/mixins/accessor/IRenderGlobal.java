package me.gavin.notorious.mixin.mixins.accessor;

import java.util.Map;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ RenderGlobal.class})
public interface IRenderGlobal {

    @Accessor("damagedBlocks")
    Map getDamagedBlocks();
}
