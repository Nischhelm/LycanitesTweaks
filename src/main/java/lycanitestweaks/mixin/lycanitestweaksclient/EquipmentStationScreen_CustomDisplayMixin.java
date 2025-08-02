package lycanitestweaks.mixin.lycanitestweaksclient;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.client.AssetManager;
import com.lycanitesmobs.client.gui.BaseContainerScreen;
import com.lycanitesmobs.client.gui.EquipmentStationScreen;
import lycanitestweaks.util.IItemStationDisplay_Mixin;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EquipmentStationScreen.class)
public abstract class EquipmentStationScreen_CustomDisplayMixin extends BaseContainerScreen {

    public EquipmentStationScreen_CustomDisplayMixin(Container container) {
        super(container);
    }

    @Inject(
            method = "drawBars",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0, remap = true),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsEquipmentStationScreen_drawBarsCustom(int backX, int backY, CallbackInfo ci, @Local(ordinal = 2) int barWidth, @Local(ordinal = 3) int barHeight, @Local(ordinal = 4) int barX, @Local(ordinal = 5) int barY, @Local(ordinal = 6) int manaBarY, @Local(ordinal = 7) int barCenter, @Local ItemStack partStack){
        if (partStack.getItem() instanceof IItemStationDisplay_Mixin) {
            IItemStationDisplay_Mixin customItem = (IItemStationDisplay_Mixin)partStack.getItem();
            int sharpness = customItem.lycanitesTweaks$getTopDisplay(partStack);
            int sharpnessMax = customItem.lycanitesTweaks$getTopMaxDisplay(partStack);
            float sharpnessNormal = (float)sharpness / (float)sharpnessMax;
            this.drawTexture(AssetManager.getTexture("GUIPetBarEmpty"), (float)barX, (float)barY, 0.0F, 1.0F, 1.0F, (float)barWidth, (float)barHeight);
            this.drawTexture(AssetManager.getTexture("GUIPetBarHealth"), (float)barX, (float)barY, 0.0F, sharpnessNormal, 1.0F, (float)barWidth * sharpnessNormal, (float)barHeight);
            String sharpnessText = sharpness + "/" + sharpnessMax;
            this.fontRenderer.drawString(sharpnessText, (float)(barCenter - this.fontRenderer.getStringWidth(sharpnessText) / 2), (float)(barY + 2), 0xFFFFFF, true);
            int mana = customItem.lycanitesTweaks$getBottomDisplay(partStack);
            int manaMax = customItem.lycanitesTweaks$getBottomMaxDisplay(partStack);
            float manaNormal = (float)mana / (float)manaMax;
            this.drawTexture(AssetManager.getTexture("GUIPetBarEmpty"), (float)barX, (float)manaBarY, 0.0F, 1.0F, 1.0F, (float)barWidth, (float)barHeight);
            this.drawTexture(AssetManager.getTexture("GUIPetBarRespawn"), (float)barX, (float)manaBarY, 0.0F, manaNormal, 1.0F, (float)barWidth * manaNormal, (float)barHeight);
            String manaText = mana + "/" + manaMax;
            this.fontRenderer.drawString(manaText, (float)(barCenter - this.fontRenderer.getStringWidth(manaText) / 2), (float)(manaBarY + 2), 0xFFFFFF, true);
        }
    }
}
