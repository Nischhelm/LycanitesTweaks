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

import java.util.ArrayList;
import java.util.HashSet;

@Config(modid = LycanitesTweaks.MODID)
public class ForgeConfigHandler {

	private static HashSet<ResourceLocation> flowersaurBiomes = null;

	private static ArrayList<PlayerMobLevelsConfig.BonusCategory> pmlBonusCategoryClientRenderOrder = null;

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	@MixinConfig.SubInstance
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Mixins based Client Tweaks")
	@Config.Name("Toggle Client Mixins")
	@MixinConfig.SubInstance
	public static final ClientFeaturesConfig clientFeaturesMixinConfig = new ClientFeaturesConfig();

	@Config.Comment("Mixins based Tweaks with highly configurable features")
	@Config.Name("Toggle Major Features Mixins")
	@MixinConfig.SubInstance
	public static final MajorFeaturesConfig majorFeaturesConfig = new MajorFeaturesConfig();

	@Config.Comment("Mixins based Tweaks with very basic options")
	@Config.Name("Toggle Minor Features Mixins")
	@MixinConfig.SubInstance
	public static final MinorFeaturesConfig minorFeaturesConfig = new MinorFeaturesConfig();

	@Config.Comment("Mod Compatibility\n" +
			"Toggles are enabled by default and will flag Fermium Booter errors, disable when associated mod is not installed")
	@Config.Name("Mod Compatibility")
	@MixinConfig.SubInstance
	public static final IntegrationConfig integrationConfig = new IntegrationConfig();

	@Config.Comment("Enable/Disable Patches for Lycanites Mobs")
	@Config.Name("Toggle Patches")
	@MixinConfig.SubInstance
	public static final PatchConfig mixinPatchesConfig = new PatchConfig();

	@Config.Comment("Dependency for various features when using default config values that use LycanitesTweaks resources\n" +
			"Makes Lycanites Mobs check and load resources from LycanitesTweaks\n" +
			"If disabled, there are no automatic replacements for resources provided by LycanitesTweaks")
	@Config.Name("Add Feature: LycanitesTweaks default JSON")
	@Config.RequiresMcRestart
	@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinjectdefaultjsonloading.json")
	public static boolean addLycanitesTweaksDefaultJSON = true;

	public static class ClientConfig {

		@Config.Comment("Enable Debug logging for auto information dumping")
		@Config.Name("Enables debug logging automatic")
		public boolean debugLoggerAutomatic = false;

		@Config.Comment("Enable Debug logging for manually triggered information")
		@Config.Name("Enables debug logging trigger")
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

		@Config.Name("Additional Effects")
		public final PotionEffectsConfig effectsConfig = new PotionEffectsConfig();

		@Config.Name("Additional Items")
		@MixinConfig.SubInstance
		public final ItemConfig itemConfig = new ItemConfig();

		@Config.Name("Additional Loot")
		@MixinConfig.SubInstance
		public final LootConfig lootConfig = new LootConfig();

		@Config.Comment("Whether Lycanites Block Protection protects against any Living Entity")
		@Config.Name("Block Protection Living Event")
		public boolean blockProtectionLivingEvent = true;


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

			@Config.Comment("Mainhand Enchanted Soulkeys Add Levels to Altar Boss")
			@Config.Name("Add Feature: Enchanted Soulkey Altar Mini Bosses")
			@Config.RequiresMcRestart
			@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeyaltarminiboss.json")
			public boolean enchantedSoulkeyAltarMiniBoss = true;

			@Config.Comment("Mainhand Enchanted Soulkeys Add Levels to Altar Boss")
			@Config.Name("Add Feature: Enchanted Soulkey Altar Main Bosses")
			@Config.RequiresMcRestart
			@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeymainboss.json")
			public boolean enchantedSoulkeyAltarMainBoss = true;

			@Config.Comment("Enchanted Soulkeys can be put inside Equipment Infuser to level up and Station to recharge")
			@Config.Name("Add Feature: Enchanted Soulkey Equipment Tiles Entities")
			@Config.RequiresMcRestart
			@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureenchantedsoulkeyequipmenttiles.json")
			public boolean enchantedSoulkeyEquipmentTiles = true;

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
		}

		public static class LootConfig {

			@Config.Comment("Lycanites Creatures can use JSON loot tables alongside Lycanites Mobs drop list")
			@Config.Name("Add Feature")
			@Config.RequiresMcRestart
			@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurecreaturevanillaloottables.json")
			public boolean vanillaBaseCreatureLootTable = true;

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

			@Config.Comment("Register Level 100+ Amalgalich, Asmodeus, and Rahovart special Enchanted Soulkey drop")
			@Config.Name("Register Boss Soulkey Loot Tables")
			@Config.RequiresMcRestart
			public boolean registerBossSoulkeyLootTables = true;

			@Config.Comment("Register Loot Tables for creatures dropping random charges of their element (This LootTable is dynamic)")
			@Config.Name("Register Random Charges Loot Tables")
			@Config.RequiresMcRestart
			public boolean registerRandomChargesLootTable = true;

			@Config.Comment("Register Loot Tables for any creature tagged as SpawnedAsBoss (ex Dungeon/Altar)")
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
	}

	public static class ClientFeaturesConfig {

		@Config.Comment("Adds LycanitesTweaks Information to Beastiary")
		@Config.Name("Add Feature: LycanitesTweaks Beastiary")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientbeastiarylt.json")
		public boolean beastiaryGUILT = true;

		@Config.Comment("Show Imperfect Summoning Information")
		@Config.Name("Add Feature: LycanitesTweaks Beastiary - Imperfect Summoning")
		public boolean beastiaryGUIImperfectSummon = true;

		@Config.Comment("Show Player Mob Levels Information")
		@Config.Name("Add Feature: LycanitesTweaks Beastiary - Player Mob Levels")
		public boolean beastiaryGUIPML = true;

		@Config.Comment("Beastiary Render order is determined by the order of this list\n" +
				"\tcategoryName - Spelling must match defaults, only modify order\n" +
				"This will be compared to the existence of 'Bonus Categories' entries in the PML config")
		@Config.Name("Add Feature: LycanitesTweaks Beastiary - PML Category Display Order")
		public String[] pmlBeastiaryOrder = {
				"AltarBossMain",
				"AltarBossMini",
				"DungeonBoss",
				"SpawnerNatural",
				"SpawnerTile",
				"SpawnerTrigger",
				"EncounterEvent",
				"SoulboundTame",
				"SummonMinion"
		};
	}

	public static class IntegrationConfig {

		@Config.Comment("Adds Distinct Damage Descriptions Information to Beastiary")
		@Config.Name("Mod Compatibility: Beastiary Info (Distinct Damage Descriptions)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "distinctdamagedescriptions", reason = "Distinct Damage Descriptions not found")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientbeastiaryddd.json")
		public boolean beastiaryGUIDDD = true;

		@Config.Comment("Allows love arrows to make Lycanites animals breed")
		@Config.Name("Mod Compatibility: Love Arrow Fix (Switch-Bow)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "switchbow", reason = "Switch-Bow not found")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.switchboxlovearrowfix.json")
		public boolean switchbowLoveArrowFix = true;

		@Config.Comment("Fix Potion Core forcibly overwriting BaseCreatureEntity motionY ")
		@Config.Name("Mod Compatibility: Potion Core Jump Fix (Potion Core)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "potioncore", reason = "Potion Core not found")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.potioncorejumpfix.json")
		public boolean potionCoreJumpFix = true;

		@Config.Comment("Whether to affect all mobs")
		@Config.Name("Mod Compatibility: Potion Core Jump Fix - All Mobs")
		public boolean fixAllMobsPotionCoreJump = true;

		/*
		 *
		 * Temporary Mod Compatibility Mixins, things I should really make a PR for
		 *
		 */

		@Config.Comment("Makes Crafted Equipment reach stat influence ReachFix attack range")
		@Config.Name("Mod Compatibility: Crafted Equipment ReachFix (ReachFix)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "reachfix", reason = "ReachFix not found")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentreachfix.json")
		public boolean craftedEquipmentReachFix = true;

		@Config.Comment("Cancels Custom Sweep and rehandle with RLCombat Sweep")
		@Config.Name("Mod Compatibility: Crafted Equipment RLCombat Sweep (RLCombat)")
		@Config.RequiresMcRestart
		@MixinConfig.CompatHandling(modid = "bettercombatmod", reason = "RLCombat not found")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentrlcombatsweep.json")
		public boolean craftedEquipmentRLCombatSweep = true;
	}

	public static class MajorFeaturesConfig {

		@Config.Name("Additional Creature Interactions")
		@MixinConfig.SubInstance
		public final CreatureInteractConfig creatureInteractConfig = new CreatureInteractConfig();

		@Config.Name("Capability: Crystal Stored Creature Bosses")
		@MixinConfig.SubInstance
		public final EntityStoreCreatureConfig escConfig = new EntityStoreCreatureConfig();

		@Config.Name("Capability: Player Mob Levels Bonus")
		@MixinConfig.SubInstance
		public final PlayerMobLevelsConfig pmlConfig = new PlayerMobLevelsConfig();

		@Config.Name("Enhanced Amalgalich")
		@MixinConfig.SubInstance
		public final BossAmalgalichConfig amalgalichConfig = new BossAmalgalichConfig();

		@Config.Name("Enhanced Asmodeus")
		@MixinConfig.SubInstance
		public final BossAsmodeusConfig asmodeusConfig = new BossAsmodeusConfig();

		@Config.Name("Enhanced Rahovart")
		@MixinConfig.SubInstance
		public final BossRahovartConfig rahovartConfig = new BossRahovartConfig();

		@Config.Name("Rework Summoning")
		@MixinConfig.SubInstance
		public final ImperfectSummoningConfig imperfectSummoningConfig = new ImperfectSummoningConfig();

		@Config.Name("Tweak Creature Stats")
		@MixinConfig.SubInstance
		public final CreatureStatsConfig creatureStatsConfig = new CreatureStatsConfig();

		@Config.Name("Tweak Lycanites Item")
		@MixinConfig.SubInstance
		public final ItemTweaksConfig itemTweaksConfig = new ItemTweaksConfig();
	}

	public static class MinorFeaturesConfig {

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

		@Config.Comment("Additionally limits damage amount on LivingDamageEvent LOWEST, this will fix one-shots dropping mob loot early")
		@Config.Name("Boss DPS Limit Recalc Modify - Reduces Amount")
		public boolean bossDamageLimitReducesAmount = true;

		@Config.Comment("When reading familiars from URL, Set Spawning Active to false")
		@Config.Name("Familiars Inactive On Join")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurefamiliarsinactiveonjoin.json")
		public boolean familiarsInactiveOnJoin = true;

		@Config.Comment("Enable customizable biome list for Arisaurs with the custom name Flowersaur")
		@Config.Name("Flowersaurs Naturally Spawn")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresflowersaurspawning.json")
		public boolean flowersaurNaturalSpawning = true;

		@Config.Comment("List of biome resource locations where custom name Arisaurs will spawn in")
		@Config.Name("Flowersaurs Naturally Spawn - Biomes")
		public String[] flowersaurSpawningBiomes = {
				"minecraft:mutated_forest",
				"biomesoplenty:mystic_grove",
				"twilightforest:enchanted_forest"
		};

		@Config.Comment("Fix explosion damage being reduced to 1 and use vanilla's fire punch-out handling")
		@Config.Name("Lycanites Fire Vanilla Like")
		@Config.RequiresMcRestart
		@MixinConfig.EarlyMixin(name = "mixins.lycanitestweaks.vanillaextinguishmoddedfire.json")
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurelycanitesfirepassable.json")
		public boolean lycanitesFiresNoBreakCollision = true;

		@Config.Comment("Adds more parity to Repulsion and Weight, repulsion gains weights benefits")
		@Config.Name("Repulsion Weight Benefits")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurerepulsionweight.json")
		public boolean repulsionWeight = true;

		@Config.Comment("Whether a Smited BaseCreatureEntity should be considered undead")
		@Config.Name("Lycanites Smited Are Undead")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresmitedundeadbasecreature.json")
		public boolean smitedMakesBaseCreatureUndead = true;
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

		// Rahovart is an example with oversight and can spawn infinite minions regardless of this
		@Config.Comment("Add persistence to minions spawned through the summonMinion method\n" +
				"This fixes vanilla Boss issues caused by despawning minions when mechanics depend on minions\n" +
				"If master dies, minion will be force despawned after 1 minute\n" +
				"Default is false as LycanitesTweaks boss enhancements directly addresses")
		@Config.Name("Fix Boss Mechanics Minion Persistence")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesbasecreatureminionpersistence.json")
		public boolean fixBaseCreatureSummonPersistence = false;

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

		@Config.Comment("Fix HealWhenNoPlayersGoal trigger check using AND instead of OR therefore bricking in most cases")
		@Config.Name("Fix Heal Goal Check")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patcheshealgoalcheck.json")
		public boolean fixHealGoalCheck = true;

		@Config.Comment("Fix Mounting when trying to Heal a tamed creature with food, will no longer mount when trying to heal the creature")
		@Config.Name("Fix Mounting With Heal Food")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchesnomountwithfood.json")
		public boolean fixMountingWithHealFood = true;


		// Mixin is not needed, original handling does not need to be canceled
		// Handle apply curing in PR, not needed here as original handling will catch active blindness
		@Config.Comment("Have NV deny Blindness from applying. This fixes visual flashing when blindness is applied every tick")
		@Config.Name("Fix Night Vision Curing Blindness")
		public boolean fixNVCuringBlindness = true;

		@Config.Comment("Fix Serpix Blizzard projectile spawning in the ground")
		@Config.Name("Fix Serpix Blizzard Offset")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patcheserpixblizzardoffset.json")
		public boolean fixSerpixBlizzardOffset = true;

		@Config.Comment("Fix Pickup host entity losing track of target such as when holding inside a wall")
		@Config.Name("Fix Pickup Target")
		@Config.RequiresMcRestart
		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.patchespickuptarget.json")
		public boolean fixPickupTargetAlways = true;

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

	public static HashSet<ResourceLocation> getFlowersaurBiomes(){
		if(ForgeConfigHandler.flowersaurBiomes == null){
			HashSet<ResourceLocation> list = new HashSet<>();
			for(String string: ForgeConfigHandler.minorFeaturesConfig.flowersaurSpawningBiomes){
				list.add(new ResourceLocation(string));
			}
			ForgeConfigHandler.flowersaurBiomes = list;
		}
		return ForgeConfigHandler.flowersaurBiomes;
	}

	public static ArrayList<PlayerMobLevelsConfig.BonusCategory> getPmlBonusCategoryClientRenderOrder(){
		if(ForgeConfigHandler.pmlBonusCategoryClientRenderOrder == null){
			ArrayList<PlayerMobLevelsConfig.BonusCategory> renderOrder = new ArrayList<>();

			for(String string : ForgeConfigHandler.clientFeaturesMixinConfig.pmlBeastiaryOrder){
				renderOrder.add(PlayerMobLevelsConfig.BonusCategory.get(string));
			}

			ForgeConfigHandler.pmlBonusCategoryClientRenderOrder = renderOrder;
		}
		return ForgeConfigHandler.pmlBonusCategoryClientRenderOrder;
	}

	@Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
	private static class EventHandler{

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if(event.getModID().equals(LycanitesTweaks.MODID)) {
				ForgeConfigHandler.flowersaurBiomes = null;
				ForgeConfigHandler.pmlBonusCategoryClientRenderOrder = null;
				ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
			}
		}
	}
}