package com.silvia.EndPlan.coremod.mixin.GT5U;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import gregtech.api.metatileentity.implementations.MTEHatchBulkCatalystHousing;

// 使用 targets 指定目标为 MTEQuantumForceTransformer 类中的第 3 个匿名内部类
@Mixin(
    targets = "gtPlusPlus.xmod.gregtech.common.tileentities.machines.multi.production.MTEQuantumForceTransformer$3",
    remap = false)
public class MTEQuantumForceTransformerMixin2 {

    /**
     * 拦截 validateRecipe 中对催化剂仓 getItemCount() 的调用。
     */
    @Redirect(
        method = "validateRecipe",
        at = @At(
            value = "INVOKE",
            target = "Lgregtech/api/metatileentity/implementations/MTEHatchBulkCatalystHousing;getItemCount()I"))
    private int redirectCatalystCount(MTEHatchBulkCatalystHousing instance) {
        // instance.getItemCount() 是机器实际读取到的催化剂数量
        int count = instance.getItemCount();
        if (count >= 1024) {
            return 2147483647;
        } else {
            return count * 2097152;
        }
    }
}
