package com.silvia.EndPlan.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gregtech.api.recipe.RecipeMap;
import gregtech.api.util.GTRecipe;

/**
 * 这是一个常驻内存的数据仓库。
 * 它在 PostInit 阶段被初始化，之后在游戏运行的任何时候，
 * 你都可以调用 RecipeRepository.getAllRecipes() 来获取数据。
 */
public class RecipeRepository {

    // --- 持久化变量区域 ---

    // 1. 存储所有配方的扁平化列表 (如果你需要遍历所有)
    private static final List<GTRecipe> ALL_RECIPES_CACHE = new ArrayList<>();

    // 2. 按照机器类型分类的 Map (如果你需要按机器查询)
    private static final Map<String, List<GTRecipe>> RECIPES_BY_MACHINE = new HashMap<>();

    // 标志位：是否已经加载过
    private static boolean isLoaded = false;

    /**
     * 初始化函数：从 GTNH 的 API 中抓取所有数据并存入我们的变量中。
     * 应该在 FMLPostInitializationEvent 中调用一次。
     */
    public static void load() {
        if (isLoaded) return;

        System.out.println("[EndPlan] 开始建立配方缓存...");
        long startTime = System.currentTimeMillis();

        // 清理旧数据（以防重载）
        ALL_RECIPES_CACHE.clear();
        RECIPES_BY_MACHINE.clear();

        // 遍历你提供的 RecipeMap.ALL_RECIPE_MAPS
        // Key 是 UnlocalizedName (例如 "gt.recipe.blastfurnace")
        // Value 是 RecipeMap 实例
        for (Map.Entry<String, RecipeMap<?>> entry : RecipeMap.ALL_RECIPE_MAPS.entrySet()) {
            String machineName = entry.getKey();
            RecipeMap<?> map = entry.getValue();

            // 获取该机器下的所有配方
            // 源码中: public Collection<GTRecipe> getAllRecipes()
            if (map != null && map.getBackend() != null) {
                List<GTRecipe> mapRecipes = new ArrayList<>(map.getAllRecipes());

                // 存入我们的分类 Map
                RECIPES_BY_MACHINE.put(machineName, mapRecipes);

                // 存入总 List
                ALL_RECIPES_CACHE.addAll(mapRecipes);
            }
        }

        isLoaded = true;
        System.out.println(
            "[EndPlan] 缓存建立完成。共收录配方: " + ALL_RECIPES_CACHE.size()
                + " 条。耗时: "
                + (System.currentTimeMillis() - startTime)
                + "ms");
    }

    // --- 外部调用接口 (API) ---

    /**
     * 获取所有已缓存的配方列表。
     * 你可以在游戏运行时任何地方调用这个函数。
     */
    public static List<GTRecipe> getAllRecipes() {
        return Collections.unmodifiableList(ALL_RECIPES_CACHE);
    }

    /**
     * 获取指定机器的所有配方。
     * 
     * @param unlocalizedName 例如 "gt.recipe.blastfurnace"
     */
    public static List<GTRecipe> getRecipesForMachine(String unlocalizedName) {
        return RECIPES_BY_MACHINE.getOrDefault(unlocalizedName, Collections.emptyList());
    }

    /**
     * 获取所有的机器名称 (Key)
     */
    public static List<String> getRegisteredMachineNames() {
        return new ArrayList<>(RECIPES_BY_MACHINE.keySet());
    }
}
