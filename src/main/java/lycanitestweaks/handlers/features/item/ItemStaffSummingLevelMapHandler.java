package lycanitestweaks.handlers.features.item;

import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.IItemStaffSummoningElementLevelMapMixin;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

public class ItemStaffSummingLevelMapHandler {

    // Using this as Forge Capabilities on ItemStack seems to attempt attaching the same stack a lot
    // Either should use Forge Caps are just settle with this disaster
    @SubscribeEvent
    public static void checkPlayerEquipmentLevels(LivingEquipmentChangeEvent event){
        if(event.getTo().getItem() instanceof IItemStaffSummoningElementLevelMapMixin) {
            if (event.getSlot() == EntityEquipmentSlot.MAINHAND || event.getSlot() == EntityEquipmentSlot.OFFHAND) {
                if(ForgeConfigHandler.client.debugLoggerAutomatic) LycanitesTweaks.LOGGER.log(Level.INFO, "Found LevelMapItem in slot:{}", event.getSlot());
                ((IItemStaffSummoningElementLevelMapMixin) event.getTo().getItem()).lycanitesTweaks$setItemStack(event.getTo());
            }
        }
    }
}
