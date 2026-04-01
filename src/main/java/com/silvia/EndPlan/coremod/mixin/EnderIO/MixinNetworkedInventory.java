package com.silvia.EndPlan.coremod.mixin.EnderIO;

import net.minecraftforge.common.util.ForgeDirection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import crazypants.enderio.conduit.item.IItemConduit;
import crazypants.enderio.conduit.item.NetworkedInventory;

@Mixin(NetworkedInventory.class)
public abstract class MixinNetworkedInventory {

    // @Shadow(remap = false)
    // boolean canExtract() {
    // return false;
    // }

    @Shadow(remap = false)
    IItemConduit con;

    @Shadow(remap = false)
    ForgeDirection conDir;

    // @Shadow(remap = false)
    // private boolean transferItems() {
    // return false;
    // }

    /**
     * @author Silvia
     * @reason Speed
     */
    @Overwrite(remap = false)
    public void onTick() {
        System.out.println("[EndPlan Debug] NetworkedInventory onTick is hijacked by Silvia!");
        int i = 0;
        // while (canExtract() && con.isExtractionRedstoneConditionMet(conDir) && i <= 4096 && transferItems()) {
        // i++;
        // }
    }
}
