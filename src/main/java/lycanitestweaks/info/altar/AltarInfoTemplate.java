package lycanitestweaks.info.altar;

import com.lycanitesmobs.core.info.AltarInfo;
import lycanitestweaks.client.gui.beastiary.AltarsBeastiaryScreen;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public abstract class AltarInfoTemplate extends AltarInfo implements IAltarBeastiaryRender {

    protected BlockPattern blockPattern;
    protected BlockPattern.PatternHelper patternHelper;
    // The block that the Soulkey is used on
    protected Block coreBlock;
    protected Block bodyBlock;

    // Offset from the top of where the Block Pattern starts checking
    protected int coreOffset;
    // Size of spawn area to clear
    protected int clearHeight;
    protected int clearWidth;

    // ==================================================
    //                    Constructor
    // ==================================================
    public AltarInfoTemplate(String name) {
        super(name);
        this.coreBlock = Blocks.DIAMOND_BLOCK;
        this.bodyBlock = Blocks.OBSIDIAN;
        this.coreOffset = 2;
        this.clearHeight = 4;
        this.clearWidth = 4;
    }

    /** Returns the raw strings to be used in the Block Pattern. Exposed for other usages such as creating a visual. **/
    abstract public String[] getBlockPatternStrings();
    /** Returns a Block Pattern to check, inheritors should implement considering the Core Block and Body Blocks **/
    abstract protected BlockPattern getBlockPattern();
    /** Returns the Entity to spawn with bare minimum defining properties. Returns null if failed checks such as checkDimensions. **/
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
        this.patternHelper = blockPattern.match(world, new BlockPos(pos.getX(), pos.getY() + this.coreOffset, pos.getZ()));
        return this.patternHelper != null;
    }

    // ==================================================
    //                     Activate
    // ==================================================
    /** Called when this Altar should activate. This will typically destroy the Altar and summon a rare mob or activate an event such as a boss event. If false is returned then the activation did not work, this is the place to check for things like dimensions. **/
    @Override
    public boolean activate(Entity activatorEntity, World world, BlockPos pos, int variant) {
        if(world.isRemote) return true;

        // Create Mini Boss:
        EntityLivingBase entityCreature = this.createEntity(activatorEntity, world);
        if(entityCreature == null) return false;

        // Clear Spawn Area:
        this.clearAltar(world, pos);
        this.clearSpawnArea(world, pos, entityCreature);

        // Spawn Mini Boss:
        entityCreature.setLocationAndAngles(pos.getX() + 0.5D, pos.getY() - 2, pos.getZ() + 0.5D, 0, 0);
        this.onSpawnEntity(activatorEntity, entityCreature);
        world.spawnEntity(entityCreature);

        return true;
    }

    protected void clearAltar(World world, BlockPos pos){
        if(this.patternHelper != null){
            for (int palmOffset = 0; palmOffset < blockPattern.getPalmLength(); ++palmOffset){
                for (int thumbOffset = 0; thumbOffset < blockPattern.getThumbLength(); ++thumbOffset){
                    BlockWorldState blockWorldStateReplace = this.patternHelper.translateOffset(palmOffset, thumbOffset, 0);
                    if(blockWorldStateReplace.getBlockState().getBlock() == this.bodyBlock)
                        world.setBlockState(blockWorldStateReplace.getPos(), Blocks.AIR.getDefaultState(), 2);
                }
            }

            for (int palmOffset = 0; palmOffset < blockPattern.getPalmLength(); ++palmOffset){
                for (int thumbOffset = 0; thumbOffset < blockPattern.getThumbLength(); ++thumbOffset){
                    BlockWorldState blockWorldStateUpdate = this.patternHelper.translateOffset(palmOffset, thumbOffset, 0);
                    world.notifyNeighborsRespectDebug(blockWorldStateUpdate.getPos(), Blocks.AIR, false);
                }
            }
            world.setBlockToAir(pos);
        }
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
                        if (!block.isAir(iblockstate, world, clearPos)
                                && !iblockstate.getMaterial().isLiquid()
                                && EntityWither.canDestroyBlock(block)
                                && world.getTileEntity(clearPos) == null
                                && ForgeEventFactory.onEntityDestroyBlock(spawnedEntity, clearPos, iblockstate)) {
                            destroyedBlocks = world.setBlockToAir(clearPos) || destroyedBlocks;
                        }
                    }
                }
            }
            if (destroyedBlocks) world.playEvent(null, 1022, pos, 0);
        }
    }


    /** Called when the entity is spawned just before it is added to the world. **/
    protected void onSpawnEntity(Entity activatingEntity, EntityLivingBase entity){
        // This can be used on extensions of this class for NBT data, etc.
    }

    @Override
    public void getBeastiaryRender(AltarsBeastiaryScreen altarsBeastiaryScreen, int mouseX, int mouseY, float partialTicks) {
        String[] blockPatternStrings = this.getBlockPatternStrings();
        int xPos = altarsBeastiaryScreen.colRightX + (altarsBeastiaryScreen.colRightWidth / 2);
        int yPos = altarsBeastiaryScreen.colRightY + Math.round((float) altarsBeastiaryScreen.colRightHeight / 2);
        float scale = (altarsBeastiaryScreen.mc.displayWidth / 1920F) * 4.0F;

        Clear:
        if(blockPatternStrings == null) {
            return;
        }

        // Render:
        int drawOffsetX = (int) (12 * scale);
        int drawOffsetY = (int) (12 * scale);
        int drawOffsetZ = (int) (4 * scale);
        int startX = xPos - (drawOffsetX * blockPatternStrings[0].length() / 2);
        int startY = yPos - (drawOffsetY * blockPatternStrings.length / 4);
        int drawX;
        int drawY = startY;

        int bodyCount = 0;

        for (int yIndex = 0; yIndex < blockPatternStrings.length; yIndex++) {
            drawX = startX;
            for (int xIndex = 0; xIndex < blockPatternStrings[yIndex].length(); xIndex++) {
                switch (blockPatternStrings[yIndex].charAt(xIndex)) {
                    case '#':
                        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.bodyBlock), drawX, drawY - (xIndex * drawOffsetY / 2), yIndex * drawOffsetZ, scale);
                        bodyCount++;
                        break;
                    case '^':
                        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.coreBlock), drawX, drawY - (xIndex * drawOffsetY / 2), yIndex * drawOffsetZ, scale);
                        break;
                }
                drawX += drawOffsetX;
            }
            drawY += drawOffsetY;
        }

        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.coreBlock), altarsBeastiaryScreen.colRightX + 2, altarsBeastiaryScreen.colRightY, 0, scale);
        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.bodyBlock), altarsBeastiaryScreen.colRightX + 2, altarsBeastiaryScreen.colRightY + drawOffsetY, 0, scale);
        altarsBeastiaryScreen.getFontRenderer().drawString("[" + bodyCount + "]", altarsBeastiaryScreen.colRightX + 2 + drawOffsetX * 1.5F, altarsBeastiaryScreen.colRightY + drawOffsetY * 1.5F, 0xFFFFFF, true);
    }
}
