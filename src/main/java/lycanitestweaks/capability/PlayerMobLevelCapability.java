package lycanitestweaks.capability;

import lycanitestweaks.LycanitesTweaks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;
import java.util.*;

public class PlayerMobLevelCapability {

    private EntityPlayer player;
    private int[] nonMainLevels = new int[5];
    private Queue<Integer> mainHandLevels = new LinkedList<>();
    private final int MAINHAND_CHECK_SIZE = 8;

    PlayerMobLevelCapability(){}

    PlayerMobLevelCapability(@Nonnull EntityPlayer player){
        this.player = player;
        Arrays.fill(nonMainLevels, 0);
        mainHandLevels.add(0);
    }

    public int getTotalLevels(){
        int total = 0;

        for(int lvl : nonMainLevels){
            LycanitesTweaks.LOGGER.log(Level.INFO, "SLOT LVL: " + lvl);
            total += lvl;
        }
        total += getHighestMainHandLevels();

        return total;
    }

    public int getHighestMainHandLevels(){
        Object[] mainHandLevelsCopy = mainHandLevels.toArray();
        Arrays.sort(mainHandLevelsCopy, Comparator.comparingInt(a -> (int)a).reversed());
        LycanitesTweaks.LOGGER.log(Level.INFO, "MAINHAND LVL: " + (int)mainHandLevelsCopy[0]);
        return (int)mainHandLevelsCopy[0];
    }

    public int getItemStackLevels(ItemStack itemStack){
        int levels = 0;
        NBTTagList enchantments = itemStack.getEnchantmentTagList();
        if(enchantments.isEmpty()) return 0;
        for(int i = 0; i < enchantments.tagCount(); i++){
            Enchantment ench = Enchantment.getEnchantmentByID(enchantments.getCompoundTagAt(i).getShort("id"));
            if(ench != null) {
                int enchLvl = enchantments.getCompoundTagAt(i).getShort("lvl");
                levels += (int)((ench.getMinEnchantability(ench.getMinLevel())) * ((float)enchLvl / ench.getMaxLevel()));
//                levels += enchLvl * (ench.getMaxEnchantability(ench.getMaxLevel()) - ench.getMinEnchantability(ench.getMaxLevel())) / ench.getMaxLevel();
//                levels += (ench.getMaxEnchantability(enchLvl) / (enchLvl * ench.getMaxLevel()));
                LycanitesTweaks.LOGGER.log(Level.INFO, ench.getName() + " : " + ench.getMinEnchantability(ench.getMinLevel()));
            }
        }
        LycanitesTweaks.LOGGER.log(Level.INFO, "LEVELS: " + levels);
        return levels;
    }

    public void setNonMainLevels(ItemStack itemStack, int slotIndex) {
//        LycanitesTweaks.LOGGER.log(Level.INFO, "SLOT: " + (slotIndex-1) + " LEVELS: " + getItemStackLevels(itemStack));
        nonMainLevels[slotIndex-1] = getItemStackLevels(itemStack);
//        LycanitesTweaks.LOGGER.log(Level.INFO, "SLOT: " + (slotIndex-1) + "LEVELS: " + nonMainLevels[slotIndex-1]);
    }

    public void setMainHandLevels(ItemStack itemStack){
        mainHandLevels.add(getItemStackLevels(itemStack));
        if(mainHandLevels.size() > MAINHAND_CHECK_SIZE) mainHandLevels.poll();
        Arrays.sort(mainHandLevels.toArray(), Comparator.comparingInt(a -> (int)a).reversed());
//        LycanitesTweaks.LOGGER.log(Level.INFO, "MAIN LEVELS: " + getItemStackLevels(itemStack));
    }
}
