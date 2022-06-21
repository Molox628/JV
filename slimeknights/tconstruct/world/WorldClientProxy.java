package slimeknights.tconstruct.world;

import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import slimeknights.tconstruct.world.entity.*;
import slimeknights.tconstruct.library.client.renderer.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraft.client.renderer.color.*;
import net.minecraft.block.properties.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.world.client.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.world.block.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class WorldClientProxy extends ClientProxy
{
    public static SlimeColorizer slimeColorizer;
    public static Minecraft minecraft;
    
    @Override
    public void preInit() {
        ((IReloadableResourceManager)WorldClientProxy.minecraft.func_110442_L()).func_110542_a((IResourceManagerReloadListener)WorldClientProxy.slimeColorizer);
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBlueSlime.class, (IRenderFactory)RenderTinkerSlime.FACTORY_BlueSlime);
        super.preInit();
    }
    
    @Override
    public void init() {
        final BlockColors blockColors = WorldClientProxy.minecraft.func_184125_al();
        blockColors.func_186722_a((state, access, pos, tintIndex) -> {
            final BlockSlimeGrass.FoliageType type = (BlockSlimeGrass.FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE);
            return this.getSlimeColorByPos(pos, type, null);
        }, new Block[] { TinkerWorld.slimeGrass, (Block)TinkerWorld.slimeGrassTall });
        blockColors.func_186722_a((state, access, pos, tintIndex) -> {
            final BlockSlimeGrass.FoliageType type = (BlockSlimeGrass.FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE);
            return this.getSlimeColorByPos(pos, type, SlimeColorizer.LOOP_OFFSET);
        }, new Block[] { (Block)TinkerWorld.slimeLeaves });
        blockColors.func_186722_a((state, access, pos, tintIndex) -> this.getSlimeColorByPos(pos, BlockSlimeGrass.FoliageType.BLUE, SlimeColorizer.LOOP_OFFSET), new Block[] { (Block)TinkerWorld.slimeVineBlue1, (Block)TinkerWorld.slimeVineBlue2, (Block)TinkerWorld.slimeVineBlue3 });
        blockColors.func_186722_a((state, access, pos, tintIndex) -> this.getSlimeColorByPos(pos, BlockSlimeGrass.FoliageType.PURPLE, SlimeColorizer.LOOP_OFFSET), new Block[] { (Block)TinkerWorld.slimeVinePurple1, (Block)TinkerWorld.slimeVinePurple2, (Block)TinkerWorld.slimeVinePurple3 });
        WorldClientProxy.minecraft.getItemColors().func_186731_a((stack, tintIndex) -> {
            final IBlockState iblockstate = ((ItemBlock)stack.func_77973_b()).func_179223_d().func_176203_a(stack.func_77960_j());
            return blockColors.func_186724_a(iblockstate, (IBlockAccess)null, (BlockPos)null, tintIndex);
        }, new Block[] { TinkerWorld.slimeGrass, (Block)TinkerWorld.slimeGrassTall, (Block)TinkerWorld.slimeLeaves, (Block)TinkerWorld.slimeVineBlue1, (Block)TinkerWorld.slimeVineBlue2, (Block)TinkerWorld.slimeVineBlue3, (Block)TinkerWorld.slimeVinePurple1, (Block)TinkerWorld.slimeVinePurple2, (Block)TinkerWorld.slimeVinePurple3 });
        super.init();
    }
    
    @Override
    public void registerModels() {
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeGrass, (IStateMapper)new StateMap.Builder().func_178442_a(new IProperty[] { (IProperty)BlockSlimeGrass.FOLIAGE }).func_178441_a());
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeLeaves, (IStateMapper)new StateMap.Builder().func_178442_a(new IProperty[] { (IProperty)BlockSlimeGrass.FOLIAGE, (IProperty)BlockLeaves.field_176236_b, (IProperty)BlockLeaves.field_176237_a }).func_178441_a());
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeGrassTall, (IStateMapper)new StateMap.Builder().func_178442_a(new IProperty[] { (IProperty)BlockSlimeGrass.FOLIAGE }).func_178441_a());
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeSapling, (IStateMapper)new StateMap.Builder().func_178442_a(new IProperty[] { (IProperty)BlockSlimeSapling.field_176479_b, (IProperty)BlockSapling.field_176480_a }).func_178441_a());
        IStateMapper vineMap = (IStateMapper)new CustomStateMap("slime_vine");
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeVineBlue1, vineMap);
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeVinePurple1, vineMap);
        vineMap = (IStateMapper)new CustomStateMap("slime_vine_mid");
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeVineBlue2, vineMap);
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeVinePurple2, vineMap);
        vineMap = (IStateMapper)new CustomStateMap("slime_vine_end");
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeVineBlue3, vineMap);
        ModelLoader.setCustomStateMapper((Block)TinkerWorld.slimeVinePurple3, vineMap);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerWorld.slimeDirt);
        final Item grass = Item.func_150898_a((Block)TinkerWorld.slimeGrass);
        for (final BlockSlimeGrass.FoliageType type : BlockSlimeGrass.FoliageType.values()) {
            for (final BlockSlimeGrass.DirtType dirt : BlockSlimeGrass.DirtType.values()) {
                final String variant = String.format("%s=%s,%s=%s", BlockSlimeGrass.SNOWY.func_177701_a(), BlockSlimeGrass.SNOWY.func_177702_a(Boolean.valueOf(false)), BlockSlimeGrass.TYPE.func_177701_a(), BlockSlimeGrass.TYPE.func_177702_a((Enum)dirt));
                final int meta = TinkerWorld.slimeGrass.func_176201_c(TinkerWorld.slimeGrass.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)dirt).func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)type));
                ModelLoader.setCustomModelResourceLocation(grass, meta, new ModelResourceLocation(grass.getRegistryName(), variant));
            }
        }
        final Item leaves = Item.func_150898_a((Block)TinkerWorld.slimeLeaves);
        for (final BlockSlimeGrass.FoliageType type2 : BlockSlimeGrass.FoliageType.values()) {
            ModelLoader.setCustomModelResourceLocation(leaves, type2.getMeta(), new ModelResourceLocation(leaves.getRegistryName(), "normal"));
        }
        IBlockState state = TinkerWorld.slimeSapling.func_176223_P();
        final Item sapling = Item.func_150898_a((Block)TinkerWorld.slimeSapling);
        ItemStack stack = new ItemStack(sapling, 1, TinkerWorld.slimeSapling.func_176201_c(state.func_177226_a((IProperty)BlockSlimeSapling.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.BLUE)));
        this.registerItemModelTiC(stack, "slime_sapling_blue");
        stack = new ItemStack(sapling, 1, TinkerWorld.slimeSapling.func_176201_c(state.func_177226_a((IProperty)BlockSlimeSapling.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.PURPLE)));
        this.registerItemModelTiC(stack, "slime_sapling_purple");
        stack = new ItemStack(sapling, 1, TinkerWorld.slimeSapling.func_176201_c(state.func_177226_a((IProperty)BlockSlimeSapling.FOLIAGE, (Comparable)BlockSlimeGrass.FoliageType.ORANGE)));
        this.registerItemModelTiC(stack, "slime_sapling_orange");
        for (final BlockSlimeGrass.FoliageType foliage : BlockSlimeGrass.FoliageType.values()) {
            state = TinkerWorld.slimeGrassTall.func_176223_P();
            state = state.func_177226_a((IProperty)BlockTallSlimeGrass.FOLIAGE, (Comparable)foliage);
            state = state.func_177226_a((IProperty)BlockTallSlimeGrass.TYPE, (Comparable)BlockTallSlimeGrass.SlimePlantType.TALL_GRASS);
            stack = new ItemStack((Block)TinkerWorld.slimeGrassTall, 1, TinkerWorld.slimeGrassTall.func_176201_c(state));
            this.registerItemModelTiC(stack, "slime_tall_grass");
            state = state.func_177226_a((IProperty)BlockTallSlimeGrass.TYPE, (Comparable)BlockTallSlimeGrass.SlimePlantType.FERN);
            stack = new ItemStack((Block)TinkerWorld.slimeGrassTall, 1, TinkerWorld.slimeGrassTall.func_176201_c(state));
            this.registerItemModelTiC(stack, "slime_fern");
        }
        this.registerItemModelTiC(new ItemStack((Block)TinkerWorld.slimeVineBlue1), "slime_vine");
        this.registerItemModelTiC(new ItemStack((Block)TinkerWorld.slimeVineBlue2), "slime_vine_mid");
        this.registerItemModelTiC(new ItemStack((Block)TinkerWorld.slimeVineBlue3), "slime_vine_end");
        this.registerItemModelTiC(new ItemStack((Block)TinkerWorld.slimeVinePurple1), "slime_vine");
        this.registerItemModelTiC(new ItemStack((Block)TinkerWorld.slimeVinePurple2), "slime_vine_mid");
        this.registerItemModelTiC(new ItemStack((Block)TinkerWorld.slimeVinePurple3), "slime_vine_end");
    }
    
    private int getSlimeColorByPos(BlockPos pos, final BlockSlimeGrass.FoliageType type, final BlockPos add) {
        if (pos == null) {
            return SlimeColorizer.getColorStatic(type);
        }
        if (add != null) {
            pos = pos.func_177971_a((Vec3i)add);
        }
        return SlimeColorizer.getColorForPos(pos, type);
    }
    
    static {
        WorldClientProxy.slimeColorizer = new SlimeColorizer();
        WorldClientProxy.minecraft = Minecraft.func_71410_x();
    }
}
