package slimeknights.tconstruct.library.client.crosshair;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

@SideOnly(Side.CLIENT)
public interface ICustomZoom
{
    float getZoomLevel(final ItemStack p0, final EntityPlayer p1);
}
