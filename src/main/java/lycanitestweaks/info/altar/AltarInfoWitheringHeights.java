package lycanitestweaks.info.altar;

import com.lycanitesmobs.ExtendedWorld;
import com.lycanitesmobs.core.info.AltarInfo;
import lycanitestweaks.client.gui.beastiary.AltarsBeastiaryScreen;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AltarInfoWitheringHeights extends AltarInfo implements IAltarNoBoost, IAltarBeastiaryRender{

    private final Block coreBlock;
    private final Block bodyBlock;
    private final int width;
    private final int height;

    public AltarInfoWitheringHeights(String name) {
        super(name);
        this.coreBlock = Blocks.NETHER_WART_BLOCK;
        this.bodyBlock = Blocks.SOUL_SAND;
        this.width = this.height = 1;
    }

    public static boolean isWitherSkull(World world, BlockPos pos){
        return (world.getBlockState(pos).getBlock() == Blocks.SKULL)
                && world.getTileEntity(pos) instanceof TileEntitySkull
                && ((TileEntitySkull) world.getTileEntity(pos)).getSkullType() == 1;
    }

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

        int interactX = pos.getX();
        int interactY = pos.getY();
        int interactZ = pos.getZ();
        int levels = 0;

        for(int offset = 1; offset <= this.height; levels = offset++){
            int checkY = interactY - offset;
            if(checkY < 0) break;
            boolean checkNextLayer = true;

            for(int checkX = interactX - offset; checkX <= interactX + offset && checkNextLayer; ++checkX){
                for(int checkZ = interactZ - offset; checkZ <= interactZ + offset; ++checkZ){
                    BlockPos checkPos = new BlockPos(checkX, checkY, checkZ);
                    Block block = world.getBlockState(checkPos).getBlock();

                    if(checkX == interactX - offset || checkX == interactX + offset || checkZ == interactZ - offset || checkZ == interactZ + offset){
                        if (!AltarInfoWitheringHeights.isWitherSkull(world, checkPos)) {
                            checkNextLayer = false;
                            break;
                        }
                    }
                    else {
                        if (!(block == bodyBlock)) {
                            checkNextLayer = false;
                            break;
                        }
                    }
                }
            }
            if(!checkNextLayer) break;
        }

        return (levels == this.height);
    }

    // ==================================================
    //                     Activate
    // ==================================================
    /** Called when this Altar should activate. This will typically destroy the Altar and summon a rare mob or activate an event such as a boss event. If false is returned then the activation did not work, this is the place to check for things like dimensions. **/
    @Override
    public boolean activate(Entity activatingEntity, World world, BlockPos pos, int variant) {
        if (world.isRemote)
            return true;

        ExtendedWorld worldExt = ExtendedWorld.getForWorld(world);
        if(worldExt == null)
            return false;
        this.clearAltar(world, pos);

        // Offset:
        pos = new BlockPos(pos.getX(), Math.max(1, pos.getY() - 3), pos.getZ());
        if(activatingEntity != null)
            pos = this.getFacingPosition(pos, 10, activatingEntity.rotationYaw);

        return super.activate(activatingEntity, world, pos, variant);
    }

    protected void clearAltar(World world, BlockPos pos){
        int y = MathHelper.floor(pos.getY());
        int x = MathHelper.floor(pos.getX());
        int z = MathHelper.floor(pos.getZ());

        for(int xTarget = x - this.width; xTarget <= x + this.width; ++xTarget) {
            for(int zTarget = z - this.width; zTarget <= z + this.width; ++zTarget) {
                for(int yTarget = y - this.height; yTarget <= y + this.height; ++yTarget) {
                    BlockPos clearPos = new BlockPos(xTarget, yTarget, zTarget);
                    IBlockState iblockstate = world.getBlockState(clearPos);
                    Block block = iblockstate.getBlock();
                    if (block == this.bodyBlock || block == this.coreBlock || AltarInfoWitheringHeights.isWitherSkull(world, clearPos)) {
                        world.setBlockToAir(clearPos);
                    }
                }
            }
        }
    }

    @Override
    public void getBeastiaryRender(AltarsBeastiaryScreen altarsBeastiaryScreen, int mouseX, int mouseY, float partialTicks) {
        int xPos = altarsBeastiaryScreen.colRightX + (altarsBeastiaryScreen.colRightWidth / 2);
        int yPos = altarsBeastiaryScreen.colRightY + Math.round((float) altarsBeastiaryScreen.colRightHeight / 2);
        float scale = (altarsBeastiaryScreen.mc.displayWidth / 1920F) * 15.0F / (1 + this.height);

        // Render:
        int drawOffsetX = (int) (12 * scale);
        int drawOffsetY = (int) (12 * scale);
        int drawOffsetZ = (int) (4 * scale);
        int startX = xPos - drawOffsetX / 2;
        int startY = yPos - drawOffsetY * this.height;
        int drawX = startX;
        int drawY = startY;

        int skullCount = 0;
        int bodyCount = 0;

        altarsBeastiaryScreen.drawItemStack(new ItemStack(coreBlock), drawX, drawY, drawOffsetZ, scale);
        int width = 3;
        for(int y = 0; y < this.height; y++) {
            startY += drawOffsetY * 2;
            drawY = startY;
            for (int z = 0; z < width; z++) {
                for (int x = 0; x < width; x++) {
                    if(z == 0 || z == width -  1 || x == 0 || x == width - 1) {
                        altarsBeastiaryScreen.drawItemStack(new ItemStack(Items.SKULL, 1, 1), drawX + (x * drawOffsetX), drawY - (x * drawOffsetY / 2), (z + x + 1) * drawOffsetZ, scale);
                        skullCount++;
                    }
                    else {
                        altarsBeastiaryScreen.drawItemStack(new ItemStack(bodyBlock), drawX + (x * drawOffsetX), drawY - (x * drawOffsetY / 2), (z + x + 1) * drawOffsetZ, scale);
                        bodyCount++;
                    }
                }
                drawX -= drawOffsetX;
                drawY -= drawOffsetY / 2;
            }
            drawX = startX;
            width += 2;
        }

        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.coreBlock), altarsBeastiaryScreen.colRightX + 2, altarsBeastiaryScreen.colRightY, drawOffsetZ, scale);
        altarsBeastiaryScreen.drawItemStack(new ItemStack(Items.SKULL, 1, 1), altarsBeastiaryScreen.colRightX + 2, altarsBeastiaryScreen.colRightY + drawOffsetY, drawOffsetZ, scale);
        altarsBeastiaryScreen.getFontRenderer().drawString("[" + skullCount + "]", altarsBeastiaryScreen.colRightX + 2 + drawOffsetX * 1.5F, altarsBeastiaryScreen.colRightY + drawOffsetY * 1.5F, 0xFFFFFF, true);
        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.bodyBlock), altarsBeastiaryScreen.colRightX + 2, altarsBeastiaryScreen.colRightY + drawOffsetY * 2, drawOffsetZ, scale);
        altarsBeastiaryScreen.getFontRenderer().drawString("[" + bodyCount + "]", altarsBeastiaryScreen.colRightX + 2 + drawOffsetX * 1.5F, altarsBeastiaryScreen.colRightY + drawOffsetY * 2.5F, 0xFFFFFF, true);
    }
}
