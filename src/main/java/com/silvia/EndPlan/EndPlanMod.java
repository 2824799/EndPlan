package com.silvia.EndPlan;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "endplan", name = "End Plan", acceptableRemoteVersions = "*")
public class EndPlanMod {

    // 这是一个好习惯：定义一个全局的 Logger，方便你以后在控制台打印调试信息
    public static Logger logger;

    // 预初始化阶段：模组刚开始加载时运行
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        logger.info("End Plan is loading...");

        // 以后你可以在这里读取配置文件(Config)
    }
}
