package lycanitestweaks.handlers.features.boss;

import com.lycanitesmobs.LycanitesMobs;
import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureManager;
import com.lycanitesmobs.core.info.ElementInfo;
import com.lycanitesmobs.core.item.ChargeItem;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.loot.EnchantWithMobLevels;
import lycanitestweaks.loot.HasMobLevels;
import lycanitestweaks.loot.IsVariant;
import lycanitestweaks.loot.ScaleWithMobLevels;
import net.minecraft.init.Items;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultBossLootHandler {

//    public static final String[] lootPoolNames = {
//            "amalgalich_emeralds_with_mob_levels", "amalgalich_book_with_mob_levels",
//            "asmodeus_emerald_with_mob_levels", "asmodeus_book_with_mob_levels",
//            "rahovart_emerald_with_mob_levels", "rahovart_book_with_mob_levels"
//    };

    public static HashMap<String, ArrayList<String>> chargeElementsMap = null;

    // JSON examples
    @SubscribeEvent
    public static void removeDefaultBossLoot(LootTableLoadEvent event){
        if(!ForgeConfigHandler.server.pmlConfig.registerBossWithLevelsLootTables) {
            if (LycanitesMobs.modid.equals(event.getName().getNamespace())) {
                switch (event.getName().getPath()) {
                    case "amalgalich":
                        event.getTable().removePool("amalgalich_emeralds_with_mob_levels");
                        event.getTable().removePool("amalgalich_book_with_mob_levels");
                        break;
                    case "asmodeus":
                        event.getTable().removePool("asmodeus_emerald_with_mob_levels");
                        event.getTable().removePool("asmodeus_book_with_mob_levels");
                        break;
                    case "rahovart":
                        event.getTable().removePool("rahovart_emerald_with_mob_levels");
                        event.getTable().removePool("rahovart_book_with_mob_levels");
                        break;
                }
            }
        }
    }

    // LootTableLoadEvent examples
    @SubscribeEvent
    public static void addDefaultBossLoot(LootTableLoadEvent event){
        if(ForgeConfigHandler.server.pmlConfig.registerDungeonBossWithLevelsLootTables) {
            if (LycanitesMobs.modid.equals(event.getName().getNamespace())) {
                LootPool bookTable = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(Items.BOOK, 1, 0,
                                        new LootFunction[]{new EnchantWithMobLevels(new LootCondition[0], new RandomValueRange(50), false, 1.0F)},
                                        new LootCondition[0],
                                        LycanitesTweaks.MODID + ":enchant_with_mob_levels_book")},
                        new LootCondition[]{new IsVariant(-1, false, false, true)},
                        new RandomValueRange(1), new RandomValueRange(0), LycanitesTweaks.MODID + "_boss_book");
                LootPool treasureBookTable = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(Items.BOOK, 1, 0,
                                        new LootFunction[]{new EnchantWithMobLevels(new LootCondition[0], new RandomValueRange(100), true, 0.75F)},
                                        new LootCondition[0],
                                        LycanitesTweaks.MODID + ":enchant_with_mob_levels_book_treasure")},
                        new LootCondition[]{
                                new IsVariant(-1, false, false, true),
                                new HasMobLevels(new RandomValueRange(100))},
                        new RandomValueRange(1), new RandomValueRange(0), LycanitesTweaks.MODID + "_boss_book_treasure");
                LootPool xpTable = new LootPool(
                        new LootEntry[]{
                                new LootEntryItem(Items.EXPERIENCE_BOTTLE, 1, 0,
                                        new LootFunction[]{new ScaleWithMobLevels(new LootCondition[0], new RandomValueRange(0, 1), 0.5F, 128)},
                                        new LootCondition[0],
                                        LycanitesTweaks.MODID + ":scale_with_mob_levels_xp")},
                        new LootCondition[]{new IsVariant(-1, false, false, true)},
                        new RandomValueRange(1), new RandomValueRange(0), LycanitesTweaks.MODID + "_boss_xp");
                event.getTable().addPool(bookTable);
                event.getTable().addPool(treasureBookTable);
                event.getTable().addPool(xpTable);
            }
        }
        if(ForgeConfigHandler.server.pmlConfig.registerRandomChargesLootTable){
            if (LycanitesMobs.modid.equals(event.getName().getNamespace())) {
                CreatureInfo creatureInfo = CreatureManager.getInstance().getCreature(event.getName().getPath());
                if(creatureInfo != null){
                    LootPool chargeTable = new LootPool(
                            new LootEntry[0],
                            new LootCondition[]{new HasMobLevels(new RandomValueRange(25))},
                            new RandomValueRange(1), new RandomValueRange(0), LycanitesTweaks.MODID + "_random_charges");
                    for(ElementInfo elementInfo : creatureInfo.elements){
                        if(DefaultBossLootHandler.getChargeElementsMap().containsKey(elementInfo.name)) {
                            for (String charge : DefaultBossLootHandler.getChargeElementsMap().get(elementInfo.name)) {
                                String entryName = LycanitesTweaks.MODID + ":_random_charge_" + charge;
                                if(chargeTable.getEntry(entryName) == null)
                                    chargeTable.addEntry(
                                            new LootEntryItem(ObjectManager.getItem(charge), 1, 0,
                                                    new LootFunction[]{
                                                            new ScaleWithMobLevels(new LootCondition[0], new RandomValueRange(0, 4), 0.1F, 0),
                                                            new LootingEnchantBonus(new LootCondition[0], new RandomValueRange(0, 1), 0)},
                                                    new LootCondition[0],
                                                    entryName)
                                    );
                            }
                        }
                    }
                    event.getTable().addPool(chargeTable);
                }
            }
        }
    }

    public static HashMap<String, ArrayList<String>> getChargeElementsMap(){
        if(DefaultBossLootHandler.chargeElementsMap == null){
            DefaultBossLootHandler.chargeElementsMap = new HashMap<>();
            ObjectManager.items.forEach((name, item) -> {
                if(item instanceof ChargeItem){
                    for(String element : ((ChargeItem) item).getElementNames().split(",")){
                        String elementString = element.trim().toLowerCase();
                        String chargeString = ((ChargeItem) item).itemName.trim();
                        ArrayList<String> projectiles;

                        if(!DefaultBossLootHandler.chargeElementsMap.containsKey(elementString)) projectiles = new ArrayList<>();
                        else projectiles = DefaultBossLootHandler.chargeElementsMap.get(elementString);
                        projectiles.add(chargeString);
                        DefaultBossLootHandler.chargeElementsMap.put(elementString, projectiles);
                    }
                }
            });
        }
        return DefaultBossLootHandler.chargeElementsMap;
    }
}
