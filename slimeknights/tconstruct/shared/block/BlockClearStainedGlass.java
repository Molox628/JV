package slimeknights.tconstruct.shared.block;

import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockClearStainedGlass extends EnumBlockConnectedTexture<EnumGlassColor>
{
    public static final PropertyEnum<EnumGlassColor> COLOR;
    
    public BlockClearStainedGlass() {
        super(Material.field_151592_s, (PropertyEnum)BlockClearStainedGlass.COLOR, (Class)EnumGlassColor.class);
        this.func_149711_c(0.3f);
        this.setHarvestLevel("pickaxe", -1);
        this.func_149672_a(SoundType.field_185853_f);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.TRANSLUCENT;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    @Nonnull
    public MapColor func_180659_g(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return ((EnumGlassColor)state.func_177229_b((IProperty)BlockClearStainedGlass.COLOR)).getMapColor();
    }
    
    public boolean func_176225_a(final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos pos, final EnumFacing side) {
        return !this.canConnect(blockState, blockAccess.func_180495_p(pos.func_177972_a(side))) && super.func_176225_a(blockState, blockAccess, pos, side);
    }
    
    @Nullable
    public float[] getBeaconColorMultiplier(final IBlockState state, final World world, final BlockPos pos, final BlockPos beaconPos) {
        if (state.func_177228_b().containsKey((Object)BlockClearStainedGlass.COLOR)) {
            final EnumGlassColor color = (EnumGlassColor)state.func_177229_b((IProperty)BlockClearStainedGlass.COLOR);
            return color.rgb;
        }
        return null;
    }
    
    static {
        COLOR = PropertyEnum.func_177709_a("color", (Class)EnumGlassColor.class);
    }
    
    public enum EnumGlassColor implements IStringSerializable, EnumBlock.IEnumMeta
    {
        WHITE(16777215, MapColor.field_151666_j), 
        ORANGE(14188339, MapColor.field_151676_q), 
        MAGENTA(11685080, MapColor.field_151675_r), 
        LIGHT_BLUE(6724056, MapColor.field_151674_s), 
        YELLOW(15066419, MapColor.field_151673_t), 
        LIME(8375321, MapColor.field_151672_u), 
        PINK(15892389, MapColor.field_151671_v), 
        GRAY(5000268, MapColor.field_151670_w), 
        SILVER(10066329, MapColor.field_151680_x), 
        CYAN(5013401, MapColor.field_151679_y), 
        PURPLE(8339378, MapColor.field_151678_z), 
        BLUE(3361970, MapColor.field_151649_A), 
        BROWN(6704179, MapColor.field_151650_B), 
        GREEN(6717235, MapColor.field_151651_C), 
        RED(10040115, MapColor.field_151645_D), 
        BLACK(1644825, MapColor.field_151646_E);
        
        private final int color;
        private final MapColor mapColor;
        private final int meta;
        private final float[] rgb;
        
        private EnumGlassColor(final int color, final MapColor mapColor) {
            this.meta = this.ordinal();
            this.color = color;
            this.mapColor = mapColor;
            this.rgb = calcRGB(color);
        }
        
        private static float[] calcRGB(final int color) {
            final float[] out = { (color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f };
            return out;
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public int getColor() {
            return this.color;
        }
        
        public MapColor getMapColor() {
            return this.mapColor;
        }
    }
}
