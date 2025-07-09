package lycanitestweaks.handlers.config.major;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.LycanitesTweaks;
import net.minecraftforge.common.config.Config;

@MixinConfig(name = LycanitesTweaks.MODID)
public class EntityStoreCreatureConfig {

    @Config.Comment("Enable Capability to replicate Lycanites Mobs PetEntry for non players")
    @Config.Name("0. Entity Store Creature")
    @Config.RequiresMcRestart
    public boolean entityStoreCreatureCapability = true;

    @Config.Comment("Store Altar mini bosses in a summon crystal entity")
    @Config.Name("Altar Mini Boss Summon Crystal")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featurealtarminibosscrystal.json")
    public boolean altarMiniBossSpawnCrystal = true;

    @Config.Comment("Store Dungeon bosses in a summon crystal entity")
    @Config.Name("Dungeon Boss Summon Crystal")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featuredungeonbosscrystal.json")
    public boolean dungeonBossSpawnCrystal = true;

    @Config.Comment("Randomly store some Mob Event spawns in an Encounter Crystal, will flag entity as SpawnedAsBoss")
    @Config.Name("Encounter Crystal Mob Event")
    @Config.RequiresMcRestart
    @MixinConfig.MixinToggle(defaultValue = true, lateMixin = "mixins.lycanitestweaks.featuremobeventencountercrystal.json")
    public boolean encounterCrystalMobEvent = true;

    @Config.Comment("1/n chance store a Mob Event spawned entity inside an Encounter Crystal")
    @Config.Name("Encounter Crystal Mob Event - Spawn Chance")
    @Config.RangeInt(min = 1)
    public int encounterCrystalSpawnChance = 50;

    @Config.Comment("Apply the Rare/Boss Damage Limit only if the stored creature is flagged as SpawnedAsBoss" +
            "\nThis grants partial consistency for non Rare Dungeon bosses who did not have this property in vanilla Lycanites" +
            "\nThis option does not grant the Rare stat boost (Creature stats config controls that option)")
    @Config.Name("Boss Crystal SpawnedAsBoss Damage Limit")
    public boolean bossCrystalSpawnedAsBossDamageLimit = true;

    @Config.Comment("Enable logic to automatically release stored Entity (Checks every second)")
    @Config.Name("Boss Crystal Tick Checks")
    public boolean bossCrystalTickChecks = true;

    @Config.Comment("1/n Chance to despawn per second, set to 0 to disable random despawning (Forced Despawning happens when further than 128 blocks)")
    @Config.Name("Encounter Crystal Despawn Chance")
    @Config.RangeInt(min = 0)
    public int encounterCrystalDespawnChance = 0;
}
