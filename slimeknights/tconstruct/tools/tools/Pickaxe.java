package slimeknights.tconstruct.tools.tools;

import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;

public class Pickaxe extends AoeToolCore
{
    public static final ImmutableSet<Material> effective_materials;
    
    public Pickaxe() {
        this(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.pickHead), PartMaterialType.extra(TinkerTools.binding) });
    }
    
    public Pickaxe(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.addCategory(Category.HARVEST);
        this.setHarvestLevel("pickaxe", 0);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, new slimeknights.tconstruct.library.materials.Material[0]);
            this.addInfiTool((List<ItemStack>)subItems, "InfiHarvester");
        }
    }
    
    @Override
    public boolean isEffective(final IBlockState state) {
        return Pickaxe.effective_materials.contains((Object)state.func_185904_a()) || ItemPickaxe.field_150915_c.contains(state.func_177230_c());
    }
    
    @Override
    public float damagePotential() {
        return 1.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.2000000476837158;
    }
    
    @Override
    protected ToolNBT buildTagData(final List<slimeknights.tconstruct.library.materials.Material> materials) {
        return this.buildDefaultTag(materials);
    }
    
    static {
        effective_materials = ImmutableSet.of((Object)Material.field_151573_f, (Object)Material.field_151574_g, (Object)Material.field_151576_e, (Object)Material.field_151588_w, (Object)Material.field_151592_s, (Object)Material.field_151598_x, (Object[])new Material[] { Material.field_76233_E });
    }
}
