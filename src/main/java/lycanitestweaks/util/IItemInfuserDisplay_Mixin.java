package lycanitestweaks.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IItemInfuserDisplay_Mixin {

    int lycanitesTweaks$getExperienceDisplay(ItemStack stack);
    int lycanitesTweaks$getNextLevelDisplay(ItemStack stack);
}
