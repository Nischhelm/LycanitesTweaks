package lycanitestweaks.handlers.features.boss;

import com.lycanitesmobs.ExtendedWorld;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

public abstract class MoreBlockProtectionHandler {

    @SubscribeEvent
    public static void onBlockBreak(LivingDestroyBlockEvent event) {
        if(event.getState() == null
                || event.isCanceled()
                || event.getEntityLiving() == null
                || event.getEntityLiving().getEntityWorld().isRemote) {
            return;
        }

        ExtendedWorld extendedWorld = ExtendedWorld.getForWorld(event.getEntity().getEntityWorld());
        if (extendedWorld.isBossNearby(new Vec3d(event.getPos()))) {
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
            if(ForgeConfigHandler.client.debugLoggerAutomatic)
                LycanitesTweaks.LOGGER.log(Level.INFO, "Boss prevented block at {}, from being broke by {}", event.getPos(), event.getEntityLiving());
        }
    }
}
