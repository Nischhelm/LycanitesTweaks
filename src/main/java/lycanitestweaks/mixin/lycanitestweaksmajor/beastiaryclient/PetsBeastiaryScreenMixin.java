package lycanitestweaks.mixin.lycanitestweaksmajor.beastiaryclient;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.GuiHandler;
import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.PetsBeastiaryScreen;
import com.lycanitesmobs.core.pets.PetEntry;
import lycanitestweaks.capability.lycanitestweaksplayer.ILycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.lycanitestweaksplayer.LycanitesTweaksPlayerCapability;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PetsBeastiaryScreen.class)
public abstract class PetsBeastiaryScreenMixin extends BeastiaryScreen {

    @Unique
    private static final int KEYBOUND_BUTTON_ID = GuiHandler.Beastiary.PETS.id - 69420;

    @Unique
    public GuiButton lycanitesTweaks$setKeyboundButton;

    public PetsBeastiaryScreenMixin(EntityPlayer player) {
        super(player);
    }

    @Inject(
            method = "initControls",
            at = @At("TAIL"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsPetsBeastiaryScreen_initControls(CallbackInfo ci){
        lycanitesTweaks$setKeyboundButton = new GuiButton(KEYBOUND_BUTTON_ID, this.colRightX * 3, this.colRightY + 20, 80, 20, I18n.format("gui.beastiary.pets.mixin.keybound"));
        this.buttonList.add(lycanitesTweaks$setKeyboundButton);
    }

    @Inject(
            method = "drawForeground",
            at = @At("TAIL"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsCreaturesBeastiaryScreen_drawForeground(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        ILycanitesTweaksPlayerCapability ltp = LycanitesTweaksPlayerCapability.getForPlayer(player);
        if(this.creaturePreviewEntity == null
                || ltp == null
                || ltp.getKeyboundPetID() == this.playerExt.selectedPet.petEntryID){
            this.lycanitesTweaks$setKeyboundButton.visible = false;
        }
        else this.lycanitesTweaks$setKeyboundButton.visible = true;
    }

    @Inject(
            method = "actionPerformed",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/GuiButton;id:I", ordinal = 0)
    )
    public void lycanitesTweaks_lycanitesMobsPetsBeastiaryScreen_actionPerformed(GuiButton button, CallbackInfo ci, @Local PetEntry petEntry){
        if(button.id == KEYBOUND_BUTTON_ID){
            ILycanitesTweaksPlayerCapability ltp = LycanitesTweaksPlayerCapability.getForPlayer(player);
            if(ltp != null) ltp.setKeyboundPet(petEntry);
        }
    }
}
