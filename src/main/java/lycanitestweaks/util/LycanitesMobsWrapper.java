package lycanitestweaks.util;

import com.lycanitesmobs.ObjectManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public abstract class LycanitesMobsWrapper {

    // Should only use EntityLivingBase as param
    public static boolean hasSmitedEffect(Entity living){
        return ((EntityLivingBase)living).isPotionActive(ObjectManager.getEffect("smited"));
    }
}
