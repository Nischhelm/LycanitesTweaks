package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class PlayerMobLevelsConfig {

    @Config.Comment("Used to lower bloated Minimum Enchantibility values via Rarity {COMMON, UNCOMMON, RARE, VERY_RARE}")
    @Config.Name("Enchantment Rarity Divisors")
    @Config.RequiresMcRestart
    public int[] enchRarityDivisors = {1, 2, 5, 10};

    @Config.Comment("Alternative to fully disabling, forge caps exist (non null) but always returns 0")
    @Config.Name("Mod Compatibility: No Calculate")
    public boolean playerMobLevelCapabilityNoCalc = false;

    @Config.Comment("Enable Capability to calculate a Mob Level associated to a player")
    @Config.Name("Player Mob Level Capability")
    @Config.RequiresMcRestart
    public boolean playerMobLevelCapability = true;

    @Config.Comment("Require a held main hand Soulgazer in order to summon a boss with additional levels")
    @Config.Name("Player Mob Level Boss Requires Soulgazer")
    public boolean pmlBossRequiresSoulgazer = true;

    @Config.Comment("If Highest Level Pet Entry should try to be calculated, unsorted levels are still stored, false always returns 0")
    @Config.Name("Player Mob Level Calculate Highest Level Pet Entry")
    public boolean pmlCalcHighestLevelPet = true;

    @Config.Comment("Ratio of Player Mob Levels used for Boss Crystals when they spawn something")
    @Config.Name("Player Mob Level Degree Boss Crystal")
    @Config.RangeDouble(min = 0.0)
    public double pmlBossCrystalDegree = 1.0D;

    @Config.Comment("Ratio of Player Mob Levels used when the main bosses finish spawning")
    @Config.Name("Player Mob Level Degree Main Boss")
    @Config.RangeDouble(min = 0.0)
    public double pmlBossMainDegree = 1.0D;

    @Config.Comment("Ratio of Player Mob Levels used for soulbounds in limited dimensions")
    @Config.Name("Player Mob Level Degree Soulbound")
    @Config.RangeDouble(min = 0.0)
    public double pmlSoulboundDegree = 1.0D;

    @Config.Comment("Ratio of Player Mob Levels used for Lycanites Spawners")
    @Config.Name("Player Mob Level Degree Spawner")
    @Config.RangeDouble(min = 0.0)
    public double pmlSpawnerDegree = 1.0D;

    @Config.Comment("Ratio of Player Mob Levels used for summon staff minions")
    @Config.Name("Player Mob Level Degree Summon")
    @Config.RangeDouble(min = 0.0)
    public double pmlSummonDegree = 0.5D;

    @Config.Comment("Dimension IDs where soulbounds are level capped to Player Mob Level")
    @Config.Name("Player Mob Level Dimensions")
    public int[] pmlMinionLimitDimIds = {
            111,
            3
    };

    @Config.Comment("Player Mob Level Dimensions is Whitelist")
    @Config.Name("Player Mob Level Dimensions is Whitelist")
    public boolean pmlMinionLimitDimIdsWhitelist = true;

    @Config.Comment("Whether limited soulbounds can spawn in dimensions blacklisted by vanilla Lycanites")
    @Config.Name("Player Mob Level Dimensions Overrules Blacklist")
    public boolean pmlMinionLimitDimOverruleBlacklist = true;

    @Config.Comment("Whether limited soulbound inventories can not have items put inside")
    @Config.Name("Player Mob Level Dimensions no inventory")
    public boolean pmlMinionLimitDimNoInventory = true;

    @Config.Comment("Whether limited soulbounds can be mounted")
    @Config.Name("Player Mob Level Dimensions not mountable")
    public boolean pmlMinionLimitDimNoMount = true;

    @Config.Comment("Whether limited dimensions prevent soulbound spirit recharge")
    @Config.Name("Player Mob Level Dimensions no spirit recharge")
    public boolean pmlMinionLimitDimNoSpiritRecharge = true;

    @Config.Comment("List of Lycanites Spawner Names to attempt to apply Player Mob Levels (ex World triggers don't have players)")
    @Config.Name("PML Spawner Names")
    public String[] pmlSpawnerNameStrings = {
            "chaos",
            "disruption",
            "sleep"
    };

    @Config.Comment("PML Spawner Names is a blacklist instead of whitelist")
    @Config.Name("PML Spawner Names Blacklist")
    public boolean pmlSpawnerNameStringsIsBlacklist = false;

    @Config.Comment("Creature level to compare to PML highest pet entry level, Requires Mixin 'Tamed Invert Over Leveled Penalty', set to 0 to disable")
    @Config.Name("PML Taming Over Leveled Start")
    public int pmlTamedOverLevelStartLevel = 20;

    @Config.Comment("If treat reputation will be penalized if creature is over leveled. Requires Mixin 'Tamed Invert Over Leveled Penalty', set to 0 to disable")
    @Config.Name("PML Taming Over Leveled Treat Point Penalty")
    public boolean pmlTamedOverLevelTreatPointPenalty = false;

    @Config.Comment("If creature could be tempted, will it remain able to be tempted if over leveled, Requires Mixin 'Tamed Invert Over Leveled Penalty', set to 0 to disable")
    @Config.Name("PML Taming Over Leveled Treat Tempt")
    public boolean pmlTamedOverLevelTreatTempt = false;
}
