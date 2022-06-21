package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockSeared extends BlockEnumSmeltery<SearedType>
{
    public static final PropertyEnum<SearedType> TYPE;
    
    public BlockSeared() {
        super(Material.field_151576_e, BlockSeared.TYPE, SearedType.class);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)SearedType.class);
    }
    
    public enum SearedType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        STONE, 
        COBBLE, 
        PAVER, 
        BRICK, 
        BRICK_CRACKED, 
        BRICK_FANCY, 
        BRICK_SQUARE, 
        ROAD, 
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
        
        public int getMeta() {
            return this.meta;
        }
    }
}
