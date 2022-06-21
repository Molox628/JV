package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.block.properties.*;

public class BlockDecoGroundSlab extends EnumBlockSlab<BlockDecoGround.DecoGroundType>
{
    public static final PropertyEnum<BlockDecoGround.DecoGroundType> TYPE;
    
    public BlockDecoGroundSlab() {
        super(Material.field_151578_c, (PropertyEnum)BlockDecoGroundSlab.TYPE, (Class)BlockDecoGround.DecoGroundType.class);
        this.func_149711_c(2.0f);
        this.func_149672_a(SoundType.field_185849_b);
        this.setHarvestLevel("shovel", -1);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
    }
    
    public IBlockState getFullBlock(final IBlockState state) {
        return TinkerCommons.blockDecoGround.func_176223_P().func_177226_a((IProperty)BlockDecoGround.TYPE, state.func_177229_b((IProperty)BlockDecoGroundSlab.TYPE));
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)BlockDecoGround.DecoGroundType.class);
    }
}
