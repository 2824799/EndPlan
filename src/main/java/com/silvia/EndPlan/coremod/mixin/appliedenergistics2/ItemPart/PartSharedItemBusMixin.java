// package com.silvia.EndPlan.coremod.mixin.appliedenergistics2.ItemPart;
//
// import appeng.api.parts.IPartHost;
// import appeng.parts.automation.PartSharedItemBus;
// import appeng.tile.inventory.AppEngInternalAEInventory;
// import appeng.tile.inventory.IAEAppEngInventory;
// import appeng.util.InventoryAdaptor;
// import org.spongepowered.asm.mixin.Mixin;
// import org.spongepowered.asm.mixin.Overwrite;
// import org.spongepowered.asm.mixin.Shadow;
// import org.spongepowered.asm.mixin.injection.At;
// import org.spongepowered.asm.mixin.injection.Redirect;
//
// @Mixin(PartSharedItemBus.class)
// public abstract class PartSharedItemBusMixin {
//
// @Shadow abstract AppEngInternalAEInventory getConfig();
//
// /**
// * 关键修改 1：拦截 AppEngInternalAEInventory 的构造函数。
// * 原代码是 new AppEngInternalAEInventory(this, 9);
// * 我们强制把它改成 81。
// * 注意：这个注入点在于构造函数 <init> 中。
// */
// @Redirect(
// method = "<init>",
// at = @At(
// value = "NEW",
// target = "appeng/tile/inventory/AppEngInternalAEInventory"
// ),
// remap = false // AE2 的这个类名通常不需要混淆映射，视具体环境而定
// )
// private AppEngInternalAEInventory modifyInventorySize(Object owner, int size) {
// // 这里 owner 其实就是 this (PartSharedItemBus)
// // 强制返回一个大小为 81 的库存
// return new AppEngInternalAEInventory((IAEAppEngInventory) owner, 81);
// }
//
// /**
// * 关键修改 2：解锁所有格子限制。
// * 原逻辑是根据容量卡数量计算：1 + upgrades * 4
// * 现逻辑：直接返回 Config 库存的总大小 (即上面改的 81)。
// *
// * @author YourName
// * @reason Unlock all slots immediately
// */
// @Overwrite(remap = false)
// protected int availableSlots() {
// // 直接返回配置库存的最大容量 (81)
// return this.getConfig().getSizeInventory();
// }
// }
