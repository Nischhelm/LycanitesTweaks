package lycanitestweaks.mixin.lycanitestweaksmajor.entitystorecrystals.altar;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.altar.AltarInfoMottleAbaia;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AltarInfoMottleAbaia.class)
public abstract class AltarInfoMottleAbaiaCrystalMixin {

    @WrapWithCondition(
            method = "activate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockToAir(Lnet/minecraft/util/math/BlockPos;)Z", remap = true),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsAltarInfoMottleAbaia_activateAltarBlocks(World instance, BlockPos pos){
        return instance.getBlockState(pos).getBlock() == Blocks.OBSIDIAN || instance.getBlockState(pos).getBlock() == Blocks.DIAMOND_BLOCK;
    }

    @Redirect(
            method = "activate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z", remap = true),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsAltarInfoMottleAbaia_activateSpawnCrystal(World instance, Entity entity, @Local(argsOnly = true) World world, @Local(argsOnly = true) BlockPos blockPos){
        return world.spawnEntity(EntityBossSummonCrystal.storeAltarBoss(instance, (BaseCreatureEntity)entity, blockPos));
    }
}
