package lycanitestweaks.network;

import com.lycanitesmobs.LycanitesMobs;
import io.netty.buffer.ByteBuf;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.capability.ILycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.LycanitesTweaksPlayerCapability;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PacketPlayerMobLevelsModifiers implements IMessage {

    private float defaultModifier = 1.0F;
    private HashMap<String, Float> pmlModifiers = new HashMap<>();

    public PacketPlayerMobLevelsModifiers() {}
    public PacketPlayerMobLevelsModifiers(PlayerMobLevelCapability pml) {
        this.defaultModifier = pml.defaultModifier;
        this.pmlModifiers = pml.pmlModifiers;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packet = new PacketBuffer(buf);
        try {
            this.defaultModifier = packet.readFloat();

            int mapSize = packet.readInt();

            for(int i = 0; i < mapSize; i++){
                this.pmlModifiers.put(packet.readString(256), packet.readFloat());
            }
        }
        catch(Exception exception) {
            LycanitesTweaks.LOGGER.warn("There was a problem decoding the packet: {}.", packet);
            LycanitesTweaks.LOGGER.warn(exception);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packet = new PacketBuffer(buf);
        packet.writeFloat(this.defaultModifier);

        packet.writeInt(this.pmlModifiers.size());
        this.pmlModifiers.forEach((creatureName, modifier) -> {
            packet.writeString(creatureName);
            packet.writeFloat(modifier);
        });
    }


    public static class ServerHandler implements IMessageHandler<PacketPlayerMobLevelsModifiers, IMessage> {

        @Override
        public IMessage onMessage(final PacketPlayerMobLevelsModifiers message, final MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private static void handle(PacketPlayerMobLevelsModifiers message, MessageContext ctx) {
            IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(ctx.getServerHandler().player);
            if(pml instanceof PlayerMobLevelCapability){
                ((PlayerMobLevelCapability) pml).defaultModifier = message.defaultModifier;
                ((PlayerMobLevelCapability) pml).pmlModifiers = message.pmlModifiers;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientHandler implements IMessageHandler<PacketPlayerMobLevelsModifiers, IMessage> {

        @Override
        public IMessage onMessage(PacketPlayerMobLevelsModifiers message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(Minecraft.getMinecraft().player);
                if (pml instanceof PlayerMobLevelCapability) {
                    ((PlayerMobLevelCapability) pml).defaultModifier = message.defaultModifier;
                    ((PlayerMobLevelCapability) pml).pmlModifiers = message.pmlModifiers;
                }
            });
            return null;
        }
    }
}
