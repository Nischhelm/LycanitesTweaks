package lycanitestweaks.handlers.config.major;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.common.config.Config;

@MixinConfig(name = LycanitesTweaks.MODID)
public class ItemTweaksConfig {

    @Config.Comment("Make offhand crafted equipment RMB ability require player to be sneaking")
    @Config.Name("Crafted Equipment Offhand RMB Needs Sneak")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featureequipmentrmbneedssneak.json")
    public boolean craftedEquipmentOffhandRMBSneak = true;

    @Config.Comment("Allows Lycanites Equipment to be enchanted with all Weapon type enchantments except Sweeping Edge.\n" +
            "Additionally allows Efficiency as the only tool enchantment.\n" +
            "Breakable type enchantments, such as Unbreaking and Mending, are not allowed.")
    @Config.Name("Crafted Equipment Enchantments")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featureequipmentswordenchantments.json")
    public boolean craftedEquipmentEnchantments = true;

    @Config.Comment("Minimum level all parts of equipment must be in order to apply enchantments")
    @Config.Name("Crafted Equipment Enchantments - Minimum Part Level")
    public int craftedEquipmentEnchantmentsMinLevelParts = 3;

    @Config.Comment("If enabled, the Equipment item shows a tooltip describing part level requirements. Else it is hidden and will only reveal enchantability when it is possible.")
    @Config.Name("Crafted Equipment Enchantments - Minimum Part Level Tooltips")
    public boolean craftedEquipmentEnchantmentsMinLevelTooltips = true;

    @Config.Comment("If enabled, the Equipment Forge will prevent Equipment from being disassembled into its individual parts. Else disassembly can be done at the cost of clearing enchantments.")
    @Config.Name("Crafted Equipment Enchantments - Prevent Disassembling")
    public boolean craftedEquipEnchDisassembleLock = true;

    @Config.Comment("Lycanites Equipment will not be able receive any enchantments in this list (of Resource Locations).\n" +
            "\tFormat: [namespace: path]")
    @Config.Name("Crafted Equipment Enchantments Blacklist")
    public String[] blacklistedCraftedEquipmentEnchants = {

    };

    @Config.Comment("Enable customizable effect list and handling for the cleansed/immunization effect")
    @Config.Name("Customizable Curing Item")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featurescustomcureitemlist.json")
    public boolean customItemCureEffectList = true;

    @Config.Comment("List of potion resource locations cleansed will cure")
    @Config.Name("Customizable Curing Item - Cleansing Crystal")
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

    @Config.Comment("Using the Soulgazer on a tamed pet will provide buffs based on its Elemental properties and Creature stats.\n" +
            "Not all pets are capable of providing buffs")
    @Config.Name("Soulgazer Buff From Pet")
    public boolean soulgazerBuffFromPet = true;

    @Config.Comment("If true, then players must be sneaking in order to obtain the buff")
    @Config.Name("Soulgazer Buff From Pet - Requires Sneak")
    public boolean soulgazerBuffFromPetSneak = true;

    @Config.Comment("Cooldown in ticks, shared with the Creature Study Cooldown")
    @Config.Name("Soulgazer Buff From Pet - Study Cooldown")
    @Config.RangeInt(min = 0)
    public int soulgazerBuffStudyCooldown = 200;

    @Config.Comment("Holding a Soulgazer will prevent debuffs based on active Soulbound pets' elemental properties")
    @Config.Name("Soulgazer Soulbound Debuff Immunities")
    public boolean soulgazerDebuffImmunity = true;

    @Config.Comment("If true, than only the Keybound Pet is checked instead of all active. Requires 'Modify Beastiary Information' to set a Keybound Pet.")
    @Config.Name("Soulgazer Soulbound Debuffs Immunities - Keybound Only")
    public boolean soulgazerDebuffImmunityKeybound = true;

    @Config.Comment("Save and use NBT stored Element Level Map to spawn higher level minions")
    @Config.Name("Summon Staff Level Map")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featuresummonstafflevelmap.json")
    public boolean summonStaffLevelMap = true;

    @Config.Comment("Summon Staffs can use the Equipment Infuser in order to gain experience")
    @Config.Name("Summon Staff Equipment Infuser")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featuresummonstaffequipmenttiles.json")
    public boolean summonStaffLevelMapEquipmentTiles = true;

    @Config.Comment("Base EXP Required to level up, scales with level, Lycanites Charge EXP is 50")
    @Config.Name("Summon Staff - Base Levelup Experience")
    public int summonStaffBaseLevelupExperience = 500;

    @Config.Comment("The first infused Charge is bound to the summon staff and limits the possible elements to use.\n" +
            "Else every charge and every element can be leveled up on a single staff.")
    @Config.Name("Summon Staff - Elements Limited By Charge")
    public boolean summonStaffElementsByCharge = true;

}
