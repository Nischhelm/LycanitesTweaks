package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.handlers.config.major.BossAmalgalichConfig;
import lycanitestweaks.handlers.config.major.BossAsmodeusConfig;
import lycanitestweaks.handlers.config.major.BossRahovartConfig;
import lycanitestweaks.handlers.config.major.CreatureInteractConfig;
import lycanitestweaks.handlers.config.major.CreatureStatsConfig;
import lycanitestweaks.handlers.config.major.EntityStoreCreatureConfig;
import lycanitestweaks.handlers.config.major.ImperfectSummoningConfig;
import lycanitestweaks.handlers.config.major.ItemTweaksConfig;
import lycanitestweaks.handlers.config.major.PlayerMobLevelsConfig;
import net.minecraftforge.common.config.Config;

public class MajorFeaturesConfig {

    @Config.Name("Creature Interactions")
    @MixinConfig.SubInstance
    public final CreatureInteractConfig creatureInteractConfig = new CreatureInteractConfig();

    @Config.Name("Crystal Stored Creature Entities")
    @MixinConfig.SubInstance
    public final EntityStoreCreatureConfig escConfig = new EntityStoreCreatureConfig();

    @Config.Name("Player Mob Levels Bonus")
    @MixinConfig.SubInstance
    public final PlayerMobLevelsConfig pmlConfig = new PlayerMobLevelsConfig();

    @Config.Name("Enhanced Amalgalich")
    @MixinConfig.SubInstance
    public final BossAmalgalichConfig amalgalichConfig = new BossAmalgalichConfig();

    @Config.Name("Enhanced Asmodeus")
    @MixinConfig.SubInstance
    public final BossAsmodeusConfig asmodeusConfig = new BossAsmodeusConfig();

    @Config.Name("Enhanced Rahovart")
    @MixinConfig.SubInstance
    public final BossRahovartConfig rahovartConfig = new BossRahovartConfig();

    @Config.Name("Imperfect Summoning")
    @MixinConfig.SubInstance
    public final ImperfectSummoningConfig imperfectSummoningConfig = new ImperfectSummoningConfig();

    @Config.Name("Tweak Creature Stats")
    @MixinConfig.SubInstance
    public final CreatureStatsConfig creatureStatsConfig = new CreatureStatsConfig();

    @Config.Name("Vanilla Lycanites Item Tweaks")
    @MixinConfig.SubInstance
    public final ItemTweaksConfig itemTweaksConfig = new ItemTweaksConfig();
}
