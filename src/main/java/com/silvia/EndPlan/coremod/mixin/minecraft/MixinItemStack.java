package com.silvia.EndPlan.coremod.mixin.minecraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.silvia.EndPlan.test;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    public abstract Item getItem();

    /**
     * @author YourName
     * @reason 1
     */
    @Overwrite
    public int getMaxStackSize() {
        return test.getnum();
    }
}
