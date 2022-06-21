package slimeknights.tconstruct.shared;

import net.minecraft.client.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.color.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.renderer.block.statemap.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.item.*;

public class CommonsClientProxy extends ClientProxy
{
    public static Minecraft minecraft;
    
    @Override
    public void init() {
        final BlockColors blockColors = CommonsClientProxy.minecraft.func_184125_al();
        blockColors.func_186722_a((IBlockColor)new IBlockColor() {
            public int func_186720_a(@Nonnull final IBlockState state, final IBlockAccess access, final BlockPos pos, final int tintIndex) {
                final BlockClearStainedGlass.EnumGlassColor type = (BlockClearStainedGlass.EnumGlassColor)state.func_177229_b((IProperty)BlockClearStainedGlass.COLOR);
                return type.getColor();
            }
        }, new Block[] { (Block)TinkerCommons.blockClearStainedGlass });
        CommonsClientProxy.minecraft.getItemColors().func_186731_a((IItemColor)new IItemColor() {
            public int func_186726_a(@Nonnull final ItemStack stack, final int tintIndex) {
                final IBlockState iblockstate = ((ItemBlock)stack.func_77973_b()).func_179223_d().func_176203_a(stack.func_77960_j());
                return blockColors.func_186724_a(iblockstate, (IBlockAccess)null, (BlockPos)null, tintIndex);
            }
        }, new Block[] { (Block)TinkerCommons.blockClearStainedGlass });
        super.init();
    }
    
    @Override
    public void registerModels() {
        ModelLoader.setCustomStateMapper((Block)TinkerCommons.blockClearStainedGlass, (IStateMapper)new StateMap.Builder().func_178442_a(new IProperty[] { (IProperty)BlockClearStainedGlass.COLOR }).func_178441_a());
        TinkerCommons.nuggets.registerItemModels();
        TinkerCommons.ingots.registerItemModels();
        TinkerCommons.materials.registerItemModels();
        TinkerCommons.edibles.registerItemModels();
        ModelRegisterUtil.registerItemModel(TinkerCommons.book, 0, "inventory");
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.blockMetal);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.blockSoil);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.blockOre);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.blockFirewood);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.blockSlime);
        ModelRegisterUtil.registerItemBlockMeta(TinkerCommons.blockSlimeCongealed);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.slabFirewood);
        ModelRegisterUtil.registerItemModel(TinkerCommons.stairsFirewood);
        ModelRegisterUtil.registerItemModel(TinkerCommons.stairsLavawood);
        ModelRegisterUtil.registerItemModel(TinkerCommons.blockClearGlass);
        ModelRegisterUtil.registerItemModel((Block)TinkerCommons.blockClearStainedGlass);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.blockDecoGround);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerCommons.slabDecoGround);
        ModelRegisterUtil.registerItemModel(TinkerCommons.stairsMudBrick);
    }
    
    static {
        CommonsClientProxy.minecraft = Minecraft.func_71410_x();
    }
}
