package lycanitestweaks.item;

import com.lycanitesmobs.client.localisation.LanguageManager;
import com.lycanitesmobs.core.info.AltarInfo;
import com.lycanitesmobs.core.item.ChargeItem;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public class ItemEnchantedSoulkey extends Item {

    // Hybrid of EquipmentPart and Soulkey
    // Don't even need to extend Soulkey or ItemBase

    public String itemName;
    public int variant;

    public ItemEnchantedSoulkey(String itemName, int variant) {
        super();
        this.itemName = itemName;
        this.variant = variant;
        this.setMaxStackSize(1);

        this.setRegistryName(LycanitesTweaks.MODID, this.itemName);
        this.setTranslationKey(this.itemName);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack stack){
        return true;
    }

    /** Gets or creates an NBT Compound for the provided itemstack. **/
    public NBTTagCompound getTagCompound(ItemStack itemStack) {
        if(itemStack.hasTagCompound()) {
            return itemStack.getTagCompound();
        }
        return new NBTTagCompound();
    }

    /**
     * Determines the Equipment Sharpness repair amount of the provided itemstack. Stack size is not taken into account.
     * @param itemStack The itemstack to check the item and nbt data of.
     * @return The amount of Sharpness the provided itemstack restores.
     */
    public static int getSoulkeySharpnessRepair(ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            Item item = itemStack.getItem();
            ResourceLocation checkId = item.getRegistryName();

            if(new ResourceLocation("minecraft:diamond_block").equals(checkId)
                    || new ResourceLocation("minecraft:emerald_block").equals(checkId)){
                return 1;
            }
        }
        return 0;
    }

    /**
     * Determines the Equipment Mana repair amount of the provided itemstack. Stack size is not taken into account.
     * @param itemStack The itemstack to check the item and nbt data of.
     * @return The amount of Mana the provided itemstack restores.
     */
    public static int getSoulkeyManaRepair(ItemStack itemStack) {
        return (itemStack.getItem() == Items.NETHER_STAR) ? 1 : 0;
    }

    @Override
    public void addInformation(ItemStack itemStack, World world, List<String> tooltip, ITooltipFlag tooltipFlag) {
        super.addInformation(itemStack, world, tooltip, tooltipFlag);

//        tooltip.add(LanguageManager.translate(""));
        tooltip.add("Equipment Station");
        tooltip.add("Level: " + this.getLevel(itemStack) + "/" + this.getMaxLevel(itemStack));
        tooltip.add("Gem Power: " + this.getSharpness(itemStack) + "/" + ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages);
        tooltip.add("Mana: " + this.getMana(itemStack) + "/" + ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages);
    }

    /** Returns the Equipment Part level for the provided ItemStack. **/
    public int getMaxLevel(ItemStack itemStack) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        int level = 100;
        if(nbt.hasKey("soulkeyMaxLevel")) {
            level = nbt.getInteger("soulkeyMaxLevel");
        }
        return level;
    }

    /** Sets the level of the provided Equipment Item Stack. **/
    public void setMaxLevel(ItemStack itemStack, int level) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        nbt.setInteger("soulkeyMaxLevel", Math.max(Math.min(level, ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxLevel), 0));
        itemStack.setTagCompound(nbt);
    }

    /** Returns the Equipment Part level for the provided ItemStack. **/
    public int getLevel(ItemStack itemStack) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        int level = 1;
        if(nbt.hasKey("soulkeyLevel")) {
            level = nbt.getInteger("soulkeyLevel");
        }
        return level;
    }

    /** Sets the level of the provided Equipment Item Stack. **/
    public void setLevel(ItemStack itemStack, int level) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        nbt.setInteger("soulkeyLevel", Math.max(Math.min(level, this.getMaxLevel(itemStack)), 0));
        itemStack.setTagCompound(nbt);
    }

    /** Increases the level of the provided Equipment Item Stack. This will also level up the part if the level is enough. **/
    public boolean addLevel(ItemStack itemStack, int level) {
        int currentlevel = this.getLevel(itemStack);
        if(currentlevel >= this.getLevel(itemStack)) {
            return false;
        }
        this.setLevel(itemStack, this.getLevel(itemStack) + level);
        return true;
    }

    /** Decreases the level of the provided Equipment Item Stack. This will also level up the part if the level is enough. **/
    public boolean removelevel(ItemStack itemStack, int level) {
        int currentlevel = this.getLevel(itemStack);
        if(currentlevel <= 0) {
            return false;
        }
        this.setLevel(itemStack, this.getLevel(itemStack) - level);
        return true;
    }

    /** Sets the experience of the provided Equipment Item Stack. **/
    public void setExperience(ItemStack itemStack, int experience) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        nbt.setInteger("soulkeyExperience", experience);
        itemStack.setTagCompound(nbt);
    }

    /** Increases the experience of the provided Equipment Item Stack. This will also level up the part if the experience is enough. **/
    public void addExperience(ItemStack itemStack, int experience) {
        int currentLevel = this.getLevel(itemStack);
        if(currentLevel >= this.getMaxLevel(itemStack)) {
            this.setExperience(itemStack, 0);
        }
        int increasedExperience = this.getExperience(itemStack) + experience;
        int nextLevelExperience = this.getExperienceForNextLevel(itemStack);
        if(increasedExperience >= nextLevelExperience) {
            increasedExperience = increasedExperience - nextLevelExperience;
            this.setLevel(itemStack, currentLevel + 1);
        }
        this.setExperience(itemStack, increasedExperience);
    }

    /** Returns the Equipment Part Experience for the provided ItemStack. **/
    public int getExperience(ItemStack itemStack) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        int experience = 0;
        if(nbt.hasKey("soulkeyExperience")) {
            experience = nbt.getInteger("soulkeyExperience");
        }
        return experience;
    }

    /**
     * Determines how much experience the part needs in order to level up.
     * @return Experience required for a level up.
     */
    public int getExperienceForNextLevel(ItemStack itemStack) {
        return ForgeConfigHandler.server.itemConfig.enchantedSoulkeyBaseLevelupExperience
                + Math.round(ForgeConfigHandler.server.itemConfig.enchantedSoulkeyBaseLevelupExperience
                    * (Math.min(ForgeConfigHandler.server.itemConfig.enchantedSoulkeyNextLevelFinalScale,
                        this.getLevel(itemStack)) - 1) * 0.25F);
    }

    /**
     * Determines if the provided itemstack can be consumed to add experience this part.
     * @param itemStack The possible leveling itemstack.
     * @return True if this part should consume the itemstack and gain experience.
     */
    public boolean isLevelingChargeItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ChargeItem;
    }

    /**
     * Determines how much experience the provided charge itemstack can grant this part.
     * @param itemStack The possible leveling itemstack.
     * @return The amount of experience to gain.
     */
    public int getExperienceFromChargeItem(ItemStack itemStack) {
        return (this.isLevelingChargeItem(itemStack)) ? ChargeItem.CHARGE_EXPERIENCE : 0;
    }

    /** Returns the Equipment Part Sharpness for the provided ItemStack. **/
    public int getSharpness(ItemStack itemStack) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        int sharpness = 0;
        if(nbt.hasKey("soulkeySharpness")) {
            sharpness = nbt.getInteger("soulkeySharpness");
        }
        return sharpness;
    }

    /** Sets the sharpness of the provided Equipment Item Stack. **/
    public void setSharpness(ItemStack itemStack, int sharpness) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        nbt.setInteger("soulkeySharpness", Math.max(Math.min(sharpness, ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages), 0));
        itemStack.setTagCompound(nbt);
    }

    /** Increases the sharpness of the provided Equipment Item Stack. This will also level up the part if the sharpness is enough. **/
    public boolean addSharpness(ItemStack itemStack, int sharpness) {
        int currentSharpness = this.getSharpness(itemStack);
        if(currentSharpness >= ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages) {
            return false;
        }
        this.setSharpness(itemStack, this.getSharpness(itemStack) + sharpness);
        return true;
    }

    /** Decreases the sharpness of the provided Equipment Item Stack. This will also level up the part if the sharpness is enough. **/
    public boolean removeSharpness(ItemStack itemStack, int sharpness) {
        int currentSharpness = this.getSharpness(itemStack);
        if(currentSharpness <= 0) {
            return false;
        }
        this.setSharpness(itemStack, this.getSharpness(itemStack) - sharpness);
        return true;
    }

    /** Returns the Equipment Part Mana for the provided ItemStack. **/
    public int getMana(ItemStack itemStack) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        int mana = 0;
        if(nbt.hasKey("soulkeyMana")) {
            mana = nbt.getInteger("soulkeyMana");
        }
        return mana;
    }

    /** Sets the mana of the provided Equipment Item Stack. **/
    public void setMana(ItemStack itemStack, int mana) {
        NBTTagCompound nbt = this.getTagCompound(itemStack);
        nbt.setInteger("soulkeyMana", Math.max(Math.min(mana, ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages), 0));
        itemStack.setTagCompound(nbt);
    }

    /** Increases the mana of the provided Equipment Item Stack. This will also level up the part if the mana is enough. **/
    public boolean addMana(ItemStack itemStack, int mana) {
        int currentMana = this.getMana(itemStack);
        if(currentMana >= ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages) {
            return false;
        }
        this.setMana(itemStack, this.getMana(itemStack) + mana);
        return true;
    }

    /** Decreases the mana of the provided Equipment Item Stack. This will also level up the part if the mana is enough. **/
    public boolean removeMana(ItemStack itemStack, int mana) {
        int currentMana = this.getMana(itemStack);
        if(currentMana <= 0) {
            return false;
        }
        this.setMana(itemStack, this.getMana(itemStack) - mana);
        return true;
    }

    public boolean isCharged(ItemStack itemStack){
        int charges = (this.variant == 0) ? 0 : 1;
        return this.getMana(itemStack) > 0 && this.getSharpness(itemStack) > charges;
    }

    public void reduceCharge(ItemStack itemStack){
        int charges = (this.variant == 0) ? 1 : 2;
        this.removeMana(itemStack, 1);
        this.removeSharpness(itemStack, charges);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);

        if(player.capabilities.isCreativeMode) {
            this.setLevel(itemStack, this.getMaxLevel(itemStack));
            this.setMaxLevel(itemStack, ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxLevel);
            this.setMana(itemStack, ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages);
            this.setSharpness(itemStack, ForgeConfigHandler.server.itemConfig.enchantedSoulkeyMaxUsages);
        }


        if(!this.isCharged(itemStack) && !player.getEntityWorld().isRemote){
            String message = LanguageManager.translate("enchantedkey.use.nocharge");
            player.sendMessage(new TextComponentString(message));
            return EnumActionResult.FAIL;
        }

        if (!AltarInfo.checkAltarsEnabled() && !player.getEntityWorld().isRemote) {
            String message = LanguageManager.translate("message.soulkey.disabled");
            player.sendMessage(new TextComponentString(message));
            return EnumActionResult.FAIL;
        }

        // Get Possible Altars:
        List<AltarInfo> possibleAltars = new ArrayList<>();
        if (AltarInfo.altars.isEmpty())
            LycanitesTweaks.LOGGER.log(Level.WARN, "No altars have been registered, Soulkeys will not work at all.");

        for (AltarInfo altarInfo : AltarInfo.altars.values()) {
            if (altarInfo.checkBlockEvent(player, world, pos) && altarInfo.quickCheck(player, world, pos)) {
                possibleAltars.add(altarInfo);
            }
        }
        if (possibleAltars.isEmpty()) {
            String message = LanguageManager.translate("message.soulkey.none");
            player.sendMessage(new TextComponentString(message));
            return EnumActionResult.FAIL;
        }

        // Activate First Valid Altar:
        for (AltarInfo altarInfo : possibleAltars) {
            if (altarInfo.fullCheck(player, world, pos)) {

                // Valid Altar:
                if (!player.getEntityWorld().isRemote) {
                    if (!altarInfo.activate(player, world, pos, this.variant)) {
                        String message = LanguageManager.translate("message.soulkey.badlocation");
                        player.sendMessage(new TextComponentString(message));
                        return EnumActionResult.FAIL;
                    }
                    if (!player.capabilities.isCreativeMode) {
                        this.reduceCharge(itemStack);
                    }
                    String message = LanguageManager.translate("message.soulkey.active");
                    player.sendMessage(new TextComponentString(message));
                }
                return EnumActionResult.SUCCESS;
            }
        }

        if (!player.capabilities.isCreativeMode) {
            String message = LanguageManager.translate("message.soulkey.invalid");
            player.sendMessage(new TextComponentString(message));
        }

        return EnumActionResult.FAIL;
    }
}
