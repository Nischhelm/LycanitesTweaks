package lycanitestweaks.handlers.config.major;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class ItemTweaksConfig {

    @Config.Comment("Make offhand crafted equipment RMB ability require player to be sneaking")
    @Config.Name("Crafted Equipment Offhand RMB Needs Sneak")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureequipmentrmbneedssneak.json")
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
            "lycanitesmobs:bleed",
            "defiledlands:bleeding",
            "srparasites:bleed"
    };

    @Config.Comment("Save and use NBT stored Element Level Map to spawn higher level minions")
    @Config.Name("Summon Staff Level Map")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstafflevelmap.json")
    public boolean summonStaffLevelMap = true;

    @Config.Comment("Summon Staffs can use the Equipment Infuser in order to gain experience")
    @Config.Name("Summon Staff Equipment Infuser")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstaffequipmenttiles.json")
    public boolean summonStaffLevelMapEquipmentTiles = true;

    @Config.Comment("Base EXP Required to level up, scales with level, Lycanites Charge EXP is 50")
    @Config.Name("Summon Staff - Base Levelup Experience")
    public int summonStaffBaseLevelupExperience = 500;

    @Config.Comment("The first infused Charge is bound to the summon staff and limits the possible elements to use.\n" +
            "Else every charge and every element can be leveled up on a single staff.")
    @Config.Name("Summon Staff - Elements Limited By Charge")
    public boolean summonStaffElementsByCharge = true;

}
