package lycanitestweaks.info.altar;

import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AltarInfoChargedCreeper extends AltarInfoTemplate implements IAltarNoBoost{

    private final String[] BLOCK_PATTERN_STRINGS = new String[]{
            "~^~",
            "~#~",
            "~#~",
            "###",
            "#~#"
    };

    public AltarInfoChargedCreeper(String name) {
        super(name);
        this.coreBlock = Blocks.TNT;
        this.bodyBlock = Blocks.SLIME_BLOCK;
        this.coreOffset = 0;
    }

    @Override
    public String[] getBlockPatternStrings() {
        return this.BLOCK_PATTERN_STRINGS;
    }

    @Override
    protected BlockPattern getBlockPattern(){
        if (this.blockPattern == null){
            this.blockPattern = FactoryBlockPattern.start()
                    .aisle(
                            this.getBlockPatternStrings()[0],
                            this.getBlockPatternStrings()[1],
                            this.getBlockPatternStrings()[2],
                            this.getBlockPatternStrings()[3],
                            this.getBlockPatternStrings()[4]
                    )
                    .where('#', BlockWorldState.hasState(BlockStateMatcher.forBlock(this.bodyBlock)))
                    .where('^', BlockWorldState.hasState(BlockStateMatcher.forBlock(this.coreBlock)))
                    .where('~', blockWorldState -> blockWorldState.getBlockState().getBlock() != this.bodyBlock)
                    .build();
        }
        return this.blockPattern;
    }

    @Override
    protected void clearSpawnArea(World world, BlockPos pos, EntityLivingBase spawnedEntity){}

    @Override
    protected EntityLivingBase createEntity(Entity entity, World world) {
        EntityCreeper creeper = new EntityCreeper(world);
        creeper.enablePersistence();
        return creeper;
    }

    @Override
    protected void onSpawnEntity(Entity activatingEntity, EntityLivingBase entity){
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();

        EntityLightningBolt lightningBolt = new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ(), true);
        world.addWeatherEffect(lightningBolt);
        entity.onStruckByLightning(lightningBolt);
    }
}
