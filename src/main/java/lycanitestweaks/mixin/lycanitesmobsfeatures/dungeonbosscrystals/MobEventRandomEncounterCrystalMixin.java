package lycanitestweaks.mixin.lycanitesmobsfeatures.dungeonbosscrystals;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.mobevent.MobEvent;
import lycanitestweaks.entity.item.EntityEncounterSummonCrystal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEvent.class)
public abstract class MobEventRandomEncounterCrystalMixin {

    @Inject(
            method = "onSpawn",
            at = @At("TAIL"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobEvent_onSpawnEncounterCrystal(EntityLiving entity, World world, EntityPlayer player, BlockPos pos, int level, int ticks, int variant, CallbackInfo ci){
        if(entity instanceof BaseCreatureEntity && !((BaseCreatureEntity) entity).isTemporary) return; // Hopefully skip all Bosses

        if(world.rand.nextInt(ForgeConfigHandler.server.escConfig.encounterCrystalSpawnChance) == 0){
            if(EntityEncounterSummonCrystal.trySpawnEncounterCrystal(world, entity)) entity.setDead();
        }
    }
}
