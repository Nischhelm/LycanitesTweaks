package lycanitestweaks.mixin.lycanitesmobsfeatures.minionhostlevels;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityAsmodeus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityAsmodeus.class)
public abstract class EntityAsmodeusMinionLevelsMixin extends BaseCreatureEntity {

    public EntityAsmodeusMinionLevelsMixin(World world) {
        super(world);
    }

    @ModifyArg(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityAsmodeus;summonMinion(Lnet/minecraft/entity/EntityLivingBase;DD)V"),
            index = 0,
            remap = false
    )
    private EntityLivingBase lycanitestweaks_lycanitesEntityAsmodeus_updatePhases(EntityLivingBase minion){
        if(minion instanceof BaseCreatureEntity){
            ((BaseCreatureEntity)minion).applyLevel(this.getLevel());
        }
        return minion;
    }
}
