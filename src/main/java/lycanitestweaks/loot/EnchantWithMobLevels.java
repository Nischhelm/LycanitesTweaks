package lycanitestweaks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

public class EnchantWithMobLevels extends LootFunction {

    private final RandomValueRange randomLevel;
    private final boolean isTreasure;
    private final float scale;

    public EnchantWithMobLevels(LootCondition[] conditionsIn, RandomValueRange randomLevel, boolean isTreasure, float scale) {
        super(conditionsIn);
        this.randomLevel = randomLevel;
        this.isTreasure = isTreasure;
        this.scale = scale;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if(context.getLootedEntity() instanceof BaseCreatureEntity) {
            return EnchantmentHelper.addRandomEnchantment(rand, stack,
                    (int)(((BaseCreatureEntity)context.getLootedEntity()).getLevel() * this.scale),
                    this.isTreasure);
        }
        return EnchantmentHelper.addRandomEnchantment(rand, stack, randomLevel.generateInt(rand), this.isTreasure);
    }

    public static class Serializer extends LootFunction.Serializer<EnchantWithMobLevels> {

        public Serializer() {
            super(new ResourceLocation(LycanitesTweaks.MODID + ":" + "enchant_with_mob_levels"), EnchantWithMobLevels.class);
        }

        public void serialize(JsonObject object, EnchantWithMobLevels functionClazz, JsonSerializationContext serializationContext) {
            object.add("levels", serializationContext.serialize(functionClazz.randomLevel));
            object.add("treasure", serializationContext.serialize(functionClazz.isTreasure));
            object.add("scale", serializationContext.serialize(functionClazz.scale));
        }

        public EnchantWithMobLevels deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            RandomValueRange levels = (RandomValueRange)JsonUtils.deserializeClass(object, "levels", deserializationContext, RandomValueRange.class);
            boolean isTreasure = JsonUtils.getBoolean(object, "treasure", false);
            float scale = JsonUtils.getFloat(object, "scale", 1.0F);
            return new EnchantWithMobLevels(conditionsIn, (RandomValueRange)JsonUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class), isTreasure, scale);
        }
    }
}
