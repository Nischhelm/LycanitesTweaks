package lycanitestweaks.potion;

import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

public class PotionConsumed extends PotionBase {

    public static final PotionConsumed INSTANCE = new PotionConsumed();
    public String UUID_HEALTH = "a7c92128-9cd8-4309-9c94-352e3be1029b";

    public PotionConsumed() {
        super("consumed", false, 0xF3F4F9);
        registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, UUID_HEALTH, ForgeConfigHandler.server.effectsConfig.consumedHealthModifier, 2);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if(entityLivingBase.world.isRemote) return;
        if(ForgeConfigHandler.server.effectsConfig.consumedHealthModifier == -1) entityLivingBase.setHealth(0);
    }
}