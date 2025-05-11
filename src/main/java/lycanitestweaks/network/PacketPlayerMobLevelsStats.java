package lycanitestweaks.network;

import io.netty.buffer.ByteBuf;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PacketPlayerMobLevelsStats implements IMessage {

    private int[] nonMainLevels = new int[5];
    private Queue<Integer> mainHandLevels = new LinkedList<>();
    private Map<Integer, Integer> petEntryLevels = new HashMap<>();

    public PacketPlayerMobLevelsStats() {}
    public PacketPlayerMobLevelsStats(PlayerMobLevelCapability pml) {
        this.nonMainLevels = pml.nonMainLevels;
        this.mainHandLevels = pml.mainHandLevels;
        this.petEntryLevels = pml.petEntryLevels;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        int nonMainLevelsSize = buf.readInt();
        if(nonMainLevelsSize == 5){
            for(int i = 0; i < 5; i++){
                this.nonMainLevels[i] = buf.readInt();
            }
        }

        int mainHandLevelsSize = buf.readInt();
        if(mainHandLevelsSize <= PlayerMobLevelCapability.MAINHAND_CHECK_SIZE){
            for(int i = 0; i < mainHandLevelsSize; i++){
                this.mainHandLevels.add(buf.readInt());
            }
        }

        int petEntryLevelsSize = buf.readInt();
        for(int i = 0; i < petEntryLevelsSize; i++){
            this.petEntryLevels.put(buf.readInt(), buf.readInt());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(nonMainLevels.length);
        for(int level : nonMainLevels){
            buf.writeInt(level);
        }

        buf.writeInt(mainHandLevels.size());
        for(int level : mainHandLevels){
            buf.writeInt(level);
        }

        buf.writeInt(petEntryLevels.size());
        petEntryLevels.forEach((Integer level, Integer count) -> {
            buf.writeInt(level);
            buf.writeInt(count);
        });
    }


    public static class ServerHandler implements IMessageHandler<PacketPlayerMobLevelsStats, IMessage> {

        @Override
        public IMessage onMessage(PacketPlayerMobLevelsStats message, MessageContext ctx) {
            return null;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientHandler implements IMessageHandler<PacketPlayerMobLevelsStats, IMessage> {

        @Override
        public IMessage onMessage(PacketPlayerMobLevelsStats message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(Minecraft.getMinecraft().player);
                if (pml instanceof PlayerMobLevelCapability) {
                    if(message.mainHandLevels.isEmpty()) message.mainHandLevels.add(0);

                    ((PlayerMobLevelCapability) pml).nonMainLevels = message.nonMainLevels;
                    ((PlayerMobLevelCapability) pml).mainHandLevels = message.mainHandLevels;
                    ((PlayerMobLevelCapability) pml).petEntryLevels = message.petEntryLevels;
                    pml.clearHighestLevelPetActive();
                }
            });
            return null;
        }
    }
}
