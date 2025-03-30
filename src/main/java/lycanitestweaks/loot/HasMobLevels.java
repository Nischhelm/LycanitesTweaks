package lycanitestweaks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

/*

    *** Checks Mob Levels if BaseCreatureEntity
    *** Else returns true otherwise

    *** Minimum Mob Levels ***
    "conditions": [
        {
            "condition": "lycanitesTweaks:has_mob_levels",
            "range": 10
        }
    ]

    *** Range of Mob Levels ***
    "conditions": [
        {
            "condition": "lycanitesTweaks:has_mob_levels",
            "range": {
                "min": 50,
                "max": 100
            }
        }
    ]
*/
public class HasMobLevels implements LootCondition {

    private final RandomValueRange range;

    public HasMobLevels(RandomValueRange range) {
        this.range = range;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        if(context.getLootedEntity() instanceof BaseCreatureEntity){
            BaseCreatureEntity creature = (BaseCreatureEntity)context.getLootedEntity();
            if(this.range.getMin() > 0 && creature.getLevel() < this.range.getMin()) return false;
            if(this.range.getMin() != this.range.getMax() && this.range.getMax() > 0 && creature.getLevel() > this.range.getMax()) return false;
            return true;
        }
        return true;
    }

    public static class Serializer extends LootCondition.Serializer<HasMobLevels>{

        public Serializer() {
            super(new ResourceLocation(LycanitesTweaks.MODID + ":" + "has_mob_levels"), HasMobLevels.class);
        }

        public void serialize(JsonObject json, HasMobLevels value, JsonSerializationContext context) {
            json.add("range", context.serialize(value.range));
        }

        public HasMobLevels deserialize(JsonObject json, JsonDeserializationContext context) {
            return new HasMobLevels((RandomValueRange)JsonUtils.deserializeClass(json, "range", context, RandomValueRange.class));
        }
    }
}
