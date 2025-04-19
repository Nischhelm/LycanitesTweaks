package lycanitestweaks.handlers.features.boss;

import com.lycanitesmobs.LycanitesMobs;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.loot.EnchantWithMobLevels;
import lycanitestweaks.loot.IsVariant;
import lycanitestweaks.loot.ScaleWithMobLevels;
import net.minecraft.init.Items;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DefaultBossLootHandler {

//    public static final String[] lootPoolNames = {
//            "amalgalich_emeralds_with_mob_levels", "amalgalich_book_with_mob_levels",
//            "asmodeus_emerald_with_mob_levels", "asmodeus_book_with_mob_levels",
//            "rahovart_emerald_with_mob_levels", "rahovart_book_with_mob_levels"
//    };

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

    @SubscribeEvent
    public static void addDefaultBossLoot(LootTableLoadEvent event){
        if(ForgeConfigHandler.server.pmlConfig.registerDungeonBossWithLevelsLootTables) {
            if (LycanitesMobs.modid.equals(event.getName().getNamespace())) {
                switch (event.getName().getPath()) {
//                    case "amalgalich":
//                        break;
//                    case "asmodeus":
//                        break;
//                    case "rahovart":
//                        break;
                    default:
                        LootPool bookTable = new LootPool(
                                new LootEntry[]{
                                        new LootEntryItem(Items.BOOK, 1, 0,
                                                new LootFunction[]{new EnchantWithMobLevels(new LootCondition[0], new RandomValueRange(50), false, 1.0F)},
                                                new LootCondition[0],
                                                LycanitesTweaks.MODID + ":enchant_with_mob_levels_book")},
                                new LootCondition[]{new IsVariant(-1, false, false, true)},
                                new RandomValueRange(1), new RandomValueRange(0), LycanitesTweaks.MODID + "_boss_book");
                        LootPool xpTable = new LootPool(
                                new LootEntry[]{
                                        new LootEntryItem(Items.EXPERIENCE_BOTTLE, 1, 0,
                                                new LootFunction[]{new ScaleWithMobLevels(new LootCondition[0], new RandomValueRange(0, 1), 0.5F, 128)},
                                                new LootCondition[0],
                                                LycanitesTweaks.MODID + ":scale_with_mob_levels_xp")},
                                new LootCondition[]{new IsVariant(-1, false, false, true)},
                                new RandomValueRange(1), new RandomValueRange(0), LycanitesTweaks.MODID + "_boss_xp");
                        event.getTable().addPool(bookTable);
                        event.getTable().addPool(xpTable);
                }
            }
        }
    }
}
