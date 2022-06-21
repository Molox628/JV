package slimeknights.tconstruct.library.book.elements;

import slimeknights.mantle.client.gui.book.element.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.gui.*;

@SideOnly(Side.CLIENT)
public class ElementTinkerItem extends ElementItem
{
    public boolean noTooltip;
    
    public ElementTinkerItem(final ItemStack item) {
        this(0, 0, 1.0f, item);
    }
    
    public ElementTinkerItem(final int x, final int y, final float scale, final Item item) {
        super(x, y, scale, item);
        this.noTooltip = false;
    }
    
    public ElementTinkerItem(final int x, final int y, final float scale, final Block item) {
        super(x, y, scale, item);
        this.noTooltip = false;
    }
    
    public ElementTinkerItem(final int x, final int y, final float scale, final ItemStack item) {
        super(x, y, scale, item);
        this.noTooltip = false;
    }
    
    public ElementTinkerItem(final int x, final int y, final float scale, final Collection<ItemStack> itemCycle) {
        super(x, y, scale, (Collection)itemCycle);
        this.noTooltip = false;
    }
    
    public ElementTinkerItem(final int x, final int y, final float scale, final Collection<ItemStack> itemCycle, final String action) {
        super(x, y, scale, (Collection)itemCycle, action);
        this.noTooltip = false;
    }
    
    public ElementTinkerItem(final int x, final int y, final float scale, final ItemStack... itemCycle) {
        super(x, y, scale, itemCycle);
        this.noTooltip = false;
    }
    
    public ElementTinkerItem(final int x, final int y, final float scale, final ItemStack[] itemCycle, final String action) {
        super(x, y, scale, itemCycle, action);
        this.noTooltip = false;
    }
    
    public void drawOverlay(final int mouseX, final int mouseY, final float partialTicks, FontRenderer fontRenderer) {
        if (this.noTooltip) {
            return;
        }
        if (this.tooltip == null) {
            fontRenderer = this.mc.field_71466_p;
        }
        super.drawOverlay(mouseX, mouseY, partialTicks, fontRenderer);
    }
}
