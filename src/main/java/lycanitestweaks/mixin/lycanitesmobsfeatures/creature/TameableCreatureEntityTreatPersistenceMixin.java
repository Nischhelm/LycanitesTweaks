package lycanitestweaks.mixin.lycanitesmobsfeatures.creature;

import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TameableCreatureEntity.class)
public abstract class TameableCreatureEntityTreatPersistenceMixin extends AgeableCreatureEntity {

    public TameableCreatureEntityTreatPersistenceMixin(World world) {
        super(world);
    }

    @Inject(
            method = "tame",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/info/Beastiary;getCreatureKnowledge(Ljava/lang/String;)Lcom/lycanitesmobs/core/info/CreatureKnowledge;"),
            remap = false
    )
    private void lycanitesTweaks_lycanitesTameableCreatureEntity_tame(EntityPlayer player, CallbackInfoReturnable<Boolean> cir){
        this.enablePersistence();
    }
}
