package lycanitestweaks.mixin.lycanitesmobspatches.goals;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
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

    @ModifyReturnValue(
            method = "canPlaceBlock",
            at = @At(value = "RETURN", ordinal = 2),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsPlaceBlockGoal_canPlaceBlock(boolean original, @Local(argsOnly = true) BlockPos pos, @Local IBlockState targetState){
        return original && ForgeEventFactory.onEntityDestroyBlock(this.host, pos, targetState);
    }
}
