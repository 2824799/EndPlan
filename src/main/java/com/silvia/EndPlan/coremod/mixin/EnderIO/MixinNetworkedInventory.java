package com.silvia.EndPlan.coremod.mixin.EnderIO;

import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import crazypants.enderio.conduit.item.IItemConduit;
import crazypants.enderio.conduit.item.NetworkedInventory;

@Mixin(value = NetworkedInventory.class, remap = false)
public abstract class MixinNetworkedInventory {

    @Shadow
    boolean canExtract() {
        return false;
    }

    @Shadow
    IItemConduit con;

    @Shadow
    ForgeDirection conDir;

    @Shadow
    private boolean transferItems() {
        return false;
    }

    /**
     * @author Silvia
     * @reason Speed
     */
    @Overwrite
    public void onTick() {
        int i = 0;
        while (canExtract() && con.isExtractionRedstoneConditionMet(conDir) && i <= 4096 && transferItems()) {
            i++;
        }
    }
}
