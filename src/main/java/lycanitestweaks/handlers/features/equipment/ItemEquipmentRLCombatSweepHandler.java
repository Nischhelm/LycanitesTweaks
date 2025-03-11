package lycanitestweaks.handlers.features.equipment;

import bettercombat.mod.event.RLCombatSweepEvent;
import com.lycanitesmobs.core.entity.ExtendedEntity;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

public class ItemEquipmentRLCombatSweepHandler {

    @SubscribeEvent
    public static void onSweepAttack(RLCombatSweepEvent event){
        if(event.getItemStack().getItem() instanceof ItemEquipment){
            ItemEquipment lycanitesEquipment = (ItemEquipment)event.getItemStack().getItem();
            ExtendedEntity extendedEntity = ExtendedEntity.getForEntity(event.getEntityPlayer());
            if (extendedEntity == null) return;
            int currentCooldown = extendedEntity.getProjectileCooldown(1, "equipment_melee");
            boolean attackOnCooldown = currentCooldown > 0;

            if (!event.getEntityPlayer().isSneaking() && !attackOnCooldown){
                event.setDoSweep(true);
                event.setSweepModifier(Math.max(1.0F, event.getSweepModifier()));
                LycanitesTweaks.LOGGER.log(Level.INFO, "O: " + event.getSweepingAABB() + " L: " + lycanitesEquipment.getDamageRange(event.getItemStack()));
                event.setSweepingAABB(event.getSweepingAABB().grow(lycanitesEquipment.getDamageRange(event.getItemStack()) - 1));
//                event.setSweepingAABB(event.getEntityPlayer().getEntityBoundingBox().grow(lycanitesEquipment.getDamageRange(event.getItemStack()) + 1.0D));
            }
        }
    }
}
