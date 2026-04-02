package com.silvia.EndPlan.coremod.mixin.EnderIO;

import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import crazypants.enderio.conduit.AbstractConduit;
import crazypants.enderio.conduit.item.IItemConduit;
import crazypants.enderio.conduit.item.ItemConduit;

@Mixin(value = ItemConduit.class, remap = false)
public abstract class MixinItemConduit extends AbstractConduit implements IItemConduit {

    @Inject(method = "getMaximumExtracted", at = @At("HEAD"), cancellable = true)
    public void getMaximumExtractedFast(ForgeDirection dir, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(2147483647); // 强制返回最大值，原方法后续代码不再执行
    }

    @Inject(method = "getTickTimePerItem", at = @At("HEAD"), cancellable = true)
    public void getTickTimePerItemFast(ForgeDirection dir, CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(0.000001f); // 强制返回极小值
    }
}
