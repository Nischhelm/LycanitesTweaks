package lycanitestweaks.util;

import net.minecraft.item.ItemStack;

public interface IItemStationDisplay_Mixin {

    int lycanitesTweaks$getTopDisplay(ItemStack stack);
    int lycanitesTweaks$getBottomDisplay(ItemStack stack);

    int lycanitesTweaks$getTopMaxDisplay(ItemStack stack);
    int lycanitesTweaks$getBottomMaxDisplay(ItemStack stack);
}
