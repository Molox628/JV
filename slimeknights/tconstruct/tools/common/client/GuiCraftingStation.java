package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.tools.common.client.module.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.inventory.*;

@SideOnly(Side.CLIENT)
public class GuiCraftingStation extends GuiTinkerStation
{
    private static final ResourceLocation BACKGROUND;
    protected final TileCraftingStation tile;
    
    public GuiCraftingStation(final InventoryPlayer playerInv, final World world, final BlockPos pos, final TileCraftingStation tile) {
        super(world, pos, (ContainerTinkerStation<?>)tile.createContainer(playerInv, world, pos));
        this.tile = tile;
        if (this.field_147002_h instanceof ContainerCraftingStation) {
            final ContainerCraftingStation container = (ContainerCraftingStation)this.field_147002_h;
            final ContainerSideInventory chestContainer = (ContainerSideInventory)container.getSubContainer((Class)ContainerSideInventory.class);
            if (chestContainer != null) {
                if (chestContainer.getTile() instanceof TileEntityChest) {
                    ((TileEntityChest)chestContainer.getTile()).doubleChestHandler = null;
                }
                this.addModule((GuiModule)new GuiSideInventory(this, (Container)chestContainer, chestContainer.getSlotCount(), chestContainer.columns));
            }
        }
    }
    
    public boolean isSlotInChestInventory(final Slot slot) {
        final GuiModule module = this.getModuleForSlot(slot.field_75222_d);
        return module instanceof GuiSideInventory;
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiCraftingStation.BACKGROUND);
        super.func_146976_a(partialTicks, mouseX, mouseY);
    }
    
    static {
        BACKGROUND = new ResourceLocation("textures/gui/container/crafting_table.png");
    }
}
