package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.entity.projectile.EntityHellfireBarrier;
import com.lycanitesmobs.core.entity.projectile.EntityHellfireWall;
import com.lycanitesmobs.core.entity.projectile.EntityHellfireWave;
import com.lycanitesmobs.core.entity.projectile.EntityShadowfireBarrier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = {
        EntityHellfireBarrier.class,
        EntityHellfireWall.class,
        EntityHellfireWave.class,
        EntityShadowfireBarrier.class
})
public abstract class BossProjectileEntity_ExplosionImmunityMixin extends BaseProjectileEntity {

    public BossProjectileEntity_ExplosionImmunityMixin(World world) {
        super(world);
    }

    @Unique
    @Override
    public boolean isImmuneToExplosions() {
        return true;
    }
}
