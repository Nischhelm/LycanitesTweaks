package lycanitestweaks.capability;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.pets.PetEntry;
import lycanitestweaks.handlers.config.PlayerMobLevelsConfig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IPlayerMobLevelCapability {

    EntityPlayer getPlayer();
    void setPlayer(EntityPlayer player);

    void sync();

    int getTotalLevelsForCategory(PlayerMobLevelsConfig.BonusCategory category);
    int getTotalLevelsForCategory(PlayerMobLevelsConfig.BonusCategory category, BaseCreatureEntity creature);

    int getTotalEnchantmentLevels();

    int getCurrentLevelBestiary(BaseCreatureEntity creature);
    int getHighestLevelPetActive();
    int getHighestLevelPetSoulbound();
    int getHighestMainHandLevels();
    int getItemStackLevels(ItemStack itemStack);

    void addNewPetLevels(int levels);
    void addPetEntryLevels(PetEntry entry);
    void clearHighestLevelPetActive();
    void removePetEntryLevels(PetEntry entry);
    void setNonMainLevels(ItemStack itemStack, int slotIndex);
    void setMainHandLevels(ItemStack itemStack);
}
