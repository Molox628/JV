package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockOre extends EnumBlock<OreTypes>
{
    public static final PropertyEnum<OreTypes> TYPE;
    
    public BlockOre() {
        this(Material.field_151576_e);
    }
    
    public BlockOre(final Material material) {
        super(material, (PropertyEnum)BlockOre.TYPE, (Class)OreTypes.class);
        this.func_149711_c(10.0f);
        this.setHarvestLevel("pickaxe", 4);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)OreTypes.class);
    }
    
    public enum OreTypes implements IStringSerializable, EnumBlock.IEnumMeta
    {
        COBALT, 
        ARDITE;
        
        public final int meta;
        
        private OreTypes() {
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
