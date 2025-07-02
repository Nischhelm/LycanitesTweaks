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
    // TODO move everything here
    private static final Map<String, Set<String>> assetPaths = new HashMap<>();
    private static final Set<ResourceLocation> flowersaurBiomes = new HashSet<>();
    private static final List<PlayerMobLevelsConfig.BonusCategory> pmlBonusCategoryClientRenderOrder = new ArrayList<>();

    private static final Set<String> altarBeastiaryBlacklist = new HashSet<>();
    private static final Set<String> creatureBeastiaryBlacklist = new HashSet<>();
    private static final Map<String, Set<Integer>> creatureSubspeciesBeastiaryBlacklist = new HashMap<>();
    private static final Set<String> elementBeastiaryBlacklist = new HashSet<>();

    public static void pluginInit(){
        // initialise earlier for mixins that run on startup
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

        if(false) {
            ForgeConfigProvider.assetPaths.get("creatures").add("jsons/sonoftitans");
            ForgeConfigProvider.assetPaths.get("creatures").add("jsons/srp");
        }
    }

    public static void init(){
        //initialise always available (instead of lazy created) sets here
    }

    public static void reset() {
        ForgeConfigProvider.flowersaurBiomes.clear();
        ForgeConfigProvider.pmlBonusCategoryClientRenderOrder.clear();
        ForgeConfigProvider.altarBeastiaryBlacklist.clear();
        ForgeConfigProvider.creatureBeastiaryBlacklist.clear();
        ForgeConfigProvider.creatureSubspeciesBeastiaryBlacklist.clear();
        ForgeConfigProvider.elementBeastiaryBlacklist.clear();
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

    public static Set<String> getAltarBeastiaryBlacklist(){
        if(altarBeastiaryBlacklist.isEmpty() && ForgeConfigHandler.clientFeaturesMixinConfig.altarInfoBeastiaryBlacklist.length > 0)
            altarBeastiaryBlacklist.addAll(Arrays
                    .stream(ForgeConfigHandler.clientFeaturesMixinConfig.altarInfoBeastiaryBlacklist)
                    .collect(Collectors.toList()));
        return altarBeastiaryBlacklist;
    }

    public static Set<String> getCreatureBeastiaryBlacklist(){
        if(creatureBeastiaryBlacklist.isEmpty() && ForgeConfigHandler.clientFeaturesMixinConfig.creatureInfoBeastiaryBlacklist.length > 0)
            creatureBeastiaryBlacklist.addAll(Arrays
                    .stream(ForgeConfigHandler.clientFeaturesMixinConfig.creatureInfoBeastiaryBlacklist)
                    .collect(Collectors.toList()));
        return creatureBeastiaryBlacklist;
    }

    public static Map<String, Set<Integer>> getCreatureSubspeciesBeastiaryBlacklist(){
        if(creatureSubspeciesBeastiaryBlacklist.isEmpty() && ForgeConfigHandler.clientFeaturesMixinConfig.creatureSubspeciesInfoBeastiaryBlacklist.length > 0)
            creatureSubspeciesBeastiaryBlacklist.putAll(Arrays
                    .stream(ForgeConfigHandler.clientFeaturesMixinConfig.creatureSubspeciesInfoBeastiaryBlacklist)
                    .map(s -> s.split(":"))
                    .collect(Collectors.toMap(
                            split -> split[0].trim(),
                            split -> {
                                try {
                                    return Arrays.stream(split[1].split(",")).map(String::trim).map(Integer::valueOf).collect(Collectors.toSet());
                                } catch (Exception e){
                                    return new HashSet<>();
                                }
                            }
                    )));
        return creatureSubspeciesBeastiaryBlacklist;
    }

    public static Set<String> getElementBeastiaryBlacklist(){
        if(elementBeastiaryBlacklist.isEmpty() && ForgeConfigHandler.clientFeaturesMixinConfig.elementInfoBeastiaryBlacklist.length > 0)
            elementBeastiaryBlacklist.addAll(Arrays
                    .stream(ForgeConfigHandler.clientFeaturesMixinConfig.elementInfoBeastiaryBlacklist)
                    .collect(Collectors.toList()));
        return elementBeastiaryBlacklist;
    }
}
