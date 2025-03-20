package lycanitestweaks.mixin.lycanitesmobsfeatures.tamedbabypotion;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
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

@Mixin(TameableCreatureEntity.class)
public abstract class TameableCreatureEntityBabyPotionMixin extends AgeableCreatureEntity {

    @Unique
    private final String COMMAND_AGE_BABY = "baby age";

    public TameableCreatureEntityBabyPotionMixin(World world) {
        super(world);
    }

    @Shadow(remap = false)
    public abstract EntityPlayer getPlayerOwner();

    @Inject(
            method = "getInteractCommands",
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;putAll(Ljava/util/Map;)V"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesTameableCreatureEntity_getInteractCommandsSize(EntityPlayer player, EnumHand hand, ItemStack itemStack, CallbackInfoReturnable<HashMap<Integer, String>> cir, @Local() HashMap<Integer, String> commands) {
        if(player.isSneaking() && itemStack.getItem() instanceof ItemPotion && lycanitesTweaks$canPlayerModify(player)){
            commands.put(BaseCreatureEntity.COMMAND_PIORITIES.ITEM_USE.id, COMMAND_AGE_BABY);
        }
    }

    @Inject(
            method = "performCommand",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void lycanitesTweaks_lycanitesTameableCreatureEntity_performCommandSoulkey(String command, EntityPlayer player, EnumHand hand, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir){
        if(COMMAND_AGE_BABY.equals(command)) {
            this.setGrowingAge(this.growthTime);
            this.consumePlayersItem(player, hand, itemStack);
            cir.setReturnValue(true);
        }
    }

    // Causes a visual desync for summoned temporary minions for some reason
    @Unique
    public boolean lycanitesTweaks$canPlayerModify(EntityPlayer player){
        if(this.isTemporary) return false;
        else if (this.isChild()) return false;
        else if(this.getPlayerOwner() == player) return true;
        return false;
    }
}
