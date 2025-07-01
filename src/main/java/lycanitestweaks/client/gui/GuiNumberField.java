package lycanitestweaks.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiNumberField extends GuiTextField {

    public GuiNumberField(int id, FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(id, fontRenderer, x, y, width, height);
        this.setValidator(s -> s.matches("-?\\d*.?\\d*"));
    }

    // Probably should just use GuiTextField with exact regex
    @Override
    public boolean textboxKeyTyped(char typedChar, int keyCode){
        if(!(Character.isDigit(typedChar) || typedChar == '-') && this.getText().isEmpty()) return false;
        if(Character.isAlphabetic(typedChar)) return false;
        return super.textboxKeyTyped(typedChar, keyCode);
    }
}
