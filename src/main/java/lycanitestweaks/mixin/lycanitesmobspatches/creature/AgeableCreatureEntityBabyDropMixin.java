package lycanitestweaks.mixin.lycanitesmobspatches.creature;

import com.lycanitesmobs.core.entity.AgeableCreatureEntity;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.CreatureManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AgeableCreatureEntity.class)
public abstract class AgeableCreatureEntityBabyDropMixin extends BaseCreatureEntity {

    @Shadow public abstract boolean isChild();

    public AgeableCreatureEntityBabyDropMixin(World world) {
        super(world);
    }

    @Unique
    @Override
    protected boolean canDropLoot(){
        if(CreatureManager.getInstance().creatureGroups.get("animal").hasEntity(this)) return !this.isChild();
        return true;
    }
}
