package me.gavin.notorious.util;

import me.gavin.notorious.stuff.IMinecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathUtil implements IMinecraft {

    public static float normalize(float value, float min, float max) {
        return 1.0F - (value - min) / (max - min);
    }

    public static float[] calculateLookAt(double x, double y, double z, EntityPlayer me) {
        double dirx = lerp((double) MathUtil.mc.getRenderPartialTicks(), me.lastTickPosX, me.posX) - x;
        double diry = lerp((double) MathUtil.mc.getRenderPartialTicks(), me.lastTickPosY, me.posY) + (double) me.getEyeHeight() - y;
        double dirz = lerp((double) MathUtil.mc.getRenderPartialTicks(), me.lastTickPosZ, me.posZ) - z;
        double distance = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= distance;
        diry /= distance;
        dirz /= distance;
        float pitch = (float) Math.asin(diry);
        float yaw = (float) Math.atan2(dirz, dirx);

        pitch = (float) ((double) (pitch * 180.0F) / 3.141592653589793D);
        yaw = (float) ((double) (yaw * 180.0F) / 3.141592653589793D);
        yaw += 90.0F;
        return new float[] { yaw, pitch};
    }

    private static float[] getLegitRotations(Vec3d vec) {
        Vec3d eyesPos = getEyesPos();
        double diffX = vec.x - eyesPos.x;
        double diffY = vec.y - eyesPos.y;
        double diffZ = vec.z - eyesPos.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

        return new float[] { MathUtil.mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - MathUtil.mc.player.rotationYaw), MathUtil.mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - MathUtil.mc.player.rotationPitch)};
    }

    private static Vec3d getEyesPos() {
        return new Vec3d(MathUtil.mc.player.posX, MathUtil.mc.player.posY + (double) MathUtil.mc.player.getEyeHeight(), MathUtil.mc.player.posZ);
    }

    public static float[] calcAngle(Vec3d from, Vec3d to) {
        double difX = to.x - from.x;
        double difY = (to.y - from.y) * -1.0D;
        double difZ = to.z - from.z;
        double dist = (double) MathHelper.sqrt(difX * difX + difZ * difZ);

        return new float[] { (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0D), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)))};
    }

    public static float lerp(float delta, float start, float end) {
        return start + delta * (end - start);
    }

    public static double lerp(double delta, double start, double end) {
        return start + delta * (end - start);
    }

    public static double square(double input) {
        return input * input;
    }

    public static int clamp(int num, int min, int max) {
        return num < min ? min : Math.min(num, max);
    }
}
