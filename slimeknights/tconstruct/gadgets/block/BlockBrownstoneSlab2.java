package slimeknights.tconstruct.gadgets.block;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.block.properties.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockBrownstoneSlab2 extends EnumBlockSlab<BrownstoneType>
{
    public static final PropertyEnum<BrownstoneType> TYPE;
    
    public BlockBrownstoneSlab2() {
        super(Material.field_151576_e, (PropertyEnum)BlockBrownstoneSlab2.TYPE, (Class)BrownstoneType.class);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    public void func_176199_a(final World worldIn, final BlockPos pos, final Entity entity) {
        if (entity.func_70090_H()) {
            entity.field_70159_w *= 1.2;
            entity.field_70179_y *= 1.2;
        }
        else {
            entity.field_70159_w *= 1.25;
            entity.field_70179_y *= 1.25;
        }
    }
    
    public IBlockState getFullBlock(final IBlockState state) {
        if (TinkerGadgets.brownstone == null) {
            return null;
        }
        return TinkerGadgets.brownstone.func_176223_P().func_177226_a((IProperty)BlockBrownstone.TYPE, (Comparable)((BrownstoneType)state.func_177229_b((IProperty)BlockBrownstoneSlab2.TYPE)).asBrownstone());
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)BrownstoneType.class);
    }
    
    public enum BrownstoneType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        CREEPER, 
        BRICK_TRIANGLE, 
        BRICK_SMALL, 
        TILE;
        
        public final int meta;
        
        private BrownstoneType() {
            this.meta = this.ordinal();
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public BlockBrownstone.BrownstoneType asBrownstone() {
            switch (this) {
                case CREEPER: {
                    return BlockBrownstone.BrownstoneType.CREEPER;
                }
                case BRICK_SMALL: {
                    return BlockBrownstone.BrownstoneType.BRICK_SMALL;
                }
                case BRICK_TRIANGLE: {
                    return BlockBrownstone.BrownstoneType.BRICK_TRIANGLE;
                }
                case TILE: {
                    return BlockBrownstone.BrownstoneType.TILE;
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
