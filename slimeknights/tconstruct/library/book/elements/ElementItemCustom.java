package slimeknights.tconstruct.library.book.elements;

import slimeknights.mantle.client.gui.book.element.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;

@SideOnly(Side.CLIENT)
public class ElementItemCustom extends ElementItem
{
    public int depth;
    
    public ElementItemCustom(final int x, final int y, final float scale, final ItemStack... item) {
        super(x, y, scale, item);
        this.depth = 0;
    }
    
    public void draw(final int mouseX, final int mouseY, final float partialTicks, final FontRenderer fontRenderer) {
        GlStateManager.func_179109_b(0.0f, 0.0f, (float)(-this.depth));
        super.draw(mouseX, mouseY, partialTicks, fontRenderer);
        GlStateManager.func_179109_b(0.0f, 0.0f, (float)this.depth);
    }
    
    public void drawOverlay(final int mouseX, final int mouseY, final float partialTicks, final FontRenderer fontRenderer) {
    }
}
