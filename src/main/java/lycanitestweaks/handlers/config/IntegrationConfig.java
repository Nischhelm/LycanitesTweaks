package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class IntegrationConfig {

    // TODO Better bauble config
    @Config.Comment("SoulgazerSoulgazerSoulgazerSoulgazerSoulgazer")
    @Config.Name("Mod Compatibility: Soulgazer Bauble (BaublesAPI)")
    @Config.RequiresMcRestart
    public boolean soulgazerBauble = true;

    @Config.Comment("Charm Else All")
    @Config.Name("Mod Compatibility: Soulgazer Bauble Charm")
    public boolean soulgazerBaubleCharm = true;

    @Config.Comment("Ender Pearl")
    @Config.Name("Mod Compatibility: Soulgazer Bauble Repair Material")
    public boolean soulgazerBaubleRepairMaterial = true;

    @Config.Comment("Adds Distinct Damage Descriptions Information to Beastiary")
    @Config.Name("Mod Compatibility: Beastiary Info (Distinct Damage Descriptions)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "distinctdamagedescriptions", reason = "Dependency Missing")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientbeastiaryddd.json")
    public boolean beastiaryGUIDDD = true;

    @Config.Comment("Allows love arrows to make Lycanites animals breed")
    @Config.Name("Mod Compatibility: Love Arrow Fix (Switch-Bow)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "switchbow", reason = "Dependency Missing")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.switchboxlovearrowfix.json")
    public boolean switchbowLoveArrowFix = true;

    @Config.Comment("Fix Potion Core forcibly overwriting BaseCreatureEntity motionY ")
    @Config.Name("Mod Compatibility: Potion Core Jump Fix (Potion Core)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "potioncore", reason = "Dependency Missing")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.potioncorejumpfix.json")
    public boolean potionCoreJumpFix = true;

    @Config.Comment("Whether to affect all mobs - otherwise only LycanitesMobs entities are affected")
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
//		@MixinConfig.CompatHandling(modid = "reachfix", reason = "ReachFix not found")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentreachfix.json")
    public boolean craftedEquipmentReachFix = true;

    @Config.Comment("Cancels Custom Sweep and rehandle with RLCombat Sweep")
    @Config.Name("Mod Compatibility: Crafted Equipment RLCombat Sweep (RLCombat)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "bettercombatmod", reason = "RLCombat not found")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentrlcombatsweep.json")
    public boolean craftedEquipmentRLCombatSweep = true;

    // TODO add either forced rlcombat offhand or optional spam the log option
}
