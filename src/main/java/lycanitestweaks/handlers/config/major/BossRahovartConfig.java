package lycanitestweaks.handlers.config.major;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class BossRahovartConfig {

    @Config.Comment("Main toggle to enable this feature and its configs")
    @Config.Name("0. Enable")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossrahovarttweaks.json")
    public boolean bossTweaksRahovart = true;

    @Config.Comment("Replace the 50hp/sec heal with a 2% Max HP/sec heal")
    @Config.Name("1. Heal Portion When No Players")
    @Config.RequiresMcRestart
    public boolean healPortionNoPlayers = true;

    @Config.Comment("Should Rahovart try attacking players hiding in arena walls")
    @Config.Name("1. Player Xray Target")
    public boolean playerXrayTarget = true;

    @Config.Comment("Projectile to replace targeted 'hellfireball' auto attack")
    @Config.Name("2. Main Targeted Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileTarget = "sigilwraithskull";

    @Config.Comment("Projectile to replace all players 'hellfireball' auto attack")
    @Config.Name("2. Main All Players Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileAll = "sigilhellfireball";

    @Config.Comment("Maximum Lifespan in ticks for Belphs and Behemoths. Set to 0 to not use a temporary timer")
    @Config.Name("3. Minion Temporary Duration")
    @Config.RangeInt(min = 0)
    public int minionTemporaryDuration = 1200;

    @Config.Comment("Minimum spawn range for only Belphs and Behemoths (Lycanites uses 5)")
    @Config.Name("3. Minion Spawn Range Minimum")
    @Config.RangeInt(min = 0, max = 35)
    public int minionSpawnRangeMin = 15;

    @Config.Comment("Maximum spawn range for only Belphs and Behemoths (Lycanites uses 5)")
    @Config.Name("3. Minion Spawn Range Maximum")
    @Config.RangeInt(min = 0, max = 35)
    public int minionSpawnRangeMax = 35;

    @Config.Comment("Base Damage of Hellfire Energy Attacks. Lycanites uses 10")
    @Config.Name("4. Hellfire Energy Attacks Base Damage")
    public int hellfireAttacksBaseDamage = 10;

    @Config.Comment("Whether all three of Rahovart Flame Wall attacks have their damage always match level 1 Rahovart")
    @Config.Name("4. Hellfire Energy Attacks Fixed Damage")
    public boolean hellfireAttackFixedDamage = true;

    @Config.Comment("Whether on hit purge removes more than Lycanites defined list")
    @Config.Name("4. Hellfire Energy Attacks Purge Any Buff")
    public boolean hellfireAttackPurgeAnyBuff = true;

    @Config.Comment("Duration of Voided Debuff in seconds, set to 0 to disable")
    @Config.Name("4. Hellfire Energy Attacks Voided Time")
    public int hellfireAttackVoidedTime = 3;

    @Config.Comment("How much Hellfire energy is gained from a Belph in Phase 1 (Lycanites uses 20 with 0 passive energy)")
    @Config.Name("5. Hellfire Energy Belph")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergyBelph = 20;

    @Config.Comment("Hellfire energy passively gained per second in Phase 1 (Belph required to fire Wave)")
    @Config.Name("5. Hellfire Energy Self Phase 1")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP1 = 5;

    @Config.Comment("How much Hellfire energy is gained from a Behemoth in Phase 2 (Lycanites uses 20 with 0 passive energy)")
    @Config.Name("6. Hellfire Energy Behemoth")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergyBehemoth = 20;

    @Config.Comment("Hellfire energy passively gained per second in Phase 2 (Behemoth required for wall)")
    @Config.Name("6. Hellfire Energy Self Phase 2")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP2 = 5;

    @Config.Comment("Specifies Hellfire Walls to clear away from Rahovart, inner walls snap to outer walls")
    @Config.Name("6. Hellfire Wall Displacement")
    @Config.RangeInt(min = 0, max = 4)
    public int hellfireWallDisplacement = 2;

    @Config.Comment("Specifies Tick Duration of Hellfire Walls (Every 200 lines up with E/W Axis, Lycanites ues 400)")
    @Config.Name("6. Hellfire Wall Duration")
    @Config.RangeInt(min = 0)
    public int hellfireWallTimeMax = 800;

    @Config.Comment("How much Hellfire energy is refunded upon a p2->p3 transition per active wall")
    @Config.Name("6. Hellfire Wall Cleanup Refund")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireWallCleanupRefund = 50;

    @Config.Comment("Archvile Summons one Royal variant instead of 3 normal")
    @Config.Name("6. Spawns Royal Archvile")
    @Config.RequiresMcRestart
    public boolean royalArchvile = true;

    @Config.Comment("Hellfire energy passively gained per second in Phase 3 (Lycanites uses 5)")
    @Config.Name("7. Hellfire Energy Self Phase 3")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP3 = 10;

    @Config.Comment("Specifies Hellfire Barriers to clear away from Rahovart, inner barriers snap to outer barriers")
    @Config.Name("7. Hellfire Barrier Displacement")
    @Config.RangeInt(min = 0, max = 4)
    public int hellfireBarrierDisplacement = 3;

    @Config.Comment("Specifies Hellfire Barriers degradation per Belph kill (Lycanites uses 50/100)")
    @Config.Name("7. Hellfire Barrier Belph Degrade")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierBelphDegrade = 25;

    @Config.Comment("Specifies Hellfire Barriers degradation per Behemoth kill (Lycanites uses 100/100)")
    @Config.Name("7. Hellfire Barrier Behemoth Degrade")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierBehemothDegrade = 75;

    @Config.Comment("How much Hellfire energy is refunded upon a p3->p2 transition per active barrier")
    @Config.Name("7. Hellfire Barrier Cleanup Refund")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierCleanupRefund = 50;

    @Config.Comment("Should Phase 3 Summon an Ebon Cacodemon")
    @Config.Name("7. Spawns Ebon Cacodemon")
    @Config.RequiresMcRestart
    public boolean cacodemonSummon = true;
}
