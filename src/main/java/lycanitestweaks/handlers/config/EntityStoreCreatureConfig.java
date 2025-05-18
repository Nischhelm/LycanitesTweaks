package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class EntityStoreCreatureConfig {

    @Config.Comment("Apply the Rare/Boss Damage Limit only if the stored creature is flagged to spawn as a boss" +
            "\nThis grants partial consistency for non Rare Dungeon bosses who did not have this property in vanilla Lycanites" +
            "\nThis option does not grant the Rare stat boost")
    @Config.Name("Boss Crystal SpawnedAsBoss Damage Limit")
    public boolean bossCrystalSpawnedAsBossDamageLimit = true;

    @Config.Comment("Enable logic to automatically release stored Entity (Checks every second)")
    @Config.Name("Boss Crystal Tick Checks")
    public boolean bossCrystalTickChecks = true;

    @Config.Comment("1/n Chance to despawn per second, set to 0 to disable random despawning (Forced Despawning happens when further than 128 blocks)")
    @Config.Name("Encounter Crystal Despawn Chance")
    @Config.RangeInt(min = 0)
    public int encounterCrystalDespawnChance = 0;

    @Config.Comment("1/n chance store a Mob Event spawned entity inside an Encounter Crystal, Requires Mixin 'Encounter Crystal Mob Event'")
    @Config.Name("Encounter Crystal Spawn Chance")
    @Config.RangeInt(min = 1)
    public int encounterCrystalSpawnChance = 50;

    @Config.Comment("Enable Capability to replicate LycanitesMobs PetEntry for any owner")
    @Config.Name("Entity Store Creature Capability")
    @Config.RequiresMcRestart
    public boolean entityStoreCreatureCapability = true;
}
