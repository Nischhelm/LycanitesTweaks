package lycanitestweaks.mixin.lycanitesmobsfeatures.dungeonbosscrystals;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import lycanitestweaks.entity.item.EntityEncounterSummonCrystal;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityRandomEncounterCrystalMixin extends EntityLiving {

    @Shadow(remap = false)
    public long updateTick;
    @Shadow(remap = false)
    protected abstract boolean canDespawn();
    @Shadow(remap = false)
    public abstract boolean isMinion();

    public BaseCreatureEntityRandomEncounterCrystalMixin(World worldIn) {
        super(worldIn);
    }

    @Inject(
            method = "onUpdate",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/entity/BaseCreatureEntity;despawnCheck()Z", remap = false),
            remap = true
    )
    public void lycanitesTweaks_lycanitesMobs_onUpdateSpawnEncounterCrystal(CallbackInfo ci){
        if(this.updateTick % ForgeConfigHandler.server.escConfig.encounterCrystalSpawnTickRate == 0L
                && this.idleTime > ForgeConfigHandler.server.escConfig.encounterCrystalIdleTime
                && this.rand.nextInt(ForgeConfigHandler.server.escConfig.encounterCrystalSpawnChance) == 0
                && this.canDespawn()
                && !this.isMinion()){
            if(CreatureManager.getInstance().creatureGroups.get("animal").hasEntity(this)) return;

            Entity entity = this.world.getClosestPlayerToEntity(this, -1.0D);
            if (entity != null) {
                double d0 = entity.posX - this.posX;
                double d1 = entity.posY - this.posY;
                double d2 = entity.posZ - this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if(d3 > 1024.0D) {
                    if(EntityEncounterSummonCrystal.trySpawnEncounterCrystal(this.world, this)) this.setDead();
                }
            }
        }
    }
}
