package lycanitestweaks.potion;

import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import javax.annotation.Nonnull;

public class PotionConsumed extends PotionBase {

    public static final PotionConsumed INSTANCE = new PotionConsumed();
    public String UUID_HEALTH = "a7c92128-9cd8-4309-9c94-352e3be1029b";

    public PotionConsumed() {
        super("consumed", false, 0xF3F4F9);
        registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, UUID_HEALTH, ForgeConfigHandler.server.effectsConfig.consumedHealthModifier, 2);
    }

    public void applyAttributesModifiersToEntity(@Nonnull EntityLivingBase entityLivingBaseIn, @Nonnull AbstractAttributeMap attributeMapIn, int amplifier){
        super.applyAttributesModifiersToEntity(entityLivingBaseIn, attributeMapIn, amplifier);
        if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth()) {
            entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
        }
    }

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
    }
}