package slimeknights.tconstruct.smeltery.block;

import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockSearedGlass extends BlockEnumSmeltery<GlassType>
{
    public static final PropertyEnum<GlassType> TYPE;
    
    public BlockSearedGlass() {
        super(BlockSearedGlass.TYPE, GlassType.class);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_DOWN, (Comparable)Boolean.FALSE).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_EAST, (Comparable)Boolean.FALSE).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_NORTH, (Comparable)Boolean.FALSE).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_SOUTH, (Comparable)Boolean.FALSE).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_UP, (Comparable)Boolean.FALSE).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_WEST, (Comparable)Boolean.FALSE));
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSearedGlass.TYPE, (IProperty)BlockConnectedTexture.CONNECTED_DOWN, (IProperty)BlockConnectedTexture.CONNECTED_UP, (IProperty)BlockConnectedTexture.CONNECTED_NORTH, (IProperty)BlockConnectedTexture.CONNECTED_SOUTH, (IProperty)BlockConnectedTexture.CONNECTED_WEST, (IProperty)BlockConnectedTexture.CONNECTED_EAST });
    }
    
    @Nonnull
    public IBlockState func_176221_a(@Nonnull final IBlockState state, final IBlockAccess world, final BlockPos position) {
        return state.func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_DOWN, (Comparable)this.isSideConnectable(state, world, position, EnumFacing.DOWN)).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_EAST, (Comparable)this.isSideConnectable(state, world, position, EnumFacing.EAST)).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_NORTH, (Comparable)this.isSideConnectable(state, world, position, EnumFacing.NORTH)).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_SOUTH, (Comparable)this.isSideConnectable(state, world, position, EnumFacing.SOUTH)).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_UP, (Comparable)this.isSideConnectable(state, world, position, EnumFacing.UP)).func_177226_a((IProperty)BlockConnectedTexture.CONNECTED_WEST, (Comparable)this.isSideConnectable(state, world, position, EnumFacing.WEST));
    }
    
    private boolean isSideConnectable(final IBlockState state, final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
        final IBlockState connected = world.func_180495_p(pos.func_177972_a(side));
        return this.canConnect(state, connected);
    }
    
    private boolean canConnect(final IBlockState original, final IBlockState connected) {
        return connected.func_177230_c() == original.func_177230_c() && original.func_177227_a().contains(this.prop) && connected.func_177229_b((IProperty)this.prop) == original.func_177229_b((IProperty)this.prop);
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)GlassType.class);
    }
    
    public enum GlassType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        GLASS;
        
        public final int meta;
        
        private GlassType() {
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
