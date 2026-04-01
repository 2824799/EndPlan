package com.silvia.EndPlan.coremod.mixin.GT5U;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;

import gregtech.api.enums.GTValues;
import gregtech.api.metatileentity.implementations.MTEEnhancedMultiBlockBase;
import gregtech.api.recipe.check.CheckRecipeResult;
import gregtech.api.recipe.check.CheckRecipeResultRegistry;
import gregtech.api.util.GTRecipe;
import gregtech.api.util.extensions.ArrayExt;
import gtnhlanth.api.recipe.LanthanidesRecipeMaps;
import gtnhlanth.common.tileentity.MTETargetChamber;

@Mixin(value = MTETargetChamber.class, remap = false)
public abstract class TargetChamberMixin extends MTEEnhancedMultiBlockBase<MTETargetChamber>
    implements ISurvivalConstructable {

    protected TargetChamberMixin(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    @Shadow
    private ArrayList<ItemStack> getFocusItemStack() {
        return null;
    }

    @Shadow
    private GTRecipe lastRecipe;

    @Inject(method = "checkProcessing", at = @At("HEAD"), cancellable = true)
    private void injectCustomCheckProcessing(CallbackInfoReturnable<CheckRecipeResult> cir) {
        ArrayList<ItemStack> tItems = getStoredInputs();
        ArrayList<ItemStack> tFocusItemArray = getFocusItemStack();
        ArrayList<ItemStack> tItemsWithFocusItem = new ArrayList<>();

        // 1. 处理掩膜板 (放入 index 0)
        if (tFocusItemArray != null && !tFocusItemArray.isEmpty() && tFocusItemArray.get(0) != null) {
            ItemStack focus = tFocusItemArray.get(0)
                .copy();
            focus.setItemDamage(0); // 抹平耐久差异以便匹配配方
            tItemsWithFocusItem.add(focus);
        }

        tItemsWithFocusItem.addAll(tItems);

        ItemStack[] inputs = tItemsWithFocusItem.toArray(new ItemStack[0]);
        long voltage = GTValues.VP[(int) getInputVoltageTier()];

        // 2. 无视粒子查找配方
        GTRecipe recipe = LanthanidesRecipeMaps.targetChamberRecipes.findRecipeQuery()
            .items(inputs)
            .voltage(voltage)
            .cachedRecipe(lastRecipe)
            .find();

        // 诊断日志：如果找不到配方，打印当前机器识别到的数据
        if (recipe == null) {
            // System.out.print("[EndPlan-Debug] 找不到配方! 当前电压: " + voltage + " EU/t, 输入物品: ");
            // for (ItemStack is : inputs) {
            // if (is != null) {
            // System.out.print(
            // "[" + is.getDisplayName() + " x" + is.stackSize + " damage:" + is.getItemDamage() + "] ");
            // }
            // }
            // System.out.println(); // 换行

            cir.setReturnValue(CheckRecipeResultRegistry.NO_RECIPE);
            return;
        }

        // // 3. 单次处理 (不并行)
        // int batchAmount = 1;
        //
        // // 保险校验：检查物品是否足够单次运行
        // double maxParallel = recipe.maxParallelCalculatedByInputs(1, GTValues.emptyFluidStackArray, inputs);
        // if (maxParallel < 1) {
        // cir.setReturnValue(CheckRecipeResultRegistry.NO_RECIPE);
        // return;
        // }

        // 巨量并行处理
        int desiredBatchAmount = 2_050_781;
        double maxParallel = recipe
            .maxParallelCalculatedByInputs(desiredBatchAmount, GTValues.emptyFluidStackArray, inputs);
        int batchAmount = (int) Math.min(desiredBatchAmount, maxParallel);

        if (batchAmount <= 0) {
            cir.setReturnValue(CheckRecipeResultRegistry.NO_RECIPE);
            return;
        }

        // 4. 写死处理时间 1 tick
        mMaxProgresstime = 1;
        mEUt = -(int) voltage;
        lastRecipe = recipe;

        // 5. 提取产物
        ItemStack[] outputs = ArrayExt.copyItemsIfNonEmpty(recipe.mOutputs);
        for (ItemStack stack : outputs) {
            stack.stackSize *= batchAmount;
        }
        mOutputItems = outputs;

        // 6. 消耗常规输入物品
        recipe.consumeInput(batchAmount, GTValues.emptyFluidStackArray, inputs);

        // 7. 消耗掩膜板耐久度 (原版逻辑)
        int focusDurabilityDepletion = batchAmount;
        if (tFocusItemArray != null) {
            for (ItemStack stack : tFocusItemArray) {
                if (stack == null) continue;

                // 如果当前剩余耐久不足以扣除本次消耗
                if (focusDurabilityDepletion + stack.getItemDamage() >= stack.getMaxDamage() + 1) {
                    focusDurabilityDepletion -= stack.getMaxDamage() + 1 - stack.getItemDamage();
                    stack.stackSize--; // 销毁该物品
                } else {
                    // 扣除耐久并跳出循环
                    stack.setItemDamage(stack.getItemDamage() + focusDurabilityDepletion);
                    break;
                }
            }
        }

        updateSlots();
        mEfficiency = 10000;
        mEfficiencyIncrease = 10000;

        cir.setReturnValue(CheckRecipeResultRegistry.SUCCESSFUL);
    }
}
