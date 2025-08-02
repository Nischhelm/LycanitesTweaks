package lycanitestweaks.item;

import com.lycanitesmobs.core.entity.BaseProjectileEntity;
import com.lycanitesmobs.core.info.projectile.ProjectileManager;
import com.lycanitesmobs.core.item.ChargeItem;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.entity.projectile.EntityChargeArrow;
import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.ForgeConfigProvider;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemChargeStaff extends ItemBow {

    protected String infinityProjectileName;

    public ItemChargeStaff(String itemName){
        super();
        this.setMaxDamage(ToolMaterial.DIAMOND.getMaxUses());

        this.setRegistryName(LycanitesTweaks.MODID, itemName);
        this.setTranslationKey(LycanitesTweaks.MODID + "." + itemName);
    }

    @Override
    protected boolean isArrow(ItemStack stack) {
        return stack.getItem() instanceof ChargeItem;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        if(this.getMaxItemUseDuration(stack) - timeLeft >= 20) this.fireChargeProjectile(stack, world, entityLiving, timeLeft);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
        return repair.getItem() == Items.END_CRYSTAL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        if(this.findAmmo(player).isEmpty()) {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment){
        if(!ForgeConfigHandler.server.customStaffConfig.chargeStaffEnchantability) return false;
        if(ForgeConfigProvider.getChargeStaffEnchantmentBlacklist().contains(enchantment)) return false;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    public SoundEvent getFireSound(){
        return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
    }

    // The shooting velocity of the Charge, 3.0 is how much the Vanilla Bow gives to an Arrow
    public float getFireVelocityModifier(){
        return 3F;
    }

    // Condition to shoot a charge with max pierce using the Vanilla Critical Arrow property
    public boolean isCriticalPiercingCharge(int charge){
        return charge >= 60;
    }

    public String getInfinityProjectileName(){
        return this.infinityProjectileName;
    }

    protected void setInfinityProjectileName(String projectileName){
        this.infinityProjectileName = projectileName;
    }

    protected BaseProjectileEntity createInfinityProjectile(World world, EntityPlayer player){
        BaseProjectileEntity projectile = null;
        if(this.getInfinityProjectileName().isEmpty()) return projectile;

        ProjectileManager projectileManager = ProjectileManager.getInstance();
        if(projectileManager.oldModelProjectiles.containsKey(this.getInfinityProjectileName())){
            try {
                projectile = (BaseProjectileEntity) projectileManager.oldModelProjectiles.get(this.getInfinityProjectileName()).getConstructor(World.class, EntityLivingBase.class).newInstance(world, player);
            }
            catch (Exception e) {
                LycanitesTweaks.LOGGER.warn("Unable to create a oldModelProjectiles from the class: {}", this.getInfinityProjectileName());
            }
        }
        else if(projectileManager.oldSpriteProjectiles.containsKey(this.getInfinityProjectileName())){
            try {
                projectile = (BaseProjectileEntity) projectileManager.oldSpriteProjectiles.get(this.getInfinityProjectileName()).getConstructor(World.class, EntityLivingBase.class).newInstance(world, player);
            }
            catch (Exception e) {
                LycanitesTweaks.LOGGER.warn("Unable to create a oldSpriteProjectiles from the class: {}", this.getInfinityProjectileName());
            }
        }
        else if(projectileManager.getProjectile(this.getInfinityProjectileName()) != null){
            projectile = projectileManager.getProjectile(this.getInfinityProjectileName()).createProjectile(world, player);
        }
        if(projectile != null) {
            projectile.playSound(projectile.getLaunchSound(), 1.0F, 1.0F / (player.getRNG().nextFloat() * 0.4F + 0.8F));
        }
        return projectile;
    }

    public void fireChargeProjectile(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        this.fireChargeProjectile(stack, world, entityLiving, timeLeft, 0, 0, 1F);
    }

    public void fireChargeProjectile(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft, float pitchOffset, float yawOffset, float inaccuracy){
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityLiving;
            boolean hasInfinity = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = this.findAmmo(player);

            int charge = this.getMaxItemUseDuration(stack) - timeLeft;
            charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, player, charge, !itemStack.isEmpty() || hasInfinity);
            if (charge < 0) return;

            float launchVelocity = getArrowVelocity(charge);
            if (launchVelocity >= 0.1F) {
                if (!world.isRemote) {
                    BaseProjectileEntity projectile = null;

                    if (!itemStack.isEmpty()) {
                        ChargeItem chargeItem = (ChargeItem) itemStack.getItem();
                        projectile = chargeItem.createProjectile(itemStack, world, player);
                        if (projectile == null) {
                            LycanitesTweaks.LOGGER.warn("Failed to create projectile from Charge Item: {}", chargeItem.itemName);
                            return;
                        }
                        this.setInfinityProjectileName(chargeItem.projectileInfo.getName());
                        chargeItem.playSound(world, player.getPosition(), projectile.getLaunchSound(), SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                    }
                    else if (hasInfinity) {
                        projectile = this.createInfinityProjectile(world, player);
                    }
                    if(projectile == null) return;

                    world.spawnEntity(projectile);

                    EntityArrow entityarrow = new EntityChargeArrow(world, player, projectile);
                    entityarrow = this.customizeArrow(entityarrow);
                    entityarrow.shoot(player, player.rotationPitch + pitchOffset, player.rotationYaw + yawOffset, 0.0F, launchVelocity * this.getFireVelocityModifier(), inaccuracy);

                    if (this.isCriticalPiercingCharge(charge)) entityarrow.setIsCritical(true);

                    int powerBonus = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (powerBonus > 0) {
                        entityarrow.setDamage(entityarrow.getDamage() + (double)powerBonus * 0.5D + 0.5D);
                    }

                    if(ForgeConfigHandler.server.customStaffConfig.chargeStaffArrowsWorld) {
                        int punchBonus = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (punchBonus > 0) {
                            entityarrow.setKnockbackStrength(punchBonus);
                        }
                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            entityarrow.setFire(100);
                        }
                    }
                    stack.damageItem(1, player);
                    entityarrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED;

                    world.spawnEntity(entityarrow);
                    projectile.playSound(this.getFireSound(), 1.0F, 1.0F / (player.getRNG().nextFloat() * 0.4F + 0.8F));

                    float spawnVelocity = MathHelper.sqrt(entityarrow.motionX * entityarrow.motionX + entityarrow.motionY * entityarrow.motionY + entityarrow.motionZ * entityarrow.motionZ);
                    int vanillaHitDamage = MathHelper.ceil((double)spawnVelocity * entityarrow.getDamage());

                    projectile.projectileLife += (int) (5 * spawnVelocity); // Some more range but not too much to make lasers OP
                    projectile.weight = (double) 5/3; // Vanilla arrow gravity
                    projectile.setDamage(vanillaHitDamage); // Vanilla arrow hit damage
                    if (entityarrow.getIsCritical()) projectile.setPierce(projectile.damage - 1); // Crit is full piercing instead

                    // Give charge the arrow's velocity
                    projectile.motionX = entityarrow.motionX;
                    projectile.motionY = entityarrow.motionY;
                    projectile.motionZ = entityarrow.motionZ;

                    if(!ForgeConfigHandler.server.customStaffConfig.chargeStaffArrowsWorld) entityarrow.setDead();
                }

                if (!player.capabilities.isCreativeMode && !hasInfinity) {
                    itemStack.shrink(1);

                    if (itemStack.isEmpty()) {
                        player.inventory.deleteStack(itemStack);
                    }
                }

                player.addStat(StatList.getObjectUseStats(this));
            }
        }
    }

    @Override
    public int getItemEnchantability() {
        return ToolMaterial.GOLD.getEnchantability();
    }
}
