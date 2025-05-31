package lycanitestweaks.mixin.lycanitestweaksmajor.beastiaryclient;

import com.lycanitesmobs.client.gui.beastiary.BeastiaryScreen;
import com.lycanitesmobs.core.info.CreatureInfo;
import lycanitestweaks.network.PacketExtendedPlayerSelectedCreature;
import lycanitestweaks.network.PacketHandler;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeastiaryScreen.class)
public abstract class BeastiaryScreenPacketMixin {

    @Inject(
            method = "onCreateDisplayEntity",
            at = @At("HEAD"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsBeastiaryScreen_onCreateDisplayEntitySendPacket(CreatureInfo creatureInfo, EntityLivingBase entity, CallbackInfo ci){
        PacketHandler.instance.sendToServer(new PacketExtendedPlayerSelectedCreature(creatureInfo, entity));
    }
}
