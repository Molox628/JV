package slimeknights.tconstruct.smeltery;

import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.smeltery.block.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.vertex.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraftforge.client.model.*;
import slimeknights.tconstruct.smeltery.client.*;
import slimeknights.tconstruct.library.*;

public class SmelteryClientEvents
{
    private static final String LOCATION_CastingBlock;
    public static final ModelResourceLocation locCastingTable;
    public static final ModelResourceLocation locCastingBasin;
    private static final ResourceLocation MODEL_BlankCast;
    public static final ResourceLocation locBlankCast;
    public static final ResourceLocation locClayCast;
    
    @SubscribeEvent
    public void onModelBake(final ModelBakeEvent event) {
        this.wrap(event, SmelteryClientEvents.locCastingTable);
        this.wrap(event, SmelteryClientEvents.locCastingBasin);
        ToolClientEvents.replacePatternModel(SmelteryClientEvents.locBlankCast, SmelteryClientEvents.MODEL_BlankCast, event, CustomTextureCreator.castLocString, TinkerRegistry.getCastItems());
        ToolClientEvents.replacePatternModel(SmelteryClientEvents.locClayCast, SmelteryClientEvents.MODEL_BlankCast, event, CustomTextureCreator.castLocString, TinkerRegistry.getCastItems(), 10974360);
        for (final BlockTank.TankType type : BlockTank.TankType.values()) {
            this.replaceTankModel(event, new ModelResourceLocation(TinkerSmeltery.searedTank.getRegistryName(), type.func_176610_l()));
        }
    }
    
    private void wrap(final ModelBakeEvent event, final ModelResourceLocation loc) {
        final IBakedModel model = (IBakedModel)event.getModelRegistry().func_82594_a((Object)loc);
        if (model != null && model instanceof IBakedModel) {
            event.getModelRegistry().func_82595_a((Object)loc, (Object)new BakedTableModel(model, null, DefaultVertexFormats.field_176599_b));
        }
    }
    
    private void replaceTankModel(final ModelBakeEvent event, final ModelResourceLocation loc) {
        final IBakedModel baked = (IBakedModel)event.getModelRegistry().func_82594_a((Object)loc);
        if (baked != null) {
            event.getModelRegistry().func_82595_a((Object)loc, (Object)new TankItemModel(baked));
        }
    }
    
    static {
        LOCATION_CastingBlock = Util.resource("casting");
        locCastingTable = new ModelResourceLocation(SmelteryClientEvents.LOCATION_CastingBlock, "type=table");
        locCastingBasin = new ModelResourceLocation(SmelteryClientEvents.LOCATION_CastingBlock, "type=basin");
        MODEL_BlankCast = Util.getResource("item/cast");
        locBlankCast = Util.getResource("cast");
        locClayCast = Util.getResource("clay_cast");
    }
}
