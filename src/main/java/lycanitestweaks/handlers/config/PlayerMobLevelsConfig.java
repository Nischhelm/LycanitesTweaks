package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.util.Pair;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PlayerMobLevelsConfig {

    private static HashMap<BonusCategory, Pair<BonusUsage, Double>> pmlBonusCategories = null;
    private static HashSet<BonusCategory> pmlBonusCategorySoulgazer = null;
    private static HashMap<Bonus, Double> pmlBonusUsagesAll = null;
    private static HashMap<Bonus, Double> pmlBonusUsagesTamed = null;
    private static HashMap<Bonus, Double> pmlBonusUsagesWild = null;
    private static HashSet<String> pmlSpawnerNames = null;

    @Config.Comment("Enable Capability to calculate a Mob Level associated to a player")
    @Config.Name("Capability: Player Mob Levels")
    @Config.RequiresMcRestart
    public boolean playerMobLevelCapability = true;

    @Config.Comment("Format: [categoryName,soulgazer,bonusGroup,multiplier]\n" +
            "\tcategoryName - The case player mob levels is added, do not change from the defaults\n" +
            "\tsoulgazer - If a mainhand Soulgazer is required to apply level boost\n" +
            "\tbonusGroup - [All,TAMED,WILD], specifies the list of multipliers to use when calculating bonus levels\n" +
            "\tmultiplier - Multiplier to use on the total bonus before it is used\n\n" +
            "Removing an entry fully disables associated features compared to zero'ing the multiplier\n" +
            "\tex. 'SpawnerTrigger' will still flag first-time spawns with 0.0 multiplier")
    @Config.Name("Capability: PML - Bonus Categories")
    public String[] pmlCategories = {
            "AltarBossMain,true,WILD,1.0",
            "AltarBossMini,true,WILD,0.75",
            "DungeonBoss,true,WILD,0.75",
            "EncounterEvent,false,WILD,1.0",
            "SoulboundTame,false,TAMED,1.0",
            "SpawnerNatural,false,ALL,0.2",
            "SpawnerTile,false,WILD,0.3",
            "SpawnerTrigger,false,WILD,0.2",
            "SummonMinion,false,TAMED,1.0"
    };

    @Config.Comment("Specifies multipliers on specific bonus sources. Fall back list for when Tamed/Wild values are not found.\n" +
            "Format: [bonusName,multiplier]\n" +
            "\tbonusName - The Source of the bonus, do not change from the defaults\n" +
            "\tmultiplier - Multiplier to use on the total bonus before it is used")
    @Config.Name("Capability: PML - Bonus Source Multipliers - ALL")
    public String[] pmlBonusAll = {
            "ActivePet,0.75",
            "BestiaryCreature,1.0",
            "BestiaryElement,1.0",
            "Enchantments,1.0",
            "PlayerDeath,-1.0"
    };

    @Config.Comment("Specifies multipliers on specific bonus sources. Used on Bonus Categories that are marked as 'TAMED'\n" +
            "Format: [bonusName,multiplier]\n" +
            "\tbonusName - The Source of the bonus, do not change from the defaults\n" +
            "\tmultiplier - Multiplier to use on the total bonus before it is used")
    @Config.Name("Capability: PML - Bonus Source Multipliers - TAMED")
    public String[] pmlBonusTamed = {
            "ActivePet,0.25",
            "BestiaryCreature,2.0",
            "BestiaryElement,2.0",
            "Enchantments,0.5",
            "PlayerDeath,0.0"
    };

    @Config.Comment("Specifies multipliers on specific bonus sources. Used on Bonus Categories that are marked as 'WILD'\n" +
            "Format: [bonusName,multiplier]\n" +
            "\tbonusName - The Source of the bonus, do not change from the defaults\n" +
            "\tmultiplier - Multiplier to use on the total bonus before it is used")
    @Config.Name("Capability: PML - Bonus Source Multipliers - WILD")
    public String[] pmlBonusWild = {
            "ActivePet,1.0",
            "BestiaryCreature,1.0",
            "BestiaryElement,0.0",
            "Enchantments,1.0",
            "PlayerDeath,-1.0"
    };

    @Config.Comment("Used to lower bloated Minimum Enchantibility values via Rarity {COMMON, UNCOMMON, RARE, VERY_RARE}")
    @Config.Name("Capability: PML - Enchantment Rarity Divisors")
    @Config.RequiresMcRestart
    public int[] enchRarityDivisors = {1, 2, 5, 10};

    @Config.Comment("Whether Highest Level Pet Entry should try to be calculated, unsorted levels are still stored, false always returns 0")
    @Config.Name("Capability: PML - Calculate Highest Level Pet Entry")
    public boolean pmlCalcHighestLevelPet = true;

    @Config.Comment("Inject handling for Player Mob Levels affecting the main Bosses")
    @Config.Name("Feature: Main Boss Bonus")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossesplayermoblevels.json")
    public boolean playerMobLevelMainBosses = true;

    @Config.Comment("Inject handling for Player Mob Level affecting JSON Spawners by whitelist")
    @Config.Name("Feature: JSON Spawner Bonus")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurejsonspawnerplayermoblevels.json")
    public boolean playerMobLevelJSONSpawner = true;

    @Config.Comment("Flags JSON entities to not be affected by the Natural Spawn Boost whether or not an entity was boosted.\n" +
            "This can make every event provide a smaller boost or make a few events stack a large boost")
    @Config.Name("Feature: JSON Spawner Bonus - Calls First Spawn")
    public boolean pmlSpawnerNameFirstSpawn = false;

    @Config.Comment("JSON Spawner Names is a blacklist instead of whitelist")
    @Config.Name("Feature: JSON Spawner Bonus - Blacklist")
    public boolean pmlSpawnerNameStringsIsBlacklist = false;

    @Config.Comment("List of Lycanites Spawner Names to attempt to apply Player Mob Levels")
    @Config.Name("Feature: JSON Spawner Bonus - Spawner Names")
    public String[] pmlSpawnerNameStrings = {
            "chaos",
            "disruption",
            "sleep"
    };

    @Config.Comment("Inject handling for Player Mob Level to limit soulbounds in specified dimensions")
    @Config.Name("Feature: Soulbound Limit Dimensions")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurelimitedbounddimensions.json")
    public boolean playerMobLevelSoulboundLimitedDimensions = true;

    @Config.Comment("Dimension IDs where soulbounds are level capped to Player Mob Level")
    @Config.Name("Feature: Soulbound Limit Dimensions - IDs")
    public int[] pmlMinionLimitDimIds = {
            111,
            3
    };

    @Config.Comment("Player Mob Level Dimensions is Whitelist")
    @Config.Name("Feature: Soulbound Limit Dimensions - Whitelist")
    public boolean pmlMinionLimitDimIdsWhitelist = true;

    @Config.Comment("Whether limited soulbounds can spawn in dimensions blacklisted by vanilla Lycanites")
    @Config.Name("Feature: Soulbound Limit Dimensions - Overrules Blacklist")
    public boolean pmlMinionLimitDimOverruleBlacklist = true;

    @Config.Comment("Whether limited soulbound inventories can not have items put inside")
    @Config.Name("Feature: Soulbound Limit Dimensions - No Inventory")
    public boolean pmlMinionLimitDimNoInventory = true;

    @Config.Comment("Whether limited soulbounds can be mounted")
    @Config.Name("Feature: Soulbound Limit Dimensions - Not Mountable")
    public boolean pmlMinionLimitDimNoMount = true;

    @Config.Comment("Whether limited dimensions prevent soulbound spirit recharge")
    @Config.Name("Feature: Soulbound Limit Dimensions - No Spirit Recharge")
    public boolean pmlMinionLimitDimNoSpiritRecharge = true;

    @Config.Comment("Inject handling for Player Mob Level to affect summon staff minions")
    @Config.Name("Feature: Player Mob Level Summon Staff")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuresummonstaffplayermoblevel.json")
    public boolean playerMobLevelSummonStaff = true;

    @Config.Comment("Lycanites Pet Manager updates Player Mob Level Capability with pet entry information")
    @Config.Name("Feature: Pet Manager Tracks Pet Levels")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurepetmanagerpmltrackspetlevels.json")
    public boolean petManagerTracksHighestLevelPet = true;

    @Config.Comment("Remove treat pacifying and lower reputation gain when taming high leveled creatures")
    @Config.Name("Feature: Over Leveled Penalty")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featuretameoverlevelpenalty.json")
    public boolean tamedOverLeveledPenalty = true;

    @Config.Comment("Creature level to compare to PML highest pet entry level, set to 0 to disable")
    @Config.Name("Feature: Over Leveled Penalty - Start")
    public int pmlTamedOverLevelStartLevel = 20;

    @Config.Comment("Whether treat reputation will be penalized if creature is over leveled.")
    @Config.Name("Feature: Over Leveled Penalty - Treat Point Penalty")
    public boolean pmlTamedOverLevelTreatPointPenalty = false;

    @Config.Comment("If creature could be tempted, will it remain able to be tempted if over leveled")
    @Config.Name("Feature: Over Leveled Penalty - Treat Tempt")
    public boolean pmlTamedOverLevelTreatTempt = false;

    public static HashMap<BonusCategory, Pair<BonusUsage, Double>> getPmlBonusCategories(){
        if(PlayerMobLevelsConfig.pmlBonusCategories == null){
            HashMap<BonusCategory, Pair<BonusUsage, Double>> map = new HashMap<>();
            for(String string : ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlCategories){
                String[] line = string.split(",");
                try {
                    BonusUsage usage = BonusUsage.ALL;
                    switch (line[2]){
                        case "TAMED":
                            usage = BonusUsage.TAMED;
                            break;
                        case "WILD":
                            usage = BonusUsage.WILD;
                            break;
                    }
                    map.put(BonusCategory.get(line[0]), new Pair<>(usage, Double.valueOf(line[3])));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in pmlCategories", string);
                }
            }
            PlayerMobLevelsConfig.pmlBonusCategories = map;
        }
        return PlayerMobLevelsConfig.pmlBonusCategories;
    }

    public static HashSet<BonusCategory> getPmlBonusCategorySoulgazer(){
        if(PlayerMobLevelsConfig.pmlBonusCategorySoulgazer == null){
            HashSet<BonusCategory> set = new HashSet<>();
            for(String string : ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlCategories){
                String[] line = string.split(",");
                try {
                    if("true".equals(line[1])) set.add(BonusCategory.get(line[0]));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in pmlCategories for Soulgazer requirements", string);
                }
            }
            PlayerMobLevelsConfig.pmlBonusCategorySoulgazer = set;
        }
        return PlayerMobLevelsConfig.pmlBonusCategorySoulgazer;
    }

    public static HashMap<Bonus, Double> getPmlBonusUsagesAll(){
        if(PlayerMobLevelsConfig.pmlBonusUsagesAll == null){
            HashMap<Bonus, Double> map = new HashMap<>();
            for(String string : ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlBonusAll){
                String[] line = string.split(",");
                try {
                    map.put(Bonus.valueOf(line[0]), Double.valueOf(line[1]));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in pmlBonusAll", string);
                }
            }
            PlayerMobLevelsConfig.pmlBonusUsagesAll = map;
        }
        return PlayerMobLevelsConfig.pmlBonusUsagesAll;
    }

    public static HashMap<Bonus, Double> getPmlBonusUsagesTamed(){
        if(PlayerMobLevelsConfig.pmlBonusUsagesTamed == null){
            HashMap<Bonus, Double> map = new HashMap<>();
            for(String string : ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlBonusTamed){
                String[] line = string.split(",");
                try {
                    map.put(Bonus.valueOf(line[0]), Double.valueOf(line[1]));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in pmlBonusTamed", string);
                }
            }
            PlayerMobLevelsConfig.pmlBonusUsagesTamed = map;
        }
        return PlayerMobLevelsConfig.pmlBonusUsagesTamed;
    }

    public static HashMap<Bonus, Double> getPmlBonusUsagesWild(){
        if(PlayerMobLevelsConfig.pmlBonusUsagesWild == null){
            HashMap<Bonus, Double> map = new HashMap<>();
            for(String string : ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlBonusWild){
                String[] line = string.split(",");
                try {
                    map.put(Bonus.valueOf(line[0]), Double.valueOf(line[1]));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in pmlBonusWild", string);
                }
            }
            PlayerMobLevelsConfig.pmlBonusUsagesWild = map;
        }
        return PlayerMobLevelsConfig.pmlBonusUsagesWild;
    }

    public static HashSet<String> getPMLSpawnerNames(){
        if(PlayerMobLevelsConfig.pmlSpawnerNames == null){
            PlayerMobLevelsConfig.pmlSpawnerNames = new HashSet<>(Arrays.asList(ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlSpawnerNameStrings));
        }
        return PlayerMobLevelsConfig.pmlSpawnerNames;
    }

    public static boolean isDimensionLimitedMinion(int dim){
        boolean found = false;
        for(int id : ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlMinionLimitDimIds){
            if (dim == id) {
                found = true;
                break;
            }
        }
        return ForgeConfigHandler.majorFeaturesConfig.pmlConfig.pmlMinionLimitDimIdsWhitelist == found;
    }

    public enum Bonus {

        ActivePet("ActivePet"),
        BestiaryCreature("BestiaryCreature"),
        BestiaryElement("BestiaryElement"),
        Enchantments("Enchantments"),
        PlayerDeath("PlayerDeath"),
        Dummy("");

        private final String name;

        Bonus(String name){
            this.name = name;
        }

        public static PlayerMobLevelsConfig.Bonus get(String name) {
            return Arrays.stream(PlayerMobLevelsConfig.Bonus.values())
                    .filter(bonus -> bonus.name.equals(name))
                    .findFirst().orElse(Dummy);
        }
    }

    public enum BonusCategory {

        AltarBossMain("AltarBossMain"),
        AltarBossMini("AltarBossMini"),
        DungeonBoss("DungeonBoss"),
        EncounterEvent("EncounterEvent"),
        SoulboundTame("SoulboundTame"),
        SpawnerNatural("SpawnerNatural"),
        SpawnerTile("SpawnerTile"),
        SpawnerTrigger("SpawnerTrigger"),
        SummonMinion("SummonMinion"),
        Dummy("");

        private final String name;

        BonusCategory(String name){
            this.name = name;
        }

        public static PlayerMobLevelsConfig.BonusCategory get(String category) {
            return Arrays.stream(PlayerMobLevelsConfig.BonusCategory.values())
                    .filter(bonus -> bonus.name.equals(category))
                    .findFirst().orElse(Dummy);
        }
    }

    public enum BonusUsage {

        ALL,
        TAMED,
        WILD
    }

    @Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
    private static class EventHandler{

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(LycanitesTweaks.MODID)) {
                PlayerMobLevelsConfig.pmlBonusCategories = null;
                PlayerMobLevelsConfig.pmlBonusCategorySoulgazer = null;
                PlayerMobLevelsConfig.pmlBonusUsagesAll = null;
                PlayerMobLevelsConfig.pmlBonusUsagesTamed = null;
                PlayerMobLevelsConfig.pmlBonusUsagesWild = null;
                PlayerMobLevelsConfig.pmlSpawnerNames = null;

                ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
