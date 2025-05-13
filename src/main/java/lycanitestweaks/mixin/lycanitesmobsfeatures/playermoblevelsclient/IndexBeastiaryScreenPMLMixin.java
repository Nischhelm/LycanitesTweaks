package lycanitestweaks.mixin.lycanitesmobsfeatures.playermoblevelsclient;

import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.IndexBeastiaryScreen;
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

import java.util.HashMap;

@Mixin(IndexBeastiaryScreen.class)
public abstract class IndexBeastiaryScreenPMLMixin extends BeastiaryScreen {

    @Unique
    private  HashMap<PlayerMobLevelsConfig.BonusCategory, Integer> lycanitesTweaks$pmlBonusCateogories = new HashMap<>();

    public IndexBeastiaryScreenPMLMixin(EntityPlayer player) {
        super(player);
    }

    @Inject(
            method = "drawForeground",
            at = @At("TAIL"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsIndexBeastiaryScreen_drawForegroundPML(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        int xOffset = this.colLeftX;
        int yOffset = this.colLeftY;
        if(this.player.ticksExisted % 20 == 0){
            IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(this.player);
            if(pml != null) {
                for (PlayerMobLevelsConfig.BonusCategory category : PlayerMobLevelsConfig.getPmlBonusCateogories().keySet()) {
                    lycanitesTweaks$pmlBonusCateogories.put(category, pml.getTotalLevelsForCategory(category, null, true));
                }
            }
        }
        String text = I18n.format("gui.beastiary.index.mixin.category");
        this.getFontRenderer().drawString(text, xOffset, yOffset, 0xFFFFFF, true);
        yOffset += 4 + this.getFontRenderer().getWordWrappedHeight(text, this.colLeftWidth);
        for(PlayerMobLevelsConfig.BonusCategory category : lycanitesTweaks$pmlBonusCateogories.keySet()) {
            text = I18n.format("gui.beastiary.index.mixin." + category.name(), lycanitesTweaks$pmlBonusCateogories.get(category));
            this.getFontRenderer().drawString(text, xOffset, yOffset, 0xFFFFFF, true);
            yOffset += 4 + this.getFontRenderer().getWordWrappedHeight(text, this.colLeftWidth);
        }

        IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(this.player);
        if(pml != null) {
            text = I18n.format("gui.beastiary.index.mixin.deathcooldown", pml.getDeathCooldown() / 20);
            this.getFontRenderer().drawString(text, xOffset, yOffset, 0xFFFFFF, true);
        }
    }
}
