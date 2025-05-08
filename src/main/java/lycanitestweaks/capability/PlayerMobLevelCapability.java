package lycanitestweaks.capability;

import com.lycanitesmobs.core.pets.PetEntry;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.network.PacketHandler;
import lycanitestweaks.network.PacketPlayerMobLevelsStats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.*;

public class PlayerMobLevelCapability implements IPlayerMobLevelCapability {

    private EntityPlayer player;
    public int[] nonMainLevels = new int[5];
    public Queue<Integer> mainHandLevels = new LinkedList<>();
    public static final int MAINHAND_CHECK_SIZE = 8;
    public Map<Integer, Integer> petEntryLevels = new HashMap<>();
    private Object[] petEntryLevelsCopy = null;

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
    public void sync() {
        PacketPlayerMobLevelsStats packet = new PacketPlayerMobLevelsStats(this);
        if(!this.player.world.isRemote) {
            EntityPlayerMP playerMP = (EntityPlayerMP) this.player;
            PacketHandler.instance.sendTo(packet, playerMP);
        }
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
    public int getHighestLevelPet() {
        if(!ForgeConfigHandler.server.pmlConfig.pmlCalcHighestLevelPet || this.petEntryLevels.isEmpty()) return 0;
        if(this.petEntryLevelsCopy == null) {
            this.petEntryLevelsCopy = petEntryLevels.keySet().toArray(new Integer[0]);
            Arrays.sort(petEntryLevelsCopy, Comparator.comparingInt(a -> (int) a).reversed());
        }
        if(ForgeConfigHandler.client.debugLoggerAutomatic)
            LycanitesTweaks.LOGGER.log(Level.INFO, "Highest: {} Level Map: {}", this.petEntryLevelsCopy[0], this.petEntryLevels);
        return (int)this.petEntryLevelsCopy[0];
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
                int enchantabilityLevels = (int)((ench.getMinEnchantability(ench.getMinLevel())) * ((float)enchLvl / ench.getMaxLevel()));

                switch (ench.getRarity()){
                    case COMMON:
                        enchantabilityLevels /= ForgeConfigHandler.server.pmlConfig.enchRarityDivisors[0];
                        break;
                    case UNCOMMON:
                        enchantabilityLevels /= ForgeConfigHandler.server.pmlConfig.enchRarityDivisors[1];
                        break;
                    case RARE:
                        enchantabilityLevels /= ForgeConfigHandler.server.pmlConfig.enchRarityDivisors[2];
                        break;
                    case VERY_RARE:
                        enchantabilityLevels /= ForgeConfigHandler.server.pmlConfig.enchRarityDivisors[3];
                        break;
                }

                levels += enchantabilityLevels;

                if(ForgeConfigHandler.client.debugLoggerTrigger)
                    LycanitesTweaks.LOGGER.log(Level.INFO, "ENCH: {}, LEVELS: {}", ench.getName(), enchantabilityLevels);
            }
        }
        return levels;
    }

    // Used by PetManager and when a new PetEntry instance is made with an existing mob
    @Override
    public void addNewPetLevels(int levels) {
        if(this.petEntryLevels.containsKey(levels)){
            this.petEntryLevels.put(levels, this.petEntryLevels.get(levels) + 1);
        }
        else{
            this.petEntryLevels.put(levels, 1);
            this.petEntryLevelsCopy = null;
        }
        this.sync();
    }

    // For adding from PetManager NBT
    @Override
    public void addPetEntryLevels(PetEntry entry) {
        this.addNewPetLevels(entry.getLevel());
    }

    // Removal from PetManager is accurate
    @Override
    public void removePetEntryLevels(PetEntry entry) {
        int level = entry.getLevel();
        if(this.petEntryLevels.containsKey(level)){
            if(this.petEntryLevels.get(level) > 1)
                this.petEntryLevels.put(level, this.petEntryLevels.get(level) - 1);
            else{
                this.petEntryLevels.remove(level);
                this.petEntryLevelsCopy = null;
            }
            this.sync();
        }
        else{
            if(ForgeConfigHandler.client.debugLoggerTrigger) LycanitesTweaks.LOGGER.log(Level.INFO, "Warning tried to remove when petEntryLevels did not have key: {}", level);
        }
    }

    @Override
    public void setNonMainLevels(ItemStack itemStack, int slotIndex) {
        this.nonMainLevels[slotIndex-1] = getItemStackLevels(itemStack);
        this.sync();
    }

    @Override
    public void setMainHandLevels(ItemStack itemStack){
        this.mainHandLevels.add(getItemStackLevels(itemStack));
        while(this.mainHandLevels.size() > PlayerMobLevelCapability.MAINHAND_CHECK_SIZE) this.mainHandLevels.poll();
        this.sync();
    }
}
