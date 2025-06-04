package lycanitestweaks.mixin.lycanitesmobspatches.goals;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.goals.actions.abilities.PlaceBlockGoal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlaceBlockGoal.class)
public abstract class PlaceBlockGoalForgeEventMixin {

    @Shadow(remap = false)
    private BaseCreatureEntity host;
    @Shadow(remap = false)
    private BlockPos pos;
    @Shadow(remap = false)
    public IBlockState blockState;

    @ModifyReturnValue(
            method = "shouldExecute",
            at = @At(value = "RETURN", ordinal = 2)
    )
    public boolean lycanitesTweaks_lycanitesMobsPlaceBlockGoal_shouldExecute(boolean original){
        return original && ForgeEventFactory.onEntityDestroyBlock(this.host, this.pos, this.blockState);
    }
}
