package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.creature.EntityAmalgalich;
import com.lycanitesmobs.core.entity.creature.EntityAsmodeus;
import com.lycanitesmobs.core.entity.creature.EntityRahovart;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = {
        EntityAmalgalich.class,
        EntityAsmodeus.class,
        EntityRahovart.class
})
public abstract class BossCreatureEntity_AvoidTargetMixin extends BaseCreatureEntity {

    public BossCreatureEntity_AvoidTargetMixin(World world) {
        super(world);
    }

    // Performance, they never move and have massive hitboxes to spam path navigator
    @Unique
    @Override
    public EntityLivingBase getAvoidTarget() { return null; }
}
