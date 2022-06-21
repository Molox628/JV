package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockSearedSlab2 extends EnumBlockSlab<SearedType> implements ITileEntityProvider
{
    public static final PropertyEnum<SearedType> TYPE;
    
    public BlockSearedSlab2() {
        super(Material.field_151576_e, (PropertyEnum)BlockSearedSlab2.TYPE, (Class)SearedType.class);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
        this.field_149758_A = true;
    }
    
    public IBlockState getFullBlock(final IBlockState state) {
        if (TinkerSmeltery.searedBlock == null) {
            return null;
        }
        return TinkerSmeltery.searedBlock.func_176223_P().func_177226_a((IProperty)BlockSeared.TYPE, (Comparable)((SearedType)state.func_177229_b((IProperty)BlockSearedSlab2.TYPE)).asSearedBlock());
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileSmelteryComponent();
    }
    
    public void func_180663_b(@Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state) {
        TinkerSmeltery.searedBlock.func_180663_b(worldIn, pos, state);
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        TinkerSmeltery.searedBlock.func_180633_a(worldIn, pos, state, placer, stack);
    }
    
    @Deprecated
    public boolean func_189539_a(final IBlockState state, final World worldIn, final BlockPos pos, final int id, final int param) {
        return TinkerSmeltery.searedBlock.func_189539_a(state, worldIn, pos, id, param);
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)SearedType.class);
    }
    
    public enum SearedType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        CREEPER, 
        BRICK_TRIANGLE, 
        BRICK_SMALL, 
        TILE;
        
        public final int meta;
        
        private SearedType() {
            this.meta = this.ordinal();
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public BlockSeared.SearedType asSearedBlock() {
            switch (this) {
                case CREEPER: {
                    return BlockSeared.SearedType.CREEPER;
                }
                case BRICK_SMALL: {
                    return BlockSeared.SearedType.BRICK_SMALL;
                }
                case BRICK_TRIANGLE: {
                    return BlockSeared.SearedType.BRICK_TRIANGLE;
                }
                case TILE: {
                    return BlockSeared.SearedType.TILE;
                }
                default: {
                    throw new IllegalArgumentException("Unknown enum value? Impossibru!");
                }
            }
        }
        
        public int getMeta() {
            return this.meta;
        }
    }
}
