package lycanitestweaks.mixin.lycanitestweakscore.assetload;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import com.lycanitesmobs.core.spawner.MobSpawn;
import lycanitestweaks.LycanitesTweaks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobSpawn.class)
public abstract class MobSpawnAdditionalMixin {

    @Unique
    private final static String SET_NBT = LycanitesTweaks.MODID + ":setNBT";

    @Unique
    private String lycanitesTweaks$mobID = "";
    @Unique
    private String lycanitesTweaks$nbtString = "";

    @Inject(
            method = "createFromJSON",
            at = @At(value = "INVOKE", target = "Lcom/lycanitesmobs/core/spawner/MobSpawn;loadFromJSON(Lcom/google/gson/JsonObject;)V"),
            remap = false
    )
    private static void qqq(JsonObject json, CallbackInfoReturnable<MobSpawn> cir, @Local MobSpawn mobSpawn){
        ((MobSpawnAdditionalMixin)(Object)mobSpawn).lycanitesTweaks$mobID = json.get("mobId").getAsString();
    }

    @Inject(
            method = "loadFromJSON",
            at = @At("TAIL"),
            remap = false
    )
    public void www(JsonObject json, CallbackInfo ci){
        if (json.has(SET_NBT)) {
            this.lycanitesTweaks$nbtString = json.get(SET_NBT).getAsString();
        }
    }

//    @Inject(
//            method = "onSpawned",
//            at = @At("TAIL"),
//            remap = false
//    )
//    public void sss(EntityLiving entityLiving, EntityPlayer player, CallbackInfo ci){
//        if(!lycanitesTweaks$nbtString.isEmpty()){
//            NBTTagCompound nbtTag = new NBTTagCompound();
//            ResourceLocation resourcelocation = EntityList.getKey(entityLiving);
//            try {
//                nbtTag = JsonToNBT.getTagFromJson(this.lycanitesTweaks$nbtString);
//            }
//            catch (Exception exception){
//                LycanitesTweaks.LOGGER.log(Level.WARN,"Encountered error trying to set NBT {}", exception.toString());
//            }
//            if(resourcelocation != null){
//                nbtTag.setString("id", resourcelocation.toString());
//                nbtTag.merge(entityLiving.getEntityData());
//                Entity entity = AnvilChunkLoader.readWorldEntityPos(nbtTag,
//                        entityLiving.getEntityWorld(),
//                        entityLiving.getPosition().getX(),
//                        entityLiving.getPosition().getY(),
//                        entityLiving.getPosition().getZ(),
//                        true);
//                if (entity == null) {
//                    LycanitesTweaks.LOGGER.log(Level.WARN,"Failed to spawn new entity with set NBT from: {}", entityLiving);
//                }
//                else {
//                    entity.setLocationAndAngles(entityLiving.getPosition().getX(), entityLiving.getPosition().getY(), entityLiving.getPosition().getZ(), entity.rotationYaw, entity.rotationPitch);
//                }
//            }
//        }
//    }

    @Inject(
            method = "createEntity",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    public void ddd(World world, CallbackInfoReturnable<EntityLiving> cir){
        if(!lycanitesTweaks$mobID.isEmpty() && !lycanitesTweaks$nbtString.isEmpty()){
            NBTTagCompound nbtTag = new NBTTagCompound();
            try {
                nbtTag = JsonToNBT.getTagFromJson(this.lycanitesTweaks$nbtString);
            }
            catch (Exception exception){
                LycanitesTweaks.LOGGER.log(Level.WARN,"Encountered error trying to set NBT {}", exception.toString());
            }
            nbtTag.setString("id", lycanitesTweaks$mobID);
            Entity entity = AnvilChunkLoader.readWorldEntityPos(nbtTag,
                    world,
                    0,
                    0,
                    0,
                    true);
            if (entity == null) {
                LycanitesTweaks.LOGGER.log(Level.WARN,"Failed to spawn new entity with set NBT");
            }
            else {
                cir.setReturnValue((EntityLiving) entity);
            }
        }
    }
}
