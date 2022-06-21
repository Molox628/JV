package slimeknights.tconstruct.world.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.world.*;
import net.minecraftforge.common.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockSlimeDirt extends EnumBlock<DirtType>
{
    public static PropertyEnum<DirtType> TYPE;
    
    public BlockSlimeDirt() {
        super(Material.field_151578_c, (PropertyEnum)BlockSlimeDirt.TYPE, (Class)DirtType.class);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_149711_c(0.55f);
        this.func_149672_a(SoundType.field_185859_l);
    }
    
    public boolean canSustainPlant(@Nonnull final IBlockState state, @Nonnull final IBlockAccess world, final BlockPos pos, @Nonnull final EnumFacing direction, final IPlantable plantable) {
        return plantable.getPlantType(world, pos) == TinkerWorld.slimePlantType || plantable.getPlantType(world, pos) == EnumPlantType.Plains;
    }
    
    static {
        BlockSlimeDirt.TYPE = (PropertyEnum<DirtType>)PropertyEnum.func_177709_a("type", (Class)DirtType.class);
    }
    
    public enum DirtType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        GREEN, 
        BLUE, 
        PURPLE, 
        MAGMA;
        
        public final int meta;
        
        private DirtType() {
            this.meta = this.ordinal();
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
