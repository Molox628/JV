package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.*;
import net.minecraftforge.fluids.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockMetal extends EnumBlock<MetalTypes>
{
    public static final PropertyEnum<MetalTypes> TYPE;
    
    public BlockMetal() {
        super(Material.field_151573_f, (PropertyEnum)BlockMetal.TYPE, (Class)MetalTypes.class);
        this.func_149711_c(5.0f);
        this.setHarvestLevel("pickaxe", -1);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final MetalTypes type : MetalTypes.values()) {
            if (type != MetalTypes.ALUBRASS || TinkerIntegration.isIntegrated(TinkerFluids.alubrass)) {
                list.add((Object)new ItemStack((Block)this, 1, type.getMeta()));
            }
        }
    }
    
    public boolean isBeaconBase(final IBlockAccess worldObj, final BlockPos pos, final BlockPos beacon) {
        return true;
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)MetalTypes.class);
    }
    
    public enum MetalTypes implements IStringSerializable, EnumBlock.IEnumMeta
    {
        COBALT, 
        ARDITE, 
        MANYULLYN, 
        KNIGHTSLIME, 
        PIGIRON, 
        ALUBRASS, 
        SILKY_JEWEL;
        
        public final int meta;
        
        private MetalTypes() {
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
