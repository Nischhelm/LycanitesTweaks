package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.altar.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {
        AltarInfoCelestialGeonach.class,
        AltarInfoCrimsonEpion.class,
        AltarInfoEbonCacodemon.class,
        AltarInfoLunarGrue.class,
        AltarInfoMottleAbaia.class,
        AltarInfoPhosphorescentChupacabra.class,
        AltarInfoRoyalArchvile.class,
        AltarInfoUmberLobber.class
})
public abstract class AltarInfoMiniBossMixin_DestroyPostsForgeEvent {

    @WrapWithCondition(
            method = "activate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockToAir(Lnet/minecraft/util/math/BlockPos;)Z", remap = true),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsAltarInfoMiniBoss_activate(World instance, BlockPos pos, @Local BaseCreatureEntity altarBoss){
        return ForgeEventFactory.onEntityDestroyBlock(altarBoss, pos, instance.getBlockState(pos));
    }
}
