package lycanitestweaks.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class RenderToggleButton extends GuiButton {

    // Lyca Beastiary ID that is unique and also not caught by default handling
    public static final int BUTTON_ID = -69420;

    public RenderToggleButton(int buttonID, int x, int y, int width, int height, String text) {
        super(buttonID, x, y, width, height, text);
        this.visible = false;
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY){
        if(this.hovered) this.enabled = !this.enabled;
        return this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }
}
