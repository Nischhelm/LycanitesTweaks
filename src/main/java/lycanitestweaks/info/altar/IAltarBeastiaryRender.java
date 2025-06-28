package lycanitestweaks.info.altar;

import lycanitestweaks.client.gui.beastiary.AltarsBeastiaryScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IAltarBeastiaryRender {

    @SideOnly(Side.CLIENT)
    void getBeastiaryRender(AltarsBeastiaryScreen altarsBeastiaryScreen, int mouseX, int mouseY, float partialTicks);
}
