package slimeknights.tconstruct.library.tinkering;

import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;

public interface IMaterialItem
{
    String getMaterialID(final ItemStack p0);
    
    Material getMaterial(final ItemStack p0);
    
    ItemStack getItemstackWithMaterial(final Material p0);
}
