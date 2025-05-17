package lycanitestweaks.mixin.ddd;

import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.PetsBeastiaryScreen;
import com.lycanitesmobs.core.item.ItemBase;
import lycanitestweaks.client.gui.DDDDescriptionList;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PetsBeastiaryScreen.class)
public abstract class PetsBeastiaryScreenDDDMixin  extends BeastiaryScreen {

    @Unique
    public DDDDescriptionList lycanitesTweaks$dddDescriptionList;

    public PetsBeastiaryScreenDDDMixin(EntityPlayer player) {
        super(player);
    }

    @Inject(
            method = "initControls",
            at = @At("TAIL"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsPetsBeastiaryScreen_initControlsDDD(CallbackInfo ci){
        int width = (int)(ItemBase.DESCRIPTION_WIDTH * 0.8);
        int height = width / 4;
        int top = (int)(this.colRightY * 0.75);
        int bottom = (int)(this.colRightY * 1.25);
        int xPos = (int) (this.colRightX * 2.6);

        lycanitesTweaks$dddDescriptionList = new DDDDescriptionList(this, width, height, top, bottom, xPos);
    }

    @Inject(
            method = "drawForeground",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/client/gui/beastiary/PetsBeastiaryScreen;renderCreature(Lcom/lycanitesmobs/core/info/CreatureInfo;IIIIF)V"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsPetsBeastiaryScreen_drawForegroundDDD(int mouseX, int mouseY, float partialTicks, CallbackInfo ci){
        this.lycanitesTweaks$dddDescriptionList.drawScreen(mouseX, mouseY, partialTicks);
    }
}
