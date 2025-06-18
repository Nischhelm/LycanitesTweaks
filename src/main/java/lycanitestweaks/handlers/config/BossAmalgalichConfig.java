package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class BossAmalgalichConfig {

    @Config.Comment("Main toggle to enable this feature and its configs")
    @Config.Name("0. Enable")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossamalgalich.json")
    public boolean bossTweaksAmalgalich = true;

    @Config.Comment("Replace the 50hp/sec heal with a 2% Max HP/sec heal")
    @Config.Name("1. Heal Portion When No Players")
    @Config.RequiresMcRestart
    public boolean healPortionNoPlayers = true;

    @Config.Comment("Whether Amalgalich attacks players hiding in arena walls")
    @Config.Name("1. Player Xray Target")
    public boolean playerXrayTarget = true;

    @Config.Comment("Projectile to replace targeted 'spectralbolt' auto attack")
    @Config.Name("2. Main Targeted Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileTarget = "lichspectralbolt";

    @Config.Comment("Projectile to replace all players 'spectralbolt' auto attack")
    @Config.Name("2. Main All Players Projectile Name")
    @Config.RequiresMcRestart
    public String mainProjectileAll = "lichspectralbolt";

    @Config.Comment("Add a targeted attack to use alongside auto attacks. Attack is like Asmodeus' except it creates less projectiles.")
    @Config.Name("2. Targeted Projectile Attack")
    @Config.RequiresMcRestart
    public boolean targetedAttack = true;

    @Config.Comment("Projectile to use for targeted attack")
    @Config.Name("2. Targeted Projectile Name")
    public String targetedProjectile = "lichshadowfire";

    @Config.Comment("Targeted Projectile Cooldown Ticks")
    @Config.Name("2. Targeted Projectile Cooldown Ticks")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public int targetedProjectileGoalCooldown = 360;

    @Config.Comment("Targeted Projectile Stamina Drain (Uptime = Cooldown/DrainRate)")
    @Config.Name("2. Targeted Projectile Stamina Drain Rate")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public int targetedProjectileStaminaDrainRate = 6;

    @Config.Comment("Consumption in any phases, goal/animation shared across the fight")
    @Config.Name("3. Consumption All Phases")
    @Config.RequiresMcRestart
    public boolean consumptionAllPhases = true;

    @Config.Comment("Consumption Cooldown Duration Ticks (Lycanites uses 400)")
    @Config.Name("3. Consumption Cooldown Duration Ticks")
    @Config.RangeInt(min = 0)
    @Config.RequiresMcRestart
    public int consumptionGoalCooldown = 400;

    @Config.Comment("Whether consumption should use LycanitesTweaks Consumption debuff")
    @Config.Name("3. Consumption Debuff effect")
    @Config.RequiresMcRestart
    public boolean consumptionEffect = true;

    @Config.Comment("Whether consumption should deal damage based on victim's max hp")
    @Config.Name("3. Consumption Damage Max HP")
    public boolean consumptionDamageMaxHP = true;

    @Config.Comment("Make Consumption more immersive by relying on Consumption debuff to reduce max hp.")
    @Config.Name("3. Consumption Damages Players")
    public boolean consumptionDamagesPlayers = false;

    @Config.Comment("Whether minion kill should heal a % of max hp instead of a flat amount. Lycanites uses a flat 25.0")
    @Config.Name("3. Consumption Kill Heal Portion")
    public boolean consumptionKillHealPortion = false;

    @Config.Comment("HP healed on minion kill")
    @Config.Name("3. Consumption Kill Heal Amount")
    public float consumptionKillHeal = 25F;

    @Config.Comment("Chance that Amalgalich killing an Epion will Extinguish Shadow Fire. Player kill is always 100%")
    @Config.Name("3. Consumption Kill Epion Extinguish")
    public float consumptionKillEpionChance = 0.8F;

    @Config.Comment("Custom value for shadow fire extinguish width on death (Lycanites uses 10)")
    @Config.Name("3. Custom Epion Extinguish Width")
    public int customEpionExtinguishWidth = 10;

    @Config.Comment("Epion Summons one Crimson variant instead of 3 normal")
    @Config.Name("3. Spawns Crimson Epion")
    @Config.RequiresMcRestart
    public boolean crimsonEpion = true;

    @Config.Comment("Replace Lob Darkling with Summon Goal, as a high level Amalgalich spams too much")
    @Config.Name("4. Lob Darklings Replace")
    @Config.RequiresMcRestart
    public boolean replaceLobDarkling = true;

    @Config.Comment("Should Phase 3 Summon a Lunar Grue")
    @Config.Name("4. Spawns Lunar Grue")
    @Config.RequiresMcRestart
    public boolean grueSummon = true;

}
