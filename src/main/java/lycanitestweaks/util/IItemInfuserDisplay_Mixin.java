package lycanitestweaks.util;

import net.minecraft.item.ItemStack;

public interface IItemInfuserDisplay_Mixin {

    int lycanitesTweaks$getExperienceDisplay(ItemStack stack);
    int lycanitesTweaks$getNextLevelDisplay(ItemStack stack);
}
