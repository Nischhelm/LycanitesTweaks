package lycanitestweaks.mixin.lycanitesmobsfeatures.vanilladeathloot;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BaseCreatureEntity.class)
public abstract class BaseCreatureEntityVanillaLootTableMixin extends EntityLiving {

    @Shadow(remap = false)
    public CreatureInfo creatureInfo;

    public BaseCreatureEntityVanillaLootTableMixin(World worldIn) {
        super(worldIn);
    }

    @Unique
    @Override
    public ResourceLocation getLootTable() {
        return this.creatureInfo.getResourceLocation();
    }

    @Unique
    @Override
    public void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source){
        super.dropLoot(wasRecentlyHit, lootingModifier, source);
        if(this.getLootTable() != null) {
            this.dropFewItems(wasRecentlyHit, lootingModifier);
            this.dropEquipment(wasRecentlyHit, lootingModifier);
        }
    }
}
