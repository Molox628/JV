package slimeknights.tconstruct.tools.common.client;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.*;

public class GuiToolForge extends GuiToolStation
{
    public GuiToolForge(final InventoryPlayer playerInv, final World world, final BlockPos pos, final TileToolForge tile) {
        super(playerInv, world, pos, tile);
        this.metal();
    }
    
    @Override
    public Set<ToolCore> getBuildableItems() {
        return TinkerRegistry.getToolForgeCrafting();
    }
}
