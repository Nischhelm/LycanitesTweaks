package lycanitestweaks.handlers;

import lycanitestweaks.handlers.config.major.PlayerMobLevelsConfig;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ForgeConfigProvider {
    private static final Map<String, Set<String>> assetPaths = new HashMap<>();
    private static final Set<ResourceLocation> flowersaurBiomes = new HashSet<>();
    private static final List<PlayerMobLevelsConfig.BonusCategory> pmlBonusCategoryClientRenderOrder = new ArrayList<>();

    public static void pluginInit(){
        // initialise earlier for mixins that run on startup
        // TODO Like PR, have custom assets be prepended to sort file names on disk.
        ForgeConfigProvider.assetPaths.put("creaturetypes", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("creaturegroups", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("creatures", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("elements", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("items", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("projectiles", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("equipment", new HashSet<>());

        ForgeConfigProvider.assetPaths.put("dungeons/themes", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("dungeons/structures", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("dungeons/sectors", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("dungeons/schematics", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("mobevents_events", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("mobevents_spawners", new HashSet<>());
        ForgeConfigProvider.assetPaths.put("spawners", new HashSet<>());
        ForgeConfigProvider.assetPaths.get("elements").add("jsons/bosselements");
        ForgeConfigProvider.assetPaths.get("projectiles").add("jsons/bossprojectiles");

        if(ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.spawnedAsBossRareBoost)
            ForgeConfigProvider.assetPaths.get("dungeons/schematics").add("jsons/rebalancedungeons/schematics");
        if(ForgeConfigHandler.server.altarsConfig.witheringHeightsAltar)
            ForgeConfigProvider.assetPaths.get("mobevents_events").add("jsons/witheraltar");
    }

    public static void init(){
        //initialise always available (instead of lazy created) sets here
    }

    public static void reset() {
        ForgeConfigProvider.flowersaurBiomes.clear();
        ForgeConfigProvider.pmlBonusCategoryClientRenderOrder.clear();
        init();
    }

    public static Set<String> getAssetPathSetFor(String lycanitesAssetPath){
        if(assetPaths.containsKey(lycanitesAssetPath)) return assetPaths.get(lycanitesAssetPath);
        return null;
    }

    public static Set<ResourceLocation> getFlowersaurBiomes(){
        if(flowersaurBiomes.isEmpty() && ForgeConfigHandler.minorFeaturesConfig.flowersaurSpawningBiomes.length > 0)
            flowersaurBiomes.addAll(Arrays
                    .stream(ForgeConfigHandler.minorFeaturesConfig.flowersaurSpawningBiomes)
                    .map(ResourceLocation::new)
                    .collect(Collectors.toSet()));
        return flowersaurBiomes;
    }

    public static List<PlayerMobLevelsConfig.BonusCategory> getPmlBonusCategoryClientRenderOrder(){
        if(pmlBonusCategoryClientRenderOrder.isEmpty() && ForgeConfigHandler.clientFeaturesMixinConfig.pmlBeastiaryOrder.length > 0)
            pmlBonusCategoryClientRenderOrder.addAll(Arrays
                    .stream(ForgeConfigHandler.clientFeaturesMixinConfig.pmlBeastiaryOrder)
                    .map(PlayerMobLevelsConfig.BonusCategory::get)
                    .collect(Collectors.toList()));
        return pmlBonusCategoryClientRenderOrder;
    }
}
