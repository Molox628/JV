package slimeknights.tconstruct.library.client.crosshair;

import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.relauncher.*;

public interface ICustomCrosshairUser
{
    @SideOnly(Side.CLIENT)
    ICrosshair getCrosshair(final ItemStack p0, final EntityPlayer p1);
    
    @SideOnly(Side.CLIENT)
    float getCrosshairState(final ItemStack p0, final EntityPlayer p1);
}
