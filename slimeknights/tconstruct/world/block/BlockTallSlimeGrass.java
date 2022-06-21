package slimeknights.tconstruct.world.block;

import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.world.*;
import net.minecraftforge.common.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockTallSlimeGrass extends BlockBush implements IShearable
{
    public static PropertyEnum<SlimePlantType> TYPE;
    public static PropertyEnum<BlockSlimeGrass.FoliageType> FOLIAGE;
    
    public BlockTallSlimeGrass() {
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_149672_a(SoundType.field_185850_c);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final SlimePlantType type : SlimePlantType.values()) {
            for (final BlockSlimeGrass.FoliageType foliage : BlockSlimeGrass.FoliageType.values()) {
                list.add((Object)new ItemStack((Block)this, 1, this.func_176201_c(this.func_176223_P().func_177226_a((IProperty)BlockTallSlimeGrass.TYPE, (Comparable)type).func_177226_a((IProperty)BlockTallSlimeGrass.FOLIAGE, (Comparable)foliage))));
            }
        }
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockTallSlimeGrass.TYPE, (IProperty)BlockTallSlimeGrass.FOLIAGE });
    }
    
    public int func_176201_c(final IBlockState state) {
        int meta = ((SlimePlantType)state.func_177229_b((IProperty)BlockTallSlimeGrass.TYPE)).getMeta();
        meta |= ((BlockSlimeGrass.FoliageType)state.func_177229_b((IProperty)BlockTallSlimeGrass.FOLIAGE)).ordinal() << 2;
        return meta;
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        int type = meta & 0x3;
        if (type >= SlimePlantType.values().length) {
            type = 0;
        }
        int foliage = meta >> 2;
        if (foliage >= BlockSlimeGrass.FoliageType.values().length) {
            foliage = 0;
        }
        IBlockState state = this.func_176223_P();
        state = state.func_177226_a((IProperty)BlockTallSlimeGrass.TYPE, (Comparable)SlimePlantType.values()[type]);
        state = state.func_177226_a((IProperty)BlockTallSlimeGrass.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.values()[foliage]);
        return state;
    }
    
    public boolean func_176200_f(final IBlockAccess worldIn, @Nonnull final BlockPos pos) {
        return true;
    }
    
    @Nonnull
    public ItemStack getPickBlock(@Nonnull final IBlockState state, final RayTraceResult target, @Nonnull final World world, @Nonnull final BlockPos pos, final EntityPlayer player) {
        final int meta = this.func_176201_c(state);
        return new ItemStack(Item.func_150898_a((Block)this), 1, meta);
    }
    
    public Item func_180660_a(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    public boolean func_176196_c(final World worldIn, final BlockPos pos) {
        final IBlockState soil = worldIn.func_180495_p(pos.func_177977_b());
        final Block ground = soil.func_177230_c();
        return ground == TinkerWorld.slimeGrass || ground == TinkerWorld.slimeDirt;
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public Block.EnumOffsetType func_176218_Q() {
        return Block.EnumOffsetType.XYZ;
    }
    
    @Nonnull
    public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) {
        return TinkerWorld.slimePlantType;
    }
    
    public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos) {
        return true;
    }
    
    public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune) {
        final IBlockState state = world.func_180495_p(pos);
        final ItemStack stack = new ItemStack((Block)this, 1, this.func_176201_c(state));
        return (List<ItemStack>)Lists.newArrayList((Object[])new ItemStack[] { stack });
    }
    
    static {
        BlockTallSlimeGrass.TYPE = (PropertyEnum<SlimePlantType>)PropertyEnum.func_177709_a("type", (Class)SlimePlantType.class);
        BlockTallSlimeGrass.FOLIAGE = BlockSlimeGrass.FOLIAGE;
    }
    
    public enum SlimePlantType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        TALL_GRASS, 
        FERN;
        
        public final int meta;
        
        private SlimePlantType() {
            this.meta = this.ordinal();
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
