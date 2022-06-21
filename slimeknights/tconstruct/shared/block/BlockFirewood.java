package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockFirewood extends EnumBlock<FirewoodType>
{
    public static final PropertyEnum<FirewoodType> TYPE;
    
    public BlockFirewood() {
        super(Material.field_151575_d, (PropertyEnum)BlockFirewood.TYPE, (Class)FirewoodType.class);
        this.func_149711_c(2.0f);
        this.func_149752_b(7.0f);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
        this.func_149715_a(0.5f);
        this.func_149672_a(SoundType.field_185848_a);
        this.setHarvestLevel("axe", -1);
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)FirewoodType.class);
    }
    
    public enum FirewoodType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        LAVAWOOD, 
        FIREWOOD;
        
        public final int meta;
        
        private FirewoodType() {
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
