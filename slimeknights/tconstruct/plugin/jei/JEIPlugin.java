package slimeknights.tconstruct.plugin.jei;

import slimeknights.tconstruct.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.plugin.jei.interpreter.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.smeltery.*;
import slimeknights.tconstruct.tools.common.*;
import slimeknights.tconstruct.plugin.jei.table.*;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.transfer.*;
import slimeknights.tconstruct.tools.common.block.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.smeltery.block.*;
import slimeknights.tconstruct.plugin.jei.smelting.*;
import java.util.*;
import slimeknights.tconstruct.plugin.jei.alloy.*;
import slimeknights.tconstruct.plugin.jei.casting.*;
import mezz.jei.api.gui.*;
import net.minecraft.client.gui.inventory.*;
import slimeknights.tconstruct.library.fluid.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.plugin.jei.drying.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.shared.block.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.*;
import slimeknights.tconstruct.smeltery.client.*;
import javax.annotation.*;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin
{
    public static IJeiHelpers jeiHelpers;
    private static final int craftOutputSlot = 0;
    private static final int craftInputSlot1 = 1;
    public static ICraftingGridHelper craftingGridHelper;
    public static IRecipeRegistry recipeRegistry;
    public static CastingRecipeCategory castingCategory;
    
    public void registerItemSubtypes(final ISubtypeRegistry registry) {
        final TableSubtypeInterpreter tableInterpreter = new TableSubtypeInterpreter();
        final PatternSubtypeInterpreter patternInterpreter = new PatternSubtypeInterpreter();
        if (TConstruct.pulseManager.isPulseLoaded("TinkerGadgets")) {
            registry.registerSubtypeInterpreter(Item.func_150898_a((Block)TinkerGadgets.rack), (ISubtypeRegistry.ISubtypeInterpreter)tableInterpreter);
        }
        if (TConstruct.pulseManager.isPulseLoaded("TinkerTools")) {
            registry.registerSubtypeInterpreter(Item.func_150898_a((Block)TinkerTools.toolTables), (ISubtypeRegistry.ISubtypeInterpreter)tableInterpreter);
            registry.registerSubtypeInterpreter(Item.func_150898_a((Block)TinkerTools.toolForge), (ISubtypeRegistry.ISubtypeInterpreter)tableInterpreter);
            final ToolPartSubtypeInterpreter toolPartInterpreter = new ToolPartSubtypeInterpreter();
            for (final IToolPart part : TinkerRegistry.getToolParts()) {
                if (part instanceof Item) {
                    registry.registerSubtypeInterpreter((Item)part, (ISubtypeRegistry.ISubtypeInterpreter)toolPartInterpreter);
                }
            }
            final ToolSubtypeInterpreter toolInterpreter = new ToolSubtypeInterpreter();
            for (final ToolCore tool : TinkerRegistry.getTools()) {
                registry.registerSubtypeInterpreter((Item)tool, (ISubtypeRegistry.ISubtypeInterpreter)toolInterpreter);
            }
            registry.registerSubtypeInterpreter((Item)TinkerTools.pattern, (ISubtypeRegistry.ISubtypeInterpreter)patternInterpreter);
        }
        if (TConstruct.pulseManager.isPulseLoaded("TinkerSmeltery")) {
            registry.registerSubtypeInterpreter((Item)TinkerSmeltery.cast, (ISubtypeRegistry.ISubtypeInterpreter)patternInterpreter);
            registry.registerSubtypeInterpreter((Item)TinkerSmeltery.clayCast, (ISubtypeRegistry.ISubtypeInterpreter)patternInterpreter);
        }
    }
    
    public void registerCategories(final IRecipeCategoryRegistration registry) {
        final IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        final IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        if (TConstruct.pulseManager.isPulseLoaded("TinkerSmeltery")) {
            JEIPlugin.castingCategory = new CastingRecipeCategory(guiHelper);
            registry.addRecipeCategories(new IRecipeCategory[] { (IRecipeCategory)new SmeltingRecipeCategory(guiHelper), (IRecipeCategory)new AlloyRecipeCategory(guiHelper), (IRecipeCategory)JEIPlugin.castingCategory });
        }
        if (TConstruct.pulseManager.isPulseLoaded("TinkerGadgets")) {
            registry.addRecipeCategories(new IRecipeCategory[] { (IRecipeCategory)new DryingRecipeCategory(guiHelper) });
        }
    }
    
    public void register(@Nonnull final IModRegistry registry) {
        JEIPlugin.jeiHelpers = registry.getJeiHelpers();
        final IGuiHelper guiHelper = JEIPlugin.jeiHelpers.getGuiHelper();
        JEIPlugin.craftingGridHelper = guiHelper.createCraftingGridHelper(1, 0);
        if (TConstruct.pulseManager.isPulseLoaded("TinkerTools")) {
            registry.handleRecipes((Class)TableRecipeFactory.TableRecipe.class, (IRecipeWrapperFactory)new TableRecipeHandler(), "minecraft.crafting");
            registry.getRecipeTransferRegistry().addRecipeTransferHandler((IRecipeTransferInfo)new CraftingStationRecipeTransferInfo());
            registry.addRecipeCatalyst((Object)new ItemStack((Block)TinkerTools.toolTables, 1, BlockToolTable.TableTypes.CraftingStation.meta), new String[] { "minecraft.crafting" });
        }
        if (TConstruct.pulseManager.isPulseLoaded("TinkerSmeltery")) {
            registry.handleRecipes((Class)AlloyRecipe.class, (IRecipeWrapperFactory)new AlloyRecipeHandler(), AlloyRecipeCategory.CATEGORY);
            registry.handleRecipes((Class)MeltingRecipe.class, (IRecipeWrapperFactory)new SmeltingRecipeHandler(), SmeltingRecipeCategory.CATEGORY);
            registry.handleRecipes((Class)CastingRecipeWrapper.class, (IRecipeWrapperFactory)new CastingRecipeHandler(), CastingRecipeCategory.CATEGORY);
            registry.addRecipeCatalyst((Object)new ItemStack((Block)TinkerSmeltery.smelteryController), new String[] { SmeltingRecipeCategory.CATEGORY, AlloyRecipeCategory.CATEGORY });
            registry.addRecipeCatalyst((Object)new ItemStack((Block)TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.TABLE.meta), new String[] { CastingRecipeCategory.CATEGORY });
            registry.addRecipeCatalyst((Object)new ItemStack((Block)TinkerSmeltery.castingBlock, 1, BlockCasting.CastingType.BASIN.meta), new String[] { CastingRecipeCategory.CATEGORY });
            registry.addRecipeCatalyst((Object)new ItemStack(TinkerSmeltery.searedFurnaceController), new String[] { "minecraft.smelting" });
            registry.addRecipes((Collection)SmeltingRecipeChecker.getSmeltingRecipes(), SmeltingRecipeCategory.CATEGORY);
            registry.addRecipes((Collection)AlloyRecipeChecker.getAlloyRecipes(), AlloyRecipeCategory.CATEGORY);
            registry.addRecipes((Collection)CastingRecipeChecker.getCastingRecipes(), CastingRecipeCategory.CATEGORY);
            registry.addAdvancedGuiHandlers(new IAdvancedGuiHandler[] { (IAdvancedGuiHandler)new TinkerGuiTankHandler((Class<GuiContainer>)GuiTinkerTank.class), (IAdvancedGuiHandler)new TinkerGuiTankHandler((Class<GuiContainer>)GuiSmeltery.class) });
            final IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
            for (final MaterialIntegration integration : TinkerRegistry.getMaterialIntegrations()) {
                if (!integration.isIntegrated() && integration.fluid instanceof FluidColored) {
                    final FluidStack stack = new FluidStack(integration.fluid, 1000);
                    blacklist.addIngredientToBlacklist((Object)stack);
                    blacklist.addIngredientToBlacklist((Object)FluidUtil.getFilledBucket(stack));
                }
            }
        }
        if (TConstruct.pulseManager.isPulseLoaded("TinkerGadgets")) {
            registry.handleRecipes((Class)DryingRecipe.class, (IRecipeWrapperFactory)new DryingRecipeHandler(), DryingRecipeCategory.CATEGORY);
            registry.addRecipes((Collection)DryingRecipeChecker.getDryingRecipes(), DryingRecipeCategory.CATEGORY);
            registry.addRecipeCatalyst((Object)BlockTable.createItemstack(TinkerGadgets.rack, 1, (Block)Blocks.field_150376_bx, 0), new String[] { DryingRecipeCategory.CATEGORY });
        }
    }
    
    public void onRuntimeAvailable(@Nonnull final IJeiRuntime jeiRuntime) {
        JEIPlugin.recipeRegistry = jeiRuntime.getRecipeRegistry();
    }
    
    private static class TinkerGuiTankHandler<T extends net.minecraft.client.gui.inventory.GuiContainer> implements IAdvancedGuiHandler<T>
    {
        private Class<T> clazz;
        
        public TinkerGuiTankHandler(final Class<T> clazz) {
            this.clazz = clazz;
        }
        
        @Nonnull
        public Class<T> getGuiContainerClass() {
            return this.clazz;
        }
        
        @Nullable
        public Object getIngredientUnderMouse(final T guiContainer, final int mouseX, final int mouseY) {
            return ((IGuiLiquidTank)guiContainer).getFluidStackAtPosition(mouseX, mouseY);
        }
    }
}
