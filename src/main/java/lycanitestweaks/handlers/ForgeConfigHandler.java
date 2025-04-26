package lycanitestweaks.handlers;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.handlers.config.*;
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

		@Config.Comment("Test Float")
		@Config.Name("Test Float")
		public float testFloat = 1.0F;
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

		@Config.Comment("Should Lycanites Block Protection protect against any Living Entity")
		@Config.Name("Block Protection Living Event")
		@Config.RequiresMcRestart
		public boolean blockProtectionLivingEvent = true;

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

			/*
				There are three categories that may overlap but all use the BOSS_DAMAGE_LIMIT mechanic
				1. Always Bosses (Boss Group)
				2. Spawned As Bosses (NBT Boss)
				3. Rare Variants (Natural Boss)

				Provided Boss configs only cover 1 and 2, and assumes 3 will never be level scaled
				Lower Health Bonus Options exists so BOSS_DAMAGE_LIMIT does create mobs with insane time to kill

				A natural rare variant can have different stat modifiers from a SpawnedAsBoss version,
				LycanitesTweaks turns all altar bosses in SpawnedAsBoss mobs for example
			 */

			@Config.Comment("Ratio of lycanites bonus Health main bosses will receive, requires Mixin 'Boss Lower Health Scale'")
			@Config.Name("Boss Health Bonus Ratio")
			@Config.RangeDouble(min = 0)
			public double bossHealthBonusRatio = 0.25D;

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

			@Config.Comment("Ratio of lycanites bonus Health bosses will receive, requires Mixin 'Boss Lower Health Scale'")
			@Config.Name("Spawned As Boss Health Bonus Ratio")
			@Config.RangeDouble(min = 0)
			public double spawnedAsBossHealthBonusRatio = 0.5D;

//			@Config.Comment("Apply 'Boss Health Bonus Ratio' to variants/dungeon bosses instead of just the main Bosses")
//			@Config.Name("Spawned As Boss Health Bonus Ratio")
//			public boolean spawnedAsBossHealthBonusRatio = false;

			@Config.Comment("Apply Mixin 'Boss Invert Health and Damage Scale' to variants/dungeon bosses instead of just the main Bosses")
			@Config.Name("Spawned As Boss Invert Health and Damage Scale")
			public boolean spawnedAsBossInvert = true;
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

		@Config.Comment("If BaseCreatureEntity isBoss and dies, kill minions and projectiles")
		@Config.Name("Boss Death Kills Minions and Projectiles")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossdeathminionprojectiles.json")
		public boolean bossDeathKillMinionProjectile = true;

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

		@Config.Comment("SpawnedAsBoss and Main Boss HP level bonus scaled down via config")
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

		@Config.Comment("Lycanites Pet Manager updates Player Mob Level Capability with pet entry information")
		@Config.Name("Pet Manager Tracks Pet Levels (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurepetmanagerpmltrackspetlevels.json")
		public boolean petManagerTracksHighestLevelPet = true;

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

		@Config.Comment("Invert bonus Health/Damage level scale for Tamed Creatures")
		@Config.Name("Tamed Invert Health and Damage Scale")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinverttameddamagehealthscale.json")
		public boolean tamedInvertHealthDamageScale = true;

		@Config.Comment("Remove treat pacifying and lower reputation gain when taming high leveled creatures (has configs) ")
		@Config.Name("Tamed Invert Over Leveled Penalty")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretameoverlevelpenalty.json")
		public boolean tamedOverLeveledPenalty = true;

		@Config.Comment("Enable whether all tamed (tamed/summoned/soulbound) variants get stats bonuses")
		@Config.Name("Tamed Variant Stat Bonuses")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurealltamedvariantstats.json")
		public boolean tamedVariantStats = true;

		@Config.Comment("Allow creatures to be tamed/studied with their healing foods (has configs)")
		@Config.Name("Tame Creatures with their healing food")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretamewithhealingfood.json")
		public boolean tameWithHealingFood = true;

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