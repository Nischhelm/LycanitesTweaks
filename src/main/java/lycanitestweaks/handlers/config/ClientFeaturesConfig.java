package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import net.minecraftforge.common.config.Config;

public class ClientFeaturesConfig {

    @Config.Comment("Dependency for adding new/hiding Beastiary information. Required for server-side to know what Creature players have selected.")
    @Config.Name("Modify Beastiary Information")
    @Config.RequiresMcRestart
    @MixinConfig.LateMixin(name = "mixins.lycanitestweaks.featureclientbeastiarylt.json")
    public boolean beastiaryGUILT = true;

    @Config.Comment("Adds a tab for Lycanites Altar renders and block counts")
    @Config.Name("Lycanites Mobs Altar Beastiary Tab")
    public boolean beastiaryGUIAltars = true;

    @Config.Comment("Adds a tab to show Player Mob Levels information")
    @Config.Name("LycanitesTweaks Player Mob Levels Beastiary Tab")
    public boolean beastiaryGUIPML = true;

    @Config.Comment("PML Beastiary Render order is determined by the order of this list.\n" +
            "\tcategoryName - Spelling must match 'Bonus Categories' entries else hidden\n" +
            "This will be compared to the existence of 'Bonus Categories' entries in the PML config")
    @Config.Name("Player Mob Levels Category Display Order")
    public String[] pmlBeastiaryOrder = {
            "AltarBossMain",
            "AltarBossMini",
            "DungeonBoss",
            "SpawnerNatural",
            "SpawnerTile",
            "SpawnerTrigger_",
            "EncounterEvent_",
            "SoulboundTame_",
            "SummonMinion"
    };

    @Config.Comment("Adds stats on Summoning tab that shows Imperfect Summoning information")
    @Config.Name("LycanitesTweaks Imperfect Summoning Tab")
    public boolean beastiaryGUIImperfectSummon = true;

    @Config.Comment("Case sensitive blacklist for hiding any Altar by name. Does not affect gameplay.")
    @Config.Name("Altar Display Blacklist")
    public String[] altarInfoBeastiaryBlacklist = {

    };

    @Config.Comment("Case sensitive blacklist for hiding any Creature by name. Does not affect gameplay.\n" +
            "Used by Lycanites Tweaks to hide easter eggs.")
    @Config.Name("Creature Display Blacklist")
    public String[] creatureInfoBeastiaryBlacklist = {
            "sonofamalgalich"
    };

    @Config.Comment("Case sensitive blacklist for hiding any Creature Subspecies by name. Does not affect gameplay.\n" +
            "Used by Lycanites Tweaks to hide easter eggs.")
    @Config.Name("Creature Subspecies Display Blacklist")
    public String[] creatureSubspeciesInfoBeastiaryBlacklist = {
            "darkling: 111"
    };

    @Config.Comment("Case sensitive blacklist for hiding any Element by name. Does not affect gameplay.")
    @Config.Name("Element Display Blacklist")
    public String[] elementInfoBeastiaryBlacklist = {
            "nightmare",
            "viral"
    };
}
