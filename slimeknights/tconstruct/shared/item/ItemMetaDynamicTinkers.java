package slimeknights.tconstruct.shared.item;

import slimeknights.mantle.item.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemMetaDynamicTinkers extends ItemMetaDynamic
{
    @SideOnly(Side.CLIENT)
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (int i = 0; i <= this.availabilityMask.length; ++i) {
                if (this.isValid(i)) {
                    if ((this != TinkerCommons.ingots && this != TinkerCommons.nuggets) || TinkerIntegration.isIntegrated(TinkerFluids.alubrass) || i != 5) {
                        subItems.add((Object)new ItemStack((Item)this, 1, i));
                    }
                }
            }
        }
    }
}
