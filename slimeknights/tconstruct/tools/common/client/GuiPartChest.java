package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.mantle.client.gui.*;
import slimeknights.tconstruct.tools.common.client.module.*;

@SideOnly(Side.CLIENT)
public class GuiPartChest extends GuiTinkerStation
{
    protected static final GuiElementScalable background;
    public GuiScalingChest guiInventory;
    
    public GuiPartChest(final InventoryPlayer playerInv, final World world, final BlockPos pos, final TilePartChest tile) {
        super(world, pos, (ContainerTinkerStation<?>)tile.createContainer(playerInv, world, pos));
        this.addModule((GuiModule)(this.guiInventory = new GuiScalingChest(this, (BaseContainer)this.container.getSubContainer((Class)ContainerPartChest.DynamicChestInventory.class))));
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiPartChest.BLANK_BACK);
        this.guiInventory.update(mouseX, mouseY);
        super.func_146976_a(partialTicks, mouseX, mouseY);
    }
    
    static {
        background = GuiGeneric.slotEmpty;
    }
}
