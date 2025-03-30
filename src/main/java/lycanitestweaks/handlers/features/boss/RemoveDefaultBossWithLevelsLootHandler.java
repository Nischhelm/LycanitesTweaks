package lycanitestweaks.handlers.features.boss;

import com.lycanitesmobs.LycanitesMobs;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RemoveDefaultBossWithLevelsLootHandler {

//    public static final String[] lootPoolNames = {
//            "amalgalich_emeralds_with_mob_levels", "amalgalich_book_with_mob_levels",
//            "asmodeus_emerald_with_mob_levels", "asmodeus_book_with_mob_levels",
//            "rahovart_emerald_with_mob_levels", "rahovart_book_with_mob_levels"
//    };

    @SubscribeEvent
    public static void removeDefaultBossLoot(LootTableLoadEvent event){
        if(LycanitesMobs.modid.equals(event.getName().getNamespace())) {
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
