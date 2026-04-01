package com.silvia.EndPlan.coremod.mixin.NHUtilities;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.xir.NHUtilities.common.entity.EntityTimeAccelerator;

@Mixin(EntityTimeAccelerator.class)
public abstract class TimeVial {

    @Shadow(remap = false)
    private int remainingTime;

    @Shadow(remap = false)
    protected abstract void tAccelerate();

    // 直接删掉 @Shadow setDead() 的声明

    /**
     * @author Silvia
     * @reason Remove remainingTime decrement
     */
    @Overwrite
    public void onEntityUpdate() {
        net.minecraft.entity.Entity self = (net.minecraft.entity.Entity) (Object) this;

        if (self.worldObj.isRemote) return;
        if (remainingTime > 0) this.tAccelerate();
        if (remainingTime <= 0) self.setDead(); // 直接使用强转后的 self 调用
    }
}
