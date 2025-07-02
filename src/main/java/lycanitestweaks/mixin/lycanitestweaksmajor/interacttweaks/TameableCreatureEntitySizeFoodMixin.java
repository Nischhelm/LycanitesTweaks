package lycanitestweaks.mixin.lycanitestweaksmajor.interacttweaks;

import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.TameableCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.item.GenericFoodItem;
import lycanitestweaks.handlers.ForgeConfigHandler;
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

@Mixin(TameableCreatureEntity.class)
public abstract class TameableCreatureEntitySizeFoodMixin extends AgeableCreatureEntity {

    @Unique
    private final String COMMAND_SIZE_UP = "size up";
    @Unique
    private final String COMMAND_SIZE_DOWN = "size down";

    public TameableCreatureEntitySizeFoodMixin(World world) {
        super(world);
    }

    @Shadow(remap = false)
    public abstract EntityPlayer getPlayerOwner();
    @Shadow(remap = false)
    public abstract boolean isSitting();
    @Shadow(remap = false)
    public abstract void playEatSound();

    @Inject(
            method = "getInteractCommands",
            at = @At(value = "INVOKE", target = "Ljava/util/HashMap;putAll(Ljava/util/Map;)V"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesTameableCreatureEntity_getInteractCommandsSize(EntityPlayer player, EnumHand hand, ItemStack itemStack, CallbackInfoReturnable<HashMap<Integer, String>> cir, @Local() HashMap<Integer, String> commands){
        if(this.isSitting()){
            if(lycanitesTweaks$canPlayerSetSize(player, true) && itemStack.getItem() instanceof GenericFoodItem && ((GenericFoodItem) itemStack.getItem()).itemName.equals("battle_burrito")){
                commands.put(BaseCreatureEntity.COMMAND_PIORITIES.ITEM_USE.id, COMMAND_SIZE_UP);
            }
            else if(lycanitesTweaks$canPlayerSetSize(player, false) && itemStack.getItem() instanceof GenericFoodItem && ((GenericFoodItem) itemStack.getItem()).itemName.equals("explorers_risotto")) {
                commands.put(BaseCreatureEntity.COMMAND_PIORITIES.ITEM_USE.id, COMMAND_SIZE_DOWN);
            }
        }
    }

    @Inject(
            method = "performCommand",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void lycanitesTweaks_lycanitesTameableCreatureEntity_performCommandSize(String command, EntityPlayer player, EnumHand hand, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (COMMAND_SIZE_UP.equals(command)) {
            this.setSizeScale(lycanitesTweaks$getModifiedSizeScale(true));
            if(isBoundPet()){
                this.getPetEntry().setEntitySize(this.sizeScale);
            }
            this.playEatSound();
            this.consumePlayersItem(player, hand, itemStack);
            cir.setReturnValue(true);
        }
        else if(COMMAND_SIZE_DOWN.equals(command)) {
            this.setSizeScale(lycanitesTweaks$getModifiedSizeScale(false));
            if(isBoundPet()){
                this.getPetEntry().setEntitySize(this.sizeScale);
            }
            this.playEatSound();
            this.consumePlayersItem(player, hand, itemStack);
            cir.setReturnValue(true);
        }
    }

    @Unique
    public double lycanitesTweaks$getModifiedSizeScale(boolean sizeUp){
        double range = ForgeConfigHandler.majorFeaturesConfig.creatureInteractConfig.sizeChangeDegree * (CreatureManager.getInstance().config.randomSizeMax - CreatureManager.getInstance().config.randomSizeMin);
        range *= (sizeUp) ? 1 : -1;
        double size = this.sizeScale + range;
        return Math.min(CreatureManager.getInstance().config.randomSizeMax, Math.max(CreatureManager.getInstance().config.randomSizeMin, size));
    }

    @Unique
    public boolean lycanitesTweaks$canPlayerSetSize(EntityPlayer player, boolean sizeUp){
        if(this.isTemporary) return false;
        else if(!sizeUp && sizeScale <= CreatureManager.getInstance().config.randomSizeMin) return false;
        else if(sizeUp && sizeScale >= CreatureManager.getInstance().config.randomSizeMax) return false;
        else if(this.getPlayerOwner() != player) return false;
        else return true;
    }
}
