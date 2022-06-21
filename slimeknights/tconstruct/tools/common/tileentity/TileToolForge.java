package slimeknights.tconstruct.tools.common.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.client.gui.inventory.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.tools.common.inventory.*;

public class TileToolForge extends TileToolStation
{
    public TileToolForge() {
        this.inventoryTitle = "gui.toolforge.name";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiToolForge(inventoryplayer, world, pos, this);
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerToolForge(inventoryplayer, this);
    }
}
