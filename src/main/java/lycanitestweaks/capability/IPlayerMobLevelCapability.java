package lycanitestweaks.capability;

import com.lycanitesmobs.core.pets.PetEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IPlayerMobLevelCapability {

    EntityPlayer getPlayer();
    void setPlayer(EntityPlayer player);

    int getTotalLevelsWithDegree(double modifier);
    int getTotalLevels();

    int getHighestMainHandLevels();
    int getItemStackLevels(ItemStack itemStack);
    int getHighestLevelPet();

    void setNonMainLevels(ItemStack itemStack, int slotIndex);
    void setMainHandLevels(ItemStack itemStack);
    void addPetEntryLevels(PetEntry entry);
    void removePetEntryLevels(PetEntry entry);
}
