package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class EntityStoreCreatureConfig {

    @Config.Comment("Enable logic to automatically release stored Entity (Checks every second)")
    @Config.Name("Boss Crystal Tick Checks")
    public boolean bossCrystalTickChecks = true;

    @Config.Comment("1/n Chance to despawn per second, set to 0 to disable random despawning (Forced Despawning happens when further than 128 blocks)")
    @Config.Name("Encounter Crystal Despawn Chance")
    @Config.RangeInt(min = 0)
    public int encounterCrystalDespawnChance = 0;

    @Config.Comment("Minimum Idle before a vanilla like despawn can happen, Requires Mixin 'Encounter Natural Spawn Summon Crystal'")
    @Config.Name("Encounter Crystal Minimum Idle Time")
    @Config.RangeInt(min = 0)
    public int encounterCrystalIdleTime = 600;

    @Config.Comment("1/n chance to despawn and store inside an Encounter Crystal, Requires Mixin 'Encounter Natural Spawn Summon Crystal'")
    @Config.Name("Encounter Crystal Spawn Chance")
    @Config.RangeInt(min = 1)
    public int encounterCrystalSpawnChance = 800;

    @Config.Comment("Tick Rate to attempt forcing a vanilla like despawn and store the creature, Requires Mixin 'Encounter Natural Spawn Summon Crystal'")
    @Config.Name("Encounter Crystal Spawn Tick Rate")
    @Config.RangeInt(min = 1)
    public int encounterCrystalSpawnTickRate = 20;

    @Config.Comment("Enable Capability to replicate LycanitesMobs PetEntry for any owner")
    @Config.Name("Entity Store Creature Capability")
    @Config.RequiresMcRestart
    public boolean entityStoreCreatureCapability = true;

    @Config.Comment("Apply the Rare/Boss Damage Limit only if the stored creature is flagged to spawn as a boss" +
            "\nThis grants partial consistency for non Rare Dungeon bosses who did not have this property in vanilla Lycanites" +
            "\nThis option does not grant the Rare stat boost")
    @Config.Name("SpawnedAsBoss Damage Limit")
    public boolean spawnedAsBossDamageLimit = true;
}
