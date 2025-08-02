package lycanitestweaks.handlers.config.server;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.common.config.Config;

@MixinConfig(name = LycanitesTweaks.MODID)
public class CustomStaffConfig {

    @Config.Comment("Adds and registers the Charge Staff, essentially a Vanilla Bow that uses Lycanites Charges as ammo.\n" +
            "Allows many Bow bonuses and enchantments to apply to charge throwing in a simple manner.")
    @Config.Name("0. Register Charge Staffs")
    @Config.RequiresMcRestart
    public boolean registerChargeStaffs = true;

    @Config.Comment("Charge Staffs momentarily spawn in an arrow so that other mods may modify it in order to apply bonuses to charge projectiles.\n" +
            "This toggle controls whether these arrows can persist to apply effects that charge projectiles are unable to copy, such as knockback properties.")
    @Config.Name("Charge Staffs Arrows")
    public boolean chargeStaffArrowsWorld = false;

    @Config.Comment("Charge Staffs momentarily spawn in an arrow so that other mods may modify it in order to apply bonuses to charge projectiles.\n" +
            "This toggle controls whether these arrows teleport every tick to the charge the arrow is linked to.")
    @Config.Name("Charge Staffs Arrows Follow Charge")
    public boolean chargeStaffArrowsTeleport = false;

    @Config.Comment("Whether Charge Staffs can have enchantments")
    @Config.Name("Charge Staffs Enchantability")
    public boolean chargeStaffEnchantability = true;

    @Config.Comment("List of enchants to blacklist from being applicable to Charge Staffs\n" +
            "Format: [modid: path]")
    @Config.Name("Charge Staff Enchantments Blacklist")
    public String[] blacklistedChargeStaffEnchants = {
            "minecraft:infinity",
            "minecraft:flame",
            "minecraft:punch",
            "mujmajnkraftsbettersurvival:arrowrecovery",
            "mujmajnkraftsbettersurvival:blast",
            "mujmajnkraftsbettersurvival:multishot",
            "somanyenchantments:lesserflame",
            "somanyenchantments:advancedflame",
            "somanyenchantments:supremeflame",
            "somanyenchantments:advancedpunch",
            "somanyenchantments:rune_arrowpiercing",
            "somanyenchantments:splitshot"
    };
}
