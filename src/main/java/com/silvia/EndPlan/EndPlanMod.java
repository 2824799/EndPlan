package com.silvia.EndPlan;

import org.apache.logging.log4j.Logger;

import com.silvia.EndPlan.command.endplan_cilent;
import com.silvia.EndPlan.recipe.RecipeRepository;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

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

    // 新增: PostInit 阶段
    // 这个阶段所有的模组都已经把配方注册好了
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        logger.info("Starting to dump recipes... 开始导出配方");

        // 这一步在开发环境下做，生产环境可以把这个注释掉或者加个Config开关
        RecipeRepository.load();
    }

    // === 新增部分：注册命令 ===
    // 这个事件在服务器启动（包括单人游戏的内置服务器）时触发
    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new endplan_cilent());
    }
}
