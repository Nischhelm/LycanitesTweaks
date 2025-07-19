package lycanitestweaks.network;

import io.netty.buffer.ByteBuf;
import lycanitestweaks.capability.lycanitestweaksplayer.ILycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.lycanitestweaksplayer.LycanitesTweaksPlayerCapability;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketKeybindsSoulgazerToggle implements IMessage {

    private byte soulgazerAuto;
    private boolean soulgazerManual;

    public PacketKeybindsSoulgazerToggle() {}
    public PacketKeybindsSoulgazerToggle(LycanitesTweaksPlayerCapability ltp) {
        this.soulgazerAuto = ltp.getSoulgazerAutoToggle();
        this.soulgazerManual = ltp.getSoulgazerManualToggle();
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


    public static class ServerHandler implements IMessageHandler<PacketKeybindsSoulgazerToggle, IMessage> {

        @Override
        public IMessage onMessage(final PacketKeybindsSoulgazerToggle message, final MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private static void handle(PacketKeybindsSoulgazerToggle message, MessageContext ctx) {
            ILycanitesTweaksPlayerCapability ltp = LycanitesTweaksPlayerCapability.getForPlayer(Minecraft.getMinecraft().player);
            if(ltp != null){
                ltp.setSoulgazerAutoToggle(message.soulgazerAuto);
                ltp.setSoulgazerManualToggle(message.soulgazerManual);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientHandler implements IMessageHandler<PacketKeybindsSoulgazerToggle, IMessage> {

        @Override
        public IMessage onMessage(PacketKeybindsSoulgazerToggle message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                ILycanitesTweaksPlayerCapability ltp = LycanitesTweaksPlayerCapability.getForPlayer(Minecraft.getMinecraft().player);
                if(ltp != null){
                    ltp.setSoulgazerAutoToggle(message.soulgazerAuto);
                    ltp.setSoulgazerManualToggle(message.soulgazerManual);
                }
            });
            return null;
        }
    }
}
