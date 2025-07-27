package lycanitestweaks.mixin.incontrol;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import lycanitestweaks.compat.InControlCompat;
import mcjty.incontrol.rules.ExperienceRule;
import mcjty.incontrol.rules.LootRule;
import mcjty.incontrol.rules.PotentialSpawnRule;
import mcjty.incontrol.rules.SpawnRule;
import mcjty.incontrol.rules.SummonAidRule;
import mcjty.tools.typed.Attribute;
import mcjty.tools.typed.GenericAttributeMapFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {
        ExperienceRule.class,
        SpawnRule.class,
        PotentialSpawnRule.class,
        SummonAidRule.class,
        LootRule.class
})
public abstract class Rules_Mixin {

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lmcjty/tools/typed/GenericAttributeMapFactory;attribute(Lmcjty/tools/typed/Attribute;)Lmcjty/tools/typed/GenericAttributeMapFactory;", ordinal = 0, remap = false)
    )
    private static GenericAttributeMapFactory lycanitesTweaks_incontrolRules_addCustomRules(GenericAttributeMapFactory original){
        return original
                .attribute(Attribute.create(InControlCompat.MIN_LEVEL))
                .attribute(Attribute.create(InControlCompat.MAX_LEVEL))
                .attribute(Attribute.create(InControlCompat.IS_SUBSPECIES))
                .attribute(Attribute.create(InControlCompat.IS_UNCOMMON))
                .attribute(Attribute.create(InControlCompat.IS_RARE));
    }
}
