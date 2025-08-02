package lycanitestweaks.mixin.lycanitestweaksclient;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.AssetManager;
import com.lycanitesmobs.client.gui.BaseContainerScreen;
import com.lycanitesmobs.client.gui.EquipmentInfuserScreen;
import com.lycanitesmobs.client.localisation.LanguageManager;
import lycanitestweaks.util.IItemInfuserDisplay_Mixin;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EquipmentInfuserScreen.class)
public abstract class EquipmentInfuserScreen_CustomDisplayMixin extends BaseContainerScreen {

    public EquipmentInfuserScreen_CustomDisplayMixin(Container container) {
        super(container);
    }

    @Inject(
            method = "drawBars",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0, remap = true),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEquipmentInfuserScreen_drawBarsCustom(int backX, int backY, CallbackInfo ci, @Local(ordinal = 2) int barWidth, @Local(ordinal = 3) int barHeight, @Local(ordinal = 4) int barX, @Local(ordinal = 5) int barY, @Local(ordinal = 6) int barCenter, @Local ItemStack partStack){
        if (partStack.getItem() instanceof IItemInfuserDisplay_Mixin) {
            IItemInfuserDisplay_Mixin customItem = (IItemInfuserDisplay_Mixin)partStack.getItem();
            int experience = customItem.lycanitesTweaks$getExperienceDisplay(partStack);
            int experienceMax = customItem.lycanitesTweaks$getNextLevelDisplay(partStack);
            float experienceNormal = (float)experience / (float)experienceMax;
            this.drawTexture(AssetManager.getTexture("GUIBarExperience"), (float)barX, (float)barY, 0.0F, experienceNormal, 1.0F, (float)barWidth * experienceNormal, (float)barHeight);
            String experienceText = LanguageManager.translate("entity.experience") + ": " + experience + "/" + experienceMax;
            this.fontRenderer.drawString(experienceText, barCenter - this.fontRenderer.getStringWidth(experienceText) / 2, barY + 2, 0xFFFFFF);
        }
    }
}
