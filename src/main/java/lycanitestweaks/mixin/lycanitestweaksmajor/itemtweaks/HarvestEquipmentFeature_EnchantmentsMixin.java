package lycanitestweaks.mixin.lycanitestweaksmajor.itemtweaks;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.item.equipment.features.HarvestEquipmentFeature;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HarvestEquipmentFeature.class)
public abstract class HarvestEquipmentFeature_EnchantmentsMixin {

    @Unique
    private boolean lycanitesTweaks$isHarvestLooping = false;

    @Inject(
            method = "onBlockDestroyed",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsHarvestEquipmentFeature_onBlockDestroyedClientCancel(World world, IBlockState harvestedBlockState, BlockPos harvestedPos, EntityLivingBase livingEntity, CallbackInfo ci){
        if(world.isRemote) ci.cancel();
    }

    @Inject(
            method = "onBlockDestroyed",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsHarvestEquipmentFeature_onBlockDestroyedHarvestMutexLock(World world, IBlockState harvestedBlockState, BlockPos harvestedPos, EntityLivingBase livingEntity, CallbackInfo ci){
        if(lycanitesTweaks$isHarvestLooping) ci.cancel();
        lycanitesTweaks$isHarvestLooping = true;
    }

    @WrapWithCondition(
            method = "onBlockDestroyed",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;destroyBlock(Lnet/minecraft/util/math/BlockPos;Z)Z", remap = true),
            remap = false
    )
    public boolean lycanitesTweaks_lycanitesMobsHarvestEquipmentFeature_onBlockDestroyedHarvest(World instance, BlockPos blockPos, boolean dropBlockAsItem, @Local(argsOnly = true) EntityLivingBase livingEntity){
        if(livingEntity instanceof EntityPlayerMP){
            ((EntityPlayerMP)livingEntity).interactionManager.tryHarvestBlock(blockPos);
            return false;
        }
        return true;
    }

    @Inject(
            method = "onBlockDestroyed",
            at = @At("RETURN"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsHarvestEquipmentFeature_onBlockDestroyedHarvestMutexUnlock(World world, IBlockState harvestedBlockState, BlockPos harvestedPos, EntityLivingBase livingEntity, CallbackInfo ci){
        lycanitesTweaks$isHarvestLooping = false;
    }
}
