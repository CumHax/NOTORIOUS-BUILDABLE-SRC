package me.gavin.notorious;

import me.gavin.notorious.event.EventProcessor;
import me.gavin.notorious.gui.ClickGuiScreen;
import me.gavin.notorious.manager.HackManager;
import me.gavin.notorious.manager.MessageManager;
import me.gavin.notorious.manager.RotationManager;
import me.gavin.notorious.util.font.CFontLoader;
import me.gavin.notorious.util.font.CFontRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.Display;

public class Notorious {

    public static Notorious INSTANCE;
    public final HackManager hackManager;
    public final ClickGuiScreen clickGuiScreen;
    public final CFontRenderer fontRenderer;
    public final MessageManager messageManager;
    public final RotationManager rotationManager;

    public Notorious() {
        Notorious.INSTANCE = this;
        this.hackManager = new HackManager();
        this.fontRenderer = new CFontRenderer(CFontLoader.HELVETICA, true, true);
        this.clickGuiScreen = new ClickGuiScreen();
        this.messageManager = new MessageManager();
        this.rotationManager = new RotationManager();

        new EventProcessor();
        MinecraftForge.EVENT_BUS.register(this);
        Display.setTitle("Notorious beta-0.3");
    }
}
