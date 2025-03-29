package lycanitestweaks.handlers;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import lycanitestweaks.LycanitesTweaks;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Config(modid = LycanitesTweaks.MODID)
public class ForgeConfigHandler {

	private static HashSet<ResourceLocation> cleansedCureEffects = null;
	private static HashSet<ResourceLocation> flowersaurBiomes = null;
	private static HashSet<String> pmlSpawnerNames = null;

	@Config.Comment("Client-Side Options")
	@Config.Name("Client Options")
	public static final ClientConfig client = new ClientConfig();

	@Config.Comment("Server-Side Options")
	@Config.Name("Server Options")
	public static final ServerConfig server = new ServerConfig();

	@Config.Comment("Enable/Disable Tweaks")
	@Config.Name("Toggle Mixins")
	public static final MixinConfig mixinConfig = new MixinConfig();

	@Config.Comment("Enable/Disable Patches")
	@Config.Name("Toggle Patches")
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

		@Config.Comment("Ratio of lycanites bonus Health bosses will receive")
		@Config.Name("Boss Health Bonus Ratio")
		@Config.RangeDouble(min = 0)
		public double bossHealthBonusRatio = 0.1D;

		@Config.Comment("Ratio of max lycanites bonus movement defense, variants get more, set to 0 to disable")
		@Config.Name("Cap Defense Ratio")
		@Config.RangeDouble(min = 0)
		public double capDefenseRatio = 5.0D;

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

		@Config.Comment("Enable Capability to calculate a Mob Level associated to a player")
		@Config.Name("Player Mob Level Capability")
		@Config.RequiresMcRestart
		public boolean playerMobLevelCapability = true;

		@Config.Comment("Ratio of Player Mob Level used to set for summon staff minions")
		@Config.Name("Player Mob Level Degree Summon")
		@Config.RangeDouble(min = 0.0)
		public double pmlSummonDegree = 0.2D;

		@Config.Comment("Ratio of Player Mob Level used to set for summon hostile bosses")
		@Config.Name("Player Mob Level Degree Boss")
		@Config.RangeDouble(min = 0.0)
		public double pmlBossDegree = 0.2D;

		@Config.Comment("Ratio of Player Mob Level used to set for Lycanites Spawners")
		@Config.Name("Player Mob Level Degree Spawner")
		@Config.RangeDouble(min = 0.0)
		public double pmlSpawnerDegree = 0.2D;

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

		@Config.Comment("List of biome resource locations where custom name Arisaurs will spawn in")
		@Config.Name("Biomes Flowersaurs Spawn In")
		public String[] flowersaurSpawningBiomes = {
				"minecraft:mutated_forest",
				"biomesoplenty:mystic_grove",
				"twilightforest:enchanted_forest"
		};

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

		@Config.Comment("Register Has Mob Levels Loot Condition")
		@Config.Name("Register HasMobLevels Loot Condition")
		@Config.RequiresMcRestart
		public boolean registerPMLLootCondition = true;

		@Config.Comment("Register Scale With Mob Levels Loot Function")
		@Config.Name("Register ScaleWithMobLevels Loot Function")
		@Config.RequiresMcRestart
		public boolean registerPMLLootFunction = true;
	}

	public static class MixinConfig {

		@Config.Comment("Fix Golems attacking tamed mobs")
		@Config.Name("Fix Golems Attacking Tamed Mobs")
		@Config.RequiresMcRestart
		public boolean ironGolemsTamedTarget = true;

		@Config.Comment("Whether a Smited LivingEntityBase is undead if method inherited (often overridden)")
		@Config.Name("Most Smited Are Undead")
		@Config.RequiresMcRestart
		public boolean smitedMakesMostUndead = true;

		@Config.Comment("Giving an Enchanted Golden Apple to a tamed creature will turn it into a baby")
		@Config.Name("Baby Age Gapple")
		@Config.RequiresMcRestart
		public boolean babyAgeGapple = true;

		@Config.Comment("Bleed damage uses setDamageIsAbsolute ontop of Magic/Armor ignoring")
		@Config.Name("Bleed Pierces")
		@Config.RequiresMcRestart
		public boolean bleedPierces = true;

		@Config.Comment("Move the Damage Limit DPS calc to LivingHurtEvent LOWEST from attackEntityFrom")
		@Config.Name("Boss DPS Limit Recalc")
		@Config.RequiresMcRestart
		public boolean bossDPSLimitRecalc = true;

		@Config.Comment("Invert bonus Health/Damage level scale for Bosses")
		@Config.Name("Boss Invert Health and Damage Scale")
		@Config.RequiresMcRestart
		public boolean bossInvertHealthDamageScale = true;

		@Config.Comment("Main Boss HP level bonus scaled down via config")
		@Config.Name("Boss Lower Health Scale")
		@Config.RequiresMcRestart
		public boolean bossLowerHealthScale = true;

		@Config.Comment("Main Boss Event is scaled to Player Mob Levels Capability")
		@Config.Name("Boss Player Mob Levels (Requires Capability)")
		@Config.RequiresMcRestart
		public boolean bossMainPlayerMobLevels = true;

		@Config.Comment("Add configurable caps to creature speed, effect durations, and pierce")
		@Config.Name("Cap Specific Creature Stats")
		@Config.RequiresMcRestart
		public boolean capSpecificStats = true;

		@Config.Comment("Make offhand crafted equipment RMB ability require player to be sneaking")
		@Config.Name("Crafted Equipment Offhand RMB Needs Sneak")
		@Config.RequiresMcRestart
		public boolean craftedEquipmentOffhandRMBSneak = true;

		@Config.Comment("Allows Crafted Equipment to use Sword Enchantments")
		@Config.Name("Crafted Equipment Sword Enchantments")
		@Config.RequiresMcRestart
		public boolean craftedEquipmentSwordEnchantments = true;

		@Config.Comment("Enable customizable effect list and handling for the cleansed effect")
		@Config.Name("Customizable Cleansed Curing list")
		@Config.RequiresMcRestart
		public boolean cleansedEffectList = true;

		@Config.Comment("When reading familiars from URL, Set Spawning Active to false")
		@Config.Name("Familiars Inactive On Join")
		@Config.RequiresMcRestart
		public boolean familiarsInactiveOnJoin = true;

		@Config.Comment("Enable customizable biome list for Arisaurs with the custom name Flowersaur")
		@Config.Name("Flowersaurs Naturally Spawn")
		@Config.RequiresMcRestart
		public boolean flowersaurSpawningBiomes = true;

		@Config.Comment("Summon minion goal matches host and minion levels (AI Goal)")
		@Config.Name("Level match minions goal")
		@Config.RequiresMcRestart
		public boolean levelMatchMinionsGoal = true;

		@Config.Comment("Host entity summons minions at matching levels (Hard Coded)")
		@Config.Name("Level match minions host method")
		@Config.RequiresMcRestart
		public boolean levelMatchMinionsHostMethod = true;

		@Config.Comment("Whether a Smited BaseCreatureEntity should be considered undead")
		@Config.Name("Lycanites Smited Are Undead")
		@Config.RequiresMcRestart
		public boolean smitedMakesBaseCreatureUndead = true;

		@Config.Comment("Allow the pet perch position to be modifiable via config")
		@Config.Name("Perch Position Modifiable")
		@Config.RequiresMcRestart
		public boolean perchPositionModifiable = true;

		@Config.Comment("Add distance checks to pickup mobs teleporting vicitims")
		@Config.Name("Pickup Checks Distances")
		@Config.RequiresMcRestart
		public boolean pickupChecksDistance = true;

		@Config.Comment("Feeding tamed creatures Burritos and Risottos will increase/decrease size scale")
		@Config.Name("Size Change Foods")
		@Config.RequiresMcRestart
		public boolean sizeChangeFoods = true;

		@Config.Comment("Make Soul Gazing a creature riding an entity dismount and attack the player")
		@Config.Name("Soul Gazer Dismounts")
		@Config.RequiresMcRestart
		public boolean soulGazerDismounts = true;

		@Config.Comment("Enable setting owned creature and animal variant status with Soul Keys")
		@Config.Name("Soulkeys Set Variant")
		@Config.RequiresMcRestart
		public boolean soulkeysSetVariant = true;

		@Config.Comment("Rework summon progression to allow summoning weaker minions at low knowledge")
		@Config.Name("Summon Progression Rework")
		@Config.RequiresMcRestart
		public boolean summonProgressionRework = true;

		@Config.Comment("Invert bonus Health/Damage level scale for Tamed Creatures")
		@Config.Name("Tamed Invert Health and Damage Scale")
		@Config.RequiresMcRestart
		public boolean tamedInvertHealthDamageScale = true;

		@Config.Comment("Enable whether all tamed (tamed/summoned/soulbound) variants get stats bonuses")
		@Config.Name("Tamed Variant Stat Bonuses")
		@Config.RequiresMcRestart
		public boolean tamedVariantStats = true;

		@Config.Comment("Feeding Treats will prevent natural and forced despawning")
		@Config.Name("Treat Sets Persistence")
		@Config.RequiresMcRestart
		public boolean treatSetsPersistence = true;

		@Config.Comment("Use Player Mob Level to affect Soul Key Bosses")
		@Config.Name("Player Mob Level Bosses (Requires Capability)")
		@Config.RequiresMcRestart
		public boolean playerMobLevelBosses = true;

		@Config.Comment("Use Player Mob Level to affect JSON Spawners by whitelist")
		@Config.Name("Player Mob Level JSON Spawner (Requires Capability)")
		@Config.RequiresMcRestart
		public boolean playerMobLevelJSONSpawner = true;

		@Config.Comment("Use Player Mob Level to affect summon staff minions")
		@Config.Name("Player Mob Level Summon Staff (Requires Capability)")
		@Config.RequiresMcRestart
		public boolean playerMobLevelSummonStaff = true;

		@Config.Comment("Lycanites Creatures can use JSON loot tables alongside LycanitesMobs drop list")
		@Config.Name("Vanilla BaseCreatureEntity Loot Table")
		@Config.RequiresMcRestart
		public boolean vanillaBaseCreatureLootTable = true;

		@Config.Comment("Makes Crafted Equipment reach stat influence ReachFix attack range")
		@Config.Name("Crafted Equipment ReachFix (ReachFix)")
		@Config.RequiresMcRestart
		public boolean craftedEquipmentReachFix = false;

		@Config.Comment("Cancels Crafted Sweep and rehandle with RLCombat Sweep")
		@Config.Name("Crafted Equipment RLCombat Sweep (RLCombat)")
		@Config.RequiresMcRestart
		public boolean craftedEquipmentRLCombatSweep = false;
	}

	public static class PatchConfig {

		@Config.Comment("Disables Soul Bounds using portals, which would kill them and set respawn cooldown")
		@Config.Name("Disable Soul Bounds Using Portals")
		@Config.RequiresMcRestart
		public boolean soulBoundNoPortal = true;

		@Config.Comment("Fix hostile AgeableCreature babies not dropping loot")
		@Config.Name("Fix AgeableCreature baby drops")
		@Config.RequiresMcRestart
		public boolean fixAgeableBabyDrops = true;

		@Config.Comment("Add persistence to summons via BaseCreature method")
		@Config.Name("Fix BaseCreature Summon Persistence")
		@Config.RequiresMcRestart
		public boolean fixBaseCreatureSummonPersistence = true;

		@Config.Comment("Add a call to super in BaseCreature's isPotionApplicable method")
		@Config.Name("Fix BaseCreature Potion Applicable")
		@Config.RequiresMcRestart
		public boolean fixBaseCreaturePotionApplicableSuper = true;

		@Config.Comment("Fix divide by zero crash in FireProjectilesGoal and RangedSpeed of zero preventing attacks")
		@Config.Name("Fix Creature Ranged Speed")
		@Config.RequiresMcRestart
		public boolean fixRangedSpeedDivideZero = true;

		@Config.Comment("Fix Ettin checking for inverted griefing flag")
		@Config.Name("Fix Ettin grief flag")
		@Config.RequiresMcRestart
		public boolean fixEttinBlockBreak = true;

		@Config.Comment("Fix Fear checking for creative capabilities instead of flight")
		@Config.Name("Fix Fear Survival Flying")
		@Config.RequiresMcRestart
		public boolean fixFearSurvivalFlying = true;

		@Config.Comment("Fix Serpix Blizzard projectile spawning in the ground")
		@Config.Name("Fix Serpix Blizzard Offset")
		@Config.RequiresMcRestart
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

	public static HashSet<String> getPMLSpawnerNames(){
		if(ForgeConfigHandler.pmlSpawnerNames == null){
			ForgeConfigHandler.pmlSpawnerNames = new HashSet<>(Arrays.asList(ForgeConfigHandler.server.pmlSpawnerNameStrings));
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

	private static File configFile = null;
	private static String configBooleanString = "";

	public static boolean getBoolean(String name) {
		if(configFile==null) {
			configFile = new File("config", LycanitesTweaks.MODID + ".cfg");
			if(configFile.exists() && configFile.isFile()) {
				try (Stream<String> stream = Files.lines(configFile.toPath())) {
					configBooleanString = stream.filter(s -> s.trim().startsWith("B:")).collect(Collectors.joining());
				}
				catch(Exception ex) {
					LycanitesTweaks.LOGGER.log(Level.ERROR, "Failed to parse LycanitesTweaks config: " + ex);
				}
			}
		}
		//If config is not generated or missing entries, don't enable injection on first run
		return configBooleanString.contains("B:\"" + name + "\"=true");
	}
}