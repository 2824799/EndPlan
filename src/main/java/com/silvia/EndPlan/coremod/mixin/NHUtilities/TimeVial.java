package com.silvia.EndPlan.coremod.mixin.NHUtilities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.xir.NHUtilities.common.entity.EntityTimeAccelerator;

@Mixin(value = EntityTimeAccelerator.class, remap = false)
public class TimeVial extends Entity {

    public TimeVial(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {}

    @Shadow
    private int remainingTime;

    @Shadow
    private void tAccelerate() {}

    /**
     * @author Silvia
     * @reason Remove remainingTime decrement
     */
    @Overwrite
    public void onEntityUpdate() {
        if (this.worldObj.isRemote) return;
        if (remainingTime > 0) this.tAccelerate();
        if (remainingTime <= 0) this.setDead();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund) {}

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {}
}
