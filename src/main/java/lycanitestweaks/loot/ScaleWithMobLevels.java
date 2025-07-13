package lycanitestweaks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

/*

    *** "scale" - Optional, multiplies with creature's levels
    *** "limit" - Optional, maximum items

    "functions": [
        {
            "function": "lycanitestweaks:scale_with_mob_levels",
            "limit": 10
        }
    ]

 */

public class ScaleWithMobLevels extends LootFunction {

    private final float scale;
    private final boolean sqrtTotal;
    private final boolean logTotal;
    private final int limit;

    public ScaleWithMobLevels(LootCondition[] conditionsIn, float scale, boolean sqrtTotal, boolean logTotal, int limit) {
        super(conditionsIn);
        this.scale = scale;
        this.sqrtTotal = sqrtTotal;
        this.logTotal = logTotal;
        this.limit = limit;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if (context.getLootedEntity() instanceof BaseCreatureEntity) {
            BaseCreatureEntity creature = (BaseCreatureEntity)context.getLootedEntity();


            // Apply linear currCount x scale x moblevel
            int newCount = (int) (stack.getCount() * this.scale * creature.getLevel());
            // Apply optional sqrt calc
            if(this.sqrtTotal) newCount = MathHelper.floor(MathHelper.sqrt(newCount));
            // Apply optional log2 calc
            if(this.logTotal) newCount = MathHelper.log2(Math.max(1, newCount));

            if(this.limit > 0) newCount = Math.min(this.limit, newCount); //max set to limit, otherwise set to currCount x scale x moblvl

            stack.setCount(newCount);
        }

        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<ScaleWithMobLevels> {

        public Serializer() {
            super(new ResourceLocation(LycanitesTweaks.MODID + ":" + "scale_with_mob_levels"), ScaleWithMobLevels.class);
        }

        public void serialize(JsonObject object, ScaleWithMobLevels functionClazz, JsonSerializationContext serializationContext) {
            object.add("scale", serializationContext.serialize(functionClazz.scale));
            object.add("limit", serializationContext.serialize(functionClazz.limit));
            object.add("sqrt", serializationContext.serialize(functionClazz.sqrtTotal));
            object.add("log2", serializationContext.serialize(functionClazz.logTotal));
        }

        public ScaleWithMobLevels deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            float scale = JsonUtils.getFloat(object, "scale", 1.0F);
            int limit = JsonUtils.getInt(object, "limit", 0);
            boolean sqrtTotal = JsonUtils.getBoolean(object, "sqrt", false);
            boolean logTotal = JsonUtils.getBoolean(object, "log2", false);
            return new ScaleWithMobLevels(conditionsIn, scale, sqrtTotal, logTotal, limit);
        }
    }
}
