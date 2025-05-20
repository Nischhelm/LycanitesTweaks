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
import java.util.HashMap;
import java.util.HashSet;


@Config(modid = LycanitesTweaks.MODID)
public class ForgeConfigHandler {

	private static HashSet<ResourceLocation> cleansedCureEffects = null;
	private static HashSet<ResourceLocation> flowersaurBiomes = null;
	private static HashSet<ResourceLocation> immunizationCureEffects = null;
	private static HashSet<String> pmlSpawnerNames = null;
	private static HashMap<String, Integer> effectsApplyScaleLevelLimited = null;
//	private static HashMap<String, Integer> elementsLevelLimitedBuffs = null;
	private static HashMap<String, Integer> elementsLevelLimitedDebuffs = null;

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Enable/Disable Client Tweaks")
	@Config.Name("Toggle Client Mixins")
	@MixinConfig.SubInstance
	public static final clientFeaturesConfig clientFeaturesMixinConfig = new clientFeaturesConfig();

	@Config.Comment("Enable/Disable Tweaks")
	@Config.Name("Toggle Mixins")
	@MixinConfig.SubInstance
	public static final featuresConfig featuresMixinConfig = new featuresConfig();



	@Config.Comment("Enable/Disable Patches")
	@Config.Name("Toggle Patches")
	@MixinConfig.SubInstance
	public static final PatchConfig mixinPatchesConfig = new PatchConfig();

	public static class ClientConfig {

		@Config.Comment("Enable Debug logging for auto information dumping")
		@Config.Name("Enables debug logging automatic")
		public boolean debugLoggerAutomatic = false;

		@Config.Comment("Enable Debug logging for manually triggered information")
		@Config.Name("Enables debug logging")
		public boolean debugLoggerTrigger = false;

		@Config.Comment("Enable Debug logging for constant tick information dumping")
		@Config.Name("Enables debug logging tick")
		public boolean debugLoggerTick = false;

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

		@Config.Comment("Translate Client Text from data that is normally stored in English (such as NBT) instead of displaying the raw string")
		@Config.Name("Translate Text When Possible")
		public boolean translateWhenPossible = true;
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

		@Config.Name("Entity Store Creature")
		public final EntityStoreCreatureConfig escConfig = new EntityStoreCreatureConfig();

		@Config.Name("Items")
		public final ItemConfig itemConfig = new ItemConfig();

		@Config.Name("Level Scaled Loot")
		public final LootConfig lootConfig = new LootConfig();

		@Config.Comment("Requires 'Cap Specific Creature Stats' or 'Boss Lower Health Scale' Mixin enabled")
		@Config.Name("Creature Stats")
		public final StatsConfig statsConfig = new StatsConfig();

		@Config.Comment("Whether Mixin 'Boss DPS Limit Recalc' should also set damage amount on LivingDamageEvent LOWEST, this is assumed to fix one-shot death desyncs")
		@Config.Name("Boss Damage Limit Reduces Amount")
		public boolean bossDamageLimitReducesAmount = true;

		@Config.Comment("Whether Lycanites Block Protection protect against any Living Entity")
		@Config.Name("Block Protection Living Event")
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

		@Config.Comment("Max size change amount based on lycanitesmobs config range, default is 0.1, or 10% of config range. Requires Mixin 'Size Change Foods'")
		@Config.Name("Tamed Size Change Degree")
		@Config.RangeDouble(min = 0.0)
		public double sizeChangeDegree = 0.1D;

		@Config.Comment("Chance for a successful attempt to tame with healing food. Requires Mixin 'Tame Creatures with their healing food'")
		@Config.Name("Tamed With Healing Food Chance")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float tameWithFoodChance = 0.3F;

		@Config.Comment("Allow flying creatures to be tamed with healing food")
		@Config.Name("Tame With Food Allow Flying")
		public boolean tamedWithFoodAllowFlying = false;

		@Config.Comment("In order to use a vanilla saddle, the mount must be at least this level. Requires Mixin 'Mounts can use Vanilla saddles with limitations'")
		@Config.Name("Vanilla Saddle Creature Level Requirement")
		public int vanillaSaddleLevelRequirement = 16;

		@Config.Comment("Allow flying creatures use the vanilla saddle")
		@Config.Name("Vanilla Saddle Allow Flying")
		public boolean vanillaSaddleAllowFlying = false;

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
				"lycanitesmobs:decay",
				"srparasites:fear",
				"mod_lavacow:fear"
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

		@Config.Comment("Apply Mixin 'Lycanites Fire No Break Collision' to all modded fires")
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

		public static class ItemConfig {

			@Config.Comment("Base EXP Required to level up, scales with level, Lycanites Charge EXP is 50")
			@Config.Name("Enchanted Soulkey Base Levelup Experience")
			public int enchantedSoulkeyBaseLevelupExperience = 500;

			@Config.Comment("At this level the required exp stops increasing, otherwise scales +25% per level")
			@Config.Name("Enchanted Soulkey Next Level Final Scale")
			public int enchantedSoulkeyNextLevelFinalScale = 16;

			@Config.Comment("Default Maximum Level Creature Enchanted Soulkeys can spawn, can be overriden with NBT")
			@Config.Name("Enchanted Soulkey Max Level")
			public int enchantedSoulkeyDefaultMaxLevel = 100;

			@Config.Comment("Default Usages when NBT is blank, recommended to match crafting recipe materials")
			@Config.Name("Enchanted Soulkey On Craft Usages")
			public int enchantedSoulkeyOnCraftUsages = 8;

			@Config.Comment("Maximum Stored Nether Star Power and Gem Power (Diamond/Emerald Blocks)")
			@Config.Name("Enchanted Soulkey Max Usages")
			public int enchantedSoulkeyMaxUsages = 1000;

			@Config.Comment("Base EXP Required to level up, scales with level, Lycanites Charge EXP is 50")
			@Config.Name("Summoning Staff Base Levelup Experience")
			public int summonStaffBaseLevelupExperience = 500;

			@Config.Comment("The first infused Charge is bound to the summon staff and limits the possible elements to use")
			@Config.Name("Summoning Staff Elements Limited By Charge")
			public boolean summonStaffElementsByCharge = true;
		}

		public static class LootConfig {

			@Config.Comment("Minimum Level the creature must be to drop Random Charges")
			@Config.Name("Random Charge Loot Minimum Mob Level")
			@Config.RequiresMcRestart
			public int randomChargeMinimumMobLevel = 5;

			@Config.Comment("Minimum count per loot entry")
			@Config.Name("Random Charge Loot Minimum Count")
			@Config.RequiresMcRestart
			public int randomChargeScaledCountMinimum = 0;

			@Config.Comment("Maximum count per loot entry")
			@Config.Name("Random Charge Loot Maximum Count")
			@Config.RequiresMcRestart
			public int randomChargeScaledCountMaximum = 4;

			@Config.Comment("How many rolls per level, default is 0.1 so average one roll for every 10 levels")
			@Config.Name("Random Charge Level Scale")
			@Config.RequiresMcRestart
			public float randomChargeLevelScale = 0.1F;

			@Config.Comment("Limit the number of items to drop, set to 0 to have no limit")
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

			@Config.Comment("Register Level 100-115 Amalgalich, Asmodeus, and Rahovart special Enchanted Soulkey drop")
			@Config.Name("Register Boss Soulkey Loot Tables")
			@Config.RequiresMcRestart
			public boolean registerBossSoulkeyLootTables = true;

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
				Lower Health Bonus Options exists so BOSS_DAMAGE_LIMIT does not create mobs with insane time to kill

				A natural rare variant can have different stat modifiers from a SpawnedAsBoss version,
				LycanitesTweaks turns all altar bosses in SpawnedAsBoss mobs for example
			 */

			@Config.Comment("Ratio of lycanites bonus Health main bosses will receive, requires Mixin 'Boss Lower Health Scale'")
			@Config.Name("Boss Health Bonus Ratio")
			@Config.RangeDouble(min = 0)
			public double bossHealthBonusRatio = 0.25D;

			@Config.Comment("Ratio of max lycanites bonus defense, variants get more, set to 0 to disable the cap")
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

			@Config.Comment("Ratio of lycanites bonus Health bosses will receive, requires Mixin 'Boss Lower Health Scale'" +
					"\nThis applies to non Rares")
			@Config.Name("Spawned As Boss Health Bonus Ratio")
			@Config.RangeDouble(min = 0)
			public double spawnedAsBossHealthBonusRatio = 0.5D;

			@Config.Comment("Ratio of lycanites bonus Health bosses will receive, requires Mixin 'Boss Lower Health Scale'" +
					"\nThis applies to Rares who normally have higher stats and the DPS limit")
			@Config.Name("Spawned As Boss Rare Health Bonus Ratio")
			@Config.RangeDouble(min = 0)
			public double spawnedAsBossRareHealthBonusRatio = 0.5D;

			@Config.Comment("Apply Mixin 'Boss Invert Health and Damage Scale' to variants/dungeon bosses instead of just the main Bosses")
			@Config.Name("Spawned As Boss Invert Health and Damage Scale")
			public boolean spawnedAsBossInvert = true;

			/*
			 * 	EffectAuraGoal - Amalgalich Auto Decay, Archvile Demon Buffs
			 * 	barghest - Leap weight
			 * 	cockatrice - Mount Ability paralysis, aphagia
			 *  eechetik - Auto plauge
			 * 	quetzodracl - Pickup Drop weight
			 * 	raiko - Pickup Drop weight
			 * 	shade - Auto fear
			 * 	strider - Pickup water breathing, penetration
			 * 	warg - Auto paralysis
			 */
			@Config.Comment("List of various Lycanites that apply effects and toggle-able level scaling cap.\n" +
					"Format:[thing,maxScaleLevel,enable]\n" +
						"\tthing - Do not change from defaults\n" +
						"\tmaxScaleLevel - Final Level before duration and amplifier stop increasing\n" +
						"\tenable - 'true' Will use the level limit")
			@Config.Name("Effects Level Limited")
			public String[] effectsLevelLimited = {
					"barghest,15,false",
					"cockatrice,15,true",
					"eechetik,15,false",
					"quetzodracl,15,false",
					"raiko,15,false",
					"shade,15,true",
					"strider,15,false",
					"warg,15,true",
					"EffectAuraGoal,15,true"
			};

			// If you think Wisp glowing is OP
//			public String[] elementsLevelLimitedBuffs = {
//
//			};

			@Config.Comment("List of loaded elements whose Debuffs that will have capped level scaling.\n" +
					"Format:[elementName,maxscaledlevel]\n" +
						"\telementName - Name of the element to limit, must be all lowercase\n" +
						"\tmaxscaledlevel - Final Level before duration and amplifier stop increasing")
			@Config.Name("Elements Level Limited Debuffs")
			public String[] elementsLevelLimitedDebuffs = {
					"arcane,15",
					"chaos,15",
					"lightning,15",
					"phase,15"
			};
		}
	}

	public static class clientFeaturesConfig {

		@Config.Comment("Adds Distinct Damage Descriptions Information to Beastiary")
		@Config.Name("Add Distinct Damage Descriptions Information to Beastiary")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "distinctdamagedescriptions")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientbeastiaryddd.json")
		public boolean beastiaryGUIDDD = true;

		@Config.Comment("Adds Imperfect Summoning Information to Beastiary")
		@Config.Name("Add Imperfect Summoning Information to Beastiary")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientimperfectsummon.json")
		public boolean beastiaryGUIImperfectSummon = true;

		@Config.Comment("Adds Player Mob Levels Information to Beastiary")
		@Config.Name("Add Player Mob Levels Information to Beastiary")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientplayermoblevels.json")
		public boolean beastiaryGUIPML = true;
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

		@Config.Comment("Whether BaseCreatureEntity isBoss and dies, kill minions and projectiles")
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

		@Config.Comment("Mainhand Enchanted Soulkeys Add Levels to Altar Boss")
		@Config.Name("Enchanted Soulkey Altar Mini Bosses")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeyaltarminiboss.json")
		public boolean enchantedSoulkeyAltarMiniBoss = true;

		@Config.Comment("Mainhand Enchanted Soulkeys Add Levels to Altar Boss")
		@Config.Name("Enchanted Soulkey Altar Main Bosses")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeymainboss.json")
		public boolean enchantedSoulkeyAltarMainBoss = true;

		@Config.Comment("Enchanted Soulkeys can be put inside Equipment Infuser and Station to recharge")
		@Config.Name("Enchanted Soulkey Equipment Tiles Entities")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeyequipmenttiles.json")
		public boolean enchantedSoulkeyEquipmentTiles = true;

		@Config.Comment("Randomly store some Mob Event spawns in an Encounter Crystal, flags entity as SpawnedAsBoss (has configs)")
		@Config.Name("Encounter Crystal Mob Event (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuremobeventencountercrystal.json")
		public boolean encounterCrystalMobEvent = true;

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

		@Config.Comment("Fix explosion damage being reduced to 1 and use vanilla's fire punch-out handling")
		@Config.Name("Lycanites Fire No Break Collision")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.vanillaextinguishmoddedfire.json")
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

		@Config.Comment("Allow mounts to be use vanilla saddles based on levels (has configs)")
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

		@Config.Comment("Grant all creatures tagged as SpawnedAsBoss the Rare variant stat multipliers instead of Common/Uncommon")
		@Config.Name("Spawned As Boos Rare Boost")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurespawnedasbossrareboost.json")
		public boolean spawnedAsBossRareBoost = true;

		@Config.Comment("Rework summon progression to allow summoning weaker minions at low knowledge")
		@Config.Name("Summon Progression Rework")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonrework.json")
		public boolean summonProgressionRework = true;

		@Config.Comment("Save and use NBT stored Element Level Map to spawn higher level minions")
		@Config.Name("Summon Staff Level Map")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstafflevelmap.json")
		public boolean summonStaffLevelMap = true;

		@Config.Comment("Summon Staffs can use the Equipment Infuser in order to gain experience")
		@Config.Name("Summon Staff Level Map Equipment Tiles")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstaffequipmenttiles.json")
		public boolean summonStaffLevelMapEquipmentTiles = true;

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

		@Config.Comment("Inject handling for Player Mob Levels affecting the main Bosses")
		@Config.Name("Player Mob Level Bosses (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossesplayermoblevels.json")
		public boolean playerMobLevelMainBosses = true;

		@Config.Comment("Inject handling for Player Mob Level affecting JSON Spawners by whitelist")
		@Config.Name("Player Mob Level JSON Spawner (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurejsonspawnerplayermoblevels.json")
		public boolean playerMobLevelJSONSpawner = true;

		@Config.Comment("Inject handling for Player Mob Level to limit soulbounds in specified dimensions")
		@Config.Name("Player Mob Level Soulbound Limit Dims (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurelimitedbounddimensions.json")
		public boolean playerMobLevelSoulboundLimitedDimensions = true;

		@Config.Comment("Inject handling for Player Mob Level to affect summon staff minions")
		@Config.Name("Player Mob Level Summon Staff (Requires Capability)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstaffplayermoblevel.json")
		public boolean playerMobLevelSummonStaff = true;

		@Config.Comment("Lycanites Creatures can use JSON loot tables alongside LycanitesMobs drop list")
		@Config.Name("Vanilla BaseCreatureEntity Loot Table")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurecreaturevanillaloottables.json")
		public boolean vanillaBaseCreatureLootTable = true;

		@Config.Comment("Allows love arrows to make Lycanites animals breed")
		@Config.Name("Mod Compatibility: Love Arrow Fix (Switch-Bow)")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.switchboxlovearrowfix.json")
		public boolean switchbowLoveArrowFix = true;

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

		@Config.Comment("Altars post LivingDestroyBlockEvent for every call to setBlockToAir")
		@Config.Name("Altar Posts Forge Event")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesaltarforgeevent.json")
		public boolean altarPostsForgeEvent = true;

		@Config.Comment("PlaceBlockGoal post LivingDestroyBlockEvent for every call to canPlaceBlock")
		@Config.Name("Can Place Block Goal Forge Event")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesplaceblockgoalforgeevent.json")
		public boolean canPlaceBlockGoalForgeEvent = true;

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

		@Config.Comment("Add persistence to summons via BaseCreature method (as fix for Rahovart/Asmodeus minion mechanics)")
		@Config.Name("Fix BaseCreature Summon Persistence")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesbasecreatureminionpersistence.json")
		public boolean fixBaseCreatureSummonPersistence = true;

		@Config.Comment("Add a call to super in BaseCreature's isPotionApplicable method, restores vanilla parity for Undead mobs")
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

		@Config.Comment("Fix Mounting when trying to Heal a tamed creature with food")
		@Config.Name("Fix Mounting With Heal Food")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesnomountwithfood.json")
		public boolean fixMountingWithHealFood = true;

		@Config.Comment("Fix Serpix Blizzard projectile spawning in the ground")
		@Config.Name("Fix Serpix Blizzard Offset")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patcheserpixblizzardoffset.json")
		public boolean fixSerpixBlizzardOffset = true;

		@Config.Comment("All Lyc containers (Equipment Forges, Infuser, Station, Summoning Pedestal) have no Item Quick Move implementation (via shift clicking). This fix makes them use newer mc quick move mechanics where the crafting slots are preferred over the player inventory+hotbar.\n" +
				"Also fixes a minimal bug in Lyca Pet chest inventory where one slot (bottom right in player inventory) was not reachable via quick move.")
		@Config.Name("Fix Container Quick Move")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.containerbettershifting.json")
		public boolean fixContainerQuickMove = true;

		@Config.Comment("Players can only interact with Lyca crafting blocks from very low distances. This fix instead makes them copy the vanilla block (crafting table, furnace etc) behavior")
		@Config.Name("Fix Tile Entity Interaction Distance")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.tileentityinteractiondistance.json")
		public boolean fixTileEntityInteractionDistance = true;
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

	public static HashMap<String, Integer> getLevelLimitedEffects(){
		if(ForgeConfigHandler.effectsApplyScaleLevelLimited == null){
			HashMap<String, Integer> map = new HashMap<>();
			for(String string : ForgeConfigHandler.server.statsConfig.effectsLevelLimited){
				String[] line = string.split(",");
				try {
					if(line[2].equals("true"))
						map.put(line[0], Integer.valueOf(line[1]));
				}
				catch (Exception exception){
					LycanitesTweaks.LOGGER.error("Failed to parse {} in effectsLevelLimited", string);
				}
			}
			ForgeConfigHandler.effectsApplyScaleLevelLimited = map;
		}
		return ForgeConfigHandler.effectsApplyScaleLevelLimited;
	}

//	public static HashMap<String, Integer> getElementsLevelLimitedBuffs(){
//		if(ForgeConfigHandler.elementsLevelLimitedBuffs == null){
//			HashMap<String, Integer> map = new HashMap<>();
//			for(String string : ForgeConfigHandler.server.statsConfig.elementsLevelLimitedBuffs){
//				String[] line = string.split(",");
//				try {
//					map.put(line[0], Integer.valueOf(line[1]));
//				}
//				catch (Exception exception){
//					LycanitesTweaks.LOGGER.error("Failed to parse {} in elementsLevelLimitedBuffs", string);
//				}
//			}
//			ForgeConfigHandler.elementsLevelLimitedBuffs = map;
//		}
//		return ForgeConfigHandler.elementsLevelLimitedBuffs;
//	}

	public static HashMap<String, Integer> getElementsLevelLimitedDebuffs(){
		if(ForgeConfigHandler.elementsLevelLimitedDebuffs == null){
			HashMap<String, Integer> map = new HashMap<>();
			for(String string : ForgeConfigHandler.server.statsConfig.elementsLevelLimitedDebuffs){
				String[] line = string.split(",");
				try {
					map.put(line[0], Integer.valueOf(line[1]));
				}
				catch (Exception exception){
					LycanitesTweaks.LOGGER.error("Failed to parse {} in elementsLevelLimitedDebuffs", string);
				}
			}
			ForgeConfigHandler.elementsLevelLimitedDebuffs = map;
		}
		return ForgeConfigHandler.elementsLevelLimitedDebuffs;
	}

	public static HashSet<String> getPMLSpawnerNames(){
		if(ForgeConfigHandler.pmlSpawnerNames == null){
			ForgeConfigHandler.pmlSpawnerNames = new HashSet<>(Arrays.asList(ForgeConfigHandler.server.pmlConfig.pmlSpawnerNameStrings));
		}
		return ForgeConfigHandler.pmlSpawnerNames;
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

	@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(LycanitesTweaks.MODID)) {
				ForgeConfigHandler.cleansedCureEffects = null;
				ForgeConfigHandler.flowersaurBiomes = null;
				ForgeConfigHandler.immunizationCureEffects = null;
				ForgeConfigHandler.pmlSpawnerNames = null;
				ForgeConfigHandler.effectsApplyScaleLevelLimited = null;
//				ForgeConfigHandler.elementsLevelLimitedBuffs = null;
				ForgeConfigHandler.elementsLevelLimitedDebuffs = null;
				ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
			}
		}
	}
}