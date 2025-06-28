package lycanitestweaks.mixin.lycanitestweaksmajor.beastiaryclient;

import com.lycanitesmobs.client.gui.BaseScreen;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import lycanitestweaks.client.gui.beastiary.AltarsBeastiaryScreen;
import lycanitestweaks.client.gui.beastiary.PMLBeastiaryScreen;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeastiaryScreen.class)
public abstract class BeastiaryScreenAltarTabMixin extends BaseScreen {

    @Shadow(remap = false)
    public EntityPlayer player;

    @Shadow(remap = false)
    public int centerX;

    @Shadow(remap = false)
    public int windowWidth;

    @Shadow(remap = false)
    public int windowY;

    @Inject(
            method = "initControls",
            at = @At("TAIL"),
            remap = false
    )
    public void sss(CallbackInfo ci){
        int menuPadding = 6;
        int menuX = this.centerX - Math.round((float)this.windowWidth / 2.0F) + menuPadding;
        int menuY = 2 * this.windowY + menuPadding;
        int menuWidth = this.windowWidth - menuPadding * 2;
        int buttonCount = 5;
        int buttonPadding = 2;
        int buttonX = menuX + buttonPadding;
        int buttonWidth = Math.round((float)(menuWidth / buttonCount)) - buttonPadding * 2;
        int buttonWidthPadded = buttonWidth + buttonPadding * 2;
        int buttonHeight = 20;
        this.buttonList.add(new GuiButton(AltarsBeastiaryScreen.BEASTIARY_ALTAR_ID, buttonX + (buttonWidthPadded * (this.buttonList.size() - 5)), menuY, buttonWidth, buttonHeight, I18n.format("gui.beastiary.altars")));
        this.buttonList.add(new GuiButton(PMLBeastiaryScreen.BEASTIARY_PML_ID, buttonX + (buttonWidthPadded * (this.buttonList.size() - 5)), menuY, buttonWidth, buttonHeight, I18n.format("gui.beastiary.pml")));
    }

    @Inject(
            method = "actionPerformed",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/client/gui/BaseScreen;actionPerformed(Lnet/minecraft/client/gui/GuiButton;)V")
    )
    public void www(GuiButton guiButton, CallbackInfo ci) {
        if (guiButton != null) {
            if (guiButton.id == AltarsBeastiaryScreen.BEASTIARY_ALTAR_ID) {
                AltarsBeastiaryScreen.openToPlayer(this.player);
            }
            if (guiButton.id == PMLBeastiaryScreen.BEASTIARY_PML_ID) {
                PMLBeastiaryScreen.openToPlayer(this.player);
            }
        }
    }
}
