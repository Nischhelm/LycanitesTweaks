package lycanitestweaks.mixin.vanilla;

import lycanitestweaks.wrapper.IClientPotionApplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class EntityLivingBaseClientPotionsMixin extends Entity implements IClientPotionApplier {
	
	public EntityLivingBaseClientPotionsMixin(World worldIn) {
		super(worldIn);
	}

	@Unique
	private boolean lycanitesTweaks$isPacket = false;

	@Override
	public void lycanitesTweaks$setIsPacket(boolean isPacket) {
		this.lycanitesTweaks$isPacket = isPacket;
	}

	@Inject(
			method = "addPotionEffect",
			at = @At("HEAD"),
			cancellable = true
	)
	private void lycanitesTweaks_vanillaEntityLivingBase_addPotionEffectClientCancel(PotionEffect potioneffectIn, CallbackInfo ci) {
		if(!this.world.isRemote) return;
		if(this.lycanitesTweaks$isPacket) {
			lycanitesTweaks$setIsPacket(false);
			return;
		}
		ci.cancel();
	}
}