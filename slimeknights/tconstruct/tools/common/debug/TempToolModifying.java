package slimeknights.tconstruct.tools.common.debug;

import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.inventory.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.modifiers.*;

public class TempToolModifying extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    private ItemStack outputTool;
    
    public TempToolModifying() {
        this.setRegistryName(Util.getResource("mod"));
    }
    
    public ItemStack func_77572_b(@Nonnull final InventoryCrafting p_77572_1_) {
        return this.outputTool;
    }
    
    public boolean func_77569_a(@Nonnull final InventoryCrafting inv, @Nonnull final World worldIn) {
        this.outputTool = null;
        final NonNullList<ItemStack> stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(inv.func_70302_i_(), (Object)ItemStack.field_190927_a);
        ItemStack tool = ItemStack.field_190927_a;
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            stacks.set(i, (Object)inv.func_70301_a(i));
            if (!((ItemStack)stacks.get(i)).func_190926_b() && ((ItemStack)stacks.get(i)).func_77973_b() instanceof TinkersItem) {
                tool = (ItemStack)stacks.get(i);
                stacks.set(i, (Object)ItemStack.field_190927_a);
            }
        }
        if (tool.func_190926_b()) {
            return false;
        }
        try {
            this.outputTool = ToolBuilder.tryModifyTool(stacks, tool, false);
        }
        catch (TinkerGuiException e) {
            System.out.println(e.getMessage());
        }
        return this.outputTool != null;
    }
    
    public ItemStack func_77571_b() {
        return this.outputTool;
    }
    
    @Nonnull
    public NonNullList<ItemStack> func_179532_b(@Nonnull final InventoryCrafting inv) {
        final NonNullList<ItemStack> stacks = (NonNullList<ItemStack>)NonNullList.func_191197_a(inv.func_70302_i_(), (Object)ItemStack.field_190927_a);
        ItemStack tool = null;
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            stacks.set(i, (Object)inv.func_70301_a(i));
            if (!((ItemStack)stacks.get(i)).func_190926_b() && ((ItemStack)stacks.get(i)).func_77973_b() instanceof TinkersItem) {
                tool = (ItemStack)stacks.get(i);
                stacks.set(i, (Object)ItemStack.field_190927_a);
            }
        }
        try {
            ToolBuilder.tryModifyTool(stacks, tool, true);
        }
        catch (TinkerGuiException e) {
            e.printStackTrace();
        }
        return stacks;
    }
    
    public boolean func_194133_a(final int width, final int height) {
        return width * height >= 2;
    }
}
