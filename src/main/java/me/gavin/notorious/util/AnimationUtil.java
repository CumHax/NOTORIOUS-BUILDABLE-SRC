package me.gavin.notorious.util;

public class AnimationUtil {

    public static float getSmooth2Animation(int duration, int time) {
        double x1 = (double) ((float) time / (float) duration);

        return (float) (6.0D * Math.pow(x1, 5.0D) - 15.0D * Math.pow(x1, 4.0D) + 10.0D * Math.pow(x1, 3.0D));
    }

    public static float getSmooth2Animation(float duration, float time) {
        double x1 = (double) (time / duration);

        return (float) (6.0D * Math.pow(x1, 5.0D) - 15.0D * Math.pow(x1, 4.0D) + 10.0D * Math.pow(x1, 3.0D));
    }
}
