package lycanitestweaks.client.renderer.entity;

import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class RenderBossSummonCrystal extends Render<EntityBossSummonCrystal> {

    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal.png");
    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES_DIAMOND = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal_diamond.png");
    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES_EMERALD = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal_emerald.png");
    private static final ResourceLocation BOSS_SUMMON_CRYSTAL_TEXTURES_ENCOUNTER = new ResourceLocation(LycanitesTweaks.MODID,"textures/entity/dungeonbosscrystal/dungeonbosscrystal_encounter.png");

    private final ModelBase modelBossSummonCrystal = new ModelEnderCrystal(0.0F, true);
    private final ModelBase modelBossSummonCrystalNoBase = new ModelEnderCrystal(0.0F, false);

    public RenderBossSummonCrystal(RenderManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    @Override
    public void doRender(EntityBossSummonCrystal entity, double x, double y, double z, float entityYaw, float partialTicks){
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
        BlockPos blockpos = entity.getBeamTarget();

        if (blockpos != null){
            this.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
            float f2 = (float)blockpos.getX() + 0.5F;
            float f3 = (float)blockpos.getY() + 0.5F;
            float f4 = (float)blockpos.getZ() + 0.5F;
            double d0 = (double)f2 - entity.posX;
            double d1 = (double)f3 - entity.posY;
            double d2 = (double)f4 - entity.posZ;
            RenderDragon.renderCrystalBeams(x + d0, y - 0.3D + (double)(f1 * 0.4F) + d1, z + d2, partialTicks, (double)f2, (double)f3, (double)f4, entity.innerRotation, entity.posX, entity.posY, entity.posZ);
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(EntityBossSummonCrystal entity){
        switch (entity.getVariantType()){
            case -1: return BOSS_SUMMON_CRYSTAL_TEXTURES_ENCOUNTER;
            case 0: return BOSS_SUMMON_CRYSTAL_TEXTURES;
            case 1: return BOSS_SUMMON_CRYSTAL_TEXTURES_DIAMOND;
            case 2: return BOSS_SUMMON_CRYSTAL_TEXTURES_EMERALD;
        }
        return new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    }

    @Override
    public boolean shouldRender(@Nonnull EntityBossSummonCrystal livingEntity, @Nonnull ICamera camera, double camX, double camY, double camZ) {
        return super.shouldRender(livingEntity, camera, camX, camY, camZ) || livingEntity.getBeamTarget() != null;
    }
}
