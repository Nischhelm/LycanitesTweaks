package lycanitestweaks.handlers.config.major;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.common.config.Config;

@MixinConfig(name = LycanitesTweaks.MODID)
public class BossRahovartConfig {

    @Config.Comment("Main toggle to enable this feature and its configs")
    @Config.Name("0. Enable Rahovart Modifications")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featurebossrahovarttweaks.json")
    public boolean bossTweaksRahovart = true;

    @Config.Comment("Replace the 50hp/sec heal with a 2% Max HP/sec heal")
    @Config.Name("Heal Portion When No Nearby Players")
    @Config.RequiresMcRestart
    public boolean healPortionNoPlayers = true;

    @Config.Comment("Player detection range where if there are no players nearby, start healing. (Lycanites uses 64)\n" +
            "LycanitesTweaks uses 48, which is inside the arena.")
    @Config.Name("Heal Portion - Range")
    @Config.RequiresMcRestart
    public int healPortionNoPlayersRange = 48;

    @Config.Comment("If minions have no target and are at least this distance away, teleport to host.")
    @Config.Name("Minion Teleport Range")
    @Config.RequiresMcRestart
    public int minionTeleportRange = 40;

    @Config.Comment("Should Rahovart try attacking players hiding in arena walls")
    @Config.Name("Player Xray Target")
    public boolean playerXrayTarget = true;

    @Config.Comment("Projectile to replace targeted 'hellfireball' auto attack. \n" +
            "LycanitesTweaks replaces it with a projectile that flies straight, \n" +
            "shoots targeted 'hellfireball' projectiles while flying, \n" +
            "shoots random 'doomfireball' projectiles while flying and \n" +
            "explodes with Creeper strength. (JSON configurable!)")
    @Config.Name("Main Targeted Auto Attack Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileTarget = "sigilwraithskull";

    @Config.Comment("Projectile to replace all players 'hellfireball' auto attack. \n" +
            "LycanitesTweaks replaces it with a projectile that flies straight. (JSON configurable!)")
    @Config.Name("Main All Players Auto Attack Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileAll = "sigilhellfireball";

    @Config.Comment("Maximum Lifespan in ticks for Belphs and Behemoths as they have persistence. Set to 0 to not use a temporary timer.\n" +
            "Vanilla Rahovart has cases where minions are not sacrificed and infinitely summons more.")
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

    @Config.Comment("Base Damage of Hellfire Energy Attacks. Lycanites uses 10")
    @Config.Name("Hellfire Energy Attacks Base Damage")
    public int hellfireAttacksBaseDamage = 10;

    @Config.Comment("Whether all three of Rahovart Flame Wall attacks have their damage always match level 1 Rahovart. Rebalanced to focus on buff removal instead of damage.")
    @Config.Name("Hellfire Energy Attacks Fixed Damage")
    public boolean hellfireAttackFixedDamage = true;

    @Config.Comment("Whether on hit purge removes more than Lycanites defined list")
    @Config.Name("Hellfire Energy Attacks Purge Any Buff")
    public boolean hellfireAttackPurgeAnyBuff = true;

    @Config.Comment("Duration of Voided debuff in seconds, set to 0 to disable")
    @Config.Name("Hellfire Energy Attacks Voided Time")
    public int hellfireAttackVoidedTime = 3;

    @Config.Comment("How much Hellfire energy is gained from a Belph in Phase 1 (Lycanites uses 20 with 0 passive energy)")
    @Config.Name("Hellfire Energy Belph")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergyBelph = 20;

    @Config.Comment("Hellfire energy passively gained per second in Phase 1 (Belph required to fire Wave)")
    @Config.Name("Hellfire Energy Passive Phase 1")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP1 = 5;

    @Config.Comment("How much Hellfire energy is gained from a Behemoth in Phase 2 (Lycanites uses 20 with 0 passive energy)")
    @Config.Name("Hellfire Energy Behemoth")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergyBehemoth = 20;

    @Config.Comment("Hellfire energy passively gained per second in Phase 2 (Behemoth required for wall)")
    @Config.Name("Hellfire Energy Passive Phase 2")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP2 = 5;

    @Config.Comment("Specifies Hellfire Walls to clear away from Rahovart,\n " +
            "inner walls snap to outer walls and will always hit original spawn position once.\n " +
            "Rebalanced to allow a small area that is safe to melee from.")
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
    public int hellfireWallCleanupRefund = 50;

    @Config.Comment("Archvile Summons one Royal variant instead of 3 normal")
    @Config.Name("Spawns Royal Archvile")
    @Config.RequiresMcRestart
    public boolean royalArchvile = true;

    @Config.Comment("Hellfire energy passively gained per second in Phase 3 (Lycanites uses 5)")
    @Config.Name("Hellfire Energy Passive Phase 3")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireEnergySelfP3 = 10;

    @Config.Comment("Specifies Hellfire Barriers to clear away from Rahovart,\n " +
            "inner barriers snap to outer barriers and will always hit original spawn position once.\n " +
            "Rebalanced to allow a small area that is safe to melee from.")
    @Config.Name("Hellfire Barrier Displacement")
    @Config.RangeInt(min = 0, max = 4)
    public int hellfireBarrierDisplacement = 3;

    @Config.Comment("Specifies Hellfire Barriers degradation per Belph kill (Lycanites uses 50/100)")
    @Config.Name("Hellfire Barrier Belph Degrade")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierBelphDegrade = 25;

    @Config.Comment("Specifies Hellfire Barriers degradation per Behemoth kill (Lycanites uses 100/100)")
    @Config.Name("Hellfire Barrier Behemoth Degrade")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierBehemothDegrade = 75;

    @Config.Comment("How much Hellfire energy is refunded upon a p3->p2 transition per active barrier")
    @Config.Name("Hellfire Barrier Cleanup Refund")
    @Config.RangeInt(min = 0, max = 100)
    public int hellfireBarrierCleanupRefund = 50;

    @Config.Comment("Should Phase 3 Summon an Ebon Cacodemon")
    @Config.Name("Spawns Ebon Cacodemon")
    @Config.RequiresMcRestart
    public boolean cacodemonSummon = true;
}
