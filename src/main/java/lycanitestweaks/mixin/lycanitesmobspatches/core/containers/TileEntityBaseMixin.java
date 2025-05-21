package lycanitestweaks.mixin.lycanitesmobspatches.core.containers;

import com.lycanitesmobs.core.tileentity.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nonnull;

@Mixin(TileEntityBase.class)
public abstract class TileEntityBaseMixin extends TileEntity implements ISidedInventory {
    @Shadow(remap = false) protected boolean destroyed;

    /**
     * @author Nischhelm
     * Lyca has max distance of 4 blocks which i guess is from way older versions. In any way, it feels weird not being able to interact with the blocks from medium distances.
     * This uses the default behavior that vanilla tile entities use.
     * Using Override instead of Overwrite cause remapping is weird here. Works just the same though.
     */
    @Override
    public boolean isUsableByPlayer(@Nonnull EntityPlayer player) {
        if (this.destroyed || this.world.getTileEntity(this.pos) != this)
            return false;
        else
            return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }
}
