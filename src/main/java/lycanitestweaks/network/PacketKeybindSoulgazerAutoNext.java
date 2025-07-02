package lycanitestweaks.network;

import io.netty.buffer.ByteBuf;
import lycanitestweaks.capability.LycanitesTweaksPlayer.ILycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.LycanitesTweaksPlayer.LycanitesTweaksPlayerCapability;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketKeybindSoulgazerAutoNext implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class ServerHandler implements IMessageHandler<PacketKeybindSoulgazerAutoNext, IMessage> {

        @Override
        public IMessage onMessage(final PacketKeybindSoulgazerAutoNext message, final MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private static void handle(PacketKeybindSoulgazerAutoNext message, MessageContext ctx) {
            ILycanitesTweaksPlayerCapability ltPlayer = LycanitesTweaksPlayerCapability.getForPlayer(ctx.getServerHandler().player);
            if(ltPlayer != null) ltPlayer.nextSoulgazerAutoToggle();
        }
    }
}
