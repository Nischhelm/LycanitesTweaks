package lycanitestweaks.mixin.lycanitesmobsfeatures.minibossaltarcrystals;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.altar.AltarInfoCelestialGeonach;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AltarInfoCelestialGeonach.class)
public abstract class AltarInfoCelestialGeonachCrystalMixin {

    @WrapWithCondition(
            method = "activate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockToAir(Lnet/minecraft/util/math/BlockPos;)Z", remap = true),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsAltarInfoCelestialGeonach_activateAltarBlocks(World instance, BlockPos pos){
        return instance.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || instance.getBlockState(pos).getBlock() == Blocks.DIAMOND_BLOCK;
    }

    @Redirect(
            method = "activate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", remap = true),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsAltarInfoCelestialGeonach_activateSpawnCrystal(World instance, Entity entity, @Local(argsOnly = true) Entity entityActivator, @Local(argsOnly = true) World world, @Local(argsOnly = true) BlockPos blockPos){
        return world.spawnEntity(EntityBossSummonCrystal.storeAltarBoss(entityActivator, instance, (BaseCreatureEntity)entity, blockPos));
    }
}
