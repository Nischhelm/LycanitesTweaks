package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevelsclient;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.lists.CreatureDescriptionList;
import com.lycanitesmobs.core.info.CreatureKnowledge;
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
            at = @At("RETURN"),
            remap = false
    )
    public String lycanitesTweaks_lycanitesMobsCreatureDescriptionList_getContentPML(String original){
        if(this.parentGui.playerExt.getBeastiary().hasKnowledgeRank(this.creatureKnowledge.creatureName, 2)){
            StringBuilder textBuilder = new StringBuilder(original);
            textBuilder.insert(0, "\n\n").insert(0, I18n.format("gui.beastiary.creatures.mixin.pml"));
            return textBuilder.toString();
        }
        return original;
    }
}
