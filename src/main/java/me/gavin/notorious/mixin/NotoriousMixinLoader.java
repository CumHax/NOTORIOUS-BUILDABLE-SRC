package me.gavin.notorious.mixin;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

@MCVersion("1.12.2")
public class NotoriousMixinLoader implements IFMLLoadingPlugin {

    public String[] getASMTransformerClass() {
        return new String[0];
    }

    public String getModContainerClass() {
        return null;
    }

    @Nullable
    public String getSetupClass() {
        return null;
    }

    public void injectData(Map data) {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.notorious.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("name");
    }

    public String getAccessTransformerClass() {
        return null;
    }
}
