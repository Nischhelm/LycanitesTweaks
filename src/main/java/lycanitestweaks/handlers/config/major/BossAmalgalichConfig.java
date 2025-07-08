package lycanitestweaks.handlers.config.major;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class BossAmalgalichConfig {

    @Config.Comment("Main toggle to enable this feature and its configs")
    @Config.Name("0. Enable Amalgalich Modifications")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossamalgalich.json")
    public boolean bossTweaksAmalgalich = true;

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

    @Config.Comment("Whether Amalgalich attacks players hiding in arena walls")
    @Config.Name("Player Xray Target")
    public boolean playerXrayTarget = true;

    @Config.Comment("Projectile to replace targeted 'spectralbolt' auto attack. \n" +
            "LycanitesTweaks replaces it with a projectile that noclips through terrain, \n" +
            "places temporary Shadow Fire, \n" +
            "and has a 10% chance to remove one buff. (JSON configurable!)")
    @Config.Name("Main Targeted Auto Attack Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileTarget = "lichspectralbolt";

    @Config.Comment("Projectile to replace all players 'spectralbolt' auto attack. \n" +
            "LycanitesTweaks replaces it with a projectile that noclips through terrain, \n" +
            "places temporary Shadow Fire, \n" +
            "and has a 10% chance to remove one buff. (JSON configurable!)")
    @Config.Name("Main All Players Auto Attack Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileAll = "lichspectralbolt";

    @Config.Comment("Add a manual single target attack to use alongside auto attacks. Attack is like Asmodeus' except it creates less projectiles.")
    @Config.Name("Additional Single Target Manual Attack")
    @Config.RequiresMcRestart
    public boolean targetedAttack = true;

    @Config.Comment("Projectile to use for the single target manual attack. \n" +
            "LycanitesTweaks uses a projectile that noclips through terrain, \n" +
            "places temporary Shadow Fire, \n" +
            "and has a 50% chance to remove one buff. (JSON configurable!)")
    @Config.Name("Single Target Manual Attack Projectile Name")
    public String targetedProjectile = "lichshadowfire";

    @Config.Comment("Single target manual attack projectile cooldown ticks")
    @Config.Name("Single Target Attack Cooldown Ticks")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public int targetedProjectileGoalCooldown = 360;

    @Config.Comment("Single target manual attack projectile stamina drain (Uptime = Cooldown/DrainRate)")
    @Config.Name("Single Target Attack Stamina Drain Rate")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public int targetedProjectileStaminaDrainRate = 6;

    @Config.Comment("Consumption in all phases, goal/animation shared across the fight")
    @Config.Name("Consumption In All Phases")
    @Config.RequiresMcRestart
    public boolean consumptionAllPhases = true;

    @Config.Comment("Consumption cooldown ticks (Lycanites uses 400)")
    @Config.Name("Consumption Cooldown Ticks")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public int consumptionGoalCooldown = 400;

    @Config.Comment("Whether Consumption should use \"lycanitestweaks:consumed\" debuff")
    @Config.Name("Consumption Debuff Effect")
    @Config.RequiresMcRestart
    public boolean consumptionEffect = true;

    @Config.Comment("Whether Consumption should deal damage based on victim's max hp")
    @Config.Name("Consumption Damage Max HP")
    public boolean consumptionDamageMaxHP = true;

    @Config.Comment("Makes Consumption more immersive by relying on Consumption debuff to reduce max hp.")
    @Config.Name("Consumption Damages Players")
    public boolean consumptionDamagesPlayers = false;

    @Config.Comment("Whether minion kill should heal a % of max hp instead of a flat amount. Lycanites uses a flat 25.0")
    @Config.Name("Consumption Kill Heal Portion")
    public boolean consumptionKillHealPortion = false;

    @Config.Comment("HP healed on minion kill")
    @Config.Name("Consumption Kill Heal Amount")
    public float consumptionKillHeal = 25F;

    @Config.Comment("Summons one Crimson variant instead of 3 normal")
    @Config.Name("Spawns Crimson Epion")
    @Config.RequiresMcRestart
    public boolean crimsonEpion = true;

    @Config.Comment("Chance that Amalgalich killing an Epion will extinguish Shadow Fire. Player kill is always 100%")
    @Config.Name("Consumption Kill Epion Extinguish Chance")
    public float consumptionKillEpionChance = 0.8F;

    @Config.Comment("Custom value for Shadow Fire extinguish width on death (Lycanites uses 10)")
    @Config.Name("Epion Extinguish Width")
    public int customEpionExtinguishWidth = 16;

    @Config.Comment("Replace Lob Darkling with Summon Goal. These Darklings are always level 1 and there is no limit to spamming the attack.")
    @Config.Name("Lob Darklings Replace With Summon Goal")
    @Config.RequiresMcRestart
    public boolean replaceLobDarkling = true;

    @Config.Comment("Should Phase 3 Summon a Lunar Grue")
    @Config.Name("Spawns Lunar Grue")
    @Config.RequiresMcRestart
    public boolean grueSummon = true;

}
