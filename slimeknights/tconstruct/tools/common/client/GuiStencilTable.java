package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.tools.common.client.module.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class GuiStencilTable extends GuiTinkerStation
{
    private static final ResourceLocation BACKGROUND;
    public static final int Column_Count = 4;
    protected GuiButtonsStencilTable buttons;
    protected GuiSideInventory sideInventory;
    protected ContainerPatternChest.DynamicChestInventory chestContainer;
    
    public GuiStencilTable(final InventoryPlayer playerInv, final World world, final BlockPos pos, final TileStencilTable tile) {
        super(world, pos, (ContainerTinkerStation<?>)tile.createContainer(playerInv, world, pos));
        this.addModule((GuiModule)(this.buttons = new GuiButtonsStencilTable(this, this.field_147002_h, false)));
        if (this.field_147002_h instanceof ContainerStencilTable) {
            final ContainerStencilTable container = (ContainerStencilTable)this.field_147002_h;
            this.chestContainer = (ContainerPatternChest.DynamicChestInventory)container.getSubContainer((Class)ContainerPatternChest.DynamicChestInventory.class);
            if (this.chestContainer != null) {
                this.addModule((GuiModule)(this.sideInventory = new GuiSideInventory(this, (Container)this.chestContainer, this.chestContainer.getSizeInventory(), this.chestContainer.columns, true, false)));
            }
        }
    }
    
    public void onSelectionPacket(final StencilTableSelectionPacket packet) {
        this.buttons.setSelectedbuttonByItem(packet.output);
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiStencilTable.BACKGROUND);
        if (this.sideInventory != null) {
            this.sideInventory.updateSlotCount(this.chestContainer.getSizeInventory());
        }
        this.drawIcon(this.field_147002_h.func_75139_a(0), Icons.ICON_Pattern);
        super.func_146976_a(partialTicks, mouseX, mouseY);
    }
    
    static {
        BACKGROUND = Util.getResource("textures/gui/stenciltable.png");
    }
}
