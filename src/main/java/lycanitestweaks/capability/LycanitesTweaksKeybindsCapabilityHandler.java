package lycanitestweaks.capability;

import lycanitestweaks.LycanitesTweaks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LycanitesTweaksKeybindsCapabilityHandler {

    public static final ResourceLocation LT_KEYBINDS_KEY = new ResourceLocation(LycanitesTweaks.MODID, "keybinds");

    @CapabilityInject(LycanitesTweaksKeybindsCapability.class)
    public static Capability<ILycanitesTweaksKeybindsCapability> LT_KEYBINDS;

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(LycanitesTweaksKeybindsCapability.class, new Storage(), LycanitesTweaksKeybindsCapability::new);
    }

    public static class AttachCapabilityHandler {
        @SubscribeEvent
        public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof EntityPlayer) {
                event.addCapability(LT_KEYBINDS_KEY, new LycanitesTweaksKeybindsCapabilityHandler.Provider((EntityPlayer)event.getObject()));
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
        private final ILycanitesTweaksKeybindsCapability instance;

        public Provider(EntityPlayer player) {
            this.instance = new LycanitesTweaksKeybindsCapability(player);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == LT_KEYBINDS;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == LT_KEYBINDS ? LT_KEYBINDS.cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return (NBTTagCompound) LT_KEYBINDS.writeNBT(instance, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            LT_KEYBINDS.readNBT(instance, null, nbt);
        }
    }

    private static class Storage implements Capability.IStorage<LycanitesTweaksKeybindsCapability> {

        @Override
        public NBTBase writeNBT(Capability<LycanitesTweaksKeybindsCapability> capability, LycanitesTweaksKeybindsCapability instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            return nbt;
        }

        @Override
        public void readNBT(Capability<LycanitesTweaksKeybindsCapability> capability, LycanitesTweaksKeybindsCapability instance, EnumFacing side, NBTBase nbt) {
        }
    }
}
