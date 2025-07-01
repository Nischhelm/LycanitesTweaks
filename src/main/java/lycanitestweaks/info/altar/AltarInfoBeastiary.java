package lycanitestweaks.info.altar;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.AltarInfo;
import com.lycanitesmobs.core.info.CreatureKnowledge;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.client.gui.beastiary.AltarsBeastiaryScreen;
import lycanitestweaks.handlers.ForgeConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class AltarInfoBeastiary extends AltarInfo implements IAltarNoBoost, IAltarBeastiaryRender{

    private final Block coreBlock;
    private final Block bodyBlock;
    private final int width;
    private final int height;

    public AltarInfoBeastiary(String name) {
        super(name);
        this.coreBlock = Blocks.REDSTONE_BLOCK;
        this.bodyBlock = Blocks.OBSIDIAN;
        this.width = this.height = ForgeConfigHandler.server.altarsConfig.beastiaryAltarObsidian;
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
                    Block block = world.getBlockState(new BlockPos(checkX, checkY, checkZ)).getBlock();

                    if(!(block == bodyBlock)){
                        checkNextLayer = false;
                        break;
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
    public boolean activate(Entity entity, World world, BlockPos pos, int variant) {
        if(world.isRemote) return true;

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        EntityLivingBase entityCreature = this.createEntity(entity, world);
        if(entityCreature == null) return false;

        if(entityCreature instanceof BaseCreatureEntity) {
            this.clearAltar(world, pos);

            // Spawn Mini Boss:
            entityCreature.setLocationAndAngles(x, y - 2, z, 0, 0);

            world.spawnEntity(entityCreature);

            return true;
        }
        return false;
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
                    if (block == this.bodyBlock || block == this.coreBlock) {
                        world.setBlockToAir(clearPos);
                    }
                }
            }
        }
    }

    protected EntityLivingBase createEntity(Entity entity, World world){
        ExtendedPlayer extendedPlayer = null;

        if(entity instanceof EntityPlayer) extendedPlayer = ExtendedPlayer.getForPlayer((EntityPlayer) entity);

        if(extendedPlayer == null || extendedPlayer.selectedCreature == null) {
            if(extendedPlayer != null) extendedPlayer.getPlayer().sendStatusMessage(new TextComponentTranslation("message.altar.beastiary.null"), true);
            return null;
        }

        if(extendedPlayer.selectedCreature.isBoss()){
            extendedPlayer.getPlayer().sendStatusMessage(new TextComponentTranslation("message.altar.beastiary.boss"), true);
            return null;
        }

        CreatureKnowledge creatureKnowledge = extendedPlayer.getBeastiary().getCreatureKnowledge(extendedPlayer.selectedCreature.getName());
        if(creatureKnowledge != null && creatureKnowledge.rank < ForgeConfigHandler.server.altarsConfig.beastiaryAltarKnowledgeRank) {
            extendedPlayer.getPlayer().sendStatusMessage(new TextComponentTranslation("message.altar.beastiary.rank"), true);
            return null;
        }

        EntityLiving selectedEntity = null;

        try {
            selectedEntity = extendedPlayer.selectedCreature.entityClass.getConstructor(new Class[]{World.class}).newInstance(world);
        } catch (Exception e) {
            LycanitesTweaks.LOGGER.error("An exception occurred when trying to create a creature in the AltarInfoBeastiary: {}", e.toString());
        }

        if(selectedEntity instanceof BaseCreatureEntity){
            // Spawn Mini Boss:
            BaseCreatureEntity entityCreature = (BaseCreatureEntity) selectedEntity;
            // Create Mini Boss:
            if (checkDimensions && !entityCreature.isNativeDimension(world)) {
                extendedPlayer.getPlayer().sendStatusMessage(new TextComponentTranslation("message.altar.fail.native"), true);
                return null;
            }
            entityCreature.altarSummoned = true;

            entityCreature.onFirstSpawn();
            entityCreature.setSubspecies(extendedPlayer.selectedSubspecies);
            entityCreature.applyVariant(extendedPlayer.selectedVariant);

            return entityCreature;
        }
        extendedPlayer.getPlayer().sendStatusMessage(new TextComponentTranslation("message.altar.beastiary.null"), true);
        return null;
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

        int bodyCount = 0;

        altarsBeastiaryScreen.drawItemStack(new ItemStack(coreBlock), drawX, drawY, 0, scale);
        int width = 3;
        for(int y = 0; y < this.height; y++) {
            startY += drawOffsetY * 2;
            drawY = startY;
            for (int z = 0; z < width; z++) {
                for (int x = 0; x < width; x++) {
                    altarsBeastiaryScreen.drawItemStack(new ItemStack(bodyBlock), drawX + (x * drawOffsetX), drawY - (x * drawOffsetY / 2), (z + x + 1) * drawOffsetZ, scale);
                    bodyCount++;
                }
                drawX -= drawOffsetX;
                drawY -= drawOffsetY / 2;
            }
            drawX = startX;
            width += 2;
        }

        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.coreBlock), altarsBeastiaryScreen.colRightX + 2, altarsBeastiaryScreen.colRightY, drawOffsetZ, scale);
        altarsBeastiaryScreen.drawItemStack(new ItemStack(this.bodyBlock), altarsBeastiaryScreen.colRightX + 2, altarsBeastiaryScreen.colRightY + drawOffsetY, drawOffsetZ, scale);
        altarsBeastiaryScreen.getFontRenderer().drawString("[" + bodyCount + "]", altarsBeastiaryScreen.colRightX + 2 + drawOffsetX * 1.5F, altarsBeastiaryScreen.colRightY + drawOffsetY * 1.5F, 0xFFFFFF, true);
    }
}
