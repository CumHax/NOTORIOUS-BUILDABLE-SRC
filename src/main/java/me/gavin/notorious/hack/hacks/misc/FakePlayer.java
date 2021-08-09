package me.gavin.notorious.hack.hacks.misc;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import me.gavin.notorious.hack.Hack;
import me.gavin.notorious.hack.RegisterHack;
import net.minecraft.client.entity.EntityOtherPlayerMP;

@RegisterHack(
    name = "FakePlayer",
    description = "Spawns a fake player",
    category = Hack.Category.Misc
)
public class FakePlayer extends Hack {

    private EntityOtherPlayerMP fakePlayer;

    protected void onEnable() {
        if (FakePlayer.mc.world != null && FakePlayer.mc.player != null) {
            this.fakePlayer = new EntityOtherPlayerMP(FakePlayer.mc.world, new GameProfile(UUID.fromString("6714531a-1c69-438e-b7d6-d6d41ca6838b"), "gerald0mc"));
            this.fakePlayer.copyLocationAndAnglesFrom(FakePlayer.mc.player);
            this.fakePlayer.inventory.copyInventory(FakePlayer.mc.player.inventory);
            FakePlayer.mc.world.addEntityToWorld(-7777, this.fakePlayer);
        } else {
            this.disable();
        }

    }

    protected void onDisable() {
        if (this.fakePlayer != null && FakePlayer.mc.world != null) {
            FakePlayer.mc.world.removeEntityFromWorld(-7777);
            this.fakePlayer = null;
        }

    }
}
