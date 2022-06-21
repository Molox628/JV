package slimeknights.tconstruct.tools.common.debug;

import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.tools.*;
import java.util.*;

public class TempToolCrafting extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe
{
    private ItemStack outputTool;
    
    public TempToolCrafting() {
        this.setRegistryName(Util.getResource("tool"));
    }
    
    public ItemStack func_77572_b(@Nonnull final InventoryCrafting p_77572_1_) {
        return this.outputTool;
    }
    
    public boolean func_77569_a(@Nonnull final InventoryCrafting inv, @Nonnull final World worldIn) {
        this.outputTool = ItemStack.field_190927_a;
        final NonNullList<ItemStack> input = (NonNullList<ItemStack>)NonNullList.func_191196_a();
        for (int i = 0; i < inv.func_70302_i_(); ++i) {
            final ItemStack slot = inv.func_70301_a(i);
            if (!slot.func_190926_b()) {
                input.add((Object)slot);
            }
        }
        final NonNullList<ItemStack> inputs = Util.deepCopyFixedNonNullList(input);
        for (final ToolCore tool : TinkerRegistry.getTools()) {
            this.outputTool = tool.buildItemFromStacks(inputs);
            if (!this.outputTool.func_190926_b()) {
                break;
            }
        }
        return this.outputTool != null;
    }
    
    public ItemStack func_77571_b() {
        return this.outputTool;
    }
    
    @Nonnull
    public NonNullList<ItemStack> func_179532_b(@Nonnull final InventoryCrafting inv) {
        return (NonNullList<ItemStack>)NonNullList.func_191196_a();
    }
    
    public boolean func_194133_a(final int width, final int height) {
        return width * height >= 2;
    }
}
