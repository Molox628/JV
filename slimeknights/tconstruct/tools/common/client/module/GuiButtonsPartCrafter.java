package slimeknights.tconstruct.tools.common.client.module;

import net.minecraft.inventory.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import java.io.*;
import slimeknights.tconstruct.library.client.*;

public class GuiButtonsPartCrafter extends GuiSideButtons
{
    private final IInventory patternChest;
    
    public GuiButtonsPartCrafter(final GuiPartBuilder parent, final Container container, final IInventory patternChest) {
        super(parent, container, 4, false);
        this.patternChest = patternChest;
    }
    
    public void updatePosition(final int parentX, final int parentY, final int parentSizeX, final int parentSizeY) {
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
        int index = 0;
        final List<ItemStack> patterns = (List<ItemStack>)Lists.newArrayList((Iterable)TinkerRegistry.getStencilTableCrafting());
        final ListIterator<ItemStack> iter = patterns.listIterator();
        while (iter.hasNext()) {
            final ItemStack pattern = iter.next();
            boolean found = false;
            for (int i = 0; i < this.patternChest.func_70302_i_(); ++i) {
                if (ItemStack.func_77989_b(pattern, this.patternChest.func_70301_a(i))) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                found = ItemStack.func_77989_b(pattern, this.parent.field_147002_h.func_75139_a(2).func_75211_c());
            }
            if (!found) {
                iter.remove();
            }
        }
        this.field_146292_n.clear();
        this.buttonCount = 0;
        for (final ItemStack stencil : patterns) {
            final Item part = Pattern.getPartFromTag(stencil);
            if (part != null) {
                if (!(part instanceof MaterialItem)) {
                    continue;
                }
                final ItemStack icon = ((MaterialItem)part).getItemstackWithMaterial(CustomTextureCreator.guiMaterial);
                final GuiButtonItem<ItemStack> button = new GuiButtonItem<ItemStack>(index++, -1, -1, icon, stencil);
                this.shiftButton(button, 0, 18);
                this.addSideButton(button);
            }
        }
        super.updatePosition(parentX, parentY, parentSizeX, parentSizeY);
    }
    
    protected void func_146284_a(final GuiButton button) throws IOException {
        if (button instanceof GuiButtonItem) {
            final ItemStack pattern = (ItemStack)((GuiButtonItem)button).data;
            TinkerNetwork.sendToServer((AbstractPacket)new PartCrafterSelectionPacket(pattern));
        }
    }
    
    protected void shiftButton(final GuiButtonItem<ItemStack> button, final int xd, final int yd) {
        button.setGraphics(Icons.ICON_Button.shift(xd, yd), Icons.ICON_ButtonHover.shift(xd, yd), Icons.ICON_ButtonPressed.shift(xd, yd), Icons.ICON);
    }
}
