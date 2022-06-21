package slimeknights.tconstruct.library.tools;

import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public abstract class SwordCore extends TinkerToolCore
{
    public static final ImmutableSet<Material> effective_materials;
    
    public SwordCore(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.setHarvestLevel("sword", 0);
    }
    
    @Override
    public boolean isEffective(final IBlockState state) {
        return SwordCore.effective_materials.contains((Object)state.func_185904_a());
    }
    
    @Override
    public float func_150893_a(final ItemStack stack, final IBlockState state) {
        if (state.func_177230_c() == Blocks.field_150321_G) {
            return super.func_150893_a(stack, state) * 7.5f;
        }
        return super.func_150893_a(stack, state);
    }
    
    @Override
    public float miningSpeedModifier() {
        return 0.5f;
    }
    
    static {
        effective_materials = ImmutableSet.of((Object)Material.field_151569_G, (Object)Material.field_151582_l, (Object)Material.field_151589_v, (Object)Material.field_151572_C, (Object)Material.field_151584_j);
    }
}
