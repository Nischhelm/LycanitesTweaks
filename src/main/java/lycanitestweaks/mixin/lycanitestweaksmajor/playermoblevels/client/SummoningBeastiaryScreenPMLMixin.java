package lycanitestweaks.mixin.lycanitestweaksmajor.playermoblevels.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.SummoningBeastiaryScreen;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureInfo;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import lycanitestweaks.handlers.config.PlayerMobLevelsConfig;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SummoningBeastiaryScreen.class)
public abstract class SummoningBeastiaryScreenPMLMixin extends BeastiaryScreen {

    @Unique
    private int lycanitesTweaks$cachePML = 0;

    public SummoningBeastiaryScreenPMLMixin(EntityPlayer player) {
        super(player);
    }

    @Inject(
            method = "drawForeground",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I", ordinal = 0, remap = true),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsSummoningBeastiaryScreen_drawForegroundPML(int mouseX, int mouseY, float partialTicks, CallbackInfo ci, @Local(ordinal = 3) int nextX, @Local(ordinal = 4) int nextY, @Local CreatureInfo selectedCreature, @Local String text){
        if(selectedCreature != null){
            nextX = Math.round(nextX * 2.5F);

            IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(this.player);
            if(pml != null && this.creaturePreviewEntity instanceof BaseCreatureEntity){
                if(this.player.ticksExisted % 20 == 0) lycanitesTweaks$cachePML = pml.getTotalLevelsForCategory(PlayerMobLevelsConfig.BonusCategory.SummonMinion, (BaseCreatureEntity)this.creaturePreviewEntity, true);
                this.getFontRenderer().drawString(I18n.format("gui.beastiary.summoning.mixin.pml", lycanitesTweaks$cachePML), nextX, nextY, 0xFFFFFF, true);
            }
        }
    }
}
