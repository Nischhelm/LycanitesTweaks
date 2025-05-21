package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;

public class ItemTweaksConfig {

    private static HashSet<ResourceLocation> cleansedCureEffects = null;
    private static HashSet<ResourceLocation> immunizationCureEffects = null;

    @Config.Comment("Make offhand crafted equipment RMB ability require player to be sneaking")
    @Config.Name("Crafted Equipment Offhand RMB Needs Sneak")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentrmbneedssneak.json")
    public boolean craftedEquipmentOffhandRMBSneak = true;

    @Config.Comment("Allows Sword Enchantments and Efficiency")
    @Config.Name("Crafted Equipment Enchantments")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureequipmentswordenchantments.json")
    public boolean craftedEquipmentEnchantments = true;

    @Config.Comment("Minimum level all parts of equipment must be in order to apply enchantments")
    @Config.Name("Crafted Equipment Enchantments - Minimum Part Level")
    public int equipmentMinLevelEnchantable = 3;

    @Config.Comment("Enable customizable effect list and handling for the cleansed/immunization effect")
    @Config.Name("Customizable Curing Item")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurescustomcureitemlist.json")
    public boolean customItemCureEffectList = true;

    @Config.Comment("List of potion resource locations cleansed will cure")
    @Config.Name("Customizable Curing Item - Cleaning Crystal")
    public String[] cleansedEffectsToCure = {
            "minecraft:wither",
            "minecraft:unluck",
            "lycanitesmobs:fear",
            "lycanitesmobs:insomnia",
            "lycanitesmobs:decay",
            "srparasites:fear",
            "mod_lavacow:fear"
    };

    @Config.Comment("List of potion resource locations immunization will cure")
    @Config.Name("Customizable Curing Item - Immunizer")
    public String[] immunizationEffectsToCure = {
            "minecraft:poison",
            "minecraft:hunger",
            "minecraft:weakness",
            "minecraft:nausea",
            "lycanitesmobs:paralysis",
            "lycanitesmobs:bleed"
    };

    @Config.Comment("Save and use NBT stored Element Level Map to spawn higher level minions")
    @Config.Name("Summon Staff Level Map")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstafflevelmap.json")
    public boolean summonStaffLevelMap = true;

    @Config.Comment("Base EXP Required to level up, scales with level, Lycanites Charge EXP is 50")
    @Config.Name("Summon Staff Level Map - Base Levelup Experience")
    public int summonStaffBaseLevelupExperience = 500;

    @Config.Comment("The first infused Charge is bound to the summon staff and limits the possible elements to use")
    @Config.Name("Summon Staff Level Map - Elements Limited By Charge")
    public boolean summonStaffElementsByCharge = true;

    @Config.Comment("Summon Staffs can use the Equipment Infuser in order to gain experience")
    @Config.Name("Summon Staff Level Map Equipment Tiles")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstaffequipmenttiles.json")
    public boolean summonStaffLevelMapEquipmentTiles = true;

    public static HashSet<ResourceLocation> getCleansedCureEffects(){
        if(ItemTweaksConfig.cleansedCureEffects == null){
            HashSet<ResourceLocation> list = new HashSet<>();
            for(String string: ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.cleansedEffectsToCure){
                list.add(new ResourceLocation(string));
            }
            ItemTweaksConfig.cleansedCureEffects = list;
        }
        return ItemTweaksConfig.cleansedCureEffects;
    }

    public static HashSet<ResourceLocation> getImmunizationCureEffects(){
        if(ItemTweaksConfig.immunizationCureEffects == null){
            HashSet<ResourceLocation> list = new HashSet<>();
            for(String string: ForgeConfigHandler.majorFeaturesConfig.itemTweaksConfig.immunizationEffectsToCure){
                list.add(new ResourceLocation(string));
            }
            ItemTweaksConfig.immunizationCureEffects = list;
        }
        return ItemTweaksConfig.immunizationCureEffects;
    }

    @Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
    private static class EventHandler{

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(LycanitesTweaks.MODID)) {
                ItemTweaksConfig.cleansedCureEffects = null;
                ItemTweaksConfig.immunizationCureEffects = null;
                ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
