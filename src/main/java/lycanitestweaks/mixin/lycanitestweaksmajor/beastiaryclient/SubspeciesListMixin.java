package lycanitestweaks.mixin.lycanitestweaksmajor.beastiaryclient;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.gui.beastiary.lists.SubspeciesList;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.Subspecies;
import lycanitestweaks.handlers.ForgeConfigProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(SubspeciesList.class)
public abstract class SubspeciesListMixin {

    @Shadow(remap = false)
    private CreatureInfo creature;

    @WrapWithCondition(
            method = "refreshList",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsSubspeciesList_refreshList(Map<Integer, SubspeciesList.Entry> instance, Object k, Object v, @Local Subspecies subspecies){
        return !(ForgeConfigProvider.getCreatureSubspeciesBeastiaryBlacklist().containsKey(this.creature.getName())
                && ForgeConfigProvider.getCreatureSubspeciesBeastiaryBlacklist().get(this.creature.getName()).contains(subspecies.index));
    }
}
