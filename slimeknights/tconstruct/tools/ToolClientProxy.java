package slimeknights.tconstruct.tools;

import net.minecraftforge.common.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.tools.common.block.*;
import net.minecraftforge.client.model.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.client.renderer.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.client.model.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.ranged.item.*;

public class ToolClientProxy extends ClientProxy
{
    @Override
    public void preInit() {
        super.preInit();
        MinecraftForge.EVENT_BUS.register((Object)new ToolClientEvents());
    }
    
    @Override
    public void postInit() {
        final RenderEvents renderEvents = new RenderEvents();
        MinecraftForge.EVENT_BUS.register((Object)renderEvents);
        ((IReloadableResourceManager)Minecraft.func_71410_x().func_110442_L()).func_110542_a((IResourceManagerReloadListener)renderEvents);
    }
    
    @Override
    public void registerModels() {
        Item tableItem = Item.func_150898_a((Block)TinkerTools.toolTables);
        ModelLoader.setCustomModelResourceLocation(tableItem, BlockToolTable.TableTypes.CraftingStation.meta, ToolClientEvents.locCraftingStation);
        ModelLoader.setCustomModelResourceLocation(tableItem, BlockToolTable.TableTypes.StencilTable.meta, ToolClientEvents.locStencilTable);
        ModelLoader.setCustomModelResourceLocation(tableItem, BlockToolTable.TableTypes.PartBuilder.meta, ToolClientEvents.locPartBuilder);
        ModelLoader.setCustomModelResourceLocation(tableItem, BlockToolTable.TableTypes.ToolStation.meta, ToolClientEvents.locToolStation);
        ModelLoader.setCustomModelResourceLocation(tableItem, BlockToolTable.TableTypes.PatternChest.meta, ToolClientEvents.locPatternChest);
        ModelLoader.setCustomModelResourceLocation(tableItem, BlockToolTable.TableTypes.PartChest.meta, ToolClientEvents.locPartChest);
        tableItem = Item.func_150898_a((Block)TinkerTools.toolForge);
        ModelLoader.setCustomModelResourceLocation(tableItem, 0, ToolClientEvents.locToolForge);
        final ResourceLocation patternLoc = ToolClientEvents.locBlankPattern;
        CustomTextureCreator.patternModelLocation = new ResourceLocation(patternLoc.func_110624_b(), "item/" + patternLoc.func_110623_a());
        ModelLoader.setCustomMeshDefinition((Item)TinkerTools.pattern, (ItemMeshDefinition)new PatternMeshDefinition(patternLoc));
        ModelRegisterUtil.registerPartModel(TinkerTools.shard);
        ModelRegisterUtil.registerPartModel(TinkerTools.sharpeningKit);
        final ModelResourceLocation boltCoreModelLocation = Util.getModelResource("parts/bolt_core" + ToolModelLoader.EXTENSION, "inventory");
        final ModelResourceLocation boltCoreGuiModelLocation = Util.getModelResource("parts/bolt_core_gui", "inventory");
        ModelLoader.setCustomMeshDefinition((Item)TinkerTools.boltCore, stack -> {
            if (stack == BoltCore.GUI_RENDER_ITEMSTACK) {
                return boltCoreGuiModelLocation;
            }
            return boltCoreModelLocation;
        });
        ModelLoader.registerItemVariants((Item)TinkerTools.boltCore, new ResourceLocation[] { (ResourceLocation)boltCoreGuiModelLocation });
        ModelLoader.registerItemVariants((Item)TinkerTools.boltCore, new ResourceLocation[] { (ResourceLocation)boltCoreModelLocation });
    }
}
