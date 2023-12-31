package cn.solarmoon.spyglass_of_curios.Util;

import cn.solarmoon.spyglass_of_curios.Common.Items.Spyglass.Method.Client.FovAlgorithm;
import cn.solarmoon.spyglass_of_curios.Init.Config;
import net.minecraft.client.Minecraft;


public class Constants {

    //静态焦距
    public static double MULTIPLIER = new FovAlgorithm.Mt().alg(Config.defaultMultiplier.get());

    public static Minecraft mc = Minecraft.getInstance();

    //静态渲染识别符
    public static String renderType = "back_waist";

    //使用标识
    public static boolean usingInHand = false;
    public static boolean usingInCurio = false;

}
