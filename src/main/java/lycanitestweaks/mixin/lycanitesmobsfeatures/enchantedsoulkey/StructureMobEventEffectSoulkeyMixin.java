package lycanitestweaks.mixin.lycanitesmobsfeatures.enchantedsoulkey;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.mobevent.effects.MobEventEffect;
import com.lycanitesmobs.core.mobevent.effects.StructureMobEventEffect;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StructureMobEventEffect.class)
public abstract class StructureMobEventEffectSoulkeyMixin extends MobEventEffect {

    @Unique
    @Override
    public void onSpawn(EntityLiving entity, World world, EntityPlayer player, BlockPos pos, int level, int ticks) {
        super.onSpawn(entity, world, player, pos, level, ticks);
        if(entity instanceof BaseCreatureEntity) {
            ((BaseCreatureEntity) entity).addLevel(level);
        }
    }
}
