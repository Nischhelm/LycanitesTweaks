package lycanitestweaks.handlers.config.server;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.common.config.Config;

@MixinConfig(name = LycanitesTweaks.MODID)
public class LootConfig {

    @Config.Comment("Lycanites Creatures can use JSON loot tables alongside Lycanites Mobs drop list - required for the added loot tables here")
    @Config.Name("0. Vanilla Lootables for Lycanites Mobs")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featurecreaturevanillaloottables.json")
    public boolean vanillaBaseCreatureLootTable = true;

    @Config.Comment("Register Loot Tables for creatures dropping random charges of their element (This LootTable is dynamic)")
    @Config.Name("Register Random Charges Loot Tables")
    @Config.RequiresMcRestart
    public boolean registerRandomChargesLootTable = true;

    @Config.Comment("Minimum Creature Level for the lycanite mob to drop Random Charges")
    @Config.Name("Random Charge Loot Minimum Mob Level")
    @Config.RequiresMcRestart
    public int randomChargeMinimumMobLevel = 0;

    @Config.Comment("How many charges to drop at minimum")
    @Config.Name("Random Charge Loot Minimum Count")
    @Config.RequiresMcRestart
    public int randomChargeScaledCountMinimum = 0;

    @Config.Comment("How many charges to drop at maximum before any bonus drops")
    @Config.Name("Random Charge Loot Maximum Count")
    @Config.RequiresMcRestart
    public int randomChargeScaledCountMaximum = 5;

    @Config.Comment("Level scale to determine the upper bound of bonus drops per mob level, lower bound is always 0.0\n" +
            "1.0 means the upper bound is 100% the mob's level, up to 10 drops for lvl 10.\n" +
            "0.5 is 50% the mob's level, so 15 drops for a lvl 30.\n + " +
            "0.1 is 10% the mob's level, so 5 drops for a lvl 50.")
    @Config.Name("Random Charge Level Scale")
    @Config.RequiresMcRestart
    public float randomChargeLevelScale = 1F;

    @Config.Comment("Limit the number of total items to drop, calculated after level bonus, set to 0 to have no limit")
    @Config.Name("Random Charge Loot Drop Limit")
    @Config.RequiresMcRestart
    public int randomChargeDropLimit = 1728;

    @Config.Comment("How many charges per looting lvl to add on top at max (will roll a random amount between 0 and this number times looting lvl). Set to 0 to disable")
    @Config.Name("Random Charge Looting Bonus")
    @Config.RequiresMcRestart
    public int randomChargeLootingBonus = 1;

    @Config.Comment("Register Loot Tables for Amalgalich, Asmodeus, and Rahovart that are scaled to Mob Levels")
    @Config.Name("Register Boss With Levels Loot Tables")
    @Config.RequiresMcRestart
    public boolean registerBossWithLevelsLootTables = true;

    @Config.Comment("Register Level 100+ Amalgalich, Asmodeus, and Rahovart special Enchanted Soulkey drop")
    @Config.Name("Register Boss Soulkey Loot Tables")
    @Config.RequiresMcRestart
    public boolean registerBossSoulkeyLootTables = true;


    @Config.Comment("Register Loot Tables for any creature tagged as SpawnedAsBoss (ex Dungeon/Modified Altar)\n" +
            "Basic configurable number of randomly enchanted books.")
    @Config.Name("Register SpawnedAsBoss Enchant Randomly Book Loot Tables")
    @Config.RequiresMcRestart
    public boolean registerSpawnedAsBossRandomBookLootTables = true;

    @Config.Comment("How many books should drop from the \"SpawnedAsBoss Enchant Randomly Book\" Loot Table\n" +
            "Consider how applying this tag is configured.\n" +
            "Dungeons always have this tag, altar mini bosses can be configured, and if random boss spawns are enabled.")
    @Config.Name("SpawnedAsBoss Enchant Randomly Book Count")
    @Config.RequiresMcRestart
    public int spawnedAsBossBookRolls = 2;

    @Config.Comment("Register Loot Tables for any creature tagged as SpawnedAsBoss (ex Dungeon/Modified Altar)\n" +
            "One non treasure enchanted book drop where the enchantWithLevels loot func is applied with 100% of the Boss' level.\n" +
            "One treasure enchanted book where enchantWithLevels loot func is applied with 75% of the Boss' level.\n" +
            "One Bottle o' Enchanting per 2 levels.")
    @Config.Name("Register SpawnedAsBoss With Levels Loot Tables")
    @Config.RequiresMcRestart
    public boolean registerSpawnedAsBossWithLevelsLootTables = true;
}
