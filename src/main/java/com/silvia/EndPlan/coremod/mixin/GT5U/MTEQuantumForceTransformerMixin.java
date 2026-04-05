package com.silvia.EndPlan.coremod.mixin.GT5U;

import java.util.Arrays;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gregtech.api.util.GTRecipe;
import gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.MTEQuantumForceTransformer;

@Mixin(value = MTEQuantumForceTransformer.class, remap = false)
public abstract class MTEQuantumForceTransformerMixin {

    /**
     * 拦截原有的 getOutputChances 方法。
     * 直接返回一个全部由 10000 (100% 概率) 填充的数组。
     */
    @Inject(method = "getOutputChances", at = @At("HEAD"), cancellable = true)
    private void inject100PercentChances(GTRecipe tRecipe, int aChanceIncreased, CallbackInfoReturnable<int[]> cir) {
        // 计算输出物总数 (物品输出 + 流体输出)
        int aOutputsAmount = tRecipe.mOutputs.length + tRecipe.mFluidOutputs.length;

        // 创建新的概率数组
        int[] maxChances = new int[aOutputsAmount];

        // 将所有输出槽位的概率直接拉满到 10000 (100%)
        Arrays.fill(maxChances, 10000);

        // 设置返回值并取消原方法的后续执行
        cir.setReturnValue(maxChances);
    }
}
