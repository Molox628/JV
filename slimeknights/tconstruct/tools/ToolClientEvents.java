package slimeknights.tconstruct.tools;

import net.minecraftforge.fml.relauncher.*;
import com.google.common.base.*;
import net.minecraft.client.renderer.texture.*;
import slimeknights.tconstruct.tools.common.block.*;
import net.minecraftforge.client.event.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.vertex.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraftforge.client.model.*;
import slimeknights.tconstruct.tools.client.*;
import slimeknights.tconstruct.library.tools.*;
import com.google.common.collect.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.library.client.model.*;
import slimeknights.mantle.client.model.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.item.*;
import net.minecraft.util.text.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class ToolClientEvents
{
    public static Function<ResourceLocation, TextureAtlasSprite> textureGetter;
    private static final String LOCATION_ToolTable;
    private static final String LOCATION_ToolForge;
    public static final ModelResourceLocation locCraftingStation;
    public static final ModelResourceLocation locStencilTable;
    public static final ModelResourceLocation locPartBuilder;
    public static final ModelResourceLocation locToolStation;
    public static final ModelResourceLocation locToolForge;
    public static final ModelResourceLocation locPatternChest;
    public static final ModelResourceLocation locPartChest;
    private static final ResourceLocation MODEL_BlankPattern;
    public static final ResourceLocation locBlankPattern;
    
    private static ModelResourceLocation getTableLoc(final BlockToolTable.TableTypes type) {
        return new ModelResourceLocation(ToolClientEvents.LOCATION_ToolTable, String.format("%s=%s", BlockToolTable.TABLES.func_177701_a(), BlockToolTable.TABLES.func_177702_a((Enum)type)));
    }
    
    @SubscribeEvent
    public void onModelBake(final ModelBakeEvent event) {
        replacePatternModel(ToolClientEvents.locBlankPattern, ToolClientEvents.MODEL_BlankPattern, event, CustomTextureCreator.patternLocString, TinkerRegistry.getPatternItems());
        replaceTableModel(ToolClientEvents.locCraftingStation, event);
        replaceTableModel(ToolClientEvents.locStencilTable, event);
        replaceTableModel(ToolClientEvents.locPartBuilder, event);
        replaceTableModel(ToolClientEvents.locToolStation, event);
        replaceTableModel(ToolClientEvents.locToolForge, event);
        replaceChestModel(ToolClientEvents.locPatternChest, event);
        replaceChestModel(ToolClientEvents.locPartChest, event);
        event.getModelRegistry().func_82595_a((Object)new ModelResourceLocation(ToolClientEvents.LOCATION_ToolTable, "inventory"), event.getModelRegistry().func_82594_a((Object)ToolClientEvents.locToolStation));
        event.getModelRegistry().func_82595_a((Object)new ModelResourceLocation(ToolClientEvents.LOCATION_ToolForge, "inventory"), event.getModelRegistry().func_82594_a((Object)ToolClientEvents.locToolForge));
    }
    
    public static void replaceTableModel(final ModelResourceLocation location, final ModelBakeEvent event) {
        try {
            final IModel model = ModelLoaderRegistry.getModel((ResourceLocation)location);
            final IBakedModel standard = (IBakedModel)event.getModelRegistry().func_82594_a((Object)location);
            final IBakedModel finalModel = (IBakedModel)new BakedTableModel(standard, model, DefaultVertexFormats.field_176600_a);
            event.getModelRegistry().func_82595_a((Object)location, (Object)finalModel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void replaceChestModel(final ModelResourceLocation location, final ModelBakeEvent event) {
        try {
            final IBakedModel original = (IBakedModel)event.getModelRegistry().func_82594_a((Object)location);
            final IBakedModel finalModel = (IBakedModel)new BakedChestModel(original);
            event.getModelRegistry().func_82595_a((Object)location, (Object)finalModel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void replacePatternModel(final ResourceLocation locPattern, final ResourceLocation modelLocation, final ModelBakeEvent event, final String baseString, final Iterable<Item> items) {
        replacePatternModel(locPattern, modelLocation, event, baseString, items, -1);
    }
    
    public static void replacePatternModel(final ResourceLocation locPattern, final ResourceLocation modelLocation, final ModelBakeEvent event, final String baseString, final Iterable<Item> items, final int color) {
        try {
            final IModel model = ModelLoaderRegistry.getModel(modelLocation);
            if (model instanceof IModel) {
                for (final Item item : items) {
                    final String suffix = Pattern.getTextureIdentifier(item);
                    final String partPatternLocation = locPattern.toString() + suffix;
                    final String partPatternTexture = baseString + suffix;
                    final IModel partPatternModel = model.retexture(ImmutableMap.of((Object)"layer0", (Object)partPatternTexture));
                    IBakedModel baked = partPatternModel.bake(partPatternModel.getDefaultState(), DefaultVertexFormats.field_176599_b, (java.util.function.Function)ToolClientEvents.textureGetter);
                    if (color > -1) {
                        final ImmutableList.Builder<BakedQuad> quads = (ImmutableList.Builder<BakedQuad>)ImmutableList.builder();
                        for (final BakedQuad quad : baked.func_188616_a((IBlockState)null, (EnumFacing)null, 0L)) {
                            quads.add((Object)ModelHelper.colorQuad(color, quad));
                        }
                        baked = (IBakedModel)new BakedSimple.Wrapper(quads.build(), baked);
                    }
                    event.getModelRegistry().func_82595_a((Object)new ModelResourceLocation(partPatternLocation, "inventory"), (Object)baked);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void materialTooltip(final ItemTooltipEvent event) {
        for (final Material material : TinkerRegistry.getAllMaterials()) {
            if (material.matches(new ItemStack[] { event.getItemStack() }).isPresent()) {
                event.getToolTip().add(TextFormatting.DARK_GRAY + material.getLocalizedName());
            }
        }
    }
    
    static {
        ToolClientEvents.textureGetter = (Function<ResourceLocation, TextureAtlasSprite>)(location -> Minecraft.func_71410_x().func_147117_R().func_110572_b(location.toString()));
        LOCATION_ToolTable = Util.resource("tooltables");
        LOCATION_ToolForge = Util.resource("toolforge");
        locCraftingStation = getTableLoc(BlockToolTable.TableTypes.CraftingStation);
        locStencilTable = getTableLoc(BlockToolTable.TableTypes.StencilTable);
        locPartBuilder = getTableLoc(BlockToolTable.TableTypes.PartBuilder);
        locToolStation = getTableLoc(BlockToolTable.TableTypes.ToolStation);
        locToolForge = new ModelResourceLocation(ToolClientEvents.LOCATION_ToolForge, "normal");
        locPatternChest = getTableLoc(BlockToolTable.TableTypes.PatternChest);
        locPartChest = getTableLoc(BlockToolTable.TableTypes.PartChest);
        MODEL_BlankPattern = Util.getResource("item/pattern");
        locBlankPattern = Util.getResource("pattern");
    }
}
