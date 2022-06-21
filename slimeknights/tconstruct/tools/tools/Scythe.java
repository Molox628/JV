package slimeknights.tconstruct.tools.tools;

import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.utils.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.enchantment.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;

public class Scythe extends Kama
{
    public static final float DURABILITY_MODIFIER = 2.2f;
    
    public Scythe() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toughToolRod), PartMaterialType.head(TinkerTools.scytheHead), PartMaterialType.extra(TinkerTools.toughBinding), PartMaterialType.handle(TinkerTools.toughToolRod) });
    }
    
    @Override
    public float damagePotential() {
        return 0.75f;
    }
    
    @Override
    public double attackSpeed() {
        return 0.8999999761581421;
    }
    
    @Override
    protected boolean breakBlock(final ItemStack stack, final BlockPos pos, final EntityPlayer player) {
        return isSilkTouch(stack) && super.breakBlock(stack, pos, player);
    }
    
    @Override
    protected void breakExtraBlock(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos pos, final BlockPos refPos) {
        if (isSilkTouch(stack)) {
            ToolHelper.shearExtraBlock(stack, world, player, pos, refPos);
            return;
        }
        ToolHelper.breakExtraBlock(stack, world, player, pos, refPos);
    }
    
    @Override
    public Set<String> getToolClasses(final ItemStack stack) {
        if (!isSilkTouch(stack)) {
            return Collections.emptySet();
        }
        return super.getToolClasses(stack);
    }
    
    @Override
    public int getHarvestLevel(final ItemStack stack, final String toolClass, @Nullable final EntityPlayer player, @Nullable final IBlockState blockState) {
        if ("shears".equals(toolClass) && !isSilkTouch(stack)) {
            return -1;
        }
        return super.getHarvestLevel(stack, toolClass, player, blockState);
    }
    
    private static boolean isSilkTouch(final ItemStack stack) {
        return EnchantmentHelper.func_77506_a(Enchantments.field_185306_r, stack) > 0;
    }
    
    @Override
    public ImmutableList<BlockPos> getAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin) {
        return ToolHelper.calcAOEBlocks(stack, world, player, origin, 3, 3, 3);
    }
    
    @Override
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity target) {
        if (player.func_184825_o(0.5f) <= 0.9f) {
            return super.onLeftClickEntity(stack, player, target);
        }
        final TinkerToolEvent.ExtraBlockBreak event = TinkerToolEvent.ExtraBlockBreak.fireEvent(stack, player, player.func_130014_f_().func_180495_p(target.func_180425_c()), 3, 3, 3, -1);
        if (event.isCanceled()) {
            return false;
        }
        player.func_130014_f_().func_184148_a((EntityPlayer)null, player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundEvents.field_187730_dW, player.func_184176_by(), 1.0f, 1.0f);
        player.func_184810_cG();
        final int distance = event.distance;
        boolean hit = false;
        for (final Entity entity : this.getAoeEntities(player, target, event)) {
            if (distance < 0 || entity.func_70032_d(target) <= distance) {
                hit |= ToolHelper.attackEntity(stack, this, (EntityLivingBase)player, entity, null, false);
            }
        }
        if (hit) {
            player.func_184821_cY();
        }
        return hit;
    }
    
    private List<Entity> getAoeEntities(final EntityPlayer player, final Entity target, final TinkerToolEvent.ExtraBlockBreak event) {
        final int width = (event.width - 1) / 2;
        final int height = (event.width - 1) / 2;
        final AxisAlignedBB box = new AxisAlignedBB(target.field_70165_t, target.field_70163_u, target.field_70161_v, target.field_70165_t + 1.0, target.field_70163_u + 1.0, target.field_70161_v + 1.0).func_72321_a((double)width, (double)height, (double)width);
        return (List<Entity>)player.func_130014_f_().func_72839_b((Entity)player, box);
    }
    
    @Override
    public boolean func_111207_a(final ItemStack stack, final EntityPlayer player, final EntityLivingBase target, final EnumHand hand) {
        if (!(target instanceof IShearable)) {
            return false;
        }
        final TinkerToolEvent.ExtraBlockBreak event = TinkerToolEvent.ExtraBlockBreak.fireEvent(stack, player, player.func_130014_f_().func_180495_p(target.func_180425_c()), 3, 3, 3, -1);
        if (event.isCanceled()) {
            return false;
        }
        final int distance = event.distance;
        boolean shorn = false;
        final int fortune = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, stack);
        for (final Entity entity : this.getAoeEntities(player, (Entity)target, event)) {
            if (distance < 0 || entity.func_70032_d((Entity)target) <= distance) {
                shorn |= this.shearEntity(stack, player.func_130014_f_(), player, entity, fortune);
            }
        }
        if (shorn) {
            this.swingTool(player, hand);
        }
        return shorn;
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 1, 2 };
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final HandleMaterialStats handle = materials.get(0).getStatsOrUnknown("handle");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final ExtraMaterialStats extra = materials.get(2).getStatsOrUnknown("extra");
        final HandleMaterialStats handle2 = materials.get(3).getStatsOrUnknown("handle");
        final ToolNBT data = new ToolNBT();
        data.head(head);
        data.extra(extra);
        data.handle(handle, handle2);
        final ToolNBT toolNBT = data;
        toolNBT.durability *= (int)2.2f;
        return data;
    }
}
