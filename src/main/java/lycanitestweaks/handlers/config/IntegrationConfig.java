package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class IntegrationConfig {

    @Config.Comment("Allows Soulgazers to be worn as a bauble. Includes keybinds to enable auto/right clicks.")
    @Config.Name("Soulgazer Bauble (BaublesAPI)")
    @Config.RequiresMcRestart
    public boolean soulgazerBauble = true;

    @Config.Comment("If true, Soulgazers will only be equippable into the charm slot. Else any slot can be used.")
    @Config.Name("Soulgazer Bauble Charm")
    public boolean soulgazerBaubleCharm = true;

    @Config.Comment("Sets Ender Pearls as the repair material")
    @Config.Name("Soulgazer Bauble Ender Pearl Reforge")
    public boolean soulgazerBaubleRepairMaterial = true;

    @Config.Comment("Adds Distinct Damage Descriptions information to Beastiary")
    @Config.Name("Beastiary Info (Distinct Damage Descriptions)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "distinctdamagedescriptions", reason = "Dependency Missing")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientbeastiaryddd.json")
    public boolean beastiaryGUIDDD = true;

    @Config.Comment("Allows love arrows breeding to apply on Lycanites animals")
    @Config.Name("Love Arrow Fix (Switch-Bow)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "switchbow", reason = "Dependency Missing")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.switchboxlovearrowfix.json")
    public boolean switchbowLoveArrowFix = true;

    @Config.Comment("Fix Potion Core forcibly overwriting BaseCreatureEntity motionY ")
    @Config.Name("Potion Core Jump Fix (Potion Core)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "potioncore", reason = "Dependency Missing")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.potioncorejumpfix.json")
    public boolean potionCoreJumpFix = true;

    @Config.Comment("Whether to affect all mobs - otherwise only LycanitesMobs entities are affected")
    @Config.Name("Potion Core Jump Fix - All Mobs")
    public boolean fixAllMobsPotionCoreJump = true;

    /*
     *
     * Temporary Mod Compatibility Mixins, things I should really make a PR for
     *
     */

    @Config.Comment("Makes Crafted Equipment reach stat influence ReachFix attack range")
    @Config.Name("Crafted Equipment Bonus ReachFix Range (ReachFix)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "reachfix", reason = "ReachFix not found")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.equipmentreachfix.json")
    public boolean craftedEquipmentReachFix = true;

    @Config.Comment("Cancels Custom Sweep and rehandle with RLCombat Sweep")
    @Config.Name("Crafted Equipment RLCombat Sweep (RLCombat)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "bettercombatmod", reason = "RLCombat not found")
//		@MixinConfig.LateMixin(name = "mixins.lycanitestweaks.rlcombatequipmentsweep.json")
    public boolean craftedEquipmentRLCombatSweep = true;

    @Config.Comment("A lazy way to get the desired behavior. You should be using RLCombat's config.")
    @Config.Name("Force Crafted Equipment Offhand Attack Whitelist (RLCombat)")
    @Config.RequiresMcRestart
//		@MixinConfig.CompatHandling(modid = "bettercombatmod", reason = "RLCombat not found")
//		@MixinConfig.LateMixin(name = )
    public boolean craftedEquipmentForceRLCombatOffhand = true;
}
