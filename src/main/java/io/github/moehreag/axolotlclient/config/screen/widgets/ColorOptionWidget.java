package io.github.moehreag.axolotlclient.config.screen.widgets;

import io.github.moehreag.axolotlclient.config.Color;
import io.github.moehreag.axolotlclient.config.options.ColorOption;
import io.github.moehreag.axolotlclient.modules.hud.util.DrawUtil;
import io.github.moehreag.axolotlclient.modules.hud.util.Rectangle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;

public class ColorOptionWidget extends ButtonWidget {

    private final ColorOption option;

    private final TextFieldWidget textField;
    private final ButtonWidget openPicker;

    public ColorOptionWidget(int id, int x, int y, ColorOption option) {
        super(id, x, y, 150, 20, "");
        this.option=option;
        textField = new TextFieldWidget(0, MinecraftClient.getInstance().textRenderer, x, y, 128, 19);
        textField.write(option.get().toString());

        openPicker = new ButtonWidget(2, x+128, y, 21, 21, ""){
            @Override
            public void render(MinecraftClient client, int mouseX, int mouseY) {
                DrawUtil.fill(x, y, x+width, y+height, option.get().getAsInt());
                DrawUtil.outlineRect(new Rectangle(x, y, width, height), new Color(-6250336));

                // Color picker icon, indicating there will be a better, bigger color selection dialog
                // for everyone uncomfortable with hexcodes (to be made first)
                //drawTexture(x, y, 0, 0, 20, 20, 21, 21);
            }
        };
    }

    @Override
    public void render(MinecraftClient client, int mouseX, int mouseY) {

        textField.y = y;
        textField.x = x;
        textField.render();

        openPicker.y = y-1;
        openPicker.x = x+128;
        openPicker.render(client, mouseX, mouseY);

    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);
    }

    @Override
    public boolean isMouseOver(MinecraftClient client, int mouseX, int mouseY) {
        return super.isMouseOver(client, mouseX, mouseY);
    }

    public void mouseClicked(int mouseX, int mouseY){
        if(openPicker.isMouseOver(MinecraftClient.getInstance(), mouseX, mouseY)){
            // WIP -> open Color picking dialog
        } else {
            textField.mouseClicked(mouseX, mouseY, 0);
        }
    }

    public void tick(){
        if(textField.isFocused()) {
            textField.tick();
        }
    }

    public void keyPressed(char c, int code){
        if(textField.isFocused()) {
            textField.keyPressed(c, code);
            option.set(Color.parse(textField.getText()));
        }
    }
}