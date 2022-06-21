package slimeknights.tconstruct.library.tinkering;

import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.common.config.*;
import java.util.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;

public class MaterialItem extends Item implements IMaterialItem
{
    public MaterialItem() {
        this.func_77627_a(true);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final Material mat : TinkerRegistry.getAllMaterials()) {
                subItems.add((Object)this.getItemstackWithMaterial(mat));
                if (!Config.listAllMaterials) {
                    break;
                }
            }
        }
    }
    
    public String getMaterialID(final ItemStack stack) {
        return this.getMaterial(stack).identifier;
    }
    
    public Material getMaterial(final ItemStack stack) {
        final NBTTagCompound tag = TagUtil.getTagSafe(stack);
        return TinkerRegistry.getMaterial(tag.func_74779_i("Material"));
    }
    
    public ItemStack getItemstackWithMaterial(final Material material) {
        final ItemStack stack = new ItemStack((Item)this);
        final NBTTagCompound tag = new NBTTagCompound();
        tag.func_74778_a("Material", material.identifier);
        stack.func_77982_d(tag);
        return stack;
    }
}
