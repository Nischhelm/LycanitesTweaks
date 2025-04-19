package lycanitestweaks.entity.item;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import lycanitestweaks.capability.EntityStoreCreatureCapabilityHandler;
import lycanitestweaks.capability.IEntityStoreCreatureCapability;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.storedcreatureentity.StoredCreatureEntity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class EntityBossSummonCrystal extends EntityEnderCrystal {

    // Lobotomized EnderCrystal, can only be killed by player melee

    // Creative Mode Players can set stored entity when sneaking
    // Nether Star - Clear current stored entity
    // Stick - Store nearby EntityLiving
    // Anything - Store nearby BaseCreatureEntity

    // Player target can become null
    private EntityPlayer target;
    // Lost and reset upon entity reload
    private float searchDistance = ForgeConfigHandler.server.escConfig.bossCrystalTickDistance;

    public EntityBossSummonCrystal(World worldIn) {
        super(worldIn);
        this.setShowBottom(false); // Manually set this to represent a stored entity
    }

    @Override
    public void onUpdate(){
        super.onUpdate();
        if(!this.world.isRemote){
            BlockPos blockpos = new BlockPos(this);

            if (!(this.world.provider instanceof WorldProviderEnd) && this.world.getBlockState(blockpos).getBlock() != Blocks.FIRE){
                this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
            }
            if(this.ticksExisted % 20 == 0 && ForgeConfigHandler.server.escConfig.bossCrystalTickChecks) {
                if(this.target == null){
                    EntityPlayer candidate = this.world.getNearestPlayerNotCreative(this, this.searchDistance);
                    if (candidate != null && candidate.canEntityBeSeen(this)) this.target = candidate;
                }
                else if(this.searchDistance < 0 || this.getDistance(this.target) > this.searchDistance) {
                    this.attackEntityFrom(DamageSource.causePlayerDamage(this.target), 0);
                }
                else if (this.getDistance(this.target) > this.searchDistance / 4) {
                    this.searchDistance--;
                }
            }
            if(this.target != null) this.setBeamTarget(this.target.getPosition());
            else this.setBeamTarget(null);
        }
    }

    // Main method to link a player to entity summoning
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount){
        if(source.getTrueSource() instanceof EntityPlayer && "player".equals(source.damageType)){
            if (!this.isDead && !this.world.isRemote){
                this.setDead();

                if (!this.world.isRemote){
                    if (!source.isExplosion()){
                        this.world.createExplosion(source.getTrueSource(), this.posX, this.posY, this.posZ, 6.0F, false);
                    }

                    IEntityStoreCreatureCapability storeCreature = this.getCapability(EntityStoreCreatureCapabilityHandler.ENTITY_STORE_CREATURE, null);
                    if(storeCreature != null){
                        storeCreature.getStoredCreatureEntity().spawnEntity((EntityLivingBase)source.getTrueSource());
                    }

                    this.onKillCommand();
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand){
        if(player.isCreative() && player.isSneaking()){
            if(!player.getEntityWorld().isRemote){
                IEntityStoreCreatureCapability storeCreature = this.getCapability(EntityStoreCreatureCapabilityHandler.ENTITY_STORE_CREATURE, null);
                if (storeCreature != null) {
                    if(player.getHeldItemMainhand().getItem() == Items.NETHER_STAR){
                        storeCreature.setStoredCreatureEntity(new StoredCreatureEntity(this, ""));
                        this.setShowBottom(false);
                        player.sendMessage(new TextComponentString("Clearing Stored Entity"));
                    }
                    else if(player.getHeldItemMainhand().getItem() == Items.STICK){
                        EntityLiving entity = (EntityLiving)this.world.findNearestEntityWithinAABB(EntityLiving.class, this.getEntityBoundingBox().grow(this.width * 2), this);
                        if (entity != null) {
                            storeCreature.setStoredCreatureEntity(StoredCreatureEntity.createFromEntity(this, entity));
                            this.setShowBottom(true);
                            player.sendMessage(new TextComponentString("Storing EntityLiving: " + storeCreature.getStoredCreatureEntity().creatureTypeName));
                        } else {
                            player.sendMessage(new TextComponentString("Failed to find a nearby EntityLiving"));
                        }
                    }
                    else {
                        BaseCreatureEntity creature = (BaseCreatureEntity)this.world.findNearestEntityWithinAABB(BaseCreatureEntity.class, this.getEntityBoundingBox().grow(this.width * 2), this);
                        if (creature != null) {
                            storeCreature.setStoredCreatureEntity(StoredCreatureEntity.createFromEntity(this, creature)
                                    .setPersistant(creature.isPersistant())
                                    .setFixate(creature.hasFixateTarget())
                                    .setHome(creature.getHomeDistanceMax())
                                    .setSpawnAsBoss(creature.spawnedAsBoss)
                                    .setTemporary(creature.temporaryDuration)
                                    .setMobDropsList(creature.savedDrops)
                                    .setBlockProtection(creature.spawnedWithBlockProtection)
                            );
                            this.setShowBottom(true);
                            player.sendMessage(new TextComponentString("Storing BaseCreatureEntity: " + storeCreature.getStoredCreatureEntity().getDisplayName()));
                        } else {
                            player.sendMessage(new TextComponentString("Failed to find a nearby BaseCreatureEntity"));
                        }
                    }
                }
                else
                    player.sendMessage(new TextComponentString("Crystal does not have EntityStoreCreature Capabilities"));
            }
            return true;
        }
        return false;
    }

}
