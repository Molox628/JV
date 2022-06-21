package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.block.properties.*;

public class BlockFirewoodSlab extends EnumBlockSlab<BlockFirewood.FirewoodType>
{
    public BlockFirewoodSlab() {
        super(Material.field_151575_d, (PropertyEnum)BlockFirewood.TYPE, (Class)BlockFirewood.FirewoodType.class);
        this.func_149711_c(2.0f);
        this.func_149752_b(7.0f);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
        this.func_149715_a(0.5f);
        this.func_149672_a(SoundType.field_185848_a);
    }
    
    public IBlockState getFullBlock(final IBlockState state) {
        if (TinkerCommons.blockFirewood == null) {
            return null;
        }
        return TinkerCommons.blockFirewood.func_176223_P().func_177226_a((IProperty)BlockFirewood.TYPE, state.func_177229_b((IProperty)BlockFirewood.TYPE));
    }
}
