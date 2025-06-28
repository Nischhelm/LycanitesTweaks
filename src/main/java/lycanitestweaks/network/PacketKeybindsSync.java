package lycanitestweaks.network;

import io.netty.buffer.ByteBuf;
import lycanitestweaks.capability.ILycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.LycanitesTweaksPlayerCapability;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketKeybindsSync implements IMessage {

    private byte soulgazerAuto;
    private boolean soulgazerManual;

    public PacketKeybindsSync() {}
    public PacketKeybindsSync(LycanitesTweaksPlayerCapability ltPlayer) {
        this.soulgazerAuto = ltPlayer.getSoulgazerAutoToggle();
        this.soulgazerManual = ltPlayer.getSoulgazerManualToggle();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.soulgazerAuto = buf.readByte();
        this.soulgazerManual = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.soulgazerAuto);
        buf.writeBoolean(this.soulgazerManual);
    }


    public static class ServerHandler implements IMessageHandler<PacketKeybindsSync, IMessage> {

        @Override
        public IMessage onMessage(PacketKeybindsSync message, MessageContext ctx) {
            return null;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientHandler implements IMessageHandler<PacketKeybindsSync, IMessage> {

        @Override
        public IMessage onMessage(PacketKeybindsSync message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                ILycanitesTweaksPlayerCapability ltPlayer = LycanitesTweaksPlayerCapability.getForPlayer(Minecraft.getMinecraft().player);
                if(ltPlayer != null){
                    ltPlayer.setSoulgazerAutoToggle(message.soulgazerAuto);
                    ltPlayer.setSoulgazerManualToggle(message.soulgazerManual);
                }
            });
            return null;
        }
    }
}
