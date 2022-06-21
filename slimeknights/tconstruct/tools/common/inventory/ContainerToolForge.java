package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.tools.common.tileentity.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.entity.*;

public class ContainerToolForge extends ContainerToolStation
{
    public ContainerToolForge(final InventoryPlayer playerInventory, final TileToolStation tile) {
        super(playerInventory, tile);
    }
    
    @Override
    protected Set<ToolCore> getBuildableTools() {
        return TinkerRegistry.getToolForgeCrafting();
    }
    
    @Override
    protected void playCraftSound(final EntityPlayer player) {
        Sounds.playSoundForAll((Entity)player, SoundEvents.field_187698_i, 0.9f, 0.95f + 0.2f * TConstruct.random.nextFloat());
    }
}
