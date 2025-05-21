package lycanitestweaks.mixin.lycanitestweaksmajor.configurablestats;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityRahovart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRahovart.class)
public abstract class EntityRahovartMinionLevelsMixin extends BaseCreatureEntity {

    public EntityRahovartMinionLevelsMixin(World world) {
        super(world);
    }

    @ModifyArg(
            method = "updatePhases",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/creature/EntityRahovart;summonMinion(Lnet/minecraft/entity/EntityLivingBase;DD)V"),
            index = 0,
            remap = false
    )
    private EntityLivingBase lycanitestweaks_lycanitesEntityRahovart_updatePhases(EntityLivingBase minion){
        if(minion instanceof BaseCreatureEntity){
            ((BaseCreatureEntity)minion).applyLevel(this.getLevel());
        }
        return minion;
    }
}
