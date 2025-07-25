package lycanitestweaks.mixin.lycanitesmobspatches.core;

import com.lycanitesmobs.client.AssetManager;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.entity.RapidFireProjectileEntity;
import com.lycanitesmobs.core.info.projectile.ProjectileInfo;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(RapidFireProjectileEntity.class)
public abstract class RapidFireProjectileEntity_SoundMixin extends BaseProjectileEntity {

    @Shadow(remap = false)
    private ProjectileInfo projectileInfo;

    public RapidFireProjectileEntity_SoundMixin(World world) {
        super(world);
    }

    // Fix sounds being mapped to the default "projectile"
    @Unique
    @Override
    public SoundEvent getLaunchSound() {
        return AssetManager.getSound(this.projectileInfo.getName());
    }

    @Unique
    @Override
    public SoundEvent getImpactSound() {
        return AssetManager.getSound(this.projectileInfo.getName() + "_impact");
    }
}
