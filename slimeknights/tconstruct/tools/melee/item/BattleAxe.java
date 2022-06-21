package slimeknights.tconstruct.tools.melee.item;

import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;
import javax.annotation.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;

public class BattleAxe extends AoeToolCore
{
    public BattleAxe() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toughToolRod), PartMaterialType.head(TinkerTools.broadAxeHead), PartMaterialType.head(TinkerTools.broadAxeHead), PartMaterialType.extra(TinkerTools.toughBinding) });
        this.addCategory(Category.WEAPON);
        this.setHarvestLevel("axe", 0);
    }
    
    @Override
    public ImmutableList<BlockPos> getAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin) {
        return ToolHelper.calcAOEBlocks(stack, world, player, origin, 2, 2, 1);
    }
    
    @Override
    public float damagePotential() {
        return 2.0f;
    }
    
    @Override
    public float damageCutoff() {
        return 30.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.0;
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 1, 2 };
    }
    
    @Nonnull
    public EnumActionResult func_180614_a(final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        return EnumActionResult.FAIL;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final HeadMaterialStats handle = materials.get(0).getStats("head");
        final HeadMaterialStats head1 = materials.get(1).getStats("head");
        final HeadMaterialStats head2 = materials.get(2).getStats("head");
        final HeadMaterialStats binding = materials.get(3).getStats("head");
        final ToolNBT data = new ToolNBT();
        data.harvestLevel = Math.max(head1.harvestLevel, head2.harvestLevel);
        data.durability = (head1.durability + head2.durability) / 2;
        final ToolNBT toolNBT = data;
        toolNBT.speed *= 0.5f;
        data.attack = (head1.attack + head2.attack) * 3.0f / 2.0f;
        return data;
    }
}
