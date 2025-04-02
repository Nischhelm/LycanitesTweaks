package lycanitestweaks.potion;

import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

public class PotionVoided extends PotionBase {

    public static final PotionVoided INSTANCE = new PotionVoided();
    public String UUID_HEALTH = "957edfae-9e9c-43e7-9910-f2665e2bdb9a";

    public PotionVoided() {
        super("voided", false, 0xF3F4F9);
        registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, UUID_HEALTH, ForgeConfigHandler.server.effectsConfig.voidedHealthModifier, 2);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBase, int amplifier) {
        if(entityLivingBase.world.isRemote) return;
        if(ForgeConfigHandler.server.effectsConfig.voidedHealthModifier == -1) entityLivingBase.setHealth(0);
    }
}