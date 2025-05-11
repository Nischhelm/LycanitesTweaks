package lycanitestweaks.handlers.config;

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

public class PlayerMobLevelsConfig {

    private static HashMap<BonusCategory, Pair<BonusUsage, Double>> pmlBonusCateogories = null;
    public static HashMap<Bonus, Double> pmlBonusUsagesAll = null;
    public static HashMap<Bonus, Double> pmlBonusUsagesTamed = null;
    public static HashMap<Bonus, Double> pmlBonusUsagesWild = null;

    @Config.Comment("Format: [categoryName,bonusGroup,multiplier]\n" +
                        "\tcategoryName - The case player mob levels is added, do not change from the defaults\n" +
                        "\tbonusGroup - [All,TAMED,WILD], specifies the list of multipliers to use when calculating bonus levels\n" +
                        "\tmultiplier - Multiplier to use on the total bonus before it is used")
    @Config.Name("Bonus Categories")
    public String[] pmlCategories = {
            "AltarBossMain,WILD,1.0",
            "AltarBossMini,WILD,1.0",
            "DungeonBoss,WILD,1.0",
            "SoulboundTame,TAMED,1.0",
            "SpawnerNatural,ALL,1.0",
            "SpawnerTrigger,ALL,1.0",
            "SummonMinion,TAMED,1.0"
    };

    @Config.Comment("Specifies multipliers on specific bonus sources. Fall back list for when Tamed/Wild values are not found.\n" +
            "Format: [bonusName,multiplier]\n" +
            "\tbonusName - The Source of the bonus, do not change from the defaults\n" +
            "\tmultiplier - Multiplier to use on the total bonus before it is used")
    @Config.Name("Bonus Source Multipliers - ALL")
    public String[] pmlBonusAll = {
            "ActivePet,1.0",
            "Bestiary,1.0",
            "Enchantments,1.0"
    };

    @Config.Comment("Specifies multipliers on specific bonus sources. Used on Bonus Categories that are marked as 'TAMED'\n" +
            "Format: [bonusName,multiplier]\n" +
                "\tbonusName - The Source of the bonus, do not change from the defaults\n" +
                "\tmultiplier - Multiplier to use on the total bonus before it is used")
    @Config.Name("Bonus Source Multipliers - TAMED")
    public String[] pmlBonusTamed = {
            "ActivePet,0.25",
            "Bestiary,2.0",
            "Enchantments,0.5"
    };

    @Config.Comment("Specifies multipliers on specific bonus sources. Used on Bonus Categories that are marked as 'WILD'\n" +
            "Format: [bonusName,multiplier]\n" +
                "\tbonusName - The Source of the bonus, do not change from the defaults\n" +
                "\tmultiplier - Multiplier to use on the total bonus before it is used")
    @Config.Name("Bonus Source Multipliers - WILD")
    public String[] pmlBonusWild = {
            "ActivePet,1.0",
            "Bestiary,1.0",
            "Enchantments,1.0"
    };

    @Config.Comment("Used to lower bloated Minimum Enchantibility values via Rarity {COMMON, UNCOMMON, RARE, VERY_RARE}")
    @Config.Name("Enchantment Rarity Divisors")
    @Config.RequiresMcRestart
    public int[] enchRarityDivisors = {1, 2, 5, 10};

    @Config.Comment("Alternative to fully disabling, forge caps exist (non null) but always returns 0")
    @Config.Name("Mod Compatibility: No Calculate")
    public boolean playerMobLevelCapabilityNoCalc = false;

    @Config.Comment("Enable Capability to calculate a Mob Level associated to a player")
    @Config.Name("Player Mob Level Capability")
    @Config.RequiresMcRestart
    public boolean playerMobLevelCapability = true;

    @Config.Comment("Require a held main hand Soulgazer in order to summon a boss with additional levels")
    @Config.Name("Player Mob Level Boss Requires Soulgazer")
    public boolean pmlBossRequiresSoulgazer = true;

    @Config.Comment("If Highest Level Pet Entry should try to be calculated, unsorted levels are still stored, false always returns 0")
    @Config.Name("Player Mob Level Calculate Highest Level Pet Entry")
    public boolean pmlCalcHighestLevelPet = true;

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
            "disruption",
            "sleep"
    };

    @Config.Comment("PML Spawner Names is a blacklist instead of whitelist")
    @Config.Name("PML Spawner Names Blacklist")
    public boolean pmlSpawnerNameStringsIsBlacklist = false;

    @Config.Comment("Creature level to compare to PML highest pet entry level, Requires Mixin 'Tamed Invert Over Leveled Penalty', set to 0 to disable")
    @Config.Name("PML Taming Over Leveled Start")
    public int pmlTamedOverLevelStartLevel = 20;

    @Config.Comment("If treat reputation will be penalized if creature is over leveled. Requires Mixin 'Tamed Invert Over Leveled Penalty', set to 0 to disable")
    @Config.Name("PML Taming Over Leveled Treat Point Penalty")
    public boolean pmlTamedOverLevelTreatPointPenalty = false;

    @Config.Comment("If creature could be tempted, will it remain able to be tempted if over leveled, Requires Mixin 'Tamed Invert Over Leveled Penalty', set to 0 to disable")
    @Config.Name("PML Taming Over Leveled Treat Tempt")
    public boolean pmlTamedOverLevelTreatTempt = false;

    public static HashMap<BonusCategory, Pair<BonusUsage, Double>> getPmlBonusCateogories(){
        if(PlayerMobLevelsConfig.pmlBonusCateogories == null){
            HashMap<BonusCategory, Pair<BonusUsage, Double>> map = new HashMap<>();
            for(String string : ForgeConfigHandler.server.pmlConfig.pmlCategories){
                String[] line = string.split(",");
                try {
                    BonusUsage usage = BonusUsage.ALL;
                    switch (line[1]){
                        case "TAMED":
                            usage = BonusUsage.TAMED;
                            break;
                        case "WILD":
                            usage = BonusUsage.WILD;
                            break;
                    }
                    map.put(BonusCategory.get(line[0]), new Pair<>(usage, Double.valueOf(line[2])));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in pmlCategories", string);
                }
            }
            PlayerMobLevelsConfig.pmlBonusCateogories = map;
        }
        return PlayerMobLevelsConfig.pmlBonusCateogories;
    }

    public static HashMap<Bonus, Double> getPmlBonusUsagesAll(){
        if(PlayerMobLevelsConfig.pmlBonusUsagesAll == null){
            HashMap<Bonus, Double> map = new HashMap<>();
            for(String string : ForgeConfigHandler.server.pmlConfig.pmlBonusAll){
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
            for(String string : ForgeConfigHandler.server.pmlConfig.pmlBonusTamed){
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
            for(String string : ForgeConfigHandler.server.pmlConfig.pmlBonusWild){
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

    public enum Bonus {

        ActivePet("ActivePet"),
        Bestiary("Bestiary"),
        Enchantments("Enchantments"),
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
        SoulboundTame("SoulboundTame"),
        SpawnerNatural("SpawnerNatural"),
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
                PlayerMobLevelsConfig.pmlBonusCateogories = null;
                PlayerMobLevelsConfig.pmlBonusUsagesAll = null;
                PlayerMobLevelsConfig.pmlBonusUsagesTamed = null;
                PlayerMobLevelsConfig.pmlBonusUsagesWild = null;

                ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
