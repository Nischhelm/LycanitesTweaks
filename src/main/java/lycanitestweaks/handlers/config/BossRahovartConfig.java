package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class BossRahovartConfig {

    @Config.Comment("Projectile to replace targeted 'hellfireball' auto attack")
    @Config.Name("Main Targeted Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileTarget = "sigilwraithskull";

    @Config.Comment("Projectile to replace all players 'hellfireball' auto attack")
    @Config.Name("Main All Players Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileAll = "sigilhellfireball";

    @Config.Comment("Replace the 50hp/sec heal with a 2% Max HP/sec heal")
    @Config.Name("Heal Portion When No Players")
    @Config.RequiresMcRestart
    public boolean healPortionNoPlayers = true;

    @Config.Comment("Tick Maximum Lifespan for Belphs and Behemoths. Set to 0 to disable")
    @Config.Name("Minion Temporary Duration")
    @Config.RangeInt(min = 0)
    public int minionTemporaryDuration = 1200;

    @Config.Comment("Minimum spawn range for only Belphs and Behemoths (Lycanites uses 5)")
    @Config.Name("Minion Spawn Range Minimum")
    @Config.RangeInt(min = 0, max = 35)
    public int minionSpawnRangeMin = 15;

    @Config.Comment("Maximum spawn range for only Belphs and Behemoths (Lycanites uses 5)")
    @Config.Name("Minion Spawn Range Maximum")
    @Config.RangeInt(min = 0, max = 35)
    public int minionSpawnRangeMax = 35;

    // Actually need to think more if flame wall armor busts are reasonable
//			@Config.Comment("If all three of Rahovart Flame Wall attacks have a damage cap based on his pierce stat")
//			@Config.Name("Hellfire Energy Attacks Limited Scaled")
//			public boolean hellfireAttackLimitedScaled = true;
//
//			@Config.Comment("Ratio of Pierce Damage that the armor reduced/durability damage will deal at max")
//			@Config.Name("Hellfire Energy Attacks Limited Scale Value")
//			@Config.RangeInt(min = 0)
//			public float hellfireAttackLimitedScaleValue = 4.0F;

    @Config.Comment("How much Hellfire energy is gained from a Belph in Phase 1 (Lycanites uses 20 with 0 passive energy)")
    @Config.Name("Hellfire Energy Belph")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergyBelph = 20;

    @Config.Comment("How much Hellfire energy is gained from a Behemoth in Phase 2 (Lycanites uses 20 with 0 passive energy)")
    @Config.Name("Hellfire Energy Behemoth")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergyBehemoth = 20;

    @Config.Comment("Hellfire energy passively gained per second in Phase 1 (Belph required to fire Wave)")
    @Config.Name("Hellfire Energy Self Phase 1")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP1 = 5;

    @Config.Comment("Hellfire energy passively gained per second in Phase 2 (Behemoth required for wall)")
    @Config.Name("Hellfire Energy Self Phase 2")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP2 = 5;

    @Config.Comment("Hellfire energy passively gained per second in Phase 3 (Lycanites uses 5)")
    @Config.Name("Hellfire Energy Self Phase 3")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP3 = 10;

    @Config.Comment("Specifies Hellfire Walls to clear away from Rahovart, inner walls snap to outer walls")
    @Config.Name("Hellfire Wall Displacement")
    @Config.RangeInt(min = 0, max = 4)
    public int hellfireWallDisplacement = 2;

    @Config.Comment("Specifies Tick Duration of Hellfire Walls (Every 200 lines up with E/W Axis, Lycanites ues 400)")
    @Config.Name("Hellfire Wall Duration")
    @Config.RangeInt(min = 0)
    public int hellfireWallTimeMax = 800;

    @Config.Comment("How much Hellfire energy is refunded upon a p2->p3 transition per active wall")
    @Config.Name("Hellfire Wall Cleanup Refund")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireWallCleanupRefund = 0;

    @Config.Comment("Specifies Hellfire Barriers to clear away from Rahovart, inner barriers snap to outer barriers")
    @Config.Name("Hellfire Barrier Displacement")
    @Config.RangeInt(min = 0, max = 4)
    public int hellfireBarrierDisplacement = 3;

    @Config.Comment("Specifies Hellfire Barriers degration per Belph kill (Lycanites uses 50/100)")
    @Config.Name("Hellfire Barrier Belph Degrade")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierBelphDegrade = 25;

    @Config.Comment("Specifies Hellfire Barriers degration per Behemoth kill (Lycanites uses 100/100)")
    @Config.Name("Hellfire Barrier Behemoth Degrade")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierBehemothDegrade = 75;

    @Config.Comment("How much Hellfire energy is refunded upon a p3->p2 transition per active barrier")
    @Config.Name("Hellfire Barrier Cleanup Refund")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierCleanupRefund = 50;

    @Config.Comment("Should Rahovart try attacking players hiding in arena walls")
    @Config.Name("Player Xray Target")
    public boolean playerXrayTarget = true;

    @Config.Comment("Archvile Summons one Royal variant instead of 3 normal")
    @Config.Name("Spawns Royal Archvile")
    @Config.RequiresMcRestart
    public boolean royalArchvile = true;

    @Config.Comment("Should Phase 3 Summon an Ebon Cacodemon")
    @Config.Name("Spawns Ebon Cacodemon")
    @Config.RequiresMcRestart
    public boolean cacodemonSummon = true;
}
