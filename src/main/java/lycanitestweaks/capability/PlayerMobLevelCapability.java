package lycanitestweaks.capability;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

import javax.annotation.Nonnull;
import java.util.*;

public class PlayerMobLevelCapability implements IPlayerMobLevelCapability {

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
    public void setNonMainLevels(ItemStack itemStack, int slotIndex) {
        nonMainLevels[slotIndex-1] = getItemStackLevels(itemStack);
    }

    @Override
    public void setMainHandLevels(ItemStack itemStack){
        mainHandLevels.add(getItemStackLevels(itemStack));
        while(mainHandLevels.size() > MAINHAND_CHECK_SIZE) mainHandLevels.poll();
    }
}
