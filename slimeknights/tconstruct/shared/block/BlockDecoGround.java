package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockDecoGround extends EnumBlock<DecoGroundType>
{
    public static final PropertyEnum<DecoGroundType> TYPE;
    
    public BlockDecoGround() {
        super(Material.field_151578_c, (PropertyEnum)BlockDecoGround.TYPE, (Class)DecoGroundType.class);
        this.func_149711_c(2.0f);
        this.func_149672_a(SoundType.field_185849_b);
        this.setHarvestLevel("shovel", -1);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)DecoGroundType.class);
    }
    
    public enum DecoGroundType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        MUDBRICK;
        
        public final int meta;
        
        private DecoGroundType() {
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
