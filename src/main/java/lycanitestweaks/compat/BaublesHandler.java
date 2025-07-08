package lycanitestweaks.compat;

import baubles.api.BaublesApi;
import com.lycanitesmobs.ObjectManager;
import net.minecraft.entity.player.EntityPlayer;

public class BaublesHandler {

    public static boolean hasSoulgazerBauble(EntityPlayer player){
        int baubleFound = BaublesApi.isBaubleEquipped(player, ObjectManager.getItem("soulgazer"));
        return (baubleFound != -1);
    }
}
