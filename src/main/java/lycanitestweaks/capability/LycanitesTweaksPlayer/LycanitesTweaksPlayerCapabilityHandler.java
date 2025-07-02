package lycanitestweaks.capability.LycanitesTweaksPlayer;

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

public class LycanitesTweaksPlayerCapabilityHandler {

    public static final ResourceLocation LT_PLAYER_KEY = new ResourceLocation(LycanitesTweaks.MODID, "player");

    @CapabilityInject(LycanitesTweaksPlayerCapability.class)
    public static Capability<ILycanitesTweaksPlayerCapability> LT_PLAYER;

    public static void registerCapability() {
        CapabilityManager.INSTANCE.register(LycanitesTweaksPlayerCapability.class, new Storage(), LycanitesTweaksPlayerCapability::new);
    }

    public static class AttachCapabilityHandler {
        @SubscribeEvent
        public static void onAttachPlayerCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if(event.getObject() instanceof EntityPlayer) {
                event.addCapability(LT_PLAYER_KEY, new LycanitesTweaksPlayerCapabilityHandler.Provider((EntityPlayer)event.getObject()));
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {
        private final ILycanitesTweaksPlayerCapability instance;

        public Provider(EntityPlayer player) {
            this.instance = new LycanitesTweaksPlayerCapability(player);
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == LT_PLAYER;
        }

        @Nullable
        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == LT_PLAYER ? LT_PLAYER.cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return (NBTTagCompound) LT_PLAYER.writeNBT(instance, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            LT_PLAYER.readNBT(instance, null, nbt);
        }
    }

    private static class Storage implements Capability.IStorage<LycanitesTweaksPlayerCapability> {

        @Override
        public NBTBase writeNBT(Capability<LycanitesTweaksPlayerCapability> capability, LycanitesTweaksPlayerCapability instance, EnumFacing side) {
            NBTTagCompound nbt = new NBTTagCompound();
            return nbt;
        }

        @Override
        public void readNBT(Capability<LycanitesTweaksPlayerCapability> capability, LycanitesTweaksPlayerCapability instance, EnumFacing side, NBTBase nbt) {
        }
    }
}
