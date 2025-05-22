package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;

public class CreatureStatsConfig {

    private static HashMap<String, Integer> effectsApplyScaleLevelLimited = null;
    private static HashMap<String, Integer> elementsApplyScaleLevelLimitedDebuffs = null;

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

    @Config.Comment("Grant all creatures tagged as SpawnedAsBoss the Rare variant stat multipliers instead of the Common/Uncommon\n" +
            "This will not automatically rebalance Dungeon Bosses. Default Lycanites distributes Bosses between level 10-250, watch out!")
    @Config.Name("Boost Spawned As Boss Non Rares")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurespawnedasbossrareboost.json")
    public boolean spawnedAsBossRareBoost = true;

    @Config.Comment("Invert bonus Health/Damage level scale for the Main Boss")
    @Config.Name("Boss Invert Health and Damage Scale")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinvertbossdamagehealthscale.json")
    public boolean bossInvertHealthDamageScale = true;

    @Config.Comment("Additionally apply to anything tagged with SpawnedAsBoss")
    @Config.Name("Boss Invert Health and Damage Scale - Apply to Spawned As Boss")
    public boolean spawnedAsBossInvert = true;

    @Config.Comment("SpawnedAsBoss and Main Boss HP level bonus scaled down via config")
    @Config.Name("Boss Lower Health Scale")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebossbonushealthmodifier.json")
    public boolean bossLowerHealthScale = true;

    @Config.Comment("Ratio of lycanites bonus Health main bosses will receive")
    @Config.Name("Boss Lower Health Scale - Main Boss Ratio")
    @Config.RangeDouble(min = 0)
    public double bossHealthBonusRatio = 0.25D;

    @Config.Comment("Ratio of lycanites bonus Health bosses will receive. This applies to non Rares")
    @Config.Name("Boss Lower Health Scale - Spawned As Boss Ratio No Rares")
    @Config.RangeDouble(min = 0)
    public double spawnedAsBossHealthBonusRatio = 0.5D;

    @Config.Comment("Ratio of lycanites bonus Health bosses will receive" +
            "\nThis applies to Rares who normally have higher stats and the DPS limit")
    @Config.Name("Boss Lower Health Scale - Spawned As Boss Ratio Rare")
    @Config.RangeDouble(min = 0)
    public double spawnedAsBossRareHealthBonusRatio = 0.5D;

    @Config.Comment("Add configurable caps to creature speed, effect durations, and pierce")
    @Config.Name("Cap Specific Stats")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurecreaturestatbonuscap.json")
    public boolean capSpecificStats = true;

    @Config.Comment("Ratio of max lycanites bonus defense, variants get more, set to 0 to disable the cap")
    @Config.Name("Cap Specific Stats - Creature Defense")
    @Config.RangeDouble(min = 0)
    public double capDefenseRatio = 4.0D;

    @Config.Comment("Ratio of max lycanites bonus effect duration, variants get more, set to 0 to disable the cap")
    @Config.Name("Cap Specific Stats - Creature Effect Duration")
    @Config.RangeDouble(min = 0)
    public double capEffectDurationRatio = 5.0D;

    @Config.Comment("Ratio of max lycanites bonus movement speed, variants get more, set to 0 to disable the cap")
    @Config.Name("Cap Specific Stats - Creature Movement Speed")
    @Config.RangeDouble(min = 0)
    public double capSpeedRatio = 3.0D;

    @Config.Comment("Ratio of max lycanites bonus pierce, variants get more, set to 0 to disable the cap")
    @Config.Name("Cap Specific Stats - Creature Pierce")
    @Config.RangeDouble(min = 0)
    public double capPierceRatio = 3.0D;

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
    @Config.Name("Cap Specific Stats - Effects Level Limited")
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

    @Config.Comment("List of loaded elements whose Debuffs that will have capped level scaling.\n" +
            "Format:[elementName,maxscaledlevel]\n" +
            "\telementName - Name of the element to limit, must be all lowercase\n" +
            "\tmaxscaledlevel - Final Level before duration and amplifier stop increasing")
    @Config.Name("Cap Specific Stats - Elements Level Limited Debuffs")
    public String[] elementsLevelLimitedDebuffs = {
            "arcane,15",
            "chaos,15",
            "lightning,15",
            "phase,15"
    };

    @Config.Comment("Invert bonus Health/Damage level scale for Hostile Minion Creatures")
    @Config.Name("Hostile Minion Invert Health and Damage Scale")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinverthostileminiondamagehealthscale.json")
    public boolean minionInvertHealthDamageScale = true;

    @Config.Comment("Summon minion goal matches host and minion levels (AI Goal/Most Mobs)")
    @Config.Name("Level match minions to creature host - summon goal")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureaiminionhostlevelmatch.json")
    public boolean levelMatchMinionsGoal = true;

    @Config.Comment("Rahovart/Asmodeus mechanic based minions match the boss' levels")
    @Config.Name("Level match minions to creature host - mechanics spawned")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurebasecreatureminionhostlevelmatch.json")
    public boolean levelMatchMinionsHostMethod = true;

    @Config.Comment("Invert bonus Health/Damage level scale for Tamed Creatures")
    @Config.Name("Tamed Invert Health and Damage Scale")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureinverttameddamagehealthscale.json")
    public boolean tamedInvertHealthDamageScale = true;

    @Config.Comment("Enable whether all tamed (tamed/summoned/soulbound) variants get stats bonuses\n" +
            "In vanilla Lycanites, only Soulbound variants were boosted due to bonus calc timing")
    @Config.Name("Tamed Variant Stat Bonuses")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featurealltamedvariantstats.json")
    public boolean tamedVariantStats = true;

    public static HashMap<String, Integer> getLevelLimitedEffects(){
        if(CreatureStatsConfig.effectsApplyScaleLevelLimited == null){
            HashMap<String, Integer> map = new HashMap<>();
            for(String string : ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.effectsLevelLimited){
                String[] line = string.split(",");
                try {
                    if(line[2].equals("true"))
                        map.put(line[0], Integer.valueOf(line[1]));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in effectsLevelLimited", string);
                }
            }
            CreatureStatsConfig.effectsApplyScaleLevelLimited = map;
        }
        return CreatureStatsConfig.effectsApplyScaleLevelLimited;
    }

    public static HashMap<String, Integer> getLevelLimitedElementDebuffs(){
        if(CreatureStatsConfig.elementsApplyScaleLevelLimitedDebuffs == null){
            HashMap<String, Integer> map = new HashMap<>();
            for(String string : ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.elementsLevelLimitedDebuffs){
                String[] line = string.split(",");
                try {
                    map.put(line[0], Integer.valueOf(line[1]));
                }
                catch (Exception exception){
                    LycanitesTweaks.LOGGER.error("Failed to parse {} in elementsLevelLimitedDebuffs", string);
                }
            }
            CreatureStatsConfig.elementsApplyScaleLevelLimitedDebuffs = map;
        }
        return CreatureStatsConfig.elementsApplyScaleLevelLimitedDebuffs;
    }

    @Mod.EventBusSubscriber(modid = LycanitesTweaks.MODID)
    private static class EventHandler{

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(LycanitesTweaks.MODID)) {
                CreatureStatsConfig.effectsApplyScaleLevelLimited = null;
                CreatureStatsConfig.elementsApplyScaleLevelLimitedDebuffs = null;
                ConfigManager.sync(LycanitesTweaks.MODID, Config.Type.INSTANCE);
            }
        }
    }
}
