package lycanitestweaks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.info.Variant;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.Random;

public class ApplyVariantItemDropsScale extends LootFunction {

    private final boolean applyRareToNBTBosses;
    private final boolean applyToUnstackables;

    public ApplyVariantItemDropsScale(LootCondition[] conditionsIn, boolean applyRareToNBTBosses, boolean applyToUnstackables) {
        super(conditionsIn);
        this.applyRareToNBTBosses = applyRareToNBTBosses;
        this.applyToUnstackables = applyToUnstackables;
    }

    @Override
    public ItemStack apply(ItemStack stack, Random rand, LootContext context) {
        if (context.getLootedEntity() instanceof BaseCreatureEntity) {
            BaseCreatureEntity creature = (BaseCreatureEntity)context.getLootedEntity();

            if(!this.applyToUnstackables && stack.getMaxStackSize() == 1) return stack;

            if(creature.isRareVariant()) stack.setCount(stack.getCount() * Variant.RARE_DROP_SCALE);
            else if(creature.spawnedAsBoss && this.applyRareToNBTBosses) stack.setCount(stack.getCount() * Variant.RARE_DROP_SCALE);
            else if(creature.getVariantIndex() != 0) stack.setCount(stack.getCount() * Variant.UNCOMMON_DROP_SCALE);
        }
        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<ApplyVariantItemDropsScale>{

        public Serializer() {
            super(new ResourceLocation(LycanitesTweaks.MODID + ":" + "apply_variant_drop_scale"), ApplyVariantItemDropsScale.class);
        }

        public void serialize(JsonObject object, ApplyVariantItemDropsScale functionClazz, JsonSerializationContext serializationContext) {
            object.add("applyRareToNBTBosses", serializationContext.serialize(functionClazz.applyRareToNBTBosses));
            object.add("applyToUnstackables", serializationContext.serialize(functionClazz.applyToUnstackables));
        }

        public ApplyVariantItemDropsScale deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn) {
            boolean applyRareToNBTBosses = JsonUtils.getBoolean(object, "applyRareToNBTBosses", false);
            boolean applyToUnstackables = JsonUtils.getBoolean(object, "applyToUnstackables", false);
            return new ApplyVariantItemDropsScale(conditionsIn, applyRareToNBTBosses, applyToUnstackables);
        }
    }
}
