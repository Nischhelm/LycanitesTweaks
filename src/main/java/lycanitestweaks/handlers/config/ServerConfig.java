package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class ServerConfig {

    @Config.Name("Additional Altars")
    @MixinConfig.SubInstance
    public final AltarsConfig altarsConfig = new AltarsConfig();

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

    public static class AltarsConfig {

        @Config.Comment("Register Beastiary Altar - Select any non boss creature from Beastiary to summon\n" +
                "\tInteract Block is Redstone above beacon layers of Obsidian")
        @Config.Name("Add Feature: Beastiary Altar")
        @Config.RequiresMcRestart
        @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientbeastiaryaltar.json")
        public boolean beastiaryAltar = true;

        @Config.Comment("Number of Beacon Style Layers required")
        @Config.Name("Add Feature: Beastiary Altar - Beacon Layers of Obsidian")
        @Config.RequiresMcRestart
        @Config.RangeInt(min = 0, max = 4)
        public int beastiaryAltarObsidian = 2;

        @Config.Comment("Beastiary Altar Minimum Creature Knowledge Rank Required")
        @Config.Name("Add Feature: Beastiary Altar - Minimum Knowledge Rank")
        public int beastiaryAltarKnowledgeRank = 2;

        @Config.Comment("Test example for a future Pull Request")
        @Config.Name("Add Feature: Zombie Horse Altar")
        @Config.RequiresMcRestart
        public boolean zombieHorseAltar = true;
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