package lycanitestweaks.handlers.config;

import net.minecraftforge.common.config.Config;

public class EntityStoreCreatureConfig {

    @Config.Comment("Boss Crystals check if Player Mob Levels is higher than stored Entity's levels")
    @Config.Name("Boss Crystal Player Mob Levels")
    public boolean bossCrystalPML = true;

    @Config.Comment("Require a held main hand Soulgazer in order to scale to Player Mob Levels")
    @Config.Name("Boss Crystal Player Mob Levels Requires Soulgazer")
    public boolean bossCrystalSoulgazerHold = true;

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

    @Config.Comment("Require a held main hand Soulgazer in order to scale to Player Mob Levels")
    @Config.Name("Encounter Crystal Player Mob Levels Requires Soulgazer")
    public boolean encounterCrystalSoulgazerHold = false;

    @Config.Comment("Enable Capability to replicate LycanitesMobs PetEntry for any owner")
    @Config.Name("Entity Store Creature Capability")
    @Config.RequiresMcRestart
    public boolean entityStoreCreatureCapability = true;
}
