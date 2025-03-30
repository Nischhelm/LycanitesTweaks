package lycanitestweaks.loot;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;

import java.util.List;
import java.util.Random;

/*

    *** "levels" - Required, replicates enchant_with_levels if not BaseCreatureEntity
    *** "treasure" - Optional
    *** "scale" - Optional, multiplies with creature's levels

    "functions": [
        {
            "function": "lycanitesTweaks:enchant_with_mob_levels",
            "levels": 30,
            "treasure": true,
            "scale": 0.5
        }
    ]

 */

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
            int levels = (int)(((BaseCreatureEntity)context.getLootedEntity()).getLevel() * this.scale);
            return this.addRandomEnchantmentUntilSuccess(rand, stack, levels, this.isTreasure);
        }
        return EnchantmentHelper.addRandomEnchantment(rand, stack, randomLevel.generateInt(rand), this.isTreasure);
    }

    // Attempts to have at least one ench without high levels resulting in the same enchants, average 1/2 level loss per loop
    public ItemStack addRandomEnchantmentUntilSuccess(Random rand, ItemStack stack, int levels, boolean isTreasure){
        ItemStack loot = EnchantmentHelper.addRandomEnchantment(rand, stack, levels, isTreasure);
        boolean emptyEnchant = (loot.getItem() == Items.ENCHANTED_BOOK) ? !loot.hasTagCompound() : loot.getEnchantmentTagList().isEmpty();

        if(emptyEnchant && levels > 0) {
            int reducedLevels = (int)(levels * MathHelper.clamp(Math.round(rand.nextFloat()), 0.25F, 0.75F ));
            return addRandomEnchantmentUntilSuccess(rand, stack, reducedLevels, isTreasure);
        }
        return loot;
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
            return new EnchantWithMobLevels(conditionsIn, levels, isTreasure, scale);
        }
    }
}
