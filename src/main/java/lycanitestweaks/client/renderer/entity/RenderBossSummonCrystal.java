package lycanitestweaks.client.renderer.entity;

import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderBossSummonCrystal extends RenderEnderCrystal {

    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal.png");
    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES_DIAMOND = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal_diamond.png");
    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES_EMERALD = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal_emerald.png");
    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES_ENCOUNTER = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal_encounter.png");

    private final ModelBase modelBossSummonCrystal = new ModelEnderCrystal(0.0F, true);
    private final ModelBase modelBossSummonCrystalNoBase = new ModelEnderCrystal(0.0F, false);

    public RenderBossSummonCrystal(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void doRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks){
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        float f = (float)entity.innerRotation + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        this.bindTexture(this.getEntityTexture(entity));
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;

        if(this.renderOutlines){
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        if(entity.shouldShowBottom()){
            this.modelBossSummonCrystal.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
        }
        else{
            this.modelBossSummonCrystalNoBase.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
        }

        if(this.renderOutlines){
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
    }

    @Override
    public ResourceLocation getEntityTexture(EntityEnderCrystal entity){
        if(entity instanceof EntityBossSummonCrystal){
            switch (((EntityBossSummonCrystal) entity).getVariantType()){
                case -1: return BOSS_SUMMON_CRYSTAL_TEXTURES_ENCOUNTER;
                case 1: return BOSS_SUMMON_CRYSTAL_TEXTURES_DIAMOND;
                case 2: return BOSS_SUMMON_CRYSTAL_TEXTURES_EMERALD;
                default: return BOSS_SUMMON_CRYSTAL_TEXTURES;
            }
        }
        return BOSS_SUMMON_CRYSTAL_TEXTURES;
    }
}
