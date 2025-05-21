package lycanitestweaks.mixin.lycanitestweaksmajor.itemtweaks;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.item.special.ItemSoulkey;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntitySoulKeyVariantMixin extends EntityLiving {

    @Unique
    private final String COMMAND_VARIANT_ANIMAL = "animal variant";

    public BaseCreatureEntitySoulKeyVariantMixin(World worldIn) {
        super(worldIn);
    }

    @Shadow(remap = false)
    public abstract void applyVariant(int variantIndex);

    @Shadow(remap = false)
    public abstract void consumePlayersItem(EntityPlayer player, EnumHand hand, ItemStack itemStack);

    @Shadow(remap = false)
    public abstract int getVariantIndex();

    @Inject(
            method = "getInteractCommands",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0, remap = true),
            remap = false
    )
    public void lycanitesTweaks_lycanitesBaseCreatureEntity_getInteractCommandsSoulkey(EntityPlayer player, EnumHand hand, ItemStack itemStack, CallbackInfoReturnable<HashMap<Integer, String>> cir, @Local() HashMap<Integer, String> commands){
        if(itemStack.getItem() instanceof ItemSoulkey
                && hand == EnumHand.OFF_HAND && player.isSneaking()
                && CreatureManager.getInstance().creatureGroups.get("animal").hasEntity(this)){
            commands.put(BaseCreatureEntity.COMMAND_PIORITIES.ITEM_USE.id, COMMAND_VARIANT_ANIMAL);
        }
    }

    @Inject(
            method = "performCommand",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void lycanitesTweaks_lycanitesBaseCreatureEntity_performCommandSoulkey(String command, EntityPlayer player, EnumHand hand, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        if(COMMAND_VARIANT_ANIMAL.equals(command)){
            if(((ItemSoulkey)itemStack.getItem()).variant != this.getVariantIndex()) {
                this.applyVariant(((ItemSoulkey) itemStack.getItem()).variant);
                this.consumePlayersItem(player, hand, itemStack);
                cir.setReturnValue(true);
            }
        }
    }
}
