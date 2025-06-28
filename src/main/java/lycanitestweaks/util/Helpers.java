package lycanitestweaks.util;

import baubles.api.BaublesApi;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.info.ElementInfo;
import com.lycanitesmobs.core.info.Variant;
import com.lycanitesmobs.core.item.ChargeItem;
import com.lycanitesmobs.core.item.equipment.ItemEquipment;
import com.lycanitesmobs.core.item.equipment.features.EffectEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.EquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.ProjectileEquipmentFeature;
import com.lycanitesmobs.core.item.equipment.features.SummonEquipmentFeature;
import com.lycanitesmobs.core.item.special.ItemSoulgazer;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class Helpers {

    private static HashMap<String, ArrayList<String>> chargeElementsMap = null;
    private static HashMap<String, ArrayList<String>> creatureElementsMap = null;


    /**
     * Check uses for determining legacy or intended behavior via config
     * @return Whether to apply extra multipliers such as Variant and NBT
     */
    public static boolean shouldApplyExtraMultipliers(BaseCreatureEntity creature){
        if(creature.isTamed()) {
            if(!ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.variantStatReceivers) return false;

            if (creature.isBoundPet()) {
                if (creature.getPetEntry().getType().equals("familiar")) return false;
                return ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.variantStatsSoulbound;
            }

            if (creature.isMinion()) return ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.variantStatsSummoned;

            return ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.variantStatsTamed;
        }
        return true;
    }

    // mfw Lycanites config for no flying mount doesn't catch mobs whose flight check considers landed state
    public static boolean isPracticallyFlying(BaseCreatureEntity entity){
        return (entity.isFlying() || entity.flySoundSpeed > 0);
    }

    public static boolean hasSoulgazerEquiped(EntityLivingBase target){
        return Helpers.hasSoulgazerEquiped(target, false);
    }

    public static boolean hasSoulgazerEquiped(EntityLivingBase target, boolean ignoreHand){
        if(!ignoreHand && target.getHeldItemMainhand().getItem() instanceof ItemSoulgazer) return true;

        if(target instanceof EntityPlayer) {
            int baubleFound = BaublesApi.isBaubleEquipped((EntityPlayer) target, ObjectManager.getItem("soulgazer"));
            if (baubleFound != -1) return true;
        }

        return false;
    }

    public static void cureActiveEffectsFromResourceSet(EntityLivingBase entity, Set<ResourceLocation> curingSet){
        Set<Potion> potionsToRemove = entity.getActivePotionEffects().stream()
                .map(PotionEffect::getPotion)
                .filter(potion -> curingSet.contains(potion.getRegistryName()))
                .collect(Collectors.toSet());
        potionsToRemove.forEach(entity::removePotionEffect);
    }

    // Performs hit effect without dealing damage
    public static void doEquipmentHitEffect(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker){
        if(!(itemStack.getItem() instanceof ItemEquipment)) return;

        ItemEquipment lycanitesEquipment = (ItemEquipment)itemStack.getItem();

        boolean usedMana = false;

        for(EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(itemStack, "effect")) {
            EffectEquipmentFeature effectFeature = (EffectEquipmentFeature)equipmentFeature;
            effectFeature.onHitEntity(itemStack, target, attacker);
        }

        for(EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(itemStack, "summon")) {
            SummonEquipmentFeature summonFeature = (SummonEquipmentFeature)equipmentFeature;
            usedMana = summonFeature.onHitEntity(itemStack, target, attacker) || usedMana;
        }

        for(EquipmentFeature equipmentFeature : lycanitesEquipment.getFeaturesByType(itemStack, "projectile")) {
            ProjectileEquipmentFeature projectileFeature = (ProjectileEquipmentFeature)equipmentFeature;
            usedMana = projectileFeature.onHitEntity(itemStack, target, attacker) || usedMana;
        }

        if (usedMana) {
          lycanitesEquipment.removeMana(itemStack, 1);
        }
    }

    public static void removeAllPositiveEffects(EntityLivingBase entity){
        ArrayList<Potion> toRemove = new ArrayList<>();

        for(PotionEffect effect : entity.getActivePotionEffects())
            if(!effect.getPotion().isBadEffect()) toRemove.add(effect.getPotion());

        for(Potion potion : toRemove) entity.removePotionEffect(potion);
    }

    public static HashMap<String, ArrayList<String>> getChargeElementsMap(){
        if(Helpers.chargeElementsMap == null){
            Helpers.chargeElementsMap = new HashMap<>();
            ObjectManager.items.forEach((name, item) -> {
                if(item instanceof ChargeItem){
                    ((ChargeItem) item).getElements().forEach(elementInfo -> {
                        String elementString = elementInfo.name;
                        String chargeString = ((ChargeItem) item).itemName.trim();
                        ArrayList<String> projectiles;

                        if(!Helpers.chargeElementsMap.containsKey(elementString)) projectiles = new ArrayList<>();
                        else projectiles = Helpers.chargeElementsMap.get(elementString);
                        projectiles.add(chargeString);
                        Helpers.chargeElementsMap.put(elementString, projectiles);
                    });
                }
            });
            if(ForgeConfigHandler.client.debugLoggerAutomatic) LycanitesTweaks.LOGGER.log(Level.INFO, "chargeElementsMap: {}", Helpers.chargeElementsMap);
        }
        return Helpers.chargeElementsMap;
    }

    public static HashMap<String, ArrayList<String>> getCreatureElementsMap(){
        if(Helpers.creatureElementsMap == null){
            Helpers.creatureElementsMap = new HashMap<>();
            CreatureManager.getInstance().creatures.forEach((creatureName, creatureInfo) -> {
                for(ElementInfo elementInfo : creatureInfo.elements){
                    ArrayList<String> creatures;

                    if(!Helpers.creatureElementsMap.containsKey(elementInfo.name)) creatures = new ArrayList<>();
                    else creatures = Helpers.creatureElementsMap.get(elementInfo.name);
                    creatures.add(creatureName);
                    Helpers.creatureElementsMap.put(elementInfo.name, creatures);
                }
            });
            if(ForgeConfigHandler.client.debugLoggerAutomatic) LycanitesTweaks.LOGGER.log(Level.INFO, "creatureElementsMap: {}", Helpers.creatureElementsMap);
        }
        return Helpers.creatureElementsMap;
    }

    public static double getImperfectHostileChance(ExtendedPlayer extendedPlayer, CreatureInfo creatureInfo){
        double hostileChance = ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectHostileBaseChance;
        if (ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectHostileBaseChance != 0.0D && extendedPlayer.getBeastiary().hasKnowledgeRank(creatureInfo.getName(), 1)) {
            hostileChance -= extendedPlayer.getBeastiary().getCreatureKnowledge(creatureInfo.getName()).experience * ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectHostileChanceModifier;
        }
        return hostileChance;
    }

    public static double getImperfectStatsChance(ExtendedPlayer extendedPlayer, CreatureInfo creatureInfo){
        double lowerStatsChance = ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectStatsBaseChance;
        if(ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectStatsChanceModifier != 0.0D && extendedPlayer.getBeastiary().hasKnowledgeRank(creatureInfo.getName(), 1)){
            lowerStatsChance -= extendedPlayer.getBeastiary().getCreatureKnowledge(creatureInfo.getName()).experience * ForgeConfigHandler.majorFeaturesConfig.imperfectSummoningConfig.imperfectStatsChanceModifier;
        }
        return lowerStatsChance;
    }

    // Extracted from com.lycanitesmobs.core.entity.BaseCreatureEntity
    /** Returns the default starting level to use. **/
    public static int getStartingLevel(EntityLivingBase entity) {
        int startingLevelMin = Math.max(1, CreatureManager.getInstance().config.startingLevelMin);
        if(CreatureManager.getInstance().config.startingLevelMax > startingLevelMin) {
            return startingLevelMin + entity.getRNG().nextInt(CreatureManager.getInstance().config.startingLevelMax - startingLevelMin);
        }
        if(CreatureManager.getInstance().config.levelPerDay > 0 && CreatureManager.getInstance().config.levelPerDayMax > 0) {
            int day = (int)Math.floor(entity.getEntityWorld().getTotalWorldTime() / 23999D);
            double levelGain = Math.min(CreatureManager.getInstance().config.levelPerDay * day, CreatureManager.getInstance().config.levelPerDayMax);
            startingLevelMin += (int)Math.floor(levelGain);
        }
        if(CreatureManager.getInstance().config.levelPerLocalDifficulty > 0) {
            double levelGain = entity.getEntityWorld().getDifficultyForLocation(entity.getPosition()).getAdditionalDifficulty();
            startingLevelMin += Math.max(0, (int)Math.floor(levelGain - 1.5D));
        }
        return startingLevelMin;
    }

    /** Attempt to get a level limited stat. **/
    public static int getEffectDurationLevelLimited(BaseCreatureEntity creature, int duration, int levelCap) {
        String statName = "EFFECT";
        double statValue = creature.creatureInfo.effectDuration;

        // Wild:
        if(Helpers.shouldApplyExtraMultipliers(creature)) {
            statValue *= Helpers.getDifficultyMultiplier(creature.getEntityWorld(), statName);
            statValue *= Helpers.getVariantMultiplier(creature, statName);
            if(creature.extraMobBehaviour != null) {
                statValue *= creature.extraMobBehaviour.multiplierEffect;
                statValue += creature.extraMobBehaviour.boostEffect;
            }
        }

        statValue *= Helpers.getLevelMultiplier(statName, creature, levelCap);

        // Going to suck when I forget to sync with the Mixin stat caps
        if(ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capEffectDurationRatio > 0) {
            statValue = Math.min(statValue,
                    ForgeConfigHandler.majorFeaturesConfig.creatureStatsConfig.capEffectDurationRatio
                            * creature.creatureInfo.effectDuration
                            * Helpers.getVariantMultiplier(creature, statName)
            );
        }
        return (int)Math.round(duration * statValue * 20);
    }

    public static int getEffectAmplifierLevelLimited(BaseCreatureEntity creature, float amplifier, int levelCap) {
        String statName = "AMPLIFIER";
        double statValue = creature.creatureInfo.effectAmplifier;

        // Wild:
        if(Helpers.shouldApplyExtraMultipliers(creature)) {
            statValue *= Helpers.getDifficultyMultiplier(creature.getEntityWorld(), statName);
            statValue *= Helpers.getVariantMultiplier(creature, statName);
        }

        statValue *= Helpers.getLevelMultiplier(statName, creature, levelCap);
        // Lycanites doesn't use amplifier scale
        return (int) Math.round(statValue);
    }

    // Extracted methods from Lycanites
    /**
     * Returns a difficulty stat multiplier for the provided stat name and the world that the entity is in.
     * @param world The world to check difficulty from.
     * @param stat The name of the stat to get the multiplier for. The entire string must be Upper Case
     * @return The stat multiplier.
     */
    public static double getDifficultyMultiplier(World world, String stat) {
        EnumDifficulty difficulty = world.getDifficulty();
        String difficultyName = "EASY";
        if(difficulty.getId() >= 3)
            difficultyName = "HARD";
        else if(difficulty == EnumDifficulty.NORMAL)
            difficultyName = "NORMAL";
        return CreatureManager.getInstance().getDifficultyMultiplier(difficultyName, stat);
    }

    /**
     * Returns a variant stat multiplier for the provided stat name and the variant that the entity is.
     * @param creature The creature to check
     * @param stat The name of the stat to get the multiplier for. The entire string must be Upper Case
     * @return The stat multiplier.
     */
    public static double getVariantMultiplier(BaseCreatureEntity creature, String stat) {
        if(creature.getVariant() != null && Variant.STAT_MULTIPLIERS.containsKey(creature.getVariant().rarity.toUpperCase(Locale.ENGLISH) + "-" + stat)) {
            return Variant.STAT_MULTIPLIERS.get(creature.getVariant().rarity.toUpperCase(Locale.ENGLISH) + "-" + stat);
        }
        return 1;
    }

    /**
     * Returns a level stat multiplier for the provided stat name and the creature's current level.
     * @param stat The name of the stat to get the multiplier for. The entire string must be Upper Case
     * @param creature The creature to check
     * @param maxLevel The max level scaling can go up to
     * @return The stat multiplier.
     */
    public static double getLevelMultiplier(String stat, BaseCreatureEntity creature, int maxLevel) {
        double statLevel = Math.max(0, Math.min(maxLevel - 1, creature.getLevel() - 1));
        return 1 + (statLevel * CreatureManager.getInstance().getLevelMultiplier(stat));
    }
}