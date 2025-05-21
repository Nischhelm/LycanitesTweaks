package lycanitestweaks.jei;

import com.lycanitesmobs.ObjectManager;
import com.lycanitesmobs.core.info.ItemConfig;
import com.lycanitesmobs.core.item.equipment.EquipmentPartManager;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {
        StringBuilder sharpnessItems = new StringBuilder();
        StringBuilder manaItems = new StringBuilder();

        ItemConfig.maxEquipmentSharpnessItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                sharpnessItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });
        ItemConfig.highEquipmentSharpnessItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                sharpnessItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });
        ItemConfig.mediumEquipmentSharpnessItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                sharpnessItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });
        ItemConfig.lowEquipmentSharpnessItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                sharpnessItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });

        ItemConfig.maxEquipmentManaItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                manaItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });
        ItemConfig.highEquipmentManaItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                manaItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });
        ItemConfig.mediumEquipmentManaItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                manaItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });
        ItemConfig.lowEquipmentManaItems.forEach((itemID) -> {
            if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemID))) {
                manaItems.append(I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemID)).getTranslationKey() + ".name")).append(", ");
            }
        });

        sharpnessItems.deleteCharAt(sharpnessItems.lastIndexOf(",")).append("\n");
        manaItems.deleteCharAt(manaItems.lastIndexOf(",")).append("\n");
        registry.addIngredientInfo(new ItemStack(ObjectManager.getItem("equipment")), VanillaTypes.ITEM,  "gui.jei.equipment.sharpness.description", sharpnessItems.toString(), "gui.jei.equipment.mana.description", manaItems.toString());


        EquipmentPartManager.getInstance().equipmentParts.forEach((name, item) -> registry.addIngredientInfo(new ItemStack(item), VanillaTypes.ITEM, "gui.jei.equipmentpart." + item.slotType + ".description", "gui.jei.equipmentpart.infuser.description"));
    }
}