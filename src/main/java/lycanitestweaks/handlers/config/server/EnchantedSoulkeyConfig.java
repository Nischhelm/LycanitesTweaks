package lycanitestweaks.handlers.config.server;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class EnchantedSoulkeyConfig {
    @Config.Comment("Mainhand Enchanted Soulkeys Add Levels to Altar Boss")
    @Config.Name("Add Feature: Enchanted Soulkey Altar Mini Bosses")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeyaltarminiboss.json")
    public boolean enchantedSoulkeyAltarMiniBoss = true;

    @Config.Comment("Mainhand Enchanted Soulkeys Add Levels to Altar Boss")
    @Config.Name("Add Feature: Enchanted Soulkey Altar Main Bosses")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeymainboss.json")
    public boolean enchantedSoulkeyAltarMainBoss = true;

    @Config.Comment("Enchanted Soulkeys can be put inside Equipment Infuser to level up and Station to recharge")
    @Config.Name("Add Feature: Enchanted Soulkey Equipment Tiles Entities")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeyequipmenttiles.json")
    public boolean enchantedSoulkeyEquipmentTiles = true;

    @Config.Comment("Base EXP Required to level up, scales with level, Lycanites Charge EXP is 50")
    @Config.Name("Enchanted Soulkey Base Levelup Experience")
    public int enchantedSoulkeyBaseLevelupExperience = 500;

    @Config.Comment("At this level the required exp stops increasing, otherwise scales +25% per level")
    @Config.Name("Enchanted Soulkey Next Level Final Scale")
    public int enchantedSoulkeyNextLevelFinalScale = 16;

    @Config.Comment("Default Maximum Level Creature Enchanted Soulkeys can spawn, can be overriden with NBT")
    @Config.Name("Enchanted Soulkey Max Level")
    public int enchantedSoulkeyDefaultMaxLevel = 100;

    @Config.Comment("Default Usages when NBT is blank, recommended to match crafting recipe materials")
    @Config.Name("Enchanted Soulkey On Craft Usages")
    public int enchantedSoulkeyOnCraftUsages = 8;

    @Config.Comment("Maximum Stored Nether Star Power and Gem Power (Diamond/Emerald Blocks)")
    @Config.Name("Enchanted Soulkey Max Usages")
    public int enchantedSoulkeyMaxUsages = 1000;
}
