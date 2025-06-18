package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class BossAsmodeusConfig {

    @Config.Comment("Main toggle to enable this feature and its configs")
    @Config.Name("0. Enable")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossasmodeustweaks.json")
    public boolean bossTweaksAsmodeus = true;

    @Config.Comment("Replace the 50hp/sec heal with a 2% Max HP/sec heal")
    @Config.Name("1. Heal Portion When No Players")
    @Config.RequiresMcRestart
    public boolean healPortionNoPlayers = true;

    @Config.Comment("Whether Asmodeus targets players behind walls. This fixes the cheese strat of hiding behind a pillar.")
    @Config.Name("1. Player Xray Target")
    public boolean playerXrayTarget = true;

    @Config.Comment("Fixes double damage and stops hitscan damage from ignoring walls with xRay on")
    @Config.Name("1. Disable Ranged Hitscan")
    public boolean disableRangedHitscan = true;

    @Config.Comment("Add an AI Ranged auto attack (targeted/all) to use alongside chaingun. Attack is like Rahovart's")
    @Config.Name("2. Additional Auto Attack")
    @Config.RequiresMcRestart
    public boolean additionalProjectileAdd = true;

    @Config.Comment("Projectile to use for targeted auto attacking")
    @Config.Name("2. Additional Targeted Projectile Name")
    @Config.RequiresMcRestart
    public String additionalProjectileTarget = "demonicchaosorb";

    @Config.Comment("Projectile to use for all players auto attacking")
    @Config.Name("2. Additional All Players Projectile Name")
    @Config.RequiresMcRestart
    public String additionalProjectileAll = "demonicchaosorb";

    @Config.Comment("Whether on hit purge removes more than Lycanites defined list")
    @Config.Name("3. Devil Gatling Purge Any Buff")
    public boolean devilGatlingPurgeAnyBuff = true;

    @Config.Comment("Duration of Voided Debuff in seconds, set to 0 to disable")
    @Config.Name("3. Devil Gatling Voided Time")
    public int devilGatlingVoidedTime = 1;

    @Config.Comment("Projectile to replace Devilstar Stream attack")
    @Config.Name("4. Devilstar Projectile Name")
    @Config.RequiresMcRestart
    public String devilstarProjectile = "demonicshockspark";

    @Config.Comment("Devilstar Stream projectile firing active ticks (Lycanites uses 100)")
    @Config.Name("4. Devilstar Stream UpTime")
    @Config.RangeInt(min = 0)
    public int devilstarStreamUpTime = 100;

    @Config.Comment("Devilstar Stream cooldown ticks (Lycanites uses 400, Gatling is 200)")
    @Config.Name("4. Devilstar Stream Cooldown")
    @Config.RangeInt(min = 0)
    public int devilstarCooldown = 360;

    @Config.Comment("Whether Devilstar Stream can be used outside Phase 1")
    @Config.Name("4. Devilstar Stream All Phases")
    public boolean devilstarStreamAllPhases = true;

    @Config.Comment("Whether Astaroth Minions are teleported away on spawn")
    @Config.Name("5. Astaroths Teleport Adjacent Node")
    public boolean astarothsTeleportAdjacent = false;

    @Config.Comment("Whether Astaroth Minions use Rare/Boss Damage Limit")
    @Config.Name("5. Astaroths Boss Damage Limit")
    public boolean astarothsUseBossDamageLimit = false;

    @Config.Comment("Whether Astaroth Minions are flagged as SpawnedAsBoss to interact with other tweaks that check for this property")
    @Config.Name("5. Astaroths Boss SpawnedAsBoss Tag")
    public boolean astarothsSpawnedAsBoss = true;

    @Config.Comment("Astaroth respawn time in seconds (2 per Player, Max 2 Alive, Lycanites uses 30)")
    @Config.Name("6. Astaroths Phase 2 Respawn Time")
    @Config.RangeInt(min = 0)
    public int astarothsRespawnTimePhase2 = 30;

    @Config.Comment("Hellshield is active whenever an Astaroth is alive instead of only phase 2")
    @Config.Name("6. Hellshield All Phases")
    public boolean hellshieldAllPhases = true;

    @Config.Comment("Amount of Damage Reduction Hellshield provides. Lycanites uses 100%")
    @Config.Name("6. Hellshield Damage Reduction")
    @Config.RangeDouble(min = 0, max = 1)
    public float hellshieldDamageReduction = 0.5F;

    @Config.Comment("Astaroth respawn time in seconds (1 per Player, Max 4 Alive per Player, Lycanites uses 40)")
    @Config.Name("7. Astaroths Phase 3 Respawn Time")
    @Config.RangeInt(min = 0)
    public int astarothsRespawnTimePhase3 = 30;

    @Config.Comment("Repair is active whenever an Astaroth is alive instead of only phase 3")
    @Config.Name("7. Repair All Phases")
    public boolean repairAllPhases = false;

    @Config.Comment("Whether Astaroths heal a % of max hp instead of a flat amount. Lycanites uses a flat 2.0")
    @Config.Name("7. Repair Heal Portion")
    public boolean repairHealPortion = false;

    @Config.Comment("HP healed per phase 3 Astaroth")
    @Config.Name("7. Repair Heal Amount")
    public float repairHeal = 25;

    @Config.Comment("Should Phase 3 Summon a Phosphorescent Chupacabra")
    @Config.Name("7. Spawns Phosphorescent Chupacabra")
    @Config.RequiresMcRestart
    public boolean chupacabraSummon = true;
}
