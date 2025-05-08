package lycanitestweaks.mixin.lycanitesmobsfeatures.capbonusstats;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.ElementInfo;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityEffectsCapMixin {

//    @ModifyArgs(
//            method = "applyBuffs",
//            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/info/ElementInfo;buffEntity(Lnet/minecraft/entity/EntityLivingBase;II)V"),
//            remap = false
//    )
//    public void lycanitesTweaks_lycanitesMobsBaseCreatureEntity_applyBuffsLevelLimit(Args args, @Local(argsOnly = true, ordinal = 0) int duration, @Local(argsOnly = true, ordinal = 1) int amplifier, @Local ElementInfo element){
//        if(ForgeConfigHandler.getElementsLevelLimitedBuffs().containsKey(element.name)){
//            args.set(1,
//                    Helpers.getEffectDurationLevelLimited(
//                            (BaseCreatureEntity)(Object)this,
//                            duration,
//                            ForgeConfigHandler.getElementsLevelLimitedBuffs().get(element.name)));
//            args.set(2,
//                    Helpers.getEffectAmplifierLevelLimited(
//                            (BaseCreatureEntity)(Object)this,
//                            amplifier,
//                            ForgeConfigHandler.getElementsLevelLimitedBuffs().get(element.name)));
//        }
//    }

    @ModifyArgs(
            method = "applyDebuffs",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/info/ElementInfo;debuffEntity(Lnet/minecraft/entity/EntityLivingBase;II)V"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsBaseCreatureEntity_applyDebuffsLevelLimit(Args args, @Local(argsOnly = true, ordinal = 0)int duration, @Local(argsOnly = true, ordinal = 1) int amplifier, @Local ElementInfo element){
        if(ForgeConfigHandler.getElementsLevelLimitedDebuffs().containsKey(element.name)){
            args.set(1,
                    Helpers.getEffectDurationLevelLimited(
                            (BaseCreatureEntity)(Object)this,
                            duration,
                            ForgeConfigHandler.getElementsLevelLimitedDebuffs().get(element.name)));
            args.set(2,
                    Helpers.getEffectAmplifierLevelLimited(
                            (BaseCreatureEntity)(Object)this,
                            amplifier,
                            ForgeConfigHandler.getElementsLevelLimitedDebuffs().get(element.name)));
        }
    }
}
