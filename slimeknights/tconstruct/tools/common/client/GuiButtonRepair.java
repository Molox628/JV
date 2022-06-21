package slimeknights.tconstruct.tools.common.client;

import net.minecraft.util.text.translation.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.client.*;

public class GuiButtonRepair extends GuiButtonItem<ToolBuildGuiInfo>
{
    public static final ToolBuildGuiInfo info;
    
    public GuiButtonRepair(final int buttonId, final int x, final int y) {
        super(buttonId, x, y, I18n.func_74838_a("gui.repair"), GuiButtonRepair.info);
    }
    
    @Override
    protected void drawIcon(final Minecraft mc) {
        mc.func_110434_K().func_110577_a(Icons.ICON);
        Icons.ICON_Anvil.draw(this.field_146128_h, this.field_146129_i);
    }
    
    static {
        final int x = 33;
        final int y = 42;
        (info = new ToolBuildGuiInfo()).addSlotPosition(x, y);
        GuiButtonRepair.info.addSlotPosition(x - 18, y + 20);
        GuiButtonRepair.info.addSlotPosition(x - 22, y - 5);
        GuiButtonRepair.info.addSlotPosition(x, y - 23);
        GuiButtonRepair.info.addSlotPosition(x + 22, y - 5);
        GuiButtonRepair.info.addSlotPosition(x + 18, y + 20);
    }
}
