package lycanitestweaks.mixin.lycanitestweaksmajor.beastiaryclient;

import com.lycanitesmobs.client.gui.beastiary.ElementsBeastiaryScreen;
import com.lycanitesmobs.client.gui.beastiary.lists.ElementList;
import com.lycanitesmobs.core.info.ElementInfo;
import com.lycanitesmobs.core.info.ElementManager;
import lycanitestweaks.handlers.ForgeConfigProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Mixin(ElementList.class)
public abstract class ElementListMixin {

    @Shadow(remap = false)
    private Map<Integer, String> elementNames;

    @Inject(
            method = "<init>",
            at = @At(value = "RETURN"),
            remap = false
    )
    public void lycanitesTweaks_lycanitesMobsElementList_init(ElementsBeastiaryScreen parentGui, int width, int height, int top, int bottom, int x, CallbackInfo ci){
        this.lycanitesTweaks$refreshList();
    }

    @Unique
    private void lycanitesTweaks$refreshList(){
        this.elementNames.clear();

        int i = 0;
        List<ElementInfo> elements = new ArrayList<>(ElementManager.getInstance().elements.values());
        elements.sort(Comparator.comparing(ElementInfo::getTitle));

        for(ElementInfo elementInfo : elements) {
            if(!ForgeConfigProvider.getElementBeastiaryBlacklist().contains(elementInfo.name))
                this.elementNames.put(i++, elementInfo.name);
        }
    }
}
