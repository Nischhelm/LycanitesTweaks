package lycanitestweaks.item;

import com.lycanitesmobs.client.AssetManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class ItemRapidChargeStaff extends ItemChargeStaff {

    public ItemRapidChargeStaff(String itemName) {
        super(itemName);
        this.infinityProjectileName = "devilgatling";
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityLiving;
            player.getCooldownTracker().setCooldown(this, Math.min(this.getMaxItemUseDuration(stack), this.getMaxItemUseDuration(stack) - timeLeft));
        }
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count){
        int useTime = this.getMaxItemUseDuration(stack) - count;
        if(useTime > 420) {
            player.stopActiveHand();
        }
        else if(useTime < 20) return;
        if(player.isSneaking()) this.fireChargeProjectile(stack, player.getEntityWorld(), player, count, 0, 0, 8F);
        else if(!player.isSneaking() && count % 10 == 0) this.fireChargeProjectile(stack, player.getEntityWorld(), player, count);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment){
        if(enchantment == Enchantments.INFINITY) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public SoundEvent getFireSound(){
        return AssetManager.getSound( "asmodeus_attack");
    }

    public float getFireVelocityModifier(){
        return 4F;
    }

    @Override
    protected void setInfinityProjectileName(String projectileName){
    }
}
