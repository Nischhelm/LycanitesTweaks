package lycanitestweaks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

/*

    *** "minLevelScale" - Optional, multiplies with creature's levels to set min count
    *** "maxLevelScale" - Optional, multiplies with creature's levels to set max count
    *** "itemLimit" - Optional, maximum items

    "functions": [
        {
            "function": "lycanitestweaks:add_count_from_mob_levels",
            "itemLimit": 10
        }
    ]

 */

public class AddCountFromMobLevels extends LootFunction {

    private final float minScale;
    private final float maxScale;
    private final int limit;

    public AddCountFromMobLevels(LootCondition[] conditionsIn, float minScale, float maxScale, int limit) {
        super(conditionsIn);
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.limit = limit;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if (context.getLootedEntity() instanceof BaseCreatureEntity) {
            BaseCreatureEntity creature = (BaseCreatureEntity)context.getLootedEntity();

            RandomValueRange valueRange =
                (this.minScale < this.maxScale)
                    ? new RandomValueRange(this.minScale * creature.getLevel(), this.maxScale * creature.getLevel())
                    : new RandomValueRange(this.minScale * creature.getLevel());

            int newCount = stack.getCount() + valueRange.generateInt(rand);
            if(this.limit > 0) newCount = Math.min(this.limit, newCount); //max set to limit, otherwise set to currCount x scale x moblvl

            stack.setCount(newCount);
        }

        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<AddCountFromMobLevels> {

        public Serializer() {
            super(new ResourceLocation(LycanitesTweaks.MODID + ":" + "add_count_from_mob_levels"), AddCountFromMobLevels.class);
        }

        public void serialize(JsonObject object, AddCountFromMobLevels functionClazz, JsonSerializationContext serializationContext) {
            object.add("minLevelScale", serializationContext.serialize(functionClazz.minScale));
            object.add("maxLeveScale", serializationContext.serialize(functionClazz.maxScale));
            object.add("itemLimit", serializationContext.serialize(functionClazz.limit));
        }

        public AddCountFromMobLevels deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            float minScale = JsonUtils.getFloat(object, "minLevelScale", 1.0F);
            float maxScale = JsonUtils.getFloat(object, "maxLeveScale", 0.0F);
            int limit = JsonUtils.getInt(object, "itemLimit", 0);
            return new AddCountFromMobLevels(conditionsIn, minScale, maxScale, limit);
        }
    }
}
