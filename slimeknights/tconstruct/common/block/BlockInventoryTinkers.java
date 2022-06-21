package slimeknights.tconstruct.common.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.*;

public abstract class BlockInventoryTinkers extends BlockInventory
{
    protected BlockInventoryTinkers(final Material material) {
        super(material);
    }
    
    protected boolean openGui(final EntityPlayer player, final World world, final BlockPos pos) {
        player.openGui((Object)TConstruct.instance, 0, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
        return true;
    }
}
