package slimeknights.tconstruct.shared.block;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.properties.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class BlockSlimeCongealed extends Block
{
    private static final AxisAlignedBB AABB;
    
    public BlockSlimeCongealed() {
        super(Material.field_151571_B);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_149711_c(0.5f);
        this.field_149765_K = 0.5f;
        this.func_149649_H();
        this.func_149672_a(SoundType.field_185859_l);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final BlockSlime.SlimeType type : BlockSlime.SlimeType.values()) {
            list.add((Object)new ItemStack((Block)this, 1, type.meta));
        }
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSlime.TYPE });
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)BlockSlime.SlimeType.fromMeta(meta));
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((BlockSlime.SlimeType)state.func_177229_b((IProperty)BlockSlime.TYPE)).meta;
    }
    
    public int func_180651_a(final IBlockState state) {
        return this.func_176201_c(state);
    }
    
    public AxisAlignedBB func_180646_a(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos) {
        return BlockSlimeCongealed.AABB;
    }
    
    public void func_176216_a(final World world, final Entity entity) {
        if (!(entity instanceof EntityLivingBase) && !(entity instanceof EntityItem)) {
            super.func_176216_a(world, entity);
            return;
        }
        if (entity.field_70181_x < -0.25) {
            entity.field_70181_x *= -1.2000000476837158;
            entity.field_70143_R = 0.0f;
            if (entity instanceof EntityItem) {
                entity.field_70122_E = false;
            }
        }
        else {
            super.func_176216_a(world, entity);
        }
    }
    
    public void func_180658_a(final World worldIn, final BlockPos pos, final Entity entityIn, final float fallDistance) {
        entityIn.func_180430_e(fallDistance, 0.0f);
    }
    
    public boolean canSustainLeaves(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return true;
    }
    
    public void func_180663_b(@Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state) {
        final byte b0 = 4;
        final int i = b0 + 1;
        if (worldIn.func_175707_a(pos.func_177982_a(-i, -i, -i), pos.func_177982_a(i, i, i))) {
            for (final BlockPos blockpos1 : BlockPos.func_177980_a(pos.func_177982_a(-b0, -b0, -b0), pos.func_177982_a((int)b0, (int)b0, (int)b0))) {
                final IBlockState iblockstate1 = worldIn.func_180495_p(blockpos1);
                if (iblockstate1.func_177230_c().isLeaves(iblockstate1, (IBlockAccess)worldIn, blockpos1)) {
                    iblockstate1.func_177230_c().beginLeavesDecay(iblockstate1, worldIn, blockpos1);
                }
            }
        }
    }
    
    static {
        AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.625, 1.0);
    }
}
