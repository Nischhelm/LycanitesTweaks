package lycanitestweaks.info.altar;

import com.lycanitesmobs.core.info.AltarInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public abstract class AltarInfoTemplate extends AltarInfo {

    protected BlockPattern blockPattern;
    protected Block coreBlock;
    protected Block bodyBlock;

    protected int coreOffset = 0;
    protected int clearHeight = 0;
    protected int clearWidth = 0;

    public AltarInfoTemplate(String name) {
        super(name);
    }

    abstract protected BlockPattern getBlockPattern();
    abstract protected EntityLivingBase createEntity(Entity entity, World world);

    // ==================================================
    //                     Checking
    // ==================================================
    /** Called first when checking for a valid altar, this should be fairly lightweight such as just checking if the first block checked is valid, a more in depth check if then done after. **/
    @Override
    public boolean quickCheck(Entity entity, World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == this.coreBlock;
    }

    /** Called if the QuickCheck() is passed, this should check the entire altar structure and if true is returned, the altar will activate. **/
    @Override
    public boolean fullCheck(Entity entity, World world, BlockPos pos) {
        if(!this.quickCheck(entity, world, pos)) return false;

        BlockPattern blockPattern = this.getBlockPattern();
        BlockPattern.PatternHelper patternHelper = blockPattern.match(world, new BlockPos(pos.getX(), pos.getY() + this.coreOffset, pos.getZ()));
        if(patternHelper != null){
            for (int palmOffset = 0; palmOffset < blockPattern.getPalmLength(); ++palmOffset){
                for (int thumbOffset = 0; thumbOffset < blockPattern.getThumbLength(); ++thumbOffset){
                    BlockWorldState blockWorldStateReplace = patternHelper.translateOffset(palmOffset, thumbOffset, 0);
                    world.setBlockState(blockWorldStateReplace.getPos(), Blocks.AIR.getDefaultState(), 2);
                }
            }

            for (int palmOffset = 0; palmOffset < blockPattern.getPalmLength(); ++palmOffset){
                for (int thumbOffset = 0; thumbOffset < blockPattern.getThumbLength(); ++thumbOffset){
                    BlockWorldState blockWorldStateUpdate = patternHelper.translateOffset(palmOffset, thumbOffset, 0);
                    world.notifyNeighborsRespectDebug(blockWorldStateUpdate.getPos(), Blocks.AIR, false);
                }
            }
            return true;
        }
        return false;
    }

    // ==================================================
    //                     Activate
    // ==================================================
    /** Called when this Altar should activate. This will typically destroy the Altar and summon a rare mob or activate an event such as a boss event. If false is returned then the activation did not work, this is the place to check for things like dimensions. **/
    @Override
    public boolean activate(Entity entity, World world, BlockPos pos, int variant) {
        if(world.isRemote)
            return true;

        EntityLivingBase entityCreature = this.createEntity(entity, world);
        this.clearSpawnArea(world, pos, entityCreature);

        // Spawn Mini Boss:
        entityCreature.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0, 0);
        world.spawnEntity(entityCreature);
        this.onSpawnEntity(entityCreature);

        // Call it, return value is wrong for minibosses
        super.activate(entity, world, pos, variant);
        return true;
    }

    protected void clearSpawnArea(World world, BlockPos pos, EntityLivingBase spawnedEntity){
        if(ForgeEventFactory.getMobGriefingEvent(world, spawnedEntity)) {
            int y = pos.getY();
            int x = pos.getX();
            int z = pos.getZ();
            boolean destroyedBlocks = false;

            for (int xTarget = x - this.clearWidth; xTarget <= x + this.clearWidth; ++xTarget) {
                for (int zTarget = z - this.clearWidth; zTarget <= z + this.clearWidth; ++zTarget) {
                    for (int yTarget = y - this.clearHeight; yTarget <= y + this.clearHeight; ++yTarget) {
                        BlockPos clearPos = new BlockPos(xTarget, yTarget, zTarget);
                        IBlockState iblockstate = world.getBlockState(clearPos);
                        Block block = iblockstate.getBlock();
                        if (!block.isAir(iblockstate, world, clearPos) && EntityWither.canDestroyBlock(block) && world.getTileEntity(clearPos) == null && ForgeEventFactory.onEntityDestroyBlock(spawnedEntity, clearPos, iblockstate)) {
                            destroyedBlocks = world.setBlockToAir(clearPos) || destroyedBlocks;
                        }
                    }
                }
            }
            if (destroyedBlocks) world.playEvent(null, 1022, pos, 0);
        }
    }

    protected void onSpawnEntity(Entity entity){}
}
