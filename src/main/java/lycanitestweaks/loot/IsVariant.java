package lycanitestweaks.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Random;

/*

    *** Checks BaseCreatureEntity
    *** Else returns true otherwise

    "conditions": [
        {
            "condition": "lycanitesTweaks:is_variant",
            "rare": false,
            "uncommon": false,
            "subspecies": 0
        }
    ]

 */

public class IsVariant implements LootCondition {

    private boolean mustBeUncommon = false;
    private boolean mustBeRare = false;
    private boolean spawnedAsBoss = false;
    private int subspecies = 0;

    public IsVariant(int subspecies, boolean mustBeUncommon, boolean mustBeRare, boolean spawnedAsBoss){
        this.subspecies = subspecies;
        this.mustBeUncommon = mustBeUncommon;
        this.mustBeRare = mustBeRare;
        this.spawnedAsBoss = spawnedAsBoss;
    }

    @Override
    public boolean testCondition(Random rand, LootContext context) {
        if(context.getLootedEntity() instanceof BaseCreatureEntity){
            BaseCreatureEntity creature = (BaseCreatureEntity)context.getLootedEntity();

            if(creature.getSubspeciesIndex() != this.subspecies) return false;
            if(this.mustBeRare && !creature.isRareVariant()) return false;
            else if(this.mustBeUncommon && creature.getVariantIndex() == 0) return false;
            if(this.spawnedAsBoss && !creature.spawnedAsBoss) return false;
        }

        return true;
    }

    public static class Serializer extends LootCondition.Serializer<IsVariant>{

        public Serializer() {
            super(new ResourceLocation(LycanitesTweaks.MODID + ":" + "is_variant"), IsVariant.class);
        }

        public void serialize(JsonObject json, IsVariant value, JsonSerializationContext context) {
            json.add("subspecies", context.serialize(value.subspecies));
            json.add("uncommon", context.serialize(value.mustBeUncommon));
            json.add("rare", context.serialize(value.mustBeRare));
            json.add("spawnedAsBoss", context.serialize(value.spawnedAsBoss));
        }

        public IsVariant deserialize(JsonObject json, JsonDeserializationContext context) {
            return new IsVariant(JsonUtils.getInt(json, "subspecies", 0),
                    JsonUtils.getBoolean(json, "uncommon", false),
                    JsonUtils.getBoolean(json, "rare", false),
                    JsonUtils.getBoolean(json, "spawnedAsBoss", false));
        }
    }
}
