package lycanitestweaks.util;

import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.block.BlockFireBase;
import com.lycanitesmobs.core.entity.creature.EntityTremor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public abstract class LycanitesMobsWrapper {

    // Should only use EntityLivingBase as param
    public static boolean hasSmitedEffect(Entity living){
        return ((EntityLivingBase)living).isPotionActive(ObjectManager.getEffect("smited"));
    }

    public static boolean isLycanitesFire(IBlockAccess world, BlockPos pos){ return (world.getBlockState(pos).getBlock() instanceof BlockFireBase); }

    public static boolean isTremor(Entity entity) { return entity instanceof EntityTremor; }
}
