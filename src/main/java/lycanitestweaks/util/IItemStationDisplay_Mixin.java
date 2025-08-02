package lycanitestweaks.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface IItemStationDisplay_Mixin {

    int lycanitesTweaks$getTopDisplay(ItemStack stack);
    int lycanitesTweaks$getBottomDisplay(ItemStack stack);

    int lycanitesTweaks$getTopMaxDisplay(ItemStack stack);
    int lycanitesTweaks$getBottomMaxDisplay(ItemStack stack);
}
