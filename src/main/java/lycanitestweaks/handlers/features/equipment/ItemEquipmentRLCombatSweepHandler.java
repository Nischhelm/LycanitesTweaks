package lycanitestweaks.handlers.features.equipment;

import bettercombat.mod.compat.EnchantCompatHandler;
import bettercombat.mod.event.RLCombatSweepEvent;
import bettercombat.mod.handler.EventHandlers;
import com.lycanitesmobs.core.entity.ExtendedEntity;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import lycanitestweaks.util.Helpers;
import meldexun.reachfix.util.ReachFixUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static bettercombat.mod.util.Helpers.execNullable;

public class ItemEquipmentRLCombatSweepHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLycanitesSweepAttack(RLCombatSweepEvent event){
        if(event.getItemStack().getItem() instanceof ItemEquipment){
            ExtendedEntity extendedEntity = ExtendedEntity.getForEntity(event.getEntityPlayer());
            if (extendedEntity == null) return;
            int currentCooldown = extendedEntity.getProjectileCooldown(1, "equipment_melee");
            boolean attackOnCooldown = currentCooldown > 0;

            if (!event.getEntityPlayer().isSneaking() && !attackOnCooldown){
                doLycanitesSweepAttack(event);
                event.setDoSweep(false);
            }
        }
    }

    // based on https://github.com/fonnymunkey/RLCombat/blob/master/src/main/java/bettercombat/mod/util/Helpers.java#L298
    public static void doLycanitesSweepAttack(RLCombatSweepEvent event){
        EntityPlayer player = event.getEntityPlayer();
        Entity targetEntity = event.getTargetEntity();

        float cooledStr = event.getCooledStrength();
        double reach = ReachFixUtil.getEntityReach(player, event.getOffhand() ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);

        ItemEquipment lycanitesEquipment = (ItemEquipment)event.getItemStack().getItem();
        double sweepAngle = lycanitesEquipment.getDamageSweep(event.getItemStack()) / (double)2.0F;
        if (sweepAngle > 0.0D) {
            float sweepingDamage = 1.0F + (float)lycanitesEquipment.getDamageSweep(event.getItemStack());
            event.setSweepingAABB(event.getSweepingAABB().grow(lycanitesEquipment.getDamageRange(event.getItemStack())));
            AxisAlignedBB sweepingAABB = event.getSweepingAABB();
            DamageSource sweepingDamageSource = event.getSweepingDamageSource();

            for(EntityLivingBase living : player.world.getEntitiesWithinAABB(EntityLivingBase.class, sweepingAABB)) {
                EnchantCompatHandler.attackEntityFromCooledStrength = cooledStr;
                if (living != player && living != targetEntity && !player.isOnSameTeam(living) && player.getDistanceSq(living) < reach * reach) {

                    double targetXDist = living.posX - player.posX;
                    double targetZDist = player.posZ - living.posZ;
                    double targetAngleAbsolute = (double)180.0F + Math.toDegrees(Math.atan2(targetXDist, targetZDist));
                    double targetAngle = Math.abs(targetAngleAbsolute - (double)player.rotationYaw);
                    if (targetAngle > (double)180.0F) {
                        targetAngle = (double)180.0F - (targetAngle - (double)180.0F);
                    }

                    if (!(targetAngle > sweepAngle)) {
                        Helpers.doEquipmentHitEffect(event.getItemStack(), living, player);
                        living.knockBack(player, 0.4F, (double) MathHelper.sin(player.rotationYaw * ((float)Math.PI / 180F)), (double)(-MathHelper.cos(player.rotationYaw * ((float)Math.PI / 180F))));
                        if (event.getOffhand()) {
                            execNullable(living.getCapability(EventHandlers.OFFHAND_HURTRESISTANCE, (EnumFacing)null), (sht) -> sht.attackEntityFromOffhand(living, sweepingDamageSource, sweepingDamage));
                        } else {
                            living.attackEntityFrom(sweepingDamageSource, sweepingDamage);
                        }
                    }
                }

                EnchantCompatHandler.attackEntityFromCooledStrength = 1.0F;
            }

            player.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
            player.spawnSweepParticles();
        }
    }
}
