package lycanitestweaks.capability;

import com.lycanitesmobs.core.pets.PetEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IPlayerMobLevelCapability {

    EntityPlayer getPlayer();
    void setPlayer(EntityPlayer player);

    int getTotalLevelsWithDegree(double modifier);
    int getTotalLevels();

    int getHighestLevelPet();
    int getHighestMainHandLevels();
    int getItemStackLevels(ItemStack itemStack);

    void addNewPetLevels(int levels);
    void addPetEntryLevels(PetEntry entry);
    void removePetEntryLevels(PetEntry entry);
    void setNonMainLevels(ItemStack itemStack, int slotIndex);
    void setMainHandLevels(ItemStack itemStack);
}
