package lycanitestweaks.capability;

import com.lycanitesmobs.core.pets.PetEntry;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.*;

public class PlayerMobLevelCapability implements IPlayerMobLevelCapability {

    private EntityPlayer player;
    private final int[] nonMainLevels = new int[5];
    private final Queue<Integer> mainHandLevels = new LinkedList<>();
    private final int MAINHAND_CHECK_SIZE = 8;
    public Map<Integer, Integer> petEntryLevels = new HashMap<>();
    Object[] petEntryLevelsCopy = null;

    PlayerMobLevelCapability(){}

    PlayerMobLevelCapability(@Nonnull EntityPlayer player){
        this.player = player;
        Arrays.fill(nonMainLevels, 0);
        mainHandLevels.add(0);
    }

    public static IPlayerMobLevelCapability getForPlayer(EntityPlayer player) {
        if (player == null) {
            return null;
        }
        IPlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
        if (pml != null && pml.getPlayer() != player) {
            pml.setPlayer(player);
        }
        return pml;
    }

    @Override
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public int getTotalLevelsWithDegree(double modifier){
        return (int)(getTotalLevels() * modifier);
    }

    @Override
    public int getTotalLevels(){
        if(ForgeConfigHandler.server.pmlConfig.playerMobLevelCapabilityNoCalc) return 0;
        int total = 0;

        for(int lvl : nonMainLevels){
            total += lvl;
        }
        total += getHighestMainHandLevels();

        return total;
    }

    @Override
    public int getHighestMainHandLevels(){
        Object[] mainHandLevelsCopy = mainHandLevels.toArray();
        Arrays.sort(mainHandLevelsCopy, Comparator.comparingInt(a -> (int)a).reversed());
        return (int)mainHandLevelsCopy[0];
    }

    @Override
    public int getItemStackLevels(ItemStack itemStack){
        int levels = 0;
        NBTTagList enchantments = itemStack.getEnchantmentTagList();
        if(enchantments.isEmpty()) return 0;
        for(int i = 0; i < enchantments.tagCount(); i++){
            Enchantment ench = Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getShort("id"));
            if(ench != null) {
                int enchLvl = enchantments.getCompoundTagAt(i).getShort("lvl");
                levels += (int)((ench.getMinEnchantability(ench.getMinLevel())) * ((float)enchLvl / ench.getMaxLevel()));
            }
        }
        return levels;
    }

    @Override
    public int getHighestLevelPet() {
        if(!ForgeConfigHandler.server.pmlConfig.pmlCalcHighestLevelPet) return 1;
        if(this.petEntryLevelsCopy == null) {
            this.petEntryLevelsCopy = petEntryLevels.keySet().toArray(new Integer[0]);
            Arrays.sort(petEntryLevelsCopy, Comparator.comparingInt(a -> (int) a).reversed());
        }
        if(ForgeConfigHandler.client.debugLoggerTrigger)
            LycanitesTweaks.LOGGER.log(Level.INFO, "Highest: {} Level Map: {}", this.petEntryLevelsCopy[0], this.petEntryLevels);
        return (int)this.petEntryLevelsCopy[0];
    }

    @Override
    public void setNonMainLevels(ItemStack itemStack, int slotIndex) {
        nonMainLevels[slotIndex-1] = getItemStackLevels(itemStack);
    }

    @Override
    public void setMainHandLevels(ItemStack itemStack){
        mainHandLevels.add(getItemStackLevels(itemStack));
        while(mainHandLevels.size() > MAINHAND_CHECK_SIZE) mainHandLevels.poll();
    }

    // Adding with PetManager captures the level 1 state
    @Override
    public void addPetEntryLevels(PetEntry entry) {
        this.petEntryLevelsCopy = null;
        if(this.petEntryLevels.containsKey(entry.getLevel())){
            this.petEntryLevels.put(entry.getLevel(), this.petEntryLevels.get(entry.getLevel()) + 1);
        }
        else{
            this.petEntryLevels.put(entry.getLevel(), 1);
            this.petEntryLevelsCopy = null;
        }
    }

    // Removal from PetManager is accurate
    @Override
    public void removePetEntryLevels(PetEntry entry) {
        this.petEntryLevelsCopy = null;
        if(this.petEntryLevels.containsKey(entry.getLevel())){
            if(this.petEntryLevels.get(entry.getLevel()) > 1)
                this.petEntryLevels.put(entry.getLevel(), this.petEntryLevels.get(entry.getLevel()) - 1);
            else{
                this.petEntryLevels.remove(entry.getLevel());
                this.petEntryLevelsCopy = null;
            }
        }
        else{
            if(ForgeConfigHandler.client.debugLoggerTrigger) LycanitesTweaks.LOGGER.log(Level.INFO, "Warning, petEntryLevels did not have key: {}", entry.getLevel());
        }
    }
}
