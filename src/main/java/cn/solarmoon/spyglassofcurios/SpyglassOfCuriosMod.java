package cn.solarmoon.spyglassofcurios;

import cn.solarmoon.spyglassofcurios.Config.RegisterConfig;
import cn.solarmoon.spyglassofcurios.events.SpyglassHandler;
import cn.solarmoon.spyglassofcurios.network.PacketRegister;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod("spyglassofcurios")
public class SpyglassOfCuriosMod {
    public static final String MOD_ID = "spyglassofcurios";

    public SpyglassOfCuriosMod() {
        //数据包
        PacketRegister packetRegister = new PacketRegister();
        packetRegister.register();

        //非静态事件处理器
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,() ->()->MinecraftForge.EVENT_BUS.register(new SpyglassHandler()));

        RegisterConfig.register();

    }
}


