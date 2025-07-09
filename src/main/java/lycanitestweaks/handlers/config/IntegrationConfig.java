package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class IntegrationConfig {

    @Config.Comment("Allows Soulgazers to be worn as a bauble. Includes keybinds to enable auto/right clicks.")
    @Config.Name("3. Soulgazer Bauble (BaublesAPI)")
    @Config.RequiresMcRestart
    public boolean soulgazerBauble = true;

    @Config.Comment("If true, Soulgazers will only be equippable into the charm slot. Else any slot can be used.")
    @Config.Name("3.a Soulgazer Bauble Charm")
    public boolean soulgazerBaubleCharm = true;

    @Config.Comment("Sets Ender Pearls as the repair material")
    @Config.Name("3.a Soulgazer Bauble Ender Pearl Reforge")
    public boolean soulgazerBaubleRepairMaterial = true;

    @Config.Comment("Adds Distinct Damage Descriptions information to Beastiary")
    @Config.Name("DDD Beastiary Info (Distinct Damage Descriptions)")
    @Config.RequiresMcRestart
    public boolean beastiaryGUIDDD = true;

    @Config.Comment("Load a 1% chance 6400 tick cycling rain+storm spawner for 'iceandfire:lightningdragon'.\n" +
            "This will try to spawn a copper armored stage 5 dragon for any Ice and Fire version that uses this mobId.\n" +
            "This is safe to load even if Ice and Fire is not installed.\n" +
            "Provides example of usage of \"lycanitestweaks:setNBT\" with special 'lycanitestweaksDoInitialSpawn' tag.")
    @Config.Name("I&F Copper and Lightning JSON Spawner (Ice and Fire)")
    @Config.RequiresMcRestart
    public boolean infLightingDragonSpawner = true;

    @Config.Comment("Allows love arrows breeding to apply on Lycanites animals")
    @Config.Name("Love Arrow Fix (Switch-Bow)")
    @Config.RequiresMcRestart
    public boolean switchbowLoveArrowFix = true;

    @Config.Comment("Fix Potion Core forcibly overwriting BaseCreatureEntity motionY ")
    @Config.Name("Potion Core Jump Fix (Potion Core)")
    @Config.RequiresMcRestart
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
    @Config.Name("2. Crafted Equipment Bonus ReachFix Range (ReachFix)")
    @Config.RequiresMcRestart
    public boolean craftedEquipmentReachFix = true;

    @Config.Comment("Cancels Custom Sweep and rehandle with RLCombat Sweep")
    @Config.Name("1. Crafted Equipment RLCombat Sweep (RLCombat)")
    @Config.RequiresMcRestart
    public boolean craftedEquipmentRLCombatSweep = true;

    @Config.Comment("A lazy way to get the desired behavior. You should be using RLCombat's config.")
    @Config.Name("1.a Force Crafted Equipment Offhand Attack Whitelist (RLCombat)")
    @Config.RequiresMcRestart
    public boolean craftedEquipmentForceRLCombatOffhand = true;
}
