package lycanitestweaks.compat;

import com.charles445.rltweaker.handler.LycanitesHandler;
import net.minecraft.entity.Entity;

public abstract class RLTweakerHandler
{
    public static void setRLTweakerBossRange(Entity entity, int range){
        LycanitesHandler.setLycanitesBoss(entity, true);
        LycanitesHandler.setLycanitesBossRange(entity, range);
    }
}
