package slimeknights.tconstruct.tools.tools;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.google.common.collect.*;
import javax.annotation.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;

public class Shovel extends AoeToolCore
{
    public static final ImmutableSet<Material> effective_materials;
    
    public Shovel() {
        this(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.shovelHead), PartMaterialType.extra(TinkerTools.binding) });
    }
    
    protected Shovel(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.addCategory(Category.HARVEST);
        this.setHarvestLevel("shovel", 0);
    }
    
    @Override
    public boolean isEffective(final IBlockState state) {
        return Shovel.effective_materials.contains((Object)state.func_185904_a()) || ItemSpade.field_150916_c.contains(state.func_177230_c());
    }
    
    @Nonnull
    public EnumActionResult func_180614_a(final EntityPlayer player, final World world, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final ItemStack stack = player.func_184586_b(hand);
        if (ToolHelper.isBroken(stack)) {
            return EnumActionResult.FAIL;
        }
        EnumActionResult result = Items.field_151047_v.func_180614_a(player, world, pos, hand, facing, hitX, hitY, hitZ);
        if (result == EnumActionResult.SUCCESS) {
            TinkerToolEvent.OnShovelMakePath.fireEvent(stack, player, world, pos);
        }
        final Block block = world.func_180495_p(pos).func_177230_c();
        if (block == Blocks.field_150349_c || block == Blocks.field_185774_da) {
            for (final BlockPos aoePos : this.getAOEBlocks(stack, world, player, pos)) {
                if (ToolHelper.isBroken(stack)) {
                    break;
                }
                final EnumActionResult aoeResult = Items.field_151047_v.func_180614_a(player, world, aoePos, hand, facing, hitX, hitY, hitZ);
                if (result != EnumActionResult.SUCCESS) {
                    result = aoeResult;
                }
                if (aoeResult != EnumActionResult.SUCCESS) {
                    continue;
                }
                TinkerToolEvent.OnShovelMakePath.fireEvent(stack, player, world, aoePos);
            }
        }
        return result;
    }
    
    @Override
    public double attackSpeed() {
        return 1.0;
    }
    
    @Override
    public float damagePotential() {
        return 0.9f;
    }
    
    @Override
    protected ToolNBT buildTagData(final List<slimeknights.tconstruct.library.materials.Material> materials) {
        return this.buildDefaultTag(materials);
    }
    
    static {
        effective_materials = ImmutableSet.of((Object)Material.field_151577_b, (Object)Material.field_151578_c, (Object)Material.field_151595_p, (Object)Material.field_151596_z, (Object)Material.field_151597_y, (Object)Material.field_151571_B, (Object[])new Material[] { Material.field_151568_F });
    }
}
