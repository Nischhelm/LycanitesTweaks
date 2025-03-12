package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.google.gson.JsonObject;
import com.lycanitesmobs.core.info.ItemDrop;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDrop.class)
public abstract class ItemDropBabyDropMixin {


    @Shadow(remap = false)
    public boolean adultOnly; // I wish setting this to false would work

    @Inject(
            method = "loadFromJSON",
            at = @At("HEAD"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesItemDrop_loadFromJSON(JsonObject json, CallbackInfo ci){
        adultOnly = false;
    }

    @Inject(
            method = "readFromNBT",
            at = @At("HEAD"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesItemDrop_readFromNBT(NBTTagCompound nbtTagCompound, CallbackInfo ci){
        adultOnly = false;
    }
}
