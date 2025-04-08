package lycanitestweaks.mixin.lycanitesmobsfeatures.customfirenobreakcollision;

import com.lycanitesmobs.core.block.BlockBase;
import com.lycanitesmobs.core.block.BlockFireBase;
import com.lycanitesmobs.core.info.ModInfo;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFireBase.class)
public abstract class BlockFireBaseExtinguishByWorldMixin extends BlockBase {

    public BlockFireBaseExtinguishByWorldMixin(Material material, ModInfo group, String name) {
        super(material, group, name);
    }

    // Intended to fix explosion damage calc treating custom fire as a full explosion damage blocker
    // Highly advised to pair with lycanitestweaks.mixin.vanilla.WorldExtinguishLycanitesFire
    //      AKA Mixin 'Lycanites Fire Extinguish (Vanilla)'
    @Inject(
            method = "<init>",
            at = @At("RETURN"),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobsBlockFireBase_init(Material material, ModInfo group, String name, CallbackInfo ci){
        this.noBreakCollision = true;
    }
}
