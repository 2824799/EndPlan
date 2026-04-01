// package com.silvia.EndPlan.coremod.mixin.minecraft;
//
// import net.minecraft.item.ItemStack;
//
// import org.spongepowered.asm.mixin.Mixin;
//
// @Mixin(ItemStack.class)
// public abstract class MixinItemStack {
// // // 这一行意思是：在 getMaxStackSize 方法的最后 (RETURN 之前) 插入代码
// // @Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
// // public void onGetMaxStackSize(CallbackInfoReturnable<Integer> cir) {
// // if(cir.getReturnValue()<8192&&cir.getReturnValue()>1) {
// // // 我们可以根据条件决定要不要改
// // // 强制把返回值修改为我们的值
// // cir.setReturnValue(8192);
// // }
// // }
// }
