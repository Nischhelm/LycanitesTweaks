package lycanitestweaks.potion;

import net.minecraft.entity.EntityLivingBase;

public class PotionConsumed extends PotionBase {

    public static final PotionConsumed INSTANCE = new PotionConsumed();

    public PotionConsumed() {
        super("consumed", false, 0xF3F4F9);
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