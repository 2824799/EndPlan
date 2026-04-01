package com.silvia.EndPlan.command.cilent;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import com.silvia.EndPlan.recipe.RecipeRepository;

import gregtech.api.util.GTRecipe;

public class check_gt_recipe_amount {

    public static void p(ICommandSender sender) {
        // 1. 调用我们之前存好的变量
        List<GTRecipe> allRecipes = RecipeRepository.getAllRecipes();

        // 2. 验证数据
        if (allRecipes.isEmpty()) {
            sender.addChatMessage(new ChatComponentText("错误：内存中没有配方数据！"));
        } else {
            sender.addChatMessage(new ChatComponentText("当前内存中缓存了 " + allRecipes.size() + " 条 GT 配方。"));
        }
    }
}
