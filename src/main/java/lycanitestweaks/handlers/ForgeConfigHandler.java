package lycanitestweaks.handlers;

import fermiumbooter.annotations.MixinConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import lycanitestweaks.LycanitesTweaks;

import java.util.Arrays;
import java.util.HashSet;


@Config(modid = LycanitesTweaks.MODID)
public class ForgeConfigHandler {

	private static HashSet<ResourceLocation> cleansedCureEffects = null;
	private static HashSet<ResourceLocation> flowersaurBiomes = null;
	private static HashSet<ResourceLocation> immunizationCureEffects = null;
	private static HashSet<String> pmlSpawnerNames = null;

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Enable/Disable Tweaks")
	@Config.Name("Toggle Mixins")
	@MixinConfig.SubInstance
	public static final featuresConfig featuresMixinConfig = new featuresConfig();

	@Config.Comment("Enable/Disable Patches")
	@Config.Name("Toggle Patches")
	@MixinConfig.SubInstance
	public static final PatchConfig mixinPatchesConfig = new PatchConfig();

	public static class ClientConfig {

		@Config.Comment("Enable Debug logging for manually triggered information")
		@Config.Name("Enables debug logging")
		public boolean debugLoggerTrigger = false;

		@Config.Comment("Enable Debug logging for constant information dumping")
		@Config.Name("Enables debug logging automatic")
		public boolean debugLoggerAutomatic = false;

		@Config.Comment("Example client side config option")
		@Config.Name("Example Client Option")
		public boolean exampleClientOption = true;

		@Config.Comment("Test Int")
		@Config.Name("Test Int")
		public int testInt = 1;

		@Config.Comment("Test Double")
		@Config.Name("Test Double")
		public double testDouble = 1.0D;
	}

	public static class ServerConfig {

		@Config.Comment("Requires 'Boss Tweaks Amalgalich' Mixin enabled")
		@Config.Name("Enhanced Amalgalich")
		public final BossAmalgalichConfig amalgalichConfig = new BossAmalgalichConfig();

		@Config.Comment("Requires 'Boss Tweaks Asmodeus' Mixin enabled")
		@Config.Name("Enhanced Asmodeus")
		public final BossAsmodeusConfig asmodeusConfig = new BossAsmodeusConfig();

		@Config.Comment("Requires 'Boss Tweaks Rahovart' Mixin enabled")
		@Config.Name("Enhanced Rahovart")
		public final BossRahovartConfig rahovartConfig = new BossRahovartConfig();

		@Config.Name("Potion Effects")
		public final PotionEffectsConfig effectsConfig = new PotionEffectsConfig();

		@Config.Comment("Requires 'Summon Progression Rework' Mixin enabled")
		@Config.Name("Imperfect Summoning")
		public final ImperfectSummoningConfig imperfectSummoningConfig = new ImperfectSummoningConfig();

		@Config.Name("Player Mob Levels")
		public final PlayerMobLevelsConfig pmlConfig = new PlayerMobLevelsConfig();

		@Config.Name("Entity Can Store Creature")
		public final EntityStoreCreatureConfig escConfig = new EntityStoreCreatureConfig();

		@Config.Name("Level Scaled Loot")
		public final LootConfig lootConfig = new LootConfig();

		@Config.Comment("Requires 'Cap Specific Creature Stats' or 'Boss Lower Health Scale' Mixin enabled")
		@Config.Name("Creature Stats")
		public final StatsConfig statsConfig = new StatsConfig();

		@Config.Comment("Distance between entities to trigger auto pickup drop, Default Lycanites is 32. Requires Mixin 'Pickup Checks Distances'")
		@Config.Name("Pick Up Distance")
		@Config.RangeDouble(min = 0)
		public double pickUpDistance = 8.0D;

		@Config.Comment("Pet Perch Angle In Radians, Default Lycanites is 90. Requires Mixin 'Perch Position Modifiable'")
		@Config.Name("Perch Angle Radians")
		@Config.RangeDouble(min = -360, max = 360)
		public double perchAngle = 90.0D;

		@Config.Comment("Pet Perch Distance Scale, based on ridden entity, Default Lycanites is 0.7. Requires Mixin 'Perch Position Modifiable'")
		@Config.Name("Perch Distance")
		@Config.RangeDouble(min = 0)
		public double perchDistance = 1.0D;

		@Config.Comment("Max size change amount based on lycanitesmobs config range. Requires Mixin 'Size Change Foods'")
		@Config.Name("Tamed Size Change Degree")
		@Config.RangeDouble(min = 0.0)
		public double sizeChangeDegree = 0.1D;

		@Config.Comment("Chance for a successful attempt to tame with healing food. Requires Mixin 'Tame Creatures with their healing food'")
		@Config.Name("Tamed With Healing Food Chance")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float tameWithFoodChance = 0.3F;

		@Config.Comment("Do not allow flying creatures to be tamed with healing food")
		@Config.Name("Tame With Food No Flying")
		public boolean tameWithFoodNoFlying = true;

		@Config.Comment("In order to use a vanilla saddle, the mount most be at least this level. Requires Mixin 'Mounts can use Vanilla saddles with limitations'")
		@Config.Name("Vanilla Saddle Creature Level Requirement")
		public int vanillaSaddleLevelRequirement = 16;

		@Config.Comment("Do not allow flying creatures use the vanilla saddle")
		@Config.Name("Vanilla Saddle No Flying")
		public boolean vanillaSaddleNoFlying = true;

		@Config.Comment("Minimum level all parts of equipment must be in order to apply enchantments. Requires Mixin 'Crafted Equipment Enchantments'")
		@Config.Name("Equipment Minimum Level to Apply Enchantments")
		public int equipmentMinLevelEnchantable = 3;

		@Config.Comment("List of potion resource locations cleansed will cure. Requires Mixin 'Customizable Curing Item Effects List'")
		@Config.Name("Cleansed Effects To Cure")
		public String[] cleansedEffectsToCure = {
				"minecraft:wither",
				"minecraft:unluck",
				"lycanitesmobs:fear",
				"lycanitesmobs:insomnia",
				"lycanitesmobs:decay"
		};

		@Config.Comment("List of potion resource locations immunization will cure. Requires Mixin 'Customizable Curing Item Effects List'")
		@Config.Name("Immunization Effects To Cure")
		public String[] immunizationEffectsToCure = {
				"minecraft:poison",
				"minecraft:hunger",
				"minecraft:weakness",
				"minecraft:nausea",
				"lycanitesmobs:paralysis",
				"lycanitesmobs:bleed"
		};

		@Config.Comment("List of biome resource locations where custom name Arisaurs will spawn in. Requires Mixin 'Flowersaurs Naturally Spawn'")
		@Config.Name("Biomes Flowersaurs Spawn In")
		public String[] flowersaurSpawningBiomes = {
				"minecraft:mutated_forest",
				"biomesoplenty:mystic_grove",
				"twilightforest:enchanted_forest"
		};

		@Config.Comment("Apply Mixin 'Lycanites Fire Extinguish (Vanilla)' to all modded fires")
		@Config.Name("Mod Compatibility: Fix all Modded Fire Extinguish")
		public boolean fixAllModdedFireExtinguish = true;

		@Config.Comment("Apply Mixin 'Potion Core Jump Fix (PotionCore)' to all mobs")
		@Config.Name("Mod Compatibility: Fix all Mobs PotionCore Jump")
		public boolean fixAllMobsPotionCoreJump = true;

		public static class BossAmalgalichConfig {

			@Config.Comment("Projectile to replace main 'spectralbolt' auto attack (targeted/all)")
			@Config.Name("Main Projectile Name")
			@Config.RequiresMcRestart
			public String mainProjectile = "lichspectralbolt";

			@Config.Comment("Replace the 50hp/sec heal with a 2% Max HP/sec heal")
			@Config.Name("Heal Portion When No Players")
			@Config.RequiresMcRestart
			public boolean healPortionNoPlayers = true;

			@Config.Comment("Add AI task for targeted attack to use alongside auto attacks")
			@Config.Name("Targeted Projectile Attack")
			@Config.RequiresMcRestart
			public boolean targetedAttack = true;

			@Config.Comment("Projectile to use for targeted attack")
			@Config.Name("Targeted Projectile Name")
			public String targetedProjectile = "lichshadowfire";

			@Config.Comment("Targeted Projectile Cooldown Ticks")
			@Config.Name("Targeted Projectile Cooldown Ticks")
			@Config.RangeInt(min = 0)
			@Config.RequiresMcRestart
			public int targetedProjectileGoalCooldown = 360;

			@Config.Comment("Targeted Projectile Stamina Drain (Uptime = Cooldown/DrainRate)")
			@Config.Name("Targeted Projectile Stamina Drain Rate")
			@Config.RangeInt(min = 0)
			@Config.RequiresMcRestart
			public int targetedProjectileStaminaDrainRate = 6;

			@Config.Comment("Consumption in any phases, goal/animation shared across the fight")
			@Config.Name("Consumption All Phases")
			@Config.RequiresMcRestart
			public boolean consumptionAllPhases = true;

			@Config.Comment("Consumption Cooldown Duration Ticks (Lycanites uses 400)")
			@Config.Name("Consumption Cooldown Duration Ticks")
			@Config.RangeInt(min = 0)
			@Config.RequiresMcRestart
			public int consumptionGoalCooldown = 400;

			@Config.Comment("If consumption should use LycanitesTweaks Consumption debuff")
			@Config.Name("Consumption Debuff effect")
			@Config.RequiresMcRestart
			public boolean consumptionEffect = true;

			@Config.Comment("If consumption should deal damage based on victim's max hp")
			@Config.Name("Consumption Damage Max HP")
			public boolean consumptionDamageMaxHP = true;

			@Config.Comment("Make Consumption more immersive by relying on Consumption debuff to reduce max hp.")
			@Config.Name("Consumption Damages Players")
			public boolean consumptionDamagesPlayers = false;

			@Config.Comment("Portion of HP healed on minion kill, set to 0 to use original 25.0 healing")
			@Config.Name("Consumption Kill Heal")
			public float consumptionKillHeal = 0.005F;

			@Config.Comment("Chance that Amalgalich killing an Epion will Extinguish Shadow Fire")
			@Config.Name("Consumption Kill Epion Extinguish")
			public float consumptionKillEpionChance = 0.8F;

			@Config.Comment("Custom value for shadow fire extinguish width on death (Lycanites uses 10)")
			@Config.Name("Custom Epion Extinguish Width")
			public int customEpionExtinguishWidth = 10;

			@Config.Comment("Replace Lob Darkling with Summon Goal, as a high level Amalgalich spams too much")
			@Config.Name("Lob Darklings Replace")
			@Config.RequiresMcRestart
			public boolean replaceLobDarkling = true;

			@Config.Comment("Should Amalgalich try attacking players hiding in arena walls")
			@Config.Name("Player Xray Target")
			public boolean playerXrayTarget = true;

			@Config.Comment("Epion Summons one Crimson variant instead of 3 normal")
			@Config.Name("Spawns Crimson Epion")
			@Config.RequiresMcRestart
			public boolean crimsonEpion = true;

			@Config.Comment("Should Phase 3 Summon a Lunar Grue")
			@Config.Name("Spawns Lunar Grue")
			@Config.RequiresMcRestart
			public boolean grueSummon = true;

		}

		public static class BossAsmodeusConfig {

			@Config.Comment("Replace the 50hp/sec heal with a 2% Max HP/sec heal")
			@Config.Name("Heal Portion When No Players")
			@Config.RequiresMcRestart
			public boolean healPortionNoPlayers = true;

			@Config.Comment("Add AI Ranged auto attacking (targeted/all) for an additional projectile")
			@Config.Name("Additional Projectile Register")
			@Config.RequiresMcRestart
			public boolean additionalProjectileAdd = true;

			@Config.Comment("Projectile to use for auto attacking")
			@Config.Name("Additional Projectile Name")
			@Config.RequiresMcRestart
			public String additionalProjectile = "demonicchaosorb";

			@Config.Comment("Fixes double damage and stop hitscan damage ignoring walls with xRay on")
			@Config.Name("Disable Ranged Hitscan")
			public boolean disableRangedHitscan = true;

			@Config.Comment("Should on hit purge remove more than Lycanites defined list?")
			@Config.Name("Devil Gatling Purge Any Buff")
			public boolean devilGatlingPurgeAnyBuff = true;

			@Config.Comment("Duration of Voided Debuff in seconds, set to 0 to disable")
			@Config.Name("Devil Gatling Voided Time")
			public int devilGatlingVoidedTime = 1;

			@Config.Comment("Projectile to replace Devilstar Stream attack")
			@Config.Name("Devilstar Projectile Name")
			@Config.RequiresMcRestart
			public String devilstarProjectile = "demonicshockspark";

			@Config.Comment("Devilstar Stream projectile firing active ticks (Lycanites uses 100)")
			@Config.Name("Devilstar Stream UpTime")
			@Config.RangeInt(min = 0)
			public int devilstarStreamUpTime = 100;

			@Config.Comment("Devilstar Stream cooldown ticks (Lycanites uses 400, Gatling is 200)")
			@Config.Name("Devilstar Stream Cooldown")
			@Config.RangeInt(min = 0)
			public int devilstarCooldown = 360;

			@Config.Comment("Whether Devilstar Stream can be used outside Phase 1")
			@Config.Name("Devilstar Stream All Phases")
			public boolean devilstarStreamAllPhases = true;

			@Config.Comment("Whether Astaroth Minions are teleported away on spawn")
			@Config.Name("Astaroths Teleport Adjacent Node")
			public boolean astarothsTeleportAdjacent = true;

			@Config.Comment("Whether Astaroth Minions use Rare/Boss Damage Limit")
			@Config.Name("Astaroths Boss Damage Limit")
			public boolean astarothsUseBossDamageLimit = true;

			@Config.Comment("Astaroth respawn time in seconds (2 per Player Max 2 Alive, Lycanites uses 30)")
			@Config.Name("Astaroths Phase 2 Respawn Time")
			@Config.RangeInt(min = 0)
			public int astarothsRespawnTimePhase2 = 30;

			@Config.Comment("Astaroth respawn time in seconds (1 per Player Max 4 Alive per Player, Lycanites uses 40)")
			@Config.Name("Astaroths Phase 3 Respawn Time")
			@Config.RangeInt(min = 0)
			public int astarothsRespawnTimePhase3 = 30;

			@Config.Comment("Hell Shield Damage Received when hit is pow(ATTACK_DAMAGE, power)")
			@Config.Name("Hell Shield Damage Power")
			@Config.RangeInt(min = 1)
			public int hellShieldDamagePower = 3;

			@Config.Comment("Hell Shield Health for 100% Damage Reduction, reduction is linear up to this point")
			@Config.Name("Hell Shield Maximum Health")
			@Config.RangeInt(min = 1)
			public int hellShieldHealthMax = 20000;

			@Config.Comment("Hell Shield Overheal to extend full power shield")
			@Config.Name("Hell Shield Overheal Ratio")
			@Config.RangeDouble(min = 1D)
			public double hellShieldOverhealRatio = 2.0D;

			@Config.Comment("Hell Shield Max Health Regenerated by alive Astaroths every second")
			@Config.Name("Hell Shield Astaroth Regeneration")
			@Config.RangeDouble(min = 0D)
			public double hellShieldAstarothRegen = 0.1D;

			@Config.Comment("Should Asmodeus attempt attacks on players behind arena pillars")
			@Config.Name("Player Xray Target")
			public boolean playerXrayTarget = true;

			@Config.Comment("Should Phase 3 Summon a Phosphorescent Chupacabra")
			@Config.Name("Spawns Phosphorescent Chupacabra")
			@Config.RequiresMcRestart
			public boolean chupacabraSummon = true;
		}

		public static class BossRahovartConfig {

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
			public int hellfireWallCleanupRefund = 50;

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

		public static class PotionEffectsConfig {

			@Config.Comment("Register Consumed Potion Effect")
			@Config.Name("Register Consumed")
			@Config.RequiresMcRestart
			public boolean registerConsumed = true;

			@Config.Comment("Consumed denies buffs being applied")
			@Config.Name("Consumed Buffs Denies")
			public boolean consumedDeniesBuffs = true;

			@Config.Comment("Consumed removes buffs when applied")
			@Config.Name("Consumed Buffs Removes")
			public boolean consumedRemovesBuffs = true;

			@Config.Comment("Consumed Max Health multiplied total to reduce")
			@Config.Name("Consumed Health Modifier")
			@Config.RequiresMcRestart
			@Config.RangeDouble(min = -1D, max = 0)
			public double consumedHealthModifier = -0.95D;

			@Config.Comment("Consumed applies item cooldown")
			@Config.Name("Consumed Item Cooldown Ticks")
			@Config.RangeInt(min = 0)
			public int consumedItemCooldown = 10;

			@Config.Comment("Consumed makes all damage piercing")
			@Config.Name("Consumed Piercing All")
			public boolean consumedPiecingAll = true;

			@Config.Comment("Consumed makes environmental damage piercing")
			@Config.Name("Consumed Piercing Environment")
			public boolean consumedPiecingEnvironment = false;

			@Config.Comment("Register Voided Potion Effect")
			@Config.Name("Register Voided")
			@Config.RequiresMcRestart
			public boolean registerVoided = true;

			@Config.Comment("Voided denies buffs being applied")
			@Config.Name("Voided Buffs Denies")
			public boolean voidedDeniesBuffs = true;

			@Config.Comment("Voided removes buffs when applied")
			@Config.Name("Voided Buffs Removes")
			public boolean voidedRemovesBuffs = false;

			@Config.Comment("Voided Max Health multiplied total to reduce")
			@Config.Name("Voided Health Modifier")
			@Config.RequiresMcRestart
			@Config.RangeDouble(min = -1D, max = 0)
			public double voidedHealthModifier = -0.1D;

			@Config.Comment("Voided applies item cooldown")
			@Config.Name("Voided Item Cooldown Ticks")
			@Config.RangeInt(min = 0)
			public int voidedItemCooldown = 0;

			@Config.Comment("Voided makes all damage piercing")
			@Config.Name("Voided Piercing All")
			public boolean voidedPiecingAll = false;

			@Config.Comment("Voided makes environmental damage piercing")
			@Config.Name("Voided Piercing Environment")
			public boolean voidedPiecingEnvironment = true;
		}

		public static class ImperfectSummoningConfig{

			@Config.Comment("Nerfs minions who are summoned without variant summoning knowledge, toggles the entire feature")
			@Config.Name("Imperfect Summoning")
			public boolean imperfectSummoning = true;

			@Config.Comment("Knowledge Rank to summon normal minions, updates client visuals except for lang files")
			@Config.Name("Imperfect Normal Summon Rank")
			@Config.RangeInt(min = 0)
			public int normalSummonRank = 1;

			@Config.Comment("Knowledge Rank to summon variant minions")
			@Config.Name("Imperfect Variant Summon Rank")
			@Config.RangeInt(min = 0)
			public int variantSummonRank = 2;

			@Config.Comment("Chance for imperfect minion to spawn with reduced defenses or offenses")
			@Config.Name("Imperfect Reduced Stat Summon Base Chance")
			@Config.RangeDouble(min = 0.0, max = 1.0)
			public double imperfectStatsBaseChance = 1.0D;

			@Config.Comment("Chance Reduction per point of creature knowledge")
			@Config.Name("Imperfect Reduced Stat Summon Chance Modifier")
			@Config.RangeDouble(min = 0.0)
			public double imperfectStatsChanceModifier = 0.001D;

			@Config.Comment("Chance for an imperfect minion to be hostile to the host")
			@Config.Name("Imperfect Hostile Summon Base Chance")
			@Config.RangeDouble(min = 0.0, max = 1.0)
			public double imperfectHostileBaseChance = 0.1D;

			@Config.Comment("Chance Reduction per point of creature knowledge")
			@Config.Name("Imperfect Hostile Summon Chance Modifier")
			@Config.RangeDouble(min = 0.0)
			public double imperfectHostileChanceModifier = 0.0D;
		}

		public static class PlayerMobLevelsConfig{

			@Config.Comment("Enable Capability to calculate a Mob Level associated to a player")
			@Config.Name("Player Mob Level Capability")
			@Config.RequiresMcRestart
			public boolean playerMobLevelCapability = true;

			@Config.Comment("Require a held main hand Soulgazer in order to summon a boss with additional levels")
			@Config.Name("Player Mob Level Boss Requires Soulgazer")
			public boolean pmlBossRequiresSoulgazer = true;

			@Config.Comment("Ratio of Player Mob Levels used for summon staff minions")
			@Config.Name("Player Mob Level Degree Summon")
			@Config.RangeDouble(min = 0.0)
			public double pmlSummonDegree = 0.2D;

			@Config.Comment("Ratio of Player Mob Levels used for soulbounds in limited dimensions")
			@Config.Name("Player Mob Level Degree Soulbound")
			@Config.RangeDouble(min = 0.0)
			public double pmlSoulboundDegree = 0.2D;

			@Config.Comment("Ratio of Player Mob Levels used when the main bosses finish spawning")
			@Config.Name("Player Mob Level Degree Main Boss")
			@Config.RangeDouble(min = 0.0)
			public double pmlBossMainDegree = 0.2D;

			@Config.Comment("Ratio of Player Mob Levels used for Boss Crystals when they spawn something")
			@Config.Name("Player Mob Level Degree Boss Crystal")
			@Config.RangeDouble(min = 0.0)
			public double pmlBossCrystalDegree = 0.2D;

			@Config.Comment("Ratio of Player Mob Levels used for Lycanites Spawners")
			@Config.Name("Player Mob Level Degree Spawner")
			@Config.RangeDouble(min = 0.0)
			public double pmlSpawnerDegree = 0.2D;

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
					"darkness",
					"death",
					"disruption",
					"sleep",
					"undeath"
			};

			@Config.Comment("PML Spawner Names is a blacklist instead of whitelist")
			@Config.Name("PML Spawner Names Blacklist")
			public boolean pmlSpawnerNameStringsIsBlacklist = false;
		}

		public static class EntityStoreCreatureConfig{

			@Config.Comment("Boss Crystals check if Player Mob Levels is higher than stored Entity's levels")
			@Config.Name("Boss Crystal Player Mob Levels")
			public boolean bossCrystalPML = true;

			@Config.Comment("Require a held main hand Soulgazer in order to scale to Player Mob Levels")
			@Config.Name("Boss Crystal Player Mob Levels Requires Soulgazer")
			public boolean bossCrystalSoulgazerHold = true;

			@Config.Comment("Enable logic to automatically release stored Entity (Checks every second)")
			@Config.Name("Boss Crystal Tick Checks")
			public boolean bossCrystalTickChecks = true;

			@Config.Comment("1/n Chance to despawn per second, set to 0 to disable random despawning (Forced Despawning happens when further than 128 blocks)")
			@Config.Name("Encounter Crystal Despawn Chance")
			@Config.RangeInt(min = 0)
			public int encounterCrystalDespawnChance = 0;

			@Config.Comment("Require a held main hand Soulgazer in order to scale to Player Mob Levels")
			@Config.Name("Encounter Crystal Player Mob Levels Requires Soulgazer")
			public boolean encounterCrystalSoulgazerHold = false;

			@Config.Comment("1/n chance to spawn, creature must have been idle for 600 ticks first, Requires Mixin 'Encounter Natural Spawn Summon Crystal'")
			@Config.Name("Encounter Crystal Spawn Chance")
			@Config.RangeInt(min = 1)
			public int encounterCrystalSpawnChance = 800;

			@Config.Comment("Tick Rate to attempt forcing a vanilla like despawn and store the creature, Requires Mixin 'Encounter Natural Spawn Summon Crystal'")
			@Config.Name("Encounter Crystal Spawn Tick Rate")
			@Config.RangeInt(min = 1)
			public int encounterCrystalSpawnTickRate = 20;

			@Config.Comment("Enable Capability to replicate LycanitesMobs PetEntry for any owner")
			@Config.Name("Entity Store Creature Capability")
			@Config.RequiresMcRestart
			public boolean entityStoreCreatureCapability = true;
		}

		public static class LootConfig {

			@Config.Comment("Minimum Level the creature must be to drop Random Charges")
			@Config.Name("Random Charge Loot Minimum Mob Level")
			@Config.RequiresMcRestart
			public int randomChargeMinimumMobLevel = 25;

			@Config.Comment("Minimum count per loot entry")
			@Config.Name("Random Charge Loot Minimum Count")
			@Config.RequiresMcRestart
			public int randomChargeScaledCountMinimum = 0;

			@Config.Comment("Maximum count per loot entry")
			@Config.Name("Random Charge Loot Maximum Count")
			@Config.RequiresMcRestart
			public int randomChargeScaledCountMaximum = 4;

			@Config.Comment("How many rolls per level, default is 0.1 so one roll for every 10 levels")
			@Config.Name("Random Charge Level Scale")
			@Config.RequiresMcRestart
			public float randomChargeLevelScale = 0.1F;

			@Config.Comment("Limit the number of items to drop, set to 0 to disable")
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

			@Config.Comment("Register Loot Tables for creatures dropping random charges of their element (This LootTable is dynamic)")
			@Config.Name("Register Random Charges Loot Tables")
			@Config.RequiresMcRestart
			public boolean registerRandomChargesLootTable = true;

			@Config.Comment("Register Loot Tables for any creature defined as SpawnedAsBoss (ex Dungeon/Altar)")
			@Config.Name("Register SpawnedAsBoss With Levels Loot Tables")
			@Config.RequiresMcRestart
			public boolean registerSpawnedAsBossWithLevelsLootTables = true;

			@Config.Comment("Register Has Mob Levels Loot Condition")
			@Config.Name("Register HasMobLevels Loot Condition")
			@Config.RequiresMcRestart
			public boolean registerPMLLootCondition = true;

			@Config.Comment("Register Scale With Mob Levels Loot Function")
			@Config.Name("Register ScaleWithMobLevels Loot Function")
			@Config.RequiresMcRestart
			public boolean registerPMLLootFunction = true;
		}

		public static class StatsConfig{

			@Config.Comment("Ratio of lycanites bonus Health bosses will receive, requires Mixin 'Boss Lower Health Scale'")
			@Config.Name("Boss Health Bonus Ratio")
			@Config.RangeDouble(min = 0)
			public double bossHealthBonusRatio = 0.1D;

			@Config.Comment("Ratio of max lycanites bonus movement defense, variants get more, set to 0 to disable the cap")
			@Config.Name("Cap Defense Ratio")
			@Config.RangeDouble(min = 0)
			public double capDefenseRatio = 4.0D;

			@Config.Comment("Ratio of max lycanites bonus movement speed, variants get more, set to 0 to disable the cap")
			@Config.Name("Cap Speed Ratio")
			@Config.RangeDouble(min = 0)
			public double capSpeedRatio = 3.0D;

			@Config.Comment("Ratio of max lycanites bonus effect duration, variants get more, set to 0 to disable the cap")
			@Config.Name("Cap Effect Duration Ratio")
			@Config.RangeDouble(min = 0)
			public double capEffectDurationRatio = 5.0D;

			@Config.Comment("Ratio of max lycanites bonus pierce, variants get more, set to 0 to disable the cap")
			@Config.Name("Cap Pierce Ratio")
			@Config.RangeDouble(min = 0)
			public double capPierceRatio = 3.0D;
		}
	}

	public static class featuresConfig {

		/*
		*
		* Features, no plans on porting Vanilla Mixins into Lycanites
		*
		 */

		@Config.Comment("Fix Iron Golems attacking tamed mobs")
		@Config.Name("Fix Golems Attacking Tamed Mobs (Vanilla)")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.vanillairongolemtargettamed.json")
		public boolean ironGolemsTamedTarget = true;

		@Config.Comment("Fix Withers attacking Tremors")
		@Config.Name("Fix Withers Attacking Tremors (Vanilla)")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.vanillawithertargettremor.json")
		public boolean witherTargetTremor = true;

		@Config.Comment("Add Modded Fires to the specific Blocks.FIRE check made by the World")
		@Config.Name("Lycanites Fire Extinguish (Vanilla)")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.vanillaextinguishmoddedfire.json")
		public boolean lycanitesFireExtinguish = true;

		@Config.Comment("Whether a Smited LivingEntityBase is undead if method inherited (often overridden)")
		@Config.Name("Most Smited Are Undead (Vanilla)")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.vanillasmitedundeadlivingbase.json")
		public boolean smitedMakesMostUndead = true;

		/*
		 *
		 * Features that I feel are otherwise out of scope of LycanitesMobs, can change of course
		 *
		 */

		@Config.Comment("Have LycanitesMobs check and load resources from LycanitesTweaks")
		@Config.Name("Add LycanitesTweaks default JSON")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinjectdefaultjsonloading.json")
		public boolean addLycanitesTweaksDefaultJSON = true;

		@Config.Comment("Store Altar Mini Bosses in a Summon Crystal Entity to interact with Player Mob Levels")
		@Config.Name("Altar Mini Boss Spawn Summon Crystal (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurealtarminibosscrystal.json")
		public boolean altarMiniBossSpawnCrystal = true;

		@Config.Comment("Giving an Enchanted Golden Apple to a tamed creature will turn it into a baby")
		@Config.Name("Baby Age Gapple")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretamedbabygapple.json")
		public boolean babyAgeGapple = true;

		@Config.Comment("Bleed damage uses setDamageIsAbsolute ontop of Magic/Armor ignoring")
		@Config.Name("Bleed Pierces")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebleedpierces.json")
		public boolean bleedPierces = true;

		@Config.Comment("Move the Damage Limit DPS calc to LivingHurtEvent LOWEST from attackEntityFrom")
		@Config.Name("Boss DPS Limit Recalc")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossdamagelimitdpsrecalc.json")
		public boolean bossDPSLimitRecalc = true;

		@Config.Comment("Invert bonus Health/Damage level scale for the Boss Group")
		@Config.Name("Boss Invert Health and Damage Scale")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinvertbossdamagehealthscale.json")
		public boolean bossInvertHealthDamageScale = true;

		@Config.Comment("Main Boss HP level bonus scaled down via config")
		@Config.Name("Boss Lower Health Scale")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossbonushealthmodifier.json")
		public boolean bossLowerHealthScale = true;

		@Config.Comment("Tweaks to Amalgalich with additional config options")
		@Config.Name("Boss Tweaks Amalgalich")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossamalgalich.json")
		public boolean bossTweaksAmalgalich = true;

		@Config.Comment("Tweaks to Asmodeus with additional config options")
		@Config.Name("Boss Tweaks Asmodeus")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossasmodeustweaks.json")
		public boolean bossTweaksAsmodeus = true;

		@Config.Comment("Tweaks to Rahovart with additional config options")
		@Config.Name("Boss Tweaks Rahovart")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossrahovarttweaks.json")
		public boolean bossTweaksRahovart = true;

		@Config.Comment("Add configurable caps to creature speed, effect durations, and pierce")
		@Config.Name("Cap Specific Creature Stats")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurecreaturestatbonuscap.json")
		public boolean capSpecificStats = true;

		@Config.Comment("Make offhand crafted equipment RMB ability require player to be sneaking")
		@Config.Name("Crafted Equipment Offhand RMB Needs Sneak")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentrmbneedssneak.json")
		public boolean craftedEquipmentOffhandRMBSneak = true;

		@Config.Comment("Allows Sword Enchantments and Efficiency, provides config option for requiring leveled parts")
		@Config.Name("Crafted Equipment Enchantments")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureequipmentswordenchantments.json")
		public boolean craftedEquipmentEnchantments = true;

		@Config.Comment("Enable customizable effect list and handling for the cleansed/immunization effect")
		@Config.Name("Customizable Curing Item Effects List")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurescustomcureitemlist.json")
		public boolean customItemCureEffectList = true;

		@Config.Comment("Store Dungeon Bosses in a Summon Crystal Entity to interact with Player Mob Levels")
		@Config.Name("Dungeon Boss Spawn Summon Crystal (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuredungeonbosscrystal.json")
		public boolean dungeonBossSpawnCrystal = true;

		@Config.Comment("Attempt a vanilla like despawn and store the creature in a Crystal Entity to interact with Player Mob Levels")
		@Config.Name("Encounter Natural Spawn Summon Crystal (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurenaturalencountercrystal.json")
		public boolean encounterNaturalSpawnCrystal = true;

		@Config.Comment("When reading familiars from URL, Set Spawning Active to false")
		@Config.Name("Familiars Inactive On Join")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurefamiliarsinactiveonjoin.json")
		public boolean familiarsInactiveOnJoin = true;

		@Config.Comment("Enable customizable biome list for Arisaurs with the custom name Flowersaur")
		@Config.Name("Flowersaurs Naturally Spawn")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresflowersaurspawning.json")
		public boolean flowersaurSpawningBiomes = true;

		@Config.Comment("Summon minion goal matches host and minion levels (AI Goal)")
		@Config.Name("Level match minions goal")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureaiminionhostlevelmatch.json")
		public boolean levelMatchMinionsGoal = true;

		@Config.Comment("Host entity summons minions at matching levels (Hard Coded)")
		@Config.Name("Level match minions host method")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebasecreatureminionhostlevelmatch.json")
		public boolean levelMatchMinionsHostMethod = true;

		@Config.Comment("Fix explosion damage being reduced to 1, pair with early Mixin 'Lycanites Fire Extinguish (Vanilla)")
		@Config.Name("Lycanites Fire No Break Collision")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurelycanitesfirepassable.json")
		public boolean lycanitesFiresNoBreakCollision = true;

		@Config.Comment("Whether a Smited BaseCreatureEntity should be considered undead")
		@Config.Name("Lycanites Smited Are Undead")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresmitedundeadbasecreature.json")
		public boolean smitedMakesBaseCreatureUndead = true;

		@Config.Comment("Invert bonus Health/Damage level scale for Hostile Minion Creatures")
		@Config.Name("Minion Invert Health and Damage Scale")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinverthostileminiondamagehealthscale.json")
		public boolean minionInvertHealthDamageScale = true;

		@Config.Comment("Allow mounts to be use vanilla saddles under certain conditions (has configs)")
		@Config.Name("Mounts can use Vanilla saddles with limitations")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurelimitedvanillasaddle.json")
		public boolean mountVanillaSaddleLimited = true;

		@Config.Comment("Allow the pet perch position to be modifiable via config")
		@Config.Name("Perch Position Modifiable")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureperchposition.json")
		public boolean perchPositionModifiable = true;

		@Config.Comment("Add distance checks to pickup mobs teleporting victims")
		@Config.Name("Pickup Checks Distances")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureentitypickupfix.json")
		public boolean pickupChecksDistance = true;

		@Config.Comment("Adds more parity to Repulsion and Weight, repulsion gains weights benefits")
		@Config.Name("Repulsion Weight Benefits")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurerepulsionweight.json")
		public boolean repulsionWeight = true;

		@Config.Comment("Feeding tamed creatures Burritos and Risottos will increase/decrease size scale")
		@Config.Name("Size Change Foods")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretamedsizechangefood.json")
		public boolean sizeChangeFoods = true;

		@Config.Comment("Make Soul Gazing a creature riding an entity dismount and attack the player")
		@Config.Name("Soul Gazer Dismounts")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresoulgazerdismounts.json")
		public boolean soulGazerDismounts = true;

		@Config.Comment("Enable setting owned creature and animal variant status with Soul Keys")
		@Config.Name("Soulkeys Set Variant")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresoulkeyvariantset.json")
		public boolean soulkeysSetVariant = true;

		@Config.Comment("Rework summon progression to allow summoning weaker minions at low knowledge")
		@Config.Name("Summon Progression Rework")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonrework.json")
		public boolean summonProgressionRework = true;

		@Config.Comment("Allow creatures to be tamed/studied with their healing foods (has configs)")
		@Config.Name("Tame Creatures with their healing food")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretamewithhealingfood.json")
		public boolean tameWithHealingFood = true;

		@Config.Comment("Invert bonus Health/Damage level scale for Tamed Creatures")
		@Config.Name("Tamed Invert Health and Damage Scale")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinverttameddamagehealthscale.json")
		public boolean tamedInvertHealthDamageScale = true;

		@Config.Comment("Enable whether all tamed (tamed/summoned/soulbound) variants get stats bonuses")
		@Config.Name("Tamed Variant Stat Bonuses")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurealltamedvariantstats.json")
		public boolean tamedVariantStats = true;

		@Config.Comment("Feeding Treats will prevent natural and forced despawning")
		@Config.Name("Treat Sets Persistence")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretameabletreatpersistence.json")
		public boolean treatSetsPersistence = true;

		@Config.Comment("Use Player Mob Level to affect the main Bosses")
		@Config.Name("Player Mob Level Bosses (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossesplayermoblevels.json")
		public boolean playerMobLevelMainBosses = true;

		@Config.Comment("Use Player Mob Level to affect JSON Spawners by whitelist")
		@Config.Name("Player Mob Level JSON Spawner (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurejsonspawnerplayermoblevels.json")
		public boolean playerMobLevelJSONSpawner = true;

		@Config.Comment("Use Player Mob Level to limit soulbounds in specified dimensions")
		@Config.Name("Player Mob Level Soulbound Limit Dims (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurelimitedbounddimensions.json")
		public boolean playerMobLevelSoulboundLimitedDimensions = true;

		@Config.Comment("Use Player Mob Level to affect summon staff minions")
		@Config.Name("Player Mob Level Summon Staff (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstaffplayermoblevel.json")
		public boolean playerMobLevelSummonStaff = true;

		@Config.Comment("Lycanites Creatures can use JSON loot tables alongside LycanitesMobs drop list")
		@Config.Name("Vanilla BaseCreatureEntity Loot Table")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurecreaturevanillaloottables.json")
		public boolean vanillaBaseCreatureLootTable = true;

		@Config.Comment("Fix Potion Core forcibly overwriting BaseCreatureEntity motionY ")
		@Config.Name("Mod Compatibility: Potion Core Jump Fix (PotionCore)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "potioncore")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.potioncorejumpfix.json")
		public boolean potionCoreJumpFix = true;

		/*
		*
		* Temporary Mod Compatibility Mixins, things I should really make a PR for
		*
		 */

		@Config.Comment("Makes Crafted Equipment reach stat influence ReachFix attack range")
		@Config.Name("Mod Compatibility: Crafted Equipment ReachFix (ReachFix)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "reachfix")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentreachfix.json")
		public boolean craftedEquipmentReachFix = true;

		@Config.Comment("Cancels Custom Sweep and rehandle with RLCombat Sweep")
		@Config.Name("Mod Compatibility: Crafted Equipment RLCombat Sweep (RLCombat)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "bettercombatmod")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentrlcombatsweep.json")
		public boolean craftedEquipmentRLCombatSweep = true;
	}

	public static class PatchConfig {

		/*
		 *
		 * Temporary Mixin Patches, intended to become PR in LycanitesMobs
		 *
		 */

		@Config.Comment("Disables Soul Bounds using portals, which would kill them and set respawn cooldown")
		@Config.Name("Disable Soul Bounds Using Portals")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchessoulboundnoportal.json")
		public boolean soulBoundNoPortal = true;

		@Config.Comment("Fix hostile AgeableCreature babies not dropping loot")
		@Config.Name("Fix AgeableCreature baby drops")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesageablebabydrops.json")
		public boolean fixAgeableBabyDrops = true;

		@Config.Comment("Add persistence to summons via BaseCreature method")
		@Config.Name("Fix BaseCreature Summon Persistence")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesbasecreatureminionpersistence.json")
		public boolean fixBaseCreatureSummonPersistence = true;

		@Config.Comment("Add a call to super in BaseCreature's isPotionApplicable method")
		@Config.Name("Fix BaseCreature Potion Applicable")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesbasecreaturepotionapplicable.json")
		public boolean fixBaseCreaturePotionApplicableSuper = true;

		@Config.Comment("Fix divide by zero crash in FireProjectilesGoal and RangedSpeed of zero preventing attacks")
		@Config.Name("Fix Creature Ranged Speed")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesrangedspeeddividebyzero.json")
		public boolean fixRangedSpeedDivideZero = true;

		@Config.Comment("Fix Ettin checking for inverted griefing flag")
		@Config.Name("Fix Ettin grief flag")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesettingriefflag.json")
		public boolean fixEttinBlockBreak = true;

		@Config.Comment("Fix Fear checking for creative capabilities instead of flight")
		@Config.Name("Fix Fear Survival Flying")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesfearsurvivalflying.json")
		public boolean fixFearSurvivalFlying = true;

		@Config.Comment("Fix HealWhenNoPlayersGoal trigger check using AND instead of OR")
		@Config.Name("Fix Heal Goal Check")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patcheshealgoalcheck.json")
		public boolean fixHealGoalCheck = true;

		@Config.Comment("Fix Serpix Blizzard projectile spawning in the ground")
		@Config.Name("Fix Serpix Blizzard Offset")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patcheserpixblizzardoffset.json")
		public boolean fixSerpixBlizzardOffset = true;
	}

	public static HashSet<ResourceLocation> getCleansedCureEffects(){
		if(ForgeConfigHandler.cleansedCureEffects == null){
			HashSet<ResourceLocation> list = new HashSet<>();
			for(String string: ForgeConfigHandler.server.cleansedEffectsToCure){
				list.add(new ResourceLocation(string));
			}
			ForgeConfigHandler.cleansedCureEffects = list;
		}
		return ForgeConfigHandler.cleansedCureEffects;
	}

	public static HashSet<ResourceLocation> getFlowersaurBiomes(){
		if(ForgeConfigHandler.flowersaurBiomes == null){
			HashSet<ResourceLocation> list = new HashSet<>();
			for(String string: ForgeConfigHandler.server.flowersaurSpawningBiomes){
				list.add(new ResourceLocation(string));
			}
			ForgeConfigHandler.flowersaurBiomes = list;
		}
		return ForgeConfigHandler.flowersaurBiomes;
	}

	public static HashSet<ResourceLocation> getImmunizationCureEffects(){
		if(ForgeConfigHandler.immunizationCureEffects == null){
			HashSet<ResourceLocation> list = new HashSet<>();
			for(String string: ForgeConfigHandler.server.immunizationEffectsToCure){
				list.add(new ResourceLocation(string));
			}
			ForgeConfigHandler.immunizationCureEffects = list;
		}
		return ForgeConfigHandler.immunizationCureEffects;
	}

	public static boolean isDimensionLimitedMinion(int dim){
		boolean found = false;
		for(int id : ForgeConfigHandler.server.pmlConfig.pmlMinionLimitDimIds){
			if (dim == id) {
				found = true;
				break;
			}
		}
		return ForgeConfigHandler.server.pmlConfig.pmlMinionLimitDimIdsWhitelist == found;
	}

	public static HashSet<String> getPMLSpawnerNames(){
		if(ForgeConfigHandler.pmlSpawnerNames == null){
			ForgeConfigHandler.pmlSpawnerNames = new HashSet<>(Arrays.asList(ForgeConfigHandler.server.pmlConfig.pmlSpawnerNameStrings));
		}
		return ForgeConfigHandler.pmlSpawnerNames;
	}

	@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(LycanitesTweaks.MODID)) {
				ForgeConfigHandler.cleansedCureEffects = null;
				ForgeConfigHandler.flowersaurBiomes = null;
				ForgeConfigHandler.pmlSpawnerNames = null;
				ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
			}
		}
	}
}