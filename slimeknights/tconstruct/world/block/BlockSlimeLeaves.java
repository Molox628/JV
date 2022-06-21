package slimeknights.tconstruct.world.block;

import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.math.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.world.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import java.util.*;
import com.google.common.collect.*;

public class BlockSlimeLeaves extends BlockLeaves
{
    public BlockSlimeLeaves() {
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_149711_c(0.3f);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockSlimeLeaves.field_176236_b, (Comparable)false).func_177226_a((IProperty)BlockSlimeLeaves.field_176237_a, (Comparable)true));
    }
    
    public void func_180650_b(final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state, final Random rand) {
        super.func_180650_b(worldIn, pos, state, rand);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final BlockSlimeGrass.FoliageType type : BlockSlimeGrass.FoliageType.values()) {
            list.add((Object)new ItemStack((Block)this, 1, this.func_176201_c(this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)type))));
        }
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return Blocks.field_150362_t.func_149662_c(state);
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return Blocks.field_150362_t.func_180664_k();
    }
    
    public boolean func_176225_a(@Nonnull final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos pos, @Nonnull final EnumFacing side) {
        this.field_185686_c = !Blocks.field_150362_t.func_149662_c(blockState);
        return super.func_176225_a(blockState, blockAccess, pos, side);
    }
    
    protected int func_176232_d(final IBlockState state) {
        return 25;
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return Item.func_150898_a((Block)TinkerWorld.slimeSapling);
    }
    
    protected void func_176234_a(final World worldIn, final BlockPos pos, final IBlockState state, final int chance) {
        if (worldIn.field_73012_v.nextInt(chance) == 0) {
            ItemStack stack = null;
            if (state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE) == BlockSlimeGrass.FoliageType.PURPLE) {
                stack = TinkerCommons.matSlimeBallPurple.func_77946_l();
            }
            else if (state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE) == BlockSlimeGrass.FoliageType.BLUE) {
                if (worldIn.field_73012_v.nextInt(3) == 0) {
                    stack = TinkerCommons.matSlimeBallBlue.func_77946_l();
                }
                else {
                    stack = new ItemStack(Items.field_151123_aH);
                }
            }
            if (stack != null) {
                func_180635_a(worldIn, pos, stack);
            }
        }
    }
    
    public int func_180651_a(final IBlockState state) {
        return ((BlockSlimeGrass.FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE)).ordinal() & 0x3;
    }
    
    @Nonnull
    protected ItemStack func_180643_i(final IBlockState state) {
        return new ItemStack(Item.func_150898_a((Block)this), 1, ((BlockSlimeGrass.FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE)).ordinal() & 0x3);
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSlimeGrass.FOLIAGE, (IProperty)BlockSlimeLeaves.field_176236_b, (IProperty)BlockSlimeLeaves.field_176237_a });
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        int type = meta % 4;
        if (type < 0 || type >= BlockSlimeGrass.FoliageType.values().length) {
            type = 0;
        }
        final BlockSlimeGrass.FoliageType grass = BlockSlimeGrass.FoliageType.values()[type];
        return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)grass).func_177226_a((IProperty)BlockSlimeLeaves.field_176237_a, (Comparable)((meta & 0x4) == 0x0)).func_177226_a((IProperty)BlockSlimeLeaves.field_176236_b, (Comparable)((meta & 0x8) > 0));
    }
    
    public int func_176201_c(final IBlockState state) {
        int meta = ((BlockSlimeGrass.FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE)).ordinal() & 0x3;
        if (!(boolean)state.func_177229_b((IProperty)BlockSlimeLeaves.field_176237_a)) {
            meta |= 0x4;
        }
        if (state.func_177229_b((IProperty)BlockSlimeLeaves.field_176236_b)) {
            meta |= 0x8;
        }
        return meta;
    }
    
    @Nonnull
    public BlockPlanks.EnumType func_176233_b(final int meta) {
        throw new UnsupportedOperationException();
    }
    
    public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune) {
        final IBlockState state = world.func_180495_p(pos);
        return (List<ItemStack>)Lists.newArrayList((Object[])new ItemStack[] { this.func_180643_i(state) });
    }
    
    public boolean isLeaves(final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        return true;
    }
}
