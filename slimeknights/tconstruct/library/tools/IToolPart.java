package slimeknights.tconstruct.library.tools;

import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraftforge.fml.relauncher.*;

public interface IToolPart extends IMaterialItem
{
    int getCost();
    
    boolean canUseMaterial(final Material p0);
    
    default boolean canUseMaterialForRendering(final Material mat) {
        return this.canUseMaterial(mat);
    }
    
    boolean hasUseForStat(final String p0);
    
    default boolean canBeCrafted() {
        return true;
    }
    
    default boolean canBeCasted() {
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    default ItemStack getOutlineRenderStack() {
        return this.getItemstackWithMaterial(CustomTextureCreator.guiMaterial);
    }
}
