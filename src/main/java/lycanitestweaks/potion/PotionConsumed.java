package lycanitestweaks.potion;

import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PotionConsumed extends PotionBase {

    public static final PotionConsumed INSTANCE = new PotionConsumed();
    public String UUID_HEALTH = "a7c92128-9cd8-4309-9c94-352e3be1029b";

    public PotionConsumed() {
        super("consumed", true, 0xFFFFFF);
        registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, this.UUID_HEALTH, ForgeConfigHandler.server.effectsConfig.consumedHealthModifier, 2);
    }

    @Override
    @Nonnull
    public List<ItemStack> getCurativeItems(){
        return new ArrayList<>();
    }

    @Override
    public void applyAttributesModifiersToEntity(@Nonnull EntityLivingBase entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier){
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
        }
    }

    @Override
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier){
        return modifier.getAmount();
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if(entityLivingBase.world.isRemote) return;

        if(entityLivingBase instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLivingBase;

            if (ForgeConfigHandler.server.effectsConfig.consumedItemCooldown > 0) {
                player.getCooldownTracker().setCooldown(player.getHeldItemMainhand().getItem(), ForgeConfigHandler.server.effectsConfig.consumedItemCooldown);
                player.getCooldownTracker().setCooldown(player.getHeldItemOffhand().getItem(), ForgeConfigHandler.server.effectsConfig.consumedItemCooldown);
            }
        }
    }
}