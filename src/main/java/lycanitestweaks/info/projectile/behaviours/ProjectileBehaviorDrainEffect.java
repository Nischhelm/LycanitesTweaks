package lycanitestweaks.info.projectile.behaviours;

import com.google.gson.JsonObject;
import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.info.projectile.behaviours.ProjectileBehaviour;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ProjectileBehaviorDrainEffect extends ProjectileBehaviour {

    private boolean stealEffect = false;
    private boolean allEffects = false;
    private float chance = 1.0F;

    @Override
    public void loadFromJSON(JsonObject json) {
        if(json.has("stealEffect")){
            this.stealEffect = json.get("stealEffect").getAsBoolean();
        }
        if(json.has("allEffects")){
            this.allEffects = json.get("allEffects").getAsBoolean();
        }
        if(json.has("chance")){
            this.chance = json.get("chance").getAsFloat();
        }
    }

    @Override
    public void onProjectileDamage(BaseProjectileEntity projectile, World world, EntityLivingBase target, float damage) {
        if(projectile.getThrower() == null || target == null || world.isRemote) {
            return;
        }

        ArrayList<PotionEffect> toRemove = new ArrayList<>();

        if(world.rand.nextFloat() < this.chance) {
            ArrayList<PotionEffect> goodEffects = new ArrayList<>();
            for (PotionEffect effect : target.getActivePotionEffects()) {
                if (!effect.getPotion().isBadEffect()) goodEffects.add(effect);
            }

            if(this.allEffects) toRemove = goodEffects;
            else if(!goodEffects.isEmpty()) toRemove.add(goodEffects.get(world.rand.nextInt(goodEffects.size())));
        }

        toRemove.forEach((effect) -> {
            target.removePotionEffect(effect.getPotion());
            if(this.stealEffect) projectile.getThrower().addPotionEffect(effect);
        });
    }
}
