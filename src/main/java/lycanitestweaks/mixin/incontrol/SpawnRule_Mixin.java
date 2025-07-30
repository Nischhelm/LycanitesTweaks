package lycanitestweaks.mixin.incontrol;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import lycanitestweaks.compat.InControlCompat;
import mcjty.incontrol.rules.SpawnRule;
import mcjty.tools.typed.Attribute;
import mcjty.tools.typed.GenericAttributeMapFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpawnRule.class)
public abstract class SpawnRule_Mixin {

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lmcjty/tools/typed/GenericAttributeMapFactory;attribute(Lmcjty/tools/typed/Attribute;)Lmcjty/tools/typed/GenericAttributeMapFactory;", ordinal = 0, remap = false)
    )
    private static GenericAttributeMapFactory lycanitesTweaks_incontrolSpawnRule_addCustomRules(GenericAttributeMapFactory original){
        return original
                .attribute(Attribute.create(InControlCompat.ADD_LEVEL))
                .attribute(Attribute.create(InControlCompat.ADD_LEVEL_RAND_MIN))
                .attribute(Attribute.create(InControlCompat.SET_LEVEL))
                .attribute(Attribute.create(InControlCompat.SET_SUBSPECIES))
                .attribute(Attribute.create(InControlCompat.SET_VARIANT))
                .attribute(Attribute.create(InControlCompat.SET_SPAWNEDASBOSS))
                .attribute(Attribute.create(InControlCompat.SET_BOSSDAMAGELIMIT));
    }
}
