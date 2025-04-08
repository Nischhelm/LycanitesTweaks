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
		@Config.Name("Lycanites Tweaks Amalgalich")
		public final BossAmalgalichConfig amalgalichConfig = new BossAmalgalichConfig();

		@Config.Comment("Requires 'Boss Tweaks Asmodeus' Mixin enabled")
		@Config.Name("Lycanites Tweaks Asmodeus")
		public final BossAsmodeusConfig asmodeusConfig = new BossAsmodeusConfig();

		@Config.Comment("Requires 'Boss Tweaks Rahovart' Mixin enabled")
		@Config.Name("Lycanites Tweaks Rahovart")
		public final BossRahovartConfig rahovartConfig = new BossRahovartConfig();

		@Config.Name("Lycanites Tweaks Potion Effects")
		public final PotionEffectsConfig effectsConfig = new PotionEffectsConfig();

		@Config.Name("Lycanites Tweaks Imperfect Summoning")
		public final ImperfectSummoningConfig imperfectSummoningConfig = new ImperfectSummoningConfig();

		@Config.Name("Lycanites Tweaks Player Mob Levels")
		public final PlayerMobLevelsConfig pmlConfig = new PlayerMobLevelsConfig();

		@Config.Name("Lycanites Tweaks Creature Stats")
		public final StatsConfig statsConfig = new StatsConfig();

		@Config.Comment("Do an instance check for BlockFire with Mixin 'Lycanites Fire Extinguish (Vanilla)'")
		@Config.Name("Only Check Lycanites Fire Punchable")
		public boolean onlyCheckLycanitesFire = false;

		@Config.Comment("Distance between entities to trigger auto pickup drop")
		@Config.Name("Pick Up Distance")
		@Config.RangeDouble(min = 0)
		public double pickUpDistance = 32.0D;

		@Config.Comment("Perch Angle In Radians")
		@Config.Name("Perch Angle Radians")
		@Config.RangeDouble(min = -360, max = 360)
		public double perchAngle = 90.0D;

		@Config.Comment("Perch Distance Scale, based on ridden entity, Default Lycanites is 0.7")
		@Config.Name("Perch Distance")
		@Config.RangeDouble(min = 0)
		public double perchDistance = 1.0D;

		@Config.Comment("Max degree for size change food based on lycanitesmobs config range")
		@Config.Name("Tamed Size Change Degree")
		@Config.RangeDouble(min = 0.0)
		public double sizeChangeDegree = 0.1D;

		@Config.Comment("Minimum level all parts of equipment must be in order to apply Sword Enchantments")
		@Config.Name("Equipment Minimum Level for to sword Enchantments")
		public int equipmentMinLevelEnchantable = 0;

		@Config.Comment("List of potion resource locations cleansed will cure")
		@Config.Name("Cleansed Effects To Cure")
		public String[] cleansedEffectsToCure = {
				"minecraft:wither",
				"minecraft:unluck",
				"lycanitesmobs:fear",
				"lycanitesmobs:insomnia",
				"lycanitesmobs:decay"
		};

		@Config.Comment("List of potion resource locations immunization will cure")
		@Config.Name("Immunization Effects To Cure")
		public String[] immunizationEffectsToCure = {
				"minecraft:poison",
				"minecraft:hunger",
				"minecraft:weakness",
				"minecraft:nausea",
				"lycanitesmobs:paralysis"
		};

		@Config.Comment("List of biome resource locations where custom name Arisaurs will spawn in")
		@Config.Name("Biomes Flowersaurs Spawn In")
		public String[] flowersaurSpawningBiomes = {
				"minecraft:mutated_forest",
				"biomesoplenty:mystic_grove",
				"twilightforest:enchanted_forest"
		};

		public static class BossAmalgalichConfig {

			@Config.Comment("Projectile to replace main 'spectralbolt' auto attack (targeted/all)")
			@Config.Name("Main Projectile Name")
			@Config.RequiresMcRestart
			public String mainProjectile = "lichspectralbolt";

			@Config.Comment("Add AI task for targeted attack to use alongside auto attacks")
			@Config.Name("Targeted Projectile Attack")
			@Config.RequiresMcRestart
			public boolean targetedAttack = true;

			@Config.Comment("Projectile to used for targeted attack")
			@Config.Name("Targeted Projectile Name")
			public String targetedProjectile = "lichshadowfire";

			@Config.Comment("Consumption in any phases, goal/animation shared across the fight")
			@Config.Name("Consumption All Phases")
			@Config.RequiresMcRestart
			public boolean consumptionAllPhases = true;

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

			@Config.Comment("Custom value to shadow fire extinguish width on death")
			@Config.Name("Custom Epion Extinguish Width")
			public int customEpionExtinguishWidth = 10;

			@Config.Comment("Replace Lob Darkling with Summon Goal, as a high level Amalgalich spams too much")
			@Config.Name("Lob Darklings Replace")
			@Config.RequiresMcRestart
			public boolean replaceLobDarkling = true;

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

			@Config.Comment("Add AI Ranged auto attacking (targeted/all) for an additional projectile")
			@Config.Name("Additional Projectile Register")
			@Config.RequiresMcRestart
			public boolean additionalProjectileAdd = true;

			@Config.Comment("Projectile to use for auto attacking")
			@Config.Name("Additional Projectile Name")
			@Config.RequiresMcRestart
			public String additionalProjectile = "demonicchaosorb";

			@Config.Comment("Fixes double damage and has better player Xray")
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

			@Config.Comment("Whether Devilstar Stream can be used outside Phase 1")
			@Config.Name("Devilstar Stream All Phases")
			public boolean devilstarStreamAllPhases = true;

			@Config.Comment("Whether Astaroth Minions are teleported away on spawn")
			@Config.Name("Astaroths Teleport Adjacent Node")
			public boolean astarothsTeleportAdjacent = true;

			@Config.Comment("Whether Astaroth Minions use Rare/Boss Damage Limit")
			@Config.Name("Astaroths Boss Damage Limit")
			public boolean astarothsUseBossDamageLimit = true;

			@Config.Comment("Hell Shield Exponent for base ATTACK_DAMAGE to damage shield with")
			@Config.Name("Hell Shield Damage Power")
			@Config.RangeInt(min = 1)
			public int hellShieldDamagePower = 3;

			@Config.Comment("Hell Shield Health for 100% Damage Reduction")
			@Config.Name("Hell Shield Maximum Health")
			@Config.RangeInt(min = 1)
			public int hellShieldHealthMax = 20000;

			@Config.Comment("Hell Shield Overheal to extend full power shield")
			@Config.Name("Hell Shield Overheal Ratio")
			@Config.RangeDouble(min = 1D)
			public double hellShieldOverhealRatio = 2.0D;

			@Config.Comment("Hell Shield Max Health Regenerated by Astaroths")
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

			@Config.Comment("Tick Maximum Lifespan for Belphs and Behemoths. Set to 0 to disable")
			@Config.Name("Minion Temporary Duration")
			@Config.RangeInt(min = 0)
			public int minionTemporaryDuration = 1200;

			@Config.Comment("Minimum spawn range for only Belphs and Behemoths")
			@Config.Name("Minion Spawn Range Minimum")
			@Config.RangeInt(min = 0, max = 35)
			public int minionSpawnRangeMin = 15;

			@Config.Comment("Maximum spawn range for only Belphs and Behemoths")
			@Config.Name("Minion Spawn Range Maximum")
			@Config.RangeInt(min = 0, max = 35)
			public int minionSpawnRangeMax = 35;

			@Config.Comment("Specifies Hellfire Walls to clear away from Rahovart")
			@Config.Name("Hellfire Wall Displacement")
			@Config.RangeInt(min = 0, max = 4)
			public int hellfireWallDisplacement = 2;

			@Config.Comment("Specifies Tick Duration of Hellfire Walls")
			@Config.Name("Hellfire Wall Duration")
			@Config.RangeInt(min = 0)
			public int hellfireWallTimeMax = 800;

			@Config.Comment("Specifies Hellfire Barriers to clear away from Rahovart")
			@Config.Name("Hellfire Barrier Displacement")
			@Config.RangeInt(min = 0, max = 4)
			public int hellfireBarrierDisplacement = 2;

			@Config.Comment("Specifies Hellfire Barriers degration per Belph kill (Lycanites uses 50/100)")
			@Config.Name("Hellfire Barrier Belph Degrade")
			@Config.RangeInt(min = 0, max = 100)
			public int hellfireBarrierBelphDegrade = 25;

			@Config.Comment("Specifies Hellfire Barriers degration per Behemoth kill (Lycanites uses 100/100)")
			@Config.Name("Hellfire Barrier Behemoth Degrade")
			@Config.RangeInt(min = 0, max = 100)
			public int hellfireBarrierBehemothDegrade = 50;

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

			@Config.Comment("Nerfs minions who are summoned without variant summoning knowledge")
			@Config.Name("Imperfect Summoning")
			public boolean imperfectSummoning = true;

			@Config.Comment("Knowledge Rank to summon normal minions")
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

			@Config.Comment("Ratio of Player Mob Level used to set for summon staff minions")
			@Config.Name("Player Mob Level Degree Summon")
			@Config.RangeDouble(min = 0.0)
			public double pmlSummonDegree = 0.2D;

			@Config.Comment("Ratio of Player Mob Level used to set for soulbounds in limited dimensions")
			@Config.Name("Player Mob Level Degree Soulbound")
			@Config.RangeDouble(min = 0.0)
			public double pmlSoulboundDegree = 0.2D;

			@Config.Comment("Ratio of Player Mob Level used to set for summon hostile bosses")
			@Config.Name("Player Mob Level Degree Boss")
			@Config.RangeDouble(min = 0.0)
			public double pmlBossDegree = 0.2D;

			@Config.Comment("Ratio of Player Mob Level used to set for Lycanites Spawners")
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

			@Config.Comment("Register Loot Tables for Amalgalich, Asmodeus, and Rahovart that are scaled to Mob Levels")
			@Config.Name("Register Boss With Levels Loot Tables")
			@Config.RequiresMcRestart
			public boolean registerBossWithLevelsLootTables = true;

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

			@Config.Comment("Ratio of lycanites bonus Health bosses will receive")
			@Config.Name("Boss Health Bonus Ratio")
			@Config.RangeDouble(min = 0)
			public double bossHealthBonusRatio = 0.1D;

			@Config.Comment("Ratio of max lycanites bonus movement defense, variants get more, set to 0 to disable")
			@Config.Name("Cap Defense Ratio")
			@Config.RangeDouble(min = 0)
			public double capDefenseRatio = 4.0D;

			@Config.Comment("Ratio of max lycanites bonus movement speed, variants get more, set to 0 to disable")
			@Config.Name("Cap Speed Ratio")
			@Config.RangeDouble(min = 0)
			public double capSpeedRatio = 3.0D;

			@Config.Comment("Ratio of max lycanites bonus effect duration, variants get more, set to 0 to disable")
			@Config.Name("Cap Effect Duration Ratio")
			@Config.RangeDouble(min = 0)
			public double capEffectDurationRatio = 5.0D;

			@Config.Comment("Ratio of max lycanites bonus pierce, variants get more, set to 0 to disable")
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

		@Config.Comment("Fix Golems attacking tamed mobs")
		@Config.Name("Fix Golems Attacking Tamed Mobs (Vanilla)")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.vanillairongolemtargettamed.json")
		public boolean ironGolemsTamedTarget = true;

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

		@Config.Comment("Invert bonus Health/Damage level scale for Bosses")
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

		@Config.Comment("Allows Crafted Equipment to use Sword Enchantments")
		@Config.Name("Crafted Equipment Sword Enchantments")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureequipmentswordenchantments.json")
		public boolean craftedEquipmentSwordEnchantments = true;

		@Config.Comment("Enable customizable effect list and handling for the cleansed/immunization effect")
		@Config.Name("Customizable Curing Item Effects List")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurescustomcureitemlist.json")
		public boolean customItemCureEffectList = true;

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

		@Config.Comment("Set noBreakCollision, pair with early Mixin 'Lycanites Fire Extinguish (Vanilla)")
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

		@Config.Comment("Allow the pet perch position to be modifiable via config")
		@Config.Name("Perch Position Modifiable")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureperchposition.json")
		public boolean perchPositionModifiable = true;

		@Config.Comment("Add distance checks to pickup mobs teleporting vicitims")
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

		@Config.Comment("Use Player Mob Level to affect Soul Key Bosses")
		@Config.Name("Player Mob Level Bosses (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossesplayermoblevels.json")
		public boolean playerMobLevelBosses = true;

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

		/*
		*
		* Temporary Mod Compatibility Mixins, things I should really make a PR for
		*
		 */

		@Config.Comment("Makes Crafted Equipment reach stat influence ReachFix attack range")
		@Config.Name("Crafted Equipment ReachFix (ReachFix)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentreachfix.json")
		public boolean craftedEquipmentReachFix = false;

		@Config.Comment("Cancels Crafted Sweep and rehandle with RLCombat Sweep")
		@Config.Name("Crafted Equipment RLCombat Sweep (RLCombat)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentrlcombatsweep.json")
		public boolean craftedEquipmentRLCombatSweep = false;
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