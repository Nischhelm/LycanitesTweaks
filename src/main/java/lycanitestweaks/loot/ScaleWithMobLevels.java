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

public class ScaleWithMobLevels extends LootFunction {

    private final RandomValueRange count;
    private final float scale;
    private final int limit;

    public ScaleWithMobLevels(LootCondition[] conditionsIn, RandomValueRange count, float scale, int limit) {
        super(conditionsIn);
        this.count = count;
        this.scale = scale;
        this.limit = limit;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if (context.getLootedEntity() instanceof BaseCreatureEntity) {
            BaseCreatureEntity creature = (BaseCreatureEntity)context.getLootedEntity();

            int i = Math.round(creature.getLevel() * this.scale);
            if (i == 0) {
                return stack;
            }

            float f = (float)i * this.count.generateFloat(rand);
            stack.grow(Math.round(f));
            if (this.limit > 0 && stack.getCount() > this.limit) {
                stack.setCount(this.limit);
            }
        }

        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<ScaleWithMobLevels> {

        public Serializer() {
            super(new ResourceLocation(LycanitesTweaks.MODID + ":" + "scale_with_mob_levels"), ScaleWithMobLevels.class);
        }

        public void serialize(JsonObject object, ScaleWithMobLevels functionClazz, JsonSerializationContext serializationContext) {
            object.add("count", serializationContext.serialize(functionClazz.count));
            object.add("scale", serializationContext.serialize(functionClazz.scale));
            if(functionClazz.limit > 0){
                object.add("limit", serializationContext.serialize(functionClazz.limit));
            }
        }

        public ScaleWithMobLevels deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            float scale = JsonUtils.getFloat(object, "scale", 1.0F);
            int limit = JsonUtils.getInt(object, "limit", 0);
            return new ScaleWithMobLevels(conditionsIn, (RandomValueRange)JsonUtils.deserializeClass(object, "count", deserializationContext, RandomValueRange.class), scale, limit);
        }
    }
}
