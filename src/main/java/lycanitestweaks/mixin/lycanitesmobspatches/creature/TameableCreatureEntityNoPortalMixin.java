package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TameableCreatureEntity.class)
public abstract class TameableCreatureEntityNoPortalMixin extends AgeableCreatureEntity {

    public TameableCreatureEntityNoPortalMixin(World world) {
        super(world);
    }

    @Override
    @Unique
    public void setPortal(BlockPos pos){
        if(this.isBoundPet()) this.timeUntilPortal = this.getPortalCooldown();
        super.setPortal(pos);
    }

}
