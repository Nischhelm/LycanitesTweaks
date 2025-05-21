package lycanitestweaks.mixin.lycanitesmobspatches.core.containers;

import com.lycanitesmobs.core.container.EquipmentInfuserContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.annotation.Nonnull;

@Mixin(EquipmentInfuserContainer.class)
public abstract class EquipmentInfuserContainerMixin extends Container {

    /**
     * @author Nischhelm
     * @reason base lyca removed the whole quick move mechanic from all the containers except pet inventory, this reinstates it
     */
    @Overwrite
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stackOriginalInSlot = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackRemainingInSlot = slot.getStack();
            stackOriginalInSlot = stackRemainingInSlot.copy();

            /*
            0 to 8 player hotbar
            9 to 35 player inv
            36 charge
            37 tool
             */

            //Move from charge/tool slots to hotbar/inventory
            if (index == 36 || index == 37) {
                //Hotbar and Inventory are swapped in Lyca Containers, so we have to split it up, technically this is stupid af
                if (!this.mergeItemStack(stackRemainingInSlot, 0, 9, true) &&
                        !this.mergeItemStack(stackRemainingInSlot, 9, 36, true))
                    return ItemStack.EMPTY;

                slot.onSlotChange(stackRemainingInSlot, stackOriginalInSlot);
            }
            //Move from hotbar+inventory to charge/tool slots
            else if (!this.mergeItemStack(stackRemainingInSlot, 36, 38, false))
                return ItemStack.EMPTY;

            //Move from inventory to hotbar
            else if (index >= 9 && index < 35) {
                if (!this.mergeItemStack(stackRemainingInSlot, 0, 9, false))
                    return ItemStack.EMPTY;
            }
            //Move from hotbar to inventory
            else if (index >= 0 && index < 9) {
                if (!this.mergeItemStack(stackRemainingInSlot, 9, 36, false))
                    return ItemStack.EMPTY;
            }

            if (stackRemainingInSlot.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();

            if (stackRemainingInSlot.getCount() == stackOriginalInSlot.getCount()) return ItemStack.EMPTY;

            slot.onTake(player, stackRemainingInSlot);
        }

        return stackOriginalInSlot;
    }
}
