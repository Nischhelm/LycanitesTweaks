package lycanitestweaks.mixin.lycanitestweaksmajor.playermoblevels.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.SummoningBeastiaryScreen;
import com.lycanitesmobs.core.info.CreatureInfo;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Helpers;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SummoningBeastiaryScreen.class)
public abstract class SummoningBeastiaryScreenImperfectMixin extends BeastiaryScreen {

    public SummoningBeastiaryScreenImperfectMixin(EntityPlayer player) {
        super(player);
    }

    @Inject(
            method = "drawForeground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I", ordinal = 0, remap = true),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsSummoningBeastiaryScreen_drawForegroundImperfect(int mouseX, int mouseY, float partialTicks, CallbackInfo ci, @Local(ordinal = 3) int nextX, @Local(ordinal = 4) int nextY, @Local CreatureInfo selectedCreature, @Local String text){
        if(selectedCreature != null){
            nextX += (int)2.5F;

            if(ForgeConfigHandler.majorFeaturesConfig.pmlConfig.playerMobLevelSummonStaff){
                nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, this.colRightWidth);
            }

            if(ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectSummoning &&
                    !this.playerExt.getBeastiary().hasKnowledgeRank(selectedCreature.getName(), ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.variantSummonRank)) {
                this.getFontRenderer().drawString(I18n.format("gui.beastiary.summoning.mixin.imperfect"), nextX, nextY, 0xFFFFFF, true);
                nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, this.colRightWidth);
                this.getFontRenderer().drawString(I18n.format("gui.beastiary.summoning.mixin.imperfect.hostile", (int)(100 * Helpers.getImperfectHostileChance(playerExt, selectedCreature))), nextX, nextY, 0xFFFFFF, true);
                nextY += 4 + this.getFontRenderer().getWordWrappedHeight(text, this.colRightWidth);
                this.getFontRenderer().drawString(I18n.format("gui.beastiary.summoning.mixin.imperfect.stats",(int)(100 * Helpers.getImperfectStatsChance(playerExt, selectedCreature))), nextX, nextY, 0xFFFFFF, true);
            }

        }
    }
}
