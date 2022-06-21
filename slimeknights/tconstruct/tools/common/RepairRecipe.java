package slimeknights.tconstruct.tools.common;

import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.inventory.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.tools.*;
import com.google.common.collect.*;

public class RepairRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    private static final Set<Item> repairItems;
    
    public RepairRecipe() {
        this.setRegistryName(Util.getResource("repair"));
    }
    
    public boolean func_77569_a(@Nonnull final InventoryCrafting inv, @Nonnull final World worldIn) {
        return !this.getRepairedTool(inv, true).func_190926_b();
    }
    
    @Nonnull
    public ItemStack func_77572_b(@Nonnull final InventoryCrafting inv) {
        return this.getRepairedTool(inv, true);
    }
    
    @Nonnull
    private ItemStack getRepairedTool(@Nonnull final InventoryCrafting inv, final boolean simulate) {
        ItemStack tool = null;
        NonNullList<ItemStack> input = (NonNullList<ItemStack>)NonNullList.func_191197_a(inv.func_70302_i_(), (Object)ItemStack.field_190927_a);
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            ItemStack slot = inv.func_70301_a(i);
            if (!slot.func_190926_b()) {
                slot = slot.func_77946_l();
                slot.func_190920_e(1);
                final Item item = slot.func_77973_b();
                if (item instanceof TinkersItem) {
                    if (tool != null) {
                        return ItemStack.field_190927_a;
                    }
                    tool = slot;
                }
                else {
                    if (!RepairRecipe.repairItems.contains(item)) {
                        return ItemStack.field_190927_a;
                    }
                    input.set(i, (Object)slot);
                }
            }
        }
        if (tool == null) {
            return ItemStack.field_190927_a;
        }
        if (simulate) {
            input = Util.deepCopyFixedNonNullList(input);
        }
        return ((TinkersItem)tool.func_77973_b()).repair(tool.func_77946_l(), input);
    }
    
    @Nonnull
    public ItemStack func_77571_b() {
        return ItemStack.field_190927_a;
    }
    
    public NonNullList<ItemStack> func_179532_b(@Nonnull final InventoryCrafting inv) {
        return (NonNullList<ItemStack>)NonNullList.func_191197_a(inv.func_70302_i_(), (Object)ItemStack.field_190927_a);
    }
    
    public boolean func_194133_a(final int width, final int height) {
        return width >= 3 && height >= 3;
    }
    
    public boolean func_192399_d() {
        return true;
    }
    
    static {
        repairItems = (Set)ImmutableSet.of((Object)TinkerTools.sharpeningKit);
    }
}
