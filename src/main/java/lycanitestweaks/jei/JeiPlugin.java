package lycanitestweaks.jei;

import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.item.equipment.EquipmentPartManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        EquipmentPartManager.getInstance().equipmentParts.forEach((name, item) -> {
            registry.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, "gui.jei.equipmentpart." + item.slotType + ".description", "gui.jei.equipmentpart.infuser.description");
        });

        registry.addIngredientInfo(new ItemStack(ObjectManager.getItem("equipment")), VanillaTypes.ITEM,  "gui.jei.equipment.mana.description", "gui.jei.equipment.mana.description");
    }
}