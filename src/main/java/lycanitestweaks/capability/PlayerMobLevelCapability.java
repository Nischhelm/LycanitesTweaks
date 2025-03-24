package lycanitestweaks.capability;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import java.util.*;

public class PlayerMobLevelCapability {

    private EntityPlayer player;
    private final int[] nonMainLevels = new int[5];
    private final Queue<Integer> mainHandLevels = new LinkedList<>();
    private final int MAINHAND_CHECK_SIZE = 8;

    PlayerMobLevelCapability(){}

    PlayerMobLevelCapability(@Nonnull EntityPlayer player){
        this.player = player;
        Arrays.fill(nonMainLevels, 0);
        mainHandLevels.add(0);
    }

    public static PlayerMobLevelCapability getForPlayer(EntityPlayer player) {
        if (player == null) {
            return null;
        }
        PlayerMobLevelCapability pml = player.getCapability(PlayerMobLevelCapabilityHandler.PLAYER_MOB_LEVEL, null);
        if (pml != null && pml.player != player) {
            pml.player = player;
        }
        return pml;
    }

    public int getTotalLevelsWithDegree(double modifier){
        return (int)(getTotalLevels() * modifier);
    }

    public int getTotalLevels(){
        int total = 0;

        for(int lvl : nonMainLevels){
            total += lvl;
        }
        total += getHighestMainHandLevels();

        return total;
    }

    public int getHighestMainHandLevels(){
        Object[] mainHandLevelsCopy = mainHandLevels.toArray();
        Arrays.sort(mainHandLevelsCopy, Comparator.comparingInt(a -> (int)a).reversed());
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
            }
        }
        return levels;
    }

    public void setNonMainLevels(ItemStack itemStack, int slotIndex) {
        nonMainLevels[slotIndex-1] = getItemStackLevels(itemStack);
    }

    public void setMainHandLevels(ItemStack itemStack){
        mainHandLevels.add(getItemStackLevels(itemStack));
        while(mainHandLevels.size() > MAINHAND_CHECK_SIZE) mainHandLevels.poll();
    }
}
