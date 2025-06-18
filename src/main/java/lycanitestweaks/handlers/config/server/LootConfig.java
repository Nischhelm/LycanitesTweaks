package lycanitestweaks.handlers.config.server;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class LootConfig {
    @Config.Comment("Lycanites Creatures can use JSON loot tables alongside Lycanites Mobs drop list - required for the added loot tables here")
    @Config.Name("Add Feature")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurecreaturevanillaloottables.json")
    public boolean vanillaBaseCreatureLootTable = true;

    @Config.Comment("Minimum Creature Level for the lycanite mob to drop Random Charges")
    @Config.Name("Random Charge Loot Minimum Mob Level")
    @Config.RequiresMcRestart
    public int randomChargeMinimumMobLevel = 5;

    @Config.Comment("How many charges to drop at minimum")
    @Config.Name("Random Charge Loot Minimum Count")
    @Config.RequiresMcRestart
    public int randomChargeScaledCountMinimum = 0;

    @Config.Comment("How many charges to drop at maximum")
    @Config.Name("Random Charge Loot Maximum Count")
    @Config.RequiresMcRestart
    public int randomChargeScaledCountMaximum = 4;

    @Config.Comment("How many charges per level, default is 0.1 so average one charge for every 10 levels")
    @Config.Name("Random Charge Level Scale")
    @Config.RequiresMcRestart
    public float randomChargeLevelScale = 0.1F;

    @Config.Comment("Limit the number of items to drop, set to 0 to have no limit")
    @Config.Name("Random Charge Loot Drop Limit")
    @Config.RequiresMcRestart
    public int randomChargeDropLimit = 0;

    @Config.Comment("Set to 1 to use the standard vanilla looting bonus, set to 0 to disable")
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

    @Config.Comment("Register Loot Tables for creatures dropping random charges of their element (This LootTable is dynamic)")
    @Config.Name("Register Random Charges Loot Tables")
    @Config.RequiresMcRestart
    public boolean registerRandomChargesLootTable = true;

    @Config.Comment("Register Loot Tables for any creature tagged as SpawnedAsBoss (ex Dungeon/Altar)")
    @Config.Name("Register SpawnedAsBoss With Levels Loot Tables")
    @Config.RequiresMcRestart
    public boolean registerSpawnedAsBossWithLevelsLootTables = true;

    //TODO: nischcomment are you sure you want to make this configable?
    // if the default loot tables are loaded and this is false, you will get crashes (i assume) or at least error logs
    // also, why not separate the two conditions and two functions into four configs then? i dont get it
    // i would just not add the configs, less is more.
    // dont see a problem in registering loot funcs/conds even if they are not used
    @Config.Comment("Register Has Mob Levels Loot Condition")
    @Config.Name("Register HasMobLevels Loot Condition")
    @Config.RequiresMcRestart
    public boolean registerPMLLootCondition = true;

    @Config.Comment("Register Scale With Mob Levels Loot Function")
    @Config.Name("Register ScaleWithMobLevels Loot Function")
    @Config.RequiresMcRestart
    public boolean registerPMLLootFunction = true;
}
