package slimeknights.tconstruct.smeltery.item;

import slimeknights.mantle.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.client.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.entity.*;
import net.minecraftforge.fluids.*;

public class ItemTank extends ItemBlockMeta
{
    public ItemTank(final Block block) {
        super(block);
        this.func_185043_a(new ResourceLocation("amount"), (IItemPropertyGetter)TankCapacityGetter.INSTANCE);
    }
    
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        super.func_77624_a(stack, worldIn, (List)tooltip, flagIn);
        if (stack.func_77942_o()) {
            final FluidTank tank = new FluidTank(0);
            tank.readFromNBT(stack.func_77978_p());
            if (tank.getFluidAmount() > 0) {
                tooltip.add(Util.translateFormatted("tooltip.tank.fluid", tank.getFluid().getLocalizedName()));
                tooltip.add(Util.translateFormatted("tooltip.tank.amount", tank.getFluid().amount));
            }
        }
    }
    
    public enum TankCapacityGetter implements IItemPropertyGetter
    {
        INSTANCE;
        
        public float func_185085_a(final ItemStack stack, final World worldIn, final EntityLivingBase entityIn) {
            if (!stack.func_77942_o()) {
                return 0.0f;
            }
            final FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.func_77978_p());
            if (fluid != null && fluid.amount > 0) {
                return fluid.amount / 4000.0f;
            }
            return 0.0f;
        }
    }
}
