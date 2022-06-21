package slimeknights.tconstruct.shared;

import slimeknights.tconstruct.common.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.client.event.*;
import slimeknights.tconstruct.library.fluid.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.block.statemap.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import net.minecraft.item.*;

public class FluidsClientProxy extends ClientProxy
{
    @Override
    public void registerModels() {
        super.registerModels();
        this.registerFluidModels(TinkerFluids.searedStone);
        this.registerFluidModels(TinkerFluids.obsidian);
        this.registerFluidModels(TinkerFluids.clay);
        this.registerFluidModels(TinkerFluids.dirt);
        this.registerFluidModels(TinkerFluids.gold);
        this.registerFluidModels(TinkerFluids.emerald);
        this.registerFluidModels(TinkerFluids.glass);
        this.registerFluidModels(TinkerFluids.milk);
        this.registerFluidModels(TinkerFluids.blueslime);
        this.registerFluidModels(TinkerFluids.purpleSlime);
        this.registerFluidModels(TinkerFluids.blood);
    }
    
    @SubscribeEvent
    public void registerTextures(final TextureStitchEvent.Pre event) {
        final TextureMap map = event.getMap();
        map.func_174942_a(FluidColored.ICON_LiquidStill);
        map.func_174942_a(FluidColored.ICON_LiquidFlowing);
        map.func_174942_a(FluidColored.ICON_MilkStill);
        map.func_174942_a(FluidColored.ICON_MilkFlowing);
        map.func_174942_a(FluidColored.ICON_StoneStill);
        map.func_174942_a(FluidColored.ICON_StoneFlowing);
        map.func_174942_a(FluidMolten.ICON_MetalStill);
        map.func_174942_a(FluidMolten.ICON_MetalFlowing);
    }
    
    @Override
    public void registerFluidModels(final Fluid fluid) {
        if (fluid == null) {
            return;
        }
        final Block block = fluid.getBlock();
        if (block != null) {
            final Item item = Item.func_150898_a(block);
            final FluidStateMapper mapper = new FluidStateMapper(fluid);
            if (item != Items.field_190931_a) {
                ModelLoader.registerItemVariants(item, new ResourceLocation[0]);
                ModelLoader.setCustomMeshDefinition(item, (ItemMeshDefinition)mapper);
            }
            ModelLoader.setCustomStateMapper(block, (IStateMapper)mapper);
        }
    }
    
    public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
    {
        public final Fluid fluid;
        public final ModelResourceLocation location;
        
        public FluidStateMapper(final Fluid fluid) {
            this.fluid = fluid;
            this.location = new ModelResourceLocation(Util.getResource("fluid_block"), fluid.getName());
        }
        
        @Nonnull
        protected ModelResourceLocation func_178132_a(@Nonnull final IBlockState state) {
            return this.location;
        }
        
        @Nonnull
        public ModelResourceLocation func_178113_a(@Nonnull final ItemStack stack) {
            return this.location;
        }
    }
}
