package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevelsclient;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.lists.CreatureDescriptionList;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import com.lycanitesmobs.core.info.CreatureManager;
import net.minecraft.client.resources.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreatureDescriptionList.class)
public abstract class CreatureDescriptionListPMLMixin {

    @Shadow(remap = false)
    protected BeastiaryScreen parentGui;
    @Shadow(remap = false)
    public CreatureKnowledge creatureKnowledge;

    @ModifyReturnValue(
            method = "getContent",
            at = @At(value = "RETURN", ordinal = 2),
            remap = false
    )
    public String lycanitesTweaks_lycanitesMobsCreatureDescriptionList_getContentPML(String original){
        if(this.parentGui.playerExt.getBeastiary().hasKnowledgeRank(this.creatureKnowledge.creatureName, 2)){
            StringBuilder textBuilder = new StringBuilder();

            if(CreatureManager.getInstance().config.startingLevelMax > CreatureManager.getInstance().config.startingLevelMin){
                textBuilder.append(I18n.format("gui.beastiary.creatures.mixin.pml.range",
                        CreatureManager.getInstance().config.startingLevelMin,
                        CreatureManager.getInstance().config.startingLevelMax));
            }
            else{
                int timeLevel = (int)(CreatureManager.getInstance().config.levelPerLocalDifficulty * 6.75D);
                if(CreatureManager.getInstance().config.levelPerDay > 0) timeLevel += CreatureManager.getInstance().config.levelPerDayMax;
                textBuilder.append(I18n.format("gui.beastiary.creatures.mixin.pml.time", timeLevel));
            }
            textBuilder.append("\n\n").append(original);
            return textBuilder.toString();
        }
        return original;
    }
}
