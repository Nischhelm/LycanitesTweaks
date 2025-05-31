package lycanitestweaks.network;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import com.lycanitesmobs.core.entity.ExtendedPlayer;
import com.lycanitesmobs.core.info.CreatureInfo;
import com.lycanitesmobs.core.info.CreatureManager;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketExtendedPlayerSelectedCreature implements IMessage {

    public String creatureName;
    public int selectedSubspecies = 0;
    public int selectedVariant = 0;

    public PacketExtendedPlayerSelectedCreature(){}
    public PacketExtendedPlayerSelectedCreature(CreatureInfo creatureInfo, EntityLivingBase entity){
        this.creatureName = creatureInfo.getName();
        if(entity instanceof BaseCreatureEntity){
            this.selectedSubspecies = ((BaseCreatureEntity) entity).getSubspeciesIndex();
            this.selectedVariant = ((BaseCreatureEntity) entity).getVariantIndex();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packet = new PacketBuffer(buf);
        this.creatureName = packet.readString(256);
        this.selectedSubspecies = packet.readInt();
        this.selectedVariant = packet.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packet = new PacketBuffer(buf);
        packet.writeString(this.creatureName);
        packet.writeInt(this.selectedSubspecies);
        packet.writeInt(this.selectedVariant);
    }

    public static class ServerHandler implements IMessageHandler<PacketExtendedPlayerSelectedCreature, IMessage> {

        @Override
        public IMessage onMessage(final PacketExtendedPlayerSelectedCreature message, final MessageContext ctx) {
            FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
            return null;
        }

        private static void handle(PacketExtendedPlayerSelectedCreature message, MessageContext ctx) {
            ExtendedPlayer extendedPlayer = ExtendedPlayer.getForPlayer(ctx.getServerHandler().player);
            if(extendedPlayer != null) {
                extendedPlayer.selectedCreature = CreatureManager.getInstance().getCreature(message.creatureName);
                extendedPlayer.selectedSubspecies = message.selectedSubspecies;
                extendedPlayer.selectedVariant = message.selectedVariant;
            }
        }
    }
}
