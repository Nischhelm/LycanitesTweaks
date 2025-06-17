package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class MinorFeaturesConfig {
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
