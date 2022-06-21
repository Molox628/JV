package slimeknights.tconstruct.gadgets.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockDriedClay extends EnumBlock<DriedClayType>
{
    public static final PropertyEnum<DriedClayType> TYPE;
    
    public BlockDriedClay() {
        super(Material.field_151576_e, (PropertyEnum)BlockDriedClay.TYPE, (Class)DriedClayType.class);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGadgets);
        this.func_149711_c(1.5f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185851_d);
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)DriedClayType.class);
    }
    
    public enum DriedClayType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        CLAY, 
        BRICK;
        
        public final int meta;
        
        private DriedClayType() {
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
