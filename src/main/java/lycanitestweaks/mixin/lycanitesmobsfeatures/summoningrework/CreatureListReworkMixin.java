package lycanitestweaks.mixin.lycanitesmobsfeatures.summoningrework;

import com.lycanitesmobs.client.gui.beastiary.lists.CreatureList;
import lycanitestweaks.handlers.ForgeConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CreatureList.class)
public abstract class CreatureListReworkMixin {

    // Client and Gameplay
    @ModifyConstant(
            method = "refreshList",
            constant = @Constant(intValue = 2),
            remap = false
    )
    public int lycanitesTweaks_lycanitesCreatureList_refreshList(int constant){
        return ForgeConfigHandler.server.imperfectSummoningConfig.normalSummonRank;
    }
}
